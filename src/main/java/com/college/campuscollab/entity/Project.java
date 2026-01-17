package com.college.campuscollab.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "projects")
@Getter
@Setter
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String projectName;

    private String teamLeaderName;

    private String course;

    private Integer semester;

    private String techStack;

    @Column(length = 1000)
    private String description;

    //  UPDATED LINKS
    private String liveLink;   // hosted project output
    private String codeLink;   // GitHub / source code
    private String status;
    // Screenshots (store file paths or URLs)
    @ElementCollection
    private List<String> screenshots;

    @ManyToOne
    private User owner;

}
