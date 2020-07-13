package com.client.automation.keywords;

import org.openqa.selenium.WebDriver;

import com.client.automation.library.ContentRepository;


public class Globalvariable {
	public WebDriver driver;
	private String keyword = "Global";
	public String err1 = "No";

	public Globalvariable(WebDriver driver) {
		this.driver = driver;
	}

	/**
	 * 
	 * @description - to store the value in global map(content repo) and use that value across the test case 
	 * @param testdata = testcasename.#hashname#=value || testcasename.#hashname#
	 * @param contentRepo
	 * @return #hashname# is stored in content repo and can be used globally
	 */
	public String storeGlobal(String testdata, ContentRepository contentRepo) {
		String globalarray[] ;
		try{
			if(testdata.contains("=")){
				globalarray = testdata.split("=");
				contentRepo.addObjectToMap(globalarray[0],globalarray[1]);
				String errorchk = contentRepo.getObjectProperty(globalarray[0]);
				System.out.println(errorchk);
				if(!errorchk.equals(globalarray[1])){
					err1 = "At test step - Keyword = "+keyword+", Action = "+ "storeGlobal"+", Data Stored in - globalmap is " + errorchk + "data from testdata is :"+globalarray[1]; // This is to Display on console as well in Log File
					System.out.println(err1);
				}else if(errorchk.isEmpty()||errorchk==null){
					err1 = "At test step - Keyword = "+keyword+", Action = "+ "storeGlobal"+", Data Stored in - globalmap is " + errorchk + "data from testdata is :"+globalarray[1]+" : data stored in map is empty or null "; // This is to Display on console as well in Log File
					System.out.println(err1);
				}
			}else{
				String value = testdata.replaceAll("#","");
				value = value.substring(value.indexOf("#"),value.lastIndexOf("#")+1);
				contentRepo.addObjectToMap(testdata,value);
				String errorchk = contentRepo.getObjectProperty(testdata);
				System.out.println(errorchk);
				if(!errorchk.equals(testdata)){
					err1 = "At test step - Keyword = "+keyword+", Action = "+ "storeGlobal"+", Data Stored in - globalmap is " + errorchk + "data from testdata is :"+testdata; // This is to Display on console as well in Log File
					System.out.println(err1);
				}else if(errorchk.isEmpty()||errorchk==null){
					err1 = "At test step - Keyword = "+keyword+", Action = "+ "storeGlobal"+", Data Stored in - globalmap is " + errorchk + "data from testdata is :"+testdata+" : data stored in map is empty or null "; // This is to Display on console as well in Log File
					System.out.println(err1);
				}
			}
		}
		catch(Exception e){
			err1 = "At test step - Keyword = "+keyword+", Action = "+ "storeGlobal  - > error in storing the data in global hashmap "; // This is to Display on console as well in Log File
			System.out.println(err1);
		}
		return err1;
	}
}

