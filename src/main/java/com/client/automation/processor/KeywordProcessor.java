package com.client.automation.processor;


import java.util.List;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.client.automation.keywords.Button;
import com.client.automation.keywords.CheckBox;
import com.client.automation.keywords.Dropdown;
import com.client.automation.keywords.EditBox;
import com.client.automation.keywords.Element;
import com.client.automation.keywords.Email;
import com.client.automation.keywords.Globalvariable;
import com.client.automation.keywords.Link;
import com.client.automation.keywords.Lists;
import com.client.automation.keywords.RadioButton;
import com.client.automation.keywords.Script;
import com.client.automation.keywords.URL;
import com.client.automation.keywords.Validation;
import com.client.automation.keywords.Wait;
import com.client.automation.keywords.Window;
import com.client.automation.library.ContentRepository;
import com.client.automation.library.DynamicVarRepository;
import com.client.automation.library.Global;

public class KeywordProcessor {

	private static Logger logger = Logger.getLogger(KeywordProcessor.class.getName());
	private String keyword;
	private String action;
	private String objProperty;
	private String testData;
	private String flowType;
	private int testScriptRowIndex;
	private String variableNameFromTestStep;
	private String failureOfTestStep;
	private DynamicVarRepository dynamicVariables;
	private WebDriver driver;
	private String currentTab = null;
	private By objectLocator = null;
	private String statusTestStep = null;// Update the Status in Result file and
	// print on console
	// Preparing default Error message for the entire execution of Test Cases
	private String errMain = keyword
			+ " not present please check..... For Keyword " + keyword
			+ " - Action - " + action + ".. Locator : " + objProperty;

	public KeywordProcessor(WebDriver driver) {

		setDriver(driver);
		//setWaitTime(waitTime);
		//setScriptLogFile(scriptLogFile);
		//setDynamicVariables(dynamicVariables);

	}
	
	public void setTeststepProperties(TestStep teststep){
		
		setKeyword(teststep.getKeyword());
		setAction(teststep.getAction());
		setTestData(teststep.getTestdata());
		setObjProperty(teststep.getObjectProperty());
		setFlowType(teststep.getFlow());
		setTestScriptRowIndex(Integer.parseInt(teststep.getTestStepIndex()));
		//setResultCellIndex(TestStep.RESULT_CELL_INDEX);
		//setScriptName(scriptName);
		setVariableNameFromTestStep(teststep.getVariableName());
		
	}

	public String execute(TestStep teststep, ContentRepository contentRepo) throws Exception{
			//int testScriptRowIndex, Integer resultCellIndex, String scriptName, String variableNameFromTestStep) throws Exception{
		//All Keyword Control are handled vis Switch cases and respective function library will be called.

		String statusTestStep = null;
		setTeststepProperties(teststep);
		//setTestScriptRowIndex(testScriptRowIndex);
		//setResultCellIndex(resultCellIndex);
		//setScriptName(scriptName);
		//setVariableNameFromTestStep(variableNameFromTestStep);
		prepareByObject(); // check for Object Locator and assign it to object locator variable
		testData 		= (testData.isEmpty() && !variableNameFromTestStep.isEmpty()) ? dynamicVariables.getObjectProperty(variableNameFromTestStep) : testData;
		testData 		= new VariableResolver(dynamicVariables.getDynamicVariablesMap()).resolveDynamicVariables(testData);
		new Wait(driver).waitForPageLoad(); // to ensure page loads before starting step
		if (keyword.equalsIgnoreCase("URL")){
			//logging
			logger.info("Thread url: " + Thread.currentThread().getId());

			switch (action) {

			case "get":
				
				failureOfTestStep= new URL(driver).get(testData); //	failureOfTestStep = Returns null if test is Passed, otherwise error message defined in Function Library will be returned								

				break;	

			case "refresh":

				failureOfTestStep=new URL(driver).refresh();								

				break;

			case "navigateback":

				failureOfTestStep= new URL(driver).navigateback(); //	failureOfTestStep = Returns null if test is Passed, otherwise error message defined in Function Library will be returned								

				break;	

			case "close":

				failureOfTestStep=new URL(driver).close();								

				break;

			case "validate":

				failureOfTestStep=new URL(driver).validate(testData, "");								

				break;			

			case "hastext":

				failureOfTestStep=new URL(driver).hasText(testData, "");								

				break;			
			}

			//statusTestStep = getTestStepResult(); // Update the Status in Result file and print on console

		}

		else if (keyword.equalsIgnoreCase("EditBox")){

			switch(action){

			case "enter":

				if (getObjectLocator() != null){

					failureOfTestStep= new EditBox(driver).enter(getObjectLocator(), testData);

				}else {

					prepareFailureOfTestStepMessage();				
				}

				break;

			case "clear":

				if (getObjectLocator() != null){

					failureOfTestStep=new EditBox(driver).clear(getObjectLocator());

				}else {

					prepareFailureOfTestStepMessage();				
				}

				break;

			case "validate":

				if (getObjectLocator() != null){

					failureOfTestStep=new EditBox(driver).validate(getObjectLocator(),testData);

				}else {

					prepareFailureOfTestStepMessage();				
				}

				break;

			}

			//statusTestStep = getTestStepResult(); // Update the Status in Result file and print on console

		} 

		else if (keyword.equalsIgnoreCase("Globalvariable")) {

			switch (action) {
			case "storeglobal":
				testData = (!variableNameFromTestStep.isEmpty() ? variableNameFromTestStep: testData);
				failureOfTestStep = new Globalvariable(driver).storeGlobal(testData,contentRepo);
			}

		}else if (keyword.equalsIgnoreCase("Wait")){

			if(action==null){

				new Wait(driver).threadwait("20");
			}
			else{

				switch (action) {

				case "threadwait":

					new Wait(driver).threadwait(testData);

					break;

				case "explicitwait":
					
					
					new Wait(driver).explicitWait(objProperty);
					
					break;
					
				case "explicitwaitbyvisible":	
					
					
						new Wait(driver).explicitWaitbyvisible(getObjectLocator());
					

					break;

				default:

					new Wait(driver).threadwait("10");

					break;
				}
			}

			failureOfTestStep = "No";

			//statusTestStep = getTestStepResult(); // Update the Status in Result file and print on console

		}else if (keyword.equalsIgnoreCase("Button")){

			switch (action) {

			case "click":

				if (getObjectLocator()!=null){

					failureOfTestStep=new Button(driver).click(getObjectLocator());
				}else {

					prepareFailureOfTestStepMessage();				
				}

				break;

			case "jsclick":

				if (getObjectLocator()!=null){

					failureOfTestStep=new Button(driver).jsclick(getObjectLocator());
				}else {

					prepareFailureOfTestStepMessage();				
				}

				break;

			case "validate":

				if (getObjectLocator()!=null){

					failureOfTestStep=new Button(driver).validate(getObjectLocator());
				}else {

					prepareFailureOfTestStepMessage();				

				}

				break;

			}

			//statusTestStep = getTestStepResult(); // Update the Status in Result file and print on console

		}else if (keyword.equalsIgnoreCase("Script")){

			switch (action) {

			case "execute":

					failureOfTestStep=new Script(driver).execute(testData,contentRepo);

				break;
			case "executeifqueryempty":

				failureOfTestStep=new Script(driver).executeifqueryempty(testData,contentRepo);

			break;
			}

			//statusTestStep = getTestStepResult(); // Update the Status in Result file and print on console

		}else if (keyword.equalsIgnoreCase("window")){
			String currenthandle = driver.getWindowHandle();
			
			Window window = null;
			switch (action) {

			case "scroll":

				failureOfTestStep = new Window(driver).scroll(testData);
				
				break;
				
			case "getwindowhandle":

				testData= (!variableNameFromTestStep.isEmpty() ? variableNameFromTestStep : testData);
				window = new Window(driver);
				failureOfTestStep = window.getWindowHandle();
				String windowHandle = window.getCurrentWindowHandle();

				System.out.println("text : " + windowHandle + "  stored in dynamic variable : " + testData);
				logger.info("text : " + windowHandle + "  stored in dynamic variable : " + testData);

				dynamicVariables.addObjectToMap(testData, windowHandle);															

				break;

			case "switchtowindow":

				if (testData !=null){

					failureOfTestStep = new Window(driver).switchToWindow(testData);

				}else {

					prepareFailureOfTestStepMessage();				

				}

				break;

			case "switchtonewwindow":
				
				failureOfTestStep = new Window(driver).switchToNewWindow();

				break;
				
			case "acceptalert":
				
				failureOfTestStep = new Window(driver).acceptAlert();

				break;

			case "dismissalert":
				
				failureOfTestStep = new Window(driver).dismissAlert();

				break;
				
			case "switchtoiframe":
				
				failureOfTestStep = new Window(driver).switchToIframe(testData);

				break;
				
			case "switchtodefault":
	
				failureOfTestStep = new Window(driver).switchTodefault(getObjectLocator());
				
				break;

	
			}

			//statusTestStep = getTestStepResult(); // Update the Status in Result file and print on console

		}else if (keyword.equalsIgnoreCase("dropdown")){

			switch (action) {

			case "select":

				if (getObjectLocator()!=null){

					failureOfTestStep=new Dropdown(driver).select(getObjectLocator(), testData);
				}else {

					prepareFailureOfTestStepMessage();				
				}

				break;

			case "verifydropdownvalues":

				if (getObjectLocator()!=null){

					failureOfTestStep=new Dropdown(driver).verifyDropdownValues(getObjectLocator(), testData);
				}else {

					prepareFailureOfTestStepMessage();				
				}

				break;


			case "selectlistitems":

				if (getObjectLocator()!=null){

					failureOfTestStep=new Dropdown(driver).selectlistitems(getObjectLocator(), testData);

				}else {

					prepareFailureOfTestStepMessage();				

				}

				break;

			case "selectautosearchvalue":

				failureOfTestStep=new Dropdown(driver).selectAutoSearchValue(testData);

				break;
				
			case "getdropdownvalues":
			
				List<String> dropdownlist = new Dropdown(driver).getdropdownvalues(getObjectLocator());
				
				if(dropdownlist==null){
					failureOfTestStep = "store dropdown value Failed";
				}else{
					failureOfTestStep = "No";
					dynamicVariables.addObjectToMap(variableNameFromTestStep, dropdownlist.toString());
				}
			
				break;
			}

			//statusTestStep = getTestStepResult(); // Update the Status in Result file and print on console

		}else if (keyword.equalsIgnoreCase("Link")){

			switch (action){

			case "click":

				if (getObjectLocator()!=null){

					failureOfTestStep=new Link(driver).click(getObjectLocator());
				}else {

					prepareFailureOfTestStepMessage();				
				}

				break;

			case "validate":

				if (getObjectLocator()!=null){

					failureOfTestStep=new Link(driver).validate(getObjectLocator());
				}else {

					prepareFailureOfTestStepMessage();				
				}									

				break;

			case "getactions":

				if (getObjectLocator()!=null){

					failureOfTestStep=new Link(driver).getActions(getObjectLocator(),testData);

				}else {

					prepareFailureOfTestStepMessage();				
				}									

				break;	
			}

			//statusTestStep = getTestStepResult(); // Update the Status in Result file and print on console

		}else if (keyword.equalsIgnoreCase("Validation")){

			switch (action){

			case "assertion":

				if(getObjectLocator()==null&&testData!=null){
					if(testData.contains("=")){
						boolean date = false;
						if(testData.contains("date")){
							date = true;
							testData = testData.replaceAll("date","").trim();
						}
						String[] testDataarray = testData.split("=");
						String testdata1 = testDataarray[0];
						String testdata2 = testDataarray[1];
						if(date){
							failureOfTestStep = new Validation(driver).dateassertion(testdata1,testdata2);
						}
						else{
						if(testdata1.contains("-")){ //if(testdata1.contains("-")||testdata1.contains("/")){ removed ||testdata1.contains("/") on 29/10/2015 - Prabs
							failureOfTestStep = new Validation(driver).datevalidation(testdata1,testdata2);
						}else{
							failureOfTestStep = new Validation(driver).assertion(testdata1,testdata2);
						}
						}
					}else{
						System.out.println("PASS 2 TESTDATA TO DO THE COMPARISION and DATA SHOULD BE VALUE1=VALUE2");
						logger.info("PASS 2 TESTDATA TO DO THE COMPARISION");
						prepareFailureOfTestStepMessage();
					}

				}else{
					String errorMessage  = "Test data should not be null"
							+ "TestStep : " + (getTestScriptRowIndex() + 1)
							+ "; Keyword : " + getKeyword() + "; Action : " + getAction()
							+ "; testdata : " + testData + ";";
					setFailureOfTestStep(errorMessage);
				}

				break;

			case "amountrange":

				if(getObjectLocator()==null&&testData!=null){
					if(testData.contains("=")){
						String[] testDataarray = testData.split("=");
						String testdata1 = testDataarray[0];
						String testdata2 = testDataarray[1];

						failureOfTestStep = new Validation(driver).amountrange(testdata1,testdata2);

					}else{
						System.out.println("PASS 2 TESTDATA TO DO THE COMPARISION");
						logger.info("PASS 2 TESTDATA TO DO THE COMPARISION");
						prepareFailureOfTestStepMessage();
					}

				}else{
					String errorMessage  = "Test data should not be null"
							+ "TestStep : " + (getTestScriptRowIndex() + 1)
							+ "; Keyword : " + getKeyword() + "; Action : " + getAction()
							+ "; testdata : " + testData + ";";
					setFailureOfTestStep(errorMessage);
				}

				break;

			case "validate":

				if (getObjectLocator()!=null){

					failureOfTestStep=new Validation(driver).validate(getObjectLocator(),testData);												

				}else {

					prepareFailureOfTestStepMessage();				
				}

				break;

			case "validatetext":

				if (getObjectLocator()!=null){

					failureOfTestStep=new Validation(driver).validateText(getObjectLocator(),testData);												
				}else {

					prepareFailureOfTestStepMessage();				
				}

				break;
				
			case "comparevalues":

					if(testData.contains("=")){
							String[] testDataCompareArray = testData.split("=");
							String verifyData1 = testDataCompareArray[0];
							String verifyData2 = testDataCompareArray[1];
						
						failureOfTestStep=new Validation(driver).compareValues(verifyData1,verifyData2);	
						
					}else{
						System.out.println("PASS 2 TESTDATA TO DO THE COMPARISION");
						logger.info("PASS 2 TESTDATA TO DO THE COMPARISION");
						prepareFailureOfTestStepMessage();
					}
					

					break;	
					
			case "valuenotpresent":

				if(testData.contains("=")){
						String[] testDataCompareArray = testData.split("=");
						String verifyData1 = testDataCompareArray[0];
						String verifyData2 = testDataCompareArray[1];
					
					failureOfTestStep=new Validation(driver).valuenotpresent(verifyData1,verifyData2);	
					
				}else{
					System.out.println("PASS 2 TESTDATA TO DO THE COMPARISION");
					logger.info("PASS 2 TESTDATA TO DO THE COMPARISION");
					prepareFailureOfTestStepMessage();
				}
				

				break;
			case "validatelist":

				if (getObjectLocator()!=null){

					failureOfTestStep=new Validation(driver).validateList(getObjectLocator(),testData);												
				}else {

					prepareFailureOfTestStepMessage();				
				}

				break;

			case "validateproperty":

				if (getObjectLocator()!=null){

					failureOfTestStep=new Validation(driver).validateProperty(getObjectLocator(),testData);												
				}else {

					prepareFailureOfTestStepMessage();				
				}

				break;

			case "pagetitle":

				failureOfTestStep=new Validation(driver).pageTitle(testData);												

				break;
				
			case "searchtext":

				if (testData!=null){

					failureOfTestStep=new Validation(driver).searchText(testData);												

				}else {

					prepareFailureOfTestStepMessage();				
				}

				break;	
				
			case "searchexacttext":

				if (testData!=null){

					failureOfTestStep=new Validation(driver).searchExactText(testData);												

				}else {

					prepareFailureOfTestStepMessage();				
				}

				break;	

				
			}
			
			
			statusTestStep = getTestStepResult(); // Update the Status in Result file and print on console

		}else if (keyword.equalsIgnoreCase("RadioButton")){

			switch (action){

			case "click":

				if (getObjectLocator()!=null){

					failureOfTestStep=new RadioButton(driver).click(getObjectLocator());

				}else { 

					prepareFailureOfTestStepMessage();				
				}	

				break;

			case "byvalue":

				if (getObjectLocator()!=null){

					failureOfTestStep=new RadioButton(driver).byValue(getObjectLocator(),testData);

				}else {

					prepareFailureOfTestStepMessage();				
				}	

				break;

			case "bylabel":

				if (getObjectLocator()!=null){

					failureOfTestStep=new RadioButton(driver).byLabel(getObjectLocator(),testData);

				}else {

					prepareFailureOfTestStepMessage();				
				}	

				break;

			case "isselected":

				if (getObjectLocator()!=null){

					failureOfTestStep=new RadioButton(driver).isSelected(getObjectLocator(),testData);
				}else {

					prepareFailureOfTestStepMessage();				
				}	

				break;
			}

			//statusTestStep = getTestStepResult(); // Update the Status in Result file and print on console

		}else if (keyword.equalsIgnoreCase("CheckBox")){

			switch (action){

			case "click":

				if (getObjectLocator()!=null){

					failureOfTestStep=new CheckBox(driver).click(getObjectLocator());
				}else {

					prepareFailureOfTestStepMessage();				
				}	

				break;

			case "check":

				if (getObjectLocator()!=null){

					failureOfTestStep=new CheckBox(driver).check(getObjectLocator());
				}else {

					prepareFailureOfTestStepMessage();				
				}	

				break;

			case "isselected":

				if (getObjectLocator()!=null){

					failureOfTestStep=new CheckBox(driver).isSelected(getObjectLocator());
				}else {

					prepareFailureOfTestStepMessage();				
				}	

				break;
				
			case "uncheck":

				if (getObjectLocator()!=null){

					failureOfTestStep=new CheckBox(driver).unCheck(getObjectLocator());
				}else {

					prepareFailureOfTestStepMessage();				
				}	
				break;
			}

			//statusTestStep = getTestStepResult(); // Update the Status in Result file and print on console

		}else if (keyword.equalsIgnoreCase("Email")){
			//driver.close();
			switch (action){

			case "getlink":

				failureOfTestStep=new Email(dynamicVariables).getLink("qaautomationmodria@gmail.com", "Test@@123", "The best of Gmail",testData);

				break;

			case "searchsubject":

				failureOfTestStep=new Email(dynamicVariables).searchSubject(testData);

				break;

			case "setpasswordfromemail":

				System.out.println("login to searchNatorSubject case loop");

				failureOfTestStep=new Email(dynamicVariables).setPassword(testData,variableNameFromTestStep);

				break;

			case "searchrecipient":

				failureOfTestStep=new Email(dynamicVariables).searchRecipient(testData);

				break;

			case "searchbody":

				failureOfTestStep=new Email(dynamicVariables).searchBody(testData);

				break;

			case "searchtosubjectbody":

				failureOfTestStep=new Email(dynamicVariables).searchToSubjectBody(testData);

				break;

			case "getapplink":

				String appEmailLinkVaribale = null;
				String appEmailLink;
				if (variableNameFromTestStep!=null){
					appEmailLinkVaribale = variableNameFromTestStep;
				}						
				appEmailLink=new Email(dynamicVariables).getApplicationLink(testData);
				if(!appEmailLink.equalsIgnoreCase("No")){
					failureOfTestStep="No";
					dynamicVariables.addObjectToMap(appEmailLinkVaribale, appEmailLink);															
				}else{
					failureOfTestStep=appEmailLink;
				}
				break;

			case "getpasswordresetlink":
				String passwordEmailLinkVaribale = null;
				String passwordEmailLink;
				if (variableNameFromTestStep!=null){
					passwordEmailLinkVaribale = variableNameFromTestStep;
				}						
				passwordEmailLink = new Email(dynamicVariables).getPasswordResetLink(testData);
				if(!passwordEmailLink.equalsIgnoreCase("No")){
					failureOfTestStep="No";
					dynamicVariables.addObjectToMap(passwordEmailLinkVaribale, passwordEmailLink);															
				}else{
					failureOfTestStep=passwordEmailLink;
				}
				break;
			}

			//statusTestStep = getTestStepResult(); // Update the Status in Result file and print on console

		}else if (keyword.equalsIgnoreCase("Popup")){

			//statusTestStep = getTestStepResult(); // Update the Status in Result file and print on console

		}else if (keyword.equalsIgnoreCase("Element")){

			failureOfTestStep = "No";

			switch(action){

			case "gettext":
				if (getObjectLocator()!=null){

					testData= (!variableNameFromTestStep.isEmpty() ? variableNameFromTestStep : testData);

					String elementText =new Element(driver).getText(getObjectLocator(),testData);
					if(testData.equalsIgnoreCase("#CASENUMBER#")){
						elementText = elementText.replace("(", "");
						elementText = elementText.replace(")", "");
					}
					if(testData.equalsIgnoreCase("#ACCOUNTID#")){
						logger.info("varible is given in testscripts");
					}
					else{
						logger.info("variable field is empty");
					}
					System.out.println("text : " + elementText + "  stored in dynamic variable : " + testData);
					logger.info("text : " + elementText + "  stored in dynamic variable : " + testData);
					dynamicVariables.addObjectToMap(testData, elementText);															
				}
				else{

					failureOfTestStep = keyword + " not present please check..... For Keyword " +keyword + " - Action - " + action + ".. Locator : " + objProperty;
				}


				break;
				
			case "sendkeys":
				if (getObjectLocator()!=null){

					testData= (!variableNameFromTestStep.isEmpty() ? variableNameFromTestStep : testData);

					failureOfTestStep =new Element(driver).sendkeys(getObjectLocator(),testData);

				}
				else{

					failureOfTestStep = keyword + " not present please check..... For Keyword " +keyword + " - Action - " + action + ".. Locator : " + objProperty;
				}


				break;



			case "getmoneyvalue":
				if (getObjectLocator()!=null){

					testData= (!variableNameFromTestStep.isEmpty() ? variableNameFromTestStep : testData);

					failureOfTestStep =new Element(driver).getMoneyValue(getObjectLocator(),testData, dynamicVariables);

				}
				else{

					failureOfTestStep = keyword + " not present please check..... For Keyword " +keyword + " - Action - " + action + ".. Locator : " + objProperty;
				}


				break;

			case "getproperty":
				if (getObjectLocator()!=null){

					testData= (!variableNameFromTestStep.isEmpty() ? variableNameFromTestStep : testData);
					Element element = new Element(driver);
					String elementText = element.getProperty(getObjectLocator(),testData);

					System.out.println("text : " + elementText + "  stored in dynamic variable : " + testData);
					logger.info("text : " + elementText + "  stored in dynamic variable : " + testData);

					dynamicVariables.addObjectToMap(element.getVariable(), elementText);															
				}
				else{

					failureOfTestStep = keyword + " not present please check..... For Keyword " +keyword + " - Action - " + action + ".. Locator : " + objProperty;
				}


				break;

			case "doubleclick":
				if (getObjectLocator()!=null){

					new Element(driver).doubleClick(getObjectLocator());

				}
				else{

					failureOfTestStep = keyword + " not present please check..... For Keyword " +keyword + " - Action - " + action + ".. Locator : " + objProperty;
				}


				break;

			case "getcaseid":
				if (variableNameFromTestStep != null || testData != null) {
					testData=(variableNameFromTestStep!= null && !variableNameFromTestStep.isEmpty() ? variableNameFromTestStep : testData);
					String caseId =new Element(driver).getCaseId(testData);
					dynamicVariables.addObjectToMap(testData, caseId);															
				}else{
					failureOfTestStep = keyword + " not present please check..... For Keyword " +keyword + " - Action - " + action + ".. Locator : " + objProperty;
				}

				break;

			case "getaaacasenumber":
				if (variableNameFromTestStep != null || testData != null){
					testData=(variableNameFromTestStep!= null && !variableNameFromTestStep.isEmpty() ? variableNameFromTestStep : testData);
					String caseNumber =new Element(driver).getAAACaseNumber(getObjectLocator(), testData);
					dynamicVariables.addObjectToMap(testData, caseNumber);															
				}else{
					failureOfTestStep = keyword + " not present please check..... For Keyword " +keyword + " - Action - " + action + ".. Locator : " + objProperty;
				}

				break;

			case "mousehover":
				if (getObjectLocator()!=null){
					failureOfTestStep=new Element(driver).mouseHover(getObjectLocator());														
				}else{
					failureOfTestStep = keyword + " not present please check..... For Keyword " +keyword + " - Action - " + action + ".. Locator : " + objProperty;
				}

				break;

			case "contextclick":
				if (getObjectLocator()!=null){
					failureOfTestStep=new Element(driver).contextClick(getObjectLocator());														
				}else{
					failureOfTestStep = keyword + " not present please check..... For Keyword " +keyword + " - Action - " + action + ".. Locator : " + objProperty;
				}

				break;

			case "isvisible":
				if (getObjectLocator()!=null){
					failureOfTestStep=new Element(driver).isVisible(getObjectLocator(),testData);														
				}else{
					failureOfTestStep = keyword + " not present please check..... For Keyword " +keyword + " - Action - " + action + ".. Locator : " + objProperty;
				}

				break;

			case "isenabled":
				if (getObjectLocator()!=null){
					failureOfTestStep=new Element(driver).isEnabled(getObjectLocator(),testData);														
				}else{
					failureOfTestStep = keyword + " not present please check..... For Keyword " +keyword + " - Action - " + action + ".. Locator : " + objProperty;
				}

				break;	

			case "isselected":
				if (getObjectLocator()!=null){
					failureOfTestStep=new Element(driver).isSelected(getObjectLocator(),testData);														
				}else{
					failureOfTestStep = keyword + " not present please check..... For Keyword " +keyword + " - Action - " + action + ".. Locator : " + objProperty;
				}

				break;

			case "isExists":

				failureOfTestStep=new Element(driver).isExists(getObjectLocator(),testData);	
				break;

			}

			//statusTestStep = getTestStepResult(); // Update the Status in Result file and print on console

		}else if(keyword.equalsIgnoreCase("Lists")){

			switch(action){

			case "getalllistvalues":

				if(variableNameFromTestStep !=null || testData != null){
					testData = variableNameFromTestStep!=null && !variableNameFromTestStep.isEmpty() ? variableNameFromTestStep : testData ;
					Lists lst = new Lists(driver);
					failureOfTestStep = lst.getalllistvalues(getObjectLocator(),objProperty);
					String listvalues = lst.getListvalues().toString();
					System.out.println("List values to add in hashmap :"+listvalues);
					logger.info("List values to add in hashmap :"+listvalues);
					System.out.println("List key to add in hashmap :"+testData);
					logger.info("List key to add in hashmap :"+testData);
					dynamicVariables.addObjectToMap(testData,listvalues);
					System.out.println(dynamicVariables.getObjectProperty(testData));
					logger.info(dynamicVariables.getObjectProperty(testData));
				}

				break;
				
			case "clicklistitem":

				if(variableNameFromTestStep !=null || testData != null){
					testData = variableNameFromTestStep!=null && !variableNameFromTestStep.isEmpty() ? variableNameFromTestStep : testData ;
					Lists lst = new Lists(driver);
					failureOfTestStep = lst.clickListItem(testData);
					//String listvalues = lst.getListvalues().toString();

				}

				break;
				
			
			}
			//statusTestStep = getTestStepResult();

		}else if(keyword.equalsIgnoreCase("Varibale")){

			switch(action){

			case "store":

				dynamicVariables.addObjectToMap(variableNameFromTestStep,testData);

				break;
			}
			//statusTestStep = getTestStepResult();
			
		}else {
		

			failureOfTestStep = "ERROR : Keyword : " + keyword + " and  Action : "+ action + " is not valid ";

			errMain=failureOfTestStep+"\n"+	"At test Step - Keyword = "+keyword + ", Action = " + action + ", Locator = " + objProperty;

			//statusTestStep = new TestResultsProcessor().testStepResult(failureOfTestStep, scriptLogFile, testScriptRowIndex, resultCellIndex, scriptName, errMain);

		} // Closing if condition of Keyword & switch cases for each control and actions

		
		statusTestStep = getTestStepResult();
		
		if(action != null && !action.equalsIgnoreCase("close") && !keyword.equalsIgnoreCase("email")) 

			new Wait(driver).waitForPageLoad();

		return statusTestStep;
	}

	private void prepareByObject() throws Exception {

		By objectLocator = null;

		if (objProperty != null && !objProperty.isEmpty()) {

			objectLocator = new WebElementProcessor(driver).isBy(driver,
					objProperty);
		}

		setObjectLocator(objectLocator);

	}

	private By getObjectLocator() {

		return objectLocator;
	}

	private void setObjectLocator(By objectLocator) {

		this.objectLocator = objectLocator;

	}

	
	public String getTestStepResult() throws Exception {

		if (flowType.equals("Negative")) {

			System.out.println("At test Step - Keyword = " + getKeyword() + ", Action = " + getAction() + ", Locator = "
					+ getObjProperty());

			failureOfTestStep = "No";
			// logging
			logger.info("At test Step - Keyword = " + getKeyword()
					+ ", Action = " + getAction() + ", Locator = "
					+ getObjProperty());
		}

		if (!failureOfTestStep.equalsIgnoreCase("NO")) {

			prepareErrMain();

		}

		if (!failureOfTestStep.equalsIgnoreCase("No")){
			
			statusTestStep = "FAIL";
			
		}else{
			
			statusTestStep = "PASS";
		}

		
		return statusTestStep;
	}

	private void prepareErrMain() {
		String errorMessage = "At Test Step : " + (getTestScriptRowIndex() + 1) + "\n" + getFailureOfTestStep() + "\n"
				   + "Keyword = " + getKeyword() + ", Action = "
				+ getAction() + ", Locator = " + getObjProperty() +
				"\n" + getAAACaseDetails();
		setErrMain(errorMessage);
	}

	public String getErrMain() {
		return errMain;
	}

	private void setErrMain(String errMain) {
		this.errMain = errMain;
	}

	private void prepareFailureOfTestStepMessage() {

		String errorMessage = "Object is not found with the locator given \n"
				+ "TestStep : " + (getTestScriptRowIndex() + 1)
				+ "; Keyword : " + getKeyword() + "; Action : " + getAction()
				+ "; Locator : " + getObjProperty() + ";";

		setFailureOfTestStep(errorMessage);

	}

	private String getKeyword() {
		return keyword;
	}

	private String getAction() {
		return action;
	}

	private String getObjProperty() {
		return objProperty;
	}

	private int getTestScriptRowIndex() {
		return testScriptRowIndex;
	}

	/*
	 * private String getTestData() { return testData; }
	 * 
	 * private String getFlowType() { return flowType; }
	 * 
	 * private int getResultCellIndex() { return resultCellIndex; }
	 * 
	 * private String getScriptName() { return scriptName; }
	 * 
	 * private String get() { return ; }
	 * 
	 * private String getScriptLogFile() { return scriptLogFile; }
	 * 
	 * private DynamicVarRepository getDynamicVariables() { return
	 * dynamicVariables; }
	 * 
	 * private WebDriver getDriver() { return driver; }
	 * 
	 * private String getVariableNameFromTestStep() { return
	 * variableNameFromTestStep; }
	 */

	public String getFailureOfTestStep() {
		return failureOfTestStep;
	}

	private void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	private void setAction(String action) {
		this.action = action;
	}

	private void setObjProperty(String objProperty) {
		this.objProperty = objProperty;
	}

	private void setTestData(String testData) {
		this.testData = testData;
	}

	private void setFlowType(String flowType) {
		this.flowType = flowType;
	}

	private void setTestScriptRowIndex(int testScriptRowIndex) {
		this.testScriptRowIndex = testScriptRowIndex;
	}

/*	
	private void setResultCellIndex(int resultCellIndex) {
		//this.resultCellIndex = resultCellIndex;
	}*/

	public void setDynamicVariables(DynamicVarRepository dynamicVariables) {
		this.dynamicVariables = dynamicVariables;
	}

	private void setDriver(WebDriver driver) {
		this.driver = driver;
	}

	private void setVariableNameFromTestStep(String variableNameFromTestStep) {
		this.variableNameFromTestStep = variableNameFromTestStep;
	}

	private void setFailureOfTestStep(String failureOfTestStep) {
		this.failureOfTestStep = failureOfTestStep;
	}
	
	private String getAAACaseDetails() {
		String caseDetails = "";
		if (dynamicVariables.getObjectProperty("#CASENO#") != null)
			caseDetails = "#CASENO#" + " : " + dynamicVariables.getObjectProperty("#CASENO#") + "\n";
		if (dynamicVariables.getObjectProperty("#CASE_ID#") != null)
			caseDetails = caseDetails + "#CASE_ID#" + " : " + dynamicVariables.getObjectProperty("#CASE_ID#");
		return caseDetails;
	}


}
