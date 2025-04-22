package com.demo.service.impl;

import com.demo.dto.DataImportResult;
import com.demo.model.User;
import com.demo.repository.UserRepository;
import com.demo.service.DataProcessingService;
import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.exceptions.CsvValidationException;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class DataProcessingServiceImpl implements DataProcessingService {
    private static final Logger log = LoggerFactory.getLogger(DataProcessingServiceImpl.class);
    private static final int BATCH_SIZE = 100;
    private static final String[] EXPECTED_HEADERS = {"Email", "First Name", "Last Name"};

    private final UserRepository userRepository;

    @Autowired
    public DataProcessingServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public DataImportResult importData(MultipartFile file) {
        if (!validateFileFormat(file)) {
            return new DataImportResult(0, 0, 0, List.of("Invalid file format"));
        }

        try {
            String filename = file.getOriginalFilename();
            if (filename != null && filename.endsWith(".xlsx")) {
                return processExcelFile(file);
            } else if (filename != null && filename.endsWith(".csv")) {
                return processCsvFile(file);
            }
            return new DataImportResult(0, 0, 0, List.of("Unsupported file format"));
        } catch (Exception e) {
            log.error("Error importing data", e);
            return new DataImportResult(0, 0, 0, List.of(e.getMessage()));
        }
    }

    @Override
    public DataImportResult validateData(MultipartFile file) {
        if (!validateFileFormat(file)) {
            return new DataImportResult(0, 0, 0, List.of("Invalid file format"));
        }

        try {
            String filename = file.getOriginalFilename();
            if (filename != null && filename.endsWith(".xlsx")) {
                return validateExcelFile(file) ? 
                    new DataImportResult(1, 1, 0, List.of()) :
                    new DataImportResult(1, 0, 1, List.of("Invalid Excel format"));
            } else if (filename != null && filename.endsWith(".csv")) {
                return validateCsvFile(file) ?
                    new DataImportResult(1, 1, 0, List.of()) :
                    new DataImportResult(1, 0, 1, List.of("Invalid CSV format"));
            }
            return new DataImportResult(0, 0, 0, List.of("Unsupported file format"));
        } catch (Exception e) {
            log.error("Error validating data", e);
            return new DataImportResult(0, 0, 0, List.of(e.getMessage()));
        }
    }

    @Override
    public Resource exportData(String type, String format) {
        try {
            byte[] content;
            if ("excel".equalsIgnoreCase(format)) {
                content = exportDataToExcel(type);
            } else if ("csv".equalsIgnoreCase(format)) {
                content = exportDataToCsv(type);
            } else {
                throw new IllegalArgumentException("Unsupported format: " + format);
            }
            return new ByteArrayResource(content);
        } catch (Exception e) {
            log.error("Error exporting data", e);
            throw new RuntimeException("Failed to export data: " + e.getMessage());
        }
    }

    @Override
    public boolean validateFileFormat(MultipartFile file) {
        String filename = file.getOriginalFilename();
        return filename != null && 
               (filename.endsWith(".xlsx") || filename.endsWith(".csv"));
    }

    private byte[] generateExportData(String type, String format) {
        // Implement export logic based on type and format
        return new byte[0]; // Placeholder
    }

    private byte[] exportDataToExcel(String entityType) {
        try (XSSFWorkbook workbook = new XSSFWorkbook();
             ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            
            Sheet sheet = workbook.createSheet("Users");
            int rowNum = 0;
            
            // Write headers
            Row headerRow = sheet.createRow(rowNum++);
            for (int i = 0; i < EXPECTED_HEADERS.length; i++) {
                headerRow.createCell(i).setCellValue(EXPECTED_HEADERS[i]);
            }
            
            // Write data
            List<User> users = userRepository.findAll();
            for (User user : users) {
                Row row = sheet.createRow(rowNum++);
                row.createCell(0).setCellValue(user.getEmail());
                row.createCell(1).setCellValue(user.getFirstName());
                row.createCell(2).setCellValue(user.getLastName());
            }
            
            workbook.write(out);
            return out.toByteArray();
        } catch (IOException e) {
            log.error("Error exporting to Excel", e);
            throw new RuntimeException("Failed to export data", e);
        }
    }

    private byte[] exportDataToCsv(String entityType) {
        try (StringWriter stringWriter = new StringWriter();
             CSVWriter csvWriter = new CSVWriter(stringWriter)) {
            
            // Write headers
            csvWriter.writeNext(EXPECTED_HEADERS);
            
            // Write data
            List<User> users = userRepository.findAll();
            for (User user : users) {
                csvWriter.writeNext(new String[]{
                    user.getEmail(),
                    user.getFirstName(),
                    user.getLastName()
                });
            }
            
            return stringWriter.toString().getBytes();
        } catch (IOException e) {
            log.error("Error exporting to CSV", e);
            throw new RuntimeException("Failed to export data", e);
        }
    }

    private boolean validateExcelFile(MultipartFile file) throws IOException {
        try (InputStream is = file.getInputStream();
             Workbook workbook = new XSSFWorkbook(is)) {
            
            Sheet sheet = workbook.getSheetAt(0);
            Row headerRow = sheet.getRow(0);
            
            // Validate headers
            if (headerRow == null) return false;
            for (int i = 0; i < EXPECTED_HEADERS.length; i++) {
                Cell cell = headerRow.getCell(i);
                if (cell == null || !EXPECTED_HEADERS[i].equals(cell.getStringCellValue())) {
                    return false;
                }
            }
            
            return true;
        }
    }

    private boolean validateCsvFile(MultipartFile file) throws IOException, CsvValidationException {
        try (Reader reader = new InputStreamReader(file.getInputStream());
             CSVReader csvReader = new CSVReader(reader)) {
            
            String[] header = csvReader.readNext();
            if (header == null || header.length < EXPECTED_HEADERS.length) {
                return false;
            }
            
            for (int i = 0; i < EXPECTED_HEADERS.length; i++) {
                if (!EXPECTED_HEADERS[i].equals(header[i])) {
                    return false;
                }
            }
            
            return true;
        }
    }

    private DataImportResult processExcelFile(MultipartFile file) throws IOException {
        List<String> errors = new ArrayList<>();
        int totalRecords = 0;
        int successCount = 0;

        try (InputStream is = file.getInputStream();
             Workbook workbook = new XSSFWorkbook(is)) {
            
            Sheet sheet = workbook.getSheetAt(0);
            totalRecords = sheet.getPhysicalNumberOfRows() - 1;

            for (int i = 1; i <= totalRecords; i++) {
                Row row = sheet.getRow(i);
                if (row != null) {
                    try {
                        processExcelRow(row);
                        successCount++;
                    } catch (Exception e) {
                        errors.add("Row " + i + ": " + e.getMessage());
                    }
                }
            }
        }

        return new DataImportResult(totalRecords, successCount, totalRecords - successCount, errors);
    }

    private void processExcelRow(Row row) {
        User user = new User();
        user.setEmail(row.getCell(0).getStringCellValue());
        user.setFirstName(row.getCell(1).getStringCellValue());
        user.setLastName(row.getCell(2).getStringCellValue());
        userRepository.save(user);
    }

    private DataImportResult processCsvFile(MultipartFile file) throws IOException, CsvValidationException {
        List<String> errors = new ArrayList<>();
        int totalRecords = 0;
        int successCount = 0;

        try (Reader reader = new InputStreamReader(file.getInputStream());
             CSVReader csvReader = new CSVReader(reader)) {
            
            // Skip header
            csvReader.readNext();
            
            String[] line;
            while ((line = csvReader.readNext()) != null) {
                totalRecords++;
                try {
                    processCsvLine(line);
                    successCount++;
                } catch (Exception e) {
                    errors.add("Row " + totalRecords + ": " + e.getMessage());
                }
            }
        }

        return new DataImportResult(totalRecords, successCount, totalRecords - successCount, errors);
    }

    private void processCsvLine(String[] line) {
        if (line.length < 3) {
            throw new IllegalArgumentException("Insufficient columns");
        }

        User user = new User();
        user.setEmail(line[0]);
        user.setFirstName(line[1]);
        user.setLastName(line[2]);
        userRepository.save(user);
    }
} 