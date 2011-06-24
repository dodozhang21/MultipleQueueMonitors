package net.pureessence;

import java.util.Arrays;
import java.util.List;

import net.pureessence.worker.ClaimMonitorService;
import net.pureessence.worker.VippReplyMonitorService;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class PutMessagesOnQueue {
	private static List<String> claimQueueMessages = Arrays.asList("Yes", "Yes", "Yes", "Yes", "No");
	private static List<String> vippReplyQueueMessages = Arrays.asList("Yes", "Yes", "Yes", "Yes");
	
	public static void main(String[] args) throws InterruptedException {
		// Load Services
		ApplicationContext context = new ClassPathXmlApplicationContext(new String[] {
				"/app.xml",
				"/queues.xml",
				"/activemq.xml"
			});
		
		ClaimMonitorService claimMonitorService = context.getBean("claimMonitorService", ClaimMonitorService.class);
		
		for(String message : claimQueueMessages) {
			claimMonitorService.putMessage(message);
		}

		VippReplyMonitorService vippReplyMonitorService = context.getBean("vippReplyMonitorService", VippReplyMonitorService.class);
		
		for(String message : vippReplyQueueMessages) {
			vippReplyMonitorService.putMessage(message);
		}
	}
}
