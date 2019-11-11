package com.planit.automation.keywords;

import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.io.File;
import java.util.List;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.planit.automation.library.Global;


/**
 * FileUpload - Keyword
 * @class FileUpload
 * @description - FileUpload related all Actions are mentioned in this class
 * @throws Exception
 */
public class FileUpload{
	
	private static Logger logger = Logger.getLogger(FileUpload.class.getName());
	public String logMessage = null;
	private String keyword = "FileUpload";
	public String err1="No";
	public WebDriver driver;
	public static volatile boolean fileUploadInProgress = false;

	public FileUpload(WebDriver driver){
		this.driver = driver;
	}
	
	/**
	 * FileUpload - Keyword & click - Action
	 * @method click
	 * @description - click action to Select File to Upload.
	 * 
	 * @throws Exception
	 */
	public String click(By btnName,String filePath) throws Exception {
		try{
			err1="No";
			driver.findElement(btnName).click();
			StringSelection stringSelection = new StringSelection(filePath);
			Toolkit.getDefaultToolkit().getSystemClipboard().setContents(stringSelection, null);
			//native key strokes for CTRL, V and ENTER keys and Steps for Uploading file..
			Robot robot = new Robot();
			Thread.sleep(3000);
			robot.keyPress(KeyEvent.VK_CONTROL);
			robot.keyPress(KeyEvent.VK_V);
			robot.keyRelease(KeyEvent.VK_V);
			robot.keyRelease(KeyEvent.VK_CONTROL);
			Thread.sleep(3000);
			/*robot.keyPress(KeyEvent.VK_ENTER);
			robot.keyRelease(KeyEvent.VK_ENTER);*/
			robot.keyPress(KeyEvent.VK_ALT);
			robot.keyPress(KeyEvent.VK_O);
			robot.keyRelease(KeyEvent.VK_O);
			robot.keyRelease(KeyEvent.VK_ALT);
			Thread.sleep(2000);
			//robot.keyRelease(KeyEvent.VK_ENTER);
			//Thread.sleep(1000);

			logMessage = "At test Step - Keyword = "+keyword+", Action = "+"click"+" , Fileuploaded = "+ filePath +", Locator = " + btnName; // This is to Display on console as well in Log File
			System.out.println(logMessage); // Print on Console
			//logging
			logger.info(logMessage);
			//Log.info(logMessage); //Print on Log //Print on Log
		} catch(Exception e) {
			err1="Failure not present please check..... Locator : " + btnName; //Preparing Error message when unable to perform action
			System.out.println(err1); // Print on Console
			//logging
			logger.info(err1);
			//Log.error(err1); //Print on Log
		}
		return err1;
	}
	
	
	/**
	 * FileUpload - Keyword & upload - Action
	 * @method click
	 * @description - click action to Select File to Upload.
	 * 
	 * @throws Exception
	 */
	public String upload(By btnName,String filePath) throws Exception {
		try{
			err1="No";
			while (fileUploadInProgress){
				System.out.println("Waiting for other browser");
				//logging
				logger.info("Waiting for other browser");
				Thread.sleep(1000);
			}
			
			if(!fileUploadInProgress){
				fileUploadInProgress = true;
				driver.findElement(btnName).click();
				Thread.sleep(2000);
				String autoItCmdLineExecutor = Global.getToolsFolder() + "\\fileUpload.exe "+ "\"" + filePath + "\"";
				Runtime.getRuntime().exec(autoItCmdLineExecutor);
				

				Thread.sleep(8000);
				if(isFileUploadSuceessful(filePath)){

					logMessage = "At test Step - Keyword = "+keyword+", Action = "+"upload"+" , Fileuploaded = "+ filePath +", Locator = " + btnName; // This is to Display on console as well in Log File
					System.out.println(logMessage); // Print on Console
					//logging
					logger.info(logMessage);

				} else {
					err1="Failure upload is Failed!! Keyword : " + keyword + " Action : " + "upload"   +" , Fileuploaded = "+ filePath +", Locator = " + btnName; // This is to Display on console as well in Log File
					System.out.println(err1);
					//logging
					logger.info(logMessage);
				}
			}	
			
			//Log.info(logMessage); //Print on Log //Print on Log
		} catch(Exception e) {
			err1="Failure not present please check..... Locator : " + btnName; //Preparing Error message when unable to perform action
			System.out.println(e.getMessage());
			System.out.println(err1); // Print on Console
			//logging
			logger.info(e.getMessage());
			logger.error(err1);
			
			//Log.error(err1); //Print on Log
		}
		fileUploadInProgress = false;
		return err1;
	}

	private boolean isFileUploadSuceessful(String filePath) {
		boolean fileUploadSuccessful = true;
		if (Global.getClientName().equalsIgnoreCase("KLARNA")){
			File f = new File(filePath);
			String fileName = f.getName();

			List<WebElement> filesUploaded= driver.findElements(By.cssSelector(".remove"));
			for (WebElement fileUploaded : filesUploaded){
				if(fileUploaded.getText().contains(fileName)){
					fileUploadSuccessful = true;
					break;
				}else {
					fileUploadSuccessful = false;
				}

			}
		}
		return fileUploadSuccessful;
		
	}
}
