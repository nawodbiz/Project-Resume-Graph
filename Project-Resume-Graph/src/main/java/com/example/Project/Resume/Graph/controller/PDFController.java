package com.example.Project.Resume.Graph.controller;

import com.example.Project.Resume.Graph.errors.ApiException;
import com.example.Project.Resume.Graph.service.ReadPdfService;


import com.example.Project.Resume.Graph.service.ResponseHandling;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.logging.Logger;


@RestController
@RequestMapping("/api")
@Data
public class PDFController {
    private static final Logger logger = Logger.getLogger(PDFController.class.getName());
    @Autowired
    private ReadPdfService readPdfService;
    @Autowired
    private ResponseHandling responseHandling;
    @PostMapping("/getExperiences")
    public String getExperiences(@RequestBody MultipartFile file) throws Exception {
        Boolean isTrue = responseHandling.handleTheResponse(file);
        if(isTrue) {
            return new ReadPdfService().getFinalJsonOutput().toString();
        }else{
            return new ApiException(false,ResponseHandling.message, HttpStatus.BAD_REQUEST).getJsonObject().toString();
        }
    }
}
