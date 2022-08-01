package com.example.Project.Resume.Graph.controller;
import com.example.Project.Resume.Graph.service.ReadPdfService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
@RestController
@RequestMapping("/api")
public class PDFController {
    @Autowired
    private ReadPdfService readPdfService;

    @PostMapping("/getExperiences")
    public String getExperiences(@RequestBody MultipartFile file) throws Exception {
        String jsonOutput = "";
        try {
            jsonOutput = readPdfService.extractExperiences(file);
        } catch (Exception e) {
            return readPdfService.exceptionHandled();
        }
        return jsonOutput;
    }
}
