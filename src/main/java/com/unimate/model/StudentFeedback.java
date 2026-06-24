package com.unimate.model;

import com.unimate.enums.StudentRating;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "student_feedback")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class StudentFeedback {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private LocalDate date;

    @Lob
    @Column(nullable = false)
    private String content;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StudentRating studentRating;

    @ManyToOne
    @JoinColumn(name = "lecturer_id", nullable = false)
    private Lecturer lecturer;

    @ManyToOne
    @JoinColumn(name = "student_id", nullable = false)
    private Student student;
}
