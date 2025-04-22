package com.demo.repository;

import com.demo.model.FileEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FileRepository extends JpaRepository<FileEntity, Long> {
    boolean existsByFileName(String fileName);
} 