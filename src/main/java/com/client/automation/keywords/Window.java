package com.client.automation.keywords;

import org.apache.log4j.Logger;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;


/**
 * Button - Keyword
 * @class Button
 * @description - Button related all Actions are mentioned in this class
 * 
 * @throws Exception
 */
public class Window{
	
	private static Logger logger = Logger.getLogger(Window.class.getName());
	private String logMessage = null;
	private String keyword = "Window";
	private String err1="No";
	private WebDriver driver;
	private String currentWindowHandle;
	

	public Window(WebDriver driver){
		this.driver = driver;
	}
	
	/**
	 * Window - Keyword & getWindowHandle - Action
	 * @method getWindowHandle
	 * @description - get the window handle for the current window. 
	 * 
	 * @throws Exception
	 */
	public String getWindowHandle() throws Exception {
		try{
			err1="No";
			setCurrentWindowHandle(driver.getWindowHandle());
			new Wait(driver).waitForPageLoad();
			logMessage = "At test Step - Keyword = "+keyword+", Action = "+ "getWindowHandle" ;// This is to Display on console as well in Log File
			System.out.println(logMessage); // Print on Console
			//logging
			logger.info(logMessage);
			//Log.info(logMessage); //Print on Log //Print on Log
		} catch(Exception e) {
			err1="ERROR: Exception occurred while getting the window handle \n" + e.getMessage();//Preparing Error message when unable to perform action
			System.out.println(err1); // Print on Console
			//Log.error(err1); //Print on Log
			//logging
			logger.error(err1);
			
		}
		return err1;
	}


	/**
	 * Window - Keyword & switchToWindow - Action
	 * @method switchToWindow
	 * @description - switch to window for which handle is provided. 
	 * 
	 * @throws Exception
	 */
	public String switchToWindow(String testData) throws Exception {
		try{
			err1="No";
			driver.switchTo().window(testData);
			new Wait(driver).waitForPageLoad();
			logMessage = "At test Step - Keyword = "+keyword+", Action = "+ "switchToWindow" ;// This is to Display on console as well in Log File
			System.out.println(logMessage); // Print on Console
			//logging
			logger.info(logMessage);
			//Log.info(logMessage); //Print on Log //Print on Log
		} catch(Exception e) {
			err1="ERROR: Exception occurred while switching to the window : " +  testData + "\n" + e.getMessage();//Preparing Error message when unable to perform action
			System.out.println(err1); // Print on Console
			//Log.error(err1); //Print on Log
			//logging
			logger.error(err1);
			
		}
		return err1;
	}

	
	/**
	 * Window - Keyword & acceptAlert - Action
	 * @method acceptAlert
	 * @description - switch to acceptAlert for which handle is provided. 
	 * 
	 * @throws Exception
	 */
	public String acceptAlert() throws Exception {
		try{
			err1="No";
			Alert alert = driver.switchTo().alert();
			alert.accept();
			new Wait(driver).waitForPageLoad();
			logMessage = "At test Step - Keyword = "+keyword+", Action = "+ "switchToWindow" ;// This is to Display on console as well in Log File
			System.out.println(logMessage); // Print on Console
			//logging
			logger.info(logMessage);
			//Log.info(logMessage); //Print on Log //Print on Log
		} catch(Exception e) {
			err1="ERROR: Exception occurred while switching to the alert  : No alert pop up is present" +   e.getMessage();//Preparing Error message when unable to perform action
			System.out.println(err1); // Print on Console
			//Log.error(err1); //Print on Log
			//logging
			logger.error(err1);
			
		}
		return err1;
	}
	
	
	/**
	 * Window - Keyword & acceptAlert - Action
	 * @method acceptAlert
	 * @description - switch to acceptAlert for which handle is provided. 
	 * 
	 * @throws Exception
	 */
	public String dismissAlert() throws Exception {
		try{
			err1="No";
			Alert alert = driver.switchTo().alert();
			alert.dismiss();
			new Wait(driver).waitForPageLoad();
			logMessage = "At test Step - Keyword = "+keyword+", Action = "+ "dismissAlert" ;// This is to Display on console as well in Log File
			System.out.println(logMessage); // Print on Console
			//logging
			logger.info(logMessage);
			//Log.info(logMessage); //Print on Log //Print on Log
		} catch(Exception e) {
			err1="ERROR: Exception occurred while switching to the alert  : No alert pop up is present" +   e.getMessage();//Preparing Error message when unable to perform action
			System.out.println(err1); // Print on Console
			//Log.error(err1); //Print on Log
			//logging
			logger.error(err1);
			
		}
		return err1;
	}
	
	/**
	 * Window - Keyword & switchToIframe - Action
	 * @method switchToIframe
	 * @description - switch to frame for which handle is provided. 
	 * 
	 * @throws Exception
	 */
	public String switchToIframe(String testData) throws Exception {
		try{
			err1="No";
			//WebElement frameElement = driver.findElement(objproperty);
			driver.switchTo().defaultContent();
			driver.switchTo().frame(testData);
			//new Wait(driver).waitForPageLoad();
			logMessage = "At test Step - Keyword = "+keyword+", Action = "+ "switchToIframe" ;// This is to Display on console as well in Log File
			System.out.println(logMessage); // Print on Console
			//logging
			logger.info(logMessage);
			//Log.info(logMessage); //Print on Log //Print on Log
		} catch(Exception e) {
			err1="ERROR: Exception occurred while switching to the Iframe : " +  testData + "\n" + e.getMessage();//Preparing Error message when unable to perform action
			System.out.println(err1); // Print on Console
			//Log.error(err1); //Print on Log
			//logging
			logger.error(err1);
			
		}
		return err1;
	}
	
	
	/**
	 * Window - Keyword & switchToWindow - Action
	 * @method switchToWindow
	 * @description - switch to window for which handle is provided. 
	 * 
	 * @throws Exception
	 */
	public String switchTodefault(By objproperty) throws Exception {
		try{
			err1="No";
			driver.switchTo().defaultContent();
			new Wait(driver).waitForPageLoad();
			logMessage = "At test Step - Keyword = "+keyword+", Action = "+ "switchTodefault" ;// This is to Display on console as well in Log File
			System.out.println(logMessage); // Print on Console
			//logging
			logger.info(logMessage);
			//Log.info(logMessage); //Print on Log //Print on Log
		} catch(Exception e) {
			err1="ERROR: Exception occurred while switching to the switchTodefault : " +  objproperty.toString() + "\n" + e.getMessage();//Preparing Error message when unable to perform action
			System.out.println(err1); // Print on Console
			//Log.error(err1); //Print on Log
			//logging
			logger.error(err1);
			
		}
		return err1;
	}
	
	/**
	 * Window - Keyword & switchToNewWindow - Action
	 * @method switchToNewWindow
	 * @description - switch to new window that has been opened by previous step
	 *  
	 * @throws Exception
	 */
	
	public String switchToNewWindow() throws Exception {
		try{
			err1="No";
			for(String winHandle : driver.getWindowHandles()){
			    driver.switchTo().window(winHandle);
			}
			
			new Wait(driver).waitForPageLoad();
			
			logMessage = "At test Step - Keyword = "+keyword+", Action = "+ "switchToWindow" ;// This is to Display on console as well in Log File
			System.out.println(logMessage); // Print on Console
			//logging
			logger.info(logMessage);
			//Log.info(logMessage); //Print on Log //Print on Log
		} catch(Exception e) {
			err1="ERROR: Exception occurred while switching to the new window : \n" + e.getMessage();//Preparing Error message when unable to perform action
			System.out.println(err1); // Print on Console
			//Log.error(err1); //Print on Log
			//logging
			logger.error(err1);
			
		}
		return err1;
	}
	
	
	/**
	 * Window - Keyword & scroll - Action
	 * @method scroll
	 * @description - switch to new window that has been opened by previous step
	 *  
	 * @throws Exception
	 */
	
	public String scroll(String testdata) throws Exception {
		try{
			err1="No";
			
			JavascriptExecutor jse = (JavascriptExecutor)driver;
				jse.executeScript("window.scrollBy(0,"+testdata+")", "");
			
		//	new Wait(driver).waitForPageLoad();
			
			logMessage = "At test Step - Keyword = "+keyword+", Action = "+ "switchToWindow" ;// This is to Display on console as well in Log File
			System.out.println(logMessage); // Print on Console
			//logging
			logger.info(logMessage);
			//Log.info(logMessage); //Print on Log //Print on Log
		} catch(Exception e) {
			err1="ERROR: Exception occurred while switching to the new window : \n" + e.getMessage();//Preparing Error message when unable to perform action
			System.out.println(err1); // Print on Console
			//Log.error(err1); //Print on Log
			//logging
			logger.error(err1);
			
		}
		return err1;
	}
	
	
	
	public String getCurrentWindowHandle() {
		return currentWindowHandle;
	}

	public void setCurrentWindowHandle(String currentWindowHandle) {
		this.currentWindowHandle = currentWindowHandle;
	}

}
