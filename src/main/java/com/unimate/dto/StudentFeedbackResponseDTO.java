package com.unimate.dto;

import com.unimate.enums.StudentRating;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StudentFeedbackResponseDTO {
    private Integer id;
    private LocalDate date;
    private String content;
    private StudentRating studentRating;
    private Integer lecturerId;
    private String lecturerName;
    private Integer studentId;
    private String studentName;
}
