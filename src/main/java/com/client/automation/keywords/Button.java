package com.client.automation.keywords;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.client.automation.processor.WebElementProcessor;


/**
 * Button - Keyword
 * @class Button
 * @description - Button related all Actions are mentioned in this class
 * 
 * @throws Exception
 */
public class Button{
	
	private static Logger logger = Logger.getLogger(Button.class.getName());
	private String logMessage = null;
	private String keyword = "Button";
	private String err1="No";
	private WebDriver driver;
	

	public Button(WebDriver driver){
		this.driver = driver;
	}
	
	/**
	 * Button - Keyword & click - Action
	 * @method click
	 * @description - click action to Click on Button. 
	 * 
	 * @throws Exception
	 */
	public String click(By btnName) throws Exception {
		try{
			err1="No";
			driver.findElement(btnName).click(); //Click on Button
			new Wait(driver).waitForPageLoad();
			logMessage = "At test Step - Keyword = "+keyword+", Action = "+ "click" +" , Locator = " + btnName;// This is to Display on console as well in Log File
			System.out.println(logMessage); // Print on Console
			//logging
			logger.info(logMessage);
			//Log.info(logMessage); //Print on Log //Print on Log
		} catch(Exception e) {
			err1="Button not present please check..... Locator : " + btnName;//Preparing Error message when unable to perform action
			System.out.println(err1); // Print on Console
			//Log.error(err1); //Print on Log
			//logging
			logger.error(err1);
			
		}
		return err1;
	}

	

	/**
	 * Button - Keyword & jsclick - Action
	 * @method jsclick
	 * @description - jsclick action to Click on Button. 
	 * 
	 * @throws Exception
	 */
	public String jsclick(By btnName) throws Exception {
		try{
			err1="No";
			
			WebElement inputElement = driver.findElement(btnName); //Click on Button
			
			JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
			jsExecutor.executeScript("arguments[0].click();", inputElement);
			System.out.println("Ran Java script!!");
		
			new Wait(driver).waitForPageLoad();
			logMessage = "At test Step - Keyword = "+keyword+", Action = "+ "jsclick" +" , Locator = " + btnName;// This is to Display on console as well in Log File
			System.out.println(logMessage); // Print on Console
			//logging
			logger.info(logMessage);
			//Log.info(logMessage); //Print on Log //Print on Log
		} catch(Exception e) {
			err1="Button not present please check..... Locator : " + btnName;//Preparing Error message when unable to perform action
			System.out.println(err1); // Print on Console
			//Log.error(err1); //Print on Log
			//logging
			logger.error(err1);
			
		}
		return err1;
	}

	
	/**
	 * Button - Keyword & validate - Action
	 * @method validate
	 * @description - validate action to Validate whether Button is present or not. 
	 * 
	 * @throws Exception
	 */
	public String validate(By btnName) throws Exception {
		try{
			err1="No";
			//Below If condition to check availability of Button
			if (new WebElementProcessor(driver).isPresent(driver, btnName) == true) {
				logMessage = "At test Step - Keyword = "+keyword+", Action = "+ "validate" +" , Locator = " + btnName;// This is to Display on console as well in Log File
				System.out.println(logMessage); // Print on Console
				//logging
				logger.info(logMessage);
				//Log.info(logMessage); //Print on Log //Print on Log
			} else {
				logMessage="Validate of Button Failed - Button not present please check..... Locator : " + btnName;//Preparing Error message when unable to perform action
				System.out.println(logMessage); // Print on Console
				//logging
				logger.info(logMessage);
				//Log.error(logMessage);
			}
		} catch(Exception e) {
			err1="Validate of Button Failed - Button not present please check..... Locator : " + btnName;//Preparing Error message when unable to perform action
			System.out.println(err1); // Print on Console
			//logging
			logger.info(logMessage);
			//Log.error(err1); //Print on Log
		}
		return err1;
	}

}
