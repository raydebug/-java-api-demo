package com.demo.service.impl;

import com.demo.dto.FileResponseDto;
import com.demo.model.FileEntity;
import com.demo.model.User;
import com.demo.repository.FileRepository;
import com.demo.service.FileService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FileServiceImpl implements FileService {

    @Value("${file.upload-dir}")
    private String uploadDir;

    private final FileRepository fileRepository;

    @Override
    @Transactional
    public FileResponseDto uploadFile(MultipartFile file) {
        try {
            String fileName = StringUtils.cleanPath(file.getOriginalFilename());
            String uniqueFileName = UUID.randomUUID().toString() + "_" + fileName;
            Path targetLocation = Paths.get(uploadDir).resolve(uniqueFileName);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

            FileEntity fileEntity = new FileEntity();
            fileEntity.setFileName(fileName);
            fileEntity.setFileType(file.getContentType());
            fileEntity.setFilePath(targetLocation.toString());
            fileEntity.setFileSize(file.getSize());
            fileEntity.setUploadedBy((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal());

            FileEntity savedFile = fileRepository.save(fileEntity);
            return convertToDto(savedFile);
        } catch (IOException ex) {
            throw new RuntimeException("Could not store file. Please try again!", ex);
        }
    }

    @Override
    @Transactional
    public List<FileResponseDto> uploadMultipleFiles(List<MultipartFile> files) {
        List<FileResponseDto> responses = new ArrayList<>();
        for (MultipartFile file : files) {
            responses.add(uploadFile(file));
        }
        return responses;
    }

    @Override
    public Resource downloadFile(Long id) {
        try {
            FileEntity fileEntity = fileRepository.findById(id)
                    .orElseThrow(() -> new EntityNotFoundException("File not found"));

            Path filePath = Paths.get(fileEntity.getFilePath());
            Resource resource = new UrlResource(filePath.toUri());

            if (resource.exists()) {
                return resource;
            } else {
                throw new EntityNotFoundException("File not found");
            }
        } catch (MalformedURLException ex) {
            throw new RuntimeException("File not found", ex);
        }
    }

    @Override
    @Transactional
    public void deleteFile(Long id) {
        FileEntity fileEntity = fileRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("File not found"));

        try {
            Path filePath = Paths.get(fileEntity.getFilePath());
            Files.deleteIfExists(filePath);
            fileRepository.delete(fileEntity);
        } catch (IOException ex) {
            throw new RuntimeException("Error: " + ex.getMessage());
        }
    }

    private FileResponseDto convertToDto(FileEntity file) {
        FileResponseDto dto = new FileResponseDto();
        dto.setId(file.getId());
        dto.setFileName(file.getFileName());
        dto.setFileType(file.getFileType());
        dto.setFileSize(file.getFileSize());
        dto.setUploadedBy(file.getUploadedBy().getEmail());
        dto.setUploadedAt(file.getUploadedAt());
        return dto;
    }
} 