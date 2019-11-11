package com.planit.automation.library;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.testng.TestListenerAdapter;
import org.testng.TestNG;
import org.testng.ITestListener;


public class FrameworkTrigger {

	private static Logger logger = Logger.getLogger(FrameworkTrigger.class.getName());

	public static void main(String[] args) {
		System.out.println("Execution Started !!");
		logger.info("Execution Started !!");
		Global.resourceFolder = "\\";
		List<String> suitesList = new ArrayList<String>();
		TestListenerAdapter listener = new TestListenerAdapter();
		TestNG testng = new TestNG();
		testng.setOutputDirectory("test-output");
		suitesList.add("testng.xml");
		testng.setTestSuites(suitesList);
		testng.addListener(listener);
		testng.run(); 

	}

}
