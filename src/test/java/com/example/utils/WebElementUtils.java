package com.example.utils;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class WebElementUtils {
    private static final Logger logger = LogManager.getLogger(WebElementUtils.class);
    private static final Duration DEFAULT_TIMEOUT = Duration.ofSeconds(10);

    public static WebElement waitForElementVisible(WebDriver driver, By locator) {
        return waitForElementVisible(driver, locator, DEFAULT_TIMEOUT);
    }

    public static WebElement waitForElementVisible(WebDriver driver, By locator, Duration timeout) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, timeout);
            return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
        } catch (TimeoutException e) {
            logger.error("Element not visible: {} after {} seconds", locator, timeout.getSeconds());
            throw e;
        }
    }

    public static WebElement waitForElementClickable(WebDriver driver, By locator) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, DEFAULT_TIMEOUT);
            return wait.until(ExpectedConditions.elementToBeClickable(locator));
        } catch (TimeoutException e) {
            logger.error("Element not clickable: {}", locator);
            throw e;
        }
    }

    public static boolean isElementPresent(WebDriver driver, By locator) {
        try {
            waitForElementVisible(driver, locator);
            return true;
        } catch (TimeoutException | NoSuchElementException e) {
            return false;
        }
    }

    public static void scrollIntoView(WebDriver driver, WebElement element) {
        try {
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
            logger.debug("Scrolled element into view: {}", element);
        } catch (Exception e) {
            logger.error("Failed to scroll element into view: {}", element);
            throw e;
        }
    }

    public static void jsClick(WebDriver driver, WebElement element) {
        try {
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
            logger.debug("Clicked element using JavaScript: {}", element);
        } catch (Exception e) {
            logger.error("Failed to click element using JavaScript: {}", element);
            throw e;
        }
    }

    public static void clearAndType(WebElement element, String text) {
        try {
            element.clear();
            element.sendKeys(text);
            logger.debug("Entered text '{}' into element: {}", text, element);
        } catch (Exception e) {
            logger.error("Failed to enter text '{}' into element: {}", text, element);
            throw e;
        }
    }
}