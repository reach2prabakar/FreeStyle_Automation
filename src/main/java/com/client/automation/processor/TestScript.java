package com.client.automation.processor;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FilenameUtils;
import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.testng.ITestNGMethod;
import org.testng.ITestResult;
import org.testng.TestNG;
import org.testng.xml.XmlClass;
import org.testng.xml.XmlMethodSelector;
import org.testng.xml.XmlSuite;
import org.testng.xml.XmlTest;

import com.client.automation.library.ContentRepository;
import com.client.automation.library.Global;
import com.client.automation.library.ObjectRepository;
import com.gargoylesoftware.htmlunit.javascript.host.fetch.Request;

public class TestScript {

	/** Primitive type name -> class map. */
	private static final Map PRIMITIVE_NAME_TYPE_MAP = new HashMap();

	
	private static Logger logger = Logger.getLogger(TestScript.class.getName());
	private Sheet obWsheet;
	private Workbook obWbook;
	private TestStep teststep;
	public TestStep[] testSteps;
	private int rowsCount;
	private int colsCount;
	private int testStepIndex;
	private String[] testScriptProperties;
	private String projectFolder;
	private String moduleFolder;
	private String testScriptName;
	private String runFlag;
	private String defectId;
	private String entireScriptPath;
	private String apiLogFile;
	private String testScriptLogFile;

	public TestScript(String filePath, ContentRepository contentRepo) throws Exception {
		this.entireScriptPath = filePath;
		File file = new File(filePath);
		this.testScriptName = FilenameUtils.removeExtension(file.getName());
		this.testScriptLogFile = Global.getScriptLogFile(testScriptName);
		testScriptProperties = new String[TestSuite.TESTSUITE_COLUMNS_COUNT];
		testScriptProperties[TestSuite.ENTIRE_SCRIPT_PATH] = filePath;
		testScriptProperties[TestSuite.TESTSCRIPT_NAME_INDEX] = testScriptName;

	}
    
	public TestScript(String[] testScriptProperties, ContentRepository contentRepo, ObjectRepository objectRepo)
			throws Exception {
		this.testScriptProperties = testScriptProperties;
		this.projectFolder = testScriptProperties[TestSuite.PROJECT_FOLDER_INDEX];
		this.moduleFolder = testScriptProperties[TestSuite.MODULE_FOLDER_INDEX];
		this.testScriptName = testScriptProperties[TestSuite.TESTSCRIPT_NAME_INDEX];
		this.runFlag = testScriptProperties[TestSuite.RUN_INDEX];
		this.defectId = testScriptProperties[TestSuite.DEFECT_ID_INDEX];
		this.entireScriptPath = testScriptProperties[TestSuite.ENTIRE_SCRIPT_PATH];
		this.apiLogFile = testScriptProperties[TestSuite.API_LOGFILE];
		this.testScriptLogFile = Global.getScriptLogFile(testScriptName);
		
		ReadTestSteps(entireScriptPath, contentRepo, objectRepo);
	}

	public int getTestStepIndex() {
		return testStepIndex;
	}

	public TestStep getTeststep(int index) {
		return testSteps[index];
	}

	public void setTeststep(int index, TestStep teststep) {
		testSteps[index] = teststep;
	}

	public void setTestStepIndex(int testStepIndex) {
		this.testStepIndex = testStepIndex;
	}

	
	public void onTestStart(ITestResult iTestResult){
    	String sTestMethodName = iTestResult.getMethod().getMethodName();
    	
        Object[] ob = iTestResult.getParameters();
        String sTestSuiteName = iTestResult.getTestClass().getRealClass().getSimpleName();
        //deleteFile();
        System.out.println(("<<<*** START: " + sTestSuiteName + "." + sTestMethodName + " ***>>> "));
       /*WebDriver driver =  base.testSetup(null);
        System.out.println(sTestMethodName +" :"+driver.getWindowHandle());
        new Global().setdriver(driver);*/
        
    }
	
	
	public void invokemethod(ITestNGMethod method) {
		
		String methodname = method.getMethodName();
	}
	
	
	private void ReadTestSteps(String filePath, ContentRepository contentRepo, ObjectRepository objectRepo)
			throws Exception {
		try {
			if (new File(filePath).exists()) {

				String extension = getFileExtension(filePath);
				if (extension.equalsIgnoreCase("java")) {
					
					XmlSuite xmlsuite = new XmlSuite();
					xmlsuite.setName("TmpSuite");
					 
					XmlTest xmltest = new XmlTest(xmlsuite);
					xmltest.setName("TmpTest");
					
					
					
					List<XmlClass> xmlclass = new ArrayList<XmlClass>();
					xmlclass.add(new XmlClass("com.clientName.automation.project.Login"));
					xmltest.setXmlClasses(xmlclass) ;
					
					XmlMethodSelector methodSelector = new XmlMethodSelector();
					
					
					//methodSelector.setName("com.clientName.automation.project.Login");
					methodSelector.setPriority(1);
					
					List<XmlSuite> suites = new ArrayList<XmlSuite>();
					suites.add(xmlsuite);
					TestNG tng = new TestNG();
					tng.setXmlSuites(suites);
					tng.run();

					
					/*
					 * Class<?> classtoload = Class.forName("com." + Global.getClientName() +
					 * ".automation." + Global.getProjectName() + "." + moduleFolder); TestNG
					 * testSuite = new TestNG(); testSuite.setTestClasses(new Class[]
					 * {classtoload});
					 * 
					 * testSuite.setDefaultSuiteName("My Test Suite");
					 * testSuite.setDefaultTestName("logidswdwn"); List<String> testname = new
					 * ArrayList<String>(); testname.add("loginmethod1");
					 * 
					 * testSuite.setTestNames(testname);
					 * testSuite.setOutputDirectory(Global.getLogsFolder() + "/testng-output");
					 * testSuite.run();
					 */
				}else{
					FileInputStream fileInputStream = new FileInputStream(filePath);
					obWbook = WorkbookFactory.create(fileInputStream);
					obWsheet = obWbook.getSheetAt(0);
					Iterator<Row> rowIterator = obWsheet.iterator();
					rowsCount = obWsheet.getLastRowNum();
					colsCount = obWsheet.getRow(1).getPhysicalNumberOfCells();
					// get the total test step count from xl and store in teststep array
					testSteps = new TestStep[rowsCount + 1];
					int rowIndex = 0;

					while (rowIterator.hasNext()) {
						Row row = rowIterator.next();
						teststep = new TestStep(rowIndex, row);
						// Resolving content and object repo values
						resolveObjectIdentifiers(teststep, objectRepo);
						resolveContentIdentifiers(teststep, contentRepo);

						testSteps[rowIndex] = teststep.getTestStep();
						rowIndex++;
					}

					obWbook.close();
				}

			} else {
				System.out.println("ERROR : Test Script file not found " + filePath);
				logger.error("ERROR : Test Script file not found " + filePath);
				throw new Exception("ERROR : Test Script file not found " + filePath);
			}
		} catch (Exception e) {
			System.out.println("Exception occured while opening the TestScript file. " + e.getMessage());
			logger.error("Exception occured while opening the TestScript file. " + e.getMessage());
			throw new Exception("Exception occured while opening the TestScript file. " + e.getMessage());
		}

	}

	private String getFileExtension(String filepath) {

		String extension = FilenameUtils.getExtension(filepath);
		return extension;
	}

	private void resolveContentIdentifiers(TestStep teststep, ContentRepository contentRepo) {
		VariableResolver variableResolver = new VariableResolver(contentRepo.getcontentRepoSubtitutor());
		teststep.setTestdata(variableResolver.resolveContentIndentifiers(teststep.getTestdata()));
		teststep.setApiJson(variableResolver.resolveContentIndentifiers(teststep.getApiJson()));
		teststep.setApiUrl(variableResolver.resolveContentIndentifiers(teststep.getApiUrl()));
		teststep.setApiWebService(variableResolver.resolveContentIndentifiers(teststep.getApiWebService()));
		teststep.setApiJsonResponse(variableResolver.resolveContentIndentifiers(teststep.getApiJsonResponse()));
	}

	private void resolveObjectIdentifiers(TestStep teststep, ObjectRepository objectRepo) {
		VariableResolver variableResolver = new VariableResolver(objectRepo.getobRepoSubtitutor());
		teststep.setObjectProperty(variableResolver.resolveObjectIndentifiers(teststep.getObjectProperty()));
	}

	public TestStep[] getTestSteps() {
		return testSteps;
	}

	public void setTestSteps(TestStep[] testSteps) {
		this.testSteps = testSteps;
	}

	public int getRowsCount() {

		return rowsCount;

	}

	public int getColsCount() {

		return colsCount;

	}

	public void execute() {

	}

	public String[] getTestScriptProperties() {
		return testScriptProperties;
	}

	public String getProjectFolder() {
		return projectFolder;
	}

	public String getModuleFolder() {
		return moduleFolder;
	}

	public String getTestScriptName() {
		return testScriptName;
	}

	public String getRunFlag() {
		return runFlag;
	}

	public String getEntireScriptPath() {
		return entireScriptPath;
	}

	public String getApiLogFile() {
		return apiLogFile;
	}

	public String getTestScriptLogFile() {
		return testScriptLogFile;
	}

	public void setTestScriptProperties(String[] testScriptProperties) {
		this.testScriptProperties = testScriptProperties;
	}

	public void setProjectFolder(String projectFolder) {
		this.projectFolder = projectFolder;
	}

	public void setModuleFolder(String moduleFolder) {
		this.moduleFolder = moduleFolder;
	}

	public void setTestScriptName(String testScriptName) {
		this.testScriptName = testScriptName;
	}

	public void setRunFlag(String runFlag) {
		this.runFlag = runFlag;
	}

	public void setEntireScriptPath(String entireScriptPath) {
		this.entireScriptPath = entireScriptPath;
	}

	public void setApiLogFile(String apiLogFile) {
		this.apiLogFile = apiLogFile;
	}

	public void setTestScriptLogFile(String testScriptLogFile) {
		this.testScriptLogFile = testScriptLogFile;
	}

	public String getDefectId() {
		return defectId;
	}

	public void setDefectId(String defectId) {
		this.defectId = defectId;
	}

}
