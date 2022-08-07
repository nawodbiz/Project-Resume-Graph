package com.example.Project.Resume.Graph.service;

import com.example.Project.Resume.Graph.dao.ExperienceRepository;
import com.example.Project.Resume.Graph.dao.PositionRepository;
import com.example.Project.Resume.Graph.dao.ProfileRepository;
import com.example.Project.Resume.Graph.dto.ExperienceDTO;
import com.example.Project.Resume.Graph.dto.PositionDTO;
import com.example.Project.Resume.Graph.dto.ProfileDTO;
import com.example.Project.Resume.Graph.model.ExperienceModel;
import com.example.Project.Resume.Graph.model.PositionModel;
import com.example.Project.Resume.Graph.model.ProfileModel;
import com.example.Project.Resume.Graph.util.StartupUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static org.hibernate.tool.schema.SchemaToolingLogging.LOGGER;
@Service
public class SaveToDBService {
    @Autowired
    private ProfileRepository profileRepository;
    @Autowired
    private ExperienceRepository experienceRepository;
    @Autowired
    private PositionRepository positionRepository;
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
    public List<ExperienceModel> saveExperienceListToDB (List<ExperienceModel> experienceModelList){
        return experienceRepository.saveAll(experienceModelList);
    }
    public List<ExperienceModel> assignExperienceData (List<ExperienceDTO> experienceDTOList){
        List<ExperienceModel> experienceModelList = new ArrayList<>();
        for(ExperienceDTO experienceDTO : experienceDTOList){
            ExperienceModel experienceModel = new ExperienceModel();
            try{
                experienceModel.setCompany(experienceDTO.getCompany());
                experienceModel.setDuration(experienceDTO.getTimePeriod().getDuration().toString());
                experienceModel.setStarting(experienceDTO.getTimePeriod().getStarting().toString());
                experienceModel.setEnding(experienceDTO.getTimePeriod().getEnding().toString());
                experienceModelList.add(experienceModel);
            }catch (Exception e){
                LOGGER.error(String.format("exception at class Save To DB, assignExperienceData method : %1$s", e));
            }
        }
        return experienceModelList;
    }
    public List<PositionModel> savePositionListToDB (List<PositionModel> positionModelList){
        return positionRepository.saveAll(positionModelList);
    }
    public List<PositionModel> assignPositionData(List<PositionDTO> positionDTOList){
        List<PositionModel> positionModelList = new ArrayList<>();
        for(PositionDTO positionDTO : positionDTOList){
            PositionModel positionModel = new PositionModel();
            try{
                positionModel.setCompany(positionDTO.getCompany());
                positionModel.setTitle(positionDTO.getTitle());
                positionModel.setDescription(positionDTO.getDescription() == "" ? null : positionDTO.getDescription());
                positionModel.setDuration("month : " + positionDTO.getTimePeriod().getDuration().getMonth() +", year : "+positionDTO.getTimePeriod().getDuration().getYear());
                positionModel.setStarting("month : " + positionDTO.getTimePeriod().getStarting().getMonth() +", year : "+positionDTO.getTimePeriod().getStarting().getYear());
                positionModel.setEnding("month : " + positionDTO.getTimePeriod().getEnding().getMonth() +", year : "+positionDTO.getTimePeriod().getEnding().getYear());
                positionModelList.add(positionModel);
            }catch (Exception e){
                LOGGER.error(String.format("exception at class Save To DB, assignPositionData method : %1$s", e));
            }
        }
        return positionModelList;
    }
}
