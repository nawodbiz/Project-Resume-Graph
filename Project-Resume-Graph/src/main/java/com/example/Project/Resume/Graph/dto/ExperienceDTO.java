package com.example.Project.Resume.Graph.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExperienceDTO {
    private TimePeriodDTO timePeriod;
    private String company;
    private List<PositionDTO> positions;
}
