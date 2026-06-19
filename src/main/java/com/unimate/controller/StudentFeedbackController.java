package com.unimate.controller;

import jakarta.validation.Valid;
import com.unimate.dto.StudentFeedbackRequestDTO;
import com.unimate.dto.StudentFeedbackResponseDTO;
import com.unimate.service.StudentFeedbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping(value = "api/v8/studentFeedback/")
public class StudentFeedbackController {

    @Autowired
    private StudentFeedbackService studentFeedbackService;

    @PostMapping("/add")
    public ResponseEntity<StudentFeedbackResponseDTO> saveFeedback(@RequestBody @Valid StudentFeedbackRequestDTO feedbackRequestDTO) {
        StudentFeedbackResponseDTO responseDTO = studentFeedbackService.saveFeedback(feedbackRequestDTO);
        return ResponseEntity.ok(responseDTO);
    }
    @PutMapping("/update/{feedbackID}")
    public ResponseEntity<StudentFeedbackResponseDTO> updateFeedback(@PathVariable Integer feedbackID, @RequestBody @Valid StudentFeedbackRequestDTO feedbackRequestDTO) {
        StudentFeedbackResponseDTO responseDTO = studentFeedbackService.updateFeedback(feedbackID, feedbackRequestDTO);
        return ResponseEntity.ok(responseDTO);
    }

    @DeleteMapping("/delete/{feedbackID}")
    public ResponseEntity<String> deleteFeedback(@PathVariable Integer feedbackID) {
        studentFeedbackService.deleteFeedback(feedbackID);
        return ResponseEntity.ok("Feedback successfully deleted");
    }

    @GetMapping("/get/{feedbackID}")
    public ResponseEntity<StudentFeedbackResponseDTO> getFeedbackById(@PathVariable Integer feedbackID) {
        StudentFeedbackResponseDTO responseDTO = studentFeedbackService.getFeedbackById(feedbackID);
        return ResponseEntity.ok(responseDTO);
    }

    @GetMapping("/teacher/{teacherID}")
    public ResponseEntity<List<StudentFeedbackResponseDTO>> getFeedbackByTeacher(@PathVariable Integer teacherID) {
        List<StudentFeedbackResponseDTO> responseDTOs = studentFeedbackService.getFeedbackByTeacher(teacherID);
        return ResponseEntity.ok(responseDTOs);
    }

}
