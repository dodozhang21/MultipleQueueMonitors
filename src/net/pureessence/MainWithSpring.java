package net.pureessence;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.SynchronousQueue;

import net.pureessence.factory.DaemonThreadFactory;
import net.pureessence.worker.ClaimMonitorService;
import net.pureessence.worker.VippReplyMonitorService;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class MainWithSpring {
    private static final Logger logger = Logger.getLogger(MainWithSpring.class);
    
	public static void main(String[] args) throws InterruptedException {
		// Load the log4j properties
		PropertyConfigurator.configure("properties/log4j.properties");
		
		// log java version
		logger.info(String.format("Java Version '%s'", System.getProperty("java.version")));
		
		// Load Services
		ApplicationContext context = new ClassPathXmlApplicationContext(new String[] {
				"/app.xml",
				"/queues.xml",
				"/activemq.xml"
			});
		
		ExecutorService processMessageServicesExecutor = Executors.newFixedThreadPool(2, new DaemonThreadFactory());
		
		SynchronousQueue<Boolean> threadDiedQueue = new SynchronousQueue<Boolean>(); //this queue can hold 1 element at a time, so basically the first thread to finish will be the one to successfully put the element in the queue
		ClaimMonitorService claimMonitorService = context.getBean("claimMonitorService", ClaimMonitorService.class);
		claimMonitorService.setThreadDiedQueue(threadDiedQueue);
		VippReplyMonitorService vippReplyMonitorService = context.getBean("vippReplyMonitorService", VippReplyMonitorService.class);
		vippReplyMonitorService.setThreadDiedQueue(threadDiedQueue);
		
		processMessageServicesExecutor.execute(claimMonitorService);
		processMessageServicesExecutor.execute(vippReplyMonitorService);
		
		logger.info("started running");
		logger.info(String.format("the threadDiedQueue returned '%s'", threadDiedQueue.take()));
		logger.info("at least one thread died");
		  
		processMessageServicesExecutor.shutdown(); 
	}
}
