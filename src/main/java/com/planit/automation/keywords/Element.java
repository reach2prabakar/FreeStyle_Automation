package com.planit.automation.keywords;

import java.text.NumberFormat;
import java.util.Locale;

import org.apache.commons.jexl2.Expression;
import org.apache.commons.jexl2.JexlContext;
import org.apache.commons.jexl2.JexlEngine;
import org.apache.commons.jexl2.MapContext;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import com.planit.automation.library.DynamicVarRepository;



/**
 * Element - Keyword
 * @class Element
 * @description - Element related all Actions are mentioned in this class
 * 
 * @throws Exception
 */
public class Element{
	
	private static Logger logger = Logger.getLogger(Element.class.getName());
	public String logMessage = null;
	private String keyword = "Element";
	public String err1="No";
	public WebDriver driver;
	private String variable;

	public Element(WebDriver driver){
		this.driver = driver;
	}
	
	/**
	 * Element - Keyword & getText - Action
	 * @method getText
	 * @description - getText action to invoke URL of Application
	 * 
	 * @throws Exception
	 */
	public String getText(By elementName,String variableName) throws Exception {
		String testData = null;
		String[] inputData = variableName.split(",");
		
		
		try{
			err1="No";	
			testData = driver.findElement(elementName).getText().trim();
			logMessage = "At test step - Keyword = "+keyword+", Action = "+ "getText" +", Data Stored in -  " + variableName; // This is to Display on console as well in Log File
			System.out.println(logMessage); // Print on Console
			
			logger.info(logMessage);
			////Log.info(logMessage); //Print on Log //Print on Log
		} catch(Exception e) {
			err1="Unable to Update variable value... please check....." + variableName; //Preparing Error message when unable to launch application
			System.out.println(err1 + e.getMessage()); // Print on Console //Print on Console
			
			logger.error(err1+ e.getMessage());
			//Log.error(err1); //Print on Log //Print on Log
		}
		return testData;
	}

	/**
	 * Element - Keyword & getMoneyValue - Action
	 * @method getText
	 * @description - getText action to invoke URL of Application
	 * 
	 * @throws Exception
	 */
	public String getMoneyValue(By elementName,String variableName, DynamicVarRepository dynamicVariables) throws Exception {
		String testData = null;
		err1="No";	
		
		try{
			testData = driver.findElement(elementName).getText().replaceAll(" ","").replaceAll(",", "");
			
			NumberFormat format = NumberFormat.getCurrencyInstance(Locale.US);
			Number number = format.parse(testData);
			
			dynamicVariables.addObjectToMap(variableName, number.toString());															

			System.out.println("text : " + number.toString() + "  stored in dynamic variable : " + variableName);
			logger.info("text : " + number.toString() + "  stored in dynamic variable : " + variableName);
			

			logMessage = "At test step - Keyword = "+keyword+", Action = "+ "getMoneyValue" +", Data Stored in -  " + variableName; // This is to Display on console as well in Log File
			System.out.println(logMessage); // Print on Console
			
			logger.info(logMessage);
			////Log.info(logMessage); //Print on Log //Print on Log
		} catch(Exception e) {
			err1="Unable to Update variable value... please check....." + variableName; //Preparing Error message when unable to launch application
			System.out.println(err1 + e.getMessage()); // Print on Console //Print on Console
			
			logger.error(err1+ e.getMessage());
			//Log.error(err1); //Print on Log //Print on Log
		}
		return err1;
	}

	
	/**
	 * Element - Keyword & sendkeys - Action
	 * @method sendkeys
	 * @description - sendkeys action to invoke URL of Application
	 * 
	 * @throws Exception
	 */
	public String sendkeys(By elementName,String inputData) throws Exception {
		
		try{
			err1="No";	
			WebElement element = driver.findElement(elementName);
			element.sendKeys(inputData);
			logMessage = "At test step - Keyword = "+keyword+", Action = "+ "sendkeys" +", test data -  " + inputData; // This is to Display on console as well in Log File
			System.out.println(logMessage); // Print on Console
			
			logger.info(logMessage);
			////Log.info(logMessage); //Print on Log //Print on Log
		} catch(Exception e) {
			err1="Unable to get property value... please check..... Object : " + elementName +" testdata : "+ inputData; //Preparing Error message when unable to launch application
			System.out.println(err1 + e.getMessage()); // Print on Console //Print on Console
			
			logger.error(err1 + e.getMessage());
			//Log.error(err1); //Print on Log //Print on Log
		}
		return err1;
	}
	
	
	
	
	/**
	 * Element - Keyword & getValue - Action
	 * @method getText
	 * @description - getText action to invoke URL of Application
	 * 
	 * @throws Exception
	 */
	public String getProperty(By elementName,String inputData) throws Exception {
		String testData = null;
		String variable;
		String property;
		
		String[] inputParams = inputData.split("=");
		if(inputParams.length == 2){
			variable = inputParams[0];
			property = inputParams[1];
		} else {
			
			throw new Exception("Test data is not in exepcted format <variableName> = <property>  : " + inputData);
		}
		
		setVariable(variable);
		
		try{
			err1="No";	
			testData = driver.findElement(elementName).getAttribute(property).trim();
			logMessage = "At test step - Keyword = "+keyword+", Action = "+ "getProperty" +", test data -  " + inputData; // This is to Display on console as well in Log File
			System.out.println(logMessage); // Print on Console
			
			logger.info(logMessage);
			////Log.info(logMessage); //Print on Log //Print on Log
		} catch(Exception e) {
			err1="Unable to get property value... please check..... Object : " + elementName +" testdata : "+ inputData; //Preparing Error message when unable to launch application
			System.out.println(err1 + e.getMessage()); // Print on Console //Print on Console
			
			logger.error(err1 + e.getMessage());
			//Log.error(err1); //Print on Log //Print on Log
		}
		return testData;
	}
	
	/**
	 * Element - Keyword & getCaseID - Action
	 * @method getCaseID
	 * @description - getCaseID - Gets the case id from the buyer/seller login and strip the brackets.
	 * 
	 * @throws Exception
	 */
	public String getCaseId(String variableName) throws Exception {
		//String testData = null;
				String caseid=null;
		try{
			err1="No";	
			String url = driver.getCurrentUrl();
			String startKey = "Details/";
			String endKey = "?";
			caseid = url.substring(url.indexOf(startKey)+ startKey.length(), url.indexOf(endKey));
			/*
			testData = driver.findElement(elementName).getText().trim();
			testData = testData.replace("(", "");
			testData = testData.replace(")", "");
			testData = testData.trim();*/

			logMessage = "At test step - Keyword = "+keyword+", Action = "+ "getCaseID"+", Data Stored in -  " + variableName; // This is to Display on console as well in Log File
			System.out.println(logMessage); // Print on Console
			
			logger.info(logMessage);
			////Log.info(logMessage); //Print on Log //Print on Log
		} catch(Exception e) {
			err1="Unable to Update variable value... please check....." + variableName; //Preparing Error message when unable to launch application
			System.out.println(err1 + e.getMessage()); // Print on Console //Print on Console
			
			logger.error(err1 + e.getMessage());
			//Log.error(err1); /err1/Print on Log //Print on Log
		}
		return caseid;
	}
	/**
	 * Element - Keyword & mouseHover - Action
	 * @method getCaseID
	 * @description - getCaseID - Gets the case id from the buyer/seller login and strip the brackets.
	 * 
	 * @throws Exception
	 */
	public String mouseHover(By elementName) throws Exception {
		try{
			err1="No";	
			Actions action = new Actions(driver);
			WebElement elemnt = driver.findElement(elementName);
			action.moveToElement(elemnt).build().perform();;
			Thread.sleep(3000);


			logMessage = "At test step - Keyword = "+keyword+", Action = "+"mouseHover"; // This is to Display on console as well in Log File
			System.out.println(logMessage); // Print on Console
			
			logger.info(logMessage);
			////Log.info(logMessage); //Print on Log //Print on Log
		} catch(Exception e) {
			err1="Unable to Update variable value... please check....." ; //Preparing Error message when unable to launch application
			System.out.println(err1 + e.getMessage()); // Print on Console //Print on Console
			
			logger.error(err1 + e.getMessage());
			//Log.error(err1); //Print on Log //Print on Log
		}
		return err1;
	}

	/**
	 * Element - Keyword & isVisible - Action
	 * @method isVisible
	 * @description - check whether the given element is visible or not.
	 * 
	 * @throws Exception
	 */
	public String isVisible(By elementLocator,String expectedStatus) throws Exception {
		try{
			err1="No";
			WebElement element = driver.findElement(elementLocator);
			String actualStatus = Boolean.valueOf(element.isDisplayed()).toString();
			
			if ( actualStatus.equalsIgnoreCase(expectedStatus)){
				logMessage = "At test Step - Keyword = "+keyword+", Action = "+ "isVisible"+" , Value from Application = " +actualStatus + ", Expected Value = "+ expectedStatus +" , Locator : " + elementLocator.toString();
				System.out.println(logMessage); // Print on Console
				
				logger.info(logMessage);
				//Log.info(logMessage); //Print on Log
			}else {
				err1 = "At test Step - Keyword = Validation, Action = "+keyword+", Action = "+ "isVisible" +" , Value from Application = " +actualStatus + ", Expected Value = "+ expectedStatus +" , Locator : " + elementLocator.toString();
				System.out.println(err1); // Print on Console
				
				logger.info(err1);
				//Log.info(err1);
				return err1;
			}
		} catch(Exception e) {
			err1=keyword+" not present please check.....Exception occurred while working with Element" + elementLocator.toString(); //Preparing Error message when unable to perform action
			System.out.println(err1 + e.getMessage()); // Print on Console // Print on Console
			
			logger.error(err1 + e.getMessage());
			//Log.error(err1); //Print on Log
		}
		return err1;
	}

	
	/**
	 * Element - Keyword & isSelected - Action
	 * @method isVisible
	 * @description - check whether the given element is visible or not.
	 * 
	 * @throws Exception
	 */
	public String isSelected(By elementLocator,String expectedStatus) throws Exception {
		try{
			err1="No";
			WebElement element = driver.findElement(elementLocator);
			String actualStatus = Boolean.valueOf(element.isSelected()).toString();
			
			if ( actualStatus.equalsIgnoreCase(expectedStatus)){
				logMessage = "At test Step - Keyword = "+keyword+", Action = "+ "isSelected"+" , Value from Application = " +actualStatus + ", Expected Value = "+ expectedStatus +" , Locator : " + elementLocator.toString();
				System.out.println(logMessage); // Print on Console
				
				logger.info(logMessage);
				//Log.info(logMessage); //Print on Log
			}else {
				err1 = "At test Step - Keyword = Validation, Action = "+keyword+", Action = "+ "isSelected" +" , Value from Application = " +actualStatus + ", Expected Value = "+ expectedStatus +" , Locator : " + elementLocator.toString();
				System.out.println(err1); // Print on Console
				
				logger.info(err1);
				//Log.info(err1);
				return err1;
			}
		} catch(Exception e) {
			err1=keyword+" not present please check.....Exception occurred while working with Element" + elementLocator.toString(); //Preparing Error message when unable to perform action
			System.out.println(err1 + e.getMessage()); // Print on Console // Print on Console
			
			logger.error(err1 + e.getMessage());
			//Log.error(err1); //Print on Log
		}
		return err1;
	}

	/**
	 * Element - Keyword & isEnabled - Action
	 * @method isVisible
	 * @description - check whether the given element is visible or not.
	 * 
	 * @throws Exception
	 */
	public String isEnabled(By elementLocator,String expectedStatus) throws Exception {
		try{
			err1="No";
			WebElement element = driver.findElement(elementLocator);
			String actualStatus = Boolean.valueOf(element.isEnabled()).toString();
			
			if ( actualStatus.equalsIgnoreCase(expectedStatus)){
				logMessage = "At test Step - Keyword = "+keyword+", Action = "+ "isEnabled"+" , Value from Application = " +actualStatus + ", Expected Value = "+ expectedStatus +" , Locator : " + elementLocator.toString();
				System.out.println(logMessage); // Print on Console
				
				logger.info(logMessage);
				//Log.info(logMessage); //Print on Log
			}else {
				err1 = "At test Step - Keyword = Validation, Action = "+keyword+", Action = "+ "isEnabled" +" , Value from Application = " +actualStatus + ", Expected Value = "+ expectedStatus +" , Locator : " + elementLocator.toString();
				System.out.println(err1); // Print on Console
				
				logger.info(err1);
				//Log.info(err1);
				return err1;
			}
		} catch(Exception e) {
			err1=keyword+" not present please check.....Exception occurred while working with Element" + elementLocator.toString(); //Preparing Error message when unable to perform action
			System.out.println(err1 + e.getMessage()); // Print on Console // Print on Console
			
			logger.error(err1 + e.getMessage());
			//Log.error(err1); //Print on Log
		}
		return err1;
	}
	
	/**
	 * Element - Keyword & isExists - Action
	 * @method isVisible
	 * @description - check whether the given element is visible or not.
	 * 
	 * @throws Exception
	 */
	public String isExists(By elementLocator,String expectedStatus) throws Exception {
		try{
			err1="No";
			if (elementLocator != null) {
				if(expectedStatus.equalsIgnoreCase("TRUE")) {
					logMessage = "At test Step - Keyword = "+keyword+", Action = "+ "isExists"+" , Value from Application = " + "True " + ", Expected Value = "+ expectedStatus +" , Locator : " + elementLocator.toString();
					System.out.println(logMessage); // Print on Console
					
					logger.info(logMessage);
				} else {
					err1 = "At test Step - Keyword = Validation, Action = "+keyword+", Action = "+ "isExists" +" , Value from Application = " + "True" + ", Expected Value = "+ expectedStatus +" , Locator : " + elementLocator.toString();
					System.out.println(err1); // Print on Console
					
					logger.info(err1);
					//Log.info(err1);
					return err1;
					
				}

			} else {
				if(expectedStatus.equalsIgnoreCase("TRUE")) {
					err1 = "At test Step - Keyword = Validation, Action = "+keyword+", Action = "+ "isExists" +" , Value from Application = " + "False" + ", Expected Value = "+ expectedStatus +" , Locator : " + elementLocator.toString();
					System.out.println(err1); // Print on Console
					
					logger.info(err1);
					//Log.info(err1);
					return err1;
				} else {
					logMessage = "At test Step - Keyword = "+keyword+", Action = "+ "isExists"+" , Value from Application = " + "False " + ", Expected Value = "+ expectedStatus +" , Locator : " + elementLocator.toString();
					System.out.println(logMessage); // Print on Console
					
					logger.info(logMessage);
					
				}
				
			}
		} catch(Exception e) {
			err1=keyword +" -- " + "isExisits" + "Exception occurred while working with Element" + elementLocator.toString(); //Preparing Error message when unable to perform action
			System.out.println(err1 + e.getMessage()); // Print on Console // Print on Console
			
			logger.error(err1 + e.getMessage());
			//Log.error(err1); //Print on Log
		}
		return err1;
	}

	/**
	 * Element - Keyword & getAAACaseNumber - Action
	 * @method getAAACaseNumber
	 * @description - getAAACaseNumber action to invoke URL of Application
	 * 
	 * @throws Exception
	 */
	public String getAAACaseNumber(By elementName,String variableName) throws Exception {
		String testData = null;
		try{
			err1="No";	
			testData = driver.findElement(elementName).getText().trim();
			if(testData != null){
				testData = testData.substring(3);
			}
			logMessage = "At test step - Keyword = "+keyword+", Action = "+ "getAAACaseNumber" +", Data Stored in -  " + variableName; // This is to Display on console as well in Log File
			System.out.println(logMessage); // Print on Console
			System.out.println("Case number :" + testData + " is going to store in variable" + variableName);
			logger.info(logMessage);
			////Log.info(logMessage); //Print on Log //Print on Log
		} catch(Exception e) {
			err1="Unable to Update variable value... please check....." + variableName; //Preparing Error message when unable to launch application
			System.out.println(err1 + e.getMessage()); // Print on Console //Print on Console
			logger.error(err1 + e.getMessage());
	}
		return testData;
	}

	

	public String getVariable() {
		return variable;
	}

	public void setVariable(String variable) {
		this.variable = variable;
	}

	/**
	 * Element - Keyword & contextClick - Action
	 * @method getCaseID
	 * @description - getCaseID - Gets the case id from the buyer/seller login and strip the brackets.
	 * 
	 * @throws Exception
	 */
	public String contextClick(By elementName) throws Exception {
		try{
			err1="No";	
			WebElement elemnt = driver.findElement(elementName);
			Actions action = new Actions(driver);
			action.contextClick(elemnt).build().perform();
			Thread.sleep(3000);
			logMessage = "At test step - Keyword = "+keyword+", Action = "+"moveMouseCursor"; // This is to Display on console as well in Log File
			System.out.println(logMessage); // Print on Console
			
			logger.info(logMessage);
			////Log.info(logMessage); //Print on Log //Print on Log
		} catch(Exception e) {
			err1="Unable to Update variable value... please check....." ; //Preparing Error message when unable to launch application
			System.out.println(err1 + e.getMessage()); // Print on Console //Print on Console
			
			logger.error(err1 + e.getMessage());
			//Log.error(err1); //Print on Log //Print on Log
		}
		return err1;
	}

	
	/**
	 * Element - Keyword & doubleClick - Action
	 * @method doubleClick
	 * @description - doubleClick - double click on the element.
	 * 
	 * @throws Exception
	 */
	public String doubleClick(By elementName) throws Exception {
		try{
			err1="No";	
			WebElement elemnt = driver.findElement(elementName);
			Actions action = new Actions(driver);
			action.doubleClick(elemnt).build().perform();
			Thread.sleep(3000);
			logMessage = "At test step - Keyword = "+keyword+", Action = "+"moveMouseCursor"; // This is to Display on console as well in Log File
			System.out.println(logMessage); // Print on Console
			
			logger.info(logMessage);
			////Log.info(logMessage); //Print on Log //Print on Log
		} catch(Exception e) {
			err1="Unable to Update variable value... please check....." ; //Preparing Error message when unable to launch application
			System.out.println(err1 + e.getMessage()); // Print on Console //Print on Console
			
			logger.error(err1 + e.getMessage());
			//Log.error(err1); //Print on Log //Print on Log
		}
		return err1;
	}


}
