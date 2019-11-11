package com.planit.automation.keywords;

import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;


/**
 * Validation - Keyword
 * @class Validation
 * @description - Validation related all Actions are mentioned in this class
 * 
 * @throws Exception
 */	
public class Validation{
	
	private static Logger logger = Logger.getLogger(Validation.class.getName());
	public String logMessage = null;
	private String keyword = "Validation";
	public String err1="No";
	public WebDriver driver;
	private ArrayList<String> arraylist = new ArrayList<>();

	public Validation(WebDriver driver){
		this.driver = driver;
	}
	
	public String assertion(String tovalidate,String tovalidatewith){
		
		try{
			err1="No";
			if(tovalidate.contains("[")){
				tovalidate = tovalidate.replaceAll("[\\[\\]]","");
			}
			if(tovalidatewith.contains("[")){
				tovalidatewith = tovalidatewith.replaceAll("[\\[\\]]","");
			}
			if(tovalidatewith.contains("+")){
				tovalidatewith = tovalidatewith.replace("+","");
				if (tovalidate.trim().contains(tovalidatewith.trim())){ //Compare it to Expected Value
					logMessage = "At test Step - Keyword = "+keyword+", Action = "+ "mapvalidation"+" , Value to compare = " +tovalidate + ", Expected Value = "+ tovalidatewith;
					System.out.println("BOTH THE VALUES ARE EQUAL ===");
					System.out.println(logMessage); // Print on Console
					//logging
					logger.info(logMessage);
					//Log.info(logMessage); //Print on Log
				}else {
					err1 = "At test Step - Keyword = Validation, Action = "+keyword+", Action = "+ "validation" +" , Value to compare = " +tovalidate + ", Expected Value = "+ tovalidatewith;
					System.out.println("BOTH THE VALUES ARE NOT EQUAL ###");
					System.out.println(err1); // Print on Console
					//logging
					logger.info(err1);
					//Log.info(err1);
					return err1;
				}
			}
			else{
			if (tovalidate.trim().equalsIgnoreCase(tovalidatewith.trim())){ //Compare it to Expected Value
				logMessage = "At test Step - Keyword = "+keyword+", Action = "+ "mapvalidation"+" , Value to compare = " +tovalidate + ", Expected Value = "+ tovalidatewith;
				System.out.println("BOTH THE VALUES ARE EQUAL ===");
				System.out.println(logMessage); // Print on Console
				//logging
				logger.info(logMessage);
				//Log.info(logMessage); //Print on Log
			}else {
				err1 = "At test Step - Keyword = Validation, Action = "+keyword+", Action = "+ "validation" +" , Value to compare = " +tovalidate + ", Expected Value = "+ tovalidatewith;
				System.out.println("BOTH THE VALUES ARE NOT EQUAL ###");
				System.out.println(err1); // Print on Console
				//logging
				logger.info(err1);
				//Log.info(err1);
				return err1;
			}
			}
		} catch(Exception e) {
			e.printStackTrace();
			err1=keyword ;
			System.out.println(err1); // Print on Console
			//Log.error(err1); //Print on Log
		}
		return err1;
	}
	
	
	public String amountrange(String tovalidate,String tovalidatewith){
		
		try{
			
			err1="No";
			String amountArray[] = tovalidate.split("-");
			int str_amount = Integer.parseInt(amountArray[0]);
			int end_amount = Integer.parseInt(amountArray[1]);
			
			String[] acutalAppAmt = tovalidatewith.split(",");
			String verifyAmt;
			int verAmt;
			boolean verify = false;
			for(int i=0;i>=acutalAppAmt.length-1;i++){
				verifyAmt = acutalAppAmt[i].toString();
				verifyAmt = verifyAmt.replace("[", "");
				verifyAmt = verifyAmt.replace("]", "");
				verAmt = Integer.parseInt(verifyAmt);
				
				if ((verAmt >= str_amount) && (verAmt <= end_amount)){
					
					verify = true;
					
				}else{
					verify = false;
					break;
				}
				
			}
			
			
			
			if (verify = true){ //Compare it to Expected Value
				logMessage = "At test Step - Keyword = "+keyword+", Action = "+ "amountrange"+" , Value to compare = " +tovalidate + ", Expected Value = "+ tovalidatewith;
				System.out.println("BOTH THE VALUES ARE EQUAL ===");
				System.out.println(logMessage); // Print on Console
				//logging
				logger.info(logMessage);
				//Log.info(logMessage); //Print on Log
			}else {
				err1 = "At test Step - Keyword = Validation, Action = "+keyword+", Action = "+ "validation" +" , Value to compare = " +tovalidate + ", Expected Value = "+ tovalidatewith;
				System.out.println("BOTH THE VALUES ARE NOT EQUAL ###");
				System.out.println(err1); // Print on Console
				//logging
				logger.info(err1);
				//Log.info(err1);
				return err1;
			}
		} catch(Exception e) {
			err1=keyword ;
			System.out.println("Exception Occured At test Step - Keyword = "+keyword+", Exception is - "+ e.toString()); // Print on Console
			//Log.error(err1); //Print on Log
		}
		return err1;
	}
	
			
	/**
	 * HTMLPopUp - Keyword & validate - Action
	 * @method validate
	 * @description - validate action to validate specified String.
	 * 
	 * @throws Exception
	 */
		public String datevalidation(String tovalidate,String tovalidatewith){
				
		try{
			err1="No";
			List<Date> dates = new ArrayList<Date>();
			if(!tovalidate.contains("-")){
				err1 = "date range must be specified in this format => 01/01/2000-02/02/2001";
				System.out.println(err1);
			}
			String datearray[] = tovalidate.split("-");
			String str_date = datearray[0];
			String end_date = datearray[1];
			DateFormat formatter ; 
			
			formatter = new SimpleDateFormat("MM/dd/yyyy");
			Date  startDate = (Date)formatter.parse(str_date); 
			Date  endDate = (Date)formatter.parse(end_date);
			long interval = 24*1000 * 60 * 60; // 1 hour in millis
			long endTime =endDate.getTime() ; // create your endtime here, possibly using Calendar or Date
			long curTime = startDate.getTime();
			while (curTime <= endTime) {
			    dates.add(new Date(curTime));
			    curTime += interval;
			}
			for(int i=0;i<dates.size();i++){
			    Date lDate =(Date)dates.get(i);
			    String ds = formatter.format(lDate);    
			    setlistvalues(ds);
			}
			
			tovalidatewith=tovalidatewith.replace("[", "");
			tovalidatewith=tovalidatewith.replace("]", "");

			
			String[] expectDateValuesArray = tovalidatewith.split(",");
			String[] actualDateValuesArray = getlistvalues().toString().split(",");
			
			boolean verify = false;
			String actualDateInArray;
			for (int i = 0;i<=expectDateValuesArray.length-1;i++){
		
				for(int j=0;j<=actualDateValuesArray.length-1;j++){
					actualDateInArray=actualDateValuesArray[j].toString().replaceAll("\\[", "").trim();
					actualDateInArray=actualDateInArray.replaceAll("\\]", "").trim();
					/*System.out.println(expectDateValuesArray[i].toString().trim());
					System.out.println(actualDateInArray);*/
					if(expectDateValuesArray[i].toString().trim().contains(actualDateInArray)){
						verify =true;
						break;
					}
				}
				if (verify == false){
					break;
				}
			}			
			if (verify == true){
				logMessage = "At test Step - Keyword = "+keyword+", Action = "+ "datevalidation"+" , Value to compare = " +getlistvalues().toString() + ", Expected Value = "+ tovalidatewith;
				System.out.println("All VALUES ARE EQUAL ===");
				System.out.println(logMessage); // Print on Console
				//logging
				logger.info(logMessage);
				//Log.info(logMessage); //Print on Log
			}else{
				err1 = "At test Step - Keyword = Validation, Action = "+keyword+", Action = "+ "datevalidation" +" , Value to compare = " +getlistvalues().toString() + ", Expected Value = "+ tovalidatewith;
				System.out.println("ALL VALUES ARE NOT EQUAL ###");
				System.out.println(err1); // Print on Console
				//logging
				logger.info(err1);
				//Log.info(err1);
				return err1;
			}
			
			/*if (getlistvalues().toString().contains(tovalidatewith)){ //Compare it to Expected Value
				System.out.println("four");
				logMessage = "At test Step - Keyword = "+keyword+", Action = "+ "datevalidation"+" , Value to compare = " +getlistvalues().toString() + ", Expected Value = "+ tovalidatewith;
				System.out.println("BOTH THE VALUES ARE EQUAL ===");
				System.out.println(logMessage); // Print on Console
				//logging
				logger.info(logMessage);
				//Log.info(logMessage); //Print on Log
			}else {
				System.out.println("five");
				err1 = "At test Step - Keyword = Validation, Action = "+keyword+", Action = "+ "validation" +" , Value to compare = " +getlistvalues().toString() + ", Expected Value = "+ tovalidatewith;
				System.out.println("BOTH THE VALUES ARE NOT EQUAL ###");
				System.out.println(err1); // Print on Console
				//logging
				logger.info(err1);
				//Log.info(err1);
				return err1;
			}*/
		} catch(Exception e) {
			err1=keyword ;
			System.out.println("Exception Occured At test Step - Keyword = "+keyword+", Exception is - "+ e.toString()); // Print on Console
			//Log.error(err1); //Print on Log
		}
		return err1;
	}
		
		
		public String dateassertion(String date1,String date2){
			String err1= "no";
			String logMessage ;
			boolean verify;
			try{
				err1="No";
				
				String tovalidate = date1;
				String tovalidatewith = date2;
				if(tovalidate.contains("-")||tovalidatewith.contains("-")){	
					tovalidate= tovalidate.replaceAll("-","/");
					tovalidatewith= tovalidatewith.replaceAll("-","/");
				}
				
				if(Character.isDigit(tovalidate.charAt(0))){
					int firstnum = Integer.parseInt(tovalidate.substring(0, 2));
					if(firstnum>12&&!(Character.isDigit(tovalidate.charAt(2)))){
						String month = tovalidate.substring(3, 5);
						tovalidate = tovalidate.replaceFirst(String.valueOf(firstnum), month);
						int length = tovalidate.length();
						tovalidate = tovalidate.substring(0,3).concat(String.valueOf(firstnum)).concat(tovalidate.substring(5,length));
				//		System.out.println("tovalidate:"+tovalidate);
					}
				}
				
				if(Character.isDigit(tovalidatewith.charAt(0))){
					int firstnum = Integer.parseInt(tovalidatewith.substring(0, 2));
					if(firstnum>12&&!(Character.isDigit(tovalidatewith.charAt(2)))){
						String month = tovalidatewith.substring(3, 5);
						tovalidatewith = tovalidatewith.replaceFirst(String.valueOf(firstnum), month);
						int length = tovalidatewith.length();
						tovalidatewith = tovalidatewith.substring(0,3).concat(String.valueOf(firstnum)).concat(tovalidate.substring(5,length));
				//		System.out.println("tovalidatewith:"+tovalidatewith);
					}
				}
				
				Date str_date = new Date(tovalidate);
				Date end_date = new Date(tovalidatewith);
				String startDate = new SimpleDateFormat("MM/dd/yyyy").format(str_date);
				String endDate  = new SimpleDateFormat("MM/dd/yyyy").format(end_date);;
				
				/*System.out.println(startDate);
				System.out.println(endDate);*/
			//	System.out.println(startDate.compareTo(endDate));
				int comparision = startDate.compareTo(endDate);
				
				if(comparision==0){
					logMessage = "At test Step - Keyword = "+keyword+", Action = "+ "assertion"+" , Value to compare = " +date1 + ", Expected Value = "+ date2;
					System.out.println(logMessage); 
					System.out.println("BOTH THE DATES ARE EQUAL ===");
					// Print on Console
					//logging
					
					logger.info(logMessage); //Print on Log
				}else if(comparision<0){
					err1 = "At test Step - Keyword = "+keyword+", Action = "+ "assertion" +" , Value to compare = " +date1 + ", Expected Value = "+ date2;
					System.out.println("BOTH THE DATES ARE NOT EQUAL ###");
					System.out.println(err1); // Print on Console
					//logging
					
					logger.info(err1);
				}else if(comparision>0){
					err1 = "At test Step - Keyword = "+keyword+", Action = "+ "assertion" +" , Value to compare = " +date1 + ", Expected Value = "+ date2;
					System.out.println("BOTH THE DATES ARE NOT EQUAL ###");
					System.out.println(err1); // Print on Console
					//logging
					
					logger.info(err1);
				}

				
					return err1;
				}
				catch(Exception e) {
				e.printStackTrace();
				err1="�rror in catch" ;
				System.out.println("Exception Occured At test Step - Keyword = "+err1+", Exception is - "+ e.toString()); // Print on Console
				logger.info(err1); //Print on Log
				return err1;
			}
			
		}
	/**
	 * HTMLPopUp - Keyword & validate - Action
	 * @method validate
	 * @description - validate action to validate specified String.
	 * 
	 * @throws Exception
	 */
	public String validate(By validationName,String validateValue) throws Exception {
		try{
			err1="No";
			WebElement validateElement = driver.findElement(validationName); // Get Actual value from Application
			String ActualValue = validateElement.getText().trim(); // Get Actual value from Application
			
			if(ActualValue.isEmpty()){
				JavascriptExecutor executor = (JavascriptExecutor)driver;
				String valueOfElement = (String) executor.executeScript("return arguments[0].value", validateElement);
				ActualValue = (valueOfElement != null ? valueOfElement : ActualValue);
				//System.out.println("Appvalue inside java script :" + appValue);
			}

			if (ActualValue.equals(validateValue.trim())){ //Compare it to Expected Value
				logMessage = "At test Step - Keyword = "+keyword+", Action = "+ "validate"+" , Value from Application = " +ActualValue + ", Expected Value = "+ validateValue +" , Locator : " + validationName.toString();
				System.out.println(logMessage); // Print on Console
				//logging
				logger.info(logMessage);
				//Log.info(logMessage); //Print on Log
			}else {
				err1 = "At test Step - Keyword = Validation, Action = "+keyword+", Action = "+ "validate" +" , Value from Application = " +ActualValue + ", Expected Value = "+ validateValue +", Locator : " + validationName.toString();
				System.out.println(err1); // Print on Console
				//logging
				logger.info(err1);
				//Log.info(err1);
				return err1;
			}
		} catch(Exception e) {
			err1=keyword+" not present please check....."+ validationName.toString();
			System.out.println(err1); // Print on Console
			//Log.error(err1); //Print on Log
		}
		return err1;
	}

	

	/**
	 * HTMLPopUp - Keyword & validateText - Action
	 * @method validate
	 * @description - validate action to validate specified String.
	 * 
	 * @throws Exception
	 */
	public String validateText(By validationName,String validateValue) throws Exception {
		try{
			err1="No";
			List<WebElement> elementList = driver.findElements(validationName); // Get list of all element
			String appValue = null;
			boolean textFound=false;
			for(int elelist=0;elelist<elementList.size();elelist++){
				appValue = elementList.get(elelist).getText().trim();
				if(appValue.isEmpty()){
					JavascriptExecutor executor = (JavascriptExecutor)driver;
					String valueOfElement = (String) executor.executeScript("return arguments[0].value", elementList.get(elelist));
					appValue = (valueOfElement != null ? valueOfElement : appValue);
					//System.out.println("Appvalue inside java script :" + appValue);
				}
				//System.out.println("timeline content text :" +appValue);
				if (appValue.contains(validateValue.trim())){//Compare it to Expected Value
					textFound=true;
					break;
				}
			}

			if (textFound){ 
				logMessage = "At test Step - Keyword = "+keyword+", Action = "+ "validateText"+", Expected Value = "+ validateValue + " , Value from Application = " +appValue + ", Locator : " + validationName.toString();
				System.out.println(logMessage); // Print on Console
				//logging
				logger.info(logMessage);
				//Log.info(logMessage); //Print on Log
			}else {
				err1 = "At test Step - Keyword = Validation, Action = "+keyword+", Action = "+ "validateText"+", Expected Value = "+ validateValue +" , Value from Application = " +appValue + ", Locator : " + validationName.toString();
				System.out.println(err1); // Print on Console
				//logging
				logger.info(err1);
				//Log.info(err1);
				return err1;
			}
		} catch(Exception e) {
			err1=keyword+" : Exception occured while validating the text ....."+ validationName.toString();
			System.out.println(err1); // Print on Console
			//logging
			logger.error(err1);
			//Log.error(err1); //Print on Log
		}
		return err1;
	}
	
	/**
	 * Validation - Keyword & pageTitle - Action
	 * @method validate
	 * @description - validate action to validate specified String.
	 * 
	 * @throws Exception
	 */
	public String pageTitle(String validateValue) throws Exception {
		try{
			err1="No";
			String actualPageTitle = driver.getTitle().trim(); // Get Actual value from Application
			actualPageTitle=actualPageTitle.replaceAll("^\\s+", "");
			if (actualPageTitle.trim().contains(validateValue.trim())){ //Compare it to Expected Value
				logMessage = "At test Step - Keyword = "+keyword+", Action = "+ "validate"+" , Value from Application = "+actualPageTitle + ", Expected Value = "+ validateValue;
				System.out.println(logMessage); // Print on Console
				//logging
				logger.info(logMessage);
				//Log.info(logMessage); //Print on Log
			}else {
				err1 = "FAILED - Value from Application = :"+actualPageTitle+"|, Expected Value = :"+validateValue+"|";
				System.out.println(err1); // Print on Console
				//logging
				logger.info(err1);
				//Log.info(err1);
				return err1;
			}
		} catch(Exception e) {
			err1= "Keyword : " + keyword + "Action : pageTitle  Exception while validating Page title : "+ validateValue;
			System.out.println(err1); // Print on Console
			//Log.error(err1); //Print on Log
		}
		return err1;
	}
	
	
	/**
	 * Validation - Keyword & validateText - Action
	 * @method validate
	 * @description - validate action to validate specified String.
	 * 
	 * @throws Exception
	 */
	public String validateList(By validationName,String validateValues) throws Exception {
		try{
			err1="No";
			List<WebElement> elementList = driver.findElements(validationName); // Get list of all element
			String[] valuesToBeValidated = validateValues.split(",");
			//List<String> valuesList = Arrays.asList(valuesToBeValidated);
			List<String> appValuesList = new ArrayList<String>();
			
			String appValue = null;
			boolean valuesFound = true;
			String ValueNotFound = null;
			
			for(int elelist=0;elelist<elementList.size();elelist++){
				appValue = elementList.get(elelist).getText().trim();
				if(appValue.isEmpty()){
					//JavascriptExecutor executor = (JavascriptExecutor)driver;
					//String valueOfElement = (String) executor.executeScript("return arguments[0].value", elementList.get(elelist));
					//appValue = (valueOfElement != null ? valueOfElement : appValue);
					//System.out.println("Appvalue inside java script :" + appValue);
				}
				
				appValuesList.add(appValue.trim());
		}
			
			
			for (String ValueToBeVerified : valuesToBeValidated){
				if(appValuesList.contains(ValueToBeVerified.trim())){
					valuesFound = true;
				} else {
					valuesFound = false;
					ValueNotFound = ValueToBeVerified;
					break;
				}
					
			}
			

			if (valuesFound){ 
				logMessage = "At test Step - Keyword = "+keyword+", Action = "+ "validateText"+", Expected Values = "+ validateValues + " , Value from Application = " +appValuesList.toString() + ", Locator : " + validationName.toString();
				System.out.println(logMessage); // Print on Console
				//logging
				logger.info(logMessage);
				//Log.info(logMessage); //Print on Log
			}else {
				err1 = "At test Step - Keyword = Validation, Action = "+keyword+", Action = "+ "validateText"+", Expected Value = "+ ValueNotFound +" , Value from Application = " +appValuesList.toString() + ", Locator : " + validationName.toString();
				System.out.println(logMessage); // Print on Console + ", Locator : " + validationName.toString();
				System.out.println(err1); // Print on Console
				//logging
				logger.info(err1);
				//Log.info(err1);
				return err1;
			}
		} catch(Exception e) {
			err1=keyword+" : Exception occured while validating the text ....."+ validationName.toString();
			System.out.println(err1); // Print on Console
			//logging
			logger.error(err1);
			//Log.error(err1); //Print on Log
		}
		return err1;
	}
	/**
	 * validate - Keyword & validateProperty - Action
	 * @method validate
	 * @description - validate action to validate specified String.
	 * 
	 * @throws Exception
	 */
	public String validateProperty(By validationName,String validateValue) throws Exception {
		try{
			err1="No";
			String property;
			String valueToBeValidated;

			String[] arrayOfStings = validateValue.split("=");
			if(arrayOfStings.length > 1){
				property = arrayOfStings[0];
				valueToBeValidated = arrayOfStings[1]; 
			} else {
				property = arrayOfStings[0];
				valueToBeValidated = ""; 

			}
			
			WebElement validateElement = driver.findElement(validationName); // Get Actual value from Application
			String propertyValue = validateElement.getAttribute(property).trim(); // Get Actual value from Application
			
			if (propertyValue.equals(valueToBeValidated.trim())){ //Compare it to Expected Value
				logMessage = "At test Step - Keyword = "+keyword+", Action = "+ "validate"+" , Value from Application = " +propertyValue + ", Expected Value = "+ valueToBeValidated +" , Locator : " + validationName.toString();
				System.out.println(logMessage); // Print on Console
				//logging
				logger.info(logMessage);
				//Log.info(logMessage); //Print on Log
			}else {
				err1 = "At test Step - Keyword = Validation, Action = "+keyword+", Action = "+ "validate" +" , Value from Application = " +propertyValue + ", Expected Value = "+ valueToBeValidated +", Locator : " + validationName.toString();
				System.out.println(err1); // Print on Console
				//logging
				logger.info(err1);
				//Log.info(err1);
				return err1;
			}
		} catch(Exception e) {
			err1=keyword+" not present please check....."+ validationName.toString();
			System.out.println(err1); // Print on Console
			//Log.error(err1); //Print on Log
		}
		return err1;
	}

	/**
	 * Validate - Keyword & CompareValues - Action
	 * @method validate
	 * @description - validate action to validate specified String.
	 * 
	 * @throws Exception
	 */
	public String compareValues(String valueToCompare,String valueCompareWith) throws Exception {
		try{
			err1="No";
			String valToCompare = valueToCompare;
			String valCompareWith = valueCompareWith;
			System.out.println("Value to Compare = "+valToCompare );
			System.out.println("Value to Compare With = "+valCompareWith );
			boolean textFound=false;
			
				if (valToCompare.contains(valCompareWith.trim())){//Compare it to Expected Value
					textFound=true;
				}
			

			if (textFound){ 
				logMessage = "At test Step - Keyword = "+keyword+", Action = "+ "validateText"+", Expected Value = "+ valueCompareWith + " , Value from Application = " +valueToCompare ;
				System.out.println(logMessage); // Print on Console
				//logging
				logger.info(logMessage);
				//Log.info(logMessage); //Print on Log
			}else {
				err1 = "At test Step - Keyword = Validation, Action = "+keyword+", Action = "+ "validateText"+", Expected Value = "+ valueCompareWith +" , Value from Application = " +valueToCompare;
				System.out.println(err1); // Print on Console
				//logging
				logger.info(err1);
				//Log.info(err1);
				return err1;
			}
		} catch(Exception e) {
			err1=keyword+" : Exception occured while Comparing Values..Pls check .....";
			System.out.println(err1); // Print on Console
			//logging
			logger.error(err1);
			//Log.error(err1); //Print on Log
		}
		return err1;
	}
	
	
	/**
	 * Validate - Keyword & valuenotpresent - Action
	 * @method validate
	 * @description - validate action to validate specified String.
	 * 
	 * @throws Exception
	 */
	public String valuenotpresent(String valueToCompare,String valueCompareWith) throws Exception {
		try{
			err1="No";
			String valToCompare = valueToCompare;
			String valCompareWith = valueCompareWith;
			System.out.println("Value to Compare = "+valToCompare );
			System.out.println("Value to Compare With = "+valCompareWith );
			boolean textFound=false;
			
				if (valToCompare.contains(valCompareWith.trim())){//Compare it to Expected Value
					textFound=true;
				}
			

			if (!textFound){ 
				logMessage = "At test Step - Keyword = "+keyword+", Action = "+ "valuenotpresent"+", Expected Value = "+ valueCompareWith + " , Value from Application = " +valueToCompare ;
				System.out.println(logMessage); // Print on Console
				//logging
				logger.info(logMessage);
				//Log.info(logMessage); //Print on Log
			}else {
				err1 = "At test Step - Keyword = Validation, Action = "+keyword+", Action = "+ "valuenotpresent"+", Expected Value = "+ valueCompareWith +" , Value from Application = " +valueToCompare;
				System.out.println(err1); // Print on Console
				//logging
				logger.info(err1);
				//Log.info(err1);
				return err1;
			}
		} catch(Exception e) {
			err1=keyword+" : Exception occured while Comparing Values..Pls check .....";
			System.out.println(err1); // Print on Console
			//logging
			logger.error(err1);
			//Log.error(err1); //Print on Log
		}
		return err1;
	}
	
	
	/**
	 * Validate - Keyword & Validation - Action
	 * @method searchText
	 * @description - validate action to validate specified String.
	 * 
	 * @throws Exception
	 */
	public String searchText(String textToSearch) throws Exception {
		try{
			err1="No";
		
			if (textToSearch!=null){
				String valTextSearch = textToSearch;
				System.out.println("Text To Search in Page = "+valTextSearch );
				WebElement elementText = driver.findElement(By.xpath("//*[contains(text(),'"+valTextSearch+"')]"));
				System.out.println("Element Tag name : "+elementText.getTagName().toString());
				if (elementText.getTagName().toString()!=null){//Compare it to Expected Value
					logMessage = "At test Step - Keyword = "+keyword+", Action = "+ "searchText"+", Able to Find specified Text in the Page " ;
					System.out.println(); // Print on Console
					//logging
					logger.info(logMessage);
					//Log.info(logMessage);
					
				}else {
					err1 = "At test Step - Keyword = Validation, Action = "+keyword+", Action = "+ "searchText"+", UNABLE TO FIND TEXT IN THE PAGE..... PLS CHECK SEEMS TO BE BUG..";
					System.out.println(err1); // Print on Console
					//logging
					logger.info(err1);
					//Log.info(err1); //Print on Log
					return err1;

				}
			}
		} catch(Exception e) {
			if (e.toString().contains("NoSuchElementException")) {
				err1 = "At test Step - Keyword = Validation, Action = "+keyword+", Action = "+ "searchText"+", UNABLE TO FIND TEXT IN THE PAGE..... PLS CHECK SEEMS TO BE BUG..";
				System.out.println(err1); // Print on Console
				//logging
				logger.info(err1);
				//Log.info(err1); //Print on Log
				return err1;

			}else{
			err1=keyword+" : Exception occured while searching Text..Pls check .....";
			System.out.println(err1); // Print on Console
			//logging
			logger.error(err1);
			//Log.error(err1); //Print on Log
			}
		}
		return err1;
	}
	
	/**
	 * Validate - Keyword & Validation - Action
	 * @method searchExactText
	 * @description - validate action to validate specified String.
	 * 
	 * @throws Exception
	 */
	public String searchExactText(String textToSearch) throws Exception {
		try{
			err1="No";
			if (textToSearch!=null){
				String valTextSearch = textToSearch;
				System.out.println("Text To Search in Page = "+valTextSearch );
				WebElement elementText = driver.findElement(By.xpath("//*[text()='"+valTextSearch+"')]"));
				System.out.println("Element Tag name : "+elementText.getTagName().toString());
				if (elementText.getTagName().toString()!=null){//Compare it to Expected Value
					logMessage = "At test Step - Keyword = "+keyword+", Action = "+ "searchExactText"+", Able to Find specified Text in the Page " ;
					System.out.println(); // Print on Console
					//logging
					logger.info(logMessage);
					//Log.info(logMessage);
					
				}else {
					err1 = "At test Step - Keyword = Validation, Action = "+keyword+", Action = "+ "searchExactText"+", UNABLE TO FIND TEXT IN THE PAGE..... PLS CHECK SEEMS TO BE BUG..";
					System.out.println(err1); // Print on Console
					//logging
					logger.info(err1);
					//Log.info(err1); //Print on Log
					return err1;
	
				}
			}
		} catch(Exception e) {
			if (e.toString().contains("NoSuchElementException")) {
				err1 = "At test Step - Keyword = Validation, Action = "+keyword+", Action = "+ "searchExactText"+", UNABLE TO FIND TEXT IN THE PAGE..... PLS CHECK SEEMS TO BE BUG..";
				System.out.println(err1); // Print on Console
				//logging
				logger.info(err1);
				//Log.info(err1); //Print on Log
				return err1;
	
			}else{
			err1=keyword+" : Exception occured while searching Exact Text..Pls check .....";
			System.out.println(err1); // Print on Console
			//logging
			logger.error(err1);
			//Log.error(err1); //Print on Log
			}
		}
	
		return err1;
	}
	
	
	
	public void setlistvalues(String valuetoaddinarraylist){
		
		if(!arraylist.contains(valuetoaddinarraylist)){
		arraylist.add(valuetoaddinarraylist);
		Collections.sort(arraylist, new Comparator<String>() {
            DateFormat f = new SimpleDateFormat("MM/dd/yyyy");
            @Override
            public int compare(String o1, String o2) {
                try {
                    return f.parse(o1).compareTo(f.parse(o2));
                } catch (java.text.ParseException e) {
                    throw new IllegalArgumentException(e);
                }
            }
        });
		}
	}
	
	public ArrayList<String> getlistvalues(){
		return arraylist;
				
	}
	
	public void clearlist(){
		arraylist.clear();
	}
}
