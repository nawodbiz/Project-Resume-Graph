package com.example.Project.Resume.Graph.service;

import com.example.Project.Resume.Graph.dao.ExperienceRepository;
import com.example.Project.Resume.Graph.dao.ProfileRepository;
import com.example.Project.Resume.Graph.dto.ExperienceDTO;
import com.example.Project.Resume.Graph.dto.PositionDTO;
import com.example.Project.Resume.Graph.dto.ProfileDTO;
import com.example.Project.Resume.Graph.model.ExperienceModel;
import com.example.Project.Resume.Graph.model.PositionModel;
import com.example.Project.Resume.Graph.model.ProfileModel;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SaveToDBService {
    @Autowired
    private ProfileRepository profileRepository;
    @Autowired
    private ExperienceRepository experienceRepository;
    public ProfileModel saveProfileToDB (ProfileModel profileModel, List<ExperienceModel> experienceModelList){
        for(ExperienceModel experienceModel: experienceModelList){
            profileModel.getExperiences().add(experienceModel);
        }
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
            System.out.println(e);
        }
        return profileModel;
    }
    public List<ExperienceModel> saveExperienceListToDB (List<ExperienceModel> experienceModelList){
        return experienceRepository.saveAll(experienceModelList);
    }
    public List<ExperienceModel> assignExperienceData (List<ExperienceDTO> experienceDTOList){
        List<ExperienceModel> experienceModelList = new ArrayList<>();
        for(ExperienceDTO experienceDTO : experienceDTOList){
            ExperienceModel experienceModel = new ExperienceModel();
            try{
                experienceModel.setCompany(experienceDTO.getCompany());
                experienceModel.setDurationMonth(experienceDTO.getTimePeriod().getDuration().getMonth());
                experienceModel.setDurationYear(experienceDTO.getTimePeriod().getDuration().getYear());
                experienceModel.setStartingMonth(experienceDTO.getTimePeriod().getStarting().getMonth());
                experienceModel.setStartingYear(experienceDTO.getTimePeriod().getStarting().getYear());
                experienceModel.setEndingMonth(experienceDTO.getTimePeriod().getEnding().getMonth());
                experienceModel.setEndingYear(experienceDTO.getTimePeriod().getEnding().getYear());
                experienceModelList.add(experienceModel);
            }catch (Exception e){
                System.out.println(e);
            }
        }
        return experienceModelList;
    }
    public List<PositionModel> assignPositionData(List<PositionDTO> positionDTOList){
        List<PositionModel> positionModelList = new ArrayList<>();
        for(PositionDTO positionDTO : positionDTOList){
            PositionModel positionModel = new PositionModel();
            try{
                positionModel.setCompany(positionDTO.getCompany());
                positionModel.setTitle(positionDTO.getTitle());
                positionModel.setDescription(positionDTO.getDescription() == "" ? null : positionDTO.getDescription());
                positionModel.setDurationMonth(positionDTO.getTimePeriod().getDuration().getMonth());
                positionModel.setDurationYear(positionDTO.getTimePeriod().getDuration().getYear());
                positionModel.setStartingMonth(positionDTO.getTimePeriod().getStarting().getMonth());
                positionModel.setStartingYear(positionDTO.getTimePeriod().getStarting().getYear());
                positionModel.setEndingMonth(positionDTO.getTimePeriod().getEnding().getMonth());
                positionModel.setEndingYear(positionDTO.getTimePeriod().getEnding().getYear());
                positionModelList.add(positionModel);
            }catch (Exception e){
                System.out.println(e);
            }
        }
        return positionModelList;
    }
}
