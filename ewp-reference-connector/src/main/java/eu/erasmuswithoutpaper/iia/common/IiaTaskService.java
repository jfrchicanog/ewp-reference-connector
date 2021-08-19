/**
 * Create a new task for notification to Algoria of an approval or modification of a IIa. It has the responability to enqueue the task and check the result.
 */
package eu.erasmuswithoutpaper.iia.common;

import java.io.IOException;
import java.util.HashMap;
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

public class IiaTaskService {
	private static final Logger logger = LoggerFactory.getLogger(IncomingMobilityConverter.class);
	
	public final static String MODIFIED = "Modified";
	public final static String APPROVED = "Approved";
	
	@Inject
	static GlobalProperties globalProperties;
	
	private final static ExecutorService exeService = Executors.newCachedThreadPool();
	private static BlockingQueue<Future<String>> queue = new LinkedBlockingDeque<Future<String>>(100);
	
	private final static CompletionService<String> completionService = new ExecutorCompletionService<String>(exeService,queue);
	
	private static Map<String, Integer> taskIterationCountMap = new HashMap<String,Integer>();
	
	/**
	 * The task is enqueue
	 * @param task
	 */
	public static void addTask(Callable<String> task) {
		completionService.submit(task);
		
		checkNextResult();
	}
	
	public static Callable<String> createTask(String iiaId, String mode) {
		
		//Create the task
    	Callable<String> callableTask = () -> {
    		
    		//Build the json string
	        ObjectMapper mapper = new ObjectMapper();
	        
	    	ObjectNode node = mapper.createObjectNode();
	        node.put("agreement_uuid", iiaId);
	        node.put("description", "Approved Agreement");
	   
			String json = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(node);
				
			logger.info("Created json! " + json);
			
			String token = globalProperties.getAlgoriaAuthotizationToken();
			
			String url = null;
			if (APPROVED.equals(mode)) {
				url =  globalProperties.getAlgoriaApprovalURL();
			} else {
				url =  globalProperties.getAlgoriaModifyURL();
			}
    		
			//Invoke the method to execute the request
			Response result = MessageNotificationService.addApprovalNotification(url, json, token);
	   		
			ObjectNode nodeRes = mapper.createObjectNode();
			node.put("agreement_uuid", iiaId);
			node.put("mode", mode);
	        node.put("statusCode", result.getStatusInfo().getStatusCode());
	        
	        String bodyResult = (String) result.getEntity();
	        node.put("bodyResult", bodyResult);
	        
	        String jsonRes = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(nodeRes);
			
	   		return jsonRes;
	   	};
	   	
	   	return callableTask;
	}

	/**
	 * When the task is done the result is checked, if the received status code is service unavailable then the task is enqueue again after two hour.
	 * It is only possible to do this operation 10 times.
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
				 int statusCode = node.get("statusCode").asInt();
				 String bodyResult = node.get("bodyResult").asText();
				 String mode = node.get("mode").asText();
				
				//Evaluate the task result
				if (HttpStatus.SC_BAD_REQUEST == statusCode) {
					
					String responseJSON =  bodyResult;
					
					logger.error("Error incorrect json! " + responseJSON);
						
				} else if (HttpStatus.SC_NOT_FOUND == statusCode) {
					
					logger.error("Error the agreement was not found into Algoria");
				
				} else if (HttpStatus.SC_UNAUTHORIZED == statusCode) {
					
					String responseJSON =  bodyResult;
					
					logger.error("Error missing or invalid token! " + responseJSON);
					
				} else if (HttpStatus.SC_SERVICE_UNAVAILABLE == statusCode) {
					
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
								Callable<String> callableTask = createTask(iiaApprovalId, mode);
								addTask(callableTask);
				            }
				            
			            }, 2, TimeUnit.HOURS);
						
						scheduler.shutdown();
					}
				}
			}
		} catch (InterruptedException | ExecutionException | IOException e) {
			
			logger.error("The task was interrupted! " + e.getMessage());
		}
		
	}

}
