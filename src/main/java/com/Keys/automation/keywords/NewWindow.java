package com.planit.automation.keywords;

import java.util.ArrayList;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;

public class NewWindow{
	
	private static Logger logger = Logger.getLogger(NewWindow.class.getName());
	public String logMessage = null;
	private String keyword = "NewWindow";
	public String err1="No";
	public WebDriver driver;

	public NewWindow(WebDriver driver){
		this.driver = driver;
	}
	
	public String openWindow(int waitTime, String currentTab) throws Exception {
		try{
			err1="No";

			/*Thread.sleep(waitTime);
			currentTab = driver.getWindowHandle();
			System.out.println("currenthandle "+currentTab);
			ArrayList<String> newTabs = new ArrayList<String> (driver.getWindowHandles());
			newTabs.remove(currentTab);

			//Switch to new tab
			driver.switchTo().window(newTabs.get(0));
			currentTab = driver.getWindowHandle();
			System.out.println("new handles "+ currentTab);

			//System.out.println("label of the new window    "+driver.findElement(By.id("heading")).getText());
			//Log.info(err1);
*/			return err1;

		} catch(Exception e) {
			err1 = "At test Step - Keyword = Alerts, Action = pagealerts ,, alert is not present";
			System.out.println(err1);
			//logging
			logger.error(err1);
			//Log.error(err1);
		}
		return err1;
	}
	public String closeWindow(int waitTime) throws Exception {
		try{
			err1="No";

			Thread.sleep(waitTime);
			ArrayList<String> closetab = new ArrayList<String>(driver.getWindowHandles());
			int size = closetab.size();
			int size1 = closetab.size();
			System.out.println("total window size is :"+ size);
			//logging
			logger.info("total window size is :"+ size);
			if(size>1){
				for(int i=size1;i>1;i--){
					driver.switchTo().window(closetab.get(size-1));
					driver.close();
					closetab.remove(size-1);
					System.out.println(closetab.get(size-2));
					size = size-1;
				}
			}
			driver.switchTo().window(closetab.get(0));
			logMessage = "At test Step - Keyword = NewWindow , Action = "+ "closeWindow , all other Window is closed and driver is switched to first window";
			System.out.println(logMessage); // Print on Console
			//logging
			logger.info(logMessage);
			return err1;

		} catch(Exception e) {
			err1 = "At test Step - Keyword = NewWindow, Action = closeWindow ,, 2nd window is not closed ";
			System.out.println(err1);
			//logging
			logger.error(err1);
			//Log.error(err1);
		}
		return err1;
	} 

}
