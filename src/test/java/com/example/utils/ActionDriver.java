package com.example.utils;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.time.Duration;

public class ActionDriver {
    private final WebDriver driver;
    private final Actions actions;
    private static final Logger logger = LogManager.getLogger(ActionDriver.class);

    public ActionDriver(WebDriver driver) {
        this.driver = driver;
        this.actions = new Actions(driver);
    }

    public void click(By locator) {
        try {
            WebElement element = WebElementUtils.waitForElementClickable(driver, locator);
            WebElementUtils.scrollIntoView(driver, element);
            element.click();
            logger.debug("Clicked element: {}", locator);
        } catch (Exception e) {
            logger.error("Failed to click element: {}", locator, e);
            throw e;
        }
    }

    public void sendKeys(By locator, String text) {
        try {
            WebElement element = WebElementUtils.waitForElementVisible(driver, locator);
            WebElementUtils.clearAndType(element, text);
            logger.debug("Sent text '{}' to element: {}", text, locator);
        } catch (Exception e) {
            logger.error("Failed to send keys to element: {}", locator, e);
            throw e;
        }
    }

    public boolean isElementPresent(By locator) {
        return WebElementUtils.isElementPresent(driver, locator);
    }

    public void selectByVisibleText(By locator, String text) {
        try {
            WebElement element = WebElementUtils.waitForElementVisible(driver, locator);
            new Select(element).selectByVisibleText(text);
            logger.debug("Selected option '{}' from dropdown: {}", text, locator);
        } catch (Exception e) {
            logger.error("Failed to select option '{}' from dropdown: {}", text, locator, e);
            throw e;
        }
    }

    public void hover(By locator) {
        try {
            WebElement element = WebElementUtils.waitForElementVisible(driver, locator);
            actions.moveToElement(element).perform();
            logger.debug("Hovered over element: {}", locator);
        } catch (Exception e) {
            logger.error("Failed to hover over element: {}", locator, e);
            throw e;
        }
    }

    public void dragAndDrop(By sourceLocator, By targetLocator) {
        try {
            WebElement source = WebElementUtils.waitForElementVisible(driver, sourceLocator);
            WebElement target = WebElementUtils.waitForElementVisible(driver, targetLocator);
            actions.dragAndDrop(source, target).perform();
            logger.debug("Dragged element from {} to {}", sourceLocator, targetLocator);
        } catch (Exception e) {
            logger.error("Failed to drag and drop from {} to {}", sourceLocator, targetLocator, e);
            throw e;
        }
    }

    public void waitForPageLoad() {
        try {
            driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(30));
            logger.debug("Waited for page load");
        } catch (Exception e) {
            logger.error("Page load timeout", e);
            throw e;
        }
    }

    public String getText(By locator) {
        try {
            WebElement element = WebElementUtils.waitForElementVisible(driver, locator);
            String text = element.getText();
            logger.debug("Got text '{}' from element: {}", text, locator);
            return text;
        } catch (Exception e) {
            logger.error("Failed to get text from element: {}", locator, e);
            throw e;
        }
    }
}