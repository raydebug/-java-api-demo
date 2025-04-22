package com.demo.service.impl;

import com.demo.dto.DataImportResult;
import com.demo.model.User;
import com.demo.repository.UserRepository;
import com.demo.service.DataProcessingService;
import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DataProcessingServiceImpl implements DataProcessingService {

    private final UserRepository userRepository;

    @Override
    public DataImportResult importData(MultipartFile file) {
        DataImportResult result = new DataImportResult();
        List<String> errors = new ArrayList<>();
        
        try {
            if (isExcelFile(file)) {
                result = importExcelData(file);
            } else if (isCsvFile(file)) {
                result = importCsvData(file);
            } else {
                throw new IllegalArgumentException("Unsupported file format");
            }
        } catch (Exception e) {
            errors.add("Error processing file: " + e.getMessage());
            result.setErrors(errors);
        }
        
        return result;
    }

    @Override
    public byte[] exportDataToExcel(String entityType) {
        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet(entityType);
            
            if ("users".equals(entityType)) {
                exportUsersToExcel(sheet);
            }
            // Add more entity types as needed
            
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            workbook.write(outputStream);
            return outputStream.toByteArray();
        } catch (Exception e) {
            throw new RuntimeException("Failed to export data to Excel", e);
        }
    }

    @Override
    public byte[] exportDataToCsv(String entityType) {
        try {
            StringWriter stringWriter = new StringWriter();
            CSVWriter csvWriter = new CSVWriter(stringWriter);
            
            if ("users".equals(entityType)) {
                exportUsersToCsv(csvWriter);
            }
            // Add more entity types as needed
            
            csvWriter.close();
            return stringWriter.toString().getBytes();
        } catch (Exception e) {
            throw new RuntimeException("Failed to export data to CSV", e);
        }
    }

    @Override
    public boolean validateData(MultipartFile file) {
        try {
            if (isExcelFile(file)) {
                return validateExcelData(file);
            } else if (isCsvFile(file)) {
                return validateCsvData(file);
            }
            return false;
        } catch (Exception e) {
            return false;
        }
    }

    private boolean isExcelFile(MultipartFile file) {
        return file.getContentType().equals("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
    }

    private boolean isCsvFile(MultipartFile file) {
        return file.getContentType().equals("text/csv");
    }

    private void exportUsersToExcel(Sheet sheet) {
        List<User> users = userRepository.findAll();
        Row headerRow = sheet.createRow(0);
        headerRow.createCell(0).setCellValue("ID");
        headerRow.createCell(1).setCellValue("Email");
        headerRow.createCell(2).setCellValue("First Name");
        headerRow.createCell(3).setCellValue("Last Name");
        
        int rowNum = 1;
        for (User user : users) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(user.getId());
            row.createCell(1).setCellValue(user.getEmail());
            row.createCell(2).setCellValue(user.getFirstName());
            row.createCell(3).setCellValue(user.getLastName());
        }
    }

    private void exportUsersToCsv(CSVWriter csvWriter) {
        List<User> users = userRepository.findAll();
        String[] header = {"ID", "Email", "First Name", "Last Name"};
        csvWriter.writeNext(header);
        
        for (User user : users) {
            String[] data = {
                String.valueOf(user.getId()),
                user.getEmail(),
                user.getFirstName(),
                user.getLastName()
            };
            csvWriter.writeNext(data);
        }
    }

    // Implementation details for import and validation methods...
} 