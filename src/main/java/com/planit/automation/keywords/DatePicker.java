package com.planit.automation.keywords;


import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import com.planit.automation.library.DynamicVarRepository;




/**
 * Date - Keyword
 * @class Date
 * @description - Date related all Actions are mentioned in this class
 * 
 * @throws Exception
 */
public class DatePicker{
	
	private static Logger logger = Logger.getLogger(DatePicker.class.getName());
	private String logMessage = null;
	private String keyword = "DatePicker";
	private String err1="No";
	private WebDriver driver;
	private String mapValue;
	
	public DatePicker(WebDriver driver){
		this.driver = driver;
	}
	
	/**
	 * Date - Keyword & click - Action
	 * @method click
	 * @description - click action to click on Date Object
	 * 
	 * @throws Exception
	 */

	public String SelectFromDateInCalendar(By dateField,String testdata,String waitTime) {
		try{
			err1="No";
			String fromMonth;String fromYear;String fromDay;
			String[] datetestdata = testdata.split("/");
			fromDay = datetestdata[1];			
			fromMonth = datetestdata[0];
			fromYear = datetestdata[2];
			
			//ScrollDown The Browser On DateField.Because In AAA Date Is Overlapping
			JavascriptExecutor js = (JavascriptExecutor) driver;
			js.executeScript("arguments[0].scrollIntoView(true);", driver.findElement(dateField));

			driver.findElement(dateField).click();
			Thread.sleep(Integer.parseInt(waitTime));

			Select selectYear = new Select(driver.findElement(By.cssSelector(".ui-datepicker-title>select.ui-datepicker-year")));
			selectYear.selectByVisibleText(fromYear);
			Select selectMonth = new Select(driver.findElement(By.cssSelector(".ui-datepicker-title>select.ui-datepicker-month")));
			if(fromMonth.matches("\\d*")){
				//since in calendar values for months are from '0' to '11'
				fromMonth = String.valueOf(Integer.valueOf(fromMonth)-1);
				selectMonth.selectByValue(fromMonth);
			}else if(fromMonth.matches("\\D*")){
				selectMonth.selectByVisibleText(fromMonth);
			}
			//selectMonth.selectByVisibleText(fromMonth);
			List<WebElement> dayList= driver.findElements(By.cssSelector("table.ui-datepicker-calendar td[data-handler='selectDay'] a"));
			boolean foundDay = false;
			for(WebElement selectDay:dayList){
				if(selectDay.getText().equals(fromDay)){
					selectDay.click();
					foundDay = true;
					break;
				}
			}
			if(foundDay == false){
				System.out.println("Invalid from day:"+fromDay+" opted in calendar");
				logger.info("Invalid from day:"+fromDay+" opted in calendar");
				driver.findElement(dateField).click();
			}
			//String dateSelected = driver.findElement(By.cssSelector(dateField)).getAttribute("value");
			//return dateSelected;
		}
		catch(InterruptedException e){
			err1="******** Error occoured in test step *********: WebSelect - WebSelectByLInk  - not present please check..... Locator : " + dateField.toString();
			System.out.println(err1 + e.getMessage());
			logger.error(err1 + e.getMessage());
			
			
		}

		return err1;
	}

	public String SelectToDateInCalendar(By dateField,String testdata) {
		try{
			err1="No";
			String toMonth;String toYear;String toDay;
			String[] datetestdata = testdata.split("/");
			toDay = datetestdata[1];
			toMonth = datetestdata[0];
			toYear = datetestdata[2];

			driver.findElement(dateField).click();
			//Thread.sleep(Integer.parseInt(waitTime));

			Select selectYear = new Select(driver.findElement(By.cssSelector(".ui-datepicker-title>select.ui-datepicker-year")));
			try{
				selectYear.selectByVisibleText(toYear);
			}catch(Exception e){
				System.out.println("Invalid year:"+toYear+" w.r.t from date selection");
				//logging
				logger.error("Invalid year:"+toYear+" w.r.t from date selection");
				selectYear.getFirstSelectedOption().getAttribute("value");
			}
			Select selectMonth = new Select(driver.findElement(By.cssSelector(".ui-datepicker-title>select.ui-datepicker-month")));
			try{
				//selectMonth.selectByVisibleText(toMonth);
				if(toMonth.matches("\\d*")){
					//since in calendar values for months are from '0' to '11'
					toMonth = String.valueOf(Integer.valueOf(toMonth)-1);
					selectMonth.selectByValue(toMonth);
				}else if(toMonth.matches("\\D*")){
					selectMonth.selectByVisibleText(toMonth);
				}
			}catch(Exception e){
				System.out.println("Invalid month:"+toMonth+" selected w.r.t from date year:"+toYear);
				selectMonth.getFirstSelectedOption().getAttribute("value");
			}
			//selectMonth.selectByVisibleText(fromMonth);
			List<WebElement> dayList= driver.findElements(By.cssSelector("table.ui-datepicker-calendar td[data-handler='selectDay'] a"));
			boolean foundDay = false;
			for(WebElement selectDay:dayList){
				if(selectDay.getText().equals(toDay)){
					selectDay.click();
					foundDay = true;
					break;
				}
			}
			if(foundDay == false){
				System.out.println("Invalid from day:"+toDay+" opted in calendar");
				logger.info("Invalid from day:"+toDay+" opted in calendar");
				
				driver.findElement(dateField).click();
			}
			//String dateSelected = driver.findElement(By.cssSelector(dateField)).getAttribute("value");
			//return dateSelected;
		}
		catch(Exception e){
			err1="******** Error occoured in test step *********: WebSelect - WebSelectByLInk  - not present please check..... Locator : " + dateField.toString();
			System.out.println(err1 + e.getMessage());
			//logging
			logger.error(err1 + e.getMessage());
		}
		return err1;
	}

	
	public String SelectDateTimeInCalendar(By dateField,String testdata) {
		try{
			err1="No";
			String toMonth;String toYear;String toDay,dateData,timeData,hour,minute,amPM,hourPM;
			String[] dateTimeData = testdata.split(" ");
			dateData = 	dateTimeData[0];
			timeData = 	dateTimeData[1];
			amPM = dateTimeData[2];
			String[] datetestdata = dateData.split("/");
			toDay = datetestdata[1];
			toMonth = datetestdata[0];
			toYear = datetestdata[2];
			String[] hourMinArray = timeData.split(":");
			hour = hourMinArray[0];
			minute = hourMinArray[1];
				
			hourPM = hour +" "+amPM;
			
			if (hourPM.length()==4){
				hourPM=0+hourPM;
			}
			
			
			driver.findElement(dateField).click();
			//Thread.sleep(Integer.parseInt(waitTime));

			Select selectYear = new Select(driver.findElement(By.cssSelector(".ui-datepicker-title>select.ui-datepicker-year")));
			try{
				selectYear.selectByVisibleText(toYear);
			}catch(Exception e){
				System.out.println("Invalid year:"+toYear+" w.r.t from date selection");
				//logging
				logger.error("Invalid year:"+toYear+" w.r.t from date selection");
				selectYear.getFirstSelectedOption().getAttribute("value");
			}
			Select selectMonth = new Select(driver.findElement(By.cssSelector(".ui-datepicker-title>select.ui-datepicker-month")));
			try{
				//selectMonth.selectByVisibleText(toMonth);
				if(toMonth.matches("\\d*")){
					//since in calendar values for months are from '0' to '11'
					toMonth = String.valueOf(Integer.valueOf(toMonth)-1);
					selectMonth.selectByValue(toMonth);
				}else if(toMonth.matches("\\D*")){
					selectMonth.selectByVisibleText(toMonth);
				}
			}catch(Exception e){
				System.out.println("Invalid month:"+toMonth+" selected w.r.t from date year:"+toYear);
				selectMonth.getFirstSelectedOption().getAttribute("value");
			}
			//selectMonth.selectByVisibleText(fromMonth);
			List<WebElement> dayList= driver.findElements(By.cssSelector("table.ui-datepicker-calendar td[data-handler='selectDay'] a"));
			boolean foundDay = false;
			for(WebElement selectDay:dayList){
				if(selectDay.getText().equals(toDay)){
					selectDay.click();
					foundDay = true;
					break;
				}
			}
			
			Select selectHour = new Select(driver.findElement(By.cssSelector(".ui_tpicker_hour>div>select")));
			try{
				selectHour.selectByVisibleText(hourPM);
			}catch(Exception e){
				System.out.println("Invalid Hour:"+hourPM+" w.r.t from date selection");
				//logging
				logger.error("Invalid Hour:"+hourPM+" w.r.t from date selection");
				selectHour.getFirstSelectedOption().getAttribute("value");
			}
			
			Select selectMinute = new Select(driver.findElement(By.cssSelector(".ui_tpicker_minute>div>select")));
			try{
				selectMinute.selectByVisibleText(minute);
			}catch(Exception e){
				System.out.println("Invalid Minute:"+minute+" w.r.t from date selection");
				//logging
				logger.error("Invalid Minute:"+minute+" w.r.t from date selection");
				selectMinute.getFirstSelectedOption().getAttribute("value");
			}
			
			if(foundDay == false){
				System.out.println("Invalid from day:"+toDay+" opted in calendar");
				logger.info("Invalid from day:"+toDay+" opted in calendar");
				
				driver.findElement(dateField).click();
			}
			//String dateSelected = driver.findElement(By.cssSelector(dateField)).getAttribute("value");
			//return dateSelected;
		}
		catch(Exception e){
			err1="******** Error occoured in test step *********: WebSelect - WebSelectByLInk  - not present please check..... Locator : " + dateField.toString();
			System.out.println(err1 + e.getMessage());
			//logging
			logger.error(err1 + e.getMessage());
		}
		return err1;
	}

	
	
	
	
	public String click(By dateName) throws Exception {
		try{
			err1="No";
			driver.findElement(dateName).click();  
			logMessage = "At test Step - Keyword = "+keyword+", Action = "+ "click" +", Locator : " + dateName.toString();
			System.out.println(logMessage); // Print on Console
			logger.info(logMessage);
		} catch(Exception e) {
			err1="******** Error occoured in test step *********: "+keyword+", Action = "+"click" +" is not present please check..... Locator : " + dateName.toString();
			System.out.println(err1 + e.getMessage()); // Print on Console
			logger.error(err1 + e.getMessage());
		}
		return err1;
	}
	/**
	 * Date - Keyword & selectMonth - Action
	 * @method selectMonth
	 * @description - selectMonth action to Select Month in the Calendar
	 * 
	 * @throws Exception
	 */
	public String selectMonth(By monthName,String monthValue) throws Exception {
		try{
			err1="No";
			driver.switchTo().activeElement();
			monthValue = monthValue.replace("`", "");
			Select dropdown = new Select(driver.findElement(monthName)); 
			dropdown.selectByVisibleText(monthValue);
			logMessage = "At test Step - Keyword = "+keyword+", Action = "+ "selectMonth" +", Locator : " + monthName.toString();
			System.out.println(logMessage); // Print on Console
			logger.info(logMessage);
		} catch(Exception e) {
			err1="******** Error occoured in test step *********: "+keyword+", Action = "+ "selectMonth" +" - is not present please check..... Locator : " + monthName.toString();
			System.out.println(err1 + e.getMessage()); // Print on Console
			logger.error(err1 + e.getMessage());
		}
		return err1;
	}
	/**
	 * Date - Keyword & selectYear - Action
	 * @method selectYear
	 * @description - selectYear action to Select Year in the Calendar
	 * 
	 * @throws Exception
	 */
	public String selectYear(By yearName,String yearValue) throws Exception {
		try{
			err1="No";
			driver.switchTo().activeElement();
			yearValue = yearValue.replace("`", "");
			Select dropdown = new Select(driver.findElement(yearName)); 
			dropdown.selectByVisibleText(yearValue);
			logMessage = "At test Step - Keyword = "+keyword+", Action = "+ "selectYear" +", Locator : " + yearName.toString();
			System.out.println(logMessage); // Print on Console
			logger.info(logMessage);
		} catch(Exception e) {
			err1="******** Error occoured in test step *********: "+keyword+", Action = "+ "selectYear"+" in Date Picker is not present please check..... Locator : " + yearName.toString();
			System.out.println(err1  + e.getMessage()); // Print on Console
			logger.error(err1 + e.getMessage());
		}
		return err1;
	}

	/**
	 * Date - Keyword & selectDay - Action
	 * @method selectDay
	 * @description - selectDay action to Select Day in the Calendar
	 * 
	 * @throws Exception
	 */
	public String selectDay(By dayName,String dayValue) throws Exception {

		try{
			err1="No";
			dayValue = dayValue.replace("`", "");
			/*DatePicker is a table.So navigate to each cell   
			 * If a particular cell matches value 13 then select it  
			 */  
			WebElement dateWidget = driver.findElement(By.id("ui-datepicker-div"));  
			List<WebElement> rows=dateWidget.findElements(By.tagName("tr"));  
			List <WebElement> columns=dateWidget.findElements(By.tagName("td"));  
			for (WebElement cell: columns){  
				if (cell.getText().equals(dayValue)){  
					cell.findElement(By.linkText(dayValue)).click();  
					break;  
				}  
			}
			logMessage = "At test Step - Keyword = "+keyword+", Action = "+ "selectDay"+", Locator : " + dayName.toString();
			System.out.println(logMessage); // Print on Console
			//logging
			logger.info(logMessage);
			//Log.info(logMessage); //Print on Log
		} catch(Exception e) {
			err1="******** Error occoured in test step *********: "+keyword+", Action = "+"selectDay"+" in Date Picker is not present please check..... Locator : " + dayName.toString();
			System.out.println(err1 + e.getMessage()); // Print on Console
			//logging
			logger.error(err1 + e.getMessage());
			//Log.error(err1); //Print on Log
		}
		return err1;
	}
	
	/**
	 * Date - Keyword & selectDay - Action
	 * @param dynamicVariables 
	 * @method selectDay
	 * @description - selectDay action to Select Day in the Calendar
	 * 
	 * @throws Exception
	 */
	public String dateFormat(String testData, DynamicVarRepository dynamicVariables) throws Exception {

			String formattedDate = null;
		try{
			err1 ="No";
			String[] dateInput = testData.split(",");
			
			String dateValue = dateInput[0];
			String dateFormat = dateInput[1];
			SimpleDateFormat dt = new SimpleDateFormat("yyyy-mm-dd"); 
			Date date = dt.parse(dateValue); 
			SimpleDateFormat dt1 = new SimpleDateFormat(dateFormat);
			formattedDate = dt1.format(date);
			dynamicVariables.addObjectToMap(dateValue, formattedDate);
			logMessage = "At test Step - Keyword = "+keyword+", Action = "+ "dateFormat - "+formattedDate;
			System.out.println(logMessage); // Print on Console
			//logging
			logger.info(logMessage);
			//Log.info(logMessage); //Print on Log
		} catch(Exception e) {
			err1="******** Error occoured in test step *********: "+keyword+", Action = "+"dateFormat"+" is faled please check..... " ;
			System.out.println(err1 + e.getMessage()); // Print on Console
			//logging
			logger.error(err1 + e.getMessage());
			//Log.error(err1); //Print on Log
		}
		return err1;
	}
	
	public void setMapvalue(String formattedDate){
		this.mapValue = formattedDate;
	}
	
	public String getMapvalue(){
		
		return mapValue;
	}
}
