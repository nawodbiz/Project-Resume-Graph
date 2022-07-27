package com.example.Project.Resume.Graph.controller;

import com.example.Project.Resume.Graph.service.ReadPdfService;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.fit.pdfdom.PDFDomTree;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;


@RestController
@RequestMapping("/api")
public class PDFController {

    @Autowired
    private ReadPdfService readPdfService;

    @PostMapping("/getExperiences")

    public String getExperiences(@RequestBody MultipartFile file) throws Exception {

        String jsonOutput = readPdfService.extractExperiences(file);

        return jsonOutput;
    }


}
