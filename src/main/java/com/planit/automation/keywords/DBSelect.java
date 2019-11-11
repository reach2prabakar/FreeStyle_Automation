package com.planit.automation.keywords;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import org.apache.log4j.Logger;

import com.planit.automation.library.DynamicVarRepository;
import com.planit.automation.library.Global;

/**
 * DB - Keyword
 * @class DB
 * @description - DB related all Actions are mentioned in this class
 * 
 * @throws Exception
 */
public class DBSelect{

	private static Logger logger = Logger.getLogger(DBSelect.class.getName());
	public String logMessage = null;
	private String keyword = "DBSelect";
	public String err1="No";
	//private String dbUrl = "jdbc:mysql://audi-app-rds-new.c0kpqzhq0mlp.us-east-1.rds.amazonaws.com/gg_aaa_auto";
	//private String dbUsername = "tomcat";
	//private String dbPassword = "passw0rd";
	//private String dbClass = "com.mysql.jdbc.Driver";
	private String dbUrl; 
	private String dbUsername;
	private String dbPassword;
	private String dbClass;
	private Connection con;


	public DBSelect(){

		setDbProperties();

	}


	private void setDbProperties(){
		this.dbUrl = "jdbc:mysql://"+ Global.getDbUrl();
		this.dbUsername = Global.getDbUsername();
		this.dbPassword = Global.getDbPassword();
		this.dbClass = Global.getDbClass();
	}


	/*
	/**
	 * DB - Keyword & connect - Action
	 * @method connect
	 * @description - connect action to connect to DB
	 * 
	 * @throws Exception
	 */
	/*
	public String connect() throws Exception {
		String email = null;
		err1="No";

		String dbUrl="jdbc:mysql://172.16.1.17:3306/enact_int";
		String username="mysqladmin"; //Default username is root
		String password="Valforma@2014"; //Default password is root
		String dbClass = "com.mysql.jdbc.Driver";
		String dbUrl=Global.dbUrl;
		String username=Global.dbUsername; //Default username is root
		String password=Global.dbPassword; //Default password is root
		String dbClass = Global.dbClass;
		String query = "SELECT count(*)FROM audit a";
		try{
			Class.forName(dbClass);
			Connection con = DriverManager.getConnection (dbUrl,username,password);
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while (rs.next()) 
			{
				int dbvalue = rs.getInt(1);
				System.out.println(dbvalue);
			} //end while
			con.close();
			//driver.findElement(DateName).click();  
			logMessage = "At test Step - Keyword = "+keyword+", Action = "+ "connect"+" , Locator : ";
			System.out.println(logMessage); // Print on Console
			//Log.info(logMessage); //Print on Log
		} catch(Exception e) {
			err1="******** Error occoured in test step *********: "+keyword+" is not present please check..... Locator : ";
			System.out.println(err1); // Print on Console
			//Log.error(err1); //Print on Log
		}

		return err1;
	}
	 */

	public void connectDB() throws Exception {

		try{

			Class.forName(dbClass);

			con = DriverManager.getConnection (dbUrl, dbUsername, dbPassword);

			logMessage = "Connected to database successfully ! Database : " + dbUrl;

			System.out.println(logMessage); // Print on Console
			logger.info(logMessage);

		} catch(Exception e) {
			err1="ERROR : Error occoured in while connecting database; Connection parameters DBUrl : " + dbUrl + 
					"; DBUserName : " + dbUsername + "; DBPassword : " + dbPassword +"; " + e.getMessage();
			logger.error(err1);
			throw new Exception (err1);

		}

	}

	public void disconnectDB() throws Exception {

		try{

			con.close();

			logMessage = "Database disconnected successfully ! Database : " + dbUrl;

			System.out.println(logMessage); // Print on Console
			logger.info(logMessage);

		} catch(Exception e) {
			err1="ERROR : Error occoured in while disconnecting database; Connection parameters DBUrl : " + dbUrl + ";" + e.getMessage();
			logger.error(err1);
			throw new Exception (err1 );

		}

	}

	
	/**
	 * DBSelect - Keyword & updatequery - Action
	 * @method updatequery
	 * @description - update value in database
	 * 
	 * @throws Exception
	 */
	public String updateQuery(String testData) throws Exception {

		err1="No";
		

		try{

		
			String query = getQuery(testData);

			connectDB();

			Statement stmt = con.createStatement();

			int count = stmt.executeUpdate(query);
            System.out.println("Updated queries: "+count);
			
			disconnectDB();

			if(count<=0){
				err1 = "ERROR :  At test Step - Keyword = " + keyword + ", Action = " + "updatequery" + " , Query Results = " + "Updated queries: "+count +"\n" +
						"Test Data : " + testData;
			}

			logMessage = "At test Step - Keyword = "+keyword+", Action = "+ "updatequery" +" , Query Results = " + "Updated queries: "+count ; // This is to Display on console as well in Log File
			System.out.println(logMessage); // Print on Console
			logger.info(logMessage);

		} catch(Exception e) {

			err1="ERROR : Excepton occoured while verifying query result; Keyword : " + keyword + ", Action : " + "updatequery ," +  "Test Data : " + testData + "." +
					e.getMessage();
			System.out.println(err1 ); 
			logger.error(err1);

		}

		return err1;
	}



	/**
	 * DBSelect - Keyword & verifyDBResults - Action
	 * @method verifyResults
	 * @description - verifyResults in database
	 * 
	 * @throws Exception
	 */
	public String verifyDBResult(String testData) throws Exception {

		err1="No";
		boolean valueFound = false;
		String expectedValueNotFound = null;

		try{

			List<String> resultsList = new ArrayList<String>();

			String query = getQuery(testData);

			String[] valuesToBeVerified = getValuesToBeVerified(testData).replaceAll("\\{|\\}", "").split(",");;

			connectDB();

			Statement stmt = con.createStatement();

			ResultSet rs = stmt.executeQuery(query);

			resultsList = getQueryResultsList(rs);

			disconnectDB();

			for (String valueToBeVerified : valuesToBeVerified){
				if(resultsList.contains(valueToBeVerified.trim())){
					valueFound = true;
				} else {
					valueFound = false;
					expectedValueNotFound = valueToBeVerified;
					break;
				}

			}

			if(!valueFound){
				err1 = "ERROR :  At test Step - Keyword = " + keyword + ", Action = " + "verifyDBResult" + " , Query Results = " + resultsList.toString() + ", Expected Value = "+ expectedValueNotFound +"\n" +
						"Test Data : " + testData;
			}


			logMessage = "At test Step - Keyword = "+keyword+", Action = "+ "verifyDBResult" +" , Query Results = " + resultsList.toString()  + " " + "Values verified :" + getValuesToBeVerified(testData); // This is to Display on console as well in Log File
			System.out.println(logMessage); // Print on Console
			logger.info(logMessage);

		} catch(Exception e) {

			err1="ERROR : Excepton occoured while verifying query result; Keyword : " + keyword + ", Action : " + "verifyDBResult ," +  "Test Data : " + testData + "." +
					e.getMessage();
			System.out.println(err1 ); 
			logger.error(err1);

		}

		return err1;
	}


	/**
	 * DBSelect - Keyword & getResultsFirstRow - Action
	 * @method getResultsFirstRow
	 * @description - getResultsFirstRow in database
	 * 
	 * @throws Exception
	 */
	public String getResultsFirstRow(String testData, DynamicVarRepository dynamicVariables) throws Exception {

		err1="No";
		String queryFirstRow = null;
	
		try{

			List<String> resultsList = new ArrayList<String>();

			String query = getQuery(testData);

			String dynamicParam = getDynamicParam(testData);

			connectDB();

			Statement stmt = con.createStatement();

			ResultSet rs = stmt.executeQuery(query);

			if(isResultsSetEmpty(rs)){
				err1 = "ERROR :  At test Step - Keyword = " + keyword + ", Action = " + "getQueryFirstRow" + " , Query Results = " + resultsList.toString() +
						"Test Data : " + testData;
			} else {
				
				resultsList = getQueryResultsList(rs);

				queryFirstRow = resultsList.get(0);
				
				dynamicVariables.addObjectToMap(dynamicParam, queryFirstRow);
				
				logMessage = "At test Step - Keyword = "+keyword+", Action = "+ "getQueryFirstRow" +" , Query Results = " + resultsList.toString() + "TestData : " + testData; 
				
				System.out.println(logMessage); // Print on Console
				logger.info(logMessage);
			}

			disconnectDB();
			
		} catch(Exception e) {

			err1="ERROR : Excepton occoured while verifying query result; Keyword : " + keyword + ", Action : " + "getQueryFirstRow ," +  "Test Data : " + testData + "." +
					e.getMessage();
			System.out.println(err1 ); 
			logger.error(err1);

		}

		return err1;
	}



	private List<String> getQueryResultsList(ResultSet resultSet) throws Exception {
		List<String> resultsList = new ArrayList<String>();

		if (isResultsSetEmpty(resultSet) ) {    
			logger.error("ERROR : No rows found with the given query");
			throw new Exception("ERROR : No rows found with the given query"); 

		} else {

			while (resultSet.next()) 		{
				Object dbvalue = resultSet.getObject(1);
				
				if(dbvalue != null){
					String dbStringValue = dbvalue.toString();
					resultsList.add(dbStringValue);
				}
				//String dbStringValue = dbvalue.toString();
				//resultsList.add(dbStringValue);
			}
		}

		return resultsList;
	}

	public ConcurrentHashMap<String, String> getQueryResultsMap(ResultSet resultSet) throws Exception {
		
		ConcurrentHashMap<String, String> AAACasesMap = new ConcurrentHashMap<String, String>();

		if (isResultsSetEmpty(resultSet) ) {    
			logger.error("ERROR : No rows found with the given query");
			throw new Exception("ERROR : No rows found with the given query"); 

		} else {

			while (resultSet.next()) 		{
				Object dbcaseNumber = resultSet.getObject(1);
				Object dbcaseStatus = resultSet.getObject(2);
					
				
				if(dbcaseNumber != null){
					String caseNumber = dbcaseNumber.toString();
					String caseStatus = dbcaseStatus.toString();
					AAACasesMap.put(caseNumber, caseStatus);
				}
			}
		}

		return AAACasesMap;
	}

	public ConcurrentHashMap<String, String[]> getQueryResults2DMap(ResultSet resultSet) throws Exception {
		
		ConcurrentHashMap<String, String[]> AAACasesMap = new ConcurrentHashMap<String, String[]>();

		if (isResultsSetEmpty(resultSet) ) {    
			logger.error("ERROR : No rows found with the given query");
			throw new Exception("ERROR : No rows found with the given query"); 

		} else {

			while (resultSet.next()) 		{
				Object dbcaseNumber = resultSet.getObject(1);
				Object dbcaseStatus = resultSet.getObject(2);
				Object dbFilerName = resultSet.getObject(3);
				
					
				
				if(dbcaseNumber != null){
					String caseNumber = dbcaseNumber.toString();
					String caseStatus = dbcaseStatus.toString();
					String filerName = dbFilerName.toString();
					
					AAACasesMap.put(caseNumber, new String[]{caseStatus, filerName});
				}
			}
		}

		return AAACasesMap;
	}

	private String getValuesToBeVerified(String testData) throws Exception {
		String valuesToBeVerified = "";
		if (testData.contains("$VERIFY")){

			String[] inputParams = testData.split(";");
			for (String param : inputParams){
				if(param.contains("$VERIFY")){
					valuesToBeVerified = param.replaceAll("\\$VERIFY=","").trim();
				}
			}

		} else {
			logger.error(" $VERIFY values to be verified not found .. " + testData);
			throw new Exception("$VERIFY values to be verified not found .. " + testData);
		}


		return valuesToBeVerified;
	}


	private String getQuery(String testData) throws Exception {
		String query = "";
		if (testData.contains("$QUERY")){

			String[] inputParams = testData.split(";");
			for (String param : inputParams){
				if(param.contains("$QUERY")){
					query = param.replaceAll("\\$QUERY=","").trim();
				}
			}

		} else {
			logger.error("$QUERY not found in the Test data.. " + testData);
			throw new Exception("$QUERY not found in the Test data.. " + testData);
		}


		return query;
	}

	private boolean isResultsSetEmpty(ResultSet resultSet) throws Exception {

		return !resultSet.isBeforeFirst()? true:false;
	
	}
	
	
	private String getDynamicParam(String testData) throws Exception {
		String dynamicParam = "";
		if (testData.contains("$VARIABLE")){

			String[] inputParams = testData.split(";");
			for (String param : inputParams){
				if(param.contains("$VARIABLE")){
					dynamicParam = param.replaceAll("\\$VARIABLE=","").trim();
				}
			}

		} else {
			logger.error("$VARIABLE parameter is not foiund in the Test data.. " + testData);
			throw new Exception("$VARIABLE parameter is not foiund in the Test data.. " + testData);
		}


		return dynamicParam;
	}



	public Connection getCon() {
		return con;
	}


	public void setCon(Connection con) {
		this.con = con;
	}



}
