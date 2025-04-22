package com.example.tests;

import com.example.pages.*;
import com.example.utils.Config;
import org.testng.Assert;
import org.testng.annotations.*;
import java.util.Properties;
import java.io.FileInputStream;
import java.io.IOException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ProductTest extends BaseTest {
    private static final Logger logger = LogManager.getLogger(ProductTest.class);
    private Properties productData;
    private ProductPage productPage;
    private Config config;
    private static final String PRODUCT_DATA_PATH = "src/test/resources/testdata/productTestData.properties";

    @BeforeClass
    public void setUpClass() throws IOException {
        config = new Config();
        loadProductData();
    }

    @BeforeMethod
    public void setUp() {
        productPage = new ProductPage(driver);
        String baseUrl = config.getProperty("test.url");
        logger.info("Navigating to product page: {}", baseUrl);
        driver.get(baseUrl);
    }

    @Test(description = "Verify product search functionality")
    public void testProductSearch() {
        String searchTerm = productData.getProperty("product.search.term");
        logger.info("Searching for product: {}", searchTerm);
        
        productPage.searchProduct(searchTerm);
        
        Assert.assertTrue(productPage.isProductListDisplayed(), 
            "Product list should be displayed after search");
    }

    @Test(description = "Verify adding product to cart")
    public void testAddToCart() {
        String searchTerm = productData.getProperty("product.search.term");
        int quantity = Integer.parseInt(productData.getProperty("product.quantity"));
        logger.info("Adding product to cart: {} (quantity: {})", searchTerm, quantity);
        
        productPage
            .searchProduct(searchTerm)
            .selectQuantity(quantity)
            .addToCart();
        
        Assert.assertTrue(productPage.isAddToCartConfirmationDisplayed(), 
            "Add to cart confirmation should be displayed");
    }

    private void loadProductData() throws IOException {
        productData = new Properties();
        try (FileInputStream fis = new FileInputStream(PRODUCT_DATA_PATH)) {
            productData.load(fis);
            logger.info("Product test data loaded successfully");
        } catch (IOException e) {
            logger.error("Failed to load product test data from: {}", PRODUCT_DATA_PATH, e);
            throw e;
        }
    }

    @AfterMethod
    public void cleanUp() {
        if (driver != null) {
            driver.manage().deleteAllCookies();
            logger.info("Browser cookies cleared");
        }
    }
}