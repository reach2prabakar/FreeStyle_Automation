package com.planit.automation.processor;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Calendar;
import java.util.Iterator;
import java.util.LinkedHashMap;
import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import com.planit.automation.library.Global;


public class TestSuite {


	private static Logger logger = Logger.getLogger(TestSuite.class.getName());
	private Sheet obWsheet;
	private Workbook obWbook;
	private String filePath;
	private String failedTestSuitePath;

	private LinkedHashMap<String, String[]> testSuiteMap = new LinkedHashMap<String, String[]>();
	private LinkedHashMap<String, String[]> dataProviderMap = new LinkedHashMap<String, String[]>();

	public static final int PROJECT_FOLDER_INDEX = 0;
	public static final int MODULE_FOLDER_INDEX = 1;
	public static final int TESTSCRIPT_NAME_INDEX = 2;
	public static final int RUN_INDEX = 3;
	public static final int TEST_TYPE_INDEX = 4;
	public static final int DEFECT_ID_INDEX = 5;
	public static final int ENTIRE_SCRIPT_PATH = 6; // It is created internally not from the testsuite.xlsx
	public static final int API_LOGFILE = 7; // It is created internally not from the testsuite.xlsx

	public static final int TESTSUITE_COLUMNS_COUNT = 8;
	
	public TestSuite(String filePath) throws Exception{

		this.filePath = filePath;
		ReadTestscriptProperties(filePath);
	}


	private void ReadTestscriptProperties(String filePath) throws Exception{
		try{
			String projectFolder;
			String moduleFolder;
			String testScriptName;
			String runFlag;
			String testType;
			String defectid;
			String entireScriptPath;
			String apiLogPath;
			if(new File(filePath).exists()){
			FileInputStream fileInputStream = new FileInputStream(filePath);
			obWbook = WorkbookFactory.create(fileInputStream);
			obWsheet =  obWbook.getSheet("Scripts");
			Iterator<Row> rowIterator = obWsheet.iterator();

			while (rowIterator.hasNext())
			{
				Row row = rowIterator.next();
				if(row.getCell(TESTSCRIPT_NAME_INDEX) != null && !row.getCell(TESTSCRIPT_NAME_INDEX).getStringCellValue().isEmpty()){

					projectFolder  = getStringCellValue(row.getCell(PROJECT_FOLDER_INDEX));
					moduleFolder   = getStringCellValue(row.getCell(MODULE_FOLDER_INDEX));
					testScriptName = getStringCellValue(row.getCell(TESTSCRIPT_NAME_INDEX));
					runFlag        = getStringCellValue(row.getCell(RUN_INDEX));
					testType       = getStringCellValue(row.getCell(TEST_TYPE_INDEX));
					defectid	   = getStringCellValue(row.getCell(DEFECT_ID_INDEX));
					File f = new File(Global.getDatatableFolder()+"\\"+ projectFolder +"\\"+moduleFolder+"\\"+ testScriptName+ Global.getExcelExtn());
					if(f.exists()) { 
						entireScriptPath = Global.getDatatableFolder()+"\\"+ projectFolder +"\\"+moduleFolder+"\\"+ testScriptName+ Global.getExcelExtn();
					}else {
						entireScriptPath = Global.getJavaTestclassFolder()+"\\"+ projectFolder +"\\"+moduleFolder+".java";
					}
					
					apiLogPath     = Global.getDatatableFolder()+"\\"+projectFolder+"\\"+moduleFolder+"\\API_POST_DATA\\"+testScriptName+"_POST.xlsx";

					String[] testScriptPropeties = {projectFolder, moduleFolder, testScriptName, runFlag, 
							testType, defectid, entireScriptPath, apiLogPath};

					addObjectToTestSuiteMap(testScriptName, testScriptPropeties);

					if (runFlag.equalsIgnoreCase("yes")){
						addObjectToDataProviderMap(testScriptName, testScriptPropeties);
					}
				}

			}
			

			obWbook.close();
		
			} else {
				System.out.println("ERROR : TestSuite file not found ! " + filePath);
				logger.error("ERROR : TestSuite file not found ! " + filePath);
				throw new Exception("ERROR : TestSuite file not found ! " + filePath);
			}
			
			}catch (Exception e){

			System.out.println("ERROR : Exception occurred while reading the TestSuite file " + filePath);
			logger.error("ERROR : Exception occurred while reading the TestSuite file " + filePath);
			throw new Exception("ERROR : Exception occurred while reading the TestSuite file " + filePath + "\n" 
					+ e.getMessage());
		}
		

	}



	public String[] getTestScriptProperties(String key){

		return testSuiteMap.get(key);

	}


	private void addObjectToTestSuiteMap(String key, String[] value){

		this.testSuiteMap.put(key, value);

	}


	private void addObjectToDataProviderMap(String key, String[] value){

		this.dataProviderMap.put(key, value);

	}

	public LinkedHashMap<String, String[]> getTestSuiteMap(){

		return testSuiteMap;

	}


	public LinkedHashMap<String, String[]> getDataProviderMap(){

		return dataProviderMap;

	}

	public Object[][] getDataProviderObject() throws Exception{

		LinkedHashMap<String, String[]> dataProvideMapInfo = getDataProviderMap();
		int  counter = 0;
		Object[][] dataProvider = new Object[dataProvideMapInfo.size()][2];

		for (String testScriptName : dataProvideMapInfo.keySet()) {
			dataProvider[counter][0] = testScriptName; // test script Name
			dataProvider[counter][1] = dataProviderMap.get(testScriptName); // test script absolute path
			counter++;
		}
		if(counter == 0){
			System.out.println("ERROR :  No Test cases found / No Test case flag is set as 'Yes' from Test Suite.");
			logger.error("ERROR :  No Test cases found / No Test case flag is set as 'Yes' from Test Suite.");
			throw new Exception(" ERROR :  No Test cases found / No Test case flag is set as 'Yes' from Test Suite.");
		}
		return dataProvider;

	}



	public String getFailedTestSuitePath() {
		return failedTestSuitePath;
	}


	public void setFailedTestSuitePath(String failedTestSuitePath) {
		this.failedTestSuitePath = failedTestSuitePath;
	}


	public void creatFailedTestSuite() throws Exception, IOException{

		setFailedTestSuitePath(Global.getFailedTestSciptsSuite());
		System.out.println("Failure Test Script suite path : " + Global.getFailedTestSciptsSuite());
		String outputFileName = getFailedTestSuitePath();
		copyTestSuite(outputFileName);

		FileInputStream fileInputStream = new FileInputStream(outputFileName);
		Workbook testSuiteWbook = WorkbookFactory.create(fileInputStream);

		Sheet scriptsSheet = testSuiteWbook.getSheet("Scripts");

		int rowCnt = scriptsSheet.getLastRowNum();
		Row row = null;

		for (int rowIndex = 1; rowIndex <= rowCnt; rowIndex++) {

			row = scriptsSheet.getRow(rowIndex);
			if (row != null){
				scriptsSheet.removeRow(row);
			}

		}	

		FileOutputStream fileOut = new FileOutputStream(outputFileName);
		testSuiteWbook.write(fileOut);
		fileOut.flush();
		fileOut.close();
	}

	public void copyTestSuite(String outputFilePath) {
		if(!outputFilePath.equalsIgnoreCase(filePath)){
			InputStream inStream = null;
			OutputStream outStream = null;
			try {

				File outputFile = new File(outputFilePath);

				if(!outputFile.exists()){
					outputFile.createNewFile();
				}

				inStream = new FileInputStream(new File(filePath));
				System.out.println("Test Suite path : " + filePath);
				System.out.println("Filed Test Suite path : " + outputFilePath);

				outStream = new FileOutputStream(new File(outputFilePath));

				byte[] buffer = new byte[1024];
				int length;
				while ((length = inStream.read(buffer)) > 0) {
					outStream.write(buffer, 0, length);
				}
				inStream.close();
				outStream.close();

			} catch (IOException e) {
				System.out.println(e.getMessage());
				logger.error(e.getMessage());
			}

		}else {
			System.out.println("Warning : Both testuite file name and testsuite_failed scripts file names are same. Hence new file has not been created!! ");
			logger.warn("Warning : Both testuite file name and testsuite_failed scripts file names are same. Hence new file has not been created!! ");
		}

	}
	
	private String getStringCellValue(Cell cell){

		String cellValue = "";

		if (cell != null) {
			switch (cell.getCellType()) {

			case Cell.CELL_TYPE_STRING:
				cellValue = cell.getRichStringCellValue().getString();
				break;

			case Cell.CELL_TYPE_FORMULA:

				//cellValue = String.valueOf(cell.getNumericCellValue());
				System.out.println("Formula is " + cell.getCellFormula());
		        
				switch(cell.getCachedFormulaResultType()) {
		            case Cell.CELL_TYPE_NUMERIC:
		            	cellValue = String.valueOf(cell.getNumericCellValue());
		                break;
		            case Cell.CELL_TYPE_STRING:
		            	cellValue = cell.getStringCellValue();
		                break;
		        }
				
				break;

			case Cell.CELL_TYPE_NUMERIC:

				if (DateUtil.isCellDateFormatted(cell)) {
					double dt = cell.getNumericCellValue();
					Calendar cal = Calendar.getInstance();
					cal.setTime(DateUtil.getJavaDate(dt));
					cellValue = String.valueOf(cal.get(Calendar.YEAR));
					cellValue = cal.get(Calendar.MONTH) + 1 + "/"+ cal.get(Calendar.DAY_OF_MONTH) + "/" + cellValue;
				} 
				else{
					cellValue = String.valueOf(cell.getNumericCellValue());
				}
				break;

			case Cell.CELL_TYPE_BOOLEAN:

				cellValue = String.valueOf(cell.getBooleanCellValue());
				break;

			}

		}

		return cellValue;
	}


}
