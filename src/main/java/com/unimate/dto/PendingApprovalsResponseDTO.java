package com.unimate.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PendingApprovalsResponseDTO {
    private List<StudentResponseDTO> pendingStudents;
    private List<LecturerResponseDTO> pendingLecturers;
}
