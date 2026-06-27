package com.unimate.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "student")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(callSuper = true, onlyExplicitlyIncluded = true)
@ToString(callSuper = true, onlyExplicitlyIncluded = true)
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
