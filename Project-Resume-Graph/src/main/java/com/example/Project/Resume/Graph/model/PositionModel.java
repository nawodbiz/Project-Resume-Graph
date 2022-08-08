package com.example.Project.Resume.Graph.model;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.io.Serializable;

@Component
//@Entity
//@Table(name = "positions")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PositionModel implements Serializable {
//    @Id
//    @GeneratedValue(strategy = GenerationType.SEQUENCE)
//    @Column(name="id")
//    private long id;
    private String company;
    private String title;
    private String durationYear;
    private String durationMonth;
    private String startingYear;
    private String startingMonth;
    private String endingYear;
    private String endingMonth;
    private String description;
}
