package com.demo.service;

import com.demo.dto.FileResponseDto;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;

public interface FileService {
    FileResponseDto uploadFile(MultipartFile file);
    List<FileResponseDto> uploadMultipleFiles(List<MultipartFile> files);
    Resource downloadFile(Long id);
    void deleteFile(Long id);
} 