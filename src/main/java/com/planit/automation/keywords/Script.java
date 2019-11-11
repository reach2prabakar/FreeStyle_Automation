package com.planit.automation.keywords;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.planit.automation.library.ContentRepository;
import com.planit.automation.library.Global;
import com.planit.automation.processor.ScriptExecutor;
import com.planit.automation.processor.TestScript;


/**
 * Button - Keyword
 * @class Button
 * @description - Button related all Actions are mentioned in this class
 * 
 * @throws Exception
 */
public class Script{
	
	private static Logger logger = Logger.getLogger(Script.class.getName());
	private String logMessage = null;
	private String keyword = "Script";
	private String err1="No";
	private WebDriver driver;
	private String scriptRelPath;
	

	public Script(WebDriver driver){
		this.driver = driver;
	}
	
	/**
	 * Button - Keyword & click - Action
	 * @param contentRepo 
	 * @method click
	 * @description - click action to Click on Button. 
	 * 
	 * @throws Exception
	 */
	public String execute(String scriptPath, ContentRepository contentRepo) throws Exception {
		try{
			err1="No";
			scriptRelPath = scriptPath;
			TestScript testscript = new TestScript(Global.getDatatableFolder() + scriptRelPath,contentRepo);
			ScriptExecutor scriptexecutor = new ScriptExecutor(driver);
			scriptexecutor.executeScript(testscript,contentRepo);
			if(!scriptexecutor.getOveralltestScriptStatus().equalsIgnoreCase("PASS")){
				err1="FAILED : Script Execution is failed.  Script : " + testscript.getTestScriptName()
						+ "\n" + scriptexecutor.getErrMain();
				System.out.println(err1); // Print on Console
				logger.error(err1);
				
			} else {

			logMessage = "At test Step - Keyword = "+keyword+", Action = "+ "execute" + " Script :" + testscript.getTestScriptName();// This is to Display on console as well in Log File
			System.out.println(logMessage); // Print on Console
			logger.info(logMessage);

			}
		} catch(Exception e) {
			err1="ERROR : Exception occurred while executing the script : " + scriptPath + "\n" + e.getMessage();//Preparing Error message when unable to perform action
			System.out.println(err1); // Print on Console
			//Log.error(err1); //Print on Log
			//logging
			logger.error(err1);
			
		}
		return err1;
	}

	/**
	 * script - Keyword & executeifqueryempty - Action
	 * @param contentRepo 
	 * @method executeifqueryempty
	 * @description - click action to Click on Button. 
	 * 
	 * @throws Exception
	 */
	public String executeifqueryempty(String testdata, ContentRepository contentRepo) throws Exception {
		String testData = null;
		String queryvariable,scriptPath;
		
		String[] inputData = testdata.split(",");
		queryvariable= inputData[0];
		scriptPath = inputData[1];
		try{
			/*String[] inputData = testdata.split(",");
			queryvariable= inputData[0];
			scriptPath = inputData[1];*/
			System.out.println("query variable is:"+inputData[0]);
			
			System.out.println("script path is:"+inputData[1]);
			
			if(queryvariable==""||queryvariable==null || queryvariable.contains("#"))
			{
			
			err1="No";
			scriptRelPath = inputData[1];
			TestScript testscript = new TestScript(Global.getDatatableFolder() + inputData[1],contentRepo);
			ScriptExecutor scriptexecutor = new ScriptExecutor(driver);
			scriptexecutor.executeScript(testscript,contentRepo);
			if(!scriptexecutor.getOveralltestScriptStatus().equalsIgnoreCase("PASS")){
				err1="FAILED : Script Execution is failed.  Script : " + testscript.getTestScriptName()
						+ "\n" + scriptexecutor.getErrMain();
				System.out.println(err1); // Print on Console
				logger.error(err1);
				
			} else {

			logMessage = "At test Step - Keyword = "+keyword+", Action = "+ "executeifqueryempty" + " Script :" + testscript.getTestScriptName();// This is to Display on console as well in Log File
			System.out.println(logMessage); // Print on Console
			logger.info(logMessage);

			}//else
			}//main if
			else{
				err1="No";
				logMessage = "At test Step - Keyword = "+keyword+", Action = "+ "executeifqueryempty" + " query is not empty :" + queryvariable;// This is to Display on console as well in Log File
				System.out.println(logMessage); // Print on Console
				logger.info(logMessage);

			}
		} catch(Exception e) {
			err1="ERROR : Exception occurred while executing the script : " + scriptPath + "\n" + e.getMessage();//Preparing Error message when unable to perform action
			System.out.println(err1); // Print on Console
			//Log.error(err1); //Print on Log
			//logging
			logger.error(err1);
			
		}
		return err1;
	}



}
