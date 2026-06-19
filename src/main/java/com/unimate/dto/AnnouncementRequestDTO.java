package com.unimate.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class AnnouncementRequestDTO {
    @NotNull
    private String title;

    @NotNull
    private String content;

    @NotNull
    private LocalDate date;

    private Integer eventID;

    private Set<Integer> batchIDs;

    private Set<Integer> studentIDs;

    @NotNull
    private Integer teacherID;
}
