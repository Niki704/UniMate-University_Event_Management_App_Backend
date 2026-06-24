package com.unimate.model;

import com.unimate.enums.Badge;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Set;

@Entity
@Table(name = "batch_feedback")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class BatchFeedback {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private LocalDate date;

    @Lob
    @Column(nullable = false)
    private String content;

    @ElementCollection
    @CollectionTable(name = "batch_feedback_badges", joinColumns = @JoinColumn(name = "batch_feedback_id"))
    @Enumerated(EnumType.STRING)
    @Column(name = "badge")
    private Set<Badge> badges;

    @ManyToOne
    @JoinColumn(name = "lecturer_id", nullable = false)
    private Lecturer lecturer;

    @ManyToOne
    @JoinColumn(name = "batch_id", nullable = false)
    private Batch batch;
}
