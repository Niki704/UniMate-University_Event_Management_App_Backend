package com.unimate.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BatchDTO {
    private String batchId;
    private String batchName;
    private int startYear;
    private int endYear;
    private String userId;
}