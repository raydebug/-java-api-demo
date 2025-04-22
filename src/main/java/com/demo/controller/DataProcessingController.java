package com.demo.controller;

import com.demo.dto.ApiResponse;
import com.demo.dto.DataImportResult;
import com.demo.service.DataProcessingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/v1/data")
@RequiredArgsConstructor
public class DataProcessingController {

    private final DataProcessingService dataProcessingService;

    @PostMapping("/import")
    public ResponseEntity<ApiResponse<DataImportResult>> importData(
            @RequestParam("file") MultipartFile file) {
        DataImportResult result = dataProcessingService.importData(file);
        return ResponseEntity.ok(ApiResponse.success(result));
    }

    @GetMapping("/export")
    public ResponseEntity<byte[]> exportData(
            @RequestParam("type") String type,
            @RequestParam("format") String format) {
        
        byte[] data;
        String filename;
        String contentType;
        
        if ("excel".equals(format)) {
            data = dataProcessingService.exportDataToExcel(type);
            filename = type + ".xlsx";
            contentType = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
        } else {
            data = dataProcessingService.exportDataToCsv(type);
            filename = type + ".csv";
            contentType = "text/csv";
        }

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + filename + "\"")
                .body(data);
    }

    @PostMapping("/validate")
    public ResponseEntity<ApiResponse<Boolean>> validateData(
            @RequestParam("file") MultipartFile file) {
        boolean isValid = dataProcessingService.validateData(file);
        return ResponseEntity.ok(ApiResponse.success(isValid));
    }
} 