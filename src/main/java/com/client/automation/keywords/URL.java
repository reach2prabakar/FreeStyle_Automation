package com.client.automation.keywords;


import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;


/**
 * URL - Keyword
 * @class URL
 * @description - URL related all Actions are mentioned in this class
 * 
 * @throws Exception
 */
public class URL{
	
	private static Logger logger = Logger.getLogger(URL.class.getName());
	public String logMessage = null;
	private String keyword = "URL";
	public String err1="No";
	public WebDriver driver;

	public URL(WebDriver driver){
		this.driver = driver;
	}
	
	/**
	 * URL - Keyword & get - Action
	 * @method get
	 * @description - get action to invoke URL of Application
	 * 
	 * @throws Exception
	 */
	public String get(String urlPath) throws Exception {
		try{
			err1="No";	
			driver.navigate().to(urlPath); //Open Browser 
			closeIfbrowserUnsupportedMessageAppears();
			logMessage = "At test step - Keyword = "+keyword+", Action = "+ "get"+", Fetching Application URL :  " + urlPath; // This is to Display on console as well in Log File
			System.out.println(logMessage); // Print on Console
			//logging
			logger.info(logMessage);
			////Log.info(logMessage); //Print on Log //Print on Log
		} catch(Exception e) {
			err1="Unable to Launch Application.. please check....." + urlPath; //Preparing Error message when unable to launch application
			System.out.println(err1); // Print on Console //Print on Console
			//logging
			logger.error(err1);
			//Log.error(err1); //Print on Log //Print on Log
		}
		return err1;
	}

	/**
	 * URL - Keyword & close - Action
	 * @method close
	 * @description - close action to close driver. This is mainly written for email functionality to cover as well as to close the Application manually throgh test script
	 * 
	 * @throws Exception
	 */
	public String close() throws Exception {
		try{
			err1="No";
			driver.close(); //Close Driver
			logMessage = "At test step - Keyword = "+keyword+", Action = "+ "close"+", Closing Application :  ";// This is to Display on console as well in Log File
			System.out.println(logMessage);// Print on Console
			//logging
			logger.info(logMessage);
			//Log.info(logMessage); //Print on Log //Print on Log
		} catch(Exception e) {
			err1="Unable to CLOSE Application.. please check.....";//Preparing Error message when unable to launch application
			System.out.println(err1); // Print on Console// Print on Console
			//logging
			logger.error(err1);
			//Log.error(err1); //Print on Log//Print on Log
		}
		return err1;
	}
	/**
	 * URL - Keyword & refresh - Action
	 * @method refresh
	 * @description - refresh action to refrsh driver. This is mainly written for timeline functionality to refresh page , bcoz some times timeline content is not loaded
	 * * @throws Exception
	 */
	public String refresh() throws Exception {
		try{
			err1="No";
			driver.navigate().refresh(); //refresh browser
			//driver.manage().timeouts().implicitlyWait(10,TimeUnit.SECONDS);
			logMessage = "At test step - Keyword = "+keyword+", Action = "+ "refresh"+", refreshing page  :  ";// This is to Display on console as well in Log File
			System.out.println(logMessage);// Print on Console
			//logging
			logger.info(logMessage);
			//Log.info(logMessage); //Print on Log //Print on Log
		} catch(Exception e) {
			err1="Unable to refresh browser.. please check.....";//Preparing Error message when unable to launch application
			System.out.println(err1); // Print on Console// Print on Console
			//logging
			logger.error(err1);
			//Log.error(err1); //Print on Log//Print on Log
		}
		return err1;
	}
	
	/**
	 * URL - Keyword & Navigate back - Action
	 * @method refresh
	 * @description - Navigate back action to go back . This is mainly written for AAA first time form functionality to go back page , 
	 * * @throws Exception
	 */
	public String navigateback() throws Exception {
		try{
			err1="No";
			driver.navigate().back(); //refresh browser
			//driver.manage().timeouts().implicitlyWait(10,TimeUnit.SECONDS);
			logMessage = "At test step - Keyword = "+keyword+", Action = "+ "navigateback"+", moving back one page  :  ";// This is to Display on console as well in Log File
			System.out.println(logMessage);// Print on Console
			//logging
			logger.info(logMessage);
			//Log.info(logMessage); //Print on Log //Print on Log
		} catch(Exception e) {
			err1="Unable to go back to previous page.. please check....."+Thread.currentThread().getStackTrace()[1].getLineNumber();//Preparing Error message when unable to launch application
			System.out.println(err1); // Print on Console// Print on Console
			//logging
			logger.error(err1);
			//Log.error(err1); //Print on Log//Print on Log
		}
		return err1;
	}

	/**
	 * URL - Keyword & Validate - Action
	 * @method get
	 * @description - get action to invoke URL of Application
	 * 
	 * @throws Exception
	 */
	public String validate(String urlToBeVerified,String waitTime) throws Exception {
		try{
			err1="No";
			String actualURL = driver.getCurrentUrl(); // Get Actual value from Application
			if (actualURL.equals(urlToBeVerified.trim())){ //Compare it to Expected Value
				logMessage = "At test Step - Keyword = "+keyword+", Action = "+ "validate"+" , Value from Application = " + actualURL + ", Expected Value = "+ urlToBeVerified;
				System.out.println(logMessage); // Print on Console
				//logging
				logger.info(logMessage);
				//Log.info(logMessage); //Print on Log
			}else {
				err1 = "At test Step - Keyword = Validation, Action = "+keyword+", Action = "+ "validate" +" , Value from Application = " + actualURL + ", Expected Value = "+ urlToBeVerified;
				System.out.println(err1); // Print on Console
				//logging
				logger.info(err1);
				//Log.info(err1);
				return err1;
			}
		} catch(Exception e) {
			err1= "Keyword : " + keyword + "Action : validate    Exception while validating Page title : "+ urlToBeVerified;
			System.out.println(err1); // Print on Console
			//Log.error(err1); //Print on Log
		}
		return err1;
	}

	private void closeIfbrowserUnsupportedMessageAppears() {
		try {
		WebElement element = driver.findElement(By.id("browserUnsupportedClose"));
		Thread.sleep(2000);
		System.out.println("Browser unsuppored message appeared. Closing the Messsage!!");
		//logging
		logger.info("Browser unsuppored message appeared. Closing the Messsage!!");
		element.click();
		} catch (Exception e){
			//System.out.println("browserUnsupportedClose link is not appearing");
			//System.out.println(e.getLocalizedMessage());
		}
		
	}
	
	
	/**
	 * URL - Keyword & hasText - Action
	 * @method get
	 * @description - get action to invoke URL of Application
	 * 
	 * @throws Exception
	 */
	public String hasText(String valueToBeVerified,String waitTime) throws Exception {
		try{
			err1="No";
			String actualURL = driver.getCurrentUrl(); // Get Actual value from Application
			if (actualURL.contains(valueToBeVerified.trim())){ //Compare it to Expected Value
				logMessage = "At test Step - Keyword = "+keyword+", Action = "+ "hasText"+" , Value from Application = " + actualURL + ", Expected Value = "+ valueToBeVerified;
				System.out.println(logMessage); // Print on Console
				//logging
				logger.info(logMessage);
				//Log.info(logMessage); //Print on Log
			}else {
				err1 = "At test Step - Keyword = Validation, Action = "+keyword+", Action = "+ "hasText" +" , Value from Application = " + actualURL + ", Expected Value = "+ valueToBeVerified;
				System.out.println(err1); // Print on Console
				//logging
				logger.info(err1);
				//Log.info(err1);
				return err1;
			}
		} catch(Exception e) {
			err1= "Keyword : " + keyword + "Action : validate    Exception while validating Page title : "+ valueToBeVerified;
			System.out.println(err1); // Print on Console
			//Log.error(err1); //Print on Log
		}
		return err1;
	}
}
