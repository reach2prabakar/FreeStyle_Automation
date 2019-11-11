package com.planit.automation.keywords;

import org.apache.log4j.Logger;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.planit.automation.library.Global;


public class Wait {

	
	private static Logger logger = Logger.getLogger(Wait.class.getName());
	public WebDriver driver;
	
	public Wait(WebDriver driver){
		this.driver = driver;
	}

	public void threadwait(String waittime) throws Exception{
		//System.out.println("~~~~~~~~ Waiting for :"+(i*500)+" mSecs ~~~~~~~~");
		int waitingtime =  Integer.parseInt(waittime);
		Thread.sleep(waitingtime*100);
	}
	
	
	public void explicitWait(String objproperty) throws Exception{
		System.out.println(objproperty);
		/*WebDriverWait wait = new WebDriverWait(driver, 30);
		wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(objproperty)));*/
	}
	
	public void explicitWait(By objproperty) throws Exception{
		System.out.println(objproperty);
		WebDriverWait wait = new WebDriverWait(driver, 30);
		wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(objproperty));
	} 
	
	public void explicitWaitbyvisible(By objectLocator) {
		WebDriverWait wait = new WebDriverWait(driver, 30);
		wait.until(ExpectedConditions.visibilityOfElementLocated(objectLocator));
		
	}
	
	public void waitForPageLoad() throws Exception {
	 try{
		if(driver != null){
			if(jstrue()){
			(new WebDriverWait(driver, 10)).until(new ExpectedCondition<Boolean>() {
				public Boolean apply(WebDriver d) {
					JavascriptExecutor js = (JavascriptExecutor) d;
					return ((Boolean) js.executeScript("return jQuery.active == 0") && (Boolean)js.executeScript("return document.readyState").equals("complete"));
				}
			});
			}
			if(Global.getBrowser().equalsIgnoreCase("CHROME")){
				Thread.sleep(200);
			}
			else if(Global.getBrowser().equalsIgnoreCase("IE")){
				Thread.sleep(300);
			}
		} 
	 } catch (Exception e){
		// e.printStackTrace();
		 if(e.getMessage().contains("unexpected alert open")){
			 System.out.println("!Unexpected Alert opened and trying to accept the alert");
			 Alert alert = driver.switchTo().alert();
			 alert.accept();
		 }
		 System.out.println("Warning : Exception occured while waiting for the page load : " + e.getMessage());
		 logger.warn("Warning : Exception occured while waiting for the page load : " + e.getMessage());
	 }
		
	}

	public boolean jstrue(){
		JavascriptExecutor js = (JavascriptExecutor) driver;
		boolean returnvalue =  ((Boolean) js.executeScript("return jQuery.active > 0"));
		if(returnvalue){
		JavascriptExecutor js1 = (JavascriptExecutor) driver;
		boolean truevalue =  ((Boolean) js1.executeScript("return jQuery.active == 0") && (Boolean)js1.executeScript("return document.readyState").equals("complete"));
		System.out.println("js completed :"+truevalue);
		if(truevalue){
			returnvalue = false;
		}
		}
		//System.out.println("js enabled: "+returnvalue);
		return returnvalue;
	}
	public void waitForPageLoad(int timeoutInSeconds) throws Exception {
		 try{
			if(driver != null){
				(new WebDriverWait(driver, timeoutInSeconds)).until(new ExpectedCondition<Boolean>() {
					public Boolean apply(WebDriver d) {
						JavascriptExecutor js = (JavascriptExecutor) d;
						return ((Boolean) js.executeScript("return jQuery.active == 0") && (Boolean)js.executeScript("return document.readyState").equals("complete"));
					}
				});
				if(Global.getBrowser().equalsIgnoreCase("CHROME")){
					Thread.sleep(300);
				}
				else if(Global.getBrowser().equalsIgnoreCase("IE")){
					Thread.sleep(300);
				}
			} 
		 } catch (Exception e){
			 if(e.getMessage().contains("unexpected alert open")){
				 System.out.println("!Unexpected Alert opened and trying to accept the alert");
				 Alert alert = driver.switchTo().alert();
				 alert.accept();
			 }
			 System.out.println("Warning : Exception occured while waiting for the page load : " + e.getMessage());
			 logger.warn("Warning : Exception occured while waiting for the page load : " + e.getMessage());
		 }
			
		}

	


}
