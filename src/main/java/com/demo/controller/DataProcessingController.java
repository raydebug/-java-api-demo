package com.demo.controller;

import com.demo.dto.ApiResponse;
import com.demo.dto.DataImportResult;
import com.demo.service.DataProcessingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/v1/data")
public class DataProcessingController {
    private static final Logger log = LoggerFactory.getLogger(DataProcessingController.class);
    
    private final DataProcessingService dataProcessingService;

    @Autowired
    public DataProcessingController(DataProcessingService dataProcessingService) {
        this.dataProcessingService = dataProcessingService;
    }

    @PostMapping("/import")
    public ResponseEntity<ApiResponse<DataImportResult>> importData(@RequestParam("file") MultipartFile file) {
        DataImportResult result = dataProcessingService.importData(file);
        return ResponseEntity.ok(ApiResponse.success(result));
    }

    @PostMapping("/validate")
    public ResponseEntity<ApiResponse<DataImportResult>> validateData(@RequestParam("file") MultipartFile file) {
        DataImportResult result = dataProcessingService.validateData(file);
        return ResponseEntity.ok(ApiResponse.success(result));
    }

    @GetMapping("/export")
    public ResponseEntity<Resource> exportData(
            @RequestParam("type") String type,
            @RequestParam("format") String format) {
        Resource resource = dataProcessingService.exportData(type, format);
        String filename = String.format("export_%s.%s", type, format.toLowerCase());
        
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + filename + "\"")
                .body(resource);
    }
} 