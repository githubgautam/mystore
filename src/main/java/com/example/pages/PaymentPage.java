package com.example.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.By;

public class PaymentPage extends BasePage {

    @FindBy(css = ".bankwire")
    private WebElement bankWirePayment;

    @FindBy(css = ".cheque")
    private WebElement checkPayment;

    @FindBy(css = "#cart_navigation button[type='submit']")
    private WebElement confirmOrderButton;

    @FindBy(css = ".alert-success")
    private WebElement orderConfirmation;

    public PaymentPage(WebDriver driver) {
        super(driver);
    }

    public void selectBankWirePayment() {
        action.click(By.cssSelector(".bankwire"));
        logger.info("Selected bank wire payment method");
    }

    public void selectCheckPayment() {
        action.click(By.cssSelector(".cheque"));
        logger.info("Selected check payment method");
    }

    public void confirmOrder() {
        action.click(By.cssSelector("#cart_navigation button[type='submit']"));
        action.waitForPageLoad();
        logger.info("Confirmed order");
    }

    public boolean isOrderComplete() {
        return action.isElementPresent(By.cssSelector(".alert-success"));
    }

    public String getOrderConfirmationMessage() {
        return action.waitForElementToBeVisible(By.cssSelector(".alert-success")).getText();
    }
}