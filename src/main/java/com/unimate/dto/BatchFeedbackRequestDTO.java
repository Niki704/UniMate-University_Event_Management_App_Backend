package com.unimate.dto;

import com.unimate.enums.Badge;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BatchFeedbackRequestDTO {
    @NotNull
    private LocalDate date;

    @NotNull
    private String content;

    private Set<Badge> badges;

    @NotNull
    private Integer batchId;
}
