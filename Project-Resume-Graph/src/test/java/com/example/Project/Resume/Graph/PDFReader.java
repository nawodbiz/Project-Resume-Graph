package com.example.Project.Resume.Graph;


import org.apache.commons.lang3.StringUtils;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import org.jsoup.select.Elements;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileInputStream;



public class PDFReader {

    @Test
    public void readPDFFile() throws Exception {
        File file = new File("G:\\professional\\NIMI\\Project Resume Graph\\malith gammanpila.pdf");
        FileInputStream fis = new FileInputStream(file);

        PDDocument pdfDocument = PDDocument.load(fis);
        PDFTextStripper pdfTextStripper = new PDFTextStripper();
        String docText = pdfTextStripper.getText(pdfDocument);

        String experienceList = StringUtils.substringBetween(docText, "Experience", "Education");
        experienceList = experienceList.replaceAll("[^A-Za-z .,0-9()_(\n)/-]"," ");
        experienceList = experienceList.replaceAll("(\\s \\s)Page [0-9]* of [0-9]* (\n)  ","");



        System.out.println(experienceList);


        pdfDocument.close();
        fis.close();

    }

}
