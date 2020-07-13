package com.client.automation.library;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.commons.lang3.text.StrSubstitutor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

public class ObjectRepository {

	
	private Sheet obWsheet;
	private Workbook obWbook;
	private Map<String, String> objectMap = new HashMap<String, String>();
	private StrSubstitutor obRepoSubstitutor;
	
	public ObjectRepository(String filePath) throws Exception{
	
		ReadObjectRepository(filePath);
		initializeObjectRepository();
	}
	
	
	private void ReadObjectRepository(String filePath) throws Exception{
		try {
			FileInputStream fileInputStream = new FileInputStream(filePath);
			obWbook = WorkbookFactory.create(fileInputStream);
			obWsheet =  obWbook.getSheet("ObjectRepo");
			Iterator<Row> rowIterator = obWsheet.iterator();
			
			while (rowIterator.hasNext())
			{
				Row row = rowIterator.next();
				if(getStringCellValue(row.getCell(0)) != null && !getStringCellValue(row.getCell(0)).isEmpty()){
					String objectIdentifier = getStringCellValue(row.getCell(0)) + "." +
							getStringCellValue(row.getCell(1)) + "."+
							getStringCellValue(row.getCell(2));

					String objectProperty = getStringCellValue(row.getCell(3));

					addObjectToMap(objectIdentifier, objectProperty);
				}

			}
		} catch(FileNotFoundException e){
			System.out.println("WARNING : Object Repository file not found : " + filePath);
			System.out.println("WARNING : Ignoring the Object Repository file and Proceeding with execution");
		}

		
	}
	
	public void initializeObjectRepository() throws Exception {

		obRepoSubstitutor 					= new StrSubstitutor(getObjectRepoMap());
		obRepoSubstitutor.setEnableSubstitutionInVariables(true);

		
	}
	
	public StrSubstitutor getobRepoSubtitutor() {
		
		return obRepoSubstitutor;
	}

	public String getObjectProperty(String key){
		
		return objectMap.get(key);
		
	}
	
	private void addObjectToMap(String key, String value){
		
		this.objectMap.put(key, value);
		
	}
	
	public Map<String, String> getObjectRepoMap(){
		
		return objectMap;
		
	}
	
	private String getStringCellValue(Cell cell){

		String cellValue = null;
	 
		if (cell != null) {
			switch (cell.getCellType()) {

			case Cell.CELL_TYPE_STRING:
				cellValue = cell.getRichStringCellValue().getString();
				break;

			case Cell.CELL_TYPE_FORMULA:
				
				cellValue = String.valueOf(cell.getNumericCellValue());
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
	
}
