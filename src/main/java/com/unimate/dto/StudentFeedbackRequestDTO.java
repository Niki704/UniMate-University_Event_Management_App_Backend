package com.unimate.dto;

import com.unimate.enums.StudentRating;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StudentFeedbackRequestDTO {
    @NotNull
    private LocalDate date;

    @NotNull
    private String content;

    @NotNull
    private StudentRating studentRating;

    @NotNull
    private Integer studentId;
}
