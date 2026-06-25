package com.unimate.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StudentApproveRequestDTO {
    @NotNull
    private Integer batchId;

    @NotNull
    private String studentIdNumber;
}
