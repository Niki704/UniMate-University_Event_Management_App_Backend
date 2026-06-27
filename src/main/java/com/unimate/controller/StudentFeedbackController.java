package com.unimate.controller;

import com.unimate.dto.StudentFeedbackRequestDTO;
import com.unimate.dto.StudentFeedbackResponseDTO;
import com.unimate.security.UserPrincipal;
import com.unimate.service.StudentFeedbackService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/api/v1/student-feedback")
@RequiredArgsConstructor
public class StudentFeedbackController {

    private final StudentFeedbackService studentFeedbackService;

    @PostMapping
    @PreAuthorize("hasRole('LECTURER')")
    public ResponseEntity<StudentFeedbackResponseDTO> create(@AuthenticationPrincipal UserPrincipal lecturer,
            @Valid @RequestBody StudentFeedbackRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(studentFeedbackService.createFeedback(lecturer.getId(), dto));
    }

    @GetMapping
    public ResponseEntity<List<StudentFeedbackResponseDTO>> getFeedback(
            @RequestParam(required = false) Integer studentId,
            @RequestParam(required = false) Integer lecturerId) {

        if (studentId != null) {
            return ResponseEntity.ok(studentFeedbackService.getFeedbackByStudent(studentId));
        }
        if (lecturerId != null) {
            return ResponseEntity.ok(studentFeedbackService.getFeedbackByLecturer(lecturerId));
        }
        return ResponseEntity.ok(Collections.emptyList());
    }
}
