package com.unimate.service;

import com.unimate.dto.StudentFeedbackRequestDTO;
import com.unimate.dto.StudentFeedbackResponseDTO;
import com.unimate.model.StudentFeedback;
import com.unimate.repo.StudentFeedbackRepo;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class StudentFeedbackService {

    @Autowired
    private StudentFeedbackRepo studentFeedbackRepo;

    @Autowired
    private ModelMapper modelMapper;

    public StudentFeedbackResponseDTO saveFeedback(StudentFeedbackRequestDTO feedbackRequestDTO) {
        StudentFeedback feedback = modelMapper.map(feedbackRequestDTO, StudentFeedback.class);
        StudentFeedback savedFeedback = studentFeedbackRepo.save(feedback);
        return modelMapper.map(savedFeedback, StudentFeedbackResponseDTO.class);
    }

    public StudentFeedbackResponseDTO updateFeedback(Integer feedbackID, StudentFeedbackRequestDTO feedbackRequestDTO) {
        StudentFeedback feedback = studentFeedbackRepo.findById(feedbackID)
                .orElseThrow(() -> new RuntimeException("Feedback with ID " + feedbackID + " not found"));

        modelMapper.map(feedbackRequestDTO, feedback);
        StudentFeedback updatedFeedback = studentFeedbackRepo.save(feedback);
        return modelMapper.map(updatedFeedback, StudentFeedbackResponseDTO.class);
    }

    public void deleteFeedback(Integer feedbackID) {
        if (!studentFeedbackRepo.existsById(feedbackID)) {
            throw new RuntimeException("Feedback with ID " + feedbackID + " not found");
        }
        studentFeedbackRepo.deleteById(feedbackID);
    }

    public StudentFeedbackResponseDTO getFeedbackById(Integer feedbackID) {
        StudentFeedback feedback = studentFeedbackRepo.findById(feedbackID)
                .orElseThrow(() -> new RuntimeException("Feedback with ID " + feedbackID + " not found"));
        return modelMapper.map(feedback, StudentFeedbackResponseDTO.class);
    }

    public List<StudentFeedbackResponseDTO> getFeedbackByTeacher(Integer teacherID) {
        List<StudentFeedback> feedbackList = studentFeedbackRepo.findByTeacherID(teacherID);

        if (feedbackList.isEmpty()) {
            throw new RuntimeException("No feedback found for Teacher ID " + teacherID);
        }

        return feedbackList.stream()
                .map(feedback -> modelMapper.map(feedback, StudentFeedbackResponseDTO.class))
                .collect(Collectors.toList());
    }
}
