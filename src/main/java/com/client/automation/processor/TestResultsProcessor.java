package com.client.automation.processor;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Hyperlink;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import com.client.automation.library.Global;

public class TestResultsProcessor {
	private static Logger logger = Logger.getLogger(TestResultsProcessor.class.getName());
	private TestResultExcelReport testResult;

	public TestResultsProcessor() {
		
	}


	/**
	 * Updating Test Step Results
	 * @method testStepResult
	 * @description - testStepResult action to updated test step results
	 * 
	 * @throws Exception
	 */

	public String testStepResult(String failure, String globalScriptName, int j, Integer resultCell,String scrptNm, String errMain )throws Exception{
		String statusTestStep = "FAIL";
		
		//Common genericMethods = new Common();
		try{
			if (!failure.equalsIgnoreCase("No")){
				System.out.println(errMain);
				//logging
				logger.info(errMain);
				updateStatus(globalScriptName,j,resultCell,"Fail");
				if (Global.fromAPI_NoScreenShot==null){
					String screenShotLink = snapShot(scrptNm +".jpg",Global.screenShotPath);
					Thread.sleep(1000);
					updateScreenShot(Global.getLogPath() + Global.pathSeperator + scrptNm + Global.getExcelExtn(), 1,resultCell.intValue()+1, screenShotLink);
				}
				statusTestStep = "FAIL";
			}else{
				updateStatus(globalScriptName,j,resultCell,"Pass");
				statusTestStep = "PASS";
			}
		} catch (Exception e){	
			System.err.println("testStepResult Function - Unable to Update Status : "+e.toString());
			//logging
			logger.error("testStepResult Function - Unable to Update Status : "+e.toString());
			statusTestStep = "FAIL";
		}		
		return statusTestStep; // Return status
	}
	

	public void updateStatus(String filePath, int row, int col,String result) throws Exception {
		FileInputStream fileInputStream = new FileInputStream(filePath);
		Workbook wb = WorkbookFactory.create(fileInputStream);
		Sheet s = wb.getSheetAt(0);
		Row row1 = null;
		row1 = s.getRow(row);
		Cell cell;
		cell = row1.getCell((short) col);
		System.out.println("------>Test Step " + (row + 1) + "). :: " + result);
		//logging
		logger.info("------>Test Step " + (row + 1) + "). :: " + result);
		
		if (cell == null) {
			cell = row1.createCell(col);
		}
		cell.setCellValue(result.toString());
		FileOutputStream fileOut = new FileOutputStream(filePath);
		wb.write(fileOut);
		fileOut.flush();
		fileOut.close();
	}

	
	/**
	 * To update test Screen Shot Link
	 * @method updateScreenShot
	 * 
	 */
	public void updateScreenShot(String filePath, int row, int col,String result) throws Exception {
		Sheet excelWSheet;
		Workbook excelWBook;
		Cell cell = null;
		Row rowUpdate = null;
		String screenShotImagePath = result.replace("\\", "/");
		FileInputStream excelFile = new FileInputStream(filePath);
		// Access the required test data sheet
		//excelWBook = new XSSFWorkbook(excelFile);
		excelWBook = WorkbookFactory.create(excelFile);
		excelWSheet = excelWBook.getSheetAt(0);
		rowUpdate = excelWSheet.getRow(row);
		if (cell == null) {
			cell = rowUpdate.createCell(col);
		}
		CellStyle hlink_style = excelWBook.createCellStyle();
		Font hlink_font = excelWBook.createFont();
		hlink_font.setColor(IndexedColors.BLUE.getIndex());
		hlink_style.setFont(hlink_font);
		CreationHelper helper = excelWBook.getCreationHelper();
		Hyperlink FileLink = helper.createHyperlink(Hyperlink.LINK_FILE);
		cell.setCellValue("Error");
		FileLink.setAddress(screenShotImagePath);
		cell.setHyperlink(FileLink);
		cell.setCellStyle(hlink_style);
		cell.setCellValue(screenShotImagePath.toString());
		FileOutputStream fileOut = new FileOutputStream(filePath);
		excelWBook.write(fileOut);
		fileOut.flush();
		fileOut.close();
	}


	/**
	 * takes the Screen Shot on failure and return the Screen shot file path
	 * 
	 * @param className
	 * @param Result
	 * @throws Exception
	 */
	public String snapShot(String fileName, String filePath)
			throws IOException {
		String osName = System.getProperty("os.name");
		String commandName = "cmd.exe";
		if (osName.equals("Windows 95")) {
			commandName = "command.com";
		}
		String[] cmds = new String[9];

		cmds[0] = commandName;
		cmds[1] = "/C";
		cmds[2] = Global.toolsFolder +"\\Screenshot.exe ";
		cmds[3] = "/f";
		cmds[4] = fileName;
		cmds[5] = "/d";
		cmds[6] = filePath;
		cmds[7] = "/q";
		cmds[8] = "100,7,True";
		
		return filePath + "/" + fileName;
	}
	
	/**
	 * Updates the TestSuite_FailedScripts excel with Fail cases 
	 * @method updateFailureTestSuite
	 * 
	 */
	public void updateFailureTestSuite(String testScriptName, TestSuite testsuite)throws Exception {
		Sheet excelWSheet;
		Workbook excelWBook;
		Row row;
		String testsuiteFailedScripts = testsuite.getFailedTestSuitePath();
		FileInputStream excelFile = new FileInputStream(testsuiteFailedScripts);
		
		excelWBook = WorkbookFactory.create(excelFile);
		//excelWBook = new XSSFWorkbook(excelFile);
		excelWSheet = excelWBook.getSheet("Scripts");
		excelFile.close();
		Integer rowCount = excelWSheet.getLastRowNum() + 1;
		// System.out.println("Last row of Result File: " + rowCount);
		try {
			row = excelWSheet.createRow(rowCount);
			row.createCell(TestSuite.PROJECT_FOLDER_INDEX)
			.setCellValue(testsuite.getTestScriptProperties(testScriptName)[TestSuite.PROJECT_FOLDER_INDEX]);
			row.createCell(TestSuite.MODULE_FOLDER_INDEX)
			.setCellValue(testsuite.getTestScriptProperties(testScriptName)[TestSuite.MODULE_FOLDER_INDEX]);
			row.createCell(TestSuite.TESTSCRIPT_NAME_INDEX)
			.setCellValue(testsuite.getTestScriptProperties(testScriptName)[TestSuite.TESTSCRIPT_NAME_INDEX]);
			row.createCell(TestSuite.RUN_INDEX)
			.setCellValue("Yes");
			row.createCell(TestSuite.TEST_TYPE_INDEX)
			.setCellValue(testsuite.getTestScriptProperties(testScriptName)[TestSuite.TEST_TYPE_INDEX]);

			FileOutputStream fileOut = new FileOutputStream(testsuiteFailedScripts);
			excelWBook.write(fileOut);
			fileOut.flush();
			fileOut.close();
			
		} catch (Exception e) {
			excelWBook.close();
			throw (e);
		}

	}


	public void setResultCell(TestScript scriptName, String scriptLogFile,
			String result, String duration, String errorMessage) throws Exception {
		testResult = new TestResultExcelReport();
		testResult.updateStatusToTestResult(scriptName, scriptLogFile, result, duration, errorMessage);
		
	}


	public void createHTMLReport(String duration, String constantTimeStamp,
			String suiteStart) throws Exception {
		
		new TestResultHtmlReport(Global.getTestResultFileAbsolutePath()).createHTMLReport(duration, constantTimeStamp, suiteStart);
		
	}
		
	
}
