package com.example.Project.Resume.Graph.dao;

import com.example.Project.Resume.Graph.model.PositionModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface PositionRepository extends JpaRepository<PositionModel,Long> {
}
