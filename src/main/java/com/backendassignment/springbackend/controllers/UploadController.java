package com.backendassignment.springbackend.controllers;

import com.backendassignment.springbackend.services.CSVService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import static com.backendassignment.springbackend.services.CSVService.hasCSVFormat;

@RestController
@RequestMapping("api/csv")
public class UploadController {
    CSVService csvService;

    public UploadController(CSVService csvService) {
        this.csvService = csvService;
    }

    @PostMapping("upload")
    public String uploadFile(@RequestParam MultipartFile file){
        if (hasCSVFormat(file)) {
            try {
                csvService.save(file);
                return "Uploaded the file successfully: ";
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return "Error: File Not Uploaded";
    }

}
