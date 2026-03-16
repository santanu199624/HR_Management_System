//package com.listeners;
//
//import org.testng.ITestContext;
//import org.testng.ITestListener;
//import org.testng.ITestResult;
//
//import com.baseclass.Base_Class;
//import com.utilities.ExtentManager;
//
//public class TestListener implements ITestListener {
//
//	// Trigger when test start
//	@Override
//	public void onTestStart(ITestResult result) {
//		String testName = result.getMethod().getMethodName();
//		// Start logging in Extent Reports
//		ExtentManager.startTest(testName);
//		ExtentManager.logStep("Test started: "+testName);
//	}
//
//	// Triggered when a Test succeeds
//	@Override
//	public void onTestSuccess(ITestResult result) {
//		String testName = result.getMethod().getMethodName();
//		ExtentManager.logStepWithScreenshot(Base_Class.getDriver(), "Test passed successfully!", "Test End: " + testName+ " - Test Passed");
//	}
//
//	//Triggered when a Test failed
//	@Override
//	public void onTestFailure(ITestResult result) {
//		String testName = result.getMethod().getMethodName();
//		Throwable throwable = result.getThrowable();
////		String failureMessage = result.getThrowable().getMessage();
//		ExtentManager.logStep(throwable.toString());
//		ExtentManager.logFailure(Base_Class.getDriver(), "Test Failed!", "Failure Screenshot -: " + testName);
//	}
//
//	//Triggered when a Test skipped
//	@Override
//	public void onTestSkipped(ITestResult result) {
//		String testName = result.getMethod().getMethodName();
//		ExtentManager.logSkipp("Test Skipped :"+testName);
//	}
//
//	// Triggered when suite starts
//	@Override
//	public void onStart(ITestContext context) {
//		// Initialize the extent report
//		ExtentManager.getReporter();
//	}
//
//	// Triggered when the suite ends
//	@Override
//	public void onFinish(ITestContext context) {
//		// Flush the extent reports
//		ExtentManager.endTest();
//	}
//
//}
