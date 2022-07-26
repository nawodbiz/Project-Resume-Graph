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


//    @PostMapping("/upload")
//    public void uploadFiles(@RequestParam("file") MultipartFile file ) throws IOException {
//
//        String savedFileLocation = "uploaded/"+ UUID.randomUUID().toString()+".pdf";
//
////        File fileToSave = new File(file.getOriginalFilename());
//
//        File fileToSave = new File(savedFileLocation);
//
//        fileToSave.getParentFile().mkdirs();
//        fileToSave.delete();
//
//        Path folder = Paths.get(savedFileLocation);
//        Path fileToSavePath = Files.createFile(folder);
//
//        InputStream  fileInputStream = file.getInputStream();
//
//        Files.copy(fileInputStream, fileToSavePath, StandardCopyOption.REPLACE_EXISTING);
//        System.out.println(fileToSavePath.toString());
//
//        PDDocument pdf = PDDocument.load(new File(savedFileLocation));
//        Writer output = new PrintWriter(savedFileLocation.substring(0,savedFileLocation.length()-4)+".html", "utf-8");
//        new PDFDomTree().writeText(pdf, output);
//
//        output.close();
//        pdf.close();
//
//
//        File input = new File(savedFileLocation.substring(0,savedFileLocation.length()-4)+".html");
//        Document doc = Jsoup.parse(input, "UTF-8");
//
//
//
//
//    }


}
