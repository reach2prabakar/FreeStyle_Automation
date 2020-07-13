package com.client.automation.processor;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Map;
import java.util.TreeMap;
import java.util.TreeSet;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import com.client.automation.common.Common;
import com.client.automation.library.Global;

public class TestResultHtmlReport {
	
	private String testResultsExcelReportFilePath;
	private TreeMap<String, Integer[]> resultsMap = new TreeMap<String, Integer[]>(); // <ModuleName, [passcount, failcount, total]>
	private int passCount = 0;
	private int failCount = 0;
	private FileOutputStream fos;
	private String cont = null;
	private Workbook resultWorkbook;
	private Sheet resultSheet;
	private Row row;
	private String suiteDuration;


	
	public TestResultHtmlReport(String testResultsExcelReportFilePath){
		this.testResultsExcelReportFilePath = testResultsExcelReportFilePath;
	}

	/**
	 * To Create HTML Report
	 * 
	 * @param className
	 * @param Result
	 * @throws Exception
	 */
	public void createHTMLReport(String suiteDuration, String constantTimeStamp, String suiteStart) throws Exception {
		
			this.suiteDuration = suiteDuration;
		
		cont = "<HTML><HEAD>"
				+"<script src=\"http://code.jquery.com/jquery-1.11.3.min.js\"></script>"
				+"<script src=\"http://code.jquery.com/jquery-latest.min.js\"></script>"
				+"<script src=\"http://tuts.lookahead.io/tablesorter/js/jquery.tablesorter.js\"></script>"
				+"<script type=\"text/javascript\">$(document).ready(function() { $(\"#summaryTable\").tablesorter( {sortList: [[0,0], [1,0]]} );   }); </script>"
				+ "<TITLE>" + Global.getClientName() + " Test Report</TITLE>"
				+ getStyleTag() + getScriptTag()
				+ "<META http-equiv=Content-Type content=text/html; charset=windows-1252><META content=MSHTML 6.00.2900.3268 name=GENERATOR></HEAD>"
				+ "<H2 align=center><U><FONT color=#3b7bbf><B>" + Global.getClientName() + " Test Execution Report - " + new Common().getTodayDate() + "</B></FONT> <U></H2><HR>"
				+ "<TABLE width=100% height=37 border=0 align=center>"
				+ "<TBODY>"
					+ "<TR>"
						+ "<TD height=19 <FONT color=black><B>Project Name :: </B></FONT><FONT color=black><B>"+ Global.getClientName() +"</B></FONT>"
						+ "<TD width=50% align=right><div align=left><FONT color=black><B>Date and Time of Execution : </B></FONT><FONT color=black><B>" + suiteStart + "</B></FONT></div></TD>"
					+ "</TR>"
					+ "<TR>"
						+ "<TD height=19 <FONT color=black><B>Environment :: </B></FONT><FONT color=black><B>" + Global.getEnvironment() + " (" + Global.getUrl() + ")" + "</B></FONT>"
						+ "<TD width=50% align=right><div align=left><FONT color=black><B></B></FONT><FONT color=black><B>"+ "</B></FONT></div></TD>"
						+ "</TR>"
					+ "<TR>"
						+ "<TD height=19 <FONT color=black><B>Browser :: </B></FONT><FONT color=black><B>" + Global.getBrowserAndVersion() + "</B></FONT>"
						+ "<TD width=50% align=right><div align=left><FONT color=black><B></B></FONT><FONT color=black><B>"+ "</B></FONT></div></TD>"
					+ "</TR>"
					+ "<TR>"
						+ "<TD height=19 <FONT color=black><B>Operating System :: </B></FONT><FONT color=black><B>" + Global.getOsName() + "</B></FONT>"
						+ "<TD width=50% align=right><div align=left><FONT color=black><B></B></FONT><FONT color=black><B>" + "</B></FONT></div></TD>"
					+ "</TR>"
					+ "</TBODY></TABLE>"
					+ "<BR>";
		File fi1 = new File(Global.resultsFolder+"\\Result_"
						+ Global.constantTimeStamp + "\\Execution_Report.html");
		fos = new FileOutputStream(fi1);
		fos.write(cont.getBytes());
		
		updateSummaryOfExecution();
		updateSummaryOfConsolidatedReport();
		updateModuleWiseSummaryInHtmlReport();
		fos.close();

	}

	private String getStyleTag() {
	    String styleTag = "<style>";
	 //   styleTag+= "body{font-family:Arial};";
	 //   styleTag+= "ul,li{margin-left:0;padding-left:0;width:100%;font-weight:bold;}";
	 //   styleTag+= "table{width:95%;text-align:left;border-spacing:0;border-collapse: separate;margin-bottom:5px;}";
	 //   styleTag+= "li{font-weight:bold;padding-left:5px;list-style:none;}";
	 //   styleTag+= "ul table li{font-weight: normal}";
	 //  styleTag+= "th,td{padding: 10px;border: 1px solid #000;}";
	 //   styleTag+= "td.desc-col{width:400px;}th.desc-col{width: 390px;}";
	 //   styleTag+= "td.status-col{width:75px;}th.status-col{width: 75px;}";
	 //   styleTag+= "td.browser-col{width:345px;}th.browser-col{width: 345px;}";
	 //   styleTag+= "td.os-col{width:100px;}th.os-col{width: 100px;}";
	 //   styleTag+= "td.msg-col{width:135px;}th.msg-col{width: 135px;}";
	 //   styleTag+= "table.header{background-color: gray; color: #fff;margin-left:20px;}";
	    styleTag+= ".traceinfo{position: fixed;top: 0; bottom: 0;left: 0;right:0;background: rgba(0,0,0,0.8);z-index: 99999;opacity:0;-webkit-transition: opacity 400ms ease-in;transition: opacity 400ms ease-in;pointer-events: none;}";
	    styleTag+= ".traceinfo.visible{opacity:1;pointer-events: auto;}";
	    styleTag+= ".traceinfo > div{width: 900px;position: relative;margin: 10% auto;padding: 5px 20px 13px 20px;background: #fff;}";
	    styleTag+= ".traceinfo .close{background: #606061;color: #FFFFFF;line-height: 25px;position: absolute;right: -12px;text-align: center;top: -10px;width: 24px;text-decoration: none;font-weight: bold;}";
	    styleTag+= ".traceinfo .close:hover{background: #00d9ff;}";
	    styleTag+= "</style>";
	    
	    return styleTag;
		
	}

	private String getScriptTag() {
		   String scrpTag = "<script type='text/javascript'>";
		    scrpTag +="function showTrace(e){";
		    scrpTag +="window.event.srcElement.parentElement.getElementsByClassName('traceinfo')[0].className = 'traceinfo visible';}";
		    scrpTag +="function closeTraceModal(e){";
		    scrpTag +="window.event.srcElement.parentElement.parentElement.className = 'traceinfo';}";
		    scrpTag +="function openModal(imageSource){";
		    scrpTag +="var myWindow = window.open('','screenshotWindow');";
		    scrpTag +="myWindow.document.write('<img src=\"' +imageSource + '\" alt=\"screenshot\" />');}";
		    scrpTag += "</script>";
			
		    return scrpTag;
	}

	/**
	 * get the Execution summary from the Excel report
	 * @method updateSummaryOfExecution()
	 * 
	 */
	public void updateSummaryOfExecution()	throws Exception {
		
		FileInputStream fileInputStream = new FileInputStream(testResultsExcelReportFilePath);
		resultWorkbook = WorkbookFactory.create(fileInputStream);
		resultSheet = resultWorkbook.getSheet("Result");
		int rowCnt = resultSheet.getLastRowNum();
		
		for (int rowIndex = 1; rowIndex <= rowCnt; rowIndex++) {
			
			row = resultSheet.getRow(rowIndex);
			
			String testCaseStatus = row.getCell(TestResultExcelReport.STATUS_INDEX).getRichStringCellValue().toString();
			String moduleName	= row.getCell(TestResultExcelReport.MODULE_NAME_INDEX).getRichStringCellValue().toString();
			
			if(resultsMap.get(moduleName) != null){
				if (testCaseStatus.equalsIgnoreCase("pass")){
					Integer[] resultArray = {resultsMap.get(moduleName)[0]+1,
												resultsMap.get(moduleName)[1],
												resultsMap.get(moduleName)[2]+1};
					resultsMap.put(moduleName, resultArray);
					passCount++;
				} else {
					Integer[] resultArray = {resultsMap.get(moduleName)[0],
							resultsMap.get(moduleName)[1]+1,
							resultsMap.get(moduleName)[2]+1};
						resultsMap.put(moduleName, resultArray);
						failCount++;

				}
			} else {
				if (testCaseStatus.equalsIgnoreCase("pass")){
					Integer[] resultArray = {1,0,1};
					resultsMap.put(moduleName, resultArray);
					passCount++;
				} else {
					Integer[] resultArray = {0,1,1};
					resultsMap.put(moduleName, resultArray);
					failCount++;
					
				}			
			}

		}

		updateSummaryInHtmlReport();
		//updateModuleWiseSummaryInHtmlReport();

	}
	
	private void updateSummaryInHtmlReport() throws Exception {

		cont = "<H3 align=center><FONT color=#648fca><B>Summary of Regression Test RUN </B></FONT></H3></TR>";
		fos.write(cont.getBytes());
		cont =	"<TABLE width=80% align=center border=1>"
				+ "<TBODY><TR bgColor=#648fca>"
				+ "<TD align=middle><B><FONT color=#ffffff>Total Testcases </FONT></B></TD>"
				+ "<TD align=middle><B><FONT color=#ffffff>No. of TC Passed </FONT></B></TD>"
				+ "<TD align=middle><B><FONT color=#ffffff>No. of TC Failed </FONT></B></TD>"
				+ "<TD align=middle><B><FONT color=#ffffff>Total Time Elapsed</FONT></B></TD>"
				+ "</TR>"
				+ "<TR bgColor=#d2d2d2>";
		fos.write(cont.getBytes());

		cont = "<TD align=middle><B>" + (passCount + failCount) + " </B></TD>";
		fos.write(cont.getBytes());
		cont = "<TD align=middle><B>" + passCount + " </B></TD>";
		fos.write(cont.getBytes());
		cont = "<TD align=middle><B><FONT color=#ff0066>" + failCount + "</FONT>";
		fos.write(cont.getBytes());
		cont = "<TD align=middle><B>" + suiteDuration + " </B></TD>";
		fos.write(cont.getBytes());

		cont = ""
				+ "</TBODY></TABLE>"
				+ "<BR>";
		fos.write(cont.getBytes());
		
		
	}


	private void updateModuleWiseSummaryInHtmlReport() throws Exception {

		cont	= "<H3 align=center><FONT color=#648fca><B>Summary of Modules Report </B></FONT></H3></TR>";
		fos.write(cont.getBytes());
		cont =	"<TABLE width=80% align=center border=1>"
				+ "<TBODY><TR bgColor=#648fca>"
				+ "<TD align=left><B><FONT color=#ffffff> Module Name </FONT></B></TD>"
				+ "<TD align=middle><B><FONT color=#ffffff> Passed </FONT></B></TD>"
				+ "<TD align=middle><B><FONT color=#ffffff> Failed </FONT></B></TD>"
				+ "<TD align=middle><B><FONT color=#ffffff> Total </FONT></B></TD>"
				+ "<TD align=middle><B><FONT color=#ffffff> %Passed</FONT></B></TD>"
				+ "</TR>";
		fos.write(cont.getBytes());

		Map<String, Integer[]> DynamicVarMap = resultsMap;
		int i=1;
		for (String key: new TreeSet<String>(resultsMap.keySet())) {
			if(i%2==0) {
				cont = "<TR bgColor=#ffffff>";
			}else {
				cont = "<TR bgColor=#d2d2d2>";
			}
			
			cont = 	"<TD align=left><B>" + key + " </B></TD>"
					+"<TD align=middle><B>" + resultsMap.get(key)[0] + " </B></TD>"
					+"<TD align=middle><B><FONT color=#ff0066>" + resultsMap.get(key)[1] + "</FONT>"
					+"<TD align=middle><B>" + resultsMap.get(key)[2] + " </B></TD>"
					+"<TD align=middle><B>" + ((resultsMap.get(key)[0]*100)/resultsMap.get(key)[2]) + "% </B></TD>"
					+"</TR>";
			fos.write(cont.getBytes());

			System.out.println(key + "=" + DynamicVarMap.get(key)[0].toString() + " "
		    								+ DynamicVarMap.get(key)[1].toString() + " "
		    								+ DynamicVarMap.get(key)[2].toString() + " ");
			i++;
		}


		cont = ""
				+ "</TBODY></TABLE>"
				+ "<BR>";
		fos.write(cont.getBytes());
		
		cont= "<BR><BR><BR><BR><BR><BR>"
				+ "<TABLE width=100% align=center bgColor=#7d7d7d border=0>"
				+ "<TBODY>"
				+ "<TR>"
				+ "<TD align=middle><FONT face=Verdana, Arial color=#ffffff size=1>Report Generated by "+Global.companyName+" Test Automation Framework </FONT></TD>"
				+ "<TD align=middle><FONT face=Verdana, Arial color=#ffffff size=1>@ "+Global.copyRightYear+ " "+Global.companyName+"</FONT></TD></TR></TBODY></TABLE></U></U></BODY></HTML>";
		
		fos.write(cont.getBytes());
		
		fos.close();
	}
	
	private void updateSummaryOfConsolidatedReport() throws Exception{
		//Heading
		cont	= "<H3 align=center><FONT color=#648fca><B>Summary of Test Case Report </B></FONT></H3></TR>"
				+ "<TR bgColor=#ffffdd>";
		fos.write(cont.getBytes());

		// Table Column headers
		cont = "<TABLE id='summaryTable' class='tablesorter' width=100% align=center border=1>"
				+ "<THEAD>"
				+ "<TR bgColor=#648fca>"
				+ "<TH><FONT color=#ffffff><B>SNo</B></FONT></TH>"
				+ "<TH><FONT color=#ffffff><B>ModuleName</B></FONT></TH>"
				+ "<TH><FONT color=#ffffff><B>TestCase Name</B></FONT></TH>"
				+ "<TH align=middle><FONT color=#ffffff><B>Status</B></FONT></TH>"
				+ "<TH align=middle><FONT color=#ffffff><B>Time Taken</B></FONT></TH>"
				+ "<TH align=middle><FONT color=#ffffff><B>Defect#</B></FONT></TH>"
				+ "<TH align=middle><FONT color=#ffffff><B>Remarks</B></FONT></TH>"
				+ "</THEAD><TBODY>";
		fos.write(cont.getBytes());

		// Read Test Results sheet
		int rowCnt = resultSheet.getLastRowNum();
	
		Cell cell;

		for (int rowIndex = 1; rowIndex <= rowCnt; rowIndex++) {
			row = resultSheet.getRow(rowIndex);
			cell = row.getCell(TestResultExcelReport.SNO_INDEX);
			String sno = cell.getRichStringCellValue().toString();
			cell = row.getCell(TestResultExcelReport.MODULE_NAME_INDEX);
			String moduleName = cell.getRichStringCellValue().toString();
			cell = row.getCell(TestResultExcelReport.TESTSCRIPT_NAME_INDEX);
			String testCasename = cell.getRichStringCellValue().toString();
			cell = row.getCell(TestResultExcelReport.STATUS_INDEX);
			String Sts = cell.getRichStringCellValue().toString();
			cell = row.getCell(TestResultExcelReport.TIME_TAKEN_INDEX);
			String timeTaken = cell.getRichStringCellValue().toString();
			cell = row.getCell(TestResultExcelReport.DEFECT_ID_INDEX);
			String defectid = cell.getRichStringCellValue().toString();
			cell = row.getCell(TestResultExcelReport.REMARKS_INDEX);
			String remarks = cell.getRichStringCellValue().toString();
			remarks = remarks.replaceAll("\n", "<br>");
			
			if(rowIndex%2==0) {
				cont = "<TR bgColor=#ffffff>";
			}else {
				cont = "<TR bgColor=#d2d2d2>";
			}
			
			fos.write(cont.getBytes());
			
			cont = "<TD>" + sno + "</TD>";
			fos.write(cont.getBytes());
			
			cont = "<TD>" + moduleName + "</TD>";
			fos.write(cont.getBytes());
			
			cont = "<TD>" + testCasename + "</TD>";
			fos.write(cont.getBytes());
			
			
			//String stackTraceInfo = Sts.equalsIgnoreCase("PASS")? "": "<br/><a onclick=\"showTrace()\" href=\"#trace-modal" + j +"\"> Error </a><br/> <div id=\"#trace-modal" + j +"\" class=\"traceinfo\"><div><a href=\"#close\" onclick=\"closeTraceModal()\" title=\"Close\" class=\"close\">X</a>" + Remarks + "</div></div>";
			String stackTraceInfo = Sts.equalsIgnoreCase("PASS")? "": "<a onclick=\"showTrace()\" href=\"#trace-modal" + rowIndex +"\"> Error </a> <div id=\"#trace-modal" + rowIndex +"\" class=\"traceinfo\"><div align=\"left\"><a href=\"#close\" onclick=\"closeTraceModal()\" title=\"Close\" class=\"close\">X</a>" + remarks + "</div></div>";
			String bgcolor = Sts.equalsIgnoreCase("PASS") ? "bgcolor=\"#90EE90\"" : "bgcolor=\"#FFB6C1\"";
			String fontcolor = Sts.equalsIgnoreCase("PASS") ? "<font color=\"DarkGreen\">" : "<font color=\"DarkRed\">";
		    //str +=  '<td ">' + data.message+ stackTraceInfo+ '</td>';
			//System.out.println(stackTraceInfo);
			String cont1 = "<TD align=middle " + bgcolor + ">" + fontcolor + Sts + "</TD>";
			String cont2 = "<TD align=middle>" + timeTaken + "</TD>";
			String cont3 = "<TD align=middle>" + stackTraceInfo + "</TD>";
			String cont4 = "<TD align=middle>" + defectid + "</TD>";
			//fos.write(cont.getBytes());
			fos.write(cont1.getBytes());
			fos.write(cont2.getBytes());
			fos.write(cont4.getBytes());
			fos.write(cont3.getBytes());
			
			
		}
		cont = "</B></TD></TR></TBODY></TABLE> <BR><BR>";
		
		
		
	
		
		
		
		fos.write(cont.getBytes());

	}


}
