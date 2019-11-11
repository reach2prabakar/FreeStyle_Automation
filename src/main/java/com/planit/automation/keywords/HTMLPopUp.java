package com.planit.automation.keywords;


import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;

/**
 * HTMLPopUp - Keyword
 * @class HTMLPopUp
 * @description - HTMLPopUp related all Actions are mentioned in this class
 * 
 * @throws Exception
 */	
public class HTMLPopUp{
	
	private static Logger logger = Logger.getLogger(HTMLPopUp.class.getName());
	public String logMessage = null;
	private String keyword = "HTMLPopUp";
	public String err1="No";
	public WebDriver driver;

	public HTMLPopUp(WebDriver driver){
		this.driver = driver;
	}
	
	/**
	 * HTMLPopUp - Keyword & close - Action
	 * @method close
	 * @description - close action to close driver.
	 * 
	 * @throws Exception
	 */
	public String switchWindow() throws Exception {
		try{
			err1="No";
			driver.switchTo().activeElement();
			logMessage = "At test Step - Keyword = " + keyword + ", Action = "+ "switchWindow"; // This is to Display on console as well in Log File
			System.out.println(logMessage); // Print on Console
			//logging
			logger.info(logMessage);
			//Log.info(logMessage); //Print on Log
		} catch(Exception e) {
			err1="******** Error occoured in test step *********: Keyword = "+keyword+", Action = "+"switchWindow"; //Preparing Error message when unable to perform action
			System.out.println(err1 + e.getMessage()); // Print on Console
			//logging
			logger.error(err1 + e.getMessage());
			//Log.error(err1); //Print on Log
		}
		return err1; // Return Message
	}
}
