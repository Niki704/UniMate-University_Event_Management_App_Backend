package com.unimate.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class AnnouncementResponseDTO {
    private Integer id;
    private String title;
    private String content;
    private LocalDate date;
    private Integer eventID;
    private Set<Integer> batchIDs;
    private Set<Integer> studentIDs;
    private Integer teacherID;
}
