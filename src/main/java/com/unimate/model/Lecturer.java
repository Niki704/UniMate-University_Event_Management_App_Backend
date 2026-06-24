package com.unimate.model;

import com.unimate.enums.Department;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.Set;

@Entity
@Table(name = "lecturer")
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Data
public class Lecturer extends User {

    @Enumerated(EnumType.STRING)
    private Department department;

    @ManyToMany
    @JoinTable(name = "lecturer_batch", joinColumns = @JoinColumn(name = "lecturer_id"), inverseJoinColumns = @JoinColumn(name = "batch_id"))
    private Set<Batch> batches;

    @ManyToOne
    @JoinColumn(name = "verified_by_id")
    private Admin verifiedBy;
}
