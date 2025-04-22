package com.example.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class HomePage extends BasePage {

    @FindBy(css = ".account")
    private WebElement accountName;

    @FindBy(css = "a.logout")
    private WebElement signOutButton;

    @FindBy(css = "#block_top_menu > ul > li")
    private WebElement categoryMenu;

    private By welcomeMessage = By.cssSelector(".welcome-msg");
    private By logoutLink = By.linkText("Logout");
    private By searchBox = By.id("search");

    public HomePage(WebDriver driver) {
        super(driver);
    }

    public String getLoggedInUserName() {
        return action.waitForElementToBeVisible(By.cssSelector(".account")).getText();
    }

    public IndexPage signOut() {
        action.click(By.cssSelector("a.logout"));
        logger.info("Clicked on Sign Out button");
        return new IndexPage(driver);
    }

    public ProductPage navigateToCategory(String categoryName) {
        action.click(By.linkText(categoryName));
        logger.info("Navigated to category: " + categoryName);
        return new ProductPage(driver);
    }

    public boolean isUserLoggedIn() {
        return action.isElementPresent(By.cssSelector("a.logout"));
    }

    public boolean isDisplayed() {
        return action.isElementPresent(welcomeMessage);
    }

    public void searchProduct(String productName) {
        action.sendKeys(searchBox, productName);
    }

    public void logout() {
        action.click(logoutLink);
    }
}