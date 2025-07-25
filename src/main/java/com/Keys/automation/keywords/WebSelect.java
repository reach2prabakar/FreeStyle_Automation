package com.planit.automation.keywords;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.Select;

/**
 * WebSelect - Keyword
 * @class WebSelect
 * @description - WebSelect related all Actions are mentioned in this class
 * 
 * @throws Exception
 */
public class WebSelect{
	
	private static Logger logger = Logger.getLogger(WebSelect.class.getName());
	public String logMessage = null;
	private String keyword = "WebSelect";
	public String err1="No";
	public WebDriver driver;

	public WebSelect(WebDriver driver){
		this.driver = driver;
	}
	
	/**
	 * WebSelect - Keyword & selectByVisibleText - Action
	 * @method selectByVisibleText
	 * @description - selectByVisibleText action to Select values in List Box based on Visible values
	 * 
	 * @throws Exception
	 */
	public String selectByVisibleText(By webSelectName,String textToSelect) throws Exception {
		try{
			err1="No";
			Select dropdown = new Select(driver.findElement(webSelectName)); 
			dropdown.selectByVisibleText(textToSelect);
			logMessage = "At test Step - Keyword = "+keyword+", Action = "+ "selectByVisibleText" + " - "+ textToSelect +" . Locator : " + webSelectName.toString();
			System.out.println(logMessage); // Print on Console
			//logging
			logger.info(logMessage);
			//Log.info(logMessage); //Print on Log
		} catch(Exception e) {
			err1="******** Error occoured in test step *********: "+keyword+", Action = "+ "selectByVisibleText" + " - "+  textToSelect + " - not present please check..... Locator : " + webSelectName.toString();
			System.out.println(err1); // Print on Console
			//logging
			logger.error(err1);
			//Log.error(err1); //Print on Log
		}
		return err1; //Return Message
	}

	/**
	 * WebSelect - Keyword & validate - Action
	 * @method validate
	 * @description - validate action to Validate the Selected values with Expected one..
	 * 
	 * @throws Exception
	 */	
	public String validate(By webSelectName,String webElementValue) throws Exception {
		String message;
		try{
			err1="No";
			Select dropdown = new Select(driver.findElement(webSelectName)); 
			message = dropdown.getFirstSelectedOption().getText(); // Validate the Selected values with Expected one..
			if (message.equals(webElementValue)){
				logMessage = "At test Step - Keyword = "+keyword+", Action = "+ "validate"+"  - . Locator : " + webSelectName.toString();
				System.out.println(logMessage); // Print on Console
				//logging
				logger.info(logMessage);
				//Log.info(logMessage); //Print on Log
			}else{
				err1 = "At test Step - Keyword = "+keyword+", Action = "+"validate"+"  - . Locator : " + webSelectName.toString();
				System.out.println(err1); // Print on Console
				//logging
				logger.info(err1);
				//Log.info(err1);
			}
		} catch(Exception e) {
			err1="******** Error occoured in test step *********: "+keyword+", Action = "+ "validate"+"  - not present please check..... Locator : " + webSelectName.toString();
			System.out.println(err1); // Print on Console
			//logging
			logger.error(err1);
			//Log.error(err1); //Print on Log
		}
		return err1;
	}

	/**
	 * WebSelect - Keyword & get - Action
	 * @method get
	 * @description - get action to get the visible value.
	 * 
	 * @throws Exception
	 */	
	public String get(By webSelectName) throws Exception {
		String message;
		try{
			err1="No";
			Select dropdown = new Select(driver.findElement(webSelectName)); 
			message = dropdown.getFirstSelectedOption().getText(); //get the visible value.
			logMessage = "At test Step - Keyword = WebSelect, Action = WebSelectGetValue -  . Locator : " + webSelectName.toString();
			System.out.println(logMessage); // Print on Console
			//logging
			logger.info(logMessage);
			//Log.info(logMessage); //Print on Log
		} catch(Exception e) {
			message="******** Error occoured in test step *********: WebSelect, WebSelectGetValue - not present please check..... Locator : " + webSelectName.toString();
			System.out.println(message);
			//logging
			logger.error(message);
			//Log.error(message);
		}
		return message;
	}

	/**
	 * WebSelect - Keyword & click - Action
	 * @method click
	 * @description - click action to click on List element
	 * 
	 * @throws Exception
	 */
	public String click(By webSelectName) throws Exception {
		try{
			err1="No";
			driver.findElement(webSelectName).click();
			logMessage = "At test Step - Keyword = "+keyword+", Action = "+ "click"+"  - Locator : " + webSelectName.toString();
			System.out.println(logMessage); // Print on Console
			//logging
			logger.info(logMessage);
			//Log.info(logMessage); //Print on Log
		} catch(Exception e) {
			err1="******** Error occoured in test step *********: "+keyword+", Action = "+ "click"+"   - not present please check..... Locator : " + webSelectName.toString();
			System.out.println(err1); // Print on Console
			//logging
			logger.error(err1);
			//Log.error(err1); //Print on Log
		}
		return err1;
	}

	/**
	 * WebSelect - Keyword & selectByLink - Action
	 * @method selectByLink
	 * @description - selectByLink action to Select by Link in List box
	 * 
	 * @throws Exception
	 */			
	public String selectByLink(By webSelectName) throws Exception {
		try{
			err1="No";
			driver.findElement(webSelectName).click();
			logMessage = "At test Step - Keyword = "+keyword+", Action = "+ "selectByLink"+"  - Locator : " + webSelectName.toString();
			System.out.println(logMessage); // Print on Console
			//logging
			logger.info(logMessage);
			//Log.info(logMessage); //Print on Log
		} catch(Exception e) {
			err1="******** Error occoured in test step *********: "+keyword+", Action = "+ "selectByLink"+" - not present please check..... Locator : " + webSelectName.toString();
			System.out.println(err1); // Print on Console
			//logging
			logger.error(err1);
			//Log.error(err1); //Print on Log
		}
		return err1;
	}

}
