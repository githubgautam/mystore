package com.example.utils;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.testng.annotations.DataProvider;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Method;
import java.io.File;
import java.util.Objects;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public final class DataProviderUtils {
    private static final Logger logger = LogManager.getLogger(DataProviderUtils.class);
    private static final String TEST_DATA_PATH = System.getProperty("user.dir") + "/src/test/resources/testdata/";
    private static final String EXCEL_EXTENSION = ".xlsx";

    private DataProviderUtils() {
        throw new AssertionError("Utility class - do not instantiate");
    }

    @DataProvider(name = "excelData", parallel = true)
    public static Object[][] getTestData(Method method) {
        Objects.requireNonNull(method, "Method cannot be null");
        String testMethodName = method.getName();
        String excelFilePath = TEST_DATA_PATH + testMethodName + EXCEL_EXTENSION;
        
        File excelFile = new File(excelFilePath);
        if (!excelFile.exists()) {
            logger.error("Excel file not found: {}", excelFilePath);
            return new Object[0][0];
        }
        
        try (FileInputStream fis = new FileInputStream(excelFile);
             Workbook workbook = new XSSFWorkbook(fis)) {
            
            Sheet sheet = workbook.getSheetAt(0);
            int rowCount = sheet.getPhysicalNumberOfRows();
            
            if (rowCount <= 1) {
                logger.warn("No data found in excel file for method: {}", testMethodName);
                return new Object[0][0];
            }
            
            Row headerRow = sheet.getRow(0);
            if (headerRow == null) {
                logger.error("Header row is missing in excel file for method: {}", testMethodName);
                return new Object[0][0];
            }
            
            int colCount = headerRow.getLastCellNum();
            Object[][] data = new Object[rowCount - 1][colCount];
            
            for (int i = 1; i < rowCount; i++) {
                Row row = sheet.getRow(i);
                if (row != null) {
                    for (int j = 0; j < colCount; j++) {
                        data[i-1][j] = getCellValue(row.getCell(j));
                    }
                }
            }
            
            logger.info("Successfully loaded {} rows of test data for method: {}", rowCount - 1, testMethodName);
            return data;
            
        } catch (IOException e) {
            logger.error("Failed to load test data for method: " + testMethodName, e);
            return new Object[0][0];
        }
    }

    private static Object getCellValue(Cell cell) {
        if (cell == null) {
            return "";
        }
        
        try {
            return switch (cell.getCellType()) {
                case STRING -> cell.getStringCellValue();
                case NUMERIC -> DateUtil.isCellDateFormatted(cell) ? 
                               cell.getDateCellValue() : 
                               cell.getNumericCellValue();
                case BOOLEAN -> cell.getBooleanCellValue();
                case FORMULA -> getFormulaCellValue(cell);
                default -> "";
            };
        } catch (Exception e) {
            logger.warn("Error reading cell value: {}", e.getMessage());
            return "";
        }
    }

    private static Object getFormulaCellValue(Cell cell) {
        try {
            return cell.getStringCellValue();
        } catch (Exception e) {
            return cell.getCellFormula();
        }
    }
}