package com.planit.automation.processor;

import java.text.ParseException;
import java.util.Date;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;

import com.planit.automation.common.Common;
import com.planit.automation.library.ContentRepository;
import com.planit.automation.library.DynamicVarRepository;
import com.planit.automation.library.Global;

public class ScriptExecutor {

	private static Logger logger = Logger.getLogger(ScriptExecutor.class.getName());
	private TestScript testScript;
	private Common genericMethods;
	private WebDriver driver; 
	private Date starttime;
	private Date endtime;
	private String duration;
	private DynamicVarRepository dynamicVariables;
	private String onFailCloseApplication;
	private String overalltestScriptStatus;
	private String errMain;
	private String errMainforrpt;
	private String statusTestStep;
	private String failureOfTestStep;
	private boolean apiExcelAppendFlag;
	private String scriptName;
	private String scriptLogFile;
	private TestStep teststep;
	
	public ScriptExecutor(WebDriver driver){
		this.driver = driver;
	}

	public void executeScript(TestScript testscript, ContentRepository contentRepo) throws InterruptedException, ParseException, Exception{
		logger.info("Started Executing the test script " + testscript.getTestScriptName() );
		
		//driver 					= null; 
		testScript 				= testscript;
		genericMethods 			= new Common();
		starttime 				= new Date();
		dynamicVariables 		= new DynamicVarRepository();
		onFailCloseApplication	= Global.getOnFailCloseApplication();
		overalltestScriptStatus	= null;
		errMain 				= null;
		errMainforrpt			= null;
		statusTestStep 			= null;
		failureOfTestStep 		= "No";
		apiExcelAppendFlag		= false;
		scriptName				= testScript.getTestScriptName();
		scriptLogFile			= testScript.getTestScriptLogFile();

		try{
			genericMethods.copyDataTableFile(testScript.getEntireScriptPath(),scriptName); // Path of Data Sheet for each test Case
			//genericMethods.printStartOfExecution(scriptName);

			//logger.info("Log File for this Test Cases Stored in - " + scriptLogFile);
			//System.out.println("Log File for this Test Cases Stored in - " + scriptLogFile);

			for (int testScriptRowIndex = 2; testScriptRowIndex <= testScript.getRowsCount(); testScriptRowIndex++){

				teststep = testScript.getTeststep(testScriptRowIndex);

				String desc					= teststep.getActionDescription();
				String keyword				= teststep.getKeyword();
				String action				= teststep.getAction();
				String flowType 			= teststep.getFlow();
				//String jsonResponseExpected	= teststep.getJsonResponse();
				String objectProperty 		= teststep.getObjectProperty();
				boolean skipTestStep		= Boolean.valueOf(teststep.getSkipTestStepFlag());
				Integer resultCell 			= TestStep.RESULT_CELL_INDEX;

				//Preparing default Error message for the entire execution of Test Cases
				errMain = keyword + " not present please check..... For Keyword " +keyword + " - Action - " + action + ".. Locator : " + objectProperty;
				if (!keyword.isEmpty() && !skipTestStep){

					if (keyword.equalsIgnoreCase("API")){
						switch (action){
						case "post":
							errMain = "API processor scripts to be updated";
							break;
						
						}
						if(errMain.contains("FAILED")){
							failureOfTestStep="FAILED";
							if(flowType.equalsIgnoreCase("Negative")){
								failureOfTestStep = "No";
							}
						}
						System.out.println("failureOfTestStep :"+failureOfTestStep);
						statusTestStep = new TestResultsProcessor().testStepResult(failureOfTestStep, scriptLogFile, testScriptRowIndex, resultCell, scriptName, errMain);
						apiExcelAppendFlag=true;
					}else {
						if(!desc.equalsIgnoreCase("browsernotneeded")){
						driver 			= (!keyword.equalsIgnoreCase("EMAIL") && driver == null) ? new Browser(Global.getBrowser()).initBrowser() : driver;
						}
						KeywordProcessor keywordProcessor = new KeywordProcessor(driver);//, scriptLogFile, dynamicVariables);
						keywordProcessor.setDynamicVariables(dynamicVariables);
						keywordProcessor.execute(teststep,contentRepo);
						failureOfTestStep = keywordProcessor.getFailureOfTestStep();
						errMain			= keywordProcessor.getErrMain();
						setErrMainforrpt(errMain);
						statusTestStep = new TestResultsProcessor().testStepResult(failureOfTestStep, scriptLogFile, testScriptRowIndex, resultCell, scriptName, errMain);
					}
					
					if(flowType.equalsIgnoreCase("OPTIONAL")){
						errMain = ">>> Test Step : " + (testScriptRowIndex+1) + " is Optional and Status is : " + statusTestStep + "!! <<<";
						System.out.println(errMain);
						logger.info(errMain);
						overalltestScriptStatus = "pass";
						
					} else if (statusTestStep.equalsIgnoreCase("FAIL")){ // Fail entire test if individual Test step is failed
						overalltestScriptStatus = "FAIL";
						if (onFailCloseApplication.equalsIgnoreCase("Yes") && Global.driverQuit.equalsIgnoreCase("Yes")){
							Global.driverQuit="Yes";
						}else{
							Global.driverQuit="No";//overwrite not to close the Driver
						}

						break; //Stopping execution of Test Case
					}else{
						overalltestScriptStatus = "pass";
					}


				} else if (skipTestStep){  // Skip the test if the step is striked out
					errMain = ">>> Test Step : " + (testScriptRowIndex+1) + " is Skipped!! <<<";
					System.out.println(errMain);
					logger.info(errMain);
					overalltestScriptStatus = "pass";

				}//Closing if condition on Keyword not = null	

			} //Closing loop of iterating multiple test data

			//}//Iterating all test cases defined in config file excel
		}catch(Exception e){
			e.printStackTrace();
			errMain = e.getMessage();
			statusTestStep = "FAIL";
			overalltestScriptStatus = "FAIL";

		}
		endtime = new Date();
		duration = genericMethods.timeduration(starttime, endtime);
		genericMethods.printDynamicVarsToLog(dynamicVariables);
	
	}
	
	public String getOveralltestScriptStatus() {
		return overalltestScriptStatus;
	}

	public String getErrMain() {
		return errMain;
	}

	public String getErrMainforrpt() {
		return errMainforrpt;
	}
	
	public String getStatusTestStep() {
		return statusTestStep;
	}

	public String getFailureOfTestStep() {
		return failureOfTestStep;
	}

	public void setOveralltestScriptStatus(String overalltestScriptStatus) {
		this.overalltestScriptStatus = overalltestScriptStatus;
	}

	public void setErrMain(String errMain) {
		this.errMain = errMain;
	}

	public void setErrMainforrpt(String errMain) {
		this.errMainforrpt = errMain;
	}
	public void setStatusTestStep(String statusTestStep) {
		this.statusTestStep = statusTestStep;
	}

	public void setFailureOfTestStep(String failureOfTestStep) {
		this.failureOfTestStep = failureOfTestStep;
	}

	public Date getStarttime() {
		return starttime;
	}

	public Date getEndtime() {
		return endtime;
	}

	public String getDuration() {
		return duration;
	}

	public void setStarttime(Date starttime) {
		this.starttime = starttime;
	}

	public void setEndtime(Date endtime) {
		this.endtime = endtime;
	}

	public void setDuration(String duration) {
		this.duration = duration;
	}
	
	public WebDriver getDriver() {
		return driver;
	}

	public void setDriver(WebDriver driver) {
		this.driver = driver;
	}



}
