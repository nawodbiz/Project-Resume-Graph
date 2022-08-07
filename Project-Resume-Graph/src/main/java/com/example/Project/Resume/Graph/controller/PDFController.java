package com.example.Project.Resume.Graph.controller;

import com.example.Project.Resume.Graph.errors.ApiException;
import com.example.Project.Resume.Graph.service.ReadPdfService;
import com.example.Project.Resume.Graph.service.ResponseHandling;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
@RestController
@RequestMapping("/api")
public class PDFController {
    @Autowired
    private ReadPdfService readPdfService;
    @Autowired
    private ResponseHandling responseHandling;
    @PostMapping("/getExperiences")
    public String getExperiences(@RequestBody MultipartFile file) throws Exception {
//        Boolean isTrue = responseHandling.handleTheResponse(file);
//        if(isTrue) {
//            return new ReadPdfService().getFinalJsonOutput().toString();
//        }else{
//            return new ApiException(false,ResponseHandling.message, HttpStatus.BAD_REQUEST).getJsonObject().toString();
//        }

        try{
            return readPdfService.extractExperiences(file);
        }catch (Exception e){
            return new ApiException(false,e.getMessage(), HttpStatus.BAD_REQUEST).getJsonObject().toString();
        }finally {
            readPdfService.getFinalJsonOutput().clear();
            readPdfService.getProfileDetails().clear();
            readPdfService.getJsonData().clear();
        }
    }
}
