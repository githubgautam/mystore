# Selenium Test Automation Framework

A comprehensive test automation framework built with Selenium WebDriver, TestNG, and ExtentReports.

## Features

- Page Object Model design pattern
- Cross-browser testing support (Chrome, Firefox, Edge)
- Parallel test execution
- Extent Reports integration
- Log4j2 logging
- Screenshot capture on test failure
- Data-driven testing support
- Smoke and regression test suites
- WebDriverManager for automated driver management

## Prerequisites

- Java JDK 17 or higher
- Maven 3.8 or higher
- Chrome, Firefox, or Edge browser

## Project Structure

```
selenium-test-framework/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/example/
│   │   │       ├── pages/       # Page Object classes
│   │   │       └── utils/       # Utility classes
│   │   └── resources/          # Configuration files
│   └── test/
│       ├── java/
│       │   └── com/example/
│       │       └── tests/       # Test classes
│       └── resources/
│           └── testdata/       # Test data files
├── logs/                      # Test execution logs
├── screenshots/              # Failure screenshots
├── test-output/             # Test reports
├── pom.xml                  # Dependencies
└── testng.xml              # Test suite configuration
```

## Running Tests

### Running all tests:
```bash
mvn clean test
```

### Running specific test groups:
```bash
mvn clean test -Dgroups=smoke
mvn clean test -Dgroups=regression
```

### Running with specific browser:
```bash
mvn clean test -Dbrowser=chrome
mvn clean test -Dbrowser=firefox
mvn clean test -Dbrowser=edge
```

### Running in headless mode:
```bash
mvn clean test -Dheadless=true
```

## Test Reports

- Extent Reports: `test-output/ExtentReports/TestReport_[timestamp].html`
- TestNG Reports: `test-output/index.html`
- Logs: `logs/automation.log`

## Configuration

- `src/test/resources/config.properties`: Basic configuration
- `src/main/resources/log4j2.xml`: Logging configuration
- `src/main/resources/extent-config.xml`: Reporting configuration
- `testng.xml`: Test suite and parallel execution configuration

## Page Objects

The framework implements the Page Object Model with the following pages:
- IndexPage
- LoginPage
- HomePage
- ProductPage
- CartPage
- PaymentPage

## Utility Classes

- ActionDriver: Common Selenium actions
- DriverManager: WebDriver initialization and management
- ExtentReportManager: Test reporting
- ScreenshotUtils: Capture screenshots
- TestListener: TestNG listener for reporting and logging

## Test Data Management

Test data is managed through properties files in `src/test/resources/testdata/`:
- loginTestData.properties
- productTestData.properties

## Best Practices

1. Create new page objects for new pages
2. Add test methods to appropriate test classes
3. Update test data files for new test cases
4. Use appropriate test groups for new tests
5. Follow existing patterns for logging and reporting
