package com.org.ppe.batch.processor;



import java.io.File;
import java.util.Date;

import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.listener.JobExecutionListenerSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class JobCompletionNotificationListener extends JobExecutionListenerSupport {

   @Autowired
	public JobCompletionNotificationListener() {
		
	}
    @Override
	public void beforeJob(JobExecution jobEx) {
    	File file = new File("upload.csv");
    	if(!file.exists()) {
    		System.out.println("File Does not exist");
    		jobEx.stop();
    	}
    	
	}
    
	@Override
	public void afterJob(JobExecution jobEx) {
		if(jobEx.getStatus() == BatchStatus.COMPLETED) {
			System.out.println("batch process completed");
			Date date = new Date();
		      File oldfile = new File("upload.csv");
		      File newfile = new File(oldfile.getName() + date);
//change File Name
		      if(oldfile.renameTo(newfile)) {
		         System.out.println("File name changed succesful");
		      } else {
		         System.out.println("Rename failed");
		      } 
		}
	}
}
