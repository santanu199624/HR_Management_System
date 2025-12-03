package com.utilities;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.maven.surefire.shared.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

public class ExtentManager {
	
	private static ExtentReports extent;
	private static ThreadLocal<ExtentTest> test = new ThreadLocal<>();
	private static Map<Long, WebDriver> driverMap = new HashMap<>();
	
	// Initialize Extent reports
	
	public static ExtentReports getReporter() {
		if(extent == null) {
			String reportPath = System.getProperty("user.dir")+"/src/test/resources/ExtentReport/ExtentReport.html";
			ExtentSparkReporter spark = new ExtentSparkReporter(reportPath);
			spark.config().setReportName("Automation test report");
			spark.config().setDocumentTitle("Report");
			spark.config().setTheme(Theme.DARK);
			
			extent = new ExtentReports();
			extent.attachReporter(spark);
			// Adding system information
			extent.setSystemInfo("Operating System", System.getProperty("os.name"));
			extent.setSystemInfo("Java Version", System.getProperty("java.version"));
			extent.setSystemInfo("User Name", System.getProperty("user.name"));
		}
		return extent;
		
	}
	
	// Start the test
	public static ExtentTest startTest(String testName) {
		ExtentTest extentTest = getReporter().createTest(testName);
		test.set(extentTest);
		return extentTest;
	}
	
	// End of test
	public static void endTest() {
		getReporter().flush();
	}
	
	// Get current thread's test
	public static ExtentTest getTest() {
		return test.get();
	}
	
	// Method to get the current test
	public static String getTestName() {
		ExtentTest currentTest = getTest();
		if(currentTest != null) {
			return currentTest.getModel().getName();
		}else {
			return "No test is currently active for this thread!";
		}
	}
	
	// Log a step
	public static void logStep(String logMessage) {
		getTest().info("logMessage");
	}
	
	// Log a step validation with screenshot
	public static void logStepWithScreenshot(WebDriver driver, String logMessage, String screenShotMessage){
		getTest().pass(logMessage);
		//screenshot method
		attachScreenShot(driver, screenShotMessage);
	}
	
	// Log failure
	public static void logFailure(WebDriver driver, String logMessage, String screenShotMessage) {
		String colorMessage = String.format("<span style='color:red;'>%s</span>", logMessage);
		getTest().fail(colorMessage);
		//screenshot method
		attachScreenShot(driver, screenShotMessage);
	}
	
	// log skipp
	public static void logSkipp(String logMessage) {
		String colorMessage = String.format("<span style='color:orrange;'>%s</span>", logMessage);
		getTest().skip(colorMessage);
	}
	
	// Take a screenshot with date and time
	public static String takeScreenshot(WebDriver driver, String screenshotName) {
		TakesScreenshot ts = (TakesScreenshot) driver;
		File src = ts.getScreenshotAs(OutputType.FILE);
		// Format date and time
		String timeStamp = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss").format(new Date());
		
		// Saving the screenshot into a file
		String destPath = System.getProperty("user.dir")+"/src/test/resources/ExtentReport/screenshot"+screenshotName+"_"+timeStamp+".png";
		File path = new File(destPath);
		try {
			FileUtils.copyFile(src, path);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//Convert screenshot to Base64 fir embedding in the report
		String base64Format = convertBase64(src);
		return base64Format;
	}
	
	// Convert screenshot to base64Format
	public static String convertBase64(File screenShotFile) {
		String base64Format = "";
		// Read the file content into byte array
		
		try {
			byte[] fileContent = FileUtils.readFileToByteArray(screenShotFile);
			base64Format = Base64.getEncoder().encodeToString(fileContent);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// Convert the byte array to a base64 String
		return base64Format;
	}
	
	// Attach screnshot report using base64
	public static void attachScreenShot(WebDriver driver, String message) {
		try {
			String screenShotBase64 = takeScreenshot(driver, getTestName()); 
			getTest().info(message, com.aventstack.extentreports.MediaEntityBuilder.createScreenCaptureFromBase64String(screenShotBase64).build());
		} catch (Exception e) {
			getTest().fail("Failled to attach the screenshot :"+message);
			e.printStackTrace();
		}
		
	}
	
	// Register WebDriver for current thread
	public static void registerDriver(WebDriver driver) {
		driverMap.put(Thread.currentThread().getId(), driver);
	}

}
