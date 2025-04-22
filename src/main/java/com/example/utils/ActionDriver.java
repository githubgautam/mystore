package com.example.utils;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import java.time.Duration;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ActionDriver {
    private final WebDriver driver;
    private final WebDriverWait wait;
    private static final Logger logger = LogManager.getLogger(ActionDriver.class);
    private static final Duration TIMEOUT = Duration.ofSeconds(10);

    public ActionDriver(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, TIMEOUT);
    }

    public WebElement waitForElementToBeVisible(By locator) {
        try {
            return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
        } catch (Exception e) {
            logger.error("Element not visible: {}", locator, e);
            throw e;
        }
    }

    public void click(By locator) {
        try {
            waitForElementToBeVisible(locator).click();
            logger.debug("Clicked element: {}", locator);
        } catch (Exception e) {
            logger.error("Failed to click element: {}", locator, e);
            throw e;
        }
    }

    public void sendKeys(By locator, String text) {
        try {
            WebElement element = waitForElementToBeVisible(locator);
            element.clear();
            element.sendKeys(text);
            logger.debug("Sent text '{}' to element: {}", text, locator);
        } catch (Exception e) {
            logger.error("Failed to send keys to element: {}", locator, e);
            throw e;
        }
    }

    public boolean isElementPresent(By locator) {
        try {
            waitForElementToBeVisible(locator);
            return true;
        } catch (Exception e) {
            logger.debug("Element not present: {}", locator);
            return false;
        }
    }

    public void waitForPageLoad() {
        try {
            Thread.sleep(1000); // Basic wait for demo
            logger.debug("Waited for page load");
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            logger.error("Page load wait interrupted", e);
        }
    }
}