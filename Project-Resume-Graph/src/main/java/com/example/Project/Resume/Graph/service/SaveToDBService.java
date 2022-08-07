package com.example.Project.Resume.Graph.service;

import com.example.Project.Resume.Graph.dao.ProfileRepository;
import com.example.Project.Resume.Graph.dto.ProfileDTO;
import com.example.Project.Resume.Graph.model.ProfileModel;
import com.example.Project.Resume.Graph.util.StartupUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static org.hibernate.tool.schema.SchemaToolingLogging.LOGGER;
@Service
public class SaveToDBService {
    @Autowired
    private ProfileRepository profileRepository;
    public ProfileModel saveProfileToDB (ProfileModel profileModel){
        return this.profileRepository.save(profileModel);
    }
    public ProfileModel assignProfileData (ProfileDTO profileDTO){
        ProfileModel profileModel = new ProfileModel();
        try{
            profileModel.setProfileName(profileDTO.getProfileName());
            profileModel.setCurrentPosition(profileDTO.getCurrentPosition() == "" ? null : profileDTO.getCurrentPosition());
            profileModel.setLinkedinProfileLink(profileDTO.getLinkedinProfileLink());
            profileModel.setCurrentLocation(profileDTO.getCurrentLocation() == "" ? null : profileDTO.getCurrentLocation());
            profileModel.setEmailAddress(profileDTO.getEmailAddress() == "" ? null : profileDTO.getEmailAddress());
        }catch (Exception e){
            LOGGER.error(String.format("exception at class SavingDBService, SavingDBService method: %1$s", e));
        }
        return profileModel;

    }
}
