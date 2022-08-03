package com.example.Project.Resume.Graph.service;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@Data
public class ResponseHandling{
    @Autowired
    private ReadPdfService readPdfService;
    public static Boolean successResponse;
    public static String message;
    public Boolean handleTheResponse(MultipartFile file) {
        try {
            readPdfService.extractExperiences(file);
        } catch (Exception e) {
            message = e.getMessage();
            return false;
        }
            return successResponse = true;
    }
}
