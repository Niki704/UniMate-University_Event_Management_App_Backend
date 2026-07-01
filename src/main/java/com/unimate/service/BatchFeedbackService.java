package com.unimate.service;

import com.unimate.dto.BatchFeedbackRequestDTO;
import com.unimate.dto.BatchFeedbackResponseDTO;
import com.unimate.exception.ResourceNotFoundException;
import com.unimate.exception.UnauthorizedActionException;
import com.unimate.model.Batch;
import com.unimate.model.BatchFeedback;
import com.unimate.model.Lecturer;
import com.unimate.repo.BatchFeedbackRepo;
import com.unimate.repo.BatchRepo;
import com.unimate.repo.LecturerRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BatchFeedbackService {

    private final BatchFeedbackRepo batchFeedbackRepo;
    private final LecturerRepo lecturerRepo;
    private final BatchRepo batchRepo;

    @Transactional
    public BatchFeedbackResponseDTO createFeedback(Integer lecturerId, BatchFeedbackRequestDTO dto) {
        Lecturer lecturer = lecturerRepo.findById(lecturerId)
                .orElseThrow(() -> new ResourceNotFoundException("Lecturer not found: " + lecturerId));

        Batch batch = batchRepo.findById(dto.getBatchId())
                .orElseThrow(() -> new ResourceNotFoundException("Batch not found: " + dto.getBatchId()));

        Set<Integer> assignedBatchIds = lecturer.getBatches() == null ? Set.of()
                : lecturer.getBatches().stream().map(Batch::getId).collect(Collectors.toSet());

        if (!assignedBatchIds.contains(batch.getId())) {
            throw new UnauthorizedActionException("You are not assigned to this batch, so you cannot give it feedback");
        }

        BatchFeedback feedback = new BatchFeedback();
        feedback.setDate(dto.getDate());
        feedback.setContent(dto.getContent());
        feedback.setBadges(dto.getBadges() == null ? new HashSet<>() : dto.getBadges());
        feedback.setLecturer(lecturer);
        feedback.setBatch(batch);

        BatchFeedback saved = batchFeedbackRepo.save(feedback);
        return toResponseDTO(saved);
    }

    @Transactional(readOnly = true)
    public List<BatchFeedbackResponseDTO> getFeedbackByBatch(Integer batchId) {
        return batchFeedbackRepo.findByBatch_Id(batchId).stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<BatchFeedbackResponseDTO> getFeedbackByLecturer(Integer lecturerId) {
        return batchFeedbackRepo.findByLecturer_Id(lecturerId).stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    public BatchFeedbackResponseDTO toResponseDTO(BatchFeedback feedback) {
        BatchFeedbackResponseDTO dto = new BatchFeedbackResponseDTO();
        dto.setId(feedback.getId());
        dto.setDate(feedback.getDate());
        dto.setContent(feedback.getContent());
        dto.setBadges(new HashSet<>(feedback.getBadges())); // ← forces the lazy load NOW, inside the transaction
        dto.setLecturerId(feedback.getLecturer().getId());
        dto.setLecturerName(feedback.getLecturer().getFirstName() + " " + feedback.getLecturer().getLastName());
        dto.setBatchId(feedback.getBatch().getId());
        dto.setBatchCode(feedback.getBatch().getBatchCode());
        return dto;
    }
}
