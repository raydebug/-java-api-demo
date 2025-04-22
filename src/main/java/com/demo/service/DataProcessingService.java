package com.demo.service;

import com.demo.dto.DataImportResult;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public interface DataProcessingService {
    DataImportResult importData(MultipartFile file);
    DataImportResult validateData(MultipartFile file);
    Resource exportData(String type, String format);
    boolean validateFileFormat(MultipartFile file);
} 