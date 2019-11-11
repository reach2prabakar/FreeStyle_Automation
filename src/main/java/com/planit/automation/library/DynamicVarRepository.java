package com.planit.automation.library;


import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;






import org.apache.commons.lang3.RandomStringUtils;
import org.apache.log4j.Logger;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;

import com.planit.automation.common.Common;

public class DynamicVarRepository {

	
	private static Logger logger = Logger.getLogger(DynamicVarRepository.class.getName());
	private Map<String, String> DynamicVarMap = new HashMap<String, String>();
	
	public DynamicVarRepository() throws Exception{
	
		UpdateDefaults();
	}
	
	
	private void UpdateDefaults() throws InvalidFormatException, IOException{
		try {
		DateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy");
		Date todayDate = new Date();
		addObjectToMap("#TodaysDate#", dateFormat.format(todayDate));
		String randomNumbers = RandomStringUtils.randomNumeric(7);
		addObjectToMap("#RANDOMNUMBER#", randomNumbers);
		}catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	
		

	public String getObjectProperty(String key){
		
		return DynamicVarMap.get(key);
		
	}
	
	public void addObjectToMap(String key, String value){
		if(key != null && !key.isEmpty())
			if(value !=null && !value.isEmpty())
				this.DynamicVarMap.put(key, value);
			else{
				System.out.println("WARNING : Value is either null or Empty");
				logger.info("WARNING : Value is either null or Empty");
			}
		else {
			System.out.println("WARNING : Key is either null or Empty");
			logger.info("WARNING : Key is either null or Empty");
		}

	}
	
	public Map<String, String> getDynamicVariablesMap(){
		
		return DynamicVarMap;
		
	}
	
}
