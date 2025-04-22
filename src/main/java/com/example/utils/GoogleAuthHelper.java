package com.example.utils;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import java.time.Duration;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class GoogleAuthHelper {
    private final WebDriver driver;
    private final Config config;
    private static final Logger logger = LogManager.getLogger(GoogleAuthHelper.class);
    private final WebDriverWait wait;

    public GoogleAuthHelper(WebDriver driver) {
        this.driver = driver;
        this.config = new Config();
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public void signInWithGoogle() {
        try {
            logger.info("Starting Google authentication");
            driver.get(config.getProperty("google.auth.url"));
            
            wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("input[type='email']")))
                .sendKeys(config.getProperty("google.auth.email"));
            
            driver.findElement(By.cssSelector("#identifierNext")).click();
            
            wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("input[type='password']")))
                .sendKeys(config.getProperty("google.auth.password"));
            
            driver.findElement(By.cssSelector("#passwordNext")).click();
            
            logger.info("Google authentication completed");
        } catch (Exception e) {
            logger.error("Google authentication failed: {}", e.getMessage());
            throw new RuntimeException("Google authentication failed", e);
        }
    }
}