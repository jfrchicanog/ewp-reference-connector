/**
 * Create a new task for notification to Algoria of an approval or modification of a IIa. It has the responability to enqueue the task and check the result.
 */
package eu.erasmuswithoutpaper.iia.common;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.core.Response;

import org.apache.http.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import eu.erasmuswithoutpaper.common.control.GlobalProperties;
import eu.erasmuswithoutpaper.imobility.control.IncomingMobilityConverter;
import java.math.BigDecimal;

public class IiaTaskService {

    private static final Logger logger = LoggerFactory.getLogger(IncomingMobilityConverter.class);

    static GlobalProperties globalProperties = new GlobalProperties();

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

    public static Callable<String> createTask(String iiaId, IiaTaskEnum mode, String approvingHeiId) {
        return createTask(iiaId, mode, approvingHeiId, "Approved Agreement");
    }

    /**
     * Create a task for sending a notification to Algoria in other moment
     *
     * @param iiaId
     * @param mode Evaluate the sort of notification is needed, for approval o
     * for modification
     * @return
     */
    public static Callable<String> createTask(String iiaId, IiaTaskEnum mode, String approvingHeiId, String description) {

        //Create the task
        Callable<String> callableTask = () -> {

            //Build the json string
            ObjectMapper mapper = new ObjectMapper();

            ObjectNode node = mapper.createObjectNode();
            node.put("agreement_uuid", iiaId);
            node.put("hei_id", approvingHeiId);
            node.put("description", description);

            String json = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(node);

            logger.info("Created json! " + json);

            String token = globalProperties.getAlgoriaAuthotizationToken();

            String url = null;
            //switch case to evaluate the mode
            logger.info("Mode: " + mode.toString());
            switch (mode) {
                case CREATED:
                    url = globalProperties.getAlgoriaCreatedURL();
                    break;
                case UPDATED:
                case MODIFY:
                    url = globalProperties.getAlgoriaModifyURL();
                    break;
                case APPROVED:
                    url = globalProperties.getAlgoriaApprovalURL();
                    break;
                case DELETED:
                    url = globalProperties.getAlgoriaDeleteURL();
                    break;
                case REVERTED:
                    url = globalProperties.getAlgoriaRevertURL();
                    break;
                case TERMINATED:
                    url = globalProperties.getAlgoriaTerminateURL();
                    break;
                default:
                    break;
            }

            logger.info("ALGORIA URL: " + url);
            logger.info("ALGORIA TOKEN: " + token);

            //Invoke the method to execute the request
            Response result = MessageNotificationService.addApprovalNotification(url, json, token);

            logger.info("Status code: " + result.getStatusInfo().getStatusCode());

            ObjectNode nodeRes = mapper.createObjectNode();
            nodeRes.put("agreement_uuid", iiaId);
            nodeRes.put("approvingHeiId", approvingHeiId);
            nodeRes.put("mode", mode.toString());
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
                String iiaApprovalId = node.get("agreement_uuid").asText();
                String approvingHeiId = node.get("approvingHeiId").asText();
                int statusCode = node.get("statusCode").asInt();
                String bodyResult = node.get("bodyResult").asText();
                String mode = node.get("mode").asText();
                IiaTaskEnum iiaTaskEnum = IiaTaskEnum.valueOf(mode);

                //Evaluate the task result
                if (HttpStatus.SC_BAD_REQUEST == statusCode) {
                    logger.info("-------------------------------------");
                    logger.info("Test1");
                    logger.info("-------------------------------------");

                    String responseJSON = bodyResult;

                    logger.error("Error incorrect json! " + responseJSON);

                } else if (HttpStatus.SC_NOT_FOUND == statusCode) {
                    logger.info("-------------------------------------");
                    logger.info("Test2");
                    logger.info("-------------------------------------");

                    logger.error("Error the agreement was not found into Algoria");

                } else if (HttpStatus.SC_UNAUTHORIZED == statusCode) {
                    logger.info("-------------------------------------");
                    logger.info("Test3");
                    logger.info("-------------------------------------");

                    String responseJSON = bodyResult;

                    logger.error("Error missing or invalid token! " + responseJSON);

                } else if (HttpStatus.SC_SERVICE_UNAVAILABLE == statusCode) {
                    logger.info("-------------------------------------");
                    logger.info("Test4");
                    logger.info("-------------------------------------");

                    logger.info("The service (server) is temporarily unavailable but should be restored in the future");

                    //Evaluate the amount of sending iia notification attempts 
                    Integer count = taskIterationCountMap.get(iiaApprovalId);
                    if (count == null) {
                        taskIterationCountMap.put(iiaApprovalId, 1);
                    } else if (count == 10) {
                        taskIterationCountMap.remove(iiaApprovalId);
                    } else {
                        count++;

                        taskIterationCountMap.put(iiaApprovalId, count);

                        //Schedule add the task again after two hour
                        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
                        scheduler.schedule(new Runnable() {

                            @Override
                            public void run() {

                                //Reinsert the task into the queue
                                Callable<String> callableTask = createTask(iiaApprovalId, iiaTaskEnum, approvingHeiId);
                                addTask(callableTask);
                            }

                        }, globalProperties.getAlgoriaTaskDelay(), TimeUnit.HOURS);

                        scheduler.shutdown();
                    }
                } else {
                    logger.info("-------------------------------------");
                    logger.info("Test4");
                    logger.info("-------------------------------------");
                }
            }
        } catch (InterruptedException | ExecutionException | IOException e) {
            logger.info("-------------------------------------");
            logger.info("Test6");
            logger.info("-------------------------------------");

            logger.error("The task was interrupted! " + e.getMessage());
        }
    }
}
