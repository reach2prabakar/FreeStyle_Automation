package com.client.automation.library;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;

import com.client.automation.common.Common;

public class Global {

	private static Logger logger = Logger.getLogger(Global.class.getName());
	public static String workingDir = System.getProperty("user.dir");
	public static String resourceFolder = null;
	public static String pathSeperator = "\\";
	public static String propFileName = "Project.properties";
	public static String propFile;
	public static String globalScript;
	public static String globalResultFolder;
	public static String logPath;
	public static String constantTimeStamp;
	public static String testExecutionTime;
	public static WebDriver driver=null; 
	public static String configFile;
	public static String configurationFileName;
	public static String projectFolder;
	public static String datatableFolder;
	public static String datatableFolderName;
	public static String logsFolder;
	public static String logFolderName;
	public static String screenShotsFolder;
	public static String screenShotFolderName;
	public static String projectName;
	public static String companyName;
	public static String templatesFolder;
	public static String templatesFolderName;
	public static String resultsFolder;
	public static String resultsFolderName;
	public static String copyRightYear;
	public static String emailRecipients;
	public static String smtpHost;
	public static String emailFromUser;
	public static String emailUserPassword;
	public static String emailSubject;
	public static String emailBodyText;
	public static String silentExecution;
	public static String dbUrl;
	public static String dbUsername;
	public static String dbPassword;
	public static String dbClass;
	public static String flowType;
	public static String keyword;
	public static String action;
	public static String objProperty;
	public static String testData;
	public static String waitTime;
	public static String apiFolderName;
	public static String apiFilePath;
	public static String fromAPI_NoScreenShot=null;
	public static String browser;
	public static String moduleFolder;
	public static String failureOfTestStep; //	failureOfTestStep = Returns null if test is Passed, otherwise error message defined in Function Library will be returned
	public static String errMain = "No";
	public static String overalltestScriptStatus=null;
	public static String screenShotPath;
	public static String sendEmail;
	public static String triggerjob;
	public static String driverQuit;
	public static String clientName;
	public static String environment;
	public static String clientFolder;
	public static String objectRepoFile;
	public static String objectRepositoryFileName;
	public static String contentRepoFile;
	public static String contentRepositoryFileName;
	public static long 	 suiteStart = new Date().getTime();
	public static String varibleDifferentiator;
	public static String uploadDocumentsFolderName;
	public static String uploadDocumentsFolder;
	public static String driversFolderName;
	public static String driversFolder;
	public static String toolsFolderName;
	public static String toolsFolder;
	public static String logPath2;
	public static String browserAndVersion;
	public static String osName;
	public static String failedTestSciptsSuite;
	public static String failedTestSciptsSuiteName;
	public static String url;
	public static String onFailCloseApplication;
	public static String testResultFileAbsolutePath;
	public static String testResultTemplateAbsolutePath;
	public static String excelExtension;
	public static String downloadsFolderName;
	public static String downloadsFolder;
	public static String downloadsPath;
	private static HashMap<String, String> globalmap ; 
 	

	//public static String logPath2 = System.getProperty("user.dir")+"\\src\\main\\resources\\Results";
	public static String resultlogpath;
	//public static String resultlogpath = logPath2 + "\\" + "Modria" + "_" + "Dailyrun" + ".html";
	public static String filenameHTML = "Modria_Dailyrun.html";
	public static Map<String, String> variableNamesmap;
	
	public static void readProperties() throws Exception{
		Common common 		= new Common();
		setWorkingDir(workingDir);
		setResourceFolder(resourceFolder);
		setPropFile(getWorkingDir() + getResourceFolder() + propFileName);
		setBrowser(common.getPropValues("browser"));
		setClientName(common.getPropValues("clientName"));
		setProjectName(common.getPropValues("projectName"));
		setEnvironment(common.getPropValues("environment"));
		
		companyName 		= common.getPropValues("companyName");
		clientName			= common.getPropValues("clientName");
		copyRightYear 		= common.getPropValues("copyRightYear");
		silentExecution 	= common.getPropValues("silentExecution");
		emailRecipients	 	= common.getPropValues("emailRecipients");
		smtpHost 			= common.getPropValues("smtpHost");
		emailFromUser 		= common.getPropValues("emailFromUser");
		emailUserPassword 	= common.getPropValues("emailUserPassword");
		emailSubject 		= common.getPropValues("emailSubject");
		emailBodyText 		= common.getPropValues("emailBodyText");
		sendEmail 			= common.getPropValues("sendEmail");
		triggerjob 			= common.getPropValues("triggeremailjob");
		dbUrl 				= common.getPropValues("dbUrl");
		dbUsername 			= common.getPropValues("dbUserName");
		dbPassword 			= common.getPropValues("dbPassword");
		dbClass 			= common.getPropValues("dbClass");
		driverQuit 			= common.getPropValues("driverQuit");
		apiFolderName 		= common.getPropValues("apiFolderName");
		uploadDocumentsFolderName = common.getPropValues("uploadDocumentsFolder");
		datatableFolderName = common.getPropValues("datatableFolder");
		logFolderName       = common.getPropValues("logsFolder");
		driversFolderName	= common.getPropValues("driversFolder");
		toolsFolderName  	= common.getPropValues("toolsFolder");
		screenShotFolderName= common.getPropValues("screenShotsFolder");
		templatesFolderName = common.getPropValues("templatesFolder");
		resultsFolderName   = common.getPropValues("resultsFolder");
		configurationFileName = common.getPropValues("configurationFile");
		objectRepositoryFileName = common.getPropValues("objectRepositoryFileName");
		contentRepositoryFileName = common.getPropValues("contentRepositoryFileName");
		failedTestSciptsSuiteName = common.getPropValues("failedTestSciptsSuite");
		onFailCloseApplication = common.getPropValues("onFailCloseApplication");
		excelExtension   	= common.getPropValues("excelExtension");
		downloadsFolderName = common.getPropValues("downloadsFolderName");
		projectFolder 		= getWorkingDir();
		datatableFolder 	= getWorkingDir()+ getResourceFolder() + datatableFolderName;
		logsFolder 			= getWorkingDir()+ getResourceFolder() + logFolderName;
		screenShotsFolder 	= getWorkingDir()+ getResourceFolder() + screenShotFolderName;
		templatesFolder 	= getWorkingDir()+ getResourceFolder() + templatesFolderName;
		resultsFolder 		= getWorkingDir()+ getResourceFolder() + resultsFolderName;
		driversFolder		= getWorkingDir()+ getResourceFolder() + driversFolderName;
		toolsFolder			= getWorkingDir()+ getResourceFolder() + toolsFolderName;
		uploadDocumentsFolder = getWorkingDir()+ getResourceFolder() + uploadDocumentsFolderName;
		downloadsFolder		= getWorkingDir()+ getResourceFolder() + downloadsFolderName;
		clientFolder    	= getDatatableFolder()  + pathSeperator + getClientName();
		configFile 			= getClientFolder() + pathSeperator + configurationFileName;
		objectRepoFile		= getClientFolder() + pathSeperator + objectRepositoryFileName;
		contentRepoFile		= getClientFolder() + pathSeperator + contentRepositoryFileName;
		failedTestSciptsSuite = getClientFolder() + pathSeperator + failedTestSciptsSuiteName;
		constantTimeStamp	= common.getTimeStamp();
		globalResultFolder 	= getResultsFolder() + pathSeperator + "Result_" + constantTimeStamp;
		logPath				= Global.logsFolder+ pathSeperator + "Log_"+ Global.constantTimeStamp;
		screenShotPath		= Global.screenShotsFolder+ pathSeperator + "ScreenShot_" + Global.constantTimeStamp;
		downloadsPath		= Global.downloadsFolder + pathSeperator + "Downloads_" + Global.constantTimeStamp;
		testResultFileAbsolutePath = globalResultFolder + pathSeperator + "TestResult" + excelExtension;
		testResultTemplateAbsolutePath = templatesFolder + pathSeperator + "Result_Template" + excelExtension;
		logPath2			= getResultsFolder();
		resultlogpath		= logPath2 + pathSeperator + "Modria" + "_" + "Dailyrun" + ".html";
		varibleDifferentiator = "#(.*)#";
		variableNamesmap 	= new HashMap<String, String>();
		globalmap = new HashMap<>();
		common.createLogFolder(logPath);
		common.createScreenShotFolder(screenShotPath);
		common.createDownloadsFolder(downloadsPath);
		common.createResultFolder(globalResultFolder);

		
	}
	
	public static String getGlobalmapvalue(String key){
		return globalmap.get(key);
	}
	
	public static Map<String, String> getGlobalmap(){
		return globalmap;
	} 
	
	public static String getWorkingDir() {
		return workingDir;
	}

	public static String getResourceFolder() {
		return resourceFolder;
	}

	public static String getPropFile() {
		return propFile;
	}

	public static String getGlobalResultFolder() {
		return getResultsFolder() + "/Result_" + constantTimeStamp;
	}

	public static String getConfigFile() {
		return getClientFolder() + pathSeperator + configurationFileName;
	}


	public static String getClientName() {
		return clientName;
	}

	public static String getProjectName() {
		return projectName;
	}

	public static String getProjectFolder() {
		return getWorkingDir();
	}

	public static String getDatatableFolder() {
		return getWorkingDir()+ getResourceFolder() + datatableFolderName;
	}
	
	public static String getJavaTestclassFolder() {
		return getWorkingDir()+ "/src/main/test/com/clientName/automation/";
	}

	public static String getLogsFolder() {
		return getWorkingDir()+ getResourceFolder() + logFolderName;
	}

	public static String getScreenShotsFolder() {
		return getWorkingDir()+ getResourceFolder() + screenShotFolderName;
	}

	public static String getTemplatesFolder() {
		return getWorkingDir()+ getResourceFolder() + templatesFolderName;
	}

	public static String getCompanyName() {
		return companyName;
	}

	public static String getResultsFolder() {
		return getWorkingDir()+ getResourceFolder() + resultsFolderName;
	}

	public static String getApiFolderName() {
		return apiFolderName;
	}

	public static String getApiFilePath() {
		return apiFilePath;
	}

	public static String getBrowser() {
		return browser;
	}

	public static String getModuleFolder() {
		return moduleFolder;
	}

	public static String getEnvironment() {
		return environment;
	}

	public static String getClientFolder() {
		return getDatatableFolder()  + pathSeperator + getClientName();
	}

	public static String getObjectRepoFile() {
		return getClientFolder() + pathSeperator + objectRepositoryFileName;
	}

	public static String getContentRepoFile() {
		return getClientFolder() + pathSeperator + contentRepositoryFileName;
	}

	public static String getDriversFolder() {
		return driversFolder;
	}

	public static String getToolsFolder() {
		return toolsFolder;
	}

	public static String getUploadDocumentsFolder() {
		return uploadDocumentsFolder;
	}

	public static String getBrowserAndVersion() {
		
		return (browserAndVersion != null ? browserAndVersion : "");
	}

	public static String getOsName() {
		return osName;
	}

	public static String getFailedTestSciptsSuite() {
		return getClientFolder() + pathSeperator + failedTestSciptsSuiteName;
	}

	public static String getConfigurationFileName() {
		return configurationFileName;
	}

	public static String getEmailRecipients() {
		return emailRecipients;
	}

	public static String getUrl() {
		return url;
	}

	public static String getDbUrl() {
		return dbUrl;
	}

	public static String getDbUsername() {
		return dbUsername;
	}

	public static String getDbPassword() {
		return dbPassword;
	}

	public static String getDbClass() {
		return dbClass;
	}

	public static String getOnFailCloseApplication() {
		return onFailCloseApplication;
	}

	public static String getTestResultFileAbsolutePath() {
		return testResultFileAbsolutePath;
	}

	public static String getTestResultTemplateAbsolutePath() {
		return testResultTemplateAbsolutePath;
	}

	public static String getExcelExtn() {
		return excelExtension;
	}

	public static String getDownloadsFolderName() {
		return downloadsFolderName;
	}


	public static String getDownloadsFolder() {
		return downloadsFolder;
	}

	public static String getLogPath() {
		return logPath;
	}

	public static String getScreenShotPath() {
		return screenShotPath;
	}

	public static String getDownloadsPath() {
		return downloadsPath;
	}

	public static void setDownloadsPath(String downloadsPath) {
		Global.downloadsPath = downloadsPath;
	}

	public static void setLogPath(String logPath) {
		Global.logPath = logPath;
	}

	public static void setScreenShotPath(String screenShotPath) {
		Global.screenShotPath = screenShotPath;
	}

	public static void setDownloadsFolder(String downloadsFolder) {
		Global.downloadsFolder = downloadsFolder;
	}

	public static void setDownloadsFolderName(String downloadsFolderName) {
		Global.downloadsFolderName = downloadsFolderName;
	}

	public static void setExcelExtn(String excelExtension) {
		Global.excelExtension = excelExtension;
	}

	public static void setTestResultTemplateAbsolutePath(
			String testResultTemplateAbsolutePath) {
		Global.testResultTemplateAbsolutePath = testResultTemplateAbsolutePath;
	}

	public static void setTestResultFileAbsolutePath(
			String testResultFileAbsolutePath) {
		Global.testResultFileAbsolutePath = testResultFileAbsolutePath;
	}

	public static void setOnFailCloseApplication(String onFailCloseApplication) {
		Global.onFailCloseApplication = onFailCloseApplication;
	}
	public static void setDbUrl(String dbUrl) {
		Global.dbUrl = dbUrl;
	}

	public static void setDbUsername(String dbUsername) {
		Global.dbUsername = dbUsername;
	}

	public static void setDbPassword(String dbPassword) {
		Global.dbPassword = dbPassword;
	}

	public static void setDbClass(String dbClass) {
		Global.dbClass = dbClass;
	}

	public static void setUrl(String url) {
		Global.url = url;
	}

	public static void setEmailRecipients(String emailRecipients) {
		Global.emailRecipients = emailRecipients;
	}


	public static void setConfigurationFileName(String configurationFileName) {
		Global.configurationFileName = configurationFileName;
	}

	public static void setFailedTestSciptsSuite(String failedTestSciptsSuite) {
		Global.failedTestSciptsSuite = failedTestSciptsSuite;
	}

	public static void setBrowserAndVersion(String browserAndVersion) {
		Global.browserAndVersion = browserAndVersion;
	}

	public static void setOsName(String osName) {
		Global.osName = osName;
	}
	public static void setUploadDocumentsFolder(String uploadDocumentsFolder) {
		Global.uploadDocumentsFolder = uploadDocumentsFolder;
	}

	public static void setToolsFolder(String toolsFolder) {
		Global.toolsFolder = toolsFolder;
	}

	public static void setDriversFolder(String driversFolder) {
		Global.driversFolder = driversFolder;
	}



	public static void setWorkingDir(String workingDir) {
		Global.workingDir = workingDir;
	}

	public static void setResourceFolder(String resourceFolder) {
		if(resourceFolder != null){
			Global.resourceFolder = resourceFolder;
		}else {
			Global.resourceFolder = "\\src\\main\\resources\\";
		}
	}

	public static void setPropFile(String propFile) {
		Global.propFile = propFile;
	}
	
	public static void setGlobalResultFolder(String globalResultFolder) {
		Global.globalResultFolder = globalResultFolder;
	}

	public static void setScreenShotsFolder(String screenShotsFolder) {
		Global.screenShotsFolder = screenShotsFolder;
	}
	
	public static void setTemplatesFolder(String templatesFolder) {
		Global.templatesFolder = templatesFolder;
	}

	public static void setConfigFile(String configFile) {
		Global.configFile = configFile;
	}

	public static void setClientName(String clientName) {
		Global.clientName = clientName;
	}

	public static void setProjectName(String projectName) {
		Global.projectName = projectName;
	}

	public static void setProjectFolder(String projectFolder) {
		Global.projectFolder = projectFolder;
	}

	public static void setDatatableFolder(String datatableFolder) {
		Global.datatableFolder = datatableFolder;
	}

	public static void setLogsFolder(String logsFolder) {
		Global.logsFolder = logsFolder;
	}

	public static void setCompanyName(String companyName) {
		Global.companyName = companyName;
	}

	public static void setResultsFolder(String resultsFolder) {
		Global.resultsFolder = resultsFolder;
	}

	public static void setApiFolderName(String apiFolderName) {
		Global.apiFolderName = apiFolderName;
	}

	public static void setApiFilePath(String apiFilePath) {
		Global.apiFilePath = apiFilePath;
	}

	public static void setBrowser(String browser) {
		Global.browser = browser;
	}

	public static void setModuleFolder(String moduleFolder) {
		Global.moduleFolder = moduleFolder;
	}

	public static void setEnvironment(String environment) {
		Global.environment = environment;
	}

	public static void setClientFolder(String clientFolder) {
		Global.clientFolder = clientFolder;
	}

	public static void setObjectRepoFile(String objectRepoFile) {
		Global.objectRepoFile = objectRepoFile;
	}

	public static void setContentRepoFile(String contentRepoFile) {
		Global.contentRepoFile = contentRepoFile;
	}

	public static String getScriptLogFile(String scriptName){
		
		return getLogsFolder() + "/Log_"+ constantTimeStamp+"/"+scriptName+ getExcelExtn();
		
	}
	
	public static void setGlobalmapvalue(String key,String value){
		 globalmap.put(key, value);
	}
	
	
}
