package com.example.Project.Resume.Graph.model;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
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
    private String profileName;
    private String emailAddress;
    private String currentPosition;
    private String linkedinProfileLink;
    private String currentLocation;
}
