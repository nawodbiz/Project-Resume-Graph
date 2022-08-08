package com.example.Project.Resume.Graph.service;

import com.example.Project.Resume.Graph.dao.ProfileRepository;
import com.example.Project.Resume.Graph.model.ProfileModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FindFromDBService {
    @Autowired
    ProfileRepository profileRepository;
    public List<ProfileModel> profileList (){
        return profileRepository.findAll();
    }
    public ProfileModel findByProfileName(String profileName){
        return profileRepository.findByprofileName(profileName);
    }
}
