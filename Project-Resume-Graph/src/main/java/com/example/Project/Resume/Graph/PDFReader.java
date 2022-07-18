package com.example.Project.Resume.Graph.pdfbox;



import org.apache.pdfbox.pdmodel.PDDocument;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileInputStream;


@Component
public class PDFReader {

    public String Hello;

    public String readPDFFile() throws Exception {
        File file = new File("G:\\professional\\NIMI\\Project Resume Graph\\malith gammanpila.pdf");
        FileInputStream fis = new FileInputStream(file);

        PDDocument pdfDocument = PDDocument.load(fis);
        System.out.println(pdfDocument.getPages().getCount());

        pdfDocument.close();
        fis.close();
        return "hello";


    }
}
