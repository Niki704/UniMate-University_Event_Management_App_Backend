package com.unimate.controller;

import com.unimate.dto.BatchFeedbackRequestDTO;
import com.unimate.dto.BatchFeedbackResponseDTO;
import com.unimate.security.UserPrincipal;
import com.unimate.service.BatchFeedbackService;
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
@RequestMapping("/api/v1/batch-feedback")
@RequiredArgsConstructor
public class BatchFeedbackController {

    private final BatchFeedbackService batchFeedbackService;

    @PostMapping
    @PreAuthorize("hasRole('LECTURER')")
    public ResponseEntity<BatchFeedbackResponseDTO> create(@AuthenticationPrincipal UserPrincipal lecturer,
            @Valid @RequestBody BatchFeedbackRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(batchFeedbackService.createFeedback(lecturer.getId(), dto));
    }

    @GetMapping
    public ResponseEntity<List<BatchFeedbackResponseDTO>> getFeedback(
            @RequestParam(required = false) Integer batchId,
            @RequestParam(required = false) Integer lecturerId) {

        if (batchId != null) {
            return ResponseEntity.ok(batchFeedbackService.getFeedbackByBatch(batchId));
        }
        if (lecturerId != null) {
            return ResponseEntity.ok(batchFeedbackService.getFeedbackByLecturer(lecturerId));
        }
        return ResponseEntity.ok(Collections.emptyList());
    }
}
