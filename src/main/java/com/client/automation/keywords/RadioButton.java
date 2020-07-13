package com.client.automation.keywords;

import java.util.List;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import com.client.automation.processor.WebElementProcessor;

/**
 * RadioButton - Keyword
 * @class RadioButton
 * @description - RadioButton related all Actions are mentioned in this class
 * 
 * @throws Exception
 */
public class RadioButton{
	
	private static Logger logger = Logger.getLogger(RadioButton.class.getName());
	public String logMessage = null;
	private String keyword = "RadioButton";
	public String err1="No";
	public WebDriver driver;

	public RadioButton(WebDriver driver){
		this.driver = driver;
	}
	
	/**
	 * RadioButton - Keyword & click - Action
	 * @method click
	 * @description - click action to select specific Radio Button.
	 * 
	 * @throws Exception
	 */
	public String click(By radioName) throws Exception {
		try{
			err1="No";
			WebElement element = driver.findElement(radioName);
			boolean isPresent = new WebElementProcessor(driver).isPresent(driver, By.className("jspDrag"));
			if ( isPresent == true && !element.isDisplayed()){
				scrollUserActionScroller();
			}

			element.click();
			// below is a special case where the radio button is half visible
			if(!element.isSelected()){ 
				if (isPresent == true){
					scrollUserActionScroller();
					if(element.isDisplayed()){
						element.click();

					} else {
						System.out.println("Warning : Element not visible after scrolling. ");
					}
				}	
			}
			//driver.findElement(radioName).click();
			logMessage = "At test Step - Keyword = "+keyword+", Action = "+ "click"+" , Locator = " +  radioName.toString();// This is to Display on console as well in Log File
			System.out.println(logMessage); // Print on Console
			//logging
			logger.info(logMessage);
			//Log.info(logMessage); //Print on Log
		} catch(Exception e) {
			err1=keyword+" not present please check....." + radioName.toString(); //Preparing Error message when unable to perform action
			System.out.println(err1); // Print on Console // Print on Console
			//logging
			logger.error(err1);
			//Log.error(err1); //Print on Log
		}
		return err1;
	}

	/**
	 * RadioButton - Keyword & byValue - Action
	 * @method byValue
	 * @description - byValue action to select specific Radio Button.
	 * 
	 * @throws Exception
	 */
	public String byValue(By radioName,String radioValue) throws Exception {
		try{
			err1="No";
			List oRadioButton = driver.findElements(radioName);
			// This will tell you the number of checkboxes are present
			int iSize = oRadioButton.size();
			// Start the loop from first Radio Button to last Radio Button
			for(int i=0; i < iSize ; i++ ){
				// Store the checkbox name to the string variable, using 'Value' attribute
				String sValue = ((WebElement) oRadioButton.get(i)).getAttribute("value");
				// Select the checkbox it the value of the checkbox is same what you are looking for
				if (sValue.equalsIgnoreCase(radioValue)){
					((WebElement) oRadioButton.get(i)).click();
					// This will take the execution out of for loop
					break;
				}
			}
			logMessage = "At test Step - Keyword = "+keyword+", Action = "+ "click"+" , Locator = " + radioValue.toString(); // This is to Display on console as well in Log File
			System.out.println(logMessage); // Print on Console
			//logging
			logger.info(logMessage);
			//Log.info(logMessage); //Print on Log
		} catch(Exception e) {
			err1=keyword+" not present please check....." + radioValue.toString(); //Preparing Error message when unable to perform action
			System.out.println(err1); // Print on Console // Print on Console
			//Log.error(err1); //Print on Log
			//logging
			logger.error(err1);
		}
		return err1;
	}


	/**
	 * RadioButton - Keyword & byLabel - Action
	 * @method byValue
	 * @description - byValue action to select specific Radio Button.
	 * 
	 * @throws Exception
	 */
	public String byLabel(By radioName,String radioLabel) throws Exception {
		try{
			err1="No";
			driver.findElement(By.xpath("//label[contains(.,'"+radioLabel+"')]")).click();
			logMessage = "At test Step - Keyword = "+keyword+", Action = "+ "byLabel"+" , Locator = " + radioLabel.toString(); // This is to Display on console as well in Log File
			System.out.println(logMessage); // Print on Console
			//Log.info(logMessage); //Print on Log
			//logging
			logger.info(logMessage);
		} catch(Exception e) {
			err1=keyword+" not present please check....." + radioLabel.toString(); //Preparing Error message when unable to perform action
			System.out.println(err1); // Print on Console // Print on Console
			//logging
			logger.error(err1);
			//Log.error(err1); //Print on Log
		}
		return err1;
	}
	
	/**
	 * RadioButton - Keyword & isSelected - Action
	 * @method click
	 * @description - click action to select specific Radio Button.
	 * 
	 * @throws Exception
	 */
	public String isSelected(By radioName,String expectedStatus) throws Exception {
		try{
			err1="No";
			WebElement element = driver.findElement(radioName);
			String actualStatus = Boolean.valueOf(element.isSelected()).toString();
			
			if ( actualStatus.equalsIgnoreCase(expectedStatus)){
				logMessage = "At test Step - Keyword = "+keyword+", Action = "+ "isSelected"+" , Value from Application = " +actualStatus + ", Expected Value = "+ expectedStatus +" , Locator : " + radioName.toString();
				System.out.println(logMessage); // Print on Console
				//logging
				logger.info(logMessage);
				//Log.info(logMessage); //Print on Log
			}else {
				err1 = "At test Step - Keyword = Validation, Action = "+keyword+", Action = "+ "isSelected" +" , Value from Application = " +actualStatus + ", Expected Value = "+ expectedStatus +" , Locator : " + radioName.toString();
				System.out.println(err1); // Print on Console
				//logging
				logger.info(err1);
				//Log.info(err1);
				return err1;
			}
		} catch(Exception e) {
			err1=keyword+" not present please check.....Exception occurred while working with Radio button" + radioName.toString(); //Preparing Error message when unable to perform action
			System.out.println(err1); // Print on Console // Print on Console
			//logging
			logger.error(err1);
			//Log.error(err1); //Print on Log
		}
		return err1;
	}

	
	private void scrollUserActionScroller(){
		
		WebElement scroller = driver.findElement(By.className("jspDrag"));
		Actions action = new Actions(driver);
		action.clickAndHold(scroller).build().perform();
		action.moveByOffset(0, 27).build().perform();
		action.release(scroller).build().perform();
	}


}
