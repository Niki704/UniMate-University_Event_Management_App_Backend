package com.unimate.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class StudentFeedbackRequestDTO {
    private LocalDate date;
    private String content;
    private Integer teacherID;
    private Integer studentID;
}
