package com.example.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.By;

public class IndexPage extends BasePage {

    @FindBy(css = "a.login")
    private WebElement signInButton;

    @FindBy(css = "a.logout")
    private WebElement signOutButton;

    @FindBy(id = "search_query_top")
    private WebElement searchBox;

    @FindBy(name = "submit_search")
    private WebElement searchButton;

    private final By logoLocator = By.id("header_logo");

    public IndexPage(WebDriver driver) {
        super(driver);
    }

    public LoginPage clickSignIn() {
        action.click(By.cssSelector("a.login"));
        logger.info("Clicked on Sign In button");
        return new LoginPage(driver);
    }

    public void searchProduct(String productName) {
        action.sendKeys(By.id("search_query_top"), productName);
        action.click(By.name("submit_search"));
        logger.info("Searched for product: " + productName);
    }

    public boolean isLogoDisplayed() {
        return action.isElementPresent(logoLocator);
    }

    public boolean isUserLoggedIn() {
        return action.isElementPresent(By.cssSelector("a.logout"));
    }
}