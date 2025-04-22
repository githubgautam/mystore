package com.example.utils;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ExtentReportManager {
    private static ExtentReports extent;
    private static final ThreadLocal<ExtentTest> test = new ThreadLocal<>();
    private static final String REPORT_PATH = "test-output/ExtentReports/";
    private static final Logger logger = LogManager.getLogger(ExtentReportManager.class);

    public static void initializeReport() {
        if (extent == null) {
            extent = new ExtentReports();
            String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
            String reportFile = REPORT_PATH + "TestReport_" + timestamp + ".html";
            
            try {
                new File(REPORT_PATH).mkdirs();
                ExtentSparkReporter spark = new ExtentSparkReporter(reportFile);
                spark.config().setTheme(Theme.DARK);
                spark.config().setDocumentTitle("Selenium Test Results");
                spark.config().setReportName("Automation Test Report");
                
                extent.attachReporter(spark);
                extent.setSystemInfo("OS", System.getProperty("os.name"));
                extent.setSystemInfo("Java Version", System.getProperty("java.version"));
                extent.setSystemInfo("Browser", "Chrome");
                
                logger.info("Initialized ExtentReports: {}", reportFile);
            } catch (Exception e) {
                logger.error("Failed to initialize ExtentReports", e);
            }
        }
    }

    public static void createTest(String testName) {
        ExtentTest extentTest = extent.createTest(testName);
        test.set(extentTest);
    }

    public static ExtentTest getTest() {
        return test.get();
    }

    public static void flushReport() {
        if (extent != null) {
            extent.flush();
            logger.info("ExtentReports flushed successfully");
        }
    }
}