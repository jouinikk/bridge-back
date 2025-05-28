package com.example.cars.dataImport;

import org.springframework.web.multipart.MultipartFile;

public interface DataImporter {
    void importData(MultipartFile file) throws Exception;
}