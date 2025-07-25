package com.planit.automation.keywords;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;


/**
 * Node - Keyword
 * @class Node
 * @description - Node related all Actions are mentioned in this class
 * 
 * @throws Exception
 */

public class Node{
	
	private static Logger logger = Logger.getLogger(Node.class.getName());
	public String logMessage = null;
	private String keyword = "Node";
	public String err1="No";
	public WebDriver driver;

	public Node(WebDriver driver){
		this.driver = driver;
	}
	
	/**
	 * Node - Keyword & close - Action
	 * @method expand
	 * @description - expand action to expand node to select / search further activities.
	 * 
	 * @throws Exception
	 */
	public String expand(By nodeName) throws Exception {
		try{
			err1="No";
			driver.findElement(nodeName).click(); // Click on "+" Icon
			logMessage = "At test Step - Keyword = "+keyword+", Action = "+ "expand"+" , Locator = " + nodeName.toString(); // This is to Display on console as well in Log File
			System.out.println(logMessage); // Print on Console
			//logging
			logger.info(logMessage);
			//Log.info(logMessage); //Print on Log
		} catch(Exception e) {
			err1="Expand Failed - Node not present please check....." + nodeName.toString(); //Preparing Error message when unable to perform action
			System.out.println(err1 + e.getMessage()); // Print on Console // Print on Console
			//logging
			logger.error(err1 + e.getMessage());
			//Log.error(err1); //Print on Log
		}
		return err1;
	}

	/**
	 * Node - Keyword & collapse - Action
	 * @method collapse
	 * @description - collapse action to collapse node.
	 * 
	 * @throws Exception
	 */
	public String collapse(By nodeName) throws Exception {
		try{
			err1="No";
			driver.findElement(nodeName).click(); //Click on "-" icon.
			logMessage = "At test Step - Keyword = "+keyword+", Action = "+ "collapse"+" , Locator = " + nodeName.toString(); // This is to Display on console as well in Log File
			System.out.println(logMessage); // Print on Console
			//Log.info(logMessage); //Print on Log

		} catch(Exception e) {
			err1="Collapse Failed - Node not present please check....." + nodeName.toString(); //Preparing Error message when unable to perform action
			System.out.println(err1 + e.getMessage()); // Print on Console // Print on Console
			logger.error(err1 + e.getMessage());
			//Log.error(err1); //Print on Log
		}
		return err1;
	}
}
