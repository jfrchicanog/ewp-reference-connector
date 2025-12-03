/**
 * Create a new task for notification to Algoria of an approval or modification of a IIa. It has the responability to enqueue the task and check the result.
 */
package eu.erasmuswithoutpaper.iia.common;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import eu.erasmuswithoutpaper.common.control.GlobalProperties;
import org.apache.http.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.*;
import java.util.zip.Deflater;

public class AlgoriaTaskService {

    private static final Logger logger = LoggerFactory.getLogger(AlgoriaTaskService.class);

    public static GlobalProperties globalProperties = new GlobalProperties();

    private final static ExecutorService exeService = Executors.newCachedThreadPool();
    private static BlockingQueue<Future<String>> queue = new LinkedBlockingDeque<Future<String>>(100);

    private final static CompletionService<String> completionService = new ExecutorCompletionService<String>(exeService, queue);

    private static Map<String, Integer> taskIterationCountMap = new HashMap<String, Integer>();

    /**
     * The task is enqueue
     *
     * @param task
     */
    public static void addTask(Callable<String> task) {
        completionService.submit(task);

        checkNextResult();
    }

    public static Callable<String> createTask(AlgoriaTaskTypeEnum type, AlgoriaTaskEnum mode, Map<String, String> params, Map<String, String> urlParams) {
        return createTask(type, mode, params, urlParams, UUID.randomUUID().toString());
    }

    /**
     * Create a task for sending a notification to Algoria in other moment
     *
     * @param mode Evaluate the sort of notification is needed, for approval o
     * for modification
     * @return
     */
    public static Callable<String> createTask(AlgoriaTaskTypeEnum type, AlgoriaTaskEnum mode, Map<String, String> jsonParams, Map<String, String> urlParams, String idTask) {

        //Create the task
        Callable<String> callableTask = () -> {

            //Build the json string
            ObjectMapper mapper = new ObjectMapper();
            ObjectNode node = mapper.createObjectNode();
            for (Map.Entry<String, String> entry : jsonParams.entrySet()) {
                node.put(entry.getKey(), entry.getValue());
            }

            String json = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(node);

            logger.info("Created json! " + json);

            String token = globalProperties.getAlgoriaAuthotizationToken();

            String url = null;
            //switch case to evaluate the mode
            logger.info("Type: " + type.toString() + " Mode: " + mode.toString());
            switch (type) {
                case COURSES:
                    switch (mode) {
                        case GET_LIST:
                            url = globalProperties.getAlgoriaGetCRListUrl();
                            break;
                        default:
                            break;
                    }
                    break;
                default:
                    break;
            }


            logger.info("ALGORIA URL: " + url);
            logger.info("ALGORIA TOKEN: " + token);

            //Invoke the method to execute the request
            Response result = MessageNotificationService.addNotification(url, urlParams, json, token);

            logger.info("Status code: " + result.getStatusInfo().getStatusCode());

            ObjectNode nodeRes = mapper.createObjectNode();
            nodeRes.put("idTask", idTask);
            nodeRes.put("type", type.toString());
            nodeRes.put("mode", mode.toString());
            nodeRes.put("params", json);
            nodeRes.put("statusCode", result.getStatusInfo().getStatusCode());

            if (result.getEntity() instanceof String) {
                String bodyResult = (String) result.getEntity();
                nodeRes.put("bodyResult", bodyResult);
            } else {
                nodeRes.put("bodyResult", "");
            }

            String jsonRes = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(nodeRes);
            logger.info(jsonRes);
            return jsonRes;
        };

        return callableTask;
    }

    /**
     * When the task is done the result is checked, if the received status code
     * is service unavailable then the task is enqueue again after two hour. It
     * is only possible to do this operation 10 times.
     */
    private static void checkNextResult() {

        try {

            Future<String> future = completionService.take();

            if (future.isDone()) {

                logger.info("Task was done!");

                String response = future.get();

                ObjectMapper mapper = new ObjectMapper();

                JsonNode node = mapper.readTree(response);
                int statusCode = node.get("statusCode").asInt();
                String bodyResult = node.get("bodyResult").asText();
                String typeTxt = node.get("type").asText();
                AlgoriaTaskTypeEnum type = AlgoriaTaskTypeEnum.valueOf(typeTxt);
                String modeTxt = node.get("mode").asText();
                AlgoriaTaskEnum mode = AlgoriaTaskEnum.valueOf(modeTxt);

                Map<String, String> urlParams = new HashMap<>();
                String urlParamsTxt = node.get("urlParams").asText();
                if (urlParamsTxt != null && !urlParamsTxt.isEmpty()) {
                    String jsonUrlParamsTxt = new String(Base64.getDecoder().decode(urlParamsTxt), StandardCharsets.UTF_8);
                    JsonNode urlParamsNode = mapper.readTree(jsonUrlParamsTxt);
                    urlParamsNode.fieldNames().forEachRemaining((String fieldName) -> {
                        urlParams.put(fieldName, urlParamsNode.get(fieldName).asText());
                    });
                }
                Map<String, String> jsonParams = new HashMap<>();
                String paramsTxt = node.get("params").asText();
                if (paramsTxt != null && !paramsTxt.isEmpty()) {
                    String jsonParamsTxt = new String(Base64.getDecoder().decode(paramsTxt), StandardCharsets.UTF_8);
                    JsonNode paramsNode = mapper.readTree(jsonParamsTxt);
                    paramsNode.fieldNames().forEachRemaining((String fieldName) -> {
                        jsonParams.put(fieldName, paramsNode.get(fieldName).asText());
                    });
                }


                String idTask = node.get("idTask").asText();

                //Evaluate the task result
                if (HttpStatus.SC_BAD_REQUEST == statusCode) {
                    logger.info("TAKS_SERVICE: BAD REQUEST");

                    String responseJSON = bodyResult;

                    logger.error("Error incorrect json! " + responseJSON);

                } else if (HttpStatus.SC_NOT_FOUND == statusCode) {
                    logger.info("TASK_SERVICE: NOT FOUND");

                    logger.error("Error the agreement was not found into Algoria");

                } else if (HttpStatus.SC_UNAUTHORIZED == statusCode) {
                    logger.info("TASK_SERVICE: UNAUTHORIZED");

                    String responseJSON = bodyResult;

                    logger.error("Error missing or invalid token! " + responseJSON);

                } else if (HttpStatus.SC_SERVICE_UNAVAILABLE == statusCode) {
                    logger.info("TASK_SERVICE: SERVICE UNAVAILABLE");

                    logger.info("The service (server) is temporarily unavailable but should be restored in the future");

                    //Evaluate the amount of sending iia notification attempts 
                    Integer count = taskIterationCountMap.get(idTask);
                    if (count == null) {
                        taskIterationCountMap.put(idTask, 1);
                    } else if (count == 10) {
                        taskIterationCountMap.remove(idTask);
                    } else {
                        count++;

                        taskIterationCountMap.put(idTask, count);

                        //Schedule add the task again after two hour
                        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
                        scheduler.schedule(new Runnable() {

                            @Override
                            public void run() {

                                //Reinsert the task into the queue
                                Callable<String> callableTask = createTask(type, mode, jsonParams, urlParams, idTask);
                                addTask(callableTask);
                            }

                        }, globalProperties.getAlgoriaTaskDelay(), TimeUnit.HOURS);

                        scheduler.shutdown();
                    }
                } else {
                    logger.info("TASK_SERVICE: OK");
                }
            }
        } catch (InterruptedException | ExecutionException | IOException e) {
            logger.info("TASK_SERVICE: EXCEPTION");

            logger.error("The task was interrupted! " + e.getMessage());
        }
    }

    public static String compress(String data) throws IOException {
        byte[] input = data.getBytes("UTF-8");

        Deflater deflater = new Deflater();
        deflater.setInput(input);
        deflater.finish();

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream(input.length);
        byte[] buffer = new byte[1024];

        while (!deflater.finished()) {
            int count = deflater.deflate(buffer);
            outputStream.write(buffer, 0, count);
        }
        outputStream.close();

        byte[] output = outputStream.toByteArray();
        return Base64.getEncoder().encodeToString(output);
    }

    public static Response sendGetRequest(AlgoriaTaskTypeEnum type, AlgoriaTaskEnum mode, Map<String, String> urlParams) throws JsonProcessingException {
        return sendGetRequest(type, mode, urlParams, null);
    }

    public static Response sendGetRequest(AlgoriaTaskTypeEnum type, AlgoriaTaskEnum mode, Map<String, String> urlParams, String id) throws JsonProcessingException {

        String token = globalProperties.getAlgoriaAuthotizationToken();

        String url = null;
        //switch case to evaluate the mode
        logger.info("Type: " + type.toString() + " Mode: " + mode.toString());
        switch (type) {
            case COURSES:
                switch (mode) {
                    case GET_LIST:
                        url = globalProperties.getAlgoriaGetCRListUrl();
                        break;
                    case GET_DETAILS:
                        url = globalProperties.getAlgoriaGetCRListUrl() + id;
                        break;
                    default:
                        break;
                }
                break;
            default:
                break;
        }

        logger.info("ALGORIA URL: " + url);
        logger.info("ALGORIA TOKEN: " + token);

        //log query params in formatted way
        StringBuilder paramsLog = new StringBuilder();
        for (Map.Entry<String, String> entry : urlParams.entrySet()) {
            paramsLog.append(entry.getKey()).append("=").append(entry.getValue()).append("&");
        }
        logger.info("ALGORIA URL PARAMS: " + paramsLog.toString());

        ClientBuilder clientBuilder = ClientBuilder.newBuilder();
        WebTarget target = clientBuilder.build().target(url);

        for (Map.Entry<String, String> entry : urlParams.entrySet()) {
            target = target.queryParam(entry.getKey(), entry.getValue());
        }

        Invocation.Builder builder = target.request()
                .header("Authorization", token);

        // GET, no body
        return builder.get();

    }

}
