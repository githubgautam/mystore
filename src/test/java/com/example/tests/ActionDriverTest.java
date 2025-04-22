package com.example.tests;

import com.example.utils.ActionDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import io.github.bonigarcia.wdm.WebDriverManager;
import java.time.Duration;

public class ActionDriverTest extends BaseTest {
    private WebDriver driver;
    private ActionDriver action;

    @BeforeClass
    public void setUp() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
    }

    @Test
    public void testSearchFunctionality() {
        // Initialize ActionDriver
        action = new ActionDriver(driver);
        
        // Navigate to Google
        driver.get("https://www.google.com");
        
        try {
            // Accept cookies if present
            By cookieAccept = By.xpath("//button[contains(., 'Accept all')]");
            if (driver.findElements(cookieAccept).size() > 0) {
                action.click(cookieAccept);
            }
            
            // Perform search
            By searchBox = By.name("q");
            By searchButton = By.name("btnK");
            
            action.sendKeys(searchBox, "Selenium WebDriver");
            
            // Wait for and click search button
            driver.findElements(searchButton).get(1).click();
            
            // Verify results
            By searchResults = By.id("search");
            boolean isResultsDisplayed = action.isElementPresent(searchResults);
            
            logger.info("Search results displayed: {}", isResultsDisplayed);
            Assert.assertTrue(isResultsDisplayed, "Search results should be displayed");
            
        } catch (Exception e) {
            logger.error("Test failed: {}", e.getMessage());
            Assert.fail("Test failed: " + e.getMessage());
        }
    }

    @AfterClass
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}