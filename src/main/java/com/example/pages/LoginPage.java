package com.example.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.By;

public class LoginPage extends BasePage {

    @FindBy(id = "email")
    private WebElement emailInput;

    @FindBy(id = "passwd")
    private WebElement passwordInput;

    @FindBy(id = "SubmitLogin")
    private WebElement signInButton;

    @FindBy(css = ".alert-danger")
    private WebElement errorMessage;

    public LoginPage(WebDriver driver) {
        super(driver);
    }

    public HomePage login(String email, String password) {
        action.sendKeys(By.id("email"), email);
        action.sendKeys(By.id("passwd"), password);
        action.click(By.id("SubmitLogin"));
        logger.info("Logged in with email: " + email);
        return new HomePage(driver);
    }

    public String getErrorMessage() {
        if (action.isElementPresent(By.cssSelector(".alert-danger"))) {
            return driver.findElement(By.cssSelector(".alert-danger")).getText();
        }
        return "";
    }

    public boolean isLoginFormDisplayed() {
        return action.isElementPresent(By.id("login_form"));
    }
}