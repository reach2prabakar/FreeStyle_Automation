package com.planit.automation.processor;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.text.StrSubstitutor;
import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.reflections.Reflections;

import com.planit.automation.library.ContentRepository;
import com.planit.automation.library.Global;
import com.planit.automation.library.ObjectRepository;

import io.github.classgraph.ClassGraph;
import io.github.classgraph.ScanResult;

public class TestScript {


	private static Logger logger = Logger.getLogger(TestScript.class.getName());
	private Sheet obWsheet;
	private Workbook obWbook;
	private TestStep teststep;
	public TestStep[] testSteps;
	private int rowsCount;
	private int colsCount;
	private int testStepIndex;
	private String[] testScriptProperties;/*
											 * private StrSubstitutor obRepoSubstitutor; private StrSubstitutor
											 * contentRepoSubstitutor;
											 */
	private String projectFolder;
	private String moduleFolder;
	private String testScriptName;
	private String runFlag;
	private String defectId;
	private String entireScriptPath;
	private String apiLogFile;
	private String testScriptLogFile;

		
	
	public TestScript(String filePath,ContentRepository contentRepo) throws Exception{
		this.entireScriptPath = filePath;
		File file = new File(filePath);
		this.testScriptName = FilenameUtils.removeExtension(file.getName());
		this.testScriptLogFile = Global.getScriptLogFile(testScriptName);
		testScriptProperties = new String[TestSuite.TESTSUITE_COLUMNS_COUNT];
		testScriptProperties[TestSuite.ENTIRE_SCRIPT_PATH] = filePath;
		testScriptProperties[TestSuite.TESTSCRIPT_NAME_INDEX] = testScriptName;
		
		/*
		 * initializeObjectRepository(); initializeContentRepository(contentRepo);
		 */
		
		//ReadTestSteps(filePath);
	}

	public TestScript(String filePath) throws Exception{
		this.entireScriptPath = filePath;
		File file = new File(filePath);
		this.testScriptName = FilenameUtils.removeExtension(file.getName());
		this.testScriptLogFile = Global.getScriptLogFile(testScriptName);
		testScriptProperties = new String[TestSuite.TESTSUITE_COLUMNS_COUNT];
		testScriptProperties[TestSuite.ENTIRE_SCRIPT_PATH] = filePath;
		testScriptProperties[TestSuite.TESTSCRIPT_NAME_INDEX] = testScriptName;
		
		//initializeObjectRepository();
		
		//ReadTestSteps(filePath);
	}
	public TestScript(String[] testScriptProperties, ContentRepository contentRepo,ObjectRepository objectRepo) throws Exception{
		this.testScriptProperties = testScriptProperties;
		this.projectFolder = testScriptProperties[TestSuite.PROJECT_FOLDER_INDEX];
		this.moduleFolder = testScriptProperties[TestSuite.MODULE_FOLDER_INDEX];
		this.testScriptName = testScriptProperties[TestSuite.TESTSCRIPT_NAME_INDEX];
		this.runFlag = testScriptProperties[TestSuite.RUN_INDEX];
		this.defectId = testScriptProperties[TestSuite.DEFECT_ID_INDEX];
		this.entireScriptPath = testScriptProperties[TestSuite.ENTIRE_SCRIPT_PATH];
		this.apiLogFile = testScriptProperties[TestSuite.API_LOGFILE];
		this.testScriptLogFile = Global.getScriptLogFile(testScriptName);
		
		 
		/*
		 * initializeObjectRepository(); initializeContentRepository(contentRepo);
		 */
		ReadTestSteps(entireScriptPath,contentRepo,objectRepo);
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


	
	
	
	private void ReadTestSteps(String filePath,ContentRepository contentRepo,ObjectRepository objectRepo) throws Exception{
		try {
			if(new File(filePath).exists()){
				String extension = getFileExtension(filePath);
				if(extension.equalsIgnoreCase("java")) {
					List<String> classNames = new ArrayList<>();
					try (ScanResult scanResult = new ClassGraph().whitelistPackages("com.planit.automation.library")
					        .enableClassInfo().scan()) {
					    classNames.addAll(scanResult.getAllClasses().getNames());
					}
				}else {
					FileInputStream fileInputStream = new FileInputStream(filePath);
					obWbook 					= WorkbookFactory.create(fileInputStream);
					obWsheet					= obWbook.getSheetAt(0);
					Iterator<Row> rowIterator 	= obWsheet.iterator();
					rowsCount 					= obWsheet.getLastRowNum();
					colsCount 					= obWsheet.getRow(1).getPhysicalNumberOfCells();
					testSteps 					= new TestStep[rowsCount+1];
					int rowIndex = 0;
					
					while (rowIterator.hasNext())
					{
						Row row = rowIterator.next();
						teststep = new TestStep(rowIndex,row);
					 // Resolving content and object repo values
						resolveObjectIdentifiers(teststep,objectRepo);
						resolveContentIdentifiers(teststep,contentRepo);

						testSteps[rowIndex] = teststep.getTestStep();
						rowIndex++;
					}

					obWbook.close();
				}
			
			} else {
				System.out.println("ERROR : Test Script file not found " + filePath);
				logger.error("ERROR : Test Script file not found " + filePath);
				throw new Exception ("ERROR : Test Script file not found " + filePath);
			}
		} catch (Exception e){
			System.out.println("Exception occured while opening the TestScript file. " + e.getMessage());
			logger.error("Exception occured while opening the TestScript file. " + e.getMessage());
			throw new Exception ("Exception occured while opening the TestScript file. " + e.getMessage());
		}

	}

	private String getFileExtension(String filepath) {
		
		String extension = FilenameUtils.getExtension(filepath);
		return extension;
	}

	private void resolveContentIdentifiers(TestStep teststep,ContentRepository contentRepo) {
		
		VariableResolver variableResolver = new VariableResolver(contentRepo.getcontentRepoSubtitutor());
		
		teststep.setTestdata(variableResolver.resolveContentIndentifiers(teststep.getTestdata()));
		teststep.setApiJson(variableResolver.resolveContentIndentifiers(teststep.getApiJson()));
		teststep.setApiUrl(variableResolver.resolveContentIndentifiers(teststep.getApiUrl()));
		teststep.setApiWebService(variableResolver.resolveContentIndentifiers(teststep.getApiWebService()));
		teststep.setApiJsonResponse(variableResolver.resolveContentIndentifiers(teststep.getApiJsonResponse()));
		
	}

	private void resolveObjectIdentifiers(TestStep teststep,ObjectRepository objectRepo) {

		VariableResolver variableResolver = new VariableResolver(objectRepo.getobRepoSubtitutor());
		teststep.setObjectProperty(variableResolver.resolveObjectIndentifiers(teststep.getObjectProperty()));
		
	}

	/*
	 * public void initializeContentRepository(ContentRepository contentRepo) throws
	 * IOException, Exception {
	 * 
	 * contentRepoSubstitutor = new StrSubstitutor(contentRepo.getContentMap());
	 * contentRepoSubstitutor.setEnableSubstitutionInVariables(true);
	 * 
	 * }
	 * 
	 * public void initializeObjectRepository(ObjectRepository objectrepo) throws
	 * Exception {
	 * 
	 * ObjectRepository obRepo = new ObjectRepository(Global.getObjectRepoFile());
	 * obRepoSubstitutor = new StrSubstitutor(obRepo.getObjectRepoMap());
	 * obRepoSubstitutor.setEnableSubstitutionInVariables(true);
	 * 
	 * 
	 * }
	 */

	public TestStep[] getTestSteps() {
		return testSteps;
	}


	public void setTestSteps(TestStep[] testSteps) {
		this.testSteps = testSteps;
	}

	public int getRowsCount(){

		return rowsCount;

	}

	public int getColsCount(){

		return colsCount;

	}
	
	
	public void execute(){
		
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
