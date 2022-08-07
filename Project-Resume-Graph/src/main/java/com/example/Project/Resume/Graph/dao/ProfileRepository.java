package com.example.Project.Resume.Graph.dao;

import com.example.Project.Resume.Graph.model.ProfileModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface ProfileRepository extends JpaRepository<ProfileModel,Long> {
}
