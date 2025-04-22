package com.demo.controller;

import com.demo.dto.ApiResponse;
import com.demo.dto.FileResponseDto;
import com.demo.service.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/v1/files")
@RequiredArgsConstructor
public class FileController {

    private final FileService fileService;

    @PostMapping("/upload")
    public ResponseEntity<ApiResponse<FileResponseDto>> uploadFile(
            @RequestParam("file") MultipartFile file) {
        FileResponseDto response = fileService.uploadFile(file);
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @PostMapping("/bulk-upload")
    public ResponseEntity<ApiResponse<List<FileResponseDto>>> uploadMultipleFiles(
            @RequestParam("files") List<MultipartFile> files) {
        List<FileResponseDto> responses = fileService.uploadMultipleFiles(files);
        return ResponseEntity.ok(ApiResponse.success(responses));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Resource> downloadFile(@PathVariable Long id) {
        Resource resource = fileService.downloadFile(id);
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteFile(@PathVariable Long id) {
        fileService.deleteFile(id);
        return ResponseEntity.ok(ApiResponse.success(null));
    }
} 