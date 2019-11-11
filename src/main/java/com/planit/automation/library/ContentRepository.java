package com.planit.automation.library;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.commons.lang3.text.StrSubstitutor;
import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

public class ContentRepository {

	private static Logger logger = Logger.getLogger(ContentRepository.class.getName());
	private Workbook contentWbook;
	private Sheet contentWsheet;
	private Sheet envVarWsheet;
	private StrSubstitutor contentRepoSubstitutor;
	private Map<String, String> contentMap = new HashMap<String, String>();



	public ContentRepository(String filepath) throws IOException, Exception{
		ReadObjectRepository(filepath);
		initializeContentRepository();
	}

	private void ReadObjectRepository(String filepath) throws Exception, IOException {
		try {
			FileInputStream fileInputStream = new FileInputStream(filepath);
			contentWbook = WorkbookFactory.create(fileInputStream);
			contentWsheet =  contentWbook.getSheet("ContentRepo");
			Iterator<Row> rowIterator = contentWsheet.iterator();

			while (rowIterator.hasNext())
			{
				Row row = rowIterator.next();
				if(getStringCellValue(row.getCell(0)) != null && !getStringCellValue(row.getCell(0)).isEmpty()){
					String objectIdentifier = getStringCellValue(row.getCell(0)) + "." +
							getStringCellValue(row.getCell(1));

					String objectProperty = getStringCellValue(row.getCell(2));

					addObjectToMap(objectIdentifier, objectProperty);
				}
			}


			envVarWsheet =  contentWbook.getSheet("EnvVariables");

			Iterator<Row> envVarIterator = envVarWsheet.iterator();

			while (envVarIterator.hasNext())
			{
				Row row = envVarIterator.next();

				if(getStringCellValue(row.getCell(0)) != null && !getStringCellValue(row.getCell(0)).isEmpty()){
					String environment = getStringCellValue(row.getCell(0));
					if(environment.equalsIgnoreCase(Global.environment) || environment.equalsIgnoreCase("all")){
						String objectIdentifier = getStringCellValue(row.getCell(1));
						String objectProperty = getStringCellValue(row.getCell(2));
						addObjectToMap(objectIdentifier, objectProperty);
					}
				}
			}

			if(!getObjectProperty("url").equals("url")){
				Global.setUrl(getObjectProperty("url"));
			}

		} catch(FileNotFoundException e){
			System.out.println("WARNING : Content Repository file not found : " + filepath);
			System.out.println("WARNING : Ignoring the Content Repository file and Proceeding with execution");
			logger.info("WARNING : Content Repository file not found : " + filepath);
			logger.info("WARNING : Ignoring the Content Repository file and Proceeding with execution");
		}

	}

	public void initializeContentRepository() throws IOException, Exception {

		contentRepoSubstitutor 				= new StrSubstitutor(getContentMap());
		contentRepoSubstitutor.setEnableSubstitutionInVariables(true);
		
	}
	
	
	public StrSubstitutor getcontentRepoSubtitutor() {
		
		return contentRepoSubstitutor;
	}
	
	public String getObjectProperty(String key){

		String value = contentMap.get(key);

		return value!=null ? value : key;
	}

	public void addObjectToMap(String key, String value){

		this.contentMap.put(key, value);

	}

	public Map<String, String> getContentMap(){

		return contentMap;

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
