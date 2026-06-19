package com.unimate.controller;

import jakarta.validation.Valid;
import com.unimate.dto.BatchFeedbackRequestDTO;
import com.unimate.dto.BatchFeedbackResponseDTO;
import com.unimate.service.BatchFeedbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping(value = "api/v9/batchFeedback/")
public class BatchFeedbackController {

    @Autowired
    private BatchFeedbackService batchFeedbackService;

    @PostMapping("/add")
    public ResponseEntity<BatchFeedbackResponseDTO> saveFeedback(@RequestBody @Valid BatchFeedbackRequestDTO feedbackRequestDTO) {
        BatchFeedbackResponseDTO responseDTO = batchFeedbackService.saveFeedback(feedbackRequestDTO);
        return ResponseEntity.ok(responseDTO);
    }

    @PutMapping("/update/{feedbackID}")
    public ResponseEntity<BatchFeedbackResponseDTO> updateFeedback(@PathVariable Integer feedbackID, @RequestBody @Valid BatchFeedbackRequestDTO feedbackRequestDTO) {
        BatchFeedbackResponseDTO responseDTO = batchFeedbackService.updateFeedback(feedbackID, feedbackRequestDTO);
        return ResponseEntity.ok(responseDTO);
    }

    @DeleteMapping("/delete/{feedbackID}")
    public ResponseEntity<String> deleteFeedback(@PathVariable Integer feedbackID) {
        batchFeedbackService.deleteFeedback(feedbackID);
        return ResponseEntity.ok("Feedback successfully deleted");
    }

    @GetMapping("/get/{feedbackID}")
    public ResponseEntity<BatchFeedbackResponseDTO> getFeedbackById(@PathVariable Integer feedbackID) {
        BatchFeedbackResponseDTO responseDTO = batchFeedbackService.getFeedbackById(feedbackID);
        return ResponseEntity.ok(responseDTO);
    }

    @GetMapping("/teacher/{teacherID}")
    public ResponseEntity<List<BatchFeedbackResponseDTO>> getFeedbackByTeacher(@PathVariable Integer teacherID) {
        List<BatchFeedbackResponseDTO> responseDTOs = batchFeedbackService.getFeedbackByTeacher(teacherID);
        return ResponseEntity.ok(responseDTOs);
    }

}
