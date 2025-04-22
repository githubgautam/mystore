package com.example.tests;

import com.example.utils.Config;
import com.example.utils.DriverManager;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Listeners(com.example.utils.TestListener.class)
public class BaseTest {
    protected WebDriver driver;
    protected Config config;
    protected static final Logger logger = LogManager.getLogger(BaseTest.class);

    @BeforeTest
    public void setUp() {
        try {
            config = new Config();
            String browser = config.getProperty("browser", "chrome");
            DriverManager.setDriver(browser);
            driver = DriverManager.getDriver();
            logger.info("Test setup completed successfully");
        } catch (Exception e) {
            logger.error("Test setup failed: {}", e.getMessage());
            throw new RuntimeException("Failed to initialize test environment", e);
        }
    }

    @AfterTest
    public void tearDown() {
        DriverManager.quitDriver();
        logger.info("Test teardown completed");
    }

    public static WebDriver getDriver() {
        return DriverManager.getDriver();
    }
}