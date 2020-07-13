package com.client.automation.keywords;

import java.util.List;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import com.client.automation.processor.WebElementProcessor;


/**
 * Link - Keyword
 * @class Link
 * @description - Link related all Actions are mentioned in this class
 * 
 * @throws Exception
 */

public class Link{
	
	private static Logger logger = Logger.getLogger(Link.class.getName());
	public String logMessage = null;
	private String keyword = "Link";
	public String err1="No";
	public WebDriver driver;

	public Link(WebDriver driver){
		this.driver = driver;
	}
	
	/**
	 * Link - Keyword & click - Action
	 * @method click
	 * @description - click action to Click on Link by Link-Name.
	 * 
	 * @throws Exception
	 */
	public String click(By linkName) throws Exception {
		try{
			err1="No";
		//	new Wait(driver).waitForPageLoad();
			driver.findElement(linkName).click(); //Click on Link using Link Name
			new Wait(driver).waitForPageLoad();
			logMessage = "At test Step - Keyword = "+keyword+", Action = "+ "click" +" , Locator = " + linkName.toString();// This is to Display on console as well in Log File
			System.out.println(logMessage); // Print on Console
			//logging
			logger.info(logMessage);
			//Log.info(logMessage); //Print on Log
		} catch(Exception e) {
			err1="Link not present please check....." + linkName.toString(); //Preparing Error message when unable to perform action
			System.out.println(err1); // Print on Console 
			//logging
			logger.error(err1);
			//Log.error(err1); //Print on Log
		}
		return err1;
	}

	/**
	 * Link - Keyword & validate - Action
	 * @method validate
	 * @description - validate action to Validate whether link present or not.
	 * 
	 * @throws Exception
	 */
	public String validate(By linkName) throws Exception {
		try{
			err1="No";
			//Below if condition to check whether Link Present or Not
			if (new WebElementProcessor(driver).isPresent(driver, linkName) == true) {
				logMessage = "At test Step - Keyword = "+keyword+", Action = "+ "validate"+" , Locator = " + linkName.toString();// This is to Display on console as well in Log File
				System.out.println(logMessage); // Print on Console
				//logging
				logger.info(logMessage);
				//Log.info(logMessage); //Print on Log
			} else {
				logMessage="Validation Failed - Link not present please check..... Locator : " + linkName; //Preparing Error message when unable to perform action
				System.out.println(logMessage); // Print on Console
				//logging
				logger.info(logMessage);
				//Log.error(logMessage); //Print on Log
			}
		} catch(Exception e) {
			err1="Validation Failed - Link not present please check..... Locator : " + linkName; //Preparing Error message when unable to perform action
			System.out.println(err1); // Print on Console // Print on Console
			//logging
			logger.error(err1);
			//Log.error(err1); //Print on Log
		}
		return err1;
	}

	/**
	 * Link - Keyword & GetActions - Action
	 * @method getActions
	 * @description - getActions action to navigate to specified Actions in Action list.
	 * 
	 * @throws Exception
	 */
	public String getActions(By actionListName,String actionName) throws Exception {
		try{
			err1="No";
			Thread.sleep(1000);
			boolean viewmore =false;
			try{
				viewmore = driver.findElement(By.xpath("//*[@class='viewMoreMenus' and not(@style='display:none;')]")).isDisplayed();
			}catch(Exception e){
				System.out.println("View more option is not present");
			}
			if(viewmore){
				clickonviewmore();	
			}
			List<WebElement> actionsList = null;
			/*try{
				actionsList = driver.findElements(actionListName);	
			}catch(Exception e){
				
			}
			//Actions action = new Actions(driver);
			// This will tell you the number of List of User Actions are present
			int iSize = actionsList.size();
			if(iSize<=1){*/
			if(viewmore){
				actionsList = driver.findElements(By.cssSelector(".scroll-ul-menu.menuScrollList li"));	
			}else{
				actionsList = driver.findElements(By.cssSelector(".scroll-ul-menu li"));	
			}
			//}
				int iSize = actionsList.size();
			// Start the loop from first to last
				boolean actionlistmatch = false;
				for(int i=0; i < iSize ; i++ ){
					// Store Actions name to the string variable, using getText
					String sValue = actionsList.get(i).getText();
					if (sValue.equalsIgnoreCase(actionName)){
						actionlistmatch = true;
					}
				}
				System.out.println(actionName);
			for(int i=0; i < iSize ; i++ ){
				String sValue ;
				// Store Actions name to the string variable, using getText
				if(!actionlistmatch){
					sValue = actionsList.get(i).findElement(By.tagName("a")).getAttribute("title");
				}else{
					sValue = actionsList.get(i).getText();
				}
				System.out.println(sValue);
				if (sValue.equalsIgnoreCase(actionName)){
					Thread.sleep(1000);
					WebElement element = actionsList.get(i).findElement(By.tagName("a"));
					/*JavascriptExecutor js=(JavascriptExecutor) driver;
					boolean scriptExecutedSuccessfully = (boolean)js.executeScript("var elmnt = arguments[0]; "
								+ "if (typeof elmnt.onclick == 'function') {"
								+ "elmnt.onclick.apply();"
								+ "return true;}"
								+ "return false;"
								, element);*/
					/*if (!scriptExecutedSuccessfully){
						err1="JavaScript not executed successfully please check....." + actionListName.toString(); //Preparing Error message when unable to perform action
						
					}*/
					element.click();
					Thread.sleep(5000);
					new Wait(driver).waitForPageLoad();
					break;
				}
			}
			logMessage = "At test Step - Keyword = "+keyword+", Action = "+ "getActions"+" , Locator = " + actionListName.toString();// This is to Display on console as well in Log File
			System.out.println(logMessage); // Print on Console
			//logging
			logger.info(logMessage);
			//Log.info(logMessage); //Print on Log
		} catch(Exception e) {
			e.printStackTrace();
			err1="Link not present please check....." + actionListName.toString(); //Preparing Error message when unable to perform action
			System.out.println(err1); // Print on Console 
			//logging
			logger.error(err1);
			//Log.error(err1); //Print on Log
		}
		return err1;
	}
	
	public void clickonviewmore(){
		driver.findElement(By.xpath("//*[@class='viewMoreMenus' and not(@style='display:none;')]")).click();
	}
}
 