package com.example.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.By;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.TimeoutException;

public class CartPage extends BasePage {

    @FindBy(css = ".cart_quantity_input")
    private WebElement quantityInput;

    @FindBy(css = ".cart_total .price")
    private WebElement totalPrice;

    @FindBy(css = ".standard-checkout")
    private WebElement checkoutButton;

    @FindBy(css = ".cart_quantity_delete")
    private WebElement deleteButton;

    public CartPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    public void updateQuantity(int quantity) {
        action.sendKeys(By.cssSelector(".cart_quantity_input"), String.valueOf(quantity));
        action.waitForPageLoad();
        logger.info("Updated cart quantity to: " + quantity);
    }

    public String getTotalPrice() {
        return totalPrice.getText().trim();
    }

    public PaymentPage proceedToCheckout() {
        action.click(By.cssSelector(".standard-checkout"));
        logger.info("Proceeded to checkout");
        return new PaymentPage(driver);
    }

    public void removeItem() {
        action.click(By.cssSelector(".cart_quantity_delete"));
        action.waitForPageLoad();
        logger.info("Removed item from cart");
    }

    public boolean isCartEmpty() {
        try {
            return !action.isElementPresent(By.cssSelector(".cart_item"));
        } catch (TimeoutException e) {
            return true;
        }
    }

    public static CartPage fromProductPage(ProductPage productPage) {
        CartPage cartPage = new CartPage(productPage.getDriver());
        return cartPage;
    }
}