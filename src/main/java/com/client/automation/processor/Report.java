package com.client.automation.processor;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;
import java.lang.reflect.Method;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.commons.io.FileUtils;

import org.apache.log4j.Logger;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import com.client.automation.library.Global;


public class Report {

	
	
	
	

	  
	
	private static Logger logger = Logger.getLogger(Report.class.getName());
	private String LogFileName;
    static String startDate;
    static String LogScreenShotFolder;
    static String summaryPath;
    static String summaryPath1;
    static String strScenario;
    static String strtestCase;
    public String browsername;
    public String huburl;
    static boolean blnFailFlag;
    public int intFailCount = 0;
    public int intPassCount = 0;
    public int intVerificationCount = 0;
    public WebDriver driver;
    public static String resultsFolder = Global.getResultsFolder();
    public static String filepath = Global.configFile;
    static LinkedHashMap<String, String[]> localtestSuiteMap = new LinkedHashMap<String, String[]>();
    public static Report rep= new Report(localtestSuiteMap);
    
    public Report(LinkedHashMap<String, String[]>testSuiteMap){
    	Report.localtestSuiteMap=testSuiteMap;
    }
	
    
    public LinkedHashMap<String, String[]> createheader() throws Exception{
    	rep.createHTMLReport("Selenium_Test", "Login Function");
    	LinkedHashMap<String, String[]> testSuiteMaplocal =rep.ReadTestscriptProperties(filepath,"notstarted");
			Set<String> key = testSuiteMaplocal.keySet();
			for (String string : key) {
				rep.updateTestLog(testSuiteMaplocal.get(string));
				//System.out.println(testSuiteMaplocal.get(string));
			}
    
    return testSuiteMaplocal;
    }
    
    public void updatereport(LinkedHashMap<String, String[]> reporttestSuiteMap) throws InvalidFormatException, IOException{
    	rep.createHTMLReport("Selenium_Test", "Login Function");
    	Set<String> key = reporttestSuiteMap.keySet();
		for (String string : key) {
			rep.updateTestLog(reporttestSuiteMap.get(string));
			//System.out.println(testSuiteMaplocal.get(string));
		}
    }
    
   
			public String createHTMLReport(String Scenario, String testCase) throws IOException, InvalidFormatException{
				strScenario = Scenario;
		        strtestCase = testCase;
		        blnFailFlag = false;
		        
		        startDate =  Global.constantTimeStamp;
		        String projectName = Global.getProjectName();
		        String reportName = "Regression ON Going Report";
		        String headingColor = "#FFE4C4";
		        String settingColor = "#FFE4C4";
		        String bodyColor = "#000000";
		        LogFileName = Global.resultlogpath;
		        //System.out.println("logfilename:"+LogFileName);
		        File testLogFile = new File(LogFileName);
		        try {
		            testLogFile.createNewFile();
		        } catch (IOException e) {
		            e.printStackTrace();
		        }
		        FileOutputStream outputStream = null;
		        PrintStream printStream = null;
		        try {
		            outputStream = new FileOutputStream(testLogFile);
		            printStream = new PrintStream(outputStream);
		        } catch (FileNotFoundException e) {
		            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
		        }
		        System.out.println();
		        String testLogHeader;
		        testLogHeader = "<html>" +
		                "<head>" +
		                "<title>" +
		                projectName + " - " + reportName +
		                "</title>" +
		                "</head>" +
		                "<body>" +
		                "<p align='center'>" +
		                "<table border='1' bordercolor='#FFE4C4' bordercolorlight='#FFFFFF' cellspacing='0' id='table1' width='1200' height='100'>" +
		                "<tr bgcolor='" + headingColor + "'>" +
		                "<td colspan='6'>" +
		                "<p align='center'><font color='" + bodyColor + "' size='4' face='Copperplate Gothic Bold'>" +
		                projectName + " - " + reportName +
		                "</font></p>" +
		                "</td>" +
		                "</tr>" +
		                "<tr bgcolor='" + settingColor + "'>" +
		                "<td colspan='3'>" +
		                "<p align='justify'><b><font color='" + bodyColor + "' size='2' face='Verdana'>" +
		                "DATE: " +  rep.getTimeStamp()+ //"		Iteration Mode: " + iterationMode +
		                "</p></font></b>" +
		                "</td>" +
		                /*"<td colspan='3'>" +
		                "<p align='justify'><b><font color='" + bodyColor + "' size='2' face='Verdana'>" +
		                "Scenario: " + Scenario + "      " + "		Testcase: " + testCase +
		                "</p></font></b>" +
		                "</td>" +*/
		                "</tr>" +
		                "<tr bgcolor='" + settingColor + "'>" +
		                "<td colspan='3'>" +
		                "<p align='justify'><b><font color='" + bodyColor + "' size='2' face='Verdana'>" +
		                "Start Iteration: " + "1" +
		                "</p></font></b>" +
		                "</td>" +
		                "<td colspan='3'>" +
		                "<p align='justify'><b><font color='" + bodyColor + "' size='2' face='Verdana'>" +
		                "End Iteration: " + "1" +
		                "</p></font></b>" +
		                "</td>" +
		                "</tr>" +
		                "<tr bgcolor='" + headingColor + "'>" +
		                "<td><b><font color='" + bodyColor + "' size='2' face='Verdana'>Project</font></b></td>" +
		                "<td><b><font color='" + bodyColor + "' size='2' face='Verdana'>Module</font></b></td>" +
		                "<td><b><font color='" + bodyColor + "' size='2' face='Verdana'>TestcaseName</font></b></td>" +
		                "<td><b><font color='" + bodyColor + "' size='2' face='Verdana'>Status</font></b></td>" +
		                "<td><b><font color='" + bodyColor + "' size='2' face='Verdana'>Duration(HH:MM:SS)</font></b></td>" +
		                "<td><b><font color='" + bodyColor + "' size='2' face='Verdana'>Remarks</font></b></td>" +
		                "</tr>";
		        printStream.println(testLogHeader);
		        printStream.close();
		        return LogFileName;
}

			
			public synchronized void updateTestLog(String[] resultupdatecalue) {
				String[] value = resultupdatecalue;
				String project = value[0];
				String stepDescription=value[1];
				String testcasename = value[2];
				String stepStatus = value[3];
				String duration = value[4];
				String errMainrpt = value[5];
				/*if(stepNameMap.get(currentFile)== null){
		        	stepNameMap.put(currentFile, 0);
		        }
		        int step=stepNameMap.get(currentFile).intValue();
		        step++;
		        stepNameMap.put(currentFile, step);*/
		        
		        Boolean takeScreenshotFailedStep = false;
		        //stepName=Integer.toString(step);
		        String bodyColor = "#FFFAFA";
		        try {
		        //	System.out.println("current html file name at updatetest log function is == "+currentFile);
		        	
		        	BufferedWriter buffered = new BufferedWriter(new FileWriter(Global.resultlogpath, true));
		        	
		        	
		            String testStepRow = "<tr bgcolor='" + bodyColor + "'>" +
		                    "<td>" +  project + "</td>" +
		                    "<td>" + stepDescription + "</td>" +
		            		"<td>" + testcasename + "</td>";
		            if (stepStatus.equalsIgnoreCase("FAIL")) {
		                /*if (takeScreenshotFailedStep) {
		                    blnFailFlag = true;
		                    System.out.println("getLogFileName() file name in fail step:"+currentFile);
		                    System.out.println("currentFile - file name in fail step:"+currentFile);
		                    
		                 //   String path = takeScreenshot(driver,currentFile, stepName);
		                    String path = null;
		                    testStepRow += "<td>" +
		                            "<a href='Screenshots\\" + path + "' target='about_blank'>" +
		                            "<font color='red'><b>" + stepStatus.toUpperCase() + "</b></font>" +
		                            "</a>" +
		                            "</td>";
		                    intFailCount = 1 + intFailCount;
		                } else {*/
		            	testStepRow += "<td bgcolor='" + "red" + "'>" +
	                            "<font color='#000000'><b>" + stepStatus.toUpperCase() + "</b></font>" +
	                            "</td>";
	                    intFailCount++;
		               // }
		               
		            } else if (stepStatus.toUpperCase().equals("PASS")) {
		                /*if (takeScreenshotPassedStep) {
		                    testStepRow += "<td>" +
		                            "<a href='..\\Screenshots\\" + "" + "' target='about_blank'>" +
		                            "<font color='green'><b>" + stepStatus.toUpperCase() + "</b></font>" +
		                            "</a>" +
		                            "</td>";
		                    intPassCount = 1 + intPassCount;
		                } else {*/
		            	testStepRow += "<td bgcolor='" + "#7CFC00" + "'>" +
	                            "<font color='#000000'><b>" + stepStatus.toUpperCase() + "</b></font>" +
	                            "<b>" +
	                            "</td>";
	                    intPassCount++;
		              //  }
		            }else if (stepStatus.toUpperCase().equals("VERIFICATION")) {
		            	if (takeScreenshotFailedStep) {
		                    blnFailFlag = true;
		                    /*System.out.println("getLogFileName() file name in fail step:"+currentFile);
		                    System.out.println("currentFile - file name in fail step:"+currentFile);*/
		                 //   String path = takeScreenshot(driver,currentFile, stepName);
		                    String path = null;
//		                    String path = takeScreenshot(currentFile, stepName);
		                    testStepRow += "<td>" +
		                            "<a href='Screenshots\\" + path + "' target='about_blank'>" +
		                            "<font color='yellow'><b>" + stepStatus.toUpperCase() + "</b></font>" +
		                            "</a>" +
		                            "</td>";
		                    intVerificationCount = 1 + intVerificationCount;
		                } else {
		                    testStepRow += "<td>" +
		                            "<font color='yellow'><b>" + stepStatus.toUpperCase() + "</b></font>" +
		                            "</td>";
		                    intVerificationCount = 1 + intVerificationCount;
		                }
		                System.out.println("The tests verification");
		            } else if (stepStatus.toUpperCase().equals("DONE")) {
		                testStepRow += "<td>" +
		                        "<font color='blue'><b>" + stepStatus.toUpperCase() + "</b></font>" +
		                        "<b>" +
		                        "</td>";
		            } else {
		                testStepRow += "<td>" +
		                        "<b>" + stepStatus.toUpperCase() + "</b>" +
		                        "</td>";
		            }

		           /* testStepRow += "<td>" +
		                    dateFormat.format(date) +
		                    "</td>" +
		                    "</tr>";*/
		           testStepRow += "<td>" +
		                    duration +
		                    "</td>" ;
		           if (stepStatus.equalsIgnoreCase("FAIL")) {
		        	   
		        		   testStepRow +=  "<td align=middle>" +
			        			   errMainrpt +
			                    "</td>" ;
		        	   
		           }else{
		        	   testStepRow +=  "<td align=middle>" +
			                    "" +
			                    "</td>" ;
		           }
		           testStepRow +=   "</tr>";
		            buffered.write(testStepRow);
		            buffered.flush();
		            buffered.close();
		           
		        } catch (IOException e) {
		            e.printStackTrace();
		        }
		       
		    }
			
			public static String takeScreenshot(WebDriver driver,String screenShotPath, String stepName) {
		        
		        System.out.println("stepName:"+stepName);
		        logger.info("screenShotPath"+screenShotPath);
		        System.out.println("stepName:"+stepName);
		        logger.info("screenShotPath"+screenShotPath);
		        screenShotPath = screenShotPath.replace(".html", stepName);
		        screenShotPath = screenShotPath.concat(".png");
		        
		        String[] aScreenShotPath = screenShotPath.split("\\\\");
		        String screenShotName = aScreenShotPath[aScreenShotPath.length - 1];
		        String newScreenshotPath = "";
		        for (int i = 0; i < aScreenShotPath.length - 1; i++) {
		            if (i == 0) {
		                newScreenshotPath = aScreenShotPath[i];
		            } else {
		                newScreenshotPath = newScreenshotPath + "\\\\" + aScreenShotPath[i];
		            }
		        }

		        System.out.print("screenShotName : " + screenShotName);
		        System.out.print("New Screenshot Path : " + newScreenshotPath + "\\Screenshots\\" + screenShotName);
		        logger.info("screenShotName : " + screenShotName);
		        logger.info("New Screenshot Path : " + newScreenshotPath + "\\Screenshots\\" + screenShotName);
		        
		        try {
		    		File screenshot = ((TakesScreenshot) driver)
		    				.getScreenshotAs(OutputType.FILE);
		    		try {
		    			FileUtils.copyFile(screenshot, new File(newScreenshotPath + "\\Screenshots\\" + screenShotName));
		    		} catch (IOException e) {
		    			
		    			e.printStackTrace();
		    		}
		            
				} catch (Exception e) {
					
					e.printStackTrace();
				}
		        
		    	return screenShotName;
		    }


			public static String getTimeStamp() {
				Date d = new Date();
				SimpleDateFormat sdf = new SimpleDateFormat("MMM-dd-yyyy_h-mm-ss");
				String date = sdf.format(d).toString();
				return date;
			}
			
			private LinkedHashMap<String, String[]> ReadTestscriptProperties(String filePath,String statuss) throws Exception{
				
				String projectFolder;
				String moduleFolder;
				String testScriptName;
				String runFlag;
				String testType;
				String entireScriptPath;
				String apiLogPath;
				Workbook obWbook;
				Sheet obWsheet;
				String status = statuss;
				Date d = new Date();
				String errMain = "";
				SimpleDateFormat sdf1 = new SimpleDateFormat("MM-dd-yy_h:mm:ss");
				String date = sdf1.format(d).toString();
				final int PROJECT_FOLDER_INDEX = 0;
				final int MODULE_FOLDER_INDEX = 1;
				final int TESTSCRIPT_NAME_INDEX = 2;
				final int RUN_INDEX = 3;
				final int TEST_TYPE_INDEX = 4;
				
				
				FileInputStream fileInputStream = new FileInputStream(filePath);
				obWbook = WorkbookFactory.create(fileInputStream);
				obWsheet =  obWbook.getSheet("Scripts");
				Iterator<Row> rowIterator = obWsheet.iterator();
				rowIterator.next(); // removing the header
				while (rowIterator.hasNext())
		        {
		            Row row = rowIterator.next();
		            if(row.getCell(0) != null){
		            	projectFolder  = row.getCell(PROJECT_FOLDER_INDEX).getStringCellValue();
		            	moduleFolder   = row.getCell(MODULE_FOLDER_INDEX).getStringCellValue();
		            	testScriptName = row.getCell(TESTSCRIPT_NAME_INDEX).getStringCellValue();
		            	runFlag        = row.getCell(RUN_INDEX).getStringCellValue();
		            	testType       = row.getCell(TEST_TYPE_INDEX).getStringCellValue();
		            	entireScriptPath = Global.datatableFolder+"\\"+ projectFolder +"\\"+moduleFolder+"\\"+ testScriptName+ Global.getExcelExtn();
		            	apiLogPath     = Global.datatableFolder+"\\"+projectFolder+"\\"+moduleFolder+"\\API_POST_DATA\\"+testScriptName+"_POST.xlsx";
		            	
		            	String[] testScriptPropeties = {projectFolder, moduleFolder, testScriptName,status,date,errMain,runFlag, 
		            									testType, entireScriptPath, apiLogPath};
		            	
		            	//addObjectToTestSuiteMap(testScriptName, testScriptPropeties);
		            	
		            	localtestSuiteMap.put(testScriptName, testScriptPropeties);
		            	
		            }
		            
		        }
				obWbook.close();
				return localtestSuiteMap;
			
				
			}
	
	
}
