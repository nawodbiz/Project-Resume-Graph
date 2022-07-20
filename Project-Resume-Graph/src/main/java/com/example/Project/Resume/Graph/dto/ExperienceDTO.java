package com.example.Project.Resume.Graph.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.jsoup.nodes.Element;

import java.util.ArrayList;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExperienceDTO {

    private ArrayList<Element> companyName = new ArrayList<>();
    private ArrayList<Element> title = new ArrayList<>();
    private ArrayList<Element> timePeriod = new ArrayList<>();
    private ArrayList<Element> Location = new ArrayList<>();
    private ArrayList<Element> Description = new ArrayList<>();

}
