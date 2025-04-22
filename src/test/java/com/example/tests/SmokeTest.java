package com.example.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.example.pages.IndexPage;

public class SmokeTest extends BaseTest {
    private static final Logger logger = LogManager.getLogger(SmokeTest.class);

    @Test(description = "Verify home page loads successfully")
    public void testHomePageLoad() {
        logger.info("Starting home page load test");
        
        driver.get(config.getProperty("base.url", "https://www.example.com"));
        IndexPage indexPage = new IndexPage(driver);
        
        Assert.assertTrue(indexPage.isLogoDisplayed(), "Home page logo should be displayed");
        logger.info("Home page load test completed successfully");
    }
}