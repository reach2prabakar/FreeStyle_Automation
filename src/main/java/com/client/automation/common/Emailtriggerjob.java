package com.client.automation.common;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.client.automation.common.Common;
import com.client.automation.library.Global;

public class Emailtriggerjob implements Job{

	Common genericMethods = new Common();
		public void execute(JobExecutionContext context)
				throws JobExecutionException {
			 
			try {
				if (Global.triggerjob.equalsIgnoreCase("Yes")){
				genericMethods.sendEmailevery15min();
				}
				
			} catch (AddressException e) {
				
				e.printStackTrace();
			} catch (MessagingException e) {
				
				e.printStackTrace();
			}
			 
		}

}	
		
	
