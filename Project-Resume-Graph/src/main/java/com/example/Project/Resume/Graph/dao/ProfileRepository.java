package com.example.Project.Resume.Graph.dao;

import com.example.Project.Resume.Graph.model.ProfileModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProfileRepository extends JpaRepository<ProfileModel,Long> {
    ProfileModel findByprofileName(String profileName);
}
