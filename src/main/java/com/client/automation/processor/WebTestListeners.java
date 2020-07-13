package com.client.automation.processor;





import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.testng.IInvokedMethod;
import org.testng.IResultMap;
import org.testng.ISuite;
import org.testng.ISuiteListener;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestNGMethod;
import org.testng.ITestResult;


public class WebTestListeners
implements ITestListener,
ISuiteListener {
    public String sFilename;
    private static String screenshotPath;
    public static String testResultVideos;
    Logger log = Logger.getLogger("TestLogger.class");
    
    
    
    public static String getScreenshotPath() {
        return screenshotPath;
    }

     public void onTestFailedButWithinSuccessPercentage(ITestResult arg0) {
    }

    public void onFinish(ITestContext context) {
        this.removeIncorrectlyFailedTests(context);
        
    }

    public void onStart() {
    
    	 
        
    }

    public void onTestFailure(ITestResult result) {
    	
    }

    public String getCurrentDateTime(String format){
    	DateFormat dateFormat = new SimpleDateFormat(format);
    	Date date = new Date();
    	return dateFormat.format(date);
    }
    public void onTestSkipped(ITestResult iTestResult) {
    	
    }

    public void onTestStart(ITestResult iTestResult){
    	String sTestMethodName = iTestResult.getMethod().getMethodName();
        Object[] ob = iTestResult.getParameters();
        log.info(String.valueOf(ob.length));
        log.info(iTestResult.getMethod().getXmlTest().getName());
        log.info(iTestResult.getMethod().getXmlTest().getParameter("Browser"));
        String sTestSuiteName = iTestResult.getTestClass().getRealClass().getSimpleName();
        //deleteFile();
        log.info("=====================================================================================");
        log.info("<<<*** START: " + sTestSuiteName + "." + sTestMethodName + " ***>>> ");
        System.out.println(("<<<*** START: " + sTestSuiteName + "." + sTestMethodName + " ***>>> "));
       
        
    }
    
    public WebDriver getdriver(WebDriver driver) {
		return driver;
		
	}

	public void deleteFile(){
    	try{
    		String userdir = System.getProperty("user.dir");
    		String requestfolder = userdir+"\\"+"";
    		String responsefolder = userdir+"\\"+"";
    		
    		deletefolder(requestfolder);
    		deletefolder(responsefolder);
    		}catch(Exception e){
    		
    	}
    }
    
    private void deletefolder(String filepath) {
		File f = new File(filepath);
		if(f.exists()){
			System.out.println(filepath +" :Exist");
		try {
			FileUtils.deleteDirectory(f);
			System.out.println("XML file deleted");
		} catch (IOException e) {
			System.out.println("Error in deleting the XML file :"+filepath );
		}
		}else{
			System.out.println("No Request/response XML file exist");
		}
			
	}

	public void onTestSuccess(ITestResult iTestResult) {
        String sTestMethodName = iTestResult.getMethod().getMethodName();
        String sTestSuiteName = iTestResult.getTestClass().getRealClass().getSimpleName();
        String timeTaken = Long.toString((iTestResult.getEndMillis() - iTestResult.getStartMillis()) / 1000);
        String testName = iTestResult.getTestClass().getName() + "." + iTestResult.getMethod().getMethodName();
        log.info("<<<*** END: " + sTestSuiteName + "." + sTestMethodName + " ***>>> ");
        log.info("=====================================================================================");
        log.info("Test Passed :" + testName + ", Took " + timeTaken + " seconds");
        System.out.println("<<<*** END: " + sTestSuiteName + "." + sTestMethodName + " ***>>> ");
        System.out.println("Test Passed :" + testName + ", Took " + timeTaken + " seconds");
       
    }
 
   
    private IResultMap removeIncorrectlyFailedTests(ITestContext test) {
        ArrayList<ITestNGMethod> failsToRemove = new ArrayList<ITestNGMethod>();
        IResultMap returnValue = test.getFailedTests();
        block0 : for (ITestResult result : test.getFailedTests().getAllResults()) {
            long failedResultTime = result.getEndMillis();
            for (ITestResult resultToCheck2 : test.getFailedButWithinSuccessPercentageTests().getAllResults()) {
                if (failedResultTime != resultToCheck2.getEndMillis()) continue;
                failsToRemove.add(resultToCheck2.getMethod());
                break;
            }
            for (ITestResult resultToCheck2 : test.getPassedTests().getAllResults()) {
                if (failedResultTime != resultToCheck2.getEndMillis()) continue;
                failsToRemove.add(resultToCheck2.getMethod());
                continue block0;
            }
        }
        for (ITestNGMethod method : failsToRemove) {
            returnValue.removeResult(method);
        }
        return returnValue;
    }

    /*private String getAnnotationId(Method m) {
        Annotation[] annotation;
        for (Annotation a : annotation = m.getAnnotations()) {
            if (!(a instanceof TC)) continue;
            TC testCaseAnnotation = (TC)a;
            return testCaseAnnotation.id();
        }
        return null;
    }*/

    public void beforeInvocation(IInvokedMethod arg0, ITestResult arg1) {
    }

    public void onFinish(ISuite arg0) {
    	
    }

    public void onStart(ISuite arg0) {
        File folder = new File(arg0.getOutputDirectory());
        File target = new File(folder.getParent() + File.separator + "html" + File.separator + "screenshots");
        if (!target.exists()) {
            target.mkdirs();
        }
        screenshotPath = target.getPath();
    }

    static {
        testResultVideos = "";
    }

	@Override
	public void onStart(ITestContext context) {
		// TODO Auto-generated method stub
		
	}
}

