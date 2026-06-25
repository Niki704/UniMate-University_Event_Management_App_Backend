package com.unimate.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AnnouncementRequestDTO {
    @NotNull
    private String title;

    @NotNull
    private String content;

    @NotNull
    private LocalDate date;

    private LocalDate expiryDate;

    private Set<Integer> batchIds;

    private Set<Integer> studentIds;
}
