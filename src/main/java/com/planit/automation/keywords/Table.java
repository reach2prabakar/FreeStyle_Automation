package com.planit.automation.keywords;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.By.ByName;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;

import com.gargoylesoftware.htmlunit.ElementNotFoundException;
import com.planit.automation.common.Common;
import com.planit.automation.library.DynamicVarRepository;
import com.planit.automation.processor.WebElementProcessor;

/**
 * Table - Keyword
 * @class Table
 * @description - Table related all Actions are mentioned in this class
 * 
 * @throws Exception
 */	
public class Table{
	
	private static Logger logger = Logger.getLogger(Table.class.getName());
	public String logMessage = null;
	private String keyword = "Table";
	public String err1="No";
	public WebDriver driver;
	private ArrayList<String> arraylist = new ArrayList<>();

	public Table(WebDriver driver){
		this.driver = driver;
	}
	
	/**
	 * Table - Keyword & validate - Action
	 * @method validate
	 * @description - validate action to validate specified contents in Table.
	 * 
	 * @throws Exception
	 */
	public String validate(By tableName,String validateValue) throws Exception {
		try{
			err1="No";
			WebElement Webtable=driver.findElement(tableName); // Find Table
			String strArray[] = Webtable.getText().split("\\n"); // Get array of contents of Table
			validateValue = validateValue.replace("`", ""); //Search Value in Table
			//Iterate Table values to search Expected Value
			for(int i=0; i < strArray.length; i++){
				if (strArray[i].trim().equals(validateValue.trim())){ 
					logMessage = "At test Step - Keyword = "+keyword+", Action = "+ "validate"+" , Locator = " +  tableName.toString(); // This is to Display on console as well in Log File
					System.out.println(logMessage); // Print on Console
					//logging
					logger.info(logMessage);
					break;
				}else{
					err1 = keyword+", Action = "+ "validate"+",  Unable to find Table Valus - "+validateValue;//Preparing Error message when unable to perform action
				}
			}
		} catch(Exception e) {
			err1="******** Error occoured in test step *********: Table - Validate - not present please check..... Locator : " + tableName.toString();
			System.out.println(err1); // Print on Console
			//logging
			logger.error(err1);
			//Log.error(err1); //Print on Log
		}
		if (err1 !=null){
			System.out.println(err1); // Print on Console
			//logging
			logger.info(err1);
		}

		return err1; //return message when Failure happens, otherwise null will be return.
	}

	
	public String isTablepresent(By tableName) throws Exception {
		try{
			err1="No";
			try{
			driver.findElement(tableName);// Find Table
			}
			catch(Exception e){
				err1 = "Element not found;; Table.java - > line number " + Thread.currentThread().getStackTrace()[1].getLineNumber();
				throw new Exception(err1);
			}
			
		} catch(Exception e) {
			System.out.println(e); // Print on Console
			//logging
			logger.error(err1);
			//Log.error(err1); //Print on Log
		}
		if (!err1.equalsIgnoreCase("NO")){
			System.out.println(err1); // Print on Console
			//logging
			logger.info(err1);
		}

		return err1; //return message when Failure happens, otherwise null will be return.
	}
	/**
	 * Table - Keyword & search - Action
	 * @method search
	 * @description - search action to search specified contents in Table.
	 * 
	 * @throws Exception
	 */
	public String search(String objectsString,String searchValues) throws Exception {
		try{
			err1 = "No";
			String searchPass = null;
			String searchFail = null;
			String eachRowData = null;
			try{
				if(objectsString==null||searchValues==null){
					String error = "please check the object property and searchvales , it should not be null : Table.java -> "
				+Thread.currentThread().getStackTrace()[1].getLineNumber();
					throw new Exception(error);
				}
			}catch (Exception e) {
				throw new Exception(e);
			}
			try{
				if(!searchValues.contains("=")){
					String error = "please check the searchvales , it should have '=' in testdata, Keyword - > Table , Action - > Search works accordingly"
							+ " if no '=' in testdata change to other action or change test data: Table.java line number-> "
				+Thread.currentThread().getStackTrace()[1].getLineNumber();
					throw new Exception(error);
				}
			}catch (Exception e) {
				throw new Exception(e);
			}
			String[] searchArrayExpMultple = searchValues.split(","); // Creating Array for Expected Key=Value pair
			String[] searchArrayExpected = searchValues.split("="); // Creating Array if Search Values are more than 1
			String[] pageNoArray = null;
			String searchExpectedKey = searchArrayExpected[0].trim(); // Getting Search Key
			String searchExpectedValue = searchArrayExpected[1].toLowerCase().trim(); // Search Value
			String tableObject = null;
			String[] objArray = null;
			By objByPageNo=null;
			if(objectsString.contains(",")){
				objArray = objectsString.split(","); // Array of Objects Properties, Table, Page No and Next Page Link
				objByPageNo = new WebElementProcessor(driver).isBy(driver, objArray[1]);
				tableObject=objArray[0];
			}else{
				tableObject=objectsString;
			}		
			boolean NameFlag=false;
			int colVerificaiton = 0;
			String[] NameSplit = null;
			int rowcnt;
			boolean headerTag=false;
			String skeyIndex = null;
			String[] skeyMulIndex = null; // Check for Seach Key Multiple searched
			int pageNos;
			String pageNo;
			boolean pageFlag=false;
			if (objByPageNo!=null){
				pageNo = driver.findElement(objByPageNo).getText(); // Get Next Page No
				pageNoArray = pageNo.split(" "); //Get page No
				pageNos=Integer.parseInt(pageNoArray[3]);
				pageFlag=true;
			}else{
				//err1 = " Unable to Extract PAGE Numbers - not present please check..... For Keyword  - "+keyword+", Action - "+Global.action ;
				pageNos=1;
				pageNo="Page Number : 1";
				//return err1;
			}
			//Iterating throgh all Pages to Seach Values
			for (int pageLoop=1;pageLoop<=pageNos;pageLoop++) {
				System.out.println("pageNo " +pageLoop); //Display Page No in Console 
				By objByTable = new WebElementProcessor(driver).isBy(driver, tableObject);
				if (objByTable!=null){
					WebElement table = driver.findElement(objByTable); 
					List<WebElement> allRows1 = table.findElements(By.tagName("tr")); //Get All rows in Table
					// Now get all the TR elements from the table 
					// And iterate over them, getting the cells
					rowcnt=1;
					for (WebElement eachRow : allRows1) { 
						eachRowData = eachRow.getText().trim();
						System.out.println("Table row data " + rowcnt + " : " + eachRowData);
						//logging
						logger.info("Table row data " + rowcnt + " : " + eachRowData);
						if (searchArrayExpMultple.length<=1){
							if(headerTag==false){
								System.out.println("testing inside");// Header flag to get Column index for searching related values in columns
								headerTag=true;   //Ensure that if condition ran only for one time
								List<WebElement> eachCellHeader = eachRow.findElements(By.tagName("th"));
								
								for (int colheader=0;colheader<=(eachCellHeader.size()-1);colheader++){   //Looping throgh column header
									String cellHeaderName = eachCellHeader.get(colheader).getText().trim();
									if (searchExpectedKey.indexOf("_")>=0){ // This if condition only for First Name and Last Name
										NameFlag=true;
										NameSplit = searchExpectedKey.split("_");
										searchExpectedKey = NameSplit[0];
									}
									
									System.out.println("cellHeaderName "+ cellHeaderName);
									System.out.println("searchExpectedKey "+ searchExpectedKey);
									if (cellHeaderName.equalsIgnoreCase(searchExpectedKey)){
										colVerificaiton=colheader;  // Store column index when matches which Column we need to search
										System.out.println("colheader matched index value is "+colVerificaiton );
									}
								}
							}

							if(headerTag==true){ //Iterate for Searching Values after getting Index number of Column 
								List<WebElement> eachCellData = eachRow.findElements(By.tagName("td"));
								int noOfRowsReturned = eachCellData.size(); //Check Size of rows displyed
								if (noOfRowsReturned!=0){
									for (int eachCol=colVerificaiton;eachCol<=(noOfRowsReturned-1);eachCol++){ //Looping throgh all rows
										if (eachCol==colVerificaiton){
											String applCellValue = eachCellData.get(eachCol).getText().toLowerCase().toString();
											applCellValue=applCellValue.trim();
											
											if (NameFlag==true){ //This is only when seaching values for First and Last Name
												String[] appValueSplit = applCellValue.split(" ");
												if(appValueSplit.length>1){
												if (NameSplit[1].equalsIgnoreCase("first")){
													applCellValue = appValueSplit[0];
												}else{
													applCellValue = appValueSplit[1];
												}
												}
											}
											
											System.out.println("applCellValue :"+applCellValue);
											System.out.println("searchExpectedValue :"+searchExpectedValue);
											//System.out.println("App cell charecter ="+applCellValue.length());
											//System.out.println("Expected cell charecter ="+searchExpectedValue.length());
											//if (applCellValue.equalsIgnoreCase(searchExpectedValue)){ 
											if (applCellValue.contains(searchExpectedValue)){ 
														searchPass = "YES";
											}
											else{
												if(pageFlag==false){
													searchFail = "YES";
													err1="Search Failed for value "+searchExpectedValue;
													System.out.println("Application Value : "+applCellValue+" , Expected Value : "+searchExpectedValue);
													//logging
													logger.info("Application Value : "+applCellValue+" , Expected Value : "+searchExpectedValue);
													//break;
												}
												
											}
										}
									}
								}
								rowcnt=rowcnt+1;
							}else{
								searchFail = "YES";
								err1="NO Results found for Search = " + searchExpectedValue;
							}
							if (searchFail!=null){
								break;
							}

						}else{ //else condition for Multiple Search

							if(headerTag==false){
								//Condition for getting Multiple Index numbers as need to search multiple columns in Table
								for (int mulSearch =0; mulSearch <= (searchArrayExpMultple.length-1);mulSearch++){
									searchArrayExpected = searchArrayExpMultple[mulSearch].split("="); //Get Array of Multiple Searches
									searchExpectedKey = searchArrayExpected[0].trim(); // Get Search Column
									searchExpectedValue = searchArrayExpected[1].trim(); // Expected Value
									List<WebElement> eachCellHeader = eachRow.findElements(By.tagName("th"));
									for (int colheader=0;colheader<=(eachCellHeader.size()-1);colheader++){ // this to get Indexes of Searcahble cplumns
										String cellHeaderName = eachCellHeader.get(colheader).getText().trim();
										if (searchExpectedKey.indexOf("_")>=0){
											NameFlag=true;
											NameSplit = searchExpectedKey.split("_");
											searchExpectedKey = NameSplit[0];
										}
										if (cellHeaderName.equals(searchExpectedKey)){
											if (skeyIndex==null){
												skeyIndex= Integer.toString(colheader);
											}else{
												skeyIndex=skeyIndex+","+Integer.toString(colheader);; // This variable will store all Indexes
											}
										}
									}
								}
							}
							if (headerTag==true){ // This condition for searching values after getting indexes of Columns
								searchPass = null;	
								searchFail = null;
								skeyMulIndex = skeyIndex.split(","); // Get each column indexes
								List<WebElement> eachCellData = eachRow.findElements(By.tagName("td"));
								int noOfRowsReturned = eachCellData.size(); // Get each Rows
								if (noOfRowsReturned!=0){
									for (int skey=0; skey <= skeyMulIndex.length-1;skey++){ //Iterate each row with each Column for searches
										searchArrayExpected = searchArrayExpMultple[skey].split("=");
										searchExpectedKey = searchArrayExpected[0].trim();
										searchExpectedValue = searchArrayExpected[1].trim();
										colVerificaiton=Integer.parseInt(skeyMulIndex[skey]); //Index for column verification
										for (int eachCol=colVerificaiton;eachCol<=(noOfRowsReturned-1);eachCol++){ // This loop to start from needed Index to rest
											if (eachCol==colVerificaiton){
												String applCellValue = eachCellData.get(eachCol).getText().trim();
												if (searchExpectedKey.toLowerCase().contains("name")){ // This is only for Name search, as First & last name are in only one column
													String[] appValueSplit = applCellValue.split(" ");
													if (NameSplit[1].equalsIgnoreCase("first")){
														applCellValue = appValueSplit[0];
													}else{
														applCellValue = appValueSplit[1];
													}
												}
												System.out.println("application value :"+applCellValue);
												if (applCellValue.contains(searchExpectedValue.trim())){
													searchPass = "YES";
												}
												else{
													searchFail = "YES";
													err1="Search Failed for value "+searchExpectedValue;
													break;
												}//End If or seachPass

											}//end If of Individual Column verification 

										}//end For loop of Individual Column verification 
									} //Closing For loop of each rows
									rowcnt=rowcnt+1;

								}else{
									searchFail = "YES";
									err1="NO Results found for Search = " + searchExpectedValue;
								}
								if (searchFail!=null){
								}
							}//System.out.println("CLOSING VALUE SEARCH");
							headerTag=true;
						}
					}
					if (searchFail!=null){ //Break if Search Fails...
						break;
					}else{
						if(pageFlag){
							
							By objByPageNextLink = new WebElementProcessor(driver).isBy(driver, objArray[2]); // Get Next page link
							if (objByPageNextLink!=null){
								driver.findElement(objByPageNextLink).click(); // Click if link present
							}else{
								System.out.println(" LINK TO NEXT PAGE - not present please check..... For Keyword - "+keyword+",  Action - "+ "search");
								//logging
								logger.info(" LINK TO NEXT PAGE - not present please check..... For Keyword - "+keyword+",  Action - "+ "search");
								//return err1;
							}
							
						}
					}
					if (searchPass==null && searchFail == null){ //when both are Null
						err1="NO Results found for  Search = " + searchExpectedValue;
					}else{
						logMessage = "At test Step - Keyword "+keyword+",  Action - "+ "search"+"  - . Locator : " + objByTable.toString();
						System.out.println(logMessage); // Print on Console
						//logging
						logger.info(logMessage);
						
						//Log.info(logMessage); //Print on Log
						//err1 = null;
					}
				}else{ //if any errors in getting Page Numbers
					err1="******** Error occoured in test step *********: "+keyword+",  Action - "+ "search"+" - not present please check..... Locator : " + objByTable.toString();
					return err1;
				}
			} // Ending Page Number loop

		} catch(Exception e) {
			System.out.println(e);
			err1="******** Error occoured in test step *********: "+keyword+",  Action - "+ "search"+" - not present please check..... Locator : " ;
			// System.out.println(err1); // Print on Console
			//Log.error(err1); //Print on Log
		}

		return err1; //Return Error Message
	}

	/**
	 * Table - Keyword & myCaseSearch - Action
	 * @method myCaseSearch
	 * @description - myCaseSearch action to Search for First instance of Search Value and Click on that Row
	 * 
	 * @throws Exception
	 */			
	public String myCaseSearch(String objectsString,String searchValues) throws Exception {
		String tableObject = null;
		try{
			err1="No";
			String searchPass = null;
			String searchFail = null;
			String eachRowData = null;
			String[] searchArrayExpected = searchValues.split(","); //Get array of Searched Values
			String[] pageNoArray = null;
			String[] objArray = null;
			By objByPageNo=null;
			if(objectsString.contains(",")){
				objArray = objectsString.split(","); // Array of Objects Properties, Table, Page No and Next Page Link
				objByPageNo = new WebElementProcessor(driver).isBy(driver, objArray[1]);
				tableObject=objArray[0];
			}else{
				tableObject=objectsString;
			}
			int pageNos;
			String pageNo;
			boolean pageFlag=false;
			if (objByPageNo!=null){
				pageNo = driver.findElement(objByPageNo).getText(); // Get Next Page No
				pageNoArray = pageNo.split(" "); //Get page No
				pageNos=Integer.parseInt(pageNoArray[3]);
				pageFlag=true;
			}else{
				//err1 = " Unable to Extract PAGE Numbers - not present please check..... For Keyword  - "+keyword+", Action - "+Global.action ;
				pageNos=0;
				pageNo="Page Number : 0";
				//return err1;
			}
			for (int pageLoop=0;pageLoop<=pageNos;pageLoop++) { // Iterating all Pages for Search
				System.out.println(pageNo); //Display Page No in Console 
				By objByTable = new WebElementProcessor(driver).isBy(driver, tableObject);
				if (objByTable!=null){
					WebElement table = driver.findElement(objByTable); 
					List<WebElement> allRows = table.findElements(By.tagName("tr")); //Get All Rows
					// Now get all the TR elements from the table 
					// And iterate over them, getting the cells 
					for (WebElement eachRow : allRows) { //Iterate all rows
						eachRowData = eachRow.getText().trim();
						searchPass = null;
						searchFail = null;
						//Iterate row with all columns
						for (int r1=0;r1<=(searchArrayExpected.length-1);r1++){
							if (eachRowData.contains(searchArrayExpected[r1].trim())){ //Check for If column is matching
								searchPass = "YES";
							}
							else{
								searchFail = "YES";
								break;
							}
						}
						if (searchFail==null){
							List<WebElement> colElements = eachRow.findElements(By.tagName("td"));
							for(WebElement colElement: colElements){
								if (colElement.getText().equalsIgnoreCase(searchArrayExpected[0])){
									if(colElement.findElements(By.tagName("a")).size() > 0){
										colElement.findElement(By.linkText(searchArrayExpected[0])).click();
									} else {
										colElement.click();
									}
									break;
								}
							}
							//eachRow.findElements(By.tagName("td")).get(1).click();
							break;
						}else {
							System.out.println(searchPass);
						}
					}
					if (searchFail==null){
						break;
					}else{
						if(pageFlag){
							By objByPageNextLink = new WebElementProcessor(driver).isBy(driver, objArray[2]); // Check for Page Nos
							if (objByPageNextLink!=null){
								driver.findElement(objByPageNextLink).click(); //Get the link if Page link if Pages exists
							}else{
								err1 = " LINK TO NEXT PAGE - not present please check..... For Keyword - "+keyword+", Action - "+ "myCaseSearch" ;
								return err1;
							}
						}
					}
					logMessage = "At test Step - Keyword = "+keyword+", Action - "+  "myCaseSearch" +" - . Locator : " + objByTable.toString(); // This is to Display on console as well in Log File
					System.out.println(logMessage); // Print on Console
					//logging
					logger.info(logMessage);
					//Log.info(logMessage); //Print on Log 
					//err1 = null;
				}//Ending Table IF Condition 
			} //Ending For loop Page No

			if(searchFail != null){
				err1 = "Error : No Matches found in the Table;  Keyword - "+keyword+", Action - "+ "myCaseSearch"+" - . Expected : " + objectsString.toString();
			}
		} catch(Exception e) {
			err1="******** Error occoured in test step *********: "+keyword+", Action - "+ "myCaseSearch" +" - not present please check..... Locator : " + tableObject; //Preparing Error message when unable to perform action
			System.out.println(err1); // Print on Console
			//logging
			logger.error(err1);
			//Log.error(err1); //Print on Log
		}
		return err1; //Return Values
	}

	/**
	 * Table - Keyword & searchcolumndata - Action
	 * @method searchcolumndata
	 * @description - searchcolumndata action to Search for particuar data in the column of each row
	 * 
	 * @throws Exception
	 */			
	public String searchcolumndata(String objectsString,String searchValues) throws Exception {
		try{
			err1="No";
			String searchPass = null;
			String searchFail = null;
			String eachColData = null;
			String[] pageNoArray = null;
			String[] objArray = null;
			String tableObject = null;
			By objByPageNo=null;
			ArrayList<String> printconsole = new ArrayList<>();
			try{
				if(objectsString==null||searchValues==null){
					String error = "please check the object property and searchvales , it should not be null : Table.java -> "
				+Thread.currentThread().getStackTrace()[1].getLineNumber();
					throw new Exception(error);
				}
			}catch (Exception e) {
				throw new Exception(e);
			}
			String[] searchArrayExpected = searchValues.split(","); //Get array of Searched Values
			
			if(objectsString.contains(",")){
				objArray = objectsString.split(","); // Array of Objects Properties, Table, Page No and Next Page Link
				objByPageNo = new WebElementProcessor(driver).isBy(driver, objArray[1]);
				tableObject=objArray[0];
			}else{
				tableObject=objectsString;
			}
			int pageNos;
			String pageNo;
			boolean pageFlag=false;
			if (objByPageNo!=null){
				pageNo = driver.findElement(objByPageNo).getText(); // Get Next Page No
				pageNoArray = pageNo.split(" "); //Get page No
				pageNos=Integer.parseInt(pageNoArray[3]);
				pageFlag=true;
			}else{
				//err1 = " Unable to Extract PAGE Numbers - not present please check..... For Keyword  - "+keyword+", Action - "+Global.action ;
				pageNos=0;
				pageNo="Page Number : 0";
				//return err1;
			}
			for (int pageLoop=0;pageLoop<=pageNos;pageLoop++) { // Iterating all Pages for Search
				System.out.println(pageNo); //Display Page No in Console 
				By objByTable = new WebElementProcessor(driver).isBy(driver, tableObject);
				int linenumber = Thread.currentThread().getStackTrace()[1].getLineNumber();
				if (objByTable!=null){
					WebElement table = null;
				
					try{
						table =driver.findElement(objByTable); 
						err1 = "please check the object property , Table.java -> "
								+Thread.currentThread().getStackTrace()[1].getLineNumber();
						throw new Exception(err1);
					}catch(Exception e){
						System.out.println(e);
					}
					
					List<WebElement> allRows = null;
					try{
						allRows = table.findElements(By.tagName("tr")); //Get All Rows
						err1 = "please check the object property" + objByTable+" tr , Table.java -> "
								+Thread.currentThread().getStackTrace()[1].getLineNumber();
						throw new Exception(err1);
					}
					catch(Exception e){
						System.out.println(e);
					}
					// Now get all the TR elements from the table 
					// And iterate over them, getting the cells 
					for (WebElement eachRow : allRows) { //Iterate all rows
						List<WebElement> allcolumn = null;
						try{
						 allcolumn = eachRow.findElements(By.tagName("td"));
						 err1 = "please check the object property" + objByTable+" tr td, Table.java -> "
									+Thread.currentThread().getStackTrace()[1].getLineNumber();
							throw new Exception(err1);
						}
						catch(Exception e){
							System.out.println(e);
						}
						
						for(WebElement eachcolumn : allcolumn){
						eachColData = eachcolumn.getText().trim();
						printconsole.add(eachColData);
						searchPass = null;
						searchFail = null;
						//Iterate row with all columns
						for (int r1=0;r1<=(searchArrayExpected.length-1);r1++){
							if (eachColData.contains(searchArrayExpected[r1].trim())){ //Check for If column is matching
								searchPass = "YES";
							}
							
						}
						
					}
					}
					if (searchPass.equalsIgnoreCase("YES")){
						break;
					}else{
						if(pageFlag){
							By objByPageNextLink = new WebElementProcessor(driver).isBy(driver, objArray[2]); // Check for Page Nos
							if (objByPageNextLink!=null){
								driver.findElement(objByPageNextLink).click(); //Get the link if Page link if Pages exists
							}else{
								err1 = " LINK TO NEXT PAGE - not present please check..... For Keyword - "+keyword+", Action - "+ "searchcolumndata" ;
								return err1;
							}
						}
					}
					logMessage = "At test Step - Keyword = "+keyword+", Action - "+  "searchcolumndata" +" - . Locator : " + objByTable.toString(); // This is to Display on console as well in Log File
					System.out.println(logMessage); // Print on Console
					//logging
					logger.info(logMessage);
					//Log.info(logMessage); //Print on Log 
					//err1 = null;
				}else{
					err1="******** Error occoured in test step *********: "+keyword+", Action - "+ "searchcolumndata" +" object property- not present please check..... Locator : " + objByTable.toString()+
							"error in line number :"+linenumber; //Preparing Error message when unable to perform action
					System.out.println(err1); // Print on Console
					//logging
					logger.info(err1);
					//Log.error(err1); //Print on Log
					return err1;
				} //Ending Table IF Condition 
			} //Ending For loop Page No

			if(searchPass == null){
				err1 = "Error : No Matches found in the Table;  Keyword - "+keyword+", Action - "+ "searchcolumndata"+
						"Expected value = "+searchValues +" Value from application is = "+printconsole.toString();
			}
		} catch(Exception e) {
			err1="******** Error occoured in test step *********: "+keyword+", Action - "+ "searchcolumndata" +" " +
						e ; //Preparing Error message when unable to perform action
			System.out.println(err1); // Print on Console
			//logging
			logger.error(err1);
			//Log.error(err1); //Print on Log
		}
		return err1; //Return Values
	}

	/**
	 * Table - Keyword & getTableData - Action
	 * @method getTableData
	 * @description - getTableData action to retrieve data from Table based on search value.
	 * 
	 * @throws Exception
	 */			
	public String getTableData(String objectsString,String searchValues) throws Exception {
		err1 = "No";
		HashMap<String , String> UserRole;
		UserRole= new HashMap<String, String>();
		String searchPass = null;
		String searchFail = null;
		String eachRowData = null;
		String tableObject = null;
		By objByPageNo=null;
		String[] searchArrayExpMultple = searchValues.split(","); //Get Array of all search values
		//String[] searchArrayExpected = SearchValues.split("=");
		String[] pageNoArray = null;
		String[] objArray = objectsString.split(",");
		if(objectsString.contains(",")){
			objArray = objectsString.split(","); // Array of Objects Properties, Table, Page No and Next Page Link
			objByPageNo = new WebElementProcessor(driver).isBy(driver, objArray[1]);
			tableObject=objArray[0];
		}else{
			tableObject=objectsString;
		}
		String searchExpectedKey = searchArrayExpMultple[0].trim(); // Get Search Key
		//String SearchExpectedValue = searchArrayExpected[1].trim();
		String pageNo = null;
		int pageLoopNo = 0;
		boolean nameFlag=false; //Flag for Names Column as we have First Name and Last name here
		int colVerificaiton = 0;
		String[] nameSplit = null; //Spliting First and Last Name
		int rowcnt;
		boolean headerTag=false; //This is for Getting Column name Indexes in Table
		searchPass = null;
		searchFail = null;
		String skeyIndex = null;
		String[] skeyMulIndex = null; // Storing multiple column search indexes
		int pageNos;
		boolean pageFlag=false;
		if (objByPageNo!=null){
			pageNo = driver.findElement(objByPageNo).getText(); // Get Next Page No
			pageNoArray = pageNo.split(" "); //Get page No
			pageNos=Integer.parseInt(pageNoArray[3]);
			pageFlag=true;
		}else{
			//err1 = " Unable to Extract PAGE Numbers - not present please check..... For Keyword  - "+keyword+", Action - "+Global.action ;
			pageNos=0;
			pageNo="0";
			//return err1;
		}
		System.out.println("Pageloop:"+ pageLoopNo); //Disply Page no in Console
		boolean firstLoop = true;
		for (int pageLoop=0;pageLoop<=pageLoopNo;pageLoop++) {	//Iterate all pages
			By objByTable = new WebElementProcessor(driver).isBy(driver, objArray[0]); //Get Table Object
			if (objByTable!=null){
				WebElement table = driver.findElement(objByTable); //get Table
				List<WebElement> allRows = table.findElements(By.tagName("tr"));  //Get All the rows
				// Now get all the TR elements from the table 
				// And iterate over them, getting the cells
				for (WebElement eachRow : allRows) { 
					try{ //this block to catch the exception and continue 
						//System.out.println("Eachrow declaration"+eachRow.findElement(By.tagName("th")));
					}
					catch(Exception e){
						if(firstLoop == true){
							firstLoop=false;
							continue;
						}

					}
					rowcnt=1;
					eachRowData = eachRow.getText().trim(); //Get Each Rows
					if (searchArrayExpMultple.length<=1){
						if(headerTag==false){ //This is ontime to get Column indexes
							headerTag=true; 
							List<WebElement> eachCellHeader = eachRow.findElements(By.tagName("th")); // Get each cells
							System.out.println("column headers size:"+ eachCellHeader.size());
							//logging
							logger.info("column headers size:"+ eachCellHeader.size());
							for (int colheader=0;colheader<=(eachCellHeader.size()-1);colheader++){
								String cellHeaderName = eachCellHeader.get(colheader).getText().trim();

								if (cellHeaderName.equals(searchExpectedKey)){
									colVerificaiton=colheader; //Store Index of Matched Column
								}
							}
						}

						if(headerTag==true){ //This condition to get into Table Data
							List<WebElement> eachCellData = eachRow.findElements(By.tagName("td")); //Get Cell data
							int noOfRowsReturned = eachCellData.size();
							if (noOfRowsReturned!=0){ //Iterate all rows
								for (int eachCol=colVerificaiton;eachCol<=(noOfRowsReturned-1);eachCol++){ //Limit iteration based on Column idex
									if (eachCol==colVerificaiton){
										String applCellValue = eachCellData.get(eachCol).getText();
										if (nameFlag==true){   //This is only for First & Last Name Column
											String[] appValueSplit = applCellValue.split(" ");
											if (nameSplit[1].equalsIgnoreCase("first")){
												applCellValue = appValueSplit[0];
											}else{
												applCellValue = appValueSplit[1];
											}
										}
									}
								}
							}
							rowcnt=rowcnt+1;
						}else{
							searchFail = "YES";
							err1="NO Results found for Search = " + searchExpectedKey;
						} //Close If condition of Table
						if (searchFail!=null){
							break;
						}

					}else{ //else condition for Multiple Search

						if(headerTag==false){
							System.out.println("Inside multiple search");
							for (int mulSearch =0; mulSearch <= (searchArrayExpMultple.length-1);mulSearch++){
								searchExpectedKey = searchArrayExpMultple[mulSearch].trim(); // Columns for Multiple searches
								List<WebElement> eachCellHeader = eachRow.findElements(By.tagName("th")); //Get colums
								for (int colheader=0;colheader<=(eachCellHeader.size()-1);colheader++){ //Iterate cells to get Columns
									String cellHeaderName = eachCellHeader.get(colheader).getText().trim();
									if (cellHeaderName.equalsIgnoreCase(searchExpectedKey)){ 
										if (skeyIndex==null){
											skeyIndex= Integer.toString(colheader);
										}else{
											skeyIndex=skeyIndex+","+Integer.toString(colheader);;
										}
									}
								}//Close For loop of multiple search
							}//Close Column Index If condition
							//headerTag = true;
						}
						if (headerTag==true){ //Condition for Data after getting Index of Columns
							searchPass = null;	
							searchFail = null;
							skeyMulIndex = skeyIndex.split(",");
							List<WebElement> eachCellData = eachRow.findElements(By.tagName("td"));
							int noOfRowsReturned = eachCellData.size();
							if (noOfRowsReturned!=0){ //Get Key and Values
								String userKey = eachCellData.get(Integer.parseInt(skeyMulIndex[0])).getText().trim();
								String userValue = eachCellData.get(Integer.parseInt(skeyMulIndex[1])).getText().trim();
								UserRole.put(userKey,userValue);
								rowcnt=rowcnt+1;
							}
							else{
								searchFail = "YES";
								err1="NO Results found for Search = " + searchArrayExpMultple;
							}
							if (searchFail!=null){
							}
							//}//closing multiple column search
						}//Closing Search Values
						headerTag=true;
					}
				}
				if (searchFail==null){
					break;
				}else{
					if(pageFlag){
						System.out.println("coming inside");
						By objByPageNextLink = new WebElementProcessor(driver).isBy(driver, objArray[2]); // Check for Page Nos
						if (objByPageNextLink!=null){
							driver.findElement(objByPageNextLink).click(); //Get the link if Page link if Pages exists
						}else{
							err1 = " LINK TO NEXT PAGE - not present please check..... For Keyword - "+keyword+", Action - "+ "myCaseSearch" ;
							return err1;
						}
					}
				}
				
				logMessage = "At test Step - Keyword = "+keyword+", Action - "+  "myCaseSearch" +" - . Locator : " + objByTable.toString(); // This is to Display on console as well in Log File
				System.out.println(logMessage); // Print on Console
				//logging
				logger.info(logMessage);
				//Log.info(logMessage); //Print on Log 
				err1 = null;}
			else{
				err1="******** Error occoured in test step *********: "+keyword+", Action - "+ "myCaseSearch" +" - not present please check..... Locator : " + objByTable.toString(); //Preparing Error message when unable to perform action
				System.out.println(err1); // Print on Console
				//logging
				logger.info(err1);
				//Log.error(err1); //Print on Log
				return err1;
			} //Ending Table IF Condition 
		} //Ending For loop Page No

	
		new Common().writeSheet(UserRole, 103, "klarnaLanguageExtractTemplate.xlsx"); //Writing Sheet
		return err1;
	}


	/**
	 * Table - Keyword & getConfigInfo - Action
	 * @method getConfigInfo
	 * @description - getConfigInfo action to retrieve data from Table based on search value.
	 * 
	 * @throws Exception
	 */				
	public String getConfigInfo(String objectsString) throws Exception{
		err1="No";
		List<String> configInfo = new ArrayList<String>();
		int offset = 126;
		List<WebElement> allRows = new ArrayList<WebElement>();
		List<WebElement> allRowData = new ArrayList<WebElement>();
		String[] objArray = objectsString.split(",");
		By objByPageNo = new WebElementProcessor(driver).isBy(driver, objArray[1]); //Get page Object
		By objByTable = new WebElementProcessor(driver).isBy(driver, objArray[0]); //Get Table
		if (objByTable!=null){
			WebElement table = driver.findElement(objByTable); //Get Table data
			allRows = table.findElements(By.tagName("tr")); //Get all rows
		}
		//Iterate all rows & get data
		for(int i=0;i<allRows.size();i++){
			try{
				String data=null;
				if(i==0)
					allRowData = allRows.get(i).findElements(By.tagName("td")); //get rows of Table column name
				else
				{
					WebElement table = driver.findElement(objByTable); 
					allRows = table.findElements(By.tagName("tr")); 
					allRowData = allRows.get(i).findElements(By.tagName("td")); // get row data values
				}
				for(WebElement rowData : allRowData){ //Iterate all rows
					if(rowData.getText().equalsIgnoreCase("Edit")){
						configInfo = new ArrayList<String>(); //Store all config information
						rowData.findElement(By.tagName("a")).click();
						Thread.sleep(2000);
						for(int j=1;j<objArray.length;j++){
							By elementLocator = new WebElementProcessor(driver).isBy(driver, objArray[j].toString());
							data = driver.findElement(elementLocator).getAttribute("value");
							configInfo.add(data); // Adding data to Array
						}
						driver.findElement(By.linkText("List User Action")).click();
						Thread.sleep(5000);
						new Common().writeListToSheet(configInfo, offset++, "klarnaLanguageExtractTemplate (1).xlsx"); //Write Data to excel sheet
					}
				}//Closing For loop of iterating all rows
			}
			catch(Exception e){
				err1="******** Error occoured in test step *********: "+keyword+", Action = "+ "getConfigInfo"; // This is to Display on console as well in Log File
				e.printStackTrace();
				//logging
				logger.error(err1);
				continue;
			}
		}
		return err1;	
	}


	/**
	 * Table - Keyword & linkclick - Action
	 * @method linkclick
	 * @description - linkclick action to Search for link and click to perform action
	 * 
	 * @throws Exception
	 */			
	public String validatefield(String objectsString,String searchValues) throws Exception {
		try{
			err1="No";
			String searchPass = null;
			String searchFail = null;
			String eachRowData = null;
			By objByPageNo=null;
			try{
				if(objectsString==null||searchValues==null){
					String error = "please check the object property and searchvales , it should not be null : Table.java -> "
				+Thread.currentThread().getStackTrace()[1].getLineNumber();
					throw new Exception(error);
				}
			}catch (Exception e) {
				throw new Exception(e);
			}
			String[] searchArrayExpected = searchValues.split(","); //Get array of Searched Values
			String[] pageNoArray = null;
			String tableObject = null;
			String[] objArray = null;
			String[] Searchvalue =null;
			if(objectsString.contains(",")){
				objArray = objectsString.split(","); // Array of Objects Properties, Table, Page No and Next Page Link
				objByPageNo = new WebElementProcessor(driver).isBy(driver, objArray[1]);
				tableObject=objArray[0];
			}else{
				tableObject=objectsString;
			}
			int pageNos;
			String pageNo;
			boolean pageFlag=false;
			if (objByPageNo!=null){
				pageNo = driver.findElement(objByPageNo).getText(); // Get Next Page No
				pageNoArray = pageNo.split(" "); //Get page No
				pageNos=Integer.parseInt(pageNoArray[3]);
				pageFlag=true;
			}else{
				pageNos=0;
				pageNo="Page Number : 0";
			}
			for(int pageLoop=0;pageLoop<=pageNos;pageLoop++){ // Iterating all Pages for Search
				System.out.println(pageNo); //Display Page No in Console 
				//logging
				logger.info(pageNo);
				By objByTable = new WebElementProcessor(driver).isBy(driver, tableObject);
				if (objByTable!=null){
					List<WebElement> allRows =null;
					try{
					WebElement table = driver.findElement(objByTable); 
					allRows = table.findElements(By.tagName("tr")); //Get All Rows
					}
					catch(Exception e){
						String error = "tr tag is not present in the table /"+objByTable.toString() + " is not found"
								+ "Table.java "+ Thread.currentThread().getStackTrace()[1].getLineNumber();
						throw new Exception(error);
					}
					if(searchValues.contains("checkbox")||searchValues.contains("radio")){
					for (WebElement eachRow : allRows) { //Iterate all rows
						boolean checkbox =false;
						try{
						WebElement checkboxattribute = eachRow.findElement(By.tagName("input"));
						checkbox = checkboxattribute.isSelected();
					//	System.out.println("checkbox "+checkbox);
						}
						catch(Exception e){
							String error = "input tagname is not present in the table / check attribute is not present in the table" + "Table.java "+ Thread.currentThread().getStackTrace()[1].getLineNumber();
							throw new Exception(error);
						}
						searchPass = null;
						searchFail = null;
						if(checkbox){
							searchPass = "YES";
						}else{
							searchFail = "YES";
							throw new Exception("searchFail "+searchFail+" " + "Table.java line->"+Thread.currentThread().getStackTrace()[1].getLineNumber());
						}
						
					}
					}
					
				}
			}
						} catch(Exception e) {
							System.out.println(e);
			err1="******** Error occoured in test step *********: "+keyword+", Action - "+ "validatefield" ; //Preparing Error message when unable to perform action
			System.out.println(err1); // Print on Console
			//logging
			logger.error(err1);
			//Log.error(err1); //Print on Log
		}
		return err1; //Return Values
	}

	/**
	 * Table - Keyword & buttonClick - Action
	 * @method buttonClick
	 * @description - buttonClick action to Search for Button and click to perform action
	 * 
	 * @throws Exception
	 */			
	public String buttonClick(String objectsString,String searchValues) throws Exception {
		try{
			err1="No";
			String searchPass = null;
			String searchFail = null;
			String eachRowData = null;
			By objByPageNo=null;
			String[] searchArrayExpected = searchValues.split(","); //Get array of Searched Values
			String[] pageNoArray = null;
			String tableObject = null;
			String[] objArray = null;
			if(objectsString.contains(",")){
				objArray = objectsString.split(","); // Array of Objects Properties, Table, Page No and Next Page Link
				objByPageNo = new WebElementProcessor(driver).isBy(driver, objArray[1]);
				tableObject=objArray[0];
			}else{
				tableObject=objectsString;
			}
			int pageNos;
			String pageNo;
			boolean pageFlag=false;
			if (objByPageNo!=null){
				pageNo = driver.findElement(objByPageNo).getText(); // Get Next Page No
				pageNoArray = pageNo.split(" "); //Get page No
				pageNos=Integer.parseInt(pageNoArray[3]);
				pageFlag=true;
			}else{
				//err1 = " Unable to Extract PAGE Numbers - not present please check..... For Keyword  - "+keyword+", Action - "+Global.action ;
				pageNos=0;
				pageNo="Page Number : 0";
				//return err1;
			}
			for (int pageLoop=0;pageLoop<=pageNos;pageLoop++) { // Iterating all Pages for Search
				System.out.println(pageNo); //Display Page No in Console 
				By objByTable = new WebElementProcessor(driver).isBy(driver, tableObject);
				if (objByTable!=null){
					WebElement table = driver.findElement(objByTable); 
					List<WebElement> allRows = table.findElements(By.tagName("tr")); //Get All Rows
					List<WebElement> allRowData = new ArrayList<WebElement>();
					// Now get all the TR elements from the table 
					// And iterate over them, getting the cells 
					int rowNos =0;
					for (WebElement eachRow : allRows) { //Iterate all rows
						eachRowData = eachRow.getText().trim();
						searchPass = null;
						searchFail = null;
						//Iterate row with all columns
						for (int r1=0;r1<=(searchArrayExpected.length-1);r1++){
							if (eachRowData.contains(searchArrayExpected[0].trim())){ //Check for If column is matching
								searchPass = "YES";
							}
							else{
								searchFail = "YES";
								//break;
							}
						}
						if (searchFail==null){
							//eachRow.click();
							allRowData = allRows.get(rowNos).findElements(By.tagName("td")); // get row data values
							for(WebElement rowData : allRowData){ //Iterate all rows
								if(rowData.getText().equalsIgnoreCase(searchArrayExpected[1].trim())){
									rowData.findElement(By.tagName("button")).click();
								}
							}//Closing For loop of iterating all rows


							break;
						}
						rowNos=rowNos+1;
					}
					if (searchFail==null){
						break;
					}else{
						if (pageFlag){
							By objByPageNextLink = new WebElementProcessor(driver).isBy(driver, objArray[2]); // Check for Page Nos
							if (objByPageNextLink!=null){
								driver.findElement(objByPageNextLink).click(); //Get the link if Page link if Pages exists
							}else{
								err1 = " LINK TO NEXT PAGE - not present please check..... For Keyword - "+keyword+", Action - "+"buttonClick";
								return err1;
							}
						}
					}
					logMessage = "At test Step - Keyword = "+keyword+", Action - "+ "buttonClick" +" - . Locator : " + objByTable.toString(); // This is to Display on console as well in Log File
					System.out.println(logMessage); // Print on Console
					//logging
					logger.info(logMessage);
					//Log.info(logMessage); //Print on Log 
					//err1 = null;
				}else{
					err1="******** Error occoured in test step *********: "+keyword+", Action - "+ "buttonClick" +" - not present please check..... Locator : " + objByTable.toString(); //Preparing Error message when unable to perform action
					System.out.println(err1); // Print on Console
					//logging
					logger.info(err1);
					//Log.error(err1); //Print on Log
					return err1;
				} //Ending Table IF Condition 
			} //Ending For loop Page No

		} catch(Exception e) {
			err1="******** Error occoured in test step *********: "+keyword+", Action - "+ "buttonClick" +" - not present please check..... Locator : " ; //Preparing Error message when unable to perform action
			System.out.println(err1); // Print on Console
			//logging
			logger.error(err1);
			//Log.error(err1); //Print on Log
		}
		return err1; //Return Values
	}

	
	
	public String validaterowdatawithcolumnname(String objectsString,String searchValues) throws Exception {
	try{
		err1="No";
		String searchPass = null;
		String searchFail = null;
		String eachHeaderData = null;
		String eachRowData = null;
		String[] searchArrayExpected = searchValues.split(","); //Get array of Searched Values
		String[] pageNoArray = null;
		String[] objArray = null;
		String tableObject = null;
		int columntosearch =0;
		By objByPageNo=null;
		System.out.println("objectsString "+ objectsString);
		if(objectsString.contains(",")){
			objArray = objectsString.split(","); // Array of Objects Properties, Table, Page No and Next Page Link
			objByPageNo = new WebElementProcessor(driver).isBy(driver, objArray[1]);
			System.out.println("objByPageNo "+objByPageNo);
			tableObject=objArray[0];
		}else{
			tableObject=objectsString;
		}
		int pageNos;
		String pageNo;
		boolean pageFlag=false;
		if (objByPageNo!=null){
			pageNo = driver.findElement(objByPageNo).getText(); // Get Next Page No
			pageNoArray = pageNo.split(" "); //Get page No
			pageNos=Integer.parseInt(pageNoArray[3]);
			System.out.println("pageNos "+pageNos);
			pageFlag=true;
		}else{
			//err1 = " Unable to Extract PAGE Numbers - not present please check..... For Keyword  - "+keyword+", Action - "+Global.action ;
			pageNos=0;
			pageNo="Page Number : 0";
			//return err1;
		}
		for (int pageLoop=1;pageLoop<=pageNos;pageLoop++) { // Iterating all Pages for Search
			pageNo = driver.findElement(objByPageNo).getText(); // Get Next Page No
			pageNoArray = pageNo.split(" "); //Get page No
			pageNos=Integer.parseInt(pageNoArray[3]);
			System.out.println(pageNos); 
			logger.info(pageNo);
			By objByTable = new WebElementProcessor(driver).isBy(driver, tableObject);
			if (objByTable!=null){
				List<WebElement> allHdr = driver.findElements(By.cssSelector(objectsString+">thead>tr th")); //Get All Rows
				// Now get all the TR elements from the table 
				// And iterate over them, getting the cells 
				for (int hdr=0;hdr<allHdr.size();hdr++){ //Iterate all header
					eachHeaderData = allHdr.get(hdr).getText().trim();
					for (int hdrsearchvalue=0;hdrsearchvalue<=(searchArrayExpected.length-1);hdrsearchvalue++){
						if(eachHeaderData.contains(searchArrayExpected[hdrsearchvalue].trim())){
						columntosearch = hdr;
					}
					}
					}
				List<WebElement> allrow = driver.findElements(By.cssSelector(objectsString+">tbody>tr>td:nth-child("+columntosearch+")"));
					//Iterate row with all columns
					for (WebElement rowvalue : allrow) {
						eachRowData = rowvalue.getText().trim();
							if (eachRowData.trim().toUpperCase().contains(searchArrayExpected[1].trim().toUpperCase())){ //Check for If column is matching
								searchPass = "YES";
							}
							else{
								searchFail = "YES";
								err1="******** Error occoured in test step *********: "+keyword+", Action - "+ "validaterowdatawithcolumnname" +" - not present please check..... Locator : " ; //Preparing Error message when unable to perform action
								System.out.println("Assertion errror in table.java action --> validaterowdatawithcolumnname --> error in line number "+Thread.currentThread().getStackTrace()[1].getLineNumber());
								Assert.assertFalse(true,eachRowData+" is not equal to "+searchArrayExpected[1]);
								break;
							}
						
					}
					
					}
			else{
				String errormsg = "Table object property is not identified -returned as null,please enter correct table object property";
				throw new Exception(errormsg);
			}
					
			System.out.println(pageFlag);
			if(pageFlag){
				try{
						By objByPageNextLink = new WebElementProcessor(driver).isBy(driver, objArray[1]); // Check for Page Nos
						if (objByPageNextLink!=null){
							driver.findElement(By.cssSelector(".pagination-btn:nth-of-type(2)")).click(); //Get the link if Page link if Pages exists
							Thread.sleep(2000);
							
						}else{
							err1 = " LINK TO NEXT PAGE - not present please check..... For Keyword - "+keyword+", Action - "+ "myCaseSearch" ;
							throw new Exception(err1);
						}
				}
				catch(ElementNotFoundException e){
					System.out.println("element not found exception");
					logger.info("element not found exception");
				}
				catch (Exception e) {
					System.out.println(e);
				}
				
					
				
				
			}
			
		} //Ending For loop Page No
	

	} catch(Exception e) {
		err1="******** Error occoured in test step *********: "+keyword+", Action - "+ "validaterowdatawithcolumnname" +" - not present please check..... Locator : " ; //Preparing Error message when unable to perform action
		System.out.println(err1); // Print on Console
		logger.error(err1);
		
		
		throw new Exception(err1);
		
		//logging
		
		//Log.error(err1); //Print on Log
	}
	return err1; //Return Values

	}
	
	
	public String validatecolumnheader(String objectsString,String searchValues) throws Exception {
		try{
			err1="No";
			String searchPass = null;
			String searchFail = null;
			String eachHeaderData = null;
			String eachRowData = null;
			String[] searchArrayExpected = searchValues.split(","); //Get array of Searched Values
			String[] pageNoArray = null;
			String[] objArray = null;
			String tableObject = null;
			int columntosearch =0;
			By objByPageNo=null;
			if(objectsString.contains(",")){
				objArray = objectsString.split(","); // Array of Objects Properties, Table, Page No and Next Page Link
				objByPageNo = new WebElementProcessor(driver).isBy(driver, objArray[1]);
				System.out.println("objByPageNo "+objByPageNo);
				tableObject=objArray[0];
			}else{
				tableObject=objectsString;
				System.out.println("tableObject "+tableObject );
			}
			int pageNos;
			String pageNo;
			boolean pageFlag=false;
			if (objByPageNo!=null){
				pageNo = driver.findElement(objByPageNo).getText(); // Get Next Page No
				pageNoArray = pageNo.split(" "); //Get page No
				pageNos=Integer.parseInt(pageNoArray[3]);
				System.out.println("pageNos "+pageNos);
				pageFlag=true;
			}else{
				//err1 = " Unable to Extract PAGE Numbers - not present please check..... For Keyword  - "+keyword+", Action - "+Global.action ;
				pageNos=1;
				pageNo="Page Number : 1";
				System.out.println("pageNo Page Number : 1");
				//return err1;
			}
			for (int pageLoop=1;pageLoop<=pageNos;pageLoop++) { // Iterating all Pages for Search
				if (objByPageNo!=null){
					try{
				
				pageNo = driver.findElement(objByPageNo).getText(); // Get Next Page No
				pageNoArray = pageNo.split(" "); //Get page No
				pageNos=Integer.parseInt(pageNoArray[3]);
				System.out.println(pageNos); 
				logger.info(pageNo);
			
				}catch(Exception e){
					String err = "null point exception in object property of page number;; code error line number " + Thread.currentThread().getStackTrace()[1].getLineNumber();
					throw new Exception(err);
				}
				}
				By objByTable = new WebElementProcessor(driver).isBy(driver, tableObject);
				if (objByTable!=null){
					List<WebElement> allHdr = null;
					try{
					if(objectsString.contains("/")){
						allHdr = driver.findElements(By.xpath(objectsString+"//th"));
					}else{
						allHdr = driver.findElements(By.cssSelector(objectsString+">thead>tr th"));
					}
					}
					catch(Exception e){
						System.out.println("element not found exception table.java action --> validatecolumnheader --> error in line number "+Thread.currentThread().getStackTrace()[1].getLineNumber());
					}
					for (WebElement header : allHdr) {
						eachHeaderData = header.getText().trim();
						//System.out.println("header is "+eachHeaderData);
						for (int hdrsearchvalue=0;hdrsearchvalue<=(searchArrayExpected.length-1);hdrsearchvalue++){
							//System.out.println("searchArrayExpected" + searchArrayExpected[hdrsearchvalue]);
							if(eachHeaderData.equalsIgnoreCase(searchArrayExpected[hdrsearchvalue].trim())){
								
								searchPass = "YES";
						}
						}
					}
						
					if(searchPass.equalsIgnoreCase("YES")){
						System.out.println("Column Header is matched");
						logger.info("Column Header is matched");
					}
					try{
					if (searchPass!="YES"){ //Check for If column is matching
								searchFail = "YES";
									err1="******** Error occoured in test step *********: "+keyword+", Action - "+ "validatecolumnheader" +" - column header is mismatched " ; //Preparing Error message when unable to perform action
									System.out.println("Assertion errror in table.java action --> validatecolumnheader --> error in line number "+Thread.currentThread().getStackTrace()[1].getLineNumber());
									// if error points here check the above for and if loop where the column string comparison function is written
									
									break;
								}
					}catch(Exception e){
						
					}
							
						
						
						}
				else{
					String errormsg = "Table object property is not identified -returned as null,please enter correct table object property";
					System.out.println(errormsg);
					throw new Exception(errormsg);
				}
						
				if(pageFlag){
					try{
							By objByPageNextLink = new WebElementProcessor(driver).isBy(driver, objArray[1]); // Check for Page Nos
							if (objByPageNextLink!=null){
								driver.findElement(By.cssSelector(".pagination-btn:nth-of-type(2)")).click(); //Get the link if Page link if Pages exists
								Thread.sleep(2000);
								
							}else{
								err1 = " LINK TO NEXT PAGE - not present please check..... For Keyword - "+keyword+", Action - "+ "myCaseSearch" ;
								throw new Exception(err1);
							}
					}
					catch(ElementNotFoundException e){
						System.out.println("element not found exception");
						logger.info("element not found exception");
					}
					catch (Exception e) {
						System.out.println(e);
					}
					
						
					
					
				}
				
			} //Ending For loop Page No
		

		} catch(Exception e) {
			System.out.println(e);
			err1="******** Error occoured in test step *********: keyword "+keyword+", Action - "+ "validatecolumnheader" +" - not present please check..... Locator : " ; //Preparing Error message when unable to perform action
			System.out.println(err1); // Print on Console
			logger.error(err1);
			
			
			throw new Exception(err1);
			
			//logging
			
			//Log.error(err1); //Print on Log
		}
		return err1; //Return Values

		}
	
	
	
	public String linkClick(String objectsString,String searchValues) throws Exception {
		try{
			err1="No";
			String searchPass = null;
			String searchFail = null;
			String eachRowData = null;
			By objByPageNo=null;
			//String[] searchArrayExpected = searchValues.split(","); //Get array of Searched Values
			String[] searchArrayExpected = searchValues.replaceAll("^\"", "").split("\"?(,|$)(?=(([^\"]*\"){2})*[^\"]*$) *\"?");
			String[] pageNoArray = null;
			String tableObject = null;
			String[] objArray = null;
			String[] Searchvalue =null;
			if(objectsString.contains(",")){
				objArray = objectsString.split(","); // Array of Objects Properties, Table, Page No and Next Page Link
				objByPageNo = new WebElementProcessor(driver).isBy(driver, objArray[1]);
				tableObject=objArray[0];
			}else{
				tableObject=objectsString;
			}
			int pageNos;
			String pageNo;
			boolean pageFlag=false;
			if (objByPageNo!=null){
				pageNo = driver.findElement(objByPageNo).getText(); // Get Next Page No
				pageNoArray = pageNo.split(" "); //Get page No
				pageNos=Integer.parseInt(pageNoArray[3]);
				pageFlag=true;
			}else{
				pageNos=0;
				pageNo="Page Number : 0";
			}
			for (int pageLoop=0;pageLoop<=pageNos;pageLoop++) { // Iterating all Pages for Search
				System.out.println(pageNo); //Display Page No in Console 
				//logging
				logger.info(pageNo);
				By objByTable = new WebElementProcessor(driver).isBy(driver, tableObject);
				if (objByTable!=null){
					WebElement table = driver.findElement(objByTable); 
					List<WebElement> allRows = table.findElements(By.tagName("tr")); //Get All Rows
					List<WebElement> allRowData = new ArrayList<WebElement>();
					// Now get all the TR elements from the table 
					// And iterate over them, getting the cells 
					int rowNos =0;
					for (WebElement eachRow : allRows) { //Iterate all rows
						System.out.println(eachRow.getText() + "is eachRow gettext");
						System.out.println(searchArrayExpected[0] + "searchArrayExpected[0]");
						eachRowData = eachRow.getText().trim();
						searchPass = null;
						searchFail = null;
						//Iterate row with all columns
						
							if (eachRowData.contains(searchArrayExpected[0].trim())){ //Check for If column is matching
								searchPass = "YES";
							}
							else{
								searchFail = "YES";
								//continue;
							}
						
							if(searchArrayExpected[1].contains("text=")){
								Searchvalue = searchArrayExpected[1].split("=");
								
								WebElement editbox = allRows.get(rowNos).findElement(By.tagName("td>input"));
								if(editbox!=null){
									editbox.sendKeys(Searchvalue[1]);
								}else{
									String err = " textbox obj property is not identified,code error in Table.java" + Thread.currentThread().getStackTrace()[1].getLineNumber();
									System.out.println(err);
									throw new Exception(err);
								}
							}
							if(searchArrayExpected[1].contains("checkbox")){
								WebElement chkbox = allRows.get(rowNos).findElement(By.tagName("td>input"));
								if(chkbox!=null){
									boolean checkbox = chkbox.isSelected();
									if(!checkbox){
										chkbox.click();
									}else{
										System.out.println("the check box you are trying to select is already selected .."
												+ "code error in Table.java line number : " + Thread.currentThread().getStackTrace()[1].getLineNumber());
									}
								}else{
									String err = " chkbox obj property is not identified,code error in Table.java" + Thread.currentThread().getStackTrace()[1].getLineNumber();
									System.out.println(err);
									throw new Exception(err);
								}
							}
						if (searchFail==null){
							//eachRow.click();
							allRowData = allRows.get(rowNos).findElements(By.tagName("td")); // get row data values
							for(WebElement rowData : allRowData){ //Iterate all rows
								System.out.println(rowData.getText()+" is inside searchfail null");
								System.out.println(searchArrayExpected[1]+" searchArrayExpected[1]");
								if(rowData.getText().equalsIgnoreCase(searchArrayExpected[1].trim())){
									rowData.findElement(By.tagName("a")).click();
									pageFlag=false;
									break;
								}
							}//Closing For loop of iterating all rows


							break;
						}
						rowNos=rowNos+1;
					}
					if (searchFail==null){
						break;
					}else{
						if (pageFlag){
							By objByPageNextLink = new WebElementProcessor(driver).isBy(driver, objArray[2]); // Check for Page Nos
							if (objByPageNextLink!=null){
								driver.findElement(objByPageNextLink).click(); //Get the link if Page link if Pages exists
							}else{
								err1 = " LINK TO NEXT PAGE - not present please check..... For Keyword - "+keyword+", Action - "+ "linkClick" ;
								return err1;
							}
						}
					}
					logMessage = "At test Step - Keyword = "+keyword+", Action - "+ "linkClick" +" - . Locator : " + objByTable.toString(); // This is to Display on console as well in Log File
					System.out.println(logMessage); // Print on Console
					//logging
					logger.info(logMessage);
					//Log.info(logMessage); //Print on Log 
				//	err1 = N;
				}else{
					err1="******** Error occoured in test step *********: "+keyword+", Action - "+ "linkClick" +" - not present please check..... Locator : " + objByTable.toString(); //Preparing Error message when unable to perform action
					System.out.println(err1); // Print on Console
					//Log.error(err1); //Print on Log
					//logging
					logger.info(err1);
					return err1;
				} //Ending Table IF Condition 
			} //Ending For loop Page No

		} catch(Exception e) {
			err1="******** Error occoured in test step *********: "+keyword+", Action - "+ "linkClick" +" - not present please check..... Locator : " ; //Preparing Error message when unable to perform action
			System.out.println(err1); // Print on Console
			//logging
			logger.error(err1);
			//Log.error(err1); //Print on Log
		}
		return err1; //Return Values
	}

	
	
	public String clickonrow(String objectsString,String searchValues) throws Exception {
	try{
		err1="No";
		System.out.println(searchValues);
		String searchPass = null;
		String searchFail = null;
		String eachHeaderData = null;
		String eachRowData = null;
		String[] searchArrayExpected = searchValues.split(","); //Get array of Searched Values
		String[] pageNoArray = null;
		String[] objArray = null;
		String tableObject = null;
		int columntosearch =0;
		By objByPageNo=null;
		try{
			if(objectsString==null||searchValues==null){
				String error = "please check the object property and searchvales , it should not be null : Table.java -> "
			+Thread.currentThread().getStackTrace()[1].getLineNumber();
				throw new Exception(error);
			}
		}catch (Exception e) {
			throw new Exception(e);
		}
		if(objectsString.contains(",")){
			objArray = objectsString.split(","); // Array of Objects Properties, Table, Page No and Next Page Link
			objByPageNo = new WebElementProcessor(driver).isBy(driver, objArray[1]);
			System.out.println("objByPageNo "+objByPageNo);
			tableObject=objArray[0];
		}else{
			tableObject=objectsString;
			
		}
		int pageNos;
		String pageNo;
		boolean pageFlag=false;
		if (objByPageNo!=null){
			pageNo = driver.findElement(objByPageNo).getText(); // Get Next Page No
			pageNoArray = pageNo.split(" "); //Get page No
			pageNos=Integer.parseInt(pageNoArray[3]);
			System.out.println("pageNos "+pageNos);
			pageFlag=true;
		}else{
			//err1 = " Unable to Extract PAGE Numbers - not present please check..... For Keyword  - "+keyword+", Action - "+Global.action ;
			pageNos=1;
			pageNo="Page Number : 1";
			System.out.println("pageNo Page Number : 1");
			//return err1;
		}
		for (int pageLoop=1;pageLoop<=pageNos;pageLoop++) { // Iterating all Pages for Search
			if (objByPageNo!=null){
				try{
			
			pageNo = driver.findElement(objByPageNo).getText(); // Get Next Page No
			pageNoArray = pageNo.split(" "); //Get page No
			pageNos=Integer.parseInt(pageNoArray[3]);
			System.out.println(pageNos); 
			logger.info(pageNo);
		
			}catch(Exception e){
				String err = "null point exception in object property of page number;; code error line number " + Thread.currentThread().getStackTrace()[1].getLineNumber();
				throw new Exception(err);
			}
			}
			By objByTable = null;
			try{
			objByTable = new WebElementProcessor(driver).isBy(driver, tableObject);
			}catch(Exception e){
				String err = "element not found exception--  line number " + Thread.currentThread().getStackTrace()[1].getLineNumber();
				throw new Exception(err);
			}
			if (objByTable!=null){
				if(searchValues.contains(",")){
					if(!searchValues.contains("n")){
					try{
						WebElement chkbox = null;
						try{
						chkbox = driver.findElement(By.cssSelector(objectsString+" tr:nth-child("+searchArrayExpected[0]+")"+" td:nth-child("+searchArrayExpected[1]+")>input"));
						}catch(Exception e){
							//e.printStackTrace();
						}
						System.out.println(objectsString+" tr:nth-child("+searchArrayExpected[0]+")"+" td:nth-child("+searchArrayExpected[1]+")");
						System.out.println(chkbox);
						if(chkbox==null){
							driver.findElement(By.cssSelector(objectsString+" tr:nth-child("+searchArrayExpected[0]+")"+" td:nth-child("+searchArrayExpected[1]+")")).click();
						}else{
						driver.findElement(By.cssSelector(objectsString+" tr:nth-child("+searchArrayExpected[0]+")"+" td:nth-child("+searchArrayExpected[1]+")>input")).click();
						}
					}catch(Exception e){
						String errormsg = "Object "+ objectsString+" tr:nth-child("+searchArrayExpected[0]+")"+" td:nth-child("+searchArrayExpected[1]+")"+"not found exception--  line number " + Thread.currentThread().getStackTrace()[1].getLineNumber();
						System.out.println(errormsg);
						throw new Exception(errormsg);	
					}
					}else{
						driver.findElement(By.cssSelector(objectsString+" tr:last-child"+" td:nth-child("+searchArrayExpected[1]+")")).click();
					}
				}
				else{
						driver.findElement(By.cssSelector(objectsString+" tr:nth-child("+searchArrayExpected[0]+")")).click();
				}
				new Wait(driver).waitForPageLoad();
				}
			else{
				String errormsg = "Table object property is not identified -returned as null,please enter correct table object property" +Thread.currentThread().getStackTrace()[1].getLineNumber();
				System.out.println(errormsg);
				throw new Exception(errormsg);
			}
					
			if(pageFlag){
				try{
						By objByPageNextLink = new WebElementProcessor(driver).isBy(driver, objArray[1]); // Check for Page Nos
						if (objByPageNextLink!=null){
							driver.findElement(By.cssSelector(".pagination-btn:nth-of-type(2)")).click(); //Get the link if Page link if Pages exists
							Thread.sleep(2000);
							
						}else{
							err1 = " LINK TO NEXT PAGE - not present please check..... For Keyword - "+keyword+", Action - "+ "myCaseSearch"+Thread.currentThread().getStackTrace()[1].getLineNumber() ;
							throw new Exception(err1);
						}
				}
				catch(ElementNotFoundException e){
					System.out.println("element not found exception");
					logger.info("element not found exception");
				}
				catch (Exception e) {
					System.out.println(e);
				}
				
					
				
				
			}
			
		} //Ending For loop Page No
	

	} catch(Exception e) {
		System.out.println(e);
		err1="******** Error occoured in test step *********: keyword "+keyword+", Action - "+ "clickonrow" +" - not present please check..... Locator : " ; //Preparing Error message when unable to perform action
		System.out.println(err1); // Print on Console
		logger.error(err1);
		
		
		throw new Exception(err1);
		
		//logging
		
		//Log.error(err1); //Print on Log
	}
	return err1; //Return Values

	
	}




/**
 * Table - Keyword & tabledatanotpresent - Action
 * @method myCaseSearch
 * @description - myCaseSearch action to Search for First instance of Search Value and Click on that Row
 * 
 * @throws Exception
 */			
public String tabledatanotpresent(String objectsString,String searchValues) throws Exception {
	try{
		System.out.println("searchValues="+ searchValues);
		err1="No";
		String searchPass = null;
		String searchFail = null;
		String eachRowData = null;
		String[] searchArrayExpected = searchValues.split(","); //Get array of Searched Values
		String[] pageNoArray = null;
		String[] objArray = null;
		String tableObject = null;
		By objByPageNo=null;
		if(objectsString.contains(",")){
			objArray = objectsString.split(","); // Array of Objects Properties, Table, Page No and Next Page Link
			objByPageNo = new WebElementProcessor(driver).isBy(driver, objArray[1]);
			tableObject=objArray[0];
		}else{
			tableObject=objectsString;
		}
		int pageNos;
		String pageNo;
		boolean pageFlag=false;
		if (objByPageNo!=null){
			pageNo = driver.findElement(objByPageNo).getText(); // Get Next Page No
			pageNoArray = pageNo.split(" "); //Get page No
			pageNos=Integer.parseInt(pageNoArray[3]);
			pageFlag=true;
		}else{
			//err1 = " Unable to Extract PAGE Numbers - not present please check..... For Keyword  - "+keyword+", Action - "+Global.action ;
			pageNos=0;
			pageNo="Page Number : 0";
			//return err1;
		}
		for (int pageLoop=0;pageLoop<=pageNos;pageLoop++) { // Iterating all Pages for Search
			System.out.println(pageNo); //Display Page No in Console 
			By objByTable = new WebElementProcessor(driver).isBy(driver, tableObject);
			if (objByTable!=null){
				WebElement table = driver.findElement(objByTable); 
				List<WebElement> allRows = table.findElements(By.tagName("tr")); //Get All Rows
				// Now get all the TR elements from the table 
				// And iterate over them, getting the cells 
				for (WebElement eachRow : allRows) { //Iterate all rows
					eachRowData = eachRow.getText().trim();
					searchPass = null;
					searchFail = null;
					//Iterate row with all columns
					for (int r1=0;r1<=(searchArrayExpected.length-1);r1++){
						if (eachRowData.contains(searchArrayExpected[r1].trim())){ //Check for If column is matching
							searchFail = "YES";
						}
						else{
							searchPass = "YES";
							break;
						}
					}
					
				}
				if(pageFlag){
						By objByPageNextLink = new WebElementProcessor(driver).isBy(driver, objArray[2]); // Check for Page Nos
						if (objByPageNextLink!=null){
							driver.findElement(objByPageNextLink).click(); //Get the link if Page link if Pages exists
						}else{
							err1 = " LINK TO NEXT PAGE - not present please check..... For Keyword - "+keyword+", Action - "+ "myCaseSearch" ;
							return err1;
						}
					}
				
				logMessage = "At test Step - Keyword = "+keyword+", Action - "+  "myCaseSearch" +" - . Locator : " + objByTable.toString(); // This is to Display on console as well in Log File
				System.out.println(logMessage); // Print on Console
				//logging
				logger.info(logMessage);
				//Log.info(logMessage); //Print on Log 
				//err1 = null;
			}else{
				err1="******** Error occoured in test step *********: "+keyword+", Action - "+ "myCaseSearch" +" - not present please check..... Locator : " + objByTable.toString(); //Preparing Error message when unable to perform action
				System.out.println(err1); // Print on Console
				//logging
				logger.info(err1);
				//Log.error(err1); //Print on Log
				return err1;
			} //Ending Table IF Condition 
		} //Ending For loop Page No

		if(searchFail != null){
			
			err1 = "Error : Some Matches found in the Table;  Keyword - "+keyword+", Action - "+ "Tabledatanotpresent"+" - . Expected : " + objectsString.toString();
		}
	} catch(Exception e) {
		err1="******** Error occoured in test step *********: "+keyword+", Action - "+ "myCaseSearch" +" - not present please check..... Locator : " ; //Preparing Error message when unable to perform action
		System.out.println(err1); // Print on Console
		//logging
		logger.error(err1);
		//Log.error(err1); //Print on Log
	}
	return err1; //Return Values
   }


	/**
	 * Table - Keyword & searchrowcount - Action
	 * @method myCaseSearch
	 * @description - This Search count of Table row which is displayed on Page, not exact count of Searches for Range
	 * 
	 * @throws Exception
	 */		

// Object Property  =  "tableobjectproperty , pagenumber - obj property"
//mandatory  - tableobjectproperty ///// optional - pagenumber - obj property
//if u dont give pagenumber obj property then it will count only for 1st page
//testdata Format --->   =,100 || >,100 || <,100

	public String searchrowcount(String objectsString,String searchRowCriteria) throws Exception {
		try{
			err1="No";
			String searchPass = null;
			String searchFail = null;
			String eachRowData = null;
			String[] pageNoArray = null;
			String[] objArray = null;
			String tableObject = null;
			By objByPageNo=null;
			
			if(objectsString.contains(",")){
				objArray = objectsString.split(","); // Array of Objects Properties, Table, Page No and Next Page Link
				objByPageNo = new WebElementProcessor(driver).isBy(driver, objArray[1]);
				tableObject=objArray[0];
			}else{
				tableObject=objectsString;
			}
			int pageNos;
			String pageNo;
			boolean pageFlag=false;
			if (objByPageNo!=null){
				pageNo = driver.findElement(objByPageNo).getText(); // Get Next Page No
				pageNoArray = pageNo.split(" "); //Get page No
				pageNos=Integer.parseInt(pageNoArray[3]);
				pageFlag=true;
			}else{
				//err1 = " Unable to Extract PAGE Numbers - not present please check..... For Keyword  - "+keyword+", Action - "+Global.action ;
				pageNos=1;
				pageNo="Page Number : 0";
				//return err1;
			}
			String[] searchArrayCriteria=null;
			boolean testdata = false;
			
			//int pageNos=0;
			String searchMethod=null;
			int searchVal=0;
			int appRowCount=0;	
			int totalrowcount=0;
			if(searchRowCriteria.contains(",")){
				searchArrayCriteria = searchRowCriteria.split(","); //Get array of Searched Values
				searchMethod=searchArrayCriteria[0];
				searchVal=Integer.parseInt(searchArrayCriteria[1].trim());
				testdata = true;
			}
			
			for (int pageLoop=1;pageLoop<=pageNos;pageLoop++) {
			By objByTable = new WebElementProcessor(driver).isBy(driver, tableObject);
			if (objByTable!=null){
				WebElement table = driver.findElement(objByTable); 
				List<WebElement> allRows = table.findElements(By.tagName("tr")); //Get All Rows
				appRowCount=allRows.size()-1; //-1 to remove the header of table
				System.out.println("ROW COUNT in TABLE (Which is Displayed on Current Page Table count - Not counted from all pages of table search)"+appRowCount);
				totalrowcount = appRowCount+totalrowcount;
				
			}else{
				err1="******** Error occoured in test step *********: "+keyword+", Action - "+ "SearchRowCount" +" - Obkject - not present please check..... Locator : " + objByTable.toString(); //Preparing Error message when unable to perform action
				System.out.println(err1); // Print on Console
				//logging
				logger.info(err1);
				//Log.error(err1); //Print on Log
				return err1;
			} //Ending Table IF Condition 
			
			if(pageFlag){
				try{
						By objByPageNextLink = new WebElementProcessor(driver).isBy(driver, objArray[1]); // Check for Page Nos
						if (objByPageNextLink!=null){
							driver.findElement(By.cssSelector("#tbl_caselist+#frmPagination .page-info+.next>span")).click(); //Get the link if Page link if Pages exists
							Thread.sleep(3000);
							
						}else{
							err1 = " LINK TO NEXT PAGE - not present please check..... For Keyword - "+keyword+", Action - "+ "myCaseSearch"+Thread.currentThread().getStackTrace()[1].getLineNumber() ;
							throw new Exception(err1);
						}
				}
				catch(ElementNotFoundException e){
					System.out.println("element not found exception");
					logger.info("element not found exception");
				}
				catch (Exception e) {
					System.out.println(e);
				}
			}
			}
			System.out.println("total row count is : "+totalrowcount);
			if(testdata){
			if(searchMethod.contains("=")){
				
				if(totalrowcount==searchVal){
					searchPass="pass";
					logMessage = "At test Step - Keyword = "+keyword+", Action - "+ "SearchRowCount - From Application is "+ appRowCount +" - . Expected : " + searchRowCriteria;
					System.out.println(logMessage); // Print on Console
					//logging
					logger.info(logMessage);
				}else{
					searchFail="pass";
				}
			}else if(searchMethod.contains(">")){
				if(totalrowcount>searchVal){
					searchPass="pass";
					logMessage = "At test Step - Keyword = "+keyword+", Action - "+ "SearchRowCount - From Application is "+ appRowCount +" - . Expected : " + searchRowCriteria;
					System.out.println(logMessage); // Print on Console
					//logging
					logger.info(logMessage);
				}else{
					searchFail="pass";
				}
			}else if(searchMethod.contains("<")){
				if(totalrowcount<searchVal){
					searchPass="pass";
					logMessage = "At test Step - Keyword = "+keyword+", Action - "+ "SearchRowCount - From Application is "+ appRowCount +" - . Expected : " + searchRowCriteria;
					System.out.println(logMessage); // Print on Console
					//logging
					logger.info(logMessage);
				}else{
					searchFail="pass";
				}
			}else{
				System.out.println("Invalid Search Method, Ensure to add specified one in Test Script");
				searchFail="pass";
			}
			
				
			if(searchFail != null){
				err1 = "Error : Application count doesn't Matches to Expected data;  Keyword - "+keyword+", Action - "+ "SearchRowCount - From Application is "+ appRowCount +" - . Expected : " + searchRowCriteria;
			}
			}else{
				System.out.println("NO TESTDATA  IS GIVEN , SO NO VALIDATION IS DONE , DISPLYED ONLY TOTAL NUMBER OF ROW COUNT");
			}
		} catch(Exception e) {
			err1="******** Error occoured in test step *********: "+keyword+", Action - "+ "SearchRowCount" +" - Pls check..... Exception is : "+e.toString() ; //Preparing Error message when unable to perform action
			System.out.println(err1); // Print on Console
			//logging
			logger.error(err1);
			//Log.error(err1); //Print on Log
		}
		return err1; //Return Values
	   }
	






		/**
		 * Table - Keyword & islinkpresent - Action
		 * @method islinkpresent
		 * @description - islinkpresent action to Search for link is present in all the row or in a single row
		 * 
		 * @throws Exception
		 */			
		public String islinkpresent(String objectsString,String searchValues) throws Exception {
			try{
				err1="No";
				WebElement searchPass = null;
				WebElement searchFail = null;
				String eachColData = null;
				String[] pageNoArray = null;
				String[] objArray = null;
				String tableObject = null;
				By objByPageNo=null;
				String[] searchLinktext = null;
				ArrayList<String> printconsole = new ArrayList<>();
				try{
					if(objectsString==null||searchValues==null){
						String error = "please check the object property and searchvales , it should not be null : Table.java -> "
					+Thread.currentThread().getStackTrace()[1].getLineNumber();
						throw new Exception(error);
					}
				}catch (Exception e) {
					throw new Exception(e);
				}
				String[] searchArrayExpected = searchValues.split(","); //Get array of Searched Values
				if(searchArrayExpected.length==1){
					searchLinktext= searchValues.split("=");
				}else{
					for(int i=0;i<searchArrayExpected.length;i++){
					searchLinktext = searchArrayExpected[i].split("=");
					}
				}
				
				if(objectsString.contains(",")){
					objArray = objectsString.split(","); // Array of Objects Properties, Table, Page No and Next Page Link
					objByPageNo = new WebElementProcessor(driver).isBy(driver, objArray[1]);
					tableObject=objArray[0];
				}else{
					tableObject=objectsString;
				}
				int pageNos;
				String pageNo;
				boolean pageFlag=false;
				if (objByPageNo!=null){
					pageNo = driver.findElement(objByPageNo).getText(); // Get Next Page No
					pageNoArray = pageNo.split(" "); //Get page No
					pageNos=Integer.parseInt(pageNoArray[3]);
					pageFlag=true;
				}else{
					//err1 = " Unable to Extract PAGE Numbers - not present please check..... For Keyword  - "+keyword+", Action - "+Global.action ;
					pageNos=0;
					pageNo="Page Number : 0";
					//return err1;
				}
				for (int pageLoop=0;pageLoop<=pageNos;pageLoop++) { // Iterating all Pages for Search
					System.out.println(pageNo); //Display Page No in Console 
					By objByTable = new WebElementProcessor(driver).isBy(driver, tableObject);
					int linenumber = Thread.currentThread().getStackTrace()[1].getLineNumber();
					if (objByTable!=null){
						WebElement table = null;
					
						try{
							table =driver.findElement(objByTable); 
							err1 = "please check the object property , Table.java -> "
									+Thread.currentThread().getStackTrace()[1].getLineNumber();
							throw new Exception(err1);
						}catch(Exception e){
							System.out.println(e);
						}
						
						List<WebElement> allRows = null;
						try{
							allRows = table.findElements(By.tagName("tr")); //Get All Rows
							err1 = "please check the object property" + objByTable+" tr , Table.java -> "
									+Thread.currentThread().getStackTrace()[1].getLineNumber();
							throw new Exception(err1);
						}
						catch(Exception e){
							System.out.println(e);
						}
						// Now get all the TR elements from the table 
						// And iterate over them, getting the cells 
						for (WebElement eachRow : allRows) { //Iterate all rows
							List<WebElement> allcolumn = null;
							try{
							 allcolumn = eachRow.findElements(By.tagName("td"));
							 err1 = "please check the object property" + objByTable+" tr td, Table.java -> "
										+Thread.currentThread().getStackTrace()[1].getLineNumber();
								throw new Exception(err1);
							}
							catch(Exception e){
								System.out.println(e);
							}
							
							for(WebElement eachcolumn : allcolumn){
							eachColData = eachcolumn.getText().trim();
							printconsole.add(eachColData);
							searchPass = null;
							searchFail = null;
							//Iterate row with all columns
							for (int r1=0;r1<searchLinktext.length;r1=r1+2){
								if (eachColData.trim().contains(searchLinktext[r1].trim())){ //Check for If column is matching
									try{
									searchPass =  eachcolumn.findElement(By.tagName("a"));
									err1 = "link is not present in the mentioned text " + searchLinktext[r1]+" tr td, Table.java -> "
											+Thread.currentThread().getStackTrace()[1].getLineNumber();
									throw new Exception(err1);
									}
									catch(Exception e){
										System.out.println(e);
									}
								}
								
							}
							
						}
						}
						if (searchPass==null){
							break;
						}else{
							if(pageFlag){
								By objByPageNextLink = new WebElementProcessor(driver).isBy(driver, objArray[2]); // Check for Page Nos
								if (objByPageNextLink!=null){
									driver.findElement(objByPageNextLink).click(); //Get the link if Page link if Pages exists
								}else{
									err1 = " LINK TO NEXT PAGE - not present please check..... For Keyword - "+keyword+", Action - "+ "islinkpresent" ;
									return err1;
								}
							}
						}
						logMessage = "At test Step - Keyword = "+keyword+", Action - "+  "islinkpresent" +" - . Locator : " + objByTable.toString(); // This is to Display on console as well in Log File
						System.out.println(logMessage); // Print on Console
						//logging
						logger.info(logMessage);
						//Log.info(logMessage); //Print on Log 
						//err1 = null;
					}else{
						err1="******** Error occoured in test step *********: "+keyword+", Action - "+ "islinkpresent" +" object property- not present please check..... Locator : " + objByTable.toString()+
								"error in line number :"+linenumber; //Preparing Error message when unable to perform action
						System.out.println(err1); // Print on Console
						//logging
						logger.info(err1);
						//Log.error(err1); //Print on Log
						return err1;
					} //Ending Table IF Condition 
				} //Ending For loop Page No
		
				
			} catch(Exception e) {
				err1="******** Error occoured in test step *********: "+keyword+", Action - "+ "searchcolumndata" +" " +
							e ; //Preparing Error message when unable to perform action
				System.out.println(err1); // Print on Console
				//logging
				logger.error(err1);
				//Log.error(err1); //Print on Log
			}
			return err1; //Return Values
		}
		/**
		 * 
		 * @param objectsString
		 * @param searchValues
		 * @param dynamicVariables
		 * @return err1
		 * @description get row number from table based on the testdata values
		 * @throws Exception
		 */
		public String getRowNo(String objectsString,String searchValues,DynamicVarRepository dynamicVariables,String variableName) throws Exception{
			try{
				//dynamicVariables = dynamicVariables;
				int rowcnt=0;
				int currRow=0;
				err1="No";
				boolean searchPass = false;
				
				By objByTable = new WebElementProcessor(driver).isBy(driver, objectsString);
				if (objByTable!=null){
					//System.out.println("enter into the table verification");
					WebElement table = driver.findElement(objByTable); 
					List<WebElement> allRows = table.findElements(By.tagName("tr")); //Get All Rows
					//System.out.println("total row count is"+allRows.size());
					
					
					for (WebElement eachRow : allRows) { 
						
						String eachRowData = eachRow.getText().trim();
						//System.out.println("Table row data " + rowcnt + " : " + eachRowData);
						
						
						if(eachRowData.contains(searchValues))
						{
							//System.out.println("value is are in the row"+rowcnt);
							searchPass=true;
							currRow=rowcnt;
							if(variableName!=null){
								dynamicVariables.addObjectToMap(variableName, currRow+"");
								System.out.println("dynamic value for the row number is"+dynamicVariables.getObjectProperty(variableName));
								return err1;
								}
								else{
									err1="******** Error occoured in test step *********: "+variableName+" is null"; //Preparing Error message when unable to perform action
									System.out.println(err1); // Print on Console
									//logging
									logger.info(err1);
								}
						}
						else if (searchValues.contains("="))
						{
							String[] searchArray=searchValues.split("=");
						List<WebElement> AllCoumns = eachRow.findElements(By.tagName("td")); //Get All columns
						//System.out.println("total cloumns are:"+AllCoumns.size());
						for (WebElement eachColumn : AllCoumns) { 
							List<WebElement> allInputBoxes = eachColumn.findElements(By.tagName("input"));//get all input box
							//System.out.println("total inputboxes are:"+allInputBoxes.size());
							for (WebElement eachInput : allInputBoxes){
								//System.out.println("input box values is"+eachInput.getAttribute("value")+" for the row No"+rowcnt);
								if(eachInput.getAttribute("value").equalsIgnoreCase(searchArray[1]))
								{
									System.out.println("value is matched"+eachInput.getAttribute("value")+" and row num is:"+rowcnt);
									searchPass=true;
									if(variableName!=null)
									{
									currRow=rowcnt-1;
									dynamicVariables.addObjectToMap(variableName, currRow+"");
									System.out.println("dynamic value for the row number is"+dynamicVariables.getObjectProperty(variableName));
									return err1;
									}
									else{
										err1="******** Error occoured in test step *********: "+variableName+" is null"; //Preparing Error message when unable to perform action
										System.out.println(err1); // Print on Console
										//logging
										logger.info(err1);
									}
								}//if
							}//first for
						}//second for
						
						}//else if
						rowcnt++;
						if(searchPass==false && rowcnt-1==allRows.size())
						{
							err1="******** Error occoured in test step *********: "+keyword+", Action - "+ objectsString +" object property- not present please check..... Locator : " + objByTable.toString(); //Preparing Error message when unable to perform action
							System.out.println(err1); // Print on Console
							//logging
							logger.info(err1);
							//Log.error(err1); //Print on Log
							//return err1;
						}
					}//third for
						
				}//main if		
				return err1;
				}
			catch(Exception e){
				err1="******** Error occoured in test step *********: "+keyword+", Action - "+ "searchcolumndata" +" " +
						e ; //Preparing Error message when unable to perform action
			System.out.println(err1); // Print on Console
			//logging
			logger.error(err1);
			return err1;
			}
		}
		
		/**
		 * 
		 * @param objectsString
		 * @param searchValues
		 * @param dynamicVariables
		 * @return err1
		 * @description get row number from table based on the testdata values
		 * @throws Exception
		 */
		//testdata sample :  (columnname=value,columnname)  --- stores the respective value in hashmap
		public String getrespesctivecolumnvalues(String objectproperty,String testdata,DynamicVarRepository dynamicVariables,String variableNameFromTestStep){
			try{
				err1 ="No";
			ArrayList<String> arr = new ArrayList<>();		
			String[] testdataarray =null;
			String[] testdatainrowarray =null;
			int colnum=1;
			int rownum=1;
			int columnnumtogetkey =0;
			int columnnumtogetvalue=0;
			
			try{
				if(!testdata.contains("=")){
					err1="******** Error occoured in test step *********: Table - getrespesctivecolumnvalues ,"
							+ "Testdata should have = 'column name and the value from which key is taken'" ;
					throw new Exception(err1);
				}else if(!testdata.contains(",")) {
					err1="******** Error occoured in test step *********: Table - getrespesctivecolumnvalues ,"
							+ "Testdata should be seperated by , 'column name from which key is taken and respesctivecolumn from which values to be stored'" ;
					throw new Exception(err1);
				}
			}catch(Exception e){
				throw e;
			}
			if(testdata.contains(",")){
				testdataarray = testdata.split(",");  // split by ,  and store the values [column names] 
				if(!testdataarray[0].contains("=")){
					err1="******** Error occoured in test step *********: Table - getrespesctivecolumnvalues ,"
							+ "Testdata should have = 'left hand side the column name and right hand side the value from which key is taken'" ;
					throw new Exception(err1);
				}else{
					testdatainrowarray = testdataarray[0].split("=");  // split by = and store the values [column names and values]
				}
				}
			
			
			
			WebElement table = driver.findElement(By.cssSelector(objectproperty));
			
			List<WebElement> columnlist = table.findElements(By.tagName("th"));
			String steppass =null;
			String steppass1 =null;
			for (WebElement collist : columnlist) {
				System.out.println("header :"+collist.getText().trim());
				if(collist.getText().equalsIgnoreCase(testdataarray[1])){
					columnnumtogetvalue = colnum;
					steppass = "column matched";
					//System.out.println("inside loop columnnumtogetvalue:"+columnnumtogetvalue);
				}
				if(collist.getText().equalsIgnoreCase(testdatainrowarray[0])){
					columnnumtogetkey = colnum;
					steppass1 = "column matched";
				//	System.out.println("inside loop columnnumtogetkey:"+columnnumtogetkey);
				}
				colnum++;
			}
			
			if(steppass1==null||steppass==null){
				err1="******** Error occoured in test step *********: Table - getrespesctivecolumnvalues ,"
						+ "coloumn is not matched" ;
				throw new Exception(err1);
			}
			for (WebElement collist : columnlist) {
				if(collist.getText().equalsIgnoreCase(testdatainrowarray[0])){
					List<WebElement> allrowvalueofacolumn = null;
					
					try{
						allrowvalueofacolumn = table.findElements(By.cssSelector(objectproperty+" tbody tr td:nth-child("+columnnumtogetkey+")"));
					}catch(Exception e){
						err1="******** Error occoured in test step *********: Table - getrespesctivecolumnvalues ,"
								+ "coloumn is not matched" ;
						System.out.println(err1);
					}
					
					for (WebElement indvijualrowvalueofacolumn : allrowvalueofacolumn) {
						//System.out.println("roles :"+indvijualrowvalueofacolumn.getText());
						if(indvijualrowvalueofacolumn.getText().equalsIgnoreCase(testdatainrowarray[1])){
							String correspondingcolumn = table.findElement(By.cssSelector(objectproperty+" tbody tr:nth-child("+rownum+") td:nth-child("+columnnumtogetvalue+")")).getText();
							//System.out.println("useraction values for admin is :"+correspondingcolumn);
							if(!arr.toString().contains(correspondingcolumn)){
							arr.add(correspondingcolumn);
							}
						}
						rownum++;
					}
				}
				
			}
			
			if(arr.size()<1){
				err1="******** Error occoured in test step *********: Table - getrespesctivecolumnvalues ,"
						+ "coloumn is not matched" ;
				System.out.println(err1);
				throw new Exception(err1);
			}if(arr.size()==1){
				if(variableNameFromTestStep!=null&&!variableNameFromTestStep.isEmpty()){
					String value = arr.get(0).toString();
					dynamicVariables.addObjectToMap(variableNameFromTestStep,value);
				}else{
					String value = arr.get(0).toString();
				dynamicVariables.addObjectToMap(testdatainrowarray[0],value);
				}
			}else{
			if(variableNameFromTestStep!=null&&!variableNameFromTestStep.isEmpty()){
				dynamicVariables.addObjectToMap(variableNameFromTestStep,arr.toString());
			}else{
			dynamicVariables.addObjectToMap(testdatainrowarray[0],arr.toString());
			}
			}
		}catch(Exception e){
			err1 = e.getMessage();
		}
		
			return err1;
		}
		
		/**
		 * 
		 * @param objectsString
		 * @param searchValues
		 * @param dynamicVariables
		 * @return err1
		 * @description get row number from table based on the testdata values
		 * @throws Exception
		 * @author prabha
		 */
		//testdata sample :  (columnname=value,columnname)  --- stores the respective value in hashmap
		public String checkrespesctivecolumnvalue(String objectproperty,String testdata,DynamicVarRepository dynamicVariables,String variableNameFromTestStep){
			try{
				err1 = "NO";
				if(!testdata.contains("{")&&!testdata.contains(";")&&!testdata.contains("=")){
						err1="******** Error occoured in test step *********: Table - checkrespesctivecolumnvalue ,"
								+ "Testdata should be in the below format" 
								+ "{keycolumn=keyvalue};{searchcolumn=searchvalue} || {keycolumn1=keyvalue1,keycolumn2=keyvalue2};{searchcolumn=searchvalue,searchcolumn1=searchvalue1}";
						throw new Exception(err1);
				}
			boolean multiplekey = false;
			boolean multiplesearch = false;
			int rownumbertosearchdata =1;
			WebElement table = null;
			if(objectproperty.contains("/")){
				table =	driver.findElement(By.xpath(objectproperty));
			}else{
				table = driver.findElement(By.cssSelector(objectproperty));
			}
			String[] testdataarr = testdata.replaceAll("\\{|\\}","").split(";");
			//validation for multiple key and multipl search
			if(testdataarr[0].contains(",")){
				multiplekey = true;	
			}
			if(testdataarr[1].contains(",")){
				multiplesearch = true;	
			}
			
			if(!multiplekey){
				//first getting the key column name and key row number
				String[] keyarr = testdataarr[0].split("=");
				String keycol = keyarr[0];
				String keyvalue = keyarr[1];
				
				int colnum=1;
				String columnmatch = null;
				String rowmatch = null;
				
				List<WebElement> keycollst = table.findElements(By.tagName("th")); //list to get the column name from application
				for (WebElement indcolval : keycollst) {	//for loop for getting the column number of the single key search
					String colname = indcolval.getText();
					if(keycol.trim().equalsIgnoreCase(colname.trim())){
						columnmatch = "Pass";
						break;
					}
					colnum++;
				}
													// loop for getting the row data and match with the testdata and get the row number
				List<WebElement> rowlistofthekeycolumn = table.findElements(By.cssSelector("tbody tr>td:nth-child("+colnum+")"));
				for (WebElement rowdata : rowlistofthekeycolumn) {
					String rowvalue = rowdata.getText();
					if(keyvalue.trim().equalsIgnoreCase(rowvalue.trim())){
						rowmatch = "Pass";
						break;
					}
					rownumbertosearchdata++;
				}
				
				if(columnmatch==null&&rowmatch==null){
					err1 = "Error in getting key column and key value in single keysearch";
					System.out.println(err1);
				}else{
					columnmatch=null;
					rowmatch=null;
					colnum=1;
				}
				
				if(!multiplesearch){
					String[] searchdataarr = testdataarr[1].split("=");
					String searchcol = searchdataarr[0];
					String searchvalue = searchdataarr[1];
					
					List<WebElement> searchcollst = table.findElements(By.tagName("th")); //list to get the column name from application
					for (WebElement indcolval : searchcollst) {	//for loop for getting the column number of the single key search
						String colname = indcolval.getText();
						if(searchcol.equalsIgnoreCase(colname)){
							columnmatch = "Pass";
							break;
						}
						colnum++;
					}
					String singlesearchdata = table.findElement(By.cssSelector("tbody tr:nth-child("+rownumbertosearchdata+") td:nth-child("+colnum+")")).getText();
					if(searchvalue.equalsIgnoreCase(singlesearchdata)){
						rowmatch = "Pass";
					}
					if(columnmatch==null&&rowmatch==null){
						err1 = "Error in getting Search column and search value in single data search";
						System.out.println(err1);
					}else if(rowmatch==null){
						err1="******** Error occoured in test step *********: Table - checkrespesctivecolumnvalue ,"
								+ "Testdata is not matched with the table data retrived from application'" ;
						throw new Exception(err1);
					}
					else{
						columnmatch=null;
						rowmatch=null;
						colnum=1;
					}
				}
				else if(multiplesearch){
					String[] mulsearchdataarr = testdataarr[1].split(",");
					for(int i=0;i<mulsearchdataarr.length;i++){
						String[] searchdataarr = mulsearchdataarr[i].split("=");
						String searchcol = searchdataarr[0];
						String searchvalue = searchdataarr[1];
						String colname = null;
						boolean colmatch = false;
						List<WebElement> searchcollst = table.findElements(By.tagName("th")); //list to get the column name from application
						for (WebElement indcolval : searchcollst) {	//for loop for getting the column number of the single key search
							colname= indcolval.getText();
							if(searchcol.trim().equalsIgnoreCase(colname.trim())){
								columnmatch = "Pass";
								colmatch = true;
								break;
							}
							colnum++;
						}
						if(!colmatch){
							err1 = "Error in getting column header, Application does not have some columnn given in test data, please verify your testdata";
							throw new Exception(err1);
						}
						String searchdata = table.findElement(By.cssSelector("tbody tr:nth-child("+rownumbertosearchdata+") td:nth-child("+colnum+")")).getText();
						System.out.println("resultdata = :"+searchdata);
						if(searchvalue.trim().equalsIgnoreCase(searchdata.trim())){
							rowmatch = "Pass";
						}
						if(columnmatch==null&&rowmatch==null){
							err1 = "Error in getting Search column and search value in multple data search";
							throw new Exception(err1);
						}else if(rowmatch==null){
							err1="******** Error occoured in test step *********: Table - checkrespesctivecolumnvalue ,"
									+ "row data in multiple search from application is "+ searchdata +" is not matched with the table rowdata retrived from testdata :"+searchvalue; ;
							throw new Exception(err1);
						}else if(columnmatch==null){
							err1="******** Error occoured in test step *********: Table - checkrespesctivecolumnvalue ,"
									+ "Column in multiple search from application is "+ colname +" is not matched with the table column in testdata :"+searchcol; ;
							throw new Exception(err1);
						}
						else{
							columnmatch=null;
							rowmatch=null;
							colnum=1;
						}
					}
				}
			}
			
			return err1;
			}catch(Exception e){
				e.printStackTrace();
				err1 = e.getMessage();
				return err1;
			}
		}
		
		/**
		 * 
		 * @param objectsString
		 * @param searchValues
		 * @param dynamicVariables
		 * @return err1
		 * @description get row number from table based on the testdata values
		 * @throws Exception
		 */
		//testdata sample :  (columnname=value,columnname)  --- stores the respective value in hashmap
		public String getrespesctivecolumnvalue(String objectproperty,String testdata,DynamicVarRepository dynamicVariables,String variableNameFromTestStep){
			try{
				err1 ="No";
			ArrayList<String> arr = new ArrayList<>();		
			String[] testdataarray =null;
			String[] testdataincolumnrowarray1 =null;
			String[] testdataincolumnrowarray2 =null;
			String[] testdatasinrowarray =null;
			int colnum=1;
			int rownum=1;
			int columnnumtogetkey1 =0;
			int columnnumtogetkey2 =0;
			int columnnumtogetvalue=0;
			
			try{
				if(!testdata.contains("=")){
					err1="******** Error occoured in test step *********: Table - getrespesctivecolumnvalues ,"
							+ "Testdata should have = 'column name and the value from which key is taken'" ;
					throw new Exception(err1);
				}else if(!testdata.contains(",")) {
					err1="******** Error occoured in test step *********: Table - getrespesctivecolumnvalues ,"
							+ "Testdata should be seperated by , 'column name from which key is taken and respesctivecolumn from which values to be stored'" ;
					throw new Exception(err1);
				}
			}catch(Exception e){
				throw e;
			}
			if(testdata.contains(",")){
				testdataarray = testdata.split(",");  // split by ,  and store the values [column names] 
				if(!testdataarray[0].contains("=")){
					err1="******** Error occoured in test step *********: Table - getrespesctivecolumnvalues ,"
							+ "Testdata should have = 'left hand side the column name and right hand side the value from which key is taken'" ;
					throw new Exception(err1);
				}else{
				//	testdatainrowarray = testdataarray[0].split("=");  // split by = and store the values [column names and values]
					testdatasinrowarray = testdataarray[0].split("&&");
					testdataincolumnrowarray1 = testdatasinrowarray[0].split("=");
					testdataincolumnrowarray2 = testdatasinrowarray[1].split("=");
				}
				}
			
			
			WebElement table = driver.findElement(By.cssSelector(objectproperty));
			
			List<WebElement> columnlist = table.findElements(By.tagName("th"));
			String steppassforgettingvalue =null;
			String steppassforgettingkey1 =null;
			String steppassforgettingkey2 =null;
			for (WebElement collist : columnlist) {
				System.out.println("header :"+collist.getText().trim());
				if(collist.getText().equalsIgnoreCase(testdataarray[1])){ // this is to search for the respective column of value(from testdate) to be stored in hashmap
					columnnumtogetvalue = colnum;
					steppassforgettingvalue = "column matched";
					//System.out.println("inside loop columnnumtogetvalue:"+columnnumtogetvalue);
				}
				if(collist.getText().equalsIgnoreCase(testdataincolumnrowarray1[0])){
					columnnumtogetkey1 = colnum;
					steppassforgettingkey1 = "column matched";
				//	System.out.println("inside loop columnnumtogetkey:"+columnnumtogetkey);
				}
				if(collist.getText().equalsIgnoreCase(testdataincolumnrowarray2[0])){
					columnnumtogetkey2 = colnum;
					steppassforgettingkey2 = "column matched";
				}
				colnum++;
			}
			
			if(steppassforgettingvalue==null||steppassforgettingkey1==null||steppassforgettingkey2==null){
				err1="******** Error occoured in test step *********: Table - getrespesctivecolumnvalues ,"
						+ "coloumn is not matched" ;
				throw new Exception(err1);
			}
			
			//this is to get the row number from which the value should be picked, 
			//search is done for two values in 2 different columns and the matching row number is returned
			List<WebElement> totalrows = table.findElements(By.cssSelector(objectproperty+" tbody tr"));
			for(int i=1;i<totalrows.size();i++){
				String rowvalueofcolumnkey1 = table.findElement(By.cssSelector(objectproperty+" tbody tr:nth-child("+i+")"+" td:nth-child("+columnnumtogetkey1+")")).getText();
				if(rowvalueofcolumnkey1.equalsIgnoreCase(testdataincolumnrowarray1[1])){
					String rowvalueofcolumnkey2 = table.findElement(By.cssSelector(objectproperty+" tbody tr:nth-child("+i+")"+" td:nth-child("+columnnumtogetkey2+")")).getText();
					if(rowvalueofcolumnkey2.equalsIgnoreCase(testdataincolumnrowarray2[1])){
						break;
					}
				}
				rownum++;
			}
			
			String correspondingrowdata = table.findElement(By.cssSelector(objectproperty+" tbody tr:nth-child("+rownum+") td:nth-child("+columnnumtogetvalue+")")).getText();
			//System.out.println("useraction values for admin is :"+correspondingcolumn);
			if(correspondingrowdata==null){
				err1="******** Error occoured in test step *********: Table - getrespesctivecolumnvalue ,"
						+ "the data to be stored in hashmap is not present," ;
				throw new Exception(err1);
			}
			if(!arr.toString().contains(correspondingrowdata)){
			arr.add(correspondingrowdata);
			}
						
			if(arr.size()<1){
				err1="******** Error occoured in test step *********: Table - getrespesctivecolumnvalues ,"
						+ "coloumn is not matched" ;
				System.out.println(err1);
				throw new Exception(err1);
			}if(arr.size()==1){
				if(variableNameFromTestStep!=null&&!variableNameFromTestStep.isEmpty()){
					String value = arr.get(0).toString();
					dynamicVariables.addObjectToMap(variableNameFromTestStep,value);
				}/*else{
					String value = arr.get(0).toString();
				dynamicVariables.addObjectToMap(testdatainrowarray[0],value);
				}*/
			}else{
			if(variableNameFromTestStep!=null&&!variableNameFromTestStep.isEmpty()){
				dynamicVariables.addObjectToMap(variableNameFromTestStep,arr.toString());
			}/*else{
			dynamicVariables.addObjectToMap(testdatainrowarray[0],arr.toString());
			}*/
			}
		}catch(Exception e){
			err1 = e.getMessage();
		}
		
			return err1;
		}
		
		
		
		/**
		 * 
		 * @param objectsString
		 * @param searchValues
		 * @param dynamicVariables
		 * @return err1
		 * @description get row number from table based on the testdata values
		 * @throws Exception
		 */
		//testdata sample :  (columnname=value,columnname) --- clicks on the value
		public String clickonrespesctivecolumnvalues(String objectproperty,String testdata){
			try{
				err1 ="No";
			String[] testdataarray =null;
			String[] testdatainrowarray =null;
			int colnum=1;
			int rownum=1;
			int columnnumtogetkey =0;
			int columnnumtogetvalue=0;
			int rownumtogetvalue=0;
			
			try{
				if(!testdata.contains("=")){
					err1="******** Error occoured in test step *********: Table - getrespesctivecolumnvalues ,"
							+ "Testdata should have = 'column name and the value from which key is taken'" ;
					throw new Exception(err1);
				}else if(!testdata.contains(",")) {
					err1="******** Error occoured in test step *********: Table - getrespesctivecolumnvalues ,"
							+ "Testdata should be seperated by , 'column name from which key is taken and respesctivecolumn from which values to be stored'" ;
					throw new Exception(err1);
				}
			}catch(Exception e){
				throw e;
			}
			if(testdata.contains(",")){
				testdataarray = testdata.split(",");  // split by ,  and store the values [column names] 
				if(!testdataarray[0].contains("=")){
					err1="******** Error occoured in test step *********: Table - getrespesctivecolumnvalues ,"
							+ "Testdata should have = 'left hand side the column name and right hand side the value from which key is taken'" ;
					throw new Exception(err1);
				}else{
					testdatainrowarray = testdataarray[0].split("=");  // split by = and store the values [column names and values]
				}
				}
			
			
			WebElement table = driver.findElement(By.cssSelector(objectproperty));
			
			List<WebElement> columnlist = table.findElements(By.tagName("th"));
			System.out.println("column size :"+columnlist.size());
			String steppass =null;
			String steppass1 =null;
			for (WebElement collist : columnlist) {
				System.out.println("header :"+collist.getText().trim());
				if(collist.getText().equalsIgnoreCase(testdataarray[1])){
					columnnumtogetvalue = colnum;
					steppass = "column matched";
					System.out.println("inside loop columnnumtogetvalue:"+columnnumtogetvalue);
				}
				if(collist.getText().equalsIgnoreCase(testdatainrowarray[0])){
					columnnumtogetkey = colnum;
					steppass1 = "column matched";
					System.out.println("inside loop columnnumtogetkey:"+columnnumtogetkey);
				}
				colnum++;
			}
			System.out.println("steppass :"+steppass);
			System.out.println("steppass :"+steppass1);
			if(steppass1==null||steppass==null){
				err1="******** Error occoured in test step *********: Table - getrespesctivecolumnvalues ,"
						+ "coloumn is not matched" ;
				throw new Exception(err1);
			}
			for (WebElement collist : columnlist) {
				if(collist.getText().equalsIgnoreCase(testdatainrowarray[0])){
					List<WebElement> allrowvalueofacolumn = null;
					
					try{
						allrowvalueofacolumn = table.findElements(By.cssSelector(objectproperty+" tbody tr td:nth-child("+columnnumtogetkey+")"));
					}catch(Exception e){
						err1="******** Error occoured in test step *********: Table - getrespesctivecolumnvalues ,"
								+ "coloumn is not matched" ;
						System.out.println(err1);
					}
					WebElement chkbox=null;
					WebElement lnk=null;
					WebElement onlytdtag = null;
					boolean checkbox = false;
					boolean link = false;
					boolean button=true;
					boolean tdtag = false;
					for (WebElement indvijualrowvalueofacolumn : allrowvalueofacolumn) {
						System.out.println("roles :"+indvijualrowvalueofacolumn.getText());
						if(indvijualrowvalueofacolumn.getText().trim().equalsIgnoreCase(testdatainrowarray[1].trim())){
							rownumtogetvalue = rownum;
							break;
						}
						rownum++;
					}
					System.out.println("rownumtogetvalue :"+rownumtogetvalue);
					/*for (WebElement indvijualrowvalueofacolumn : allrowvalueofacolumn) {
						System.out.println("roles :"+indvijualrowvalueofacolumn.getText());*/
					//	if(indvijualrowvalueofacolumn.getText().trim().equalsIgnoreCase(testdatainrowarray[1].trim())){
							try{
							 chkbox = table.findElement(By.cssSelector(objectproperty+" tbody tr:nth-child("+rownumtogetvalue+") td:nth-child("+columnnumtogetvalue+")>input"));
							// System.out.println(checkbox);
							if(chkbox!=null)
								checkbox = true;
							}
							catch(Exception e){
								System.out.println("no input tag");
							}
							try{
							 lnk = table.findElement(By.cssSelector(objectproperty+" tbody tr:nth-child("+rownumtogetvalue+") td:nth-child("+columnnumtogetvalue+")>a"));
							if (lnk!=null)
								link = true;
							//this is for delete (when comes after edit button, to be enhanced later 
							//table.findElement(By.cssSelector(objectproperty+" tbody tr:nth-child("+rownum+") td:nth-child("+columnnumtogetvalue+")>a:nth-child(2)"));
							}catch(Exception e){
								//e.printStackTrace();
							}
							
							try{
								onlytdtag = table.findElement(By.cssSelector(objectproperty+" tbody tr:nth-child("+rownumtogetvalue+") td:nth-child("+columnnumtogetvalue+")"));
								if(onlytdtag!=null){
									tdtag = true;
								}
							}catch(Exception e){
								System.out.println("not only tg tag is present ");
							}
							
							if(tdtag){
								try{
								button = false;
								onlytdtag.click();
								}catch(Exception e){
									err1="******** Error occoured in test step *********: Table - getrespesctivecolumnvalues ,"
											+ "checkbox is not checked for line number "+Thread.currentThread().getStackTrace()[1].getLineNumber();
									System.out.println(err1);
								}
							}
							if(checkbox){
								try{
								//table.findElement(By.cssSelector(objectproperty+" tbody tr:nth-child("+rownum+") td:nth-child("+columnnumtogetvalue+")>input")).click();
								//	System.out.println("chechbox calue: "+chkbox.toString());
									chkbox.click();
									button=false;
								}catch(Exception e){
									err1="******** Error occoured in test step *********: Table - getrespesctivecolumnvalues ,"
											+ "checkbox is not checked for line number "+Thread.currentThread().getStackTrace()[1].getLineNumber();
									System.out.println(err1);
								}
							}
							if(link){
								try{
								table.findElement(By.cssSelector(objectproperty+" tbody tr:nth-child("+rownumtogetvalue+") td:nth-child("+columnnumtogetvalue+")>a")).click();
								button=false;
								}
								catch(Exception e){
									err1="******** Error occoured in test step *********: Table - getrespesctivecolumnvalues ,"
											+ "Link is not there for line number "+Thread.currentThread().getStackTrace()[1].getLineNumber();
									System.out.println(err1);
								}
							}
							if(button){
								try{
								table.findElement(By.cssSelector(objectproperty+" tbody tr:nth-child("+rownumtogetvalue+") td:nth-child("+columnnumtogetvalue+") p a span")).click();
								
								}
								catch(Exception e){
									err1="******** Error occoured in test step *********: Table - getrespesctivecolumnvalues ,"
											+ "Button is not there for line number "+Thread.currentThread().getStackTrace()[1].getLineNumber();
									System.out.println(err1);
								}
							}
							
					//	}
						
					//	rownum++;
					//}
					if(!checkbox&&!link&&!button&&!tdtag){
						err1="******** Error occoured in test step *********: Table - getrespesctivecolumnvalues ,"
								+ "NO match is found with the testdata and application data i"+Thread.currentThread().getStackTrace()[1].getLineNumber();
						System.out.println(err1);
					}
				}
				
			}
			
			
		}catch(Exception e){
			
		}
		
			return err1;
		}
		/**
		 * 
		 * @param objectsString
		 * @param searchValues
		 * @param dynamicVariables
		 * @return err1
		 * @description get row number from table based on the testdata values
		 * @throws Exception
		 */
		public String searchrowdatawithcolumnheader(String objectproperty,String testdata){
			try{
				err1 ="No";
			ArrayList<String> arr = new ArrayList<>();		
			String[] testdataarray =null;
			String[] testdatainrowarray =null;
			String valuetosearch1 = null;
			String valuetosearch2 = null;
			int colnum=1;
			int rownum=1;
			int columnnumtogetkey =0;
			int columnnumtogetvalue=0;
			
			try{
				if(!testdata.contains("=")){
					err1="******** Error occoured in test step *********: Table - searchrowdatawithcolumnheader ,"
							+ "Testdata should have '=' 'column name = value (sample format is status = pass||onhold or status = pass)" ;
					throw new Exception(err1);
				}/*else if(!testdata.contains(",")) {
					err1="******** Error occoured in test step *********: Table - getrespesctivecolumnvalues ,"
							+ "Testdata should be seperated by , 'column name from which key is taken and respesctivecolumn from which values to be stored'" ;
					throw new Exception(err1);
				}*/
			}catch(Exception e){
				throw e;
			}
			if(testdata.contains("=")){
				testdataarray = testdata.split("=");  // split by ,  and store the values [column names] 
				if(testdataarray[1].contains("||")){
					String[] valarr = testdataarray[1].split("||");
					valuetosearch1 = valarr[0];
					valuetosearch2 = valarr[1];
				}else{
					valuetosearch1 = testdataarray[1];  // split by = and store the values [column names and values]
				}
				}
			
			
			WebElement table = driver.findElement(By.cssSelector(objectproperty));
			
			List<WebElement> columnlist = table.findElements(By.tagName("th"));
			String steppass =null;
			String steppass1 =null;
			for (WebElement collist : columnlist) {
				System.out.println("header :"+collist.getText().trim());
				if(collist.getText().equalsIgnoreCase(testdataarray[0])){
					columnnumtogetvalue = colnum;
					steppass = "column matched";
					//System.out.println("inside loop columnnumtogetvalue:"+columnnumtogetvalue);
				}
				/*if(collist.getText().equalsIgnoreCase(testdatainrowarray[0])){
					columnnumtogetkey = colnum;
					steppass1 = "column matched";
				//	System.out.println("inside loop columnnumtogetkey:"+columnnumtogetkey);
				}*/
				colnum++;
			}
			
			/*if(steppass1==null||steppass==null)*/
			if(steppass==null){
				err1="******** Error occoured in test step *********: Table - searchrowdatawithcolumnheader ,"
						+ "column in the application is not matched with the column given in testdata" ;
				throw new Exception(err1);
			}
			for (WebElement collist : columnlist) {
				if(collist.getText().equalsIgnoreCase(testdataarray[0])){
					List<WebElement> allrowvalueofacolumn = null;
					
					try{
						allrowvalueofacolumn = table.findElements(By.cssSelector(objectproperty+" tbody tr td:nth-child("+columnnumtogetkey+")"));
					}catch(Exception e){
						err1="******** Error occoured in test step *********: Table - searchrowdatawithcolumnheader ,"
								+ "cant get all the values of a column and store in the list "+Thread.currentThread().getStackTrace()[1].getLineNumber() ;
						System.out.println(err1);
						throw new Exception(err1);
					}
					
					for (WebElement indvijualrowvalueofacolumn : allrowvalueofacolumn) {
						//System.out.println("roles :"+indvijualrowvalueofacolumn.getText());
						if(!testdata.contains("||")){
						if(indvijualrowvalueofacolumn.getText().equalsIgnoreCase(valuetosearch1)){
							logMessage = "At test Step - Keyword = "+keyword+", Action = "+ "searchrowdatawithcolumnheader"
									+valuetosearch1 + "is present in table in the column name "+collist.getText() ;
							System.out.println(logMessage); // Print on Console
							//logging
							logger.info(logMessage);
							break;
						}else{
							err1="******** Error occoured in test step *********: Table - searchrowdatawithcolumnheader ,"
									+valuetosearch1 + "is not present in table in the column name "+collist.getText() +Thread.currentThread().getStackTrace()[1].getLineNumber() ;
							System.out.println(err1);
							
						}
						rownum++;
						}else{
							if(indvijualrowvalueofacolumn.getText().equalsIgnoreCase(valuetosearch1)){
								logMessage = "At test Step - Keyword = "+keyword+", Action = "+ "searchrowdatawithcolumnheader"
										+valuetosearch1 + "is present in table in the column name "+collist.getText() ;
								System.out.println(logMessage); // Print on Console
								//logging
								logger.info(logMessage);
							}else{
								err1="******** Error occoured in test step *********: Table - searchrowdatawithcolumnheader ,"
										+valuetosearch1 + "is not present in table in the column name "+collist.getText() +Thread.currentThread().getStackTrace()[1].getLineNumber() ;
								System.out.println(err1);
								
							}
							if(indvijualrowvalueofacolumn.getText().equalsIgnoreCase(valuetosearch2)){
								logMessage = "At test Step - Keyword = "+keyword+", Action = "+ "searchrowdatawithcolumnheader"
										+valuetosearch1 + "is present in table in the column name "+collist.getText() ;
								System.out.println(logMessage); // Print on Console
								//logging
								logger.info(logMessage);
							}else{
								err1="******** Error occoured in test step *********: Table - searchrowdatawithcolumnheader ,"
										+valuetosearch2 + "is not present in table in the column name "+collist.getText() +Thread.currentThread().getStackTrace()[1].getLineNumber() ;
								System.out.println(err1);
								
							}
						}
					}
				}
				
			}
			
		}catch(Exception e){
			err1 = e.getMessage();
		}
		
			return err1;
		}
		
		/**
		 * 
		 * @param objectsString
		 * @param searchValues
		 * @param dynamicVariables
		 * @return err1
		 * @description get row number from table based on the testdata values
		 * @throws Exception
		 */
		//gets all the row values of a column and store in the hashmap
		//pass the table object property and in testdata pass the column name
		public String getallrowdataofacolumn(String objectproperty,String testdata){
			try{
			err1 ="No";
			String[] testdataarray =null;
			String[] testdatainrowarray =null;
			int colnum=1;
			int rownum=1;
			int columnnumtogetkey =0;
			int columnnumtogetvalue=0;
			
			
			// this is to handle if we want to store multiple column values in to hashmap
			if(testdata.contains(",")){
				testdataarray = testdata.split(",");  // split by ,  and store the values [column names] 
				
				}
			else{
				//testdataarray[0] = testdata;
				
			}
			WebElement table = null;
			try{
			table = driver.findElement(By.cssSelector(objectproperty));
			}
			catch(Exception e){
				err1 = "table object property is not found";
			}
			
			List<WebElement> columnlist = table.findElements(By.tagName("th"));
			String steppass =null;
			//String steppass1 =null;
			for (WebElement collist : columnlist) {
				System.out.println("header :"+collist.getText().trim());
				if(collist.getText().equalsIgnoreCase(testdata)){
					columnnumtogetvalue = colnum;
					steppass = "column matched";
					//System.out.println("inside loop columnnumtogetvalue:"+columnnumtogetvalue);
				}
				
				colnum++;
			}
			
			if(steppass==null){
				err1="******** Error occoured in test step *********: Table - getrespesctivecolumnvalues ,"
						+ "coloumn is not matched" ;
				throw new Exception(err1);
			}
			String insertIndRowColumn;
			for (WebElement collist : columnlist) {
				if(collist.getText().equalsIgnoreCase(testdata)){

					List<WebElement> allrowvalueofacolumn = null;
					
					try{
						allrowvalueofacolumn = table.findElements(By.cssSelector(objectproperty+" tbody tr td:nth-child("+columnnumtogetvalue+")"));
					}catch(Exception e){
						err1="******** Error occoured in test step *********: Table - getrespesctivecolumnvalues ,"
								+ "coloumn is not matched" ;
						System.out.println(err1);
					}
					for (int i = 1; i <=allrowvalueofacolumn.size(); i++) {
						WebElement elem = table.findElement(By.cssSelector(objectproperty+" tbody tr:nth-child("+i+") td:nth-child("+columnnumtogetvalue+")"));
						insertIndRowColumn = elem.getText();
						if(!insertIndRowColumn.isEmpty()||insertIndRowColumn==null||insertIndRowColumn.equalsIgnoreCase("")){
							Actions act = new Actions(driver);
							act.moveToElement(elem).build().perform();
							insertIndRowColumn = elem.getText();
						}
						System.out.println("indvijualrowvalueofacolumn values :"+insertIndRowColumn);
						setlistvalues(insertIndRowColumn);
					}
					//	String rowdata = table.findElement(By.cssSelector(objectproperty+" tbody tr:nth-child("+rownum+") td:nth-child("+columnnumtogetvalue+")")).getText();
						//System.out.println("useraction values for admin is :"+correspondingcolumn);
						
					
					/*for (WebElement indvijualrowvalueofacolumn : allrowvalueofacolumn) {
						System.out.println("indvijualrowvalueofacolumn values :"+indvijualrowvalueofacolumn.getText());
						if(indvijualrowvalueofacolumn.getText().contains("$")){
						insertIndRowColumn = indvijualrowvalueofacolumn.getText().replace("$", "").trim();
						}else{
							insertIndRowColumn = indvijualrowvalueofacolumn.getText().trim();
						}
						//	String rowdata = table.findElement(By.cssSelector(objectproperty+" tbody tr:nth-child("+rownum+") td:nth-child("+columnnumtogetvalue+")")).getText();
							//System.out.println("useraction values for admin is :"+correspondingcolumn);
							setlistvalues(insertIndRowColumn);
						
						rownum++;
					}*/
				}
				
			}
			
			if(getlistvalues().size()<1){
				err1="******** Error occoured in test step *********: Table - getrespesctivecolumnvalues ,"
						+ "coloumn is not matched" ;
				System.out.println(err1);
				throw new Exception(err1);
			}
			System.out.println(arraylist.toString());
			
		}catch(Exception e){
			err1 = e.getMessage();
			System.out.println(err1);
		}
		
			return err1;
		}
		
		
		
		/**
		 * 
		 * @param objectsString
		 * @param searchValues
		 * @param dynamicVariables
		 * @return err1
		 * @description to search the column values with or values
		 * @throws Exception
		 */
		// test data = columnname = value1 || value2 or columnname = value1
		public String doActionwithtwovalues(String objectproperty,String testdata) throws Exception {

			try{
				err1 ="No";
			ArrayList<String> arr = new ArrayList<>();		
			String[] testdataarray =null;
			String[] testdatainrowarray =null;
			String valuetosearch1 = null;
			String valuetosearch2 = null;
			int colnum=1;
			int rownum=1;
			int columnnumtogetkey =0;
			int columnnumtogetvalue=0;
			
			try{
				if(!testdata.contains("=")){
					err1="******** Error occoured in test step *********: Table - searchrowdatawithcolumnheader ,"
							+ "Testdata should have '=' 'column name = value (sample format is status = pass||onhold or status = pass)" ;
					throw new Exception(err1);
				}/*else if(!testdata.contains(",")) {
					err1="******** Error occoured in test step *********: Table - getrespesctivecolumnvalues ,"
							+ "Testdata should be seperated by , 'column name from which key is taken and respesctivecolumn from which values to be stored'" ;
					throw new Exception(err1);
				}*/
			}catch(Exception e){
				throw e;
			}
			if(testdata.contains("=")){
				testdataarray = testdata.split("=");  // split by ,  and store the values [column names] 
				if(testdataarray[1].contains("||")){
					String[] valarr = testdataarray[1].split("||");
					valuetosearch1 = valarr[0];
					valuetosearch2 = valarr[1];
				}else{
					valuetosearch1 = testdataarray[1];  // split by = and store the values [column names and values]
				}
				}
			
			
			WebElement table = driver.findElement(By.cssSelector(objectproperty));
			
			List<WebElement> columnlist = table.findElements(By.tagName("th"));
			String steppass =null;
			String steppass1 =null;
			for (WebElement collist : columnlist) {
				System.out.println("header :"+collist.getText().trim());
				if(collist.getText().equalsIgnoreCase(testdataarray[0])){
					columnnumtogetvalue = colnum;
					steppass = "column matched";
					//System.out.println("inside loop columnnumtogetvalue:"+columnnumtogetvalue);
				}
				/*if(collist.getText().equalsIgnoreCase(testdatainrowarray[0])){
					columnnumtogetkey = colnum;
					steppass1 = "column matched";
				//	System.out.println("inside loop columnnumtogetkey:"+columnnumtogetkey);
				}*/
				colnum++;
			}
			
			/*if(steppass1==null||steppass==null)*/
			if(steppass==null){
				err1="******** Error occoured in test step *********: Table - searchrowdatawithcolumnheader ,"
						+ "column in the application is not matched with the column given in testdata" ;
				throw new Exception(err1);
			}
			for (WebElement collist : columnlist) {
				if(collist.getText().equalsIgnoreCase(testdataarray[0])){
					List<WebElement> allrowvalueofacolumn = null;
					
					try{
						allrowvalueofacolumn = table.findElements(By.cssSelector(objectproperty+" tbody tr td:nth-child("+columnnumtogetkey+")"));
					}catch(Exception e){
						err1="******** Error occoured in test step *********: Table - searchrowdatawithcolumnheader ,"
								+ "cant get all the values of a column and store in the list "+Thread.currentThread().getStackTrace()[1].getLineNumber() ;
						System.out.println(err1);
						throw new Exception(err1);
					}
					
					for (WebElement indvijualrowvalueofacolumn : allrowvalueofacolumn) {
						//System.out.println("roles :"+indvijualrowvalueofacolumn.getText());
						if(!testdata.contains("||")){
						if(indvijualrowvalueofacolumn.getText().equalsIgnoreCase(valuetosearch1)){
							logMessage = "At test Step - Keyword = "+keyword+", Action = "+ "searchrowdatawithcolumnheader"
									+valuetosearch1 + "is present in table in the column name "+collist.getText() ;
							System.out.println(logMessage); // Print on Console
							//logging
							logger.info(logMessage);
							break;
						}else{
							err1="******** Error occoured in test step *********: Table - searchrowdatawithcolumnheader ,"
									+valuetosearch1 + "is not present in table in the column name "+collist.getText() +Thread.currentThread().getStackTrace()[1].getLineNumber() ;
							System.out.println(err1);
							
						}
						rownum++;
						}else{
							if(indvijualrowvalueofacolumn.getText().equalsIgnoreCase(valuetosearch1)){
								logMessage = "At test Step - Keyword = "+keyword+", Action = "+ "searchrowdatawithcolumnheader"
										+valuetosearch1 + "is present in table in the column name "+collist.getText() ;
								System.out.println(logMessage); // Print on Console
								//logging
								logger.info(logMessage);
							}else{
								err1="******** Error occoured in test step *********: Table - searchrowdatawithcolumnheader ,"
										+valuetosearch1 + "is not present in table in the column name "+collist.getText() +Thread.currentThread().getStackTrace()[1].getLineNumber() ;
								System.out.println(err1);
								
							}
							if(indvijualrowvalueofacolumn.getText().equalsIgnoreCase(valuetosearch2)){
								logMessage = "At test Step - Keyword = "+keyword+", Action = "+ "searchrowdatawithcolumnheader"
										+valuetosearch1 + "is present in table in the column name "+collist.getText() ;
								System.out.println(logMessage); // Print on Console
								//logging
								logger.info(logMessage);
							}else{
								err1="******** Error occoured in test step *********: Table - searchrowdatawithcolumnheader ,"
										+valuetosearch2 + "is not present in table in the column name "+collist.getText() +Thread.currentThread().getStackTrace()[1].getLineNumber() ;
								System.out.println(err1);
								
							}
						}
					}
				}
				
			}
			
		}catch(Exception e){
			err1 = e.getMessage();
		}
		
			return err1;
		
		}
		
		
		public void setlistvalues(String valuetoaddinarraylist){
			
			if(!arraylist.contains(valuetoaddinarraylist)){
			//	System.out.println(valuetoaddinarraylist);
			arraylist.add(valuetoaddinarraylist);
				if (valuetoaddinarraylist.contains("/")){
					Collections.sort(arraylist, new Comparator<String>() {
			            DateFormat f = new SimpleDateFormat("MM/dd/yyyy");
			            @Override
			            public int compare(String o1, String o2) {
			                try {
			                    return f.parse(o1).compareTo(f.parse(o2));
			                } catch (java.text.ParseException e) {
			                    throw new IllegalArgumentException(e);
			                }
			            }
			        });
				}
			}
		}
		
		public ArrayList<String> getlistvalues(){
			
			return arraylist;
					
		}
		
		public void clearlist(){
			arraylist.clear();
		}
		
		/**
		 * Table - Keyword & search - Action
		 * @method search
		 * @description - search action to search specified contents in Table.
		 * 
		 * @throws Exception
		 */
		public String searchincolumn(String objectsString,String searchValues) throws Exception {
			try{
				err1 = "No";
				String searchPass = null;
				String searchFail = null;
				String eachRowData = null;
				boolean flag=false;
				try{
					if(objectsString==null||searchValues==null){
						String error = "please check the object property and searchvales , it should not be null : Table.java -> "
					+Thread.currentThread().getStackTrace()[1].getLineNumber();
						throw new Exception(error);
					}
				}catch (Exception e) {
					throw new Exception(e);
				}
				try{
					if(!searchValues.contains("=")){
						String error = "please check the searchvales , it should have '=' in testdata, Keyword - > Table , Action - > Search works accordingly"
								+ " if no '=' in testdata change to other action or change test data: Table.java line number-> "
					+Thread.currentThread().getStackTrace()[1].getLineNumber();
						throw new Exception(error);
					}
				}catch (Exception e) {
					throw new Exception(e);
				}
				String[] searchArrayExpMultple = searchValues.split(","); // Creating Array for Expected Key=Value pair
				String[] searchArrayExpected = searchValues.split("="); // Creating Array if Search Values are more than 1
				String[] pageNoArray = null;
				String searchExpectedKey = searchArrayExpected[0].trim(); // Getting Search Key
				String searchExpectedValue = searchArrayExpected[1].toLowerCase().trim(); // Search Value
				String tableObject = null;
				String[] objArray = null;
				By objByPageNo=null;
				if(objectsString.contains(",")){
					objArray = objectsString.split(","); // Array of Objects Properties, Table, Page No and Next Page Link
					objByPageNo = new WebElementProcessor(driver).isBy(driver, objArray[1]);
					tableObject=objArray[0];
				}else{
					tableObject=objectsString;
				}		
				String PageNo = null;
				int pageLoopNo = 0;
				boolean NameFlag=false;
				int colVerificaiton = 0;
				String[] NameSplit = null;
				int rowcnt;
				boolean headerTag=false;
				String skeyIndex = null;
				String[] skeyMulIndex = null; // Check for Seach Key Multiple searched
				int pageNos;
				String pageNo;
				boolean pageFlag=false;
				if (objByPageNo!=null){
					pageNo = driver.findElement(objByPageNo).getText(); // Get Next Page No
					pageNoArray = pageNo.split(" "); //Get page No
					pageNos=Integer.parseInt(pageNoArray[3]);
					pageFlag=true;
				}else{
					//err1 = " Unable to Extract PAGE Numbers - not present please check..... For Keyword  - "+keyword+", Action - "+Global.action ;
					pageNos=1;
					pageNo="Page Number : 1";
					//return err1;
				}
				//Iterating throgh all Pages to Seach Values
				for (int pageLoop=1;pageLoop<=pageNos;pageLoop++) {
					System.out.println("pageNo " +pageLoop); //Display Page No in Console 
					By objByTable = new WebElementProcessor(driver).isBy(driver, tableObject);
					if (objByTable!=null){
						WebElement table = driver.findElement(objByTable); 
						List<WebElement> allRows1 = table.findElements(By.tagName("tr")); //Get All rows in Table
						// Now get all the TR elements from the table 
						// And iterate over them, getting the cells
						rowcnt=1;
						for (WebElement eachRow : allRows1) { 
							List<WebElement> allRows = table.findElements(By.tagName("tr"));
							eachRowData = eachRow.getText().trim();
							System.out.println("Table row data " + rowcnt + " : " + eachRowData);
							//logging
							logger.info("Table row data " + rowcnt + " : " + eachRowData);
							if (searchArrayExpMultple.length<=1){
								if(headerTag==false){
									System.out.println("testing inside");// Header flag to get Column index for searching related values in columns
									headerTag=true;   //Ensure that if condition ran only for one time
									List<WebElement> eachCellHeader = eachRow.findElements(By.tagName("th"));
									
									for (int colheader=0;colheader<=(eachCellHeader.size()-1);colheader++){   //Looping throgh column header
										String cellHeaderName = eachCellHeader.get(colheader).getText().trim();
										if (searchExpectedKey.indexOf("_")>=0){ // This if condition only for First Name and Last Name
											NameFlag=true;
											NameSplit = searchExpectedKey.split("_");
											searchExpectedKey = NameSplit[0];
										}
										
										System.out.println("cellHeaderName "+ cellHeaderName);
										System.out.println("searchExpectedKey "+ searchExpectedKey);
										if (cellHeaderName.equalsIgnoreCase(searchExpectedKey)){
											colVerificaiton=colheader;  // Store column index when matches which Column we need to search
											System.out.println("colheader matched index value is "+colVerificaiton );
										}
									}
								}

								if(headerTag==true){ //Iterate for Searching Values after getting Index number of Column 
									List<WebElement> eachCellData = eachRow.findElements(By.tagName("td"));
									int noOfRowsReturned = eachCellData.size(); //Check Size of rows displyed
									if (noOfRowsReturned!=0){
										for (int eachCol=colVerificaiton;eachCol<=(noOfRowsReturned-1);eachCol++){ //Looping throgh all rows
											if (eachCol==colVerificaiton){
												String applCellValue = eachCellData.get(eachCol).getText().toLowerCase().toString();
												applCellValue=applCellValue.trim();
												
												if (NameFlag==true){ //This is only when seaching values for First and Last Name
													String[] appValueSplit = applCellValue.split(" ");
													if(appValueSplit.length>1){
													if (NameSplit[1].equalsIgnoreCase("first")){
														applCellValue = appValueSplit[0];
													}else{
														applCellValue = appValueSplit[1];
													}
													}
												}
												
												System.out.println("applCellValue :"+applCellValue);
												System.out.println("searchExpectedValue :"+searchExpectedValue);
												//System.out.println("App cell charecter ="+applCellValue.length());
												//System.out.println("Expected cell charecter ="+searchExpectedValue.length());
												//if (applCellValue.equalsIgnoreCase(searchExpectedValue)){ 
												if (applCellValue.contains(searchExpectedValue)){ 
															searchPass = "YES";
															flag=true;
															//break;
												}
												else{
													/*if(pageFlag==false && flag==false){
														searchFail = "YES";
														err1="Search Failed for value "+searchExpectedValue;
														System.out.println("Application Value : "+applCellValue+" , Expected Value : "+searchExpectedValue);
														//logging
														logger.info("Application Value : "+applCellValue+" , Expected Value : "+searchExpectedValue);
														//break;
													}*/
													
												}
											}
										}
									}
									
									rowcnt=rowcnt+1;
									
									
								}else{
									
									searchFail = "YES";
									err1="NO Results found for Search = " + searchExpectedValue;
									
								}
								if (searchPass!=null){
									break;
								}
								

							}else{ //else condition for Multiple Search

								if(headerTag==false){
									//Condition for getting Multiple Index numbers as need to search multiple columns in Table
									for (int mulSearch =0; mulSearch <= (searchArrayExpMultple.length-1);mulSearch++){
										searchArrayExpected = searchArrayExpMultple[mulSearch].split("="); //Get Array of Multiple Searches
										searchExpectedKey = searchArrayExpected[0].trim(); // Get Search Column
										searchExpectedValue = searchArrayExpected[1].trim(); // Expected Value
										List<WebElement> eachCellHeader = eachRow.findElements(By.tagName("th"));
										for (int colheader=0;colheader<=(eachCellHeader.size()-1);colheader++){ // this to get Indexes of Searcahble cplumns
											String cellHeaderName = eachCellHeader.get(colheader).getText().trim();
											if (searchExpectedKey.indexOf("_")>=0){
												NameFlag=true;
												NameSplit = searchExpectedKey.split("_");
												searchExpectedKey = NameSplit[0];
											}
											if (cellHeaderName.equals(searchExpectedKey)){
												if (skeyIndex==null){
													skeyIndex= Integer.toString(colheader);
												}else{
													skeyIndex=skeyIndex+","+Integer.toString(colheader);; // This variable will store all Indexes
												}
											}
										}
									}
								}
								if (headerTag==true){ // This condition for searching values after getting indexes of Columns
									searchPass = null;	
									searchFail = null;
									skeyMulIndex = skeyIndex.split(","); // Get each column indexes
									List<WebElement> eachCellData = eachRow.findElements(By.tagName("td"));
									int noOfRowsReturned = eachCellData.size(); // Get each Rows
									if (noOfRowsReturned!=0){
										for (int skey=0; skey <= skeyMulIndex.length-1;skey++){ //Iterate each row with each Column for searches
											searchArrayExpected = searchArrayExpMultple[skey].split("=");
											searchExpectedKey = searchArrayExpected[0].trim();
											searchExpectedValue = searchArrayExpected[1].trim();
											colVerificaiton=Integer.parseInt(skeyMulIndex[skey]); //Index for column verification
											for (int eachCol=colVerificaiton;eachCol<=(noOfRowsReturned-1);eachCol++){ // This loop to start from needed Index to rest
												if (eachCol==colVerificaiton){
													String applCellValue = eachCellData.get(eachCol).getText().trim();
													if (searchExpectedKey.toLowerCase().contains("name")){ // This is only for Name search, as First & last name are in only one column
														String[] appValueSplit = applCellValue.split(" ");
														if (NameSplit[1].equalsIgnoreCase("first")){
															applCellValue = appValueSplit[0];
														}else{
															applCellValue = appValueSplit[1];
														}
													}
													System.out.println("application value :"+applCellValue);
													if (applCellValue.contains(searchExpectedValue.trim())){
														searchPass = "YES";
													}
													else{
														searchFail = "YES";
														err1="Search Failed for value "+searchExpectedValue;
														break;
													}//End If or seachPass

												}//end If of Individual Column verification 

											}//end For loop of Individual Column verification 
										} //Closing For loop of each rows
										rowcnt=rowcnt+1;

									}else{
										searchFail = "YES";
										err1="NO Results found for Search = " + searchExpectedValue;
									}
									if (searchPass!=null){
									}
								}//System.out.println("CLOSING VALUE SEARCH");
								headerTag=true;
							}
						}
						if (searchPass!=null){ //Break if Search Fails...
							break;
						}else{
							if(pageFlag){
								
								By objByPageNextLink = new WebElementProcessor(driver).isBy(driver, objArray[2]); // Get Next page link
								if (objByPageNextLink!=null){
									driver.findElement(objByPageNextLink).click(); // Click if link present
								}else{
									System.out.println(" LINK TO NEXT PAGE - not present please check..... For Keyword - "+keyword+",  Action - "+ "search");
									//logging
									logger.info(" LINK TO NEXT PAGE - not present please check..... For Keyword - "+keyword+",  Action - "+ "search");
									//return err1;
								}
								
							}
						}
						if (searchPass==null && searchFail == null){ //when both are Null
							err1="NO Results found for  Search = " + searchExpectedValue;
						}else{
							logMessage = "At test Step - Keyword "+keyword+",  Action - "+ "search"+"  - . Locator : " + objByTable.toString();
							System.out.println(logMessage); // Print on Console
							//logging
							logger.info(logMessage);
							
							//Log.info(logMessage); //Print on Log
							//err1 = null;
						}
					}else{ //if any errors in getting Page Numbers
						err1="******** Error occoured in test step *********: "+keyword+",  Action - "+ "search"+" - not present please check..... Locator : " + objByTable.toString();
						return err1;
					}
				} // Ending Page Number loop

			if(flag==false)
			{
				err1="NO Results found for Search = " + searchExpectedValue;
			}
			} catch(Exception e) {
				System.out.println(e);
				err1="******** Error occoured in test step *********: "+keyword+",  Action - "+ "search"+" - not present please check..... Locator : " ;
				// System.out.println(err1); // Print on Console
				//Log.error(err1); //Print on Log
			}

			return err1; //Return Error Message
		}

}


