/**
	 * Generic Library
	 * @description - This contains all Generic functions
	 * 
	 * @class GenericLibrary
	 * 
	 * @author Prabs 
	 * @versions History
	 * Date			Author		Comments
	 * 29/10/2019	Prabs		Basic File added with all controls
	 * 
	 * 
	 * 
	 * 
*/

package com.client.automation.common;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Hyperlink;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.quartz.CronTrigger;
import org.quartz.JobDetail;	
import org.quartz.Scheduler;
import org.quartz.impl.StdSchedulerFactory;

import com.client.automation.library.DynamicVarRepository;
import com.client.automation.library.Global;
import com.client.automation.processor.TestSuite;

import org.quartz.SchedulerException;
import java.util.Date;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;



public class Common {
	
	
	Calendar calendar;
	protected String samplepro=null;
	private static Logger logger = Logger.getLogger(Common.class.getName());
	/**
	 * To Return the Current Date and time stamp in 'MMM-dd-yyyy_h-mm-ss' format
	 */
	public String getTimeStamp() {
		Date d = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("MMM-dd-yyyy_h-mm-ss");
		String date = sdf.format(d).toString();
		return date;
	}
	

//	 * To Return the Current Date and time stamp in 'dd-MMM-yyyy' format

	public String getTodayDate() {
		Date d = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
		String date = sdf.format(d).toString();
		return date;
	}
	/*
	 * To Return the Current Date and time stamp in 'MMM-dd-yyyy_h-mm-ss' format
	*/
	public String getcurrenthour(){
		Date d = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("HH");
		String date = sdf.format(d).toString();
		return date;
	}
	
	/**
	 * Gets the Starttime and endtime and calculates the duration of the test.
	 * 

	 */
	
	
public String timeduration(Date starttime , Date endtime) throws InterruptedException, ParseException{
		

		SimpleDateFormat sdf1 = new SimpleDateFormat("MMM-dd-yyyy_h:mm:ss");
		String date1 = sdf1.format(starttime).toString();
		String date2 = sdf1.format(endtime).toString();
		Date diffdate1 =sdf1.parse(date1);
		Date diffdate2 =sdf1.parse(date2);
		
		
		long diff = diffdate2.getTime() - diffdate1.getTime();
		 
		long diffSeconds = diff / 1000 % 60;
		long diffMinutes = diff / (60 * 1000) % 60;
		long diffHours = diff / (60 * 60 * 1000) % 24;
		long diffDays = diff / (24 * 60 * 60 * 1000);

		String duration = diffHours+":"+diffMinutes+":"+diffSeconds;
		return duration;
}

	/**
	 * Create the Result Folder and returns the path

	 */
	public void createResultFolder(String resultsFolderAbsolutePath) {
		File file = new File(resultsFolderAbsolutePath);
		
		if (!file.exists()) {
			if (file.mkdirs()) {
				System.out.println("Result Folder is created! - " + file.getAbsolutePath());
				logger.info("Result Folder is created!");
			} else {
				System.out.println("Failed to create Result Folder! - " + file.getAbsolutePath());
				logger.info("Failed to create Result Folder!");
			}
		}

	}

	/**
	 * Create the Log Folder and returns the path
	 * 

	 */
	public void createLogFolder(String logFolderAbsolutePath) {
		System.out.println(" Time Stamp : " + Global.constantTimeStamp);
		logger.info(" Time Stamp : " + Global.constantTimeStamp);
		//DriverScript.APP_LOGS.debug(Global.constantTimeStamp);
		File file = new File(logFolderAbsolutePath);
		if (!file.exists()) {
			if (file.mkdirs()) {
				System.out.println("Log Directory is created! - " + file.getAbsolutePath());
				logger.info("Log Directory is created! - " + file.getAbsolutePath());
			} else {
				System.out.println("Failed to create Log directory! - " + file.getAbsolutePath());
				logger.info("Failed to create Log directory! - " + file.getAbsolutePath());
			}
		}

	}

	/**
	 * Create the ScreenShot Folder and returns the path
	 * 

	 */
	public void createScreenShotFolder(String screenShotFolderAbsolutePath) {
		File file = new File(screenShotFolderAbsolutePath);
		if (!file.exists()) {
			if (file.mkdirs()) {
				System.out.println("Screenshot_Directory is created! - " + file.getAbsolutePath());
				logger.info("Screenshot_Directory is created! - " + file.getAbsolutePath());
			} else {
				System.out.println("Failed to create Screenshot directory! - " +  file.getAbsolutePath());
				logger.info("Failed to create Screenshot directory! - "  + file.getAbsolutePath());
			}
		}
		
		
	}

	/**
	 * Create the Downloads Folder and returns the path
	 * 

	 */
	public void createDownloadsFolder(String downloadfolderAbsolutePath) throws Exception {
		File file = new File(downloadfolderAbsolutePath);
		if (!file.exists()) {
			if (file.mkdirs()) {
				System.out.println("Download Folder is created! - " + file.getAbsolutePath());
				logger.info("Download Folder is created!");
			} else {
				System.out.println("Failed to create Downloads Folder! - " + file.getAbsolutePath());
				logger.info("Failed to create Downloads Folder!");
				throw new Exception("ERROR: Failed to create Downloads folder :" + file.getAbsolutePath());
			}
		}

	}
	/**
	 * To Copy the Result_Template file from Template folder to Result folder

	 */
	public String copyResultTemplate() {
		InputStream inStream = null;
		OutputStream outStream = null;
		try {
			//System.out.println(Global.templatesFolder+"\\Result_Template.xlsx");
			//logging
			//logger.debug(Global.templatesFolder+"\\Result_Template.xlsx");
			File Originalfile = new File(Global.getTestResultTemplateAbsolutePath());
			File Copyfile = new File(Global.getTestResultFileAbsolutePath());
			inStream = new FileInputStream(Originalfile);
			outStream = new FileOutputStream(Copyfile);
			byte[] buffer = new byte[1024];
			int length;
			// copy the file content in bytes
			while ((length = inStream.read(buffer)) > 0) {
				outStream.write(buffer, 0, length);
			}
			inStream.close();
			outStream.close();
			inStream.close();
			outStream.close();
			System.out.println("Result file updated successful! - " + Global.getTestResultFileAbsolutePath());
			//logging
			logger.info("Result file updated successful! - " + Global.getTestResultFileAbsolutePath());
			
			return Global.getTestResultFileAbsolutePath();
			
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}

	}

	/**
	 * To Copy DataTable file to Log folder
	 * 

	 */
	public String copyDataTableFile(String path, String filename) throws Exception {
		InputStream inStream = null;
		OutputStream outStream = null;
		File Copyfile = null;
		File Originalfile = null;
		
		try {
			int length;
			Originalfile = new File(path);
			System.out.println("Original file : "+ Originalfile.getAbsolutePath());
			logger.info("Original file : "+ Originalfile.getAbsolutePath());
			Copyfile = new File(Global.getScriptLogFile(filename));
					//Global.logsFolder+"/Log_"
							//+ Global.constantTimeStamp + "/" + filename + Global.getExcelExtn());
			inStream = new FileInputStream(Originalfile);
			outStream = new FileOutputStream(Copyfile);
			byte[] buffer1 = new byte[1024];
			// copy the file content in bytes
			while ((length = inStream.read(buffer1)) > 0) {
				outStream.write(buffer1, 0, length);
			}
			inStream.close();
			outStream.close();
			System.out.println("Result (LOG) file updated successful! - " + Global.getScriptLogFile(filename));
			logger.info("Result (LOG) file updated successful! - " + Global.getScriptLogFile(filename));
			return Global.getScriptLogFile(filename);
			
		} catch (IOException e) {
			System.out.println("ERROR: Exception occurred while creating the log file : " );
			System.out.println("\n"+ Copyfile.getAbsolutePath());
			logger.error("ERROR: Exception occurred while creating the log file : " + Copyfile.getAbsolutePath() + "\n" + e.getMessage());
			throw new Exception("ERROR: Exception occurred while creating the log file : " + Copyfile.getAbsolutePath() + "\n" + e.getMessage());

		}
	}

	/**
	 * To Create Latest current Time stamp folder under Logs File and returns
	 * the path
	 * 

	 */
	public String Create_LogFile(String scrptNm) {
		InputStream inStream = null;
		OutputStream outStream = null;
		try {
			String scriptName = Global.globalScript;
			File originalfile = new File(Global.templatesFolder+"/TestStep_Template.xlsx");
			File copyfile = new File(scriptName);
			inStream = new FileInputStream(originalfile);
			outStream = new FileOutputStream(copyfile);
			byte[] buffer = new byte[1024];
			int length;
			// copy the file content in bytes
			while ((length = inStream.read(buffer)) > 0) {
				outStream.write(buffer, 0, length);
			}
			inStream.close();
			outStream.close();
			System.out.println("LogFile create successful! - " + copyfile.getAbsolutePath());
			logger.info("LogFile create successful! - " + copyfile.getAbsolutePath());
			return Global.getLogPath() + Global.pathSeperator + scrptNm + Global.getExcelExtn();
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * Updates the TestResult excel with Pass and Fail and adds the Link for
	 * @method setResultCell
	 * 
	 */
	public void setResultCell(String testScriptName, String scriptLogFile, String result, 
			String duration, String errorMessage)
			throws Exception {
		Sheet excelWSheet;
		Workbook excelWBook;
		Cell cell;
		Row row;
		CellStyle style;
		FileInputStream excelFile = new FileInputStream(Global.getTestResultFileAbsolutePath());
		// Access the required test data sheet
		excelWBook = WorkbookFactory.create(excelFile);
		//excelWBook = new XSSFWorkbook(excelFile);
		excelWSheet = excelWBook.getSheetAt(0);
		excelFile.close();
		Integer rowCount = excelWSheet.getLastRowNum() + 1;
		// System.out.println("Last row of Result File: " + rowCount);
		if (result.equalsIgnoreCase("pass")) { // If PASS
			try {
				row = excelWSheet.createRow(rowCount);
				cell = row.getCell(0,
						org.apache.poi.ss.usermodel.Row.RETURN_BLANK_AS_NULL);
				if (cell == null) {
					cell = row.createCell(0);
					cell.setCellValue(rowCount.toString());
				} else {
					cell.setCellValue(rowCount.toString());
				}
				cell = row.getCell(1,
						org.apache.poi.ss.usermodel.Row.RETURN_BLANK_AS_NULL);
				if (cell == null) {
					cell = row.createCell(1);
					cell.setCellValue(testScriptName);
				} else {
					cell.setCellValue(testScriptName);
				}
				cell = row.getCell(2,
						org.apache.poi.ss.usermodel.Row.RETURN_BLANK_AS_NULL);
				if (cell == null) {
					cell = row.createCell(2);
					style = excelWBook.createCellStyle();
					style.setFillForegroundColor(IndexedColors.GREEN.getIndex());
					style.setFillPattern(CellStyle.SOLID_FOREGROUND);
					cell.setCellValue(result);
					cell.setCellStyle(style);
				} else {
					style = excelWBook.createCellStyle();
					style.setFillForegroundColor(IndexedColors.GREEN.getIndex());
					style.setFillPattern(CellStyle.SOLID_FOREGROUND);
					cell.setCellValue(result);
					cell.setCellStyle(style);
				}// Constant variables Test Data path and Test Data file name
				
				cell = row.getCell(4,
						org.apache.poi.ss.usermodel.Row.RETURN_BLANK_AS_NULL);
				if (cell == null) {
					cell = row.createCell(4);
					cell.setCellValue(duration);
				} else {
					cell.setCellValue(duration);
				}
				
				cell = row.getCell(5,
						org.apache.poi.ss.usermodel.Row.RETURN_BLANK_AS_NULL);
				if (cell == null) {
					cell = row.createCell(5);
					cell.setCellValue(errorMessage);
				} else {
					cell.setCellValue(errorMessage);
				}
				
				FileOutputStream fileOut = new FileOutputStream(Global.getTestResultFileAbsolutePath());

				excelWBook.write(fileOut);
				
				fileOut.flush();
				fileOut.close();
			} catch (Exception e) {
				excelWBook.close();
				throw (e);
			}
		}

		else { // if FAIL

			row = excelWSheet.createRow(rowCount);
			cell = row.getCell(0,Row.RETURN_BLANK_AS_NULL);
			if (cell == null) {
				cell = row.createCell(0);
				cell.setCellValue(rowCount.toString());
			} else {
				cell.setCellValue(rowCount.toString());
			}
			cell = row.getCell(1,Row.RETURN_BLANK_AS_NULL);
			if (cell == null) {
				cell = row.createCell(1);
				cell.setCellValue(testScriptName);
			} else {
				cell.setCellValue(testScriptName);
			}
			cell = row.getCell(2,Row.RETURN_BLANK_AS_NULL);
			if (cell == null) {
				cell = row.createCell(2);
				style = excelWBook.createCellStyle();
				style.setFillForegroundColor(IndexedColors.RED.getIndex());
				style.setFillPattern(CellStyle.SOLID_FOREGROUND);
				cell.setCellValue(result);
				cell.setCellStyle(style);
			} else {
				style = excelWBook.createCellStyle();
				style.setFillForegroundColor(IndexedColors.RED.getIndex());
				style.setFillPattern(CellStyle.SOLID_FOREGROUND);
				cell.setCellValue(result);
				cell.setCellStyle(style);
			}
			
			cell = row.getCell(4,Row.RETURN_BLANK_AS_NULL);
			if (cell == null) {
				cell = row.createCell(4);
				cell.setCellValue(duration);
			} else {
				cell.setCellValue(duration);
			}
			
			cell = row.getCell(5,Row.RETURN_BLANK_AS_NULL);
			if (cell == null) {
				cell = row.createCell(5);
				cell.setCellValue(errorMessage);
			} else {
				cell.setCellValue(errorMessage);
			}
			

		}
		//String ScriptLogFile = Global.globalScript;
		String Log_ExcelLink = scriptLogFile;
		Log_ExcelLink = Log_ExcelLink.replace("\\", "/");
		cell = row.getCell(3,Row.RETURN_BLANK_AS_NULL);
		if (cell == null) {
			cell = row.createCell(3);
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
		FileOutputStream fileOut = new FileOutputStream(Global.getTestResultFileAbsolutePath());
		excelWBook.write(fileOut);
		excelWBook.close();
		fileOut.flush();
		fileOut.close();
	}

	
	/**
	 * To update test step status
	 * @method updateStatus
	 * 
	 */
	public void updateStatus(String filePath, int row, int col,String result) throws Exception {
		FileInputStream fileInputStream = new FileInputStream(filePath);
		Workbook wb = WorkbookFactory.create(fileInputStream);
		Sheet s = wb.getSheetAt(0);
		int rowCnt = s.getLastRowNum();
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
		CellStyle style;
		String screenShotImagePath = result.replace("\\", "/");
		FileInputStream excelFile = new FileInputStream(filePath);
		// Access the required test data sheet
		//excelWBook = new XSSFWorkbook(excelFile);
		excelWBook = WorkbookFactory.create(excelFile);
		excelWSheet = excelWBook.getSheetAt(0);
		Integer rowCount = excelWSheet.getLastRowNum() + 1;
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
	 * Updates the Log excel with Pass and Fail and adds the Link for Error
	 * 
	 * 
	 * 
	 */
	public void setCellData(String desc, String Result) throws Exception {
		Sheet excelWSheet;
		Workbook excelWBook;
		Cell cell;
		Row row;
		CellStyle style;
		String scriptName = Global.globalScript;
		System.out.println(scriptName);
		logger.info(scriptName);
		FileInputStream excelFile = new FileInputStream(scriptName);
		excelWBook = WorkbookFactory.create(excelFile);
		//excelWBook = new XSSFWorkbook(excelFile);
		excelWSheet = excelWBook.getSheetAt(0);
		Integer rowCount = excelWSheet.getLastRowNum() + 1;
		System.out.println("------>Test Step :: "+desc+" :: "+Result + " >RowCount: "+rowCount);
		logger.info("------>Test Step :: "+desc+" :: "+Result + " >RowCount: "+rowCount);
		if (Result.equalsIgnoreCase("pass")) {
			try {
				String screenShotLink = snapShot("Error.jpg",
						Global.screenShotPath);
				System.out.println("------>Test Step :: " + desc + " :: "+ Result + " >RowCount: " + rowCount);
				logger.info("------>Test Step :: " + desc + " :: "+ Result + " >RowCount: " + rowCount);
				row = excelWSheet.createRow(rowCount);
				cell = row.getCell(0,Row.RETURN_BLANK_AS_NULL);
				if (cell == null) {
					cell = row.createCell(0);
					cell.setCellValue(rowCount.toString());
				} else {
					cell.setCellValue(rowCount.toString());
				}
				cell = row.getCell(1,Row.RETURN_BLANK_AS_NULL);
				if (cell == null) {
					cell = row.createCell(1);
					cell.setCellValue(desc);
				} else {
					cell.setCellValue(desc);
				}
				cell = row.getCell(2,Row.RETURN_BLANK_AS_NULL);
				if (cell == null) {
					cell = row.createCell(2);
					style = excelWBook.createCellStyle();
					style.setFillForegroundColor(IndexedColors.GREEN.getIndex());
					style.setFillPattern(CellStyle.SOLID_FOREGROUND);
					cell.setCellValue(Result);
					cell.setCellStyle(style);
				} else {
					style = excelWBook.createCellStyle();
					style.setFillForegroundColor(IndexedColors.GREEN.getIndex());
					style.setFillPattern(CellStyle.SOLID_FOREGROUND);
					cell.setCellValue(Result);
					cell.setCellStyle(style);
				}// Constant variables Test Data path and Test Data file name
				FileOutputStream fileOut = new FileOutputStream(scriptName);
				excelWBook.write(fileOut);
				fileOut.flush();
				fileOut.close();
			} catch (Exception e) {
				throw (e);
			}
		} else {
			String screenShotLink = snapShot("Error.jpg", Global.screenShotPath);
			try {
				System.err.println("------>Test Step :: " + desc + " :: "
						+ Result + " >RowCount: " + rowCount);
				row = excelWSheet.createRow(rowCount);
				cell = row.getCell(0,Row.RETURN_BLANK_AS_NULL);
				if (cell == null) {
					cell = row.createCell(0);
					cell.setCellValue(rowCount.toString());
				} else {
					cell.setCellValue(rowCount.toString());
				}
				cell = row.getCell(1,Row.RETURN_BLANK_AS_NULL);
				if (cell == null) {
					cell = row.createCell(1);
					cell.setCellValue(desc);
				} else {
					cell.setCellValue(desc);
				}
				cell = row.getCell(2,Row.RETURN_BLANK_AS_NULL);
				if (cell == null) {
					cell = row.createCell(2);
					style = excelWBook.createCellStyle();
					style.setFillForegroundColor(IndexedColors.RED.getIndex());
					style.setFillPattern(CellStyle.SOLID_FOREGROUND);
					cell.setCellValue(Result);
					cell.setCellStyle(style);
				} else {
					style = excelWBook.createCellStyle();
					style.setFillForegroundColor(IndexedColors.RED.getIndex());
					style.setFillPattern(CellStyle.SOLID_FOREGROUND);
					cell.setCellValue(Result);
					cell.setCellStyle(style);
				}
				cell = row.getCell(3,Row.RETURN_BLANK_AS_NULL);
				if (cell == null) {
					cell = row.createCell(3);
					CellStyle hlink_style = excelWBook.createCellStyle();
					Font hlink_font = excelWBook.createFont();
					// hlink_font.setUnderline(Font.U_SINGLE);
					hlink_font.setColor(IndexedColors.BLUE.getIndex());
					hlink_style.setFont(hlink_font);
					CreationHelper helper = excelWBook.getCreationHelper();
					Hyperlink FileLink = helper
							.createHyperlink(Hyperlink.LINK_FILE);
					cell.setCellValue("ScreenShot");
					FileLink.setAddress(screenShotLink);
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
					cell.setCellValue(screenShotLink);
					FileLink.setAddress(screenShotLink);
					cell.setHyperlink(FileLink);
					cell.setCellStyle(hlink_style);
				}
				FileOutputStream fileOut = new FileOutputStream(scriptName);
				excelWBook.write(fileOut);
				fileOut.flush();
				fileOut.close();
			} catch (Exception e) {
				throw (e);
			}
		}// else
	}

	/**
	 * To Read Test data from DataTable file
	 * 
	 * 
	 */
	public String[] readTestdata(String filePath) throws IOException,
			InvalidFormatException {
		FileInputStream fileInputStream = new FileInputStream(filePath);
		Workbook wb = WorkbookFactory.create(fileInputStream);
		Sheet s = wb.getSheetAt(0);
		int rowCnt =  s.getLastRowNum();
		Row row = null;
		row =  s.getRow(1);
		Cell cell;
		int cols = s.getRow(1).getPhysicalNumberOfCells();
		java.util.List list = new ArrayList();
		String[] values = new String[cols];
		for (int c = 0; c < cols; c++) {
			cell = row.getCell((short) c);
			if (cell != null) {
				cell = row.getCell(c);
				values[c] = cell.getRichStringCellValue().toString();
			}
		}
		return values;
	}

	/**
	 * To Create HTML Report
	 * 

	 */
	public void createHTMLReport(String suiteDuration, String constantTimeStamp, String suiteStart) throws IOException,
			InvalidFormatException {
		String cont = null;
		String cont1 = null;
		String cont2 = null;
		String cont3 = null;
		   String scrpTag = "<script type='text/javascript'>";
		    scrpTag +="function showTrace(e){";
		    scrpTag +="window.event.srcElement.parentElement.getElementsByClassName('traceinfo')[0].className = 'traceinfo visible';}";
		    scrpTag +="function closeTraceModal(e){";
		    scrpTag +="window.event.srcElement.parentElement.parentElement.className = 'traceinfo';}";
		    scrpTag +="function openModal(imageSource){";
		    scrpTag +="var myWindow = window.open('','screenshotWindow');";
		    scrpTag +="myWindow.document.write('<img src=\"' +imageSource + '\" alt=\"screenshot\" />');}";
		    scrpTag += "</script>";
		    
		    String styleTag = "<style>";
		 //   styleTag+= "body{font-family:Arial};";
		 //   styleTag+= "ul,li{margin-left:0;padding-left:0;width:100%;font-weight:bold;}";
		 //   styleTag+= "table{width:95%;text-align:left;border-spacing:0;border-collapse: separate;margin-bottom:5px;}";
		 //   styleTag+= "li{font-weight:bold;padding-left:5px;list-style:none;}";
		 //   styleTag+= "ul table li{font-weight: normal}";
		 //  styleTag+= "th,td{padding: 10px;border: 1px solid #000;}";
		 //   styleTag+= "td.desc-col{width:400px;}th.desc-col{width: 390px;}";
		 //   styleTag+= "td.status-col{width:75px;}th.status-col{width: 75px;}";
		 //   styleTag+= "td.browser-col{width:345px;}th.browser-col{width: 345px;}";
		 //   styleTag+= "td.os-col{width:100px;}th.os-col{width: 100px;}";
		 //   styleTag+= "td.msg-col{width:135px;}th.msg-col{width: 135px;}";
		 //   styleTag+= "table.header{background-color: gray; color: #fff;margin-left:20px;}";
		    styleTag+= ".traceinfo{position: fixed;top: 0; bottom: 0;left: 0;right:0;background: rgba(0,0,0,0.8);z-index: 99999;opacity:0;-webkit-transition: opacity 400ms ease-in;transition: opacity 400ms ease-in;pointer-events: none;}";
		    styleTag+= ".traceinfo.visible{opacity:1;pointer-events: auto;}";
		    styleTag+= ".traceinfo > div{width: 900px;position: relative;margin: 10% auto;padding: 5px 20px 13px 20px;background: #fff;}";
		    styleTag+= ".traceinfo .close{background: #606061;color: #FFFFFF;line-height: 25px;position: absolute;right: -12px;text-align: center;top: -10px;width: 24px;text-decoration: none;font-weight: bold;}";
		    styleTag+= ".traceinfo .close:hover{background: #00d9ff;}";
		    styleTag+= "</style>";
		    
		cont = "<HTML><HEAD><TITLE>" + Global.getClientName() + " Test Report</TITLE>"
				+ styleTag + scrpTag
				+ "<META http-equiv=Content-Type content=text/html; charset=windows-1252><META content=MSHTML 6.00.2900.3268 name=GENERATOR></HEAD>"
				+ "<H2 align=center><U><FONT color=#187030><B>" + Global.getClientName() + " Test Execution Report - " + getTodayDate() + "</B></FONT> <U></H2><HR>"
				+ "<TABLE width=100% height=37 border=0 align=center>"
				+ "<TBODY>"
					+ "<TR>"
						+ "<TD height=19 <FONT color=black><B>Project Name :: </B></FONT><FONT color=#187030><B>"+ Global.getClientName() +"</B></FONT>"
						+ "<TD width=50% align=right><div align=left><FONT color=black><B>Date and Time of Execution : </B></FONT><FONT color=#187030><B>" + suiteStart + "</B></FONT></div></TD>"
					+ "</TR>"
					+ "<TR>"
						+ "<TD height=19 <FONT color=black><B>Environment :: </B></FONT><FONT color=#187030><B>" + Global.getEnvironment() + " (" + Global.getUrl() + ")" + "</B></FONT>"
						+ "<TD width=50% align=right><div align=left><FONT color=black><B></B></FONT><FONT color=#187030><B>"+ "</B></FONT></div></TD>"
						+ "</TR>"
					+ "<TR>"
						+ "<TD height=19 <FONT color=black><B>Browser :: </B></FONT><FONT color=#187030><B>" + Global.getBrowserAndVersion() + "</B></FONT>"
						+ "<TD width=50% align=right><div align=left><FONT color=black><B></B></FONT><FONT color=#187030><B>"+ "</B></FONT></div></TD>"
					+ "</TR>"
					+ "<TR>"
						+ "<TD height=19 <FONT color=black><B>Operating System :: </B></FONT><FONT color=#187030><B>" + Global.getOsName() + "</B></FONT>"
						+ "<TD width=50% align=right><div align=left><FONT color=black><B></B></FONT><FONT color=#187030><B>" + "</B></FONT></div></TD>"
					+ "</TR>";
		File fi1 = new File(
				Global.resultsFolder+"\\Result_"
						+ Global.constantTimeStamp + "\\Execution_Report.html");
		FileOutputStream fos = new FileOutputStream(fi1);
		fos.write(cont.getBytes());

		int passCount = 0;
		int failCount = 0;
		FileInputStream fileInputStream = new FileInputStream(Global.getTestResultFileAbsolutePath());
		Workbook wb = WorkbookFactory.create(fileInputStream);
		Sheet s = wb.getSheet("Result");
		int rowCnt = s.getLastRowNum();
		Row row = null;
		for (int j = 1; j <= rowCnt; j++) {
			row = s.getRow(j);
			Cell cell = row.getCell(2);
			String testCasename = cell.getRichStringCellValue().toString();
			if (testCasename.equalsIgnoreCase("Pass")) {
				passCount++;
			}
			if (testCasename.equalsIgnoreCase("fail")) {
				failCount++;
			}

		}

		cont = ""
			//	+ "<TR bgColor=#ffffdd>"
			//		+ "<TD></TD>"
			//		+ "<TD></TD>"
			//		+ "<TD></TD>"
			//		+ "<TD></TD>"
			//		+ "<TD></TD>"
			//		+ "</TR>"
			//		+ "<TD><B><FONT></FONT></B></TD></TR>"
					+ "</TBODY></TABLE>"
				+ "<BR>"
			//	+ "<BR><HR>"
				+ "<TABLE width=80% align=center border=1>"
				+ "<TBODY><TR bgColor=#376936>"
				+ "<TD align=middle><B><FONT color=#ffffff>Total Testcases </FONT></B></TD>"
				+ "<TD align=middle><B><FONT color=#ffffff>No. of TC Passed </FONT></B></TD>"
				+ "<TD align=middle><B><FONT color=#ffffff>No. of TC Failed </FONT></B></TD>"
				+ "<TD align=middle><B><FONT color=#ffffff>Total Time Elapsed</FONT></B></TD>"
				+ "</TR>"
				+ "<TR bgColor=#ffffdd>";
		fos.write(cont.getBytes());

		cont = "<TD align=middle><B>" + rowCnt + " </B></TD>";
		fos.write(cont.getBytes());
		cont = "<TD align=middle><B>" + passCount + " </B></TD>";
		fos.write(cont.getBytes());
		cont = "<TD align=middle><B><FONT color=#ff0066>" + failCount + "</FONT>";
		fos.write(cont.getBytes());
		cont = "<TD align=middle><B>" + suiteDuration + " </B></TD>";
		fos.write(cont.getBytes());

		cont = ""
				//+ "<TR bgColor=#ffffdd>"
				//	+ "<TD></TD>"
				//	+ "<TD></TD>"
				//	+ "<TD></TD>"
				//	+ "<TD></TD>"
				//	+ "<TD></TD>"
			//	+ "</TR>"
				//+ "<TD><B><FONT></FONT></B></TD></TR>"
				+ "</TBODY></TABLE>"
				+ "<BR>"
			//	+ "<BR><HR>"
				+ "<H3 align=center><FONT color=#187030><B>Summary of Consolidated Test Report </B></FONT></H3></TR>"
				+ "<TR bgColor=#ffffdd>";
		fos.write(cont.getBytes());

		cont = "<TABLE width=100% align=center border=1>"
				+ "<TBODY>"
				+ "<TR bgColor=#376936>"
				+ "<TD><FONT color=#ffffff><B>TestCase Name</B></FONT></TD>"
				+ "<TD align=middle><FONT color=#ffffff><B>Status</B></FONT></TD>"
				+ "<TD align=middle><FONT color=#ffffff><B>Time Taken</B></FONT></TD>"
				+ "<TD align=middle><FONT color=#ffffff><B>Remarks</B></FONT></TD>";
		fos.write(cont.getBytes());

		fileInputStream = new FileInputStream(Global.getTestResultFileAbsolutePath());
		wb = WorkbookFactory.create(fileInputStream);
		s = wb.getSheet("Result");
		rowCnt = ((org.apache.poi.ss.usermodel.Sheet) s).getLastRowNum();
		row = null;
		for (int j = 1; j <= rowCnt; j++) {
			row = s.getRow(j);
			org.apache.poi.ss.usermodel.Cell cell = row.getCell(1);
			String testCasename = cell.getRichStringCellValue().toString();
			cell = row.getCell(2);
			String Sts = cell.getRichStringCellValue().toString();
			cell = row.getCell(4);
			String timeTaken = cell.getRichStringCellValue().toString();
			cell = row.getCell(5);
			String remarks = cell.getRichStringCellValue().toString();
			remarks = remarks.replaceAll("\n", "<br>");
			cont = "<TR bgColor=#ffffdd>";
			fos.write(cont.getBytes());
			cont = "<TD>" + testCasename + "</TD>";
			//String stackTraceInfo = Sts.equalsIgnoreCase("PASS")? "": "<br/><a onclick=\"showTrace()\" href=\"#trace-modal" + j +"\"> Error </a><br/> <div id=\"#trace-modal" + j +"\" class=\"traceinfo\"><div><a href=\"#close\" onclick=\"closeTraceModal()\" title=\"Close\" class=\"close\">X</a>" + Remarks + "</div></div>";
			String stackTraceInfo = Sts.equalsIgnoreCase("PASS")? "": "<a onclick=\"showTrace()\" href=\"#trace-modal" + j +"\"> Error </a> <div id=\"#trace-modal" + j +"\" class=\"traceinfo\"><div align=\"left\"><a href=\"#close\" onclick=\"closeTraceModal()\" title=\"Close\" class=\"close\">X</a>" + remarks + "</div></div>";
			String bgcolor = Sts.equalsIgnoreCase("PASS") ? "bgcolor=\"#90EE90\"" : "bgcolor=\"#FFB6C1\"";
			String fontcolor = Sts.equalsIgnoreCase("PASS") ? "<font color=\"DarkGreen\">" : "<font color=\"DarkRed\">";
		    //str +=  '<td ">' + data.message+ stackTraceInfo+ '</td>';
			//System.out.println(stackTraceInfo);
			cont1 = "<TD align=middle " + bgcolor + ">" + fontcolor + Sts + "</TD>";
			cont2 = "<TD align=middle>" + timeTaken + "</TD>";
			cont3 = "<TD align=middle>" + stackTraceInfo + "</TD>";
			fos.write(cont.getBytes());
			fos.write(cont1.getBytes());
			fos.write(cont2.getBytes());
			fos.write(cont3.getBytes());
			
			
		}
		cont = "</B></TD></TR></TBODY></TABLE>"
				+ "<BR><BR><BR><BR><BR><BR>"
				+ "<TABLE width=100% align=center bgColor=#187030 border=0>"
				+ "<TBODY>"
				+ "<TR>"
				+ "<TD align=middle><FONT face=Verdana, Arial color=#ffffff size=1>Report Generated by "+Global.companyName+" Test Automation Framework </FONT></TD>"
				+ "<TD align=middle><FONT face=Verdana, Arial color=#ffffff size=1>@ "+Global.copyRightYear+ " "+Global.companyName+"</FONT></TD></TR></TBODY></TABLE></U></U></BODY></HTML>";
		fos.write(cont.getBytes());
		fos.close();

	}

	/**
	 * To Get the Required Test data Size
	 * 
	 */
	public List getReqCount(String filePath) {
		try {
			FileInputStream fileInputStream = new FileInputStream(filePath);
			Workbook wb = WorkbookFactory.create(fileInputStream);
			Sheet s = wb.getSheetAt(0);
			int rowCnt = s.getLastRowNum();
			Row row = null;
			row =  s.getRow(1);
			Cell cell;
			int cols = s.getRow(1).getPhysicalNumberOfCells();
			java.util.List listArr = new ArrayList();
			for (int c = 0; c < cols; c++) {
				cell =  row.getCell((short) c);
				if (cell != null
						&& cell.getStringCellValue().equalsIgnoreCase("YES")) {
					listArr.add(new Integer(c));
				}
			}
			return listArr;
		} catch (Exception ioe) {
			ioe.printStackTrace();
			return null;
		}
	}

	/**
	 * To Read Test Data
	 * 
	 */
	public String[][] Readtestdata(String filePath, int col) {
		try {
			int c;
			FileInputStream fileInputStream = new FileInputStream(filePath);
			Workbook wb = WorkbookFactory.create(fileInputStream);
			Sheet s = wb.getSheetAt(0);
			int rowCnt = s.getLastRowNum();
			Row row = null;
			Cell cell = null;
			int cols =  s.getRow(1)
					.getPhysicalNumberOfCells();
			String[][] values = new String[rowCnt + 1][cols];
			java.util.List listArr = new ArrayList();
			// To read keyword
			//System.out.println(rowCnt);
			for (int r = 2; r <= rowCnt; r++) {
				row =  s.getRow(r);
				for (c = 0; c < 14; c++) {
					cell = row.getCell((short) c);
					if (cell != null) {
						cell = row.getCell(c);
						switch (cell.getCellType()) {
						case Cell.CELL_TYPE_STRING:
							values[r][c] = cell.getRichStringCellValue().getString();
							break;
						case Cell.CELL_TYPE_FORMULA:
							
							values[r][c] = String.valueOf(cell.getNumericCellValue());
							break;
						case Cell.CELL_TYPE_NUMERIC:
							
							if (DateUtil.isCellDateFormatted(cell)) {
								double dt = cell.getNumericCellValue();
								Calendar cal = Calendar.getInstance();
								cal.setTime(DateUtil.getJavaDate(dt));
								values[r][c] = String.valueOf(cal.get(Calendar.YEAR));
								values[r][c] = cal.get(Calendar.MONTH) + 1 + "/"
										+ cal.get(Calendar.DAY_OF_MONTH) + "/" + values[r][c];
							
							} 
							else{
							values[r][c] = String.valueOf(cell.getNumericCellValue());
							}
							break;
						case Cell.CELL_TYPE_BOOLEAN:
							
							values[r][c] = String.valueOf(cell.getBooleanCellValue());
							break;
						
						}
						
						//System.out.println(values[r][c]);
					}
				}
				// To Read test data column
				cell = ((org.apache.poi.ss.usermodel.Row) row)
						.getCell((short) col);
				if (cell != null) {
					cell = row.getCell(col);
					if(cell.getCellType()==Cell.CELL_TYPE_STRING){
						values[r][c] = cell.getRichStringCellValue().toString();
						}else if(cell.getCellType()==Cell.CELL_TYPE_NUMERIC){
							values[r][c] = Double.toString(cell.getNumericCellValue());
						}
					// System.out.println(values[r][col]);
				}
			}
			return values;
		} catch (Exception ioe) {
			ioe.printStackTrace();
			return null;
		}
	}

	
	
	/**
	 * takes the Screen Shot on failure and return the Screen shot file path
	 * 

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
		Process substProcess = Runtime.getRuntime().exec(cmds, null, null);
		
		return filePath + "/" + fileName;
	}

	/**
	 * To convert seconds to hr:min:sec
	 * 
	 */
	public String getTime(long longVal) {
		// long longVal = biggy.longValue();
		int hours = (int) longVal / 3600;
		int remainder = (int) longVal - hours * 3600;
		int mins = remainder / 60;
		remainder = remainder - mins * 60;
		int secs = remainder;
		return hours + ":" + mins + ":" + secs;
	}

	/**
	 * To update Get Data Table
	 * 
	 *
	 */
	public void updateValue(String filePath, int row, int col,String setValue) throws Exception {
		System.out.println(filePath);
		logger.info(filePath);
		System.out.println(row);
		logger.info(row);
		System.out.println(col);
		logger.info(col);
		System.out.println(setValue);
		logger.info(setValue);
		FileInputStream fileInputStream = new FileInputStream(filePath);
		Workbook wb = WorkbookFactory.create(fileInputStream);
		Sheet s = wb.getSheetAt(0);
		int rowCnt = s.getLastRowNum();
		Row row1 = null;
		row1 =s.getRow(row);
		Cell cell;
		cell = row1.getCell((short) col + 1);
		//System.out.println("------>Test Step " + (row - 1) + "). :: " + SetValue);
		if (cell == null) {
			cell = row1.createCell(col + 1);
		}
		cell.setCellValue(setValue.toString());
		FileOutputStream fileOut = new FileOutputStream(filePath);
		wb.write(fileOut);
		fileOut.flush();
		fileOut.close();
	}
	

	/**
	 * Getting Properties Values
	 * 
	 *
	 */
	public String getPropValues(String propKey) throws IOException {
		try {
			Properties prop = new Properties();
			//System.out.println("Global.propFilename : === "+Global.propFileName);
			File file = new File(Global.getPropFile());
			FileInputStream fileInput = new FileInputStream(file);
			prop.load(fileInput);
			String PropValue = prop.getProperty(propKey);
			return PropValue;
		} catch (FileNotFoundException e) {
			throw new FileNotFoundException("property file '" + Global.getPropFile() + "' not found in the classpath");
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * Getting Properties Values
	 * 
	 *
	 */
	public void sendEmail() throws AddressException, MessagingException{
		
		String host = Global.smtpHost;
		String from = Global.emailFromUser; //Edit this line
		String passw = Global.emailUserPassword;      //Edit This line
		String subject=  "<<< ***** " + Global.getEnvironment().toUpperCase() + " " + Global.getClientName() + " " + Global.emailSubject + " - " + getTodayDate() + " ***** >>>" ;    //Email Subject EDIT
		String bodytext=Global.emailBodyText;  //Email body EDIT
		String[] to = Global.emailRecipients.split(","); // array of email receivers
		String path=Global.globalResultFolder+"\\"; 
		String filenameHTML="Execution_Report.html";   //name of file for java email attachment.
		String filenameExcel="TestResult"+ Global.getExcelExtn();

		Properties props = System.getProperties();
		props.put("mail.smtp.starttls.enable", "true"); // added this line
		props.put("mail.smtp.host", host);
		props.put("mail.smtp.user", from);
		props.put("mail.smtp.password", passw);
		props.put("mail.smtp.port", "587");
		props.put("mail.smtp.auth", "true");

		Session session1 = Session.getDefaultInstance(props, null);
		MimeMessage message = new MimeMessage(session1);
		message.setFrom(new InternetAddress(from));

		InternetAddress[] toAddress = new InternetAddress[to.length];

		// To get the array of addresses
		for( int i=0; i < to.length; i++ ) { // changed from a while loop
			toAddress[i] = new InternetAddress(to[i]);
		}
		
		for( int i=0; i < toAddress.length; i++) { // changed from a while loop
			message.addRecipient(Message.RecipientType.TO, toAddress[i]);
		}
		message.setSubject(subject);
		BodyPart messageBodyPart = new MimeBodyPart();

		// Fill the message
		messageBodyPart.setText(bodytext);

		// Create a multipart message
		Multipart multipart = new MimeMultipart();

		// Set text message part
		multipart.addBodyPart(messageBodyPart);

		// Part two is attachment
		messageBodyPart = new MimeBodyPart();
		DataSource source = new FileDataSource(path+filenameHTML);
		messageBodyPart.setDataHandler(new DataHandler(source));
		messageBodyPart.setFileName(filenameHTML);
	
		multipart.addBodyPart(messageBodyPart);

		MimeBodyPart messageBodyPart2 = new MimeBodyPart(); 
		DataSource source2 = new FileDataSource(Global.getTestResultFileAbsolutePath()); 
		messageBodyPart2.setDataHandler( new DataHandler(source2)); 
		messageBodyPart2.setFileName(filenameExcel); 
		multipart.addBodyPart(messageBodyPart2); 
		
		// Send the complete message parts
		message.setContent(multipart );
		Transport transport = session1.getTransport("smtp");
		transport.connect(host, from, passw);
		transport.sendMessage(message, message.getAllRecipients());
		transport.close();
		System.out.println("Email Message has been  sent");
		logger.info("Email Message has been  sent");
	}

	/**
	 * writeListToSheet
	 * 
	 *
	 */
	public void writeListToSheet(List<String> dataList, int rowOffset,String template) throws InvalidFormatException, IOException{
		try{
			int col=0;
		InputStream inp = new FileInputStream(template);
	    Workbook wb = WorkbookFactory.create(inp);
	    Sheet sheet = wb.getSheetAt(0);
	    int rows=sheet.getLastRowNum();
	    Row row =sheet.createRow(++rowOffset);
	    for(String data : dataList){
	    	//sheet.shiftRows(rowOffset++,rows,1);
	    	//Row row = sheet.getRow(++rowOffset);
	    	Cell name = row.createCell(col++);
	    	name.setCellValue(data);
	    }
	    FileOutputStream fileOut = new FileOutputStream(template);
	    wb.write(fileOut);
		fileOut.flush();
	    fileOut.close();
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}

	/**
	 * writeSheet
	 * 
	 *
	 */
	public void writeSheet(HashMap<String,String> dataList, int rowOffset,String template) throws InvalidFormatException, IOException{
		try{
			File apiDataoutPutFile = new File(template);
		FileInputStream inp = new FileInputStream(template);
		OPCPackage pkg = OPCPackage.open(inp);
		Workbook wb = WorkbookFactory.create(pkg);
		//XSSFWorkbook wb = new XSSFWorkbook(pkg);
	    Sheet sheet = wb.getSheetAt(0);
	    int rows=sheet.getLastRowNum();
	    for(String key : dataList.keySet()){
	    	//sheet.shiftRows(rowOffset++,rows,1);
	    	Row row =sheet.createRow(++rowOffset);
	    	//Row row = sheet.getRow(++rowOffset);
	    	Cell name = row.createCell(0);
	    	name.setCellValue(key);
	    	Cell label = row.createCell(1);
	    	label.setCellValue(dataList.get(key));
	    }
	    FileOutputStream fileOut = new FileOutputStream(template);
	    wb.write(fileOut);
		fileOut.flush();
	    fileOut.close();
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	
	
	/**
	 * Getting Data of Variable name from the Spread Sheet
	 * 
	 *
	 */

	public String getVariableData(String dataSheetPath, String variableName) throws IOException {
		try {
			
			String variableValue = null;
			InputStream file = new FileInputStream(dataSheetPath);
			Workbook wb = WorkbookFactory.create(file);
			Sheet s =  wb.getSheetAt(0);
			int rowCnt = s.getLastRowNum();
			int cols =s.getRow(1).getPhysicalNumberOfCells();
			Cell cellVN = null;
			Cell cellVeriableValue;
			Row row;
			for(int r=1;r<=rowCnt;r++){
				row = s.getRow(r);
				cellVN = row.getCell(1);
				if (cellVN != null) {
					cellVeriableValue = row.getCell(2);
					String cellVariableName = cellVN.getRichStringCellValue().getString();
					if (variableName.trim().equalsIgnoreCase(cellVariableName.trim())){
						variableValue = cellVeriableValue.getRichStringCellValue().toString();
					}
					
				}
			}
			
			return variableValue;
		} catch (FileNotFoundException e) {
			throw new FileNotFoundException("DataSheet  '" + dataSheetPath + "' not found......");
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InvalidFormatException e) {
			
			e.printStackTrace();
		}
		return null;
	}

	
	public  String date(int businessDays){
	    Calendar cal2 = Calendar.getInstance();
	    Calendar cal = Calendar.getInstance(); 
	    int totalDays= businessDays/5*7;
	    int remainder = businessDays % 5;
	    cal2.add(cal2.DATE, totalDays); 
	    switch(cal.get(Calendar.DAY_OF_WEEK)){
	        case 1:
	                break;
	        case 2: 
	                break;
	        case 3: 
	                if(remainder >3)
	                cal2.add(cal2.DATE,2);
	                break;
	        case 4: 
	                if(remainder >2)
	                cal2.add(cal2.DATE,2);
	                break;
	        case 5: 
	                if(remainder >1)
	                cal2.add(cal2.DATE,2);
	                break;
	        case 6: 
	                if(remainder >1)
	                cal2.add(cal2.DATE,2);
	                break;
	        case 7: 
	                if(remainder >1)
	                cal2.add(cal2.DATE,1);
	                break;
	    }
	    cal2.add(cal2.DATE, remainder); 
	    DateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy");
	    Date date = cal2.getTime();
	    String dateReturn = dateFormat.format(date);
	    return dateReturn;

	}
	
	
	public  String emaildate(int emaildate,int autoclosedate){
		Date date=new Date();
	    Calendar calendar = Calendar.getInstance();
	    date=calendar.getTime(); 
	    SimpleDateFormat s;
	    s=new SimpleDateFormat("dd-MMM-yyyy");
	    int days = autoclosedate;
	    for(int i=0;i<days;)
	    {
	    	 
	        calendar.add(Calendar.DAY_OF_MONTH, 1);
	        
	       if(calendar.get(Calendar.DAY_OF_WEEK)<=5)
	        {
	            i++;
	        }

	    }
	    calendar.add(calendar.DATE,-emaildate);
	    date=calendar.getTime(); 
	    String datereturn = s.format(date);
	    //System.out.println("autoemaildate :"+datereturn);
	    
	    return datereturn;

	}
	
	
	
	public Calendar addingdaytocalender(Calendar calendar){
		if(calendar.get(Calendar.DAY_OF_WEEK)==1)
        {
    	   calendar.add(Calendar.DAY_OF_MONTH, 1);
        }
       else if(calendar.get(Calendar.DAY_OF_WEEK)==7){
    	   calendar.add(Calendar.DAY_OF_MONTH, 2);
       }
       else if(calendar.get(Calendar.DAY_OF_WEEK)==6){
    	   calendar.add(Calendar.DAY_OF_MONTH, 3);
       }else{
    	   calendar.add(Calendar.DAY_OF_MONTH, 1);
       }
		
		return calendar;
	}
	
	

	public void sendEmailevery15min() throws AddressException, MessagingException{
		if (Global.triggerjob.equalsIgnoreCase("Yes")){
		String host = Global.smtpHost;
		String from = Global.emailFromUser; //Edit this line
		String passw = Global.emailUserPassword;      //Edit This line
		String subject=Global.emailSubject + " of " + Global.projectName + " (Ongoing Update)";    //Email Subject EDIT
		String bodytext=Global.emailBodyText;  //Email body EDIT
		String[] to = Global.emailRecipients.split(","); // array of email receivers
		String path=Global.resultlogpath; 
		String filenameHTML = Global.filenameHTML;
		Properties props = System.getProperties();
		props.put("mail.smtp.starttls.enable", "true"); // added this line
		props.put("mail.smtp.host", host);
		props.put("mail.smtp.user", from);
		props.put("mail.smtp.password", passw);
		props.put("mail.smtp.port", "587");
		props.put("mail.smtp.auth", "true");
	
		Session session1 = Session.getDefaultInstance(props, null);
		MimeMessage message = new MimeMessage(session1);
		message.setFrom(new InternetAddress(from));
	
		InternetAddress[] toAddress = new InternetAddress[to.length];
	
		// To get the array of addresses
		for( int i=0; i < to.length; i++ ) { 
			toAddress[i] = new InternetAddress(to[i]);
			System.out.println("toAddress[i] "+toAddress[i]);
			logger.info("toAddress[i] "+toAddress[i]);
		}
		
		for( int i=0; i < toAddress.length; i++) { 
			message.addRecipient(Message.RecipientType.TO, toAddress[i]);
		}
		message.setSubject(subject);
		BodyPart messageBodyPart = new MimeBodyPart();
	
		// Fill the message
		messageBodyPart.setText(bodytext);
	
		// Create a multipart message
		Multipart multipart = new MimeMultipart();
	
		// Set text message part
		multipart.addBodyPart(messageBodyPart);
	
		// Part two is attachment
		messageBodyPart = new MimeBodyPart();
		DataSource source = new FileDataSource(path);
		messageBodyPart.setDataHandler(new DataHandler(source));
		messageBodyPart.setFileName(filenameHTML);
	
		multipart.addBodyPart(messageBodyPart);
	
		// Send the complete message parts
		message.setContent(multipart);
		Transport transport = session1.getTransport("smtp");
		transport.connect(host, from, passw);
		System.out.println(message);
		System.out.println(message.getAllRecipients());
		transport.sendMessage(message, message.getAllRecipients());
		transport.close();
		System.out.println("Email Message has been  sent");
		logger.info("Email Message has been  sent");
		}
		else{
			
		}

	}	
	
	
	public void triggerjob() throws SchedulerException{
		try {
		JobDetail job = new JobDetail();  //class to create a new job
    	job.setName("SendmailJobName");
    	job.setJobClass(Emailtriggerjob.class);
    	CronTrigger trigger = new CronTrigger();  // how to trigger the job, wat are the instance
    	trigger.setName("SendmailTriggerName");
    	trigger.setCronExpression("0 0/55 * * * ?");
    	Scheduler scheduler = new StdSchedulerFactory().getScheduler();
    	scheduler.start();
    	scheduler.scheduleJob(job, trigger);
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}
	
	public void stoptrigger(int i) throws SchedulerException{
		try {
			JobDetail job1 = new JobDetail();  //class to create a new job
	    	job1.setName("Exitsendmail");
	    	job1.setJobClass(Triggerexitjob.class);
	    	CronTrigger trigger1 = new CronTrigger();  // how to trigger the job, wat are the instance
	    	trigger1.setName("ExitsendmailTriggerName");
	    	String time = "59 55 "+17+" * * ?";
	    	Scheduler scheduler1 = new StdSchedulerFactory().getScheduler();
	    	scheduler1.start();
	    	scheduler1.scheduleJob(job1, trigger1);
	    	trigger1.setCronExpression("59 55 "+17+" * * ?");
			} catch (ParseException e) {
				e.printStackTrace();
			}
	}
	public void printStartOfExecution(String scriptName){
		System.out.println("#############################     S T A R T   EXECUTION   ###################################");
		System.out.println( "@@@@@@@@@@@@@@@@@@@@@@@@@@@@"+"\t"+"\t"+scriptName+"\t"+"\t"+"@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
		System.out.println("#############################################################################################");
		//logging
		logger.info("#############################     S T A R T   EXECUTION   ###################################");
		logger.info("@@@@@@@@@@@@@@@@@@@@@@@@@@@@"+"\t"+"\t"+scriptName+"\t"+"\t"+"@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
		logger.info("#############################################################################################");
		
		
	}
	
	public void printEndOfExecution(String scriptName){
		System.out.println( "################################################################################" + "\n"+
				"@@@@@@@@@@@@@@@@@"+"\t"+"\t"+scriptName+"\t"+"\t"+"@@@@@@@@@@@@@@@@@@@@@" + "\n"+
				"###############################     E N D     ###################################");
		//logging
		logger.info("################################################################################" + "\n"+
				"@@@@@@@@@@@@@@@@@"+"\t"+"\t"+scriptName+"\t"+"\t"+"@@@@@@@@@@@@@@@@@@@@@" + "\n"+
				"###############################     E N D     ###################################");
	
	}
	
	public void printExecutionOver(){
		System.out.println("//////////////////////////////////////////////////////////////////////////////////\n"+
				"///////////////////////// E X E C U T I O N--O V E R  ////////////////////////////\n"+
				"//////////////////////////////////////////////////////////////////////////////////");
		//logging
				logger.info("//////////////////////////////////////////////////////////////////////////////////\n"+
						"///////////////////////// E X E C U T I O N--O V E R  ////////////////////////////\n"+
						"//////////////////////////////////////////////////////////////////////////////////");

	}
	
	public String getBrowserAndVersion(WebDriver driver) {
		String browser_version = null;
		Capabilities cap = ((RemoteWebDriver) driver).getCapabilities();
		String browsername = cap.getBrowserName();
		// This block to find out IE Version number
		if ("internet explorer".equalsIgnoreCase(browsername)) {
			String uAgent = (String) ((JavascriptExecutor) driver).executeScript("return navigator.userAgent;");
			//System.out.println("uAgent :" + uAgent);
			logger.info("uAgent :" + uAgent);
			//uAgent return as "MSIE 8.0 Windows" for IE8
			if (uAgent.contains("MSIE") && uAgent.contains("Windows")) {
				browser_version = uAgent.substring(uAgent.indexOf("MSIE")+5, uAgent.indexOf("Windows")-2);
			} else if (uAgent.contains("Trident/7.0")) {
				browser_version = "11.0";
			} else {
				browser_version = "0.0";
			}
		} else
		{
			//Browser version for Firefox and Chrome
			browser_version = cap.getVersion();// .split(".")[0];
		}
		String browserversion = browser_version.substring(0, browser_version.indexOf("."));
		return browsername + " " + browserversion;
	}
 
	public String getOsName () {
		String os = System.getProperty("os.name").toLowerCase();
		if (os.contains("win")) {
			return "Windows";
		} else if (os.contains("nux") || os.contains("nix")) {
			return "Linux";
		}else if (os.contains("mac")) {
			return "Mac";
		}else if (os.contains("sunos")) {
			return "Solaris";
		}else {
			return "Other";
		}
	}
	public String splitStr(String str,DynamicVarRepository dynamicVariables){
		String [] arraylist=str.split("=");
		String key=arraylist[0].toUpperCase();
		String value=arraylist[1];
		
		dynamicVariables.addObjectToMap("#"+key+"#",value);
		System.out.println("dynamic values are"+dynamicVariables.getDynamicVariablesMap());
		logger.info("dynamic values are"+dynamicVariables.getDynamicVariablesMap());
		return "";
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
	
	public void updateGlobalValues(String client, String browser, String environment, String testsuite, String emailRecipents){
		Global.setBrowser(!browser.isEmpty() ? browser : Global.getBrowser());
		Global.setEnvironment(!environment.isEmpty() ? environment : Global.getEnvironment());
		Global.setClientName(!client.isEmpty() ? client : Global.getClientName());
		//Global.setProjectName(!client.isEmpty() ? client : Global.getClientName());
		Global.setConfigurationFileName(!testsuite.isEmpty() ? testsuite : Global.getConfigurationFileName());
		String emailResipentsUpdated = (!emailRecipents.isEmpty() ? Global.getEmailRecipients() + "," + emailRecipents : Global.getEmailRecipients());
		Global.setEmailRecipients(emailResipentsUpdated);
		Global.setOsName(getOsName());
		
		System.out.println("Client Name : " + Global.getClientName());
		System.out.println("Browser  : " + Global.getBrowser());
		System.out.println("Environment : " + Global.getEnvironment());
		System.out.println("Operating System :" + Global.getOsName());
		System.out.println("Test Suite : " + Global.getConfigurationFileName());
		System.out.println("Email Resipents list :" + Global.getEmailRecipients());

		logger.info("Client Name : " + Global.getClientName());
		logger.info("Browser  : " + Global.getBrowser());
		logger.info("Environment : " + Global.getEnvironment());
		logger.info("Operating System :" + Global.getOsName());
		logger.info("Test Suite : " + Global.getConfigurationFileName());
		logger.info("Email Resipents list :" + Global.getEmailRecipients());

	}
	
	
	public void printDynamicVarsToLog(DynamicVarRepository dynamicVarRepo){
		Map<String, String> DynamicVarMap = dynamicVarRepo.getDynamicVariablesMap();
		System.out.println("<<<<<     Dynamic variables list     >>>>>");
		logger.info("<<<<<     Dynamic variables list     >>>>>");
		for (String key: DynamicVarMap.keySet()) {
		    logger.info(key + "=" + DynamicVarMap.get(key));
		    System.out.println(key + "=" + DynamicVarMap.get(key));
		}
		System.out.println("<<<<<<<<<<<<<<<<<<<<<>>>>>>>>>>>>>>>>>>>>>>");
		logger.info("<<<<<<<<<<<<<<<<<<<<<>>>>>>>>>>>>>>>>>>>>>>");
		
	}

	public String getNextBusinessDay() throws InvalidFormatException, IOException{
		Date date=new Date();
		Calendar calendar = Calendar.getInstance();
	    SimpleDateFormat s = new SimpleDateFormat("MM/dd/yyyy");
	    calendar = addingdaytocalender(calendar);
	    date=calendar.getTime(); 
	    return s.format(date);

	}
	
	
}	
