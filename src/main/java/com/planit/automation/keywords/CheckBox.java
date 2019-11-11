package com.planit.automation.keywords;

import java.util.List;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import com.planit.automation.processor.WebElementProcessor;


/**
 * CheckBox - Keyword
 * @class CheckBox
 * @description - CheckBox related all Actions are mentioned in this class
 * 
 * @throws Exception
 */

public class CheckBox{
	
	private static Logger logger = Logger.getLogger(CheckBox.class.getName());
	private String logMessage = null;
	private String keyword = "CheckBox";
	private String err1="No";
	private WebDriver driver;
	
	public CheckBox(WebDriver driver){
		this.driver = driver;
	}
	
	/**
	 * URL - Keyword & click - Action
	 * @method click
	 * @description - click action to select Check Box
	 * 
	 * @throws Exception
	 */
	public String click(By checkBoxName) throws Exception {
		try{
			err1="No";
			WebElement element = driver.findElement(checkBoxName);
			boolean isPresent = new WebElementProcessor(driver).isPresent(driver, By.className("jspDrag"));
			if ( isPresent == true && !element.isDisplayed()){
				scrollUserActionScroller();
			}
			element.click();
			logMessage = "At test Step - Keyword = "+keyword+", Action = "+ "click" +" , Locator = " + checkBoxName.toString(); // This is to Display on console as well in Log File
			System.out.println(logMessage); // Print on Console
			logger.info(logMessage);
		} catch(Exception e) {
			err1=keyword+"  not present please check....." + checkBoxName.toString();//Preparing Error message when unable to perform action
			System.out.println(err1 + e.getMessage()); // Print on Console // Print on Console
			logger.error(err1 + e.getMessage());
		}
		return err1;
	}
	
	
	/**
	  * URL - Keyword & check - Action
	  * @method check
	  * @description - check action to select Check Box
	  * 
	  * @throws Exception
	  */
	 public String isSelected(By checkBoxName) throws Exception {
	  try{
	   err1="No";
	   List<WebElement> checkBoxList = driver.findElements(checkBoxName); //Make list of all Check boxes
	   System.out.println(checkBoxList.size());
	   boolean checkBoxValue = checkBoxList.get(0).isSelected(); // Flag to check the values
	   System.out.println("boolan :"+checkBoxValue);
	   
	  } catch(Exception e) {
	   err1=keyword+" not present please check....." + checkBoxName.toString(); //Preparing Error message when unable to perform action
	   System.out.println(err1 + e.getMessage()); // Print on Console // Print on Console
	   //logging
	   logger.error(err1 + e.getMessage());
	   //Log.error(err1); //Print on Log
	  }
	  return err1;
	 }

	/**
	 * URL - Keyword & check - Action
	 * @method check
	 * @description - check action to select Check Box
	 * 
	 * @throws Exception
	 */
	public String check(By checkBoxName) throws Exception {
		try{
			err1="No";
			List<WebElement> checkBoxList = driver.findElements(checkBoxName); //Make list of all Check boxes
			System.out.println(checkBoxList.size());
			boolean checkBoxValue = checkBoxList.get(0).isSelected(); // Flag to check the values
			System.out.println("boolan :"+checkBoxValue);
			if (checkBoxValue!=true){
				JavascriptExecutor js = (JavascriptExecutor) driver;
				js.executeScript("arguments[0].scrollIntoView(true);", driver.findElement(checkBoxName));
				driver.findElement(checkBoxName).click(); // Select matched one
				checkBoxValue = checkBoxList.get(0).isSelected(); //Get value of Matched check box
				System.out.println("boolanafterselected :"+checkBoxValue);
			}
			if (checkBoxValue==true){
				logMessage = "At test Step - Keyword = "+keyword+", Action = "+ "check" +" , Locator = " + checkBoxName.toString();// This is to Display on console as well in Log File
				System.out.println(logMessage); // Print on Console
				//logging
				logger.info(logMessage);
				//Log.info(logMessage); //Print on Log
			}else{
				err1 = keyword+" is not Checked .... Please check"; //Preparing Error message when unable to perform action
			}
		} catch(Exception e) {
			err1=keyword+" not present please check....." + checkBoxName.toString(); //Preparing Error message when unable to perform action
			System.out.println(err1 + e.getMessage()); // Print on Console // Print on Console
			//logging
			logger.error(err1 + e.getMessage());
			//Log.error(err1); //Print on Log
		}
		return err1;
	}

	/**
	 * URL - Keyword & unCheck - Action
	 * @method unCheck
	 * @description - unCheck action to unselect Check Box
	 * 
	 * @throws Exception
	 */
	public String unCheck(By checkBoxName) throws Exception {
		try{
			err1="No";

			List<WebElement> checkBoxList = driver.findElements(checkBoxName); // Get list of all checkboxes
			boolean checkBoxValue = checkBoxList.get(0).isSelected(); 
			boolean isPresent = new WebElementProcessor(driver).isPresent(driver, By.className("jspDrag"));
			if ( isPresent == true ){
				scrollUserActionScroller();
			}
			
			if (checkBoxValue==true){
				driver.findElement(checkBoxName).click();
				checkBoxValue = checkBoxList.get(0).isSelected();
			}
			if (checkBoxValue!=true){
				logMessage = "At test Step - Keyword = "+keyword+", Action = "+ "unCheck" +" , Locator = " + checkBoxName.toString(); // This is to Display on console as well in Log File
				System.out.println(logMessage); // Print on Console
				logger.info(logMessage);
			}else{
				err1 = keyword+" is Checked .... Please check"; //Preparing Error message when unable to perform action
			}
		} catch(Exception e) {
			err1=keyword+" not present please check....." + checkBoxName.toString(); //Preparing Error message when unable to perform action
			System.out.println(err1 + e.getMessage()); // Print on Console // Print on Console
			logger.error(err1 + e.getMessage());
	}
		return err1;
	}
	
	private void scrollUserActionScroller(){
		
		WebElement scroller = driver.findElement(By.className("jspDrag"));
		Actions action = new Actions(driver);
		action.clickAndHold(scroller).build().perform();
		action.moveByOffset(0, 50).build().perform();
		action.release(scroller).build().perform();
	}

}
