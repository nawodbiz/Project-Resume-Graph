package com.example.Project.Resume.Graph.model;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "experiences")
@Data
@AllArgsConstructor
@NoArgsConstructor
@TypeDef(name = "jsonb", typeClass = JsonBinaryType.class)
public class ExperienceModel implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    @JsonIgnore
    private long id;
    private String company;
    private String durationYear;
    private String durationMonth;
    private String startingYear;
    private String startingMonth;
    private String endingYear;
    private String endingMonth;
    @Type(type = "jsonb")
    @Column(columnDefinition = "jsonb")
    private List<PositionModel> positions;
}
