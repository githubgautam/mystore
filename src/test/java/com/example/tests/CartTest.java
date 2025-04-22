package com.example.tests;

import com.example.pages.*;
import com.example.utils.Config;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import java.util.Properties;
import java.io.FileInputStream;
import java.io.IOException;

public class CartTest extends BaseTest {
    private Properties productData;
    private CartPage cartPage;
    private Config config;
    private ProductPage productPage;

    @BeforeClass
    public void setUp() {
        try {
            productData = new Properties();
            productData.load(new FileInputStream("src/test/resources/testdata/productTestData.properties"));
            productPage = new ProductPage(driver);
            config = new Config();
        } catch (IOException e) {
            logger.error("Failed to load test data: {}", e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @Test
    public void testCartOperations() {
        String searchTerm = productData.getProperty("product.search.term");
        
        // First add product to cart
        productPage.searchProduct(searchTerm)
                  .selectQuantity(1)
                  .addToCart();

        // Use factory method to create cart page
        cartPage = new CartPage(driver);
        productPage.proceedToCheckout();

        // Verify cart contents
        Assert.assertFalse(cartPage.isCartEmpty(), "Cart should not be empty");
        Assert.assertNotNull(cartPage.getTotalPrice(), "Total price should be displayed");
    }
}