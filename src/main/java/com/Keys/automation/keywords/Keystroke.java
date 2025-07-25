package com.planit.automation.keywords;

import org.apache.log4j.Logger;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;


/**
 * RadioButton - Keyword
 * @class RadioButton
 * @description - RadioButton related all Actions are mentioned in this class
 * 
 * @throws Exception
 */
public class Keystroke{
	
	private static Logger logger = Logger.getLogger(Keystroke.class.getName());
	public String logMessage = null;
	private String keyword = "Keystroke";
	public String err1="No";
	public WebDriver driver;

	public Keystroke(WebDriver driver){
		this.driver = driver;
	}
	
	/**
	 * Keystroke - Keyword & enter - Action
	 * @method enter
	 * @description - Send keystroke "Enter" .
	 * 
	 * @throws Exception
	 */
	public String enter() throws Exception {
		try{
			err1="No";
			Actions action = new Actions(driver);
			action.sendKeys(Keys.ENTER).build().perform();
		} catch(Exception e) {
			err1= "Error : Exception occured while sending \"Enter\""  + "Keyword : " + keyword + " Action : " + " enter"; //Preparing Error message when unable to perform action
			System.out.println(err1); 
			logger.error(err1);
		}
		return err1;
	}

	/**
	 * Keystroke - Keyword & keyname - Action
	 * @method keyname
	 * @description - Send keystroke corrosponds to keyname .
	 * 
	 * @throws Exception
	 */
	public String keyname(String testdata) throws Exception {
		try{
			err1="No";
			Actions action = new Actions(driver);
			action.sendKeys(Keys.valueOf(testdata)).build().perform();
		} catch(Exception e) {
			err1= "Error : Exception occured while sending key \"" + testdata  + "\"; Keyword : " + keyword + " Action : " + " enter"; //Preparing Error message when unable to perform action
			System.out.println(err1); 
			logger.error(err1);
		}
		return err1;
	}
}
