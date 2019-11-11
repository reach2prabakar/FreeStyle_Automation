package com.planit.automation.common;


import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

public class Triggerexitjob implements Job{

	
	public void execute(JobExecutionContext context)
			throws JobExecutionException {
		
		System.exit(0);
	}
}
