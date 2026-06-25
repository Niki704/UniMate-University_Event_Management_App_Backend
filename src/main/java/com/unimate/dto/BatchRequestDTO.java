package com.unimate.dto;

import com.unimate.enums.BatchType;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BatchRequestDTO {
    @NotNull
    private String batchCode;

    @NotNull
    private String name;

    @NotNull
    private BatchType batchType;

    @NotNull
    private Integer startYear;

    @NotNull
    private Integer endYear;
}
