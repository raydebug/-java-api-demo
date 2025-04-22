package com.demo.dto;

import java.util.List;

public class DataImportResult {
    private final int totalRecords;
    private final int successCount;
    private final int errorCount;
    private final List<String> errors;

    // Constructor
    public DataImportResult(int totalRecords, int successCount, int errorCount, List<String> errors) {
        this.totalRecords = totalRecords;
        this.successCount = successCount;
        this.errorCount = errorCount;
        this.errors = errors;
    }

    // Getters and Setters
    public int getTotalRecords() {
        return totalRecords;
    }

    public int getSuccessCount() {
        return successCount;
    }

    public int getErrorCount() {
        return errorCount;
    }

    public List<String> getErrors() {
        return errors;
    }
} 