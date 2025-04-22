package com.demo.service;

import com.demo.dto.DataImportResult;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface DataProcessingService {
    DataImportResult importData(MultipartFile file);
    byte[] exportDataToExcel(String entityType);
    byte[] exportDataToCsv(String entityType);
    boolean validateData(MultipartFile file);
} 