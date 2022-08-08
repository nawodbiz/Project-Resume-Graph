package com.example.Project.Resume.Graph.model;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "profile_data")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProfileModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Long id;
    public ProfileModel(String profileName, String emailAddress, String currentPosition, String linkedinProfileLink, String currentLocation) {
        this.profileName = profileName;
        this.emailAddress = emailAddress;
        this.currentPosition = currentPosition;
        this.linkedinProfileLink = linkedinProfileLink;
        this.currentLocation = currentLocation;
    }
    private String profileName;
    @Column(name="email_address", unique = true)
    private String emailAddress;
    private String currentPosition;
    private String linkedinProfileLink;
    private String currentLocation;
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "profile_id")
    private List<ExperienceModel> experiences = new ArrayList<>();
}
