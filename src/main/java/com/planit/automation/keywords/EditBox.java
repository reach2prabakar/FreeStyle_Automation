package com.planit.automation.keywords;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * EditBox - Keyword
 * @class EditBox
 * @description - EditBox related all Actions are mentioned in this class
 * 
 * @throws Exception
 */
public class EditBox{
	
	private static Logger logger = Logger.getLogger(EditBox.class.getName());
	public String logMessage = null;
	private String keyword = "EditBox";
	public String err1="No";
	public WebDriver driver;

	public EditBox(WebDriver driver){
		this.driver = driver;
	}
	
	/**
	 * EditBox - Keyword & enter - Action
	 * @method enter
	 * @description - enter action to Enter values in the Text Box.
	 * 
	 * @throws Exception
	 */
	public String enter(By editName,String editValue) throws Exception {
		try{
			err1="No";
			new Wait(driver).explicitWait(editName);
			if(editValue==null){
				System.out.println("Keyword --> EDITBOX ,, Action -- > ENTER is to enter values into text box , but no data is entered in textbox , please enter your testdata");
			}
			WebElement inputElement = driver.findElement(editName); //Select Text Box
			inputElement.click();
			inputElement.sendKeys(editValue); // Enter values in Text Box
			if(inputElement.getAttribute("onchange") != null ){ 
				JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
				if( (inputElement.getAttribute("autocomplete") == null) || 
						(inputElement.getAttribute("autocomplete") != null && !inputElement.getAttribute("autocomplete").equalsIgnoreCase("OFF"))){
						jsExecutor.executeScript("$(arguments[0]).change();", inputElement);
				System.out.println("Ran Java script!!");
				} 
			}
			logMessage = "At test Step - Keyword = "+keyword+", Action = "+"enter"+" , Value Entered = " + editValue +" , Locator : " + editName.toString(); // This is to Display on console as well in Log File
			System.out.println(logMessage); // Print on Console
			
			logger.info(logMessage);
			
		} catch(Exception e) {
			err1=keyword+" not present please check....."+ editName.toString(); //Preparing Error message when unable to perform action
			System.out.println(err1 + e.getMessage()); // Print on Console // Print on Console
			
			logger.error(err1 + e.getMessage());
			//Log.error(err1); //Print on Log
		}
		return err1;
	}

	
	////////// Using Jsoup trying to identify the element based on the manual test cases and get the loctor automatically and generate scripts
	//////////lot of workaround still trying
	/*
	 * EditBox - Keyword & enterByName - Action
	 * @method enter
	 * @description - enter action to Enter values in the Text Box.
	 * 
	 * @throws Exception
	 *
	public String enterByLabel(String editBoxName,String editValue) throws Exception {
		try{
			err1="No";
			String html = driver.getPageSource();
			Document doc = Jsoup.parse(html);
			String cssSelector = null;
			boolean editboxFound = false;
			Elements editboxes = doc.select("input[type=\"text\"],input[type=\"password\"],textarea");
			for(Element editbox : editboxes){
				String editboxLable = null;
				Element siblingElement = editbox.previousElementSibling();
				if(siblingElement != null){
					editboxLable = siblingElement.text();

					if(editboxLable.trim().equals(editBoxName.trim())){
						editboxFound = true;
						cssSelector = editbox.cssSelector();
						err1 = enter(By.cssSelector(cssSelector),editValue);
						break;
					}
				}
				
				
			}
			
			if (editboxFound == false){
				err1 = "Editbox : " + editBoxName + " Not found. Please check....."+ editBoxName.toString(); //Preparing Error message when unable to perform action
				System.out.println(err1); // Print on Console // Print on Console
				
				logger.error(err1);
				
			}
					
		} catch(Exception e) {
			err1 = "Exception occured while entering the value in Edit box. keyword :"+ keyword + "not present please check....."+ editBoxName.toString(); //Preparing Error message when unable to perform action
			System.out.println(err1 + e.getMessage()); // Print on Console // Print on Console
			
			logger.error(err1 + e.getMessage());
			//Log.error(err1); //Print on Log
		}
		return err1;
	}

	/
//////////Using Jsoup trying to identify the element based on the manual test cases and get the loctor automatically and generate scripts
///////////lot of workaround still trying
	
	*/
	
	
	/**
	 * EditBox - Keyword & clear - Action
	 * @method clear
	 * @description - clear action to Clear values in the Text Box.
	 * 
	 * @throws Exception
	 */
	public String clear(By editName) throws Exception {

		try{
			err1="No";
			WebElement inputElement = driver.findElement(editName);
			inputElement.clear();
			logMessage = "At test Step - Keyword = "+keyword+", Action = "+"clear"+" , Locator = " + editName.toString(); // This is to Display on console as well in Log File
			System.out.println(logMessage); // Print on Console
			
			logger.info(logMessage);
			//Log.info(logMessage); //Print on Log
		} catch(Exception e) {
			err1=keyword+" not present please check....."+ editName.toString();//Preparing Error message when unable to perform action
			System.out.println(err1 + e.getMessage()); // Print on Console // Print on Console
			
			logger.error(err1 + e.getMessage());
			//Log.error(err1); //Print on Log
		}
		return err1;
	}

	/**
	 * EditBox - Keyword & validate - Action
	 * @method validate
	 * @description - validate action to validate values in the Text Box.
	 * 
	 * @throws Exception
	 */
	public String validate(By editName,String editExtValue) throws Exception {
		try{
			err1="No";
			String actualValue = driver.findElement(editName).getAttribute("value").toString(); // Get Actual value from Application
			//Condition to validate values
			if (actualValue.equals(editExtValue)){
				logMessage = "At test Step - Keyword = "+keyword+", Action = "+"validate"+" , Locator = " + editName.toString();
				System.out.println(logMessage); // Print on Console
				
				logger.info(logMessage);
				//Log.info(logMessage); //Print on Log
			}else{
				err1 = "Application Value is : " + actualValue + " , Expected Value is : "+editExtValue;
				System.out.println(err1); // Print on Console // Print on Console
				
				logger.info(err1);
				//Log.info(err1);
			}
		} catch(Exception e) {
			err1="******** Error occoured in test step *********: "+keyword+", Action = "+"validate"+" , Locator = " + editName.toString();
			System.out.println(err1 + e.getMessage()); // Print on Console // Print on Console
			
			logger.error(err1 + e.getMessage());
			//Log.error(err1); //Print on Log
		}
		return err1;
	}
}
