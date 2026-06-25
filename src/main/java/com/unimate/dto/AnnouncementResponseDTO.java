package com.unimate.dto;

import com.unimate.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AnnouncementResponseDTO {
    private Integer id;
    private String title;
    private String content;
    private LocalDate date;
    private LocalDate expiryDate;
    private String createdByName;
    private Role createdByRole;
    private Set<String> targetBatchCodes;
    private Set<Integer> targetStudentIds;
}
