package com.planit.automation.processor;

import java.util.Calendar;

import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;

public class TestStep {


	private static Logger logger = Logger.getLogger(TestStep.class.getName());

	private static final int PAGE_NAME_INDEX = 0;
	private static final int ACTION_DESCIRPTION_INDEX = 1;
	private static final int OBJECT_PROPERTY_INDEX = 2;
	private static final int KEYWORD_INDEX = 3;
	private static final int ACTION_INDEX = 4;
	private static final int FLOW_INDEX = 5; 
	private static final int TESTDATA_INDEX = 6; 
	private static final int API_URL_INDEX = 7;
	private static final int API_WEBSERVICE_INDEX = 8; 
	private static final int API_JSON_INDEX = 9;
	private static final int API_JSON_RESPONSE_INDEX = 10;
	private static final int FILENAME_INDEX = 11;
	private static final int VARIABLE_NAME_INDEX = 12; 
	private static final int OUTPUT_VARIABLES_INDEX = 13;
	public static final int RESULT_CELL_INDEX = 14;
	private static final int STRIKEOUT_FLAG = 15;
	private static final int TESTSTEP_INDEX = 16;
	private static final int COLUMNS_COUNT = 17;
	

	private String testStepIndex;
	private String pageName;
	private String actionDescription;
	private String objectProperty;
	private String keyword;
	private String action;
	private String flow; 
	private String testdata; 
	private String apiUrl;
	private String apiWebService; 
	private String apiJson;
	private String apiJsonResponse;
	private String fileName;
	private String variableName; 
	private String outputVariables;
	private String strikeOutFlag;

	private boolean strikedOut;
	private String[] testStepArray;

	
	public TestStep(int index, Row row) throws Exception{
		prepareTestStep(index, row);
	}

	public TestStep(){

	}

	public String getApiUrl() {
		return apiUrl;
	}

	public String getApiWebService() {
		return apiWebService;
	}

	public String getApiJson() {
		return apiJson;
	}

	public String getApiJsonResponse() {
		return apiJsonResponse;
	}

	public String getVariableName() {
		return variableName;
	}


	public String getTestStepIndex() {
		return testStepIndex;
	}


	public String getPageName() {
		return pageName;
	}


	public String getActionDescription() {
		return actionDescription;
	}


	public String getObjectProperty() {
		return objectProperty;
	}


	public String getKeyword() {
		return keyword;
	}


	public String getAction() {
		return action;
	}


	public String getFlow() {
		return flow;
	}


	public String getTestdata() {
		return testdata;
	}


	public String getFileName() {
		return fileName;
	}


	public String getOutputVariables() {
		return outputVariables;
	}

	public String getSkipTestStepFlag() {
		return strikeOutFlag;
	}
	
	public void setObjectProperty(String objectProperty) {
		this.objectProperty = objectProperty;
		testStepArray[OBJECT_PROPERTY_INDEX] = objectProperty;
	}

	public void setFlow(String flow) {
		this.flow = flow;
		testStepArray[FLOW_INDEX] = flow;
	}

	public void setTestdata(String testdata) {
		this.testdata = testdata;
		testStepArray[TESTDATA_INDEX] = testdata;
	}

	public void setApiUrl(String apiUrl) {
		this.apiUrl = apiUrl;
		testStepArray[API_URL_INDEX] = apiUrl;
	}

	public void setApiWebService(String apiWebService) {
		this.apiWebService = apiWebService;
		testStepArray[API_WEBSERVICE_INDEX] = apiWebService;
	}

	public void setApiJson(String apiJson) {
		this.apiJson = apiJson;
		testStepArray[API_JSON_INDEX] = apiJson;
	}

	public void setApiJsonResponse(String apiJsonResponse) {
		this.apiJsonResponse = apiJsonResponse;
		testStepArray[API_JSON_RESPONSE_INDEX] = apiJsonResponse;
	}

	public void setVariableName(String variableName) {
		this.variableName = variableName;
		testStepArray[VARIABLE_NAME_INDEX]	= variableName;
	}


	private void prepareTestStep(int index, Row row) throws Exception{
		try {
			
				testStepArray 			= new String[COLUMNS_COUNT];

				strikedOut = false;
				testStepIndex  			= Integer.toString(index);
				pageName  				= getStringCellValue(row.getCell(PAGE_NAME_INDEX));
				actionDescription 		= getStringCellValue(row.getCell(ACTION_DESCIRPTION_INDEX));
				objectProperty  		= getStringCellValue(row.getCell(OBJECT_PROPERTY_INDEX));
				keyword   				= getStringCellValue(row.getCell(KEYWORD_INDEX));
				action  				= getStringCellValue(row.getCell(ACTION_INDEX));
				flow   					= getStringCellValue(row.getCell(FLOW_INDEX));
				testdata   				= getStringCellValue(row.getCell(TESTDATA_INDEX));
				apiUrl  				= getStringCellValue(row.getCell(API_URL_INDEX));
				apiWebService   		= getStringCellValue(row.getCell(API_WEBSERVICE_INDEX));
				apiJson   				= getStringCellValue(row.getCell(API_JSON_INDEX));
				apiJsonResponse  		= getStringCellValue(row.getCell(API_JSON_RESPONSE_INDEX));
				fileName    			= getStringCellValue(row.getCell(FILENAME_INDEX));
				variableName   			= getStringCellValue(row.getCell(VARIABLE_NAME_INDEX));
				outputVariables   		= getStringCellValue(row.getCell(OUTPUT_VARIABLES_INDEX));


				//Check if Action is null
				action	= (action != null ? action.toLowerCase().trim() : action);
				//Set flow index as 'positive' if it is empty
				flow	= (flow != null && !flow.isEmpty() ? flow.trim():"positive");
				
				
				
				strikedOut = (isStrikeOut(row.getCell(KEYWORD_INDEX)) || isStrikeOut(row.getCell(ACTION_INDEX)))? true : false; 
				strikeOutFlag  		= Boolean.toString(strikedOut);				
				addValuesArray();

		} catch (Exception e){
			e.printStackTrace();
			System.out.println("Exception occured while reading the test step row. Row Number : " + index + e.getMessage());
			logger.error("Exception occured while reading the test step row. Row Number : " + index + e.getMessage());
			throw new Exception (" Test step is not in expected format ");
		}

	}


	private void addValuesArray() throws Exception{

		testStepArray[TESTSTEP_INDEX]  			= testStepIndex;  		
		testStepArray[PAGE_NAME_INDEX]  		= pageName;  			
		testStepArray[ACTION_DESCIRPTION_INDEX] = actionDescription; 	
		testStepArray[OBJECT_PROPERTY_INDEX]  	= objectProperty;  	
		testStepArray[KEYWORD_INDEX]   			= keyword;   			
		testStepArray[ACTION_INDEX]  			= action;  			
		testStepArray[FLOW_INDEX]   			= flow;   				
		testStepArray[TESTDATA_INDEX]   		= testdata;   			
		testStepArray[API_URL_INDEX]  			= apiUrl;  			
		testStepArray[API_WEBSERVICE_INDEX]    	= apiWebService;  	
		testStepArray[API_JSON_INDEX]   		= apiJson;   			
		testStepArray[API_JSON_RESPONSE_INDEX]  = apiJsonResponse;  	
		testStepArray[FILENAME_INDEX]    		= fileName;    		
		testStepArray[VARIABLE_NAME_INDEX]   	= variableName;   		
		testStepArray[OUTPUT_VARIABLES_INDEX]   = outputVariables; 
		testStepArray[STRIKEOUT_FLAG]   		= strikeOutFlag; 



	}


	public String[] getTestStepArray(){

		return testStepArray;

	}

	public int getColsCount(){

		return testStepArray.length;

	}


	public TestStep getTestStep(){

		return this;
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


	private boolean isStrikeOut(Cell cell){
		boolean strikeoutFlag = false;
		if(cell !=  null){
			Font cellFont = cell.getSheet().getWorkbook().getFontAt(cell.getCellStyle().getFontIndex());
			strikeoutFlag = cellFont.getStrikeout();
		}

		return strikeoutFlag;
	}
}
