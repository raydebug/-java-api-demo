package com.demo.dto;

import lombok.Data;
import java.util.List;

@Data
public class DataImportResult {
    private int totalRecords;
    private int successCount;
    private int failureCount;
    private List<String> errors;
} 