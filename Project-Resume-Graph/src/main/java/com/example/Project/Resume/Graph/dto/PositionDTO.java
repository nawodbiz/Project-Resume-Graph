package com.example.Project.Resume.Graph.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PositionDTO {
    private TimePeriodDTO timePeriod;
    private String description;
    private String company;
    private String title;
}
