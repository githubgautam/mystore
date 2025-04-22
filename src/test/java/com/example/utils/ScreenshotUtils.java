package com.example.utils;

import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import com.example.tests.BaseTest;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ScreenshotUtils {
    private static final Logger logger = LogManager.getLogger(ScreenshotUtils.class);
    private static final String SCREENSHOT_PATH = "test-output/screenshots/";

    public static String captureScreenshot(String testName) {
        try {
            WebDriver driver = BaseTest.getDriver();
            if (driver == null) {
                logger.error("WebDriver instance is null");
                return null;
            }

            TakesScreenshot ts = (TakesScreenshot) driver;
            File source = ts.getScreenshotAs(OutputType.FILE);
            String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
            String fileName = testName + "_" + timestamp + ".png";
            String destination = SCREENSHOT_PATH + fileName;

            File finalDestination = new File(destination);
            FileUtils.copyFile(source, finalDestination);
            logger.info("Screenshot captured: {}", destination);
            return destination;
        } catch (IOException e) {
            logger.error("Failed to capture screenshot: {}", e.getMessage());
            return null;
        }
    }
}