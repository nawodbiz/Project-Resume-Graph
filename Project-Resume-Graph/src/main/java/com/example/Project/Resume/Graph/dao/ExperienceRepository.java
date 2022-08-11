package com.example.Project.Resume.Graph.dao;

import com.example.Project.Resume.Graph.model.ExperienceModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface ExperienceRepository extends JpaRepository<ExperienceModel,Long> {
}
