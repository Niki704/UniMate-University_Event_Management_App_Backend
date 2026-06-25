package com.unimate.dto;

import com.unimate.enums.BatchType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BatchResponseDTO {
    private Integer id;
    private String batchCode;
    private String name;
    private BatchType batchType;
    private Integer startYear;
    private Integer endYear;
    private Set<String> lecturerNames;
    private Integer studentCount;
}
