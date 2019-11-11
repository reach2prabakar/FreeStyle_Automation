package com.planit.automation.keywords;

import java.util.List;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

public class TopNav {

	private static Logger logger = Logger.getLogger(TopNav.class.getName());
	private WebDriver driver;
	public String logMessage = null;
	private String keyword = "TopNav";
	public String err1="No";
	
	public TopNav(WebDriver driver) {
		this.driver = driver;
	}
	
	public String hoverNclickOnSubNavLink(String Objproperty,String searchvalues) throws InterruptedException {
		 String action = "hoverNclickOnSubNavLink";
				 
	try{
		err1="No";
		System.out.println("object :"+Objproperty);
		System.out.println("searchvalues :"+searchvalues);
		String [] navigation = searchvalues.split(",");
		String topNavLinkText = navigation[0];
		String subNavLinkTxt = navigation[1];
		boolean subNavLinkFound = false;
		List<WebElement> listTopNavLnks =null;
		try{
			if(Objproperty.contains("//")){
			listTopNavLnks = driver.findElements(By.xpath(Objproperty+"//li"));
			}else{
			listTopNavLnks = driver.findElements(By.cssSelector(Objproperty+">li"));
			}
		}
		catch(Exception e){
			err1 = "exception in TopNav ->selecting the subnavigation link";
			 logMessage = "At test Step - Keyword = "+keyword+", Action = "+ action +" , Locator = " + Objproperty.toString()+Thread.currentThread().getStackTrace()[1].getLineNumber();
			 System.out.println(logMessage); // Print on Console
		}
		for (int lnk = 1; lnk <= listTopNavLnks.size(); lnk++) {

			WebElement topNavLnk = null;
			try{
			topNavLnk = driver
					.findElement(By.cssSelector(Objproperty+">li:nth-of-type("+lnk+")"));
			}
			catch(Exception e){
				err1 = "exception in TopNav ->selecting the subnavigation link";
				logMessage = "At test Step - Keyword = "+keyword+", Action = "+ action +" , Locator = " + Objproperty.toString()+Thread.currentThread().getStackTrace()[1].getLineNumber();
				 System.out.println(logMessage); 
			}
			String topNavLnkTxt = topNavLnk.getText();
			System.out.println("top nav lnk text:" + topNavLnkTxt);
			if (!topNavLnkTxt.isEmpty()) {
				if (topNavLnkTxt.trim().toUpperCase()
						.contains(topNavLinkText.trim().toUpperCase())) {
					Actions builder = new Actions(driver);
					builder.moveToElement(
							driver.findElement(By.linkText(topNavLinkText)))
							.build().perform();
					
					List<WebElement> listSubNav =null;
					try{
					listSubNav = driver
							.findElements(By.cssSelector(Objproperty+">li:nth-of-type("+lnk+").submenu>ul>li"));
					}catch(Exception e){
						err1 = "exception in TopNav ->selecting the subnavigation link";
						logMessage = "At test Step - Keyword = "+keyword+", Action = "+ action +" , Locator = " + Objproperty.toString()+Thread.currentThread().getStackTrace()[1].getLineNumber();
						 System.out.println(logMessage); 
					}
					System.out.println("count of sub nav links:"+ listSubNav.size());

					for (int i = 1; i <= listSubNav.size(); i++) {
						builder.moveToElement(
								driver.findElement(By.linkText(topNavLinkText)))
								.build().perform();
						// builder.moveToElement(driver.findElement(By.linkText(topNavLinkText))).build().perform();
						boolean optionsdisplayed = driver.findElement(By.cssSelector(Objproperty+">li:nth-of-type("+lnk+").submenu>ul>li:nth-of-type("+i+")>a")).isDisplayed();
						System.out.println("optionsdisplayed :"+optionsdisplayed );
						WebElement subNavLnk = driver.findElement(By.cssSelector(Objproperty+">li:nth-of-type("+lnk+").submenu>ul>li:nth-of-type("+i+")>a"));
						/*System.out.println(Objproperty+">li:nth-of-type("+lnk+").submenu>ul>li:nth-of-type("+i+")>a");
						System.out.println("subNavLnk :"+subNavLnk);*/
						String subNavLnkTxt = subNavLnk.getText();
						System.out.println("sub nav lnk text:" + subNavLnkTxt);
						if (subNavLnkTxt.trim().toUpperCase().contains(subNavLinkTxt.trim().toUpperCase())) {
							 
							try{
								subNavLnk.click();
							//driver.findElement(By.cssSelector(Objproperty+">li:nth-of-type("+lnk+").submenu>ul>li:nth-of-type("+i+")>a")).click();
							//	driver.findElement(By.linkText(subNavLinkTxt)).click();
							}catch(Exception e){
								err1 = "exception in TopNav ->selecting the subnavigation link";
								logMessage = "At test Step - Keyword = "+keyword+", Action = "+ action +" , Locator = " + Objproperty.toString()+Thread.currentThread().getStackTrace()[1].getLineNumber();
								 System.out.println(logMessage); 
							}
							subNavLinkFound = true;
							break;
						}
					}

					builder.release();
					new Wait(driver).waitForPageLoad();
				}

			}

			if (subNavLinkFound) {
				break;
			}
		}

	}catch(Exception e){
		
		logMessage = "At test Step - Keyword = "+keyword+", Action = "+ action +" , Locator = " + Objproperty.toString();
		System.out.println(logMessage);
		err1 = "exception in TopNav ->selecting the subnavigation link";
		System.out.println(e);
	}
	return err1;
	}
}
