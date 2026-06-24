package com.unimate.model;

import com.unimate.enums.BatchType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Entity
@Table(name = "batch")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Batch {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, unique = true)
    private String batchCode;

    @Column(nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private BatchType batchType;

    @Column(nullable = false)
    private Integer startYear;

    @Column(nullable = false)
    private Integer endYear;

    @ManyToMany(mappedBy = "batches")
    private Set<Lecturer> lecturers;

    @OneToMany(mappedBy = "batch")
    private Set<Student> students;
}
