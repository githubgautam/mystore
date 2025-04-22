package com.example.config;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class Configuration {
    private String browser;
    private String baseUrl;

    public Configuration() {
        // Load configuration settings from properties file or environment variables
        this.browser = System.getProperty("browser", "chrome");
        this.baseUrl = System.getProperty("baseUrl", "http://localhost:8080");
    }

    public WebDriver getDriver() {
        switch (browser.toLowerCase()) {
            case "firefox":
                System.setProperty("webdriver.gecko.driver", "path/to/geckodriver");
                return new FirefoxDriver();
            case "chrome":
            default:
                System.setProperty("webdriver.chrome.driver", "path/to/chromedriver");
                return new ChromeDriver();
        }
    }

    public String getBaseUrl() {
        return baseUrl;
    }
}