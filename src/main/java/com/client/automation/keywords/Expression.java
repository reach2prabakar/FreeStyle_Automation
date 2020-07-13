package com.client.automation.keywords;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


import com.client.automation.library.DynamicVarRepository;
import org.apache.commons.jexl2.JexlContext;
import org.apache.commons.jexl2.JexlEngine;
import org.apache.commons.jexl2.MapContext;
import org.apache.log4j.Logger;



/**
 * Button - Keyword
 * @class Button
 * @description - Button related all Actions are mentioned in this class
 * 
 * @throws Exception
 */
public class Expression{
	
	private static Logger logger = Logger.getLogger(Expression.class.getName());
	private String logMessage = null;
	private String keyword = "Expression";
	private String err1="No";
	private org.apache.commons.jexl2.Expression expression;

	public Expression(){
		
	}
	
	/**
	 * Expression - Keyword & math - Action
	 * @method click
	 * @description - resolve the expression. 
	 * 
	 * @throws Exception
	 */
	public String math(String inputData, DynamicVarRepository dynamicVariables) throws Exception {
		err1="No";	
		try{		
		String variable;
		String exp;
		String expressionResult;
		String[] inputParams = inputData.split("=");
		if(inputParams.length == 2){
			variable = inputParams[0].trim();
			exp = inputParams[1].trim().replaceAll(" ","");
			JexlEngine jexl = new JexlEngine();
			expression = jexl.createExpression(exp);
			JexlContext jctx = new MapContext();
			expressionResult = expression.evaluate(jctx).toString();
			dynamicVariables.addObjectToMap(variable, expressionResult);

			System.out.println("text : " + expressionResult + "  stored in dynamic variable : " + variable);
			logger.info("text : " + expressionResult + "  stored in dynamic variable : " + variable);
			

			
			logMessage = "At test step - Keyword = "+keyword+", Action = "+ "math" +", test data -  " + inputData; // This is to Display on console as well in Log File
			System.out.println(logMessage); // Print on Console
			
			logger.info(logMessage);

		} else {
			
			throw new Exception("Test data is not in exepcted Expression format <variableName> = <Expression>  : " + inputData);
		}
		
		
		} catch(Exception e) {
			err1="Unable to resolve the expression.. please check.....  testdata : "+ inputData; //Preparing Error message when unable to launch application
			System.out.println(err1 + e.getMessage()); // Print on Console //Print on Console
			
			logger.error(err1 + e.getMessage());
			//Log.error(err1); //Print on Log //Print on Log
		}
		return err1;
	}


	/**
	 * Expression - Keyword & string - Action
	 * @method click
	 * @description - resolve the expression. 
	 * 
	 * @throws Exception
	 */
	public String string(String inputData, DynamicVarRepository dynamicVariables) throws Exception {
		err1="No";	
		try{		
		String variable;
		String exp;
		String expressionResult;
		String[] inputParams = inputData.split("=");
		if(inputParams.length == 2){
			variable = inputParams[0].trim();
			exp = inputParams[1].trim();
			JexlEngine jexl = new JexlEngine();
			expression = jexl.createExpression(exp);
			JexlContext jctx = new MapContext();
			expressionResult = expression.evaluate(jctx).toString();
			dynamicVariables.addObjectToMap(variable, expressionResult);

			System.out.println("text : " + expressionResult + "  stored in dynamic variable : " + variable);
			logger.info("text : " + expressionResult + "  stored in dynamic variable : " + variable);
			

			
			logMessage = "At test step - Keyword = "+keyword+", Action = "+ "string" +", test data -  " + inputData; // This is to Display on console as well in Log File
			System.out.println(logMessage); // Print on Console
			
			logger.info(logMessage);

		} else {
			
			throw new Exception("Test data is not in exepcted Expression format <variableName> = <Expression>  : " + inputData);
		}
		
		
		} catch(Exception e) {
			err1="Unable to resolve the expression.. please check.....  testdata : "+ inputData; //Preparing Error message when unable to launch application
			System.out.println(err1 + e.getMessage()); // Print on Console //Print on Console
			
			logger.error(err1 + e.getMessage());
			//Log.error(err1); //Print on Log //Print on Log
		}
		return err1;
	}

	/**
	 * Expression - Keyword & string - date
	 * @method date
	 * @description - resolve the dateExpression. 
	 * 
	 * @throws Exception
	 * @author prabha
	 * @since 17/12/2015
	 * @value="#DYNAMICVARIAB# = ANYDATE ANYFORMAT(30-OCT-2015),30(DAYS TO ADD) ~ DATAFORMAT TO CHANGE(MM/DD/YYYY)
	 *  OR #DYNAMICVARIAB# = 30-OCT-2015,30N ~ MM/DD/YYYY
	 *  OR #DYNAMICVARIAB# = 30-OCT-2015 ~ MM/DD/YYYY
	 */
	public String date(String inputData,DynamicVarRepository dynamicVariables){
		String err1= "no";
		String logMessage ;
		try{
			err1="No";
			String[] datearr;
			String[] tovalidatearr;
			String tovalidate;
			String daystoaddvalue = null;
			int daystoadd = 0 ;
			String format ;
			String variable;
			String datedata;
			boolean adddaystocalender = false;
			boolean networkingdays = false;
			boolean weekend=false;
			String[] inputParams = inputData.split("=");
			if(inputParams.length == 2){
				variable = inputParams[0];
				datedata = inputParams[1];
				if(datedata.contains("~")){
					System.out.println(datedata);
					datearr = datedata.split("~");
					}else{
					System.out.println("Test data should be in this format <variableName> = <Expression~dateformat> :" + variable + "=" + "ur date ~ needed dateformat");
					throw new Exception("Test data is not in exepcted Expression format <variableName> = <Expression,dateformat || Expression.dateformat>  : " + inputData);
					
				}
				if(datearr[0].contains(",")){
			tovalidatearr = datearr[0].split(",");
			tovalidate = tovalidatearr[0];
			daystoaddvalue = tovalidatearr[1];
			adddaystocalender = true;
			if(daystoaddvalue.contains("n")||daystoaddvalue.contains("N")){
				networkingdays = true;
				String replacevalue = daystoaddvalue.replaceAll("[a-z,A-Z]","");
				daystoadd = Integer.parseInt(replacevalue.trim());
			}
			else if(daystoaddvalue.contains("w")||daystoaddvalue.contains("W")){
				System.out.println("weekend day is : "+daystoaddvalue);
				weekend=true;
				daystoadd = Integer.parseInt(daystoaddvalue.replaceAll("[a-z,A-Z]",""));
				//networkingdays = true;
				//daystoadd = Integer.parseInt(daystoaddvalue.replaceAll("[a-z,A-Z]",""));
			}
			else{
				daystoadd = Integer.parseInt(daystoaddvalue);
			}
				}else{
					tovalidate = datearr[0];
				}
			format = datearr[1];
			if(tovalidate.contains("-")){	
				tovalidate= tovalidate.replaceAll("-","/");
				}
			
			if(Character.isDigit(tovalidate.charAt(0))){
				int firstnum = Integer.parseInt(tovalidate.substring(0, 2));
				if(firstnum>12&&!(Character.isDigit(tovalidate.charAt(2)))){
					String month = tovalidate.substring(3, 5);
					tovalidate = tovalidate.replaceFirst(String.valueOf(firstnum), month);
					int length = tovalidate.length();
					tovalidate = tovalidate.substring(0,3).concat(String.valueOf(firstnum)).concat(tovalidate.substring(5,length));
					tovalidate=tovalidate.trim();
				}
			}
			
			if(format.contains("m")){
				format = format.replaceAll("m", "M");
			}
			System.out.println(tovalidate);
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");
			Date str_date = formatter.parse(tovalidate);
			System.out.println("date is : "+str_date);
			String Date = null;
			
			if(adddaystocalender){
				Date dateupdated ;
				if(networkingdays){
					Calendar calender = Calendar.getInstance(); // starts with today's date and time
					calender.setTime(str_date); 
					for(int i=0;i<daystoadd;i++){
					addingdaytocalender(calender);
					}
					dateupdated = calender.getTime(); 
					System.out.println(dateupdated);
				}else{
				Calendar calender = Calendar.getInstance(); // starts with today's date and time
				calender.setTime(str_date); 
				calender.add(Calendar.DAY_OF_YEAR, daystoadd);
				
				if(weekend){
					System.out.println("entered into weekend condition");
				if(calender.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY){
					calender.add(Calendar.DAY_OF_YEAR, 1);
					System.out.println("add weekend time :"+calender.getTime());
					//calender.get(calender.DAY_OF_WEEK,1);
						
				}//second if
				else if(calender.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY){
					calender.add(Calendar.DAY_OF_YEAR, 2);
					System.out.println("add weekend time :"+calender.getTime());
					//calender.get(calender.DAY_OF_WEEK,1);
						
				}//second if
				}//first if
				
				dateupdated = calender.getTime(); 
				System.out.println("afteradding days , now new format date is :"+dateupdated);
				}
				Date = new SimpleDateFormat(format).format(dateupdated);
				System.out.println(Date);
				dynamicVariables.addObjectToMap(variable, Date);
				
			}else{
				Date = new SimpleDateFormat(format).format(str_date);
				System.out.println(Date);
			dynamicVariables.addObjectToMap(variable, Date);
			}
			
			logMessage = "At test step - Keyword = "+keyword+", Action = "+ "Date" +", test data -  " + inputData +" is Stored in dynamicrepository as KEY="+variable+" value="+Date; // This is to Display on console as well in Log File
			System.out.println(logMessage); // Print on Console
			
			logger.info(logMessage);
			
			} else {
				System.out.println("Test data should be in this format <variableName> = <Expression~dateformat> :" );
				System.out.println("#DYNAMICVARNAME#" + "=" + "ur date ~ needed dateformat");
				throw new Exception("Test data is not in exepcted Expression format <variableName> = <Expression>  : " + inputData);
			}
				return err1;
			}
			catch(Exception e) {
			e.printStackTrace();
			err1="Exception Occured At test Step - Keyword = "+keyword+" Action= Date Exception is - "+ e.toString();
			System.out.println(err1); // Print on Console
			//Log.error(err1); //Print on Log
		}
		return err1;

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
	
	/**
	 * Expression - Keyword & string - Action
	 * @method click
	 * @description - resolve the expression. 
	 * 
	 * @throws Exception
	 */
	public String logic(String inputData) throws Exception {
		err1="No";	
		try{		
		String exp;
		String expressionResult;
		boolean result;
			exp = inputData.trim();
			JexlEngine jexl = new JexlEngine();
			expression = jexl.createExpression(exp);
			JexlContext jctx = new MapContext();
			expressionResult = expression.evaluate(jctx).toString();
			result = Boolean.valueOf(expressionResult);
			if(!result){
		
				err1="Logical expression returns false Hence failing the test case.  expression : "+ inputData; //Preparing Error message when unable to launch application
				
			}
			
			logMessage = "At test step - Keyword = "+keyword+", Action = "+ "logic" +", test data -  " + inputData; // This is to Display on console as well in Log File
			System.out.println(logMessage); // Print on Console
			
			logger.info(logMessage);
			
		
		} catch(Exception e) {
			err1="Unable to resolve the expression.. please check.....  testdata : "+ inputData; //Preparing Error message when unable to launch application
			System.out.println(err1 + e.getMessage()); // Print on Console //Print on Console
			
			logger.error(err1 + e.getMessage());
			//Log.error(err1); //Print on Log //Print on Log
		}
		return err1;
	}
	
	
	
}
