package com.demo.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class FileResponseDto {
    private Long id;
    private String fileName;
    private String fileType;
    private Long fileSize;
    private String uploadedBy;
    private LocalDateTime uploadedAt;
} 