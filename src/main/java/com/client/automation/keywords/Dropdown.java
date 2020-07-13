package com.client.automation.keywords;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;

import com.client.automation.library.Global;


/**
 * Dropdown - Keyword
 * @class Dropdown
 * @description - Node related all Actions are mentioned in this class
 * 
 * @throws Exception
 */

public class Dropdown{
	
	private static Logger logger = Logger.getLogger(Dropdown.class.getName());
	public String logMessage = null;
	private String keyword = "Dropdown";
	public String err1="No";
	public WebDriver driver;
	private List<WebElement> dropdownListElements = null;
	private WebElement dropdownElement = null;
	private boolean hasSelectTag = true;
	//private HashMap<String, String> dropdownsMap = new HashMap<String, String>();

	public Dropdown(WebDriver driver){
		this.driver = driver;
		//addDropdownsToMap();
	}
	
	/**
	 * Dropdown - Keyword & select - Action
	 * @method select
	 * @description - select the value from the dropdown.
	 * 
	 * @throws Exception
	 */
	public String select(By dropdownName, String valueToBeSelected) throws Exception {
		try{
			err1="No";
			new Wait(driver).waitForPageLoad();

			if(dropdownName != null){
				setDropdownElement(driver.findElement(dropdownName));
				if(hasSelectTag){
					Select dropdown = new Select(driver.findElement(dropdownName));
					System.out.println(getDropdownList().toString());
					dropdown.selectByVisibleText(valueToBeSelected);
					logMessage = "At test Step - Keyword = "+keyword+", Action = "+ "select" +" , Locator = " + dropdownName.toString()  + " " + valueToBeSelected; // This is to Display on console as well in Log File
					System.out.println(logMessage); // Print on Console
					logger.info(logMessage);
				} else {
					
					clickOnDropdownValue(valueToBeSelected);
					logMessage = "At test Step - Keyword = "+keyword+", Action = "+ "select" +" , Locator = " + dropdownName  + " " + valueToBeSelected; // This is to Display on console as well in Log File
					System.out.println(logMessage);
				}
			} else {
				logMessage = "At test Step - Keyword = "+keyword+", Action = "+ "select" +" , Locator = " + dropdownName  + " " + valueToBeSelected; // This is to Display on console as well in Log File
				err1="dropdown locator is null - Select dropdown value Failed - dropdown/dropdown value not present please check....." + dropdownName  + " " + valueToBeSelected; //Preparing Error message when unable to perform action
				System.out.println(logMessage);
				// throw error message that element not found
			}
			//Log.info(logMessage); //Print on Log
		} catch(Exception e) {
			err1="Select dropdown value Failed - dropdown/dropdown value not present please check....." + dropdownName.toString()  + " " + valueToBeSelected; //Preparing Error message when unable to perform action
			System.out.println(err1); // Print on Console // Print on Console
			
			logger.error(err1);
			//Log.error(err1); //Print on Log
		}
		return err1;
	}
	
	
	/**
	 * Dropdown - Keyword & getdropdownvalues - Action
	 * @method getdropdownvalues
	 * @description - store the value from the dropdown in collections and return it
	 * 
	 * @throws Exception
	 */
	 public List<String> getdropdownvalues(By dropdownproperty){
		 
		 try{
			 setDropdownElement(driver.findElement(dropdownproperty));
			List<WebElement> dropdownvalues =  getDropdwpownElements();
			List<String> dropdownlist = new ArrayList<>();
			for (WebElement element : dropdownvalues) {
				dropdownlist.add(element.getText());
			}
			Collections.sort(dropdownlist);
			return dropdownlist;
		 }catch(Exception e){
			 err1="store dropdown value Failed - dropdown/dropdown value is null....." ; //Preparing Error message when unable to perform action
				System.out.println(err1);
			 return null;
		 }
		
		
		
		 
	 }
	/**
	 * Dropdown - Keyword & selectlist of items - Action
	 * @method select
	 * @description - select the value from the dropdown.
	 * 
	 * @throws Exception
	 */
	public String selectlistitems(By dropdownName, String valueToBeSelected) throws Exception {
		try{

			err1="No";
			new Wait(driver).waitForPageLoad();
			/*Select dropdown = new Select(driver.findElement(dropdownName));
			dropdown.selectByVisibleText(valueToBeSelected);*/
			//driver.findElement(nodeName).click(); // Click on "+" Icon
			boolean errflag=true;
			WebElement list1;    
			list1=driver.findElement(dropdownName);    

			List<WebElement> lstOptions=list1.findElements(By.tagName("option"));    
			int increment=0;
			if(valueToBeSelected.contains(",")){

				String[] itemlist=valueToBeSelected.split(",");
				for (int item=0;item<itemlist.length;item++)
				{
					list1.sendKeys(Keys.CONTROL);
					for (int i=0;i<lstOptions.size();i++)
					{

						if(lstOptions.get(i).getText().equalsIgnoreCase(itemlist[item]))
						{

							lstOptions.get(i).click();
							logMessage = "At test Step - Keyword = "+keyword+", Action = "+ "select" +" , Locator = " + dropdownName.toString()  + " " + valueToBeSelected; // This is to Display on console as well in Log File
							System.out.println(logMessage); // Print on Console
							
							logger.info(logMessage);
							increment=increment+1;
							if(increment==itemlist.length)
							{
								errflag=false;
							}
						}//if

					}//second for	
				}//first for
			}//if
			else{
				for (int i=0;i<lstOptions.size();i++)
				{

					//list1.sendKeys(Keys.CONTROL);
					if(lstOptions.get(i).getText().equalsIgnoreCase(valueToBeSelected))
					{
						lstOptions.get(i).click();

						logMessage = "At test Step - Keyword = "+keyword+", Action = "+ "select" +" , Locator = " + dropdownName.toString()  + " " + valueToBeSelected; // This is to Display on console as well in Log File
						System.out.println(logMessage); // Print on Console
						
						logger.info(logMessage);
						errflag=false;
						break;
					}//if


				}//for
			}//else

			if(errflag)
			{
				System.out.println("enter into err messagge");
				err1="Select dropdown value Failed - dropdown/dropdown value not present please check....." + dropdownName.toString()  + " " + valueToBeSelected; //Preparing Error message when unable to perform action

			}		

			/*logMessage = "At test Step - Keyword = "+keyword+", Action = "+ "select" +" , Locator = " + dropdownName.toString()  + " " + valueToBeSelected; // This is to Display on console as well in Log File
			System.out.println(logMessage); // Print on Console
			
			LOGS.info(logMessage);*/
			//Log.info(logMessage); //Print on Log
		} catch(Exception e) {
			err1="Select dropdown value Failed - dropdown/dropdown value not present please check....." + dropdownName.toString()  + " " + valueToBeSelected; //Preparing Error message when unable to perform action
			System.out.println(err1); // Print on Console // Print on Console
			System.out.println(e.getStackTrace());
			
			logger.error(err1 + e.getMessage());
			logger.error(e.getStackTrace());
			//Log.error(err1); //Print on Log
		}
		return err1;
	}



	/**
	 * Dropdown - Keyword & selectAutoSearchValue - Action
	 * @method select
	 * @description - select the value from the dropdown.
	 * 
	 * @throws Exception
	 */
	public String selectAutoSearchValue(String valueToBeSelected) throws Exception {
		err1="No";
		String dropdownLocator = ".ui-autocomplete>li>a";
		boolean valueFound = false;
		Thread.sleep(1000);
		new Wait(driver).waitForPageLoad(10);
		//System.out.println("Waiting for page load");
		//Thread.sleep(2000);
		try{
			List<WebElement> autoSearchValues = driver.findElements(By.cssSelector(dropdownLocator));
			System.out.println("No fo elements found :" + autoSearchValues.size());
			for(WebElement dropdownValue : autoSearchValues){
				String value = dropdownValue.getText();
				System.out.println("dropdown value is  : " + value);
				if(dropdownValue.getText().contains(valueToBeSelected)){
					valueFound = true;
					dropdownValue.click();
					break;
				}
			}
			
			if(valueFound){
				logMessage = "At test Step - Keyword = "+keyword+", Action = "+ "selectAutoSearchValue" +" , Locator = " + dropdownLocator.toString()  + " " + valueToBeSelected; // This is to Display on console as well in Log File
			} else {
				logMessage = "At test Step - Keyword = "+keyword+", Action = "+ "selectAutoSearchValue" +" , Locator = " + dropdownLocator.toString()  + " " + valueToBeSelected; // This is to Display on console as well in Log File
				err1="Select dropdown value Failed - dropdown/dropdown value not present please check....." + dropdownLocator.toString()  + " " + valueToBeSelected; //Preparing Error message when unable to perform action
			}
			System.out.println(logMessage); // Print on Console
			logger.info(logMessage);
		} catch(Exception e) {
			err1="Exception occurred : Select dropdown value Failed - dropdown/dropdown value not present please check....." + dropdownLocator.toString()  + " " + valueToBeSelected; //Preparing Error message when unable to perform action
			System.out.println(err1 + e.getMessage()); // Print on Console // Print on Console
			
			logger.error(err1 + e.getMessage());
			//Log.error(err1); //Print on Log
		}
		
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("jQuery.active = 0");
		logger.info("WARNING : Set jQuery.active = 0 ");
		return err1;
	}	
	
	
	/**
	 * Dropdown - Keyword & verifyDropdownValues - Action
	 * @method select
	 * @description - select the value from the dropdown.
	 * 
	 * @throws Exception
	 */
	public String verifyDropdownValues(By dropdownName, String valuesToBeVerified) throws Exception {
		try{
			err1="No";
			String[] dropdownValuesToBeVerified = valuesToBeVerified.split(",");
			List<String> dropdownList = new ArrayList<String>();
			boolean valueFound = false;
			String dropdownValueNotFound = null;
			new Wait(driver).waitForPageLoad();
			
			setDropdownElement(driver.findElement(dropdownName));
			
			dropdownList = getDropdownList();
			
			
			for (String ValueToBeVerified : dropdownValuesToBeVerified){
				if(dropdownList.contains(ValueToBeVerified.trim())){
					valueFound = true;
				} else {
					valueFound = false;
					dropdownValueNotFound = ValueToBeVerified;
					break;
				}
					
			}
			
			if(!valueFound){
				err1 = "At test Step - Keyword = Dropdown, Action = " +" , Value from Application = " + dropdownList.toString() + ", Expected Value = "+ dropdownValueNotFound +", Locator : " + dropdownName.toString();
			}
			logMessage = "At test Step - Keyword = "+keyword+", Action = "+ "verifyDropdownValues" +" , Locator = " + dropdownName.toString()  + " " + valuesToBeVerified; // This is to Display on console as well in Log File
			System.out.println(logMessage); // Print on Console
			logger.info(logMessage);
		} catch(Exception e) {
			err1="Verifying dropdown value Failed - dropdown/dropdown value not present please check....." + dropdownName.toString()  + " " + valuesToBeVerified + "\n" + e.getMessage(); //Preparing Error message when unable to perform action
			System.out.println(err1); // Print on Console // Print on Console
			logger.error(err1);
		}
		return err1;
	}
	
	private By getDropdownValuesLocator() throws Exception{
		
		By dropdownObjects = null;

		String objectId = dropdownElement.getAttribute("id");
		String objectName = dropdownElement.getAttribute("name");

		if (objectId != null && !objectId.isEmpty()){

			//dropdownObjects = By.cssSelector(dropdownsMap.get(objectId));
			
			dropdownObjects = By.cssSelector("#"+objectId+ " + ul > li , " + "#"+objectId+ " + span > span");
			// eg : #acctMgmtTypeDisplay + ul > li , #acctMgmtTypeDisplay + span > span""
		}	else if (objectName != null) {
			System.out.println("dropdown id not found. Hence using name to extract dropdown values");
			dropdownObjects = By.cssSelector("[name='"+objectName+ "'] + ul > li , " + "[name='"+objectName+ "'] + span > span");
					
		}else {
			System.out.println("Not able to find the id / nmae of the given dropdown. Hence not able to extract dropdown elements");
			throw new Exception("Not able to find the id / name of the given dropdown. Hence not able to extract dropdown elements");
		}
		
			
	
		
		if (dropdownObjects == null){
			
			throw new Exception("Not able to find the locator for dropdown values");
		}
		
		return dropdownObjects;
	
	}
	

	private List<WebElement> getDropdwpownElements() throws Exception{
		
		if(hasSelectTag){
	
			Select dropdown = new Select(getDropdownElement());
			
			dropdownListElements = dropdown.getOptions();

		} else {
			
			By dropdownValuesProperty = getDropdownValuesLocator();

			dropdownListElements = driver.findElements(dropdownValuesProperty);

		}
		
		return dropdownListElements;

	}
	
	private void clickOnDropdownValue(String valueToBeSelected) throws Exception{
		
		getDropdownElement().click();
		
		List<WebElement> dropdownElements = getDropdwpownElements();
		
		for (WebElement dropdownElement: dropdownElements){

			if(dropdownElement.isDisplayed()){
				
				String dropdownValue = dropdownElement.getText();

				if(dropdownValue != null && dropdownValue.equalsIgnoreCase(valueToBeSelected)){

					JavascriptExecutor executor = (JavascriptExecutor)driver;

					executor.executeScript("arguments[0].click()", dropdownElement);

					break;

				}
			}
		}

	}
	
	
	private List<String> getDropdownList() throws Exception{
		
		getDropdownElement().click(); // open the dropdown
		
		int nullOrEmptyValuesCount = 0;
		
		List<String> dropdownList = new ArrayList<String>();
		
		List<WebElement> dropdownListElements = getDropdwpownElements();
		
		for (WebElement dropdownElement: dropdownListElements){

			if(dropdownElement.isDisplayed()){ 

				String dropdownValue = dropdownElement.getText();

				if(dropdownValue != null && !dropdownValue.isEmpty()){

					dropdownList.add(dropdownValue.trim());

				} else {

					nullOrEmptyValuesCount++;

				}
			}
		}
		
		if(nullOrEmptyValuesCount > 0){
			System.out.println("Warning : null or Empty values found in the dropdown."
								+ " Total Number of null of Emplty values found :" + nullOrEmptyValuesCount);
		}

		
		if(Global.getBrowser().equalsIgnoreCase("CHROME")){

			new Actions(driver).moveToElement(getDropdownElement(),0, -10).click().build().perform();
			
		} else {

			getDropdownElement().click();  // close the dropdown
		}
		
		return dropdownList;
		
	}
	
	
	private void setDropdownElement(WebElement dropdownElement){
		
		this.dropdownElement = dropdownElement;
		
		setSelectTagValue();
		
	}
		
	private WebElement getDropdownElement(){
		
		return dropdownElement;
		
	}
	
	
	
	private boolean setSelectTagValue(){
		
		hasSelectTag = dropdownElement.getTagName().equalsIgnoreCase("SELECT")? true : false;
		
		return hasSelectTag;
		
	}	
	/*
	private void addDropdownsToMap(){
		// map key, values are id (dropdown id), css (dropdown list css)
		dropdownsMap.put("acctMgmtTypeDisplay", "#acctMgmtType > li");

		dropdownsMap.put("newIndividualStateText", ".optionsList > span");
		
		dropdownsMap.put("companyType_visible", ".optionsList > span");
		
		dropdownsMap.put("billableText", ".optionsList > span");
		
		
	} */

}
