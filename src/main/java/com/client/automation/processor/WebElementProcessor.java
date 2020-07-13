package com.client.automation.processor;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class WebElementProcessor {
	private static Logger logger = Logger.getLogger(WebElementProcessor.class.getName());
	public WebDriver driver;
	
	public WebElementProcessor(WebDriver driver){
		this.driver = driver;
	}
	
	/**
	 * isPresent
	 * @method isPresent
	 * @description - isPresent to check whether object is present or not
	 * 
	 * @throws Exception
	 */
	public boolean isPresent(WebDriver driver,By by)throws Exception {
		try{
			explicitWait(1);
			driver.findElement(by);
			return true;
		}catch(Exception e){
			return false;
		}
	}
	
	
	/**
	 * explicitWait
	 * @method explicitWait
	 * @description - explicitWait to put hard wait in application
	 * 
	 * @throws Exception
	 */
	public void explicitWait(int i) throws Exception{
		//System.out.println("~~~~~~~~ Waiting for :"+(i*500)+" mSecs ~~~~~~~~");
		Thread.sleep(i*100);
	}
	
	
	/**
	 * isBy 
	 * @method isBy
	 * @description - isBy to iterated all locators and return By Object
	 * 
	 * @throws Exception
	 */
	public By isBy(WebDriver driver, String objProperty)throws Exception{
		try{
			
			if (this.isPresent(driver, By.cssSelector(objProperty))){
				
				return 	By.cssSelector(objProperty);
			
			} else if (this.isPresent(driver, By.id(objProperty))){
			
				return 	By.id(objProperty);	
			
			} else if(this.isPresent(driver, By.name(objProperty))){
				
				return 	By.name(objProperty);
			
			} else if (this.isPresent(driver, By.linkText(objProperty))){
			
				return 	By.linkText(objProperty);
			
			} else if (this.isPresent(driver, By.xpath(objProperty))){
			
				return 	By.xpath(objProperty);
			
			} else if (this.isPresent(driver, By.className(objProperty))){
			
				return By.className(objProperty);
			
			} else if (this.isPresent(driver, By.tagName(objProperty))){
			
				return By.tagName(objProperty);
			
			} else {
			
				return null;
			}
			
			
		} catch (Exception e){	
			System.err.println("Exception: "+e.toString());
			System.out.println("Element verification failed: Locator::");
			//logging
			logger.error("Exception: "+e.toString());
			logger.error("Element verification failed: Locator::");
		}
		
		return null;
	}// isEnabled
	
	
	/**
	 *isEnabled
	 * @method isEnabled
	 * @description - isEnabled to check whether Object is enabled
	 * 
	 * @throws Exception
	 */
	public boolean isEnabled(WebDriver driver, By by)throws Exception{
		try{
			new WebDriverWait(driver, 20).until(ExpectedConditions.visibilityOfElementLocated(by));	
			driver.findElement(by);
			return driver.findElement(by).isEnabled();
		} catch (Exception e)
		{	System.err.println("Exception: "+e.toString());
		System.out.println("WebElement verification failed: Locator::"+ by.toString());
		//logging
		logger.error("Exception: "+e.toString());
		logger.error("WebElement verification failed: Locator::"+ by.toString());
		return false;}
	}

}
