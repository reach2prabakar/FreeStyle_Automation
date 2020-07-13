package com.client.automation.processor;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.firefox.internal.ProfilesIni;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

import com.client.automation.common.Common;
import com.client.automation.library.Global;

public class Browser {
	
	
	private static Logger logger = Logger.getLogger(Browser.class.getName());
	private WebDriver driver;
	private String browserName;
	
	public Browser(String browserName){
		this.browserName = browserName;
	}
	

	public WebDriver initBrowser(){
		
		if (Global.silentExecution.equals("Yes")){
			driver = new HtmlUnitDriver(); // For Silent execution initiate HTML Unit Driver
		}else{
			//Below Condition to initiate respective Browsers
			if (browserName.equalsIgnoreCase("FIREFOX")){
				
				FirefoxProfile ffprofile = new ProfilesIni().getProfile("default");
				ffprofile.setPreference("browser.download.folderList",2);
				ffprofile.setPreference("browser.download.manager.showWhenStarting",false);
				ffprofile.setPreference("browser.download.dir",Global.getDownloadsPath());
				ffprofile.setPreference("browser.helperApps.neverAsk.saveToDisk","text/csv,application/zip,application/octet-stream");
				DesiredCapabilities capablities =DesiredCapabilities.firefox();
			    capablities.setCapability("binary", "C:\\program files\\Mozilla Firefox\\firefox.exe");
				//driver = new FirefoxDriver(capablities);
				driver = new FirefoxDriver(ffprofile);
				
				System.out.println("Starting Firefox browser... ");
				logger.info("Starting Firefox browser... ");
				
			}else if (browserName.equalsIgnoreCase("CHROME")){
				System.setProperty("webdriver.chrome.driver",Global.getDriversFolder()+ Global.pathSeperator + "chromedriver.exe");
				ChromeOptions options = new ChromeOptions();
			    options.addArguments("start-maximized");
			    driver = new ChromeDriver(options);
				System.out.println("Starting Chrome browser... ");
				logger.info("Starting Firefox browser... ");

			}else if (browserName.equalsIgnoreCase("IE")){
				System.setProperty("webdriver.ie.driver",Global.getDriversFolder()+ Global.pathSeperator + "IEDriverServer.exe");
				driver = new InternetExplorerDriver();
				System.out.println("Starting Internet Explorer browser... ");
				logger.info("Starting Firefox browser... ");

			}else{
				System.setProperty("webdriver.chrome.driver",Global.getDriversFolder()+ Global.pathSeperator + "chromedriver.exe");
				driver = new ChromeDriver();
				System.out.println("Given browser name : " + browserName + " not supported. Starting Chrome browser");
				logger.info("Starting Firefox browser... ");
			}
		}
		
		if(!browserName.equalsIgnoreCase("CHROME")){
			driver.manage().window().maximize();
		}
		
		Global.setBrowserAndVersion(new Common().getBrowserAndVersion(driver));

		return driver;
	}



}
