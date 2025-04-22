package com.example.tests;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.example.pages.HomePage;
import com.example.pages.IndexPage;
import com.example.pages.LoginPage;

public class LoginTest extends BaseTest {
    private static final Logger logger = LogManager.getLogger(LoginTest.class);
    private Properties loginData;
    private static final String TEST_DATA_PATH = System.getProperty("user.dir") 
        + File.separator + "src" + File.separator + "test" + File.separator 
        + "resources" + File.separator + "testdata" + File.separator + "loginTestData.properties";

    @BeforeTest
    @Override
    public void setUp() {
        super.setUp(); // Call parent setUp first
        loginData = new Properties();
        File testDataFile = new File(TEST_DATA_PATH);
        
        try {
            if (!testDataFile.exists()) {
                throw new RuntimeException("Test data file not found: " + TEST_DATA_PATH);
            }
            
            try (FileInputStream fis = new FileInputStream(testDataFile)) {
                loginData.load(fis);
                logger.info("Successfully loaded login test data");
            }
        } catch (IOException e) {
            logger.error("Failed to load login test data: {}", e.getMessage());
            throw new RuntimeException("Failed to load login test data", e);
        }
    }

    @Test(priority = 1, 
          description = "Verify successful login with valid credentials",
          groups = {"login", "smoke"})
    public void testValidLogin() {
        logger.info("Starting valid login test");
        
        String username = loginData.getProperty("valid.username");
        String password = loginData.getProperty("valid.password");
        
        IndexPage indexPage = new IndexPage(driver);
        LoginPage loginPage = indexPage.clickSignIn();
        HomePage homePage = loginPage.login(username, password);
        
        Assert.assertTrue(homePage.isUserLoggedIn(), "User should be logged in successfully");
        logger.info("Valid login test completed successfully");
    }

    @Test(priority = 2, 
          description = "Verify login failures with invalid credentials",
          groups = {"login", "regression"})
    public void testInvalidLogin() {
        logger.info("Starting invalid login test");
        
        IndexPage indexPage = new IndexPage(driver);
        LoginPage loginPage = indexPage.clickSignIn();
        
        loginPage.login(loginData.getProperty("invalid.username1"), 
                       loginData.getProperty("invalid.password1"));
        
        Assert.assertTrue(loginPage.isLoginFormDisplayed(), 
            "Login form should still be displayed");
        Assert.assertEquals(loginPage.getErrorMessage(), 
            loginData.getProperty("error.message.invalid"),
            "Error message should match expected message");
        
        logger.info("Invalid login test completed successfully");
    }

    @Test(priority = 3, 
          description = "Verify login validation with empty credentials",
          groups = {"login", "regression"})
    public void testEmptyCredentials() {
        logger.info("Starting empty credentials test");
        
        IndexPage indexPage = new IndexPage(driver);
        LoginPage loginPage = indexPage.clickSignIn();
        
        loginPage.login(loginData.getProperty("invalid.username3"), 
                       loginData.getProperty("invalid.password3"));
        
        Assert.assertEquals(loginPage.getErrorMessage(), 
            loginData.getProperty("error.message.empty"),
            "Error message for empty email should be displayed");
        
        logger.info("Empty credentials test completed successfully");
    }

    @Test(priority = 4, 
          description = "Verify user can sign out successfully",
          groups = {"login", "smoke"},
          dependsOnMethods = {"testValidLogin"})
    public void testSignOut() {
        logger.info("Starting sign out test");
        
        String username = loginData.getProperty("valid.username");
        String password = loginData.getProperty("valid.password");
        
        IndexPage indexPage = new IndexPage(driver);
        LoginPage loginPage = indexPage.clickSignIn();
        HomePage homePage = loginPage.login(username, password);
        
        Assert.assertTrue(homePage.isUserLoggedIn(), "User should be logged in");
        
        indexPage = homePage.signOut();
        Assert.assertFalse(indexPage.isUserLoggedIn(), "User should be logged out");
        logger.info("Sign out test completed successfully");
    }
}