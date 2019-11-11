package com.planit.automation.processor;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
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

import com.planit.automation.library.Global;

public class TestResultExcelReport {

	public static final int SNO_INDEX = 0;
	public static final int MODULE_NAME_INDEX = 1;
	public static final int TESTSCRIPT_NAME_INDEX = 2;
	public static final int STATUS_INDEX = 3;
	public static final int LOG_FILE_INDEX = 4;
	public static final int TIME_TAKEN_INDEX = 5; // It is created internally not from the testsuite.xlsx
	public static final int REMARKS_INDEX = 7; // It is created internally not from the testsuite.xlsx
	public static final int DEFECT_ID_INDEX = 6; 

	private Sheet excelWSheet;
	private Workbook excelWBook;
	private FileInputStream excelFile;



	public TestResultExcelReport() throws InvalidFormatException, IOException{
		excelFile = new FileInputStream(Global.getTestResultFileAbsolutePath());
		excelWBook = WorkbookFactory.create(excelFile);
		excelWSheet = excelWBook.getSheetAt(0);
		excelFile.close();
	}



	/**
	 * Updates the TestResult excel with Pass and Fail and adds the Link for
	 * @method setResultCell
	 * 
	 */
	public void updateStatusToTestResult(TestScript testScript, String scriptLogFile, String result, 
			String duration, String errorMessage)
					throws Exception {
		Row row;
		Integer rowCount = excelWSheet.getLastRowNum() + 1;

		// System.out.println("Last row of Result File: " + rowCount);
		//		if (result.equalsIgnoreCase("pass")) { // If PASS

		row = excelWSheet.createRow(rowCount);
		setCellValue(row, rowCount.toString(), SNO_INDEX);
		setCellValue(row, testScript.getModuleFolder(), MODULE_NAME_INDEX);
		setCellValue(row, testScript.getTestScriptName(), TESTSCRIPT_NAME_INDEX);
		setCellValue(row, result, STATUS_INDEX);
		setCellValue(row, duration, TIME_TAKEN_INDEX);
		setCellValue(row, testScript.getDefectId(), DEFECT_ID_INDEX);
		setCellValue(row, errorMessage, REMARKS_INDEX);
		applyFormat(row, STATUS_INDEX);

		updateLogFileLink(scriptLogFile, row, LOG_FILE_INDEX);
		FileOutputStream fileOut = new FileOutputStream(Global.getTestResultFileAbsolutePath());
		excelWBook.write(fileOut);
		excelWBook.close();
		fileOut.flush();
		fileOut.close();
	}


	private void setCellValue(Row row, String cellValue, int cellIndex){
		Cell cell = row.getCell(cellIndex,
				org.apache.poi.ss.usermodel.Row.RETURN_BLANK_AS_NULL);
		if (cell == null) {
			cell = row.createCell(cellIndex);
			cell.setCellValue(cellValue);
		} else {
			cell.setCellValue(cellValue);
		}
	}

	private void updateLogFileLink(String scriptLogFile, Row row, int logFileIndex){
		//String ScriptLogFile = Global.globalScript;
		String Log_ExcelLink = scriptLogFile;
		Log_ExcelLink = Log_ExcelLink.replace("\\", "/");
		Cell cell = row.getCell(logFileIndex,Row.RETURN_BLANK_AS_NULL);
		if (cell == null) {
			cell = row.createCell(logFileIndex);
			CellStyle hlink_style = excelWBook.createCellStyle();
			Font hlink_font = excelWBook.createFont();
			// hlink_font.setUnderline(Font.U_SINGLE);
			hlink_font.setColor(IndexedColors.BLUE.getIndex());
			hlink_style.setFont(hlink_font);
			CreationHelper helper = excelWBook.getCreationHelper();
			Hyperlink FileLink = helper
					.createHyperlink(Hyperlink.LINK_FILE);
			cell.setCellValue("Log_File");
			FileLink.setAddress(Log_ExcelLink);
			cell.setHyperlink(FileLink);
			cell.setCellStyle(hlink_style);
		} else {
			CellStyle hlink_style = excelWBook.createCellStyle();
			Font hlink_font = excelWBook.createFont();
			// hlink_font.setUnderline(Font.U_SINGLE);
			hlink_font.setColor(IndexedColors.BLUE.getIndex());
			hlink_style.setFont(hlink_font);
			CreationHelper helper = excelWBook.getCreationHelper();
			Hyperlink FileLink = helper
					.createHyperlink(Hyperlink.LINK_FILE);
			cell.setCellValue(Log_ExcelLink);
			FileLink.setAddress(Log_ExcelLink);
			cell.setHyperlink(FileLink);
			cell.setCellStyle(hlink_style);
		}

	}

	private void applyFormat(Row row, int cellIndex){
		CellStyle style;
		Cell cell = row.getCell(cellIndex,
				org.apache.poi.ss.usermodel.Row.RETURN_BLANK_AS_NULL);
		if (cell.getStringCellValue().equalsIgnoreCase("PASS")) {
			style = excelWBook.createCellStyle();
			style.setFillForegroundColor(IndexedColors.GREEN.getIndex());
			style.setFillPattern(CellStyle.SOLID_FOREGROUND);
			cell.setCellStyle(style);
		} else {
			style = excelWBook.createCellStyle();
			style.setFillForegroundColor(IndexedColors.RED.getIndex());
			style.setFillPattern(CellStyle.SOLID_FOREGROUND);
			cell.setCellStyle(style);
		}

	}

}
