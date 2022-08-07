package com.example.Project.Resume.Graph.controller;
import com.example.Project.Resume.Graph.dto.ExperienceDTO;
import com.example.Project.Resume.Graph.dto.JsonResponseDTO;
import com.example.Project.Resume.Graph.errors.ApiException;
import com.example.Project.Resume.Graph.model.ExperienceModel;
import com.example.Project.Resume.Graph.model.PositionModel;
import com.example.Project.Resume.Graph.model.ProfileModel;
import com.example.Project.Resume.Graph.service.ReadPdfService;
import com.example.Project.Resume.Graph.service.SaveToDBService;
import com.example.Project.Resume.Graph.util.StartupUtility;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/profile")
public class ProfileController {
    @Autowired
    private ReadPdfService readPdfService;
    @Autowired
    private SaveToDBService saveToDBService;
    @Autowired
    private StartupUtility startupUtility;
    @PostMapping
    public ProfileModel saveProfileData(@RequestBody MultipartFile file) throws JsonProcessingException {
        String jsonResponse;
        try{
            jsonResponse = readPdfService.extractExperiences(file);
        }catch (Exception e){
            jsonResponse = new ApiException(false,e.getMessage(), HttpStatus.BAD_REQUEST).getJsonObject().toString();
        }finally {
            readPdfService.getFinalJsonOutput().clear();
            readPdfService.getProfileDetails().clear();
            readPdfService.getJsonData().clear();
        }
        JsonResponseDTO jsonResponseDTO = StartupUtility.objectMapper.readValue(jsonResponse,JsonResponseDTO.class);
        ProfileModel profileData = saveToDBService.assignProfileData(jsonResponseDTO.getData().getProfile());
        List<ExperienceModel> experiencedata = saveToDBService.assignExperienceData(jsonResponseDTO.getData().getExperiences());
        saveToDBService.saveExperienceListToDB(experiencedata);
        for(ExperienceDTO selectedExperience: jsonResponseDTO.getData().getExperiences()){
            List<PositionModel> positionsData = saveToDBService.assignPositionData(selectedExperience.getPositions());
            saveToDBService.savePositionListToDB(positionsData);
        }
        return saveToDBService.saveProfileToDB(profileData);
    }
}
