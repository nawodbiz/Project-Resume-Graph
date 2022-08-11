package com.example.Project.Resume.Graph.service;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.fit.pdfdom.PDFDomTree;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@Service
public class FileManageService {
    /**upload the pdf file to the server directory*/
    public Document getSavedFileLocation(MultipartFile file, String savedFileLocation) throws IOException {
        Path filePath = Paths.get(savedFileLocation);
        InputStream fileInputStream = file.getInputStream();
        Files.copy(fileInputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
        fileInputStream.close();
        PDDocument uploadedPdf = PDDocument.load(new File(savedFileLocation));
        Writer output = new PrintWriter(savedFileLocation.substring(0, savedFileLocation.length() - 4) + ".html", "utf-8");
        new PDFDomTree().writeText(uploadedPdf, output);
        output.close();
        uploadedPdf.close();
        File input = new File(savedFileLocation.substring(0, savedFileLocation.length() - 4) + ".html");
        return Jsoup.parse(input, "UTF-8");
    }
    /**discard files from the server directory*/
    public void discardFiles(String savedFileLocation){
        {
            try {
                Files.deleteIfExists(Paths.get(savedFileLocation));
            }
            catch (IOException e) {
                System.out.println(e);
            }
        }
    }
}
