package com.example.Project.Resume.Graph.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@Service
public class FileManageService {
    /**upload the pdf file to the server directory*/
    public String getSavedFileLocation(MultipartFile file) throws IOException {
        String savedFileLocation = "uploaded/"+ UUID.randomUUID().toString()+".pdf";
        File fileToSave = new File(savedFileLocation);
        fileToSave.getParentFile().mkdirs();
        fileToSave.delete();
        Path folder = Paths.get(savedFileLocation);
        Path fileToSavePath = Files.createFile(folder);
        InputStream fileInputStream = file.getInputStream();
        Files.copy(fileInputStream, fileToSavePath, StandardCopyOption.REPLACE_EXISTING);
        return savedFileLocation;
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
