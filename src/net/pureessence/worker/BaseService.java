package net.pureessence.worker;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.BlockingQueue;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jms.core.JmsTemplate;

public abstract class BaseService implements Runnable {
    private static final Logger logger = Logger.getLogger(BaseService.class);
    
	@Resource(name="environmentTypeString")
    protected String environmentTypeString;
	
	@Autowired
    @Qualifier("jmsTemplate")
    protected JmsTemplate jmsTemplate;
	
	private BlockingQueue<Boolean> threadDiedQueue;
	
    protected Map<String, String> queues = new LinkedHashMap<String, String>();
    
    protected Boolean result;
    
    public void putMessage(String message) {
    	String queueName = getQueueName();
		jmsTemplate.convertAndSend(queueName, message);
		logger.info(String.format("Inserted %s into queue %s", message, queueName));
    }
	
	protected String getQueueName() {
		return queues.get(environmentTypeString);
	}

	public void setQueues(Map<String, String> queues) {
		this.queues = queues;
	}
	
	public void setThreadDiedQueue(BlockingQueue<Boolean> threadDiedQueue) {
		this.threadDiedQueue = threadDiedQueue;
	}

	public void run() {
		boolean result = true;
		while(result) {
			result = processMessage();
		}
		threadDiedQueue.offer(Boolean.FALSE);
	}
	
	protected abstract boolean processMessage();
}
