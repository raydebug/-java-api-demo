package com.demo.controller;

import com.demo.dto.ApiResponse;
import com.demo.dto.FileResponseDto;
import com.demo.service.FileService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/v1/files")
public class FileController {
    private static final Logger log = LoggerFactory.getLogger(FileController.class);
    
    private final FileService fileService;

    @Autowired
    public FileController(FileService fileService) {
        this.fileService = fileService;
    }

    @PostMapping("/upload")
    public ResponseEntity<ApiResponse<FileResponseDto>> uploadFile(
            @RequestParam("file") MultipartFile file) {
        FileResponseDto response = fileService.uploadFile(file);
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @PostMapping("/upload/multiple")
    public ResponseEntity<ApiResponse<List<FileResponseDto>>> uploadMultipleFiles(
            @RequestParam("files") List<MultipartFile> files) {
        List<FileResponseDto> responses = fileService.uploadMultipleFiles(files);
        return ResponseEntity.ok(ApiResponse.success(responses));
    }

    @GetMapping("/download/{id}")
    public ResponseEntity<Resource> downloadFile(@PathVariable Long id) {
        Resource resource = fileService.downloadFile(id);
        String contentType = "application/octet-stream";
        String headerValue = "attachment; filename=\"" + resource.getFilename() + "\"";

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, headerValue)
                .body(resource);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteFile(@PathVariable Long id) {
        fileService.deleteFile(id);
        return ResponseEntity.ok(ApiResponse.success(null));
    }
} 