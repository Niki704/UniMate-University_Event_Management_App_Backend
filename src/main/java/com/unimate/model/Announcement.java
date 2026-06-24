package com.unimate.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Set;

@Entity
@Table(name = "announcement")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Announcement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String title;

    @Lob
    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private LocalDate date;

    private LocalDate expiryDate;

    @ManyToOne
    @JoinColumn(name = "created_by_id", nullable = false)
    private User createdBy;

    @ManyToMany
    @JoinTable(name = "announcement_batch", joinColumns = @JoinColumn(name = "announcement_id"), inverseJoinColumns = @JoinColumn(name = "batch_id"))
    private Set<Batch> targetBatches;

    @ManyToMany
    @JoinTable(name = "announcement_student", joinColumns = @JoinColumn(name = "announcement_id"), inverseJoinColumns = @JoinColumn(name = "student_id"))
    private Set<Student> targetStudents;
}
