/**
 * MAIN
 * @description - Driver File which reads test steps from excel sheet and converts into Selenium executable format and execute the test steps
 * 
 * @class MAIN
 * 
 * @author Prabs 
 * @versions History
 * Date			Author		Comments
 * 31/10/2019	Prabs		Basic File added with all controls
 * 
 * 
 * 
 */

package com.client.automation.library;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Date;
import java.util.LinkedHashMap;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

import org.apache.log4j.Logger;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.openqa.selenium.WebDriver;
import org.quartz.SchedulerException;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.client.automation.common.Common;
import com.client.automation.processor.Report;
import com.client.automation.processor.ScriptExecutor;
import com.client.automation.processor.TestResultsProcessor;
import com.client.automation.processor.TestScript;
import com.client.automation.processor.TestSuite;

public class DriverScript {

	private static Logger logger = Logger.getLogger(DriverScript.class.getName());
	public static long suiteStart = new Date().getTime();
	public ContentRepository contentRepo ;
	public ObjectRepository objectrepo;
	Common genericMethods;
	TestSuite testSuite;
	TestResultsProcessor testResultProcessor;
	LinkedHashMap<String, String[]> reporttestSuiteMap = new LinkedHashMap<String, String[]>();
	Report rpt;
	boolean job=false;

	@Parameters({"AutomationBrowser", "AutomationEnvironment", "Client", "Testsuite", "EmailResipents"})
	@BeforeSuite
	public void intializeReport(@Optional("") String browser, @Optional("") String environment, 
			@Optional("") String client, @Optional("") String testsuite, 
			@Optional("") String emailResipents) throws Exception{
		genericMethods 				= new Common();
		testResultProcessor			= new TestResultsProcessor();
		Global.readProperties();
		genericMethods.updateGlobalValues(client, browser, environment, testsuite, emailResipents);
		genericMethods.copyResultTemplate();	
		contentRepo 		= new ContentRepository(Global.getContentRepoFile());
		objectrepo			= new ObjectRepository(Global.getObjectRepoFile());
		if (Global.triggerjob.equalsIgnoreCase("Yes")){ //Send email if Marked Yes
			rpt = new Report(reporttestSuiteMap);
			rpt.createheader();
			genericMethods.triggerjob();
			job=true;
		}
		//genericMethods.sendEmailevery15min();
	}


	@BeforeTest
	public void initialize() throws Exception{

		logger.info("Initializing......  ");
	}

	/**
	 * executescript
	 * @throws IOException 
	 * @method executescript
	 * @description - This is the main method which will be driver method to execute entire suits
	 * 
	 * @throws Exception
	 */


	@Test(dataProvider = "testScripts" , timeOut = 1800000)
	public void executescript(String scriptName, String[] testScriptProperties) throws Exception {
		System.out.println("Started Executing the test script " + scriptName );
		logger.info("Started Executing the test script " + scriptName );

		String overalltestScriptStatus;
		String errMain;
		String errMainrpt;
		String duration;
		WebDriver driver 				= null; 
		TestScript testScript 			= new TestScript(testScriptProperties,contentRepo,objectrepo);
		String scriptLogFile			= Global.getScriptLogFile(scriptName);

		//genericMethods.copyDataTableFile(testScript.getEntireScriptPath(),scriptName); // Path of Data Sheet for each test Case
		genericMethods.printStartOfExecution(scriptName);
		logger.info("Log File for this Test Cases Stored in - " + scriptLogFile);
		System.out.println("Log File for this Test Cases Stored in - " + scriptLogFile);

		// Executing the Script
		ScriptExecutor scriptExecutor = new ScriptExecutor(driver);
		scriptExecutor.executeScript(testScript,contentRepo);
		// Get Execution status
		overalltestScriptStatus = scriptExecutor.getOveralltestScriptStatus();
		errMain = scriptExecutor.getErrMain();
		errMainrpt = scriptExecutor.getErrMainforrpt();
		duration = scriptExecutor.getDuration();
		driver	= scriptExecutor.getDriver();
		
		if (Global.driverQuit.equalsIgnoreCase("Yes") && driver!=null){ //For Debugging purposed used this flag, when marked NO, Browser will not be cosed
			driver.quit();
		}


		
		genericMethods.printEndOfExecution(scriptName);
		if(job){
		String [] hashmapvalues = reporttestSuiteMap.get(scriptName);
		String status = hashmapvalues[3];
		String errormsg="";
		if(status.equalsIgnoreCase("notstarted")){
			hashmapvalues[3] = overalltestScriptStatus.toLowerCase();
			hashmapvalues[4] = duration;
			System.out.println("hashmapvalues[5] "+hashmapvalues[5]+":bfr assign");
			if(hashmapvalues[5].isEmpty()||hashmapvalues[5].equals("")){
			hashmapvalues[5] = errMainrpt;
			}
			System.out.println("hashmapvalues[5] "+hashmapvalues[5]+":aftr assign");
		//	errormsg = errMainrpt;
		}
		
		reporttestSuiteMap.put(scriptName,hashmapvalues);
		
		rpt.updatereport(reporttestSuiteMap);
		}
		
		try{

			if (overalltestScriptStatus.equalsIgnoreCase("FAIL")){ // Update Test Result Sheet with Overall status on test steps.
				testResultProcessor.setResultCell(testScript, scriptLogFile, "Fail", duration, errMain);
				genericMethods.updateFailureTestSuite(scriptName, testSuite);
				Assert.assertTrue(false, "Test script " + scriptName + "  Failed!! " + "\n" + errMain);
			}else{
				testResultProcessor.setResultCell(testScript, scriptLogFile, "Pass", duration, "");
				Assert.assertTrue(true, "Test script " + scriptName + "  Passed " + "\n");
			}
		}catch(FileNotFoundException e){
			logger.error("filenot found exception " + e);
		}catch (Exception e) {
			logger.error("Exception " + e);
		}

		
		

	}


	@AfterMethod
	public void closebrowserexcel() throws IOException{
		/*Runtime rt = Runtime.getRuntime();
		  rt.exec(new String[]{"cmd.exe","/c","taskkill /F /IM fireFoxdriver.exe"});
		  rt.exec(new String[]{"cmd.exe","/c","taskkill /F /IM fireFox.exe"});
		  rt.exec(new String[]{"cmd.exe","/c","taskkill /F /IM Chromedriver.exe"});
		  rt.exec(new String[]{"cmd.exe","/c","taskkill /F /IM chrome.exe"});
		  rt.exec(new String[]{"cmd.exe","/c","taskkill /F /IM excel.exe"});*/
	}


	@AfterTest
	public void sendReport() throws InvalidFormatException, IOException, AddressException, MessagingException, SchedulerException, Exception{
		long suiteStop = 0;
		suiteStop = new Date().getTime();
		long SuiteDuration = (suiteStop-suiteStart)/ 1000; //Get overall execution time
		String Duration= genericMethods.getTime(SuiteDuration); //Get time
		testResultProcessor.createHTMLReport(Duration, Global.constantTimeStamp, Global.constantTimeStamp); //Prepare HTML report
		if (Global.sendEmail.equalsIgnoreCase("Yes")){ //Send email if Marked Yes
			//genericMethods.sendEmail();
		}

		genericMethods.printExecutionOver();

		String hour = genericMethods.getcurrenthour();
		int hourtime = Integer.parseInt(hour);
		System.out.println("currentitme "+hourtime);
		logger.info("currentitme "+hourtime);
		//genericMethods.stoptrigger(hourtime);
		//genericMethods.sendEmailevery15min();

	}

	@AfterSuite
	public void exit() throws AddressException, MessagingException{
		if (Global.triggerjob.equalsIgnoreCase("Yes")){
			genericMethods.sendEmailevery15min();
			Global.triggerjob = "No";

		}
	}

	@DataProvider (parallel = true)
	public Object[][] testScripts() throws Exception {

		testSuite = new TestSuite(Global.getConfigFile());
		testSuite.creatFailedTestSuite();
		return testSuite.getDataProviderObject();
	}



} //Closing Main classt
