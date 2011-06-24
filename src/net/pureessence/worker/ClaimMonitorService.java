package net.pureessence.worker;

import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

@Component
public class ClaimMonitorService extends BaseService  {
    private static final Logger logger = Logger.getLogger(ClaimMonitorService.class);
	
	@Override
	@Resource(name="claimQueues")
	public void setQueues(Map<String, String> queues) {
		super.setQueues(queues);
	}
	
	public boolean processMessage() {
		String queueName = getQueueName();
		//jmsTemplate.setReceiveTimeout(500);
		
		logger.info(String.format("Waiting for message from queue %s", queueName));
		String messageString = (String)jmsTemplate.receiveAndConvert(queueName);
		logger.info(String.format("Got message from queue %s", queueName));
		logger.info("messageString:");
		logger.info(messageString);
		
		logger.info("\r\n\r\n\r\n\r\n\r\n\r\n");
		
		if("Yes".equalsIgnoreCase(messageString)) {
			return true;
		} else {
			return false;
		}
		
		
	}
}
