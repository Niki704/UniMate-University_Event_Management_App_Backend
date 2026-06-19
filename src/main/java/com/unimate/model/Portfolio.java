package com.unimate.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Portfolio {
    @Id
    private Integer userID;

    @Column(nullable = false, unique = true)
    private int portfolioID;

    @Column(nullable = false, updatable = false)
    private LocalDate createdDate;

    @Column(nullable = false)
    private LocalDate lastUpdated;

    @ElementCollection
    private List<String> skills;
//    @CollectionTable(name = "portfolio_skills", joinColumns = @JoinColumn(name = "portfolio_id"))

//    @CollectionTable(name = "portfolio_attachments", joinColumns = @JoinColumn(name = "portfolio_id"))
    @ElementCollection
    private List<String> attachments;

}
