package com.unimate.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PortfolioRequestDTO {
    private Set<String> skills;
    private Set<String> attachments;
    private Set<String> projectLinks;
}
