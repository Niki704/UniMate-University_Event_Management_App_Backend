package com.unimate.service;

import com.unimate.dto.BatchFeedbackRequestDTO;
import com.unimate.dto.BatchFeedbackResponseDTO;
import com.unimate.model.BatchFeedback;
import com.unimate.repo.BatchFeedbackRepo;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class BatchFeedbackService {

    @Autowired
    private BatchFeedbackRepo batchFeedbackRepo;

    @Autowired
    private ModelMapper modelMapper;

    public BatchFeedbackResponseDTO saveFeedback(BatchFeedbackRequestDTO feedbackRequestDTO) {
        BatchFeedback feedback = modelMapper.map(feedbackRequestDTO, BatchFeedback.class);
        BatchFeedback savedFeedback = batchFeedbackRepo.save(feedback);
        return modelMapper.map(savedFeedback, BatchFeedbackResponseDTO.class);
    }

    public BatchFeedbackResponseDTO updateFeedback(Integer feedbackID, BatchFeedbackRequestDTO feedbackRequestDTO) {
        BatchFeedback feedback = batchFeedbackRepo.findById(feedbackID)
                .orElseThrow(() -> new RuntimeException("Feedback with ID " + feedbackID + " not found"));

        modelMapper.map(feedbackRequestDTO, feedback);
        BatchFeedback updatedFeedback = batchFeedbackRepo.save(feedback);
        return modelMapper.map(updatedFeedback, BatchFeedbackResponseDTO.class);
    }

    public void deleteFeedback(Integer feedbackID) {
        if (!batchFeedbackRepo.existsById(feedbackID)) {
            throw new RuntimeException("Feedback with ID " + feedbackID + " not found");
        }
        batchFeedbackRepo.deleteById(feedbackID);
    }

    public BatchFeedbackResponseDTO getFeedbackById(Integer feedbackID) {
        BatchFeedback feedback = batchFeedbackRepo.findById(feedbackID)
                .orElseThrow(() -> new RuntimeException("Feedback with ID " + feedbackID + " not found"));
        return modelMapper.map(feedback, BatchFeedbackResponseDTO.class);
    }

    public List<BatchFeedbackResponseDTO> getFeedbackByTeacher(Integer teacherID) {
        List<BatchFeedback> feedbackList = batchFeedbackRepo.findByTeacherID(teacherID);

        if (feedbackList.isEmpty()) {
            throw new RuntimeException("No feedback found for Teacher ID " + teacherID);
        }

        return feedbackList.stream()
                .map(feedback -> modelMapper.map(feedback, BatchFeedbackResponseDTO.class))
                .collect(Collectors.toList());
    }
}
