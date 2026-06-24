package com.unimate.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "student")
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Data
public class Student extends User {

    private Integer enrollmentYear;

    @ManyToOne
    @JoinColumn(name = "batch_id")
    private Batch batch;

    @Column(unique = true)
    private String studentIdNumber;

    @ManyToOne
    @JoinColumn(name = "verified_by_id")
    private Admin verifiedBy;
}
