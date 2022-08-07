package com.example.Project.Resume.Graph.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TimePeriodDTO {
    private DurationDTO duration;
//    private EndingMonthDTO ending;
    private StartingMonthDTO starting;
}
