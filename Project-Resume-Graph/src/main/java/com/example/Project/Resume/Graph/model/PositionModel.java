package com.example.Project.Resume.Graph.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.YearMonth;
@Entity
@Table(name = "positions")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PositionModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private long id;
    private String company;
    private String title;
    private String duration;
    private String starting;
    private String ending;
    private String description;
}
