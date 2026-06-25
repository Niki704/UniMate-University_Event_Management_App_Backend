package com.unimate.dto;

import com.unimate.enums.Badge;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BatchFeedbackResponseDTO {
    private Integer id;
    private LocalDate date;
    private String content;
    private Set<Badge> badges;
    private Integer lecturerId;
    private String lecturerName;
    private Integer batchId;
    private String batchCode;
}
