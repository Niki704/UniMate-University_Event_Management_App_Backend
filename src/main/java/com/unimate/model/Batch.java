package com.unimate.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Batch {
    @Id
    private String batchId;

    @Column(nullable = false, unique = true)
    private String batchName;

    @Min(value = 1900)
    @Max(value = 2100)
    private int startYear;

    @Min(value = 1900)
    @Max(value = 2100)
    private int endYear;

    @NotNull
    @Column(length = 50)
    private String userId;
}
