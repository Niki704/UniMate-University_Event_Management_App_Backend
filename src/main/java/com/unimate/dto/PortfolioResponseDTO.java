package com.unimate.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PortfolioResponseDTO {
    private Integer id;
    private Integer studentId;
    private Set<String> skills;
    private Set<String> attachments;
    private Set<String> projectLinks;
    private LocalDateTime createdDate;
    private LocalDateTime lastUpdated;
}
