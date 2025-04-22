package com.example.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import com.example.pages.ProductPage;
import java.util.Properties;
import java.io.FileInputStream;
import java.io.IOException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ProductDetailsTest extends BaseTest {
    private ProductPage productPage;
    private Properties productData;
    private static final String PRODUCT_DATA_PATH = "src/test/resources/testdata/productTestData.properties";
    private static final Logger logger = LogManager.getLogger(ProductDetailsTest.class);

    @BeforeTest
    @Override
    public void setUp() {
        super.setUp();
        productData = new Properties();
        try (FileInputStream fis = new FileInputStream(PRODUCT_DATA_PATH)) {
            productData.load(fis);
            logger.info("Product test data loaded successfully");
            productPage = new ProductPage(driver);
        } catch (IOException e) {
            logger.error("Failed to load product test data: {}", e.getMessage());
            throw new RuntimeException("Failed to load product test data", e);
        }
    }

    @Test
    public void testProductDetails() {
        try {
            String searchTerm = productData.getProperty("product.search.term");
            logger.info("Testing product details for: {}", searchTerm);
            
            productPage.searchProduct(searchTerm);
            
            Assert.assertTrue(productPage.isProductAvailable(), 
                "Product should be available");
            logger.info("Product details test completed successfully");
        } catch (Exception e) {
            logger.error("Product details test failed: {}", e.getMessage());
            throw e;
        }
    }
}