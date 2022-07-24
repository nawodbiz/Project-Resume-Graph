package com.example.Project.Resume.Graph.controller;

import com.example.Project.Resume.Graph.service.ReadPdfService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api")
public class PDFController {

    @Autowired
    private ReadPdfService readPdfService;

    @GetMapping("/getExperiences")

    public String getExperiences(@RequestBody String fileLocation) throws Exception {


        return readPdfService.extractExperiences(fileLocation);

    }


}
