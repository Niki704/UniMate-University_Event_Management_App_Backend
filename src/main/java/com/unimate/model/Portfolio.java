package com.unimate.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Table(name = "portfolio")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Portfolio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToOne
    @JoinColumn(name = "student_id", nullable = false, unique = true)
    private Student student;

    @ElementCollection
    @CollectionTable(name = "portfolio_skills", joinColumns = @JoinColumn(name = "portfolio_id"))
    @Column(name = "skill")
    private Set<String> skills;

    @ElementCollection
    @CollectionTable(name = "portfolio_attachments", joinColumns = @JoinColumn(name = "portfolio_id"))
    @Column(name = "attachment")
    private Set<String> attachments;

    @ElementCollection
    @CollectionTable(name = "portfolio_project_links", joinColumns = @JoinColumn(name = "portfolio_id"))
    @Column(name = "project_link")
    private Set<String> projectLinks;

    @Column(nullable = false)
    private LocalDateTime createdDate;

    @Column(nullable = false)
    private LocalDateTime lastUpdated;
}
