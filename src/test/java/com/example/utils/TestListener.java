package com.example.utils;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;

public class TestListener implements ITestListener {
    private static final Logger logger = LogManager.getLogger(TestListener.class);

    @Override
    public void onStart(ITestContext context) {
        logger.info("Starting test suite: {}", context.getName());
        ExtentReportManager.initializeReport();
    }

    @Override
    public void onFinish(ITestContext context) {
        logger.info("Finished test suite: {}", context.getName());
        ExtentReportManager.flushReport();
    }

    @Override
    public void onTestStart(ITestResult result) {
        logger.info("Starting test: {}", result.getName());
        ExtentReportManager.createTest(result.getMethod().getMethodName());
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        logger.info("Test passed: {}", result.getName());
        ExtentReportManager.getTest().log(Status.PASS, 
            MarkupHelper.createLabel(result.getName() + " - Test Case Passed", ExtentColor.GREEN));
    }

    @Override
    public void onTestFailure(ITestResult result) {
        logger.error("Test failed: {} - {}", result.getName(), result.getThrowable());
        ExtentReportManager.getTest().log(Status.FAIL,
            MarkupHelper.createLabel(result.getName() + " - Test Case Failed", ExtentColor.RED));
        ExtentReportManager.getTest().fail(result.getThrowable());
        
        // Capture screenshot on failure
        String screenshotPath = ScreenshotUtils.captureScreenshot(result.getName());
        if (screenshotPath != null) {
            ExtentReportManager.getTest().addScreenCaptureFromPath(screenshotPath);
        }
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        logger.warn("Test skipped: {}", result.getName());
        ExtentReportManager.getTest().log(Status.SKIP,
            MarkupHelper.createLabel(result.getName() + " - Test Case Skipped", ExtentColor.YELLOW));
    }
}