package com.example.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.By;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.TimeoutException;

public class ProductPage extends BasePage {

    @FindBy(id = "quantity_wanted")
    private WebElement quantityInput;

    @FindBy(css = "button.add-to-cart")
    private WebElement addToCartButton;

    @FindBy(css = ".layer_cart_product h2")
    private WebElement addToCartConfirmation;

    @FindBy(css = ".product-price")
    private WebElement productPrice;

    @FindBy(css = ".product-name")
    private WebElement productName;

    @FindBy(css = ".alert")
    private WebElement alertMessage;

    @FindBy(css = "a.checkout")
    private WebElement checkoutButton;

    private final By productList = By.cssSelector(".product-list");
    private final By searchBox = By.id("search_query_top");
    private final By searchButton = By.name("submit_search");
    private final By outOfStockLabel = By.cssSelector(".out-of-stock");
    private final By addToCartSuccessMessage = By.cssSelector(".layer_cart_product h2");

    public ProductPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    public ProductPage searchProduct(String searchTerm) {
        logger.info("Searching for product: {}", searchTerm);
        action.sendKeys(searchBox, searchTerm);
        action.click(searchButton);
        action.waitForPageLoad();
        return this;
    }

    public String getProductPrice() {
        return productPrice.getText().trim();
    }

    public String getProductName() {
        return productName.getText().trim();
    }

    public boolean isProductAvailable() {
        return !action.isElementPresent(outOfStockLabel);
    }

    public String getAlertMessage() {
        return alertMessage.getText().trim();
    }

    public boolean isProductListDisplayed() {
        return action.isElementPresent(productList);
    }

    public ProductPage selectQuantity(int quantity) {
        quantityInput.clear();
        quantityInput.sendKeys(String.valueOf(quantity));
        return this;
    }

    public ProductPage addToCart() {
        logger.info("Adding product to cart");
        addToCartButton.click(); // Direct click on WebElement
        action.waitForPageLoad();
        return this;
    }

    public boolean isAddToCartConfirmationDisplayed() {
        return action.isElementPresent(addToCartSuccessMessage);
    }

    public ProductPage proceedToCheckout() {
        logger.info("Proceeding to checkout");
        action.waitForPageLoad();
        checkoutButton.click(); // Direct click on WebElement
        return this;
    }

    public WebDriver getDriver() {
        return super.getDriver();
    }
}