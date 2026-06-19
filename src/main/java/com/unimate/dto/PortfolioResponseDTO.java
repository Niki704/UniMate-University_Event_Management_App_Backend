package com.unimate.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PortfolioResponseDTO {
    private Integer userID;
    private int portfolioID;
    private LocalDate createdDate;
    private LocalDate lastUpdated;
    private List<String> skills;
    private List<String> attachments;
}
