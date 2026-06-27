package com.unimate.service;

import com.unimate.dto.BatchRequestDTO;
import com.unimate.dto.BatchResponseDTO;
import com.unimate.dto.LecturerResponseDTO;
import com.unimate.dto.StudentResponseDTO;
import com.unimate.exception.DuplicateResourceException;
import com.unimate.exception.ResourceNotFoundException;
import com.unimate.model.Batch;
import com.unimate.model.Lecturer;
import com.unimate.model.Student;
import com.unimate.repo.BatchRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BatchService {

    private final BatchRepo batchRepo;
    private final StudentService studentService;
    private final LecturerService lecturerService;

    @Transactional
    public BatchResponseDTO createBatch(BatchRequestDTO dto) {
        if (batchRepo.findByBatchCode(dto.getBatchCode()).isPresent()) {
            throw new DuplicateResourceException("Batch code already in use: " + dto.getBatchCode());
        }

        Batch batch = new Batch();
        batch.setBatchCode(dto.getBatchCode());
        batch.setName(dto.getName());
        batch.setBatchType(dto.getBatchType());
        batch.setStartYear(dto.getStartYear());
        batch.setEndYear(dto.getEndYear());
        batch.setLecturers(new HashSet<>());
        batch.setStudents(new HashSet<>());

        try {
            Batch saved = batchRepo.save(batch);
            return toResponseDTO(saved);
        } catch (DataIntegrityViolationException e) {
            throw new DuplicateResourceException("Batch code already in use: " + dto.getBatchCode());
        }
    }

    @Transactional(readOnly = true)
    public BatchResponseDTO getBatchById(Integer id) {
        Batch batch = batchRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Batch not found: " + id));
        return toResponseDTO(batch);
    }

    @Transactional(readOnly = true)
    public List<BatchResponseDTO> getAllBatches() {
        return batchRepo.findAll().stream().map(this::toResponseDTO).collect(Collectors.toList());
    }

    @Transactional
    public BatchResponseDTO updateBatch(Integer id, BatchRequestDTO dto) {
        Batch batch = batchRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Batch not found: " + id));

        if (!batch.getBatchCode().equals(dto.getBatchCode())) {
            batchRepo.findByBatchCode(dto.getBatchCode()).ifPresent(existing -> {
                throw new DuplicateResourceException("Batch code already in use: " + dto.getBatchCode());
            });
        }

        batch.setBatchCode(dto.getBatchCode());
        batch.setName(dto.getName());
        batch.setBatchType(dto.getBatchType());
        batch.setStartYear(dto.getStartYear());
        batch.setEndYear(dto.getEndYear());

        try {
            Batch saved = batchRepo.save(batch);
            return toResponseDTO(saved);
        } catch (DataIntegrityViolationException e) {
            throw new DuplicateResourceException("Batch code already in use: " + dto.getBatchCode());
        }
    }

    @Transactional(readOnly = true)
    public List<StudentResponseDTO> getStudentsOfBatch(Integer batchId) {
        Batch batch = batchRepo.findById(batchId)
                .orElseThrow(() -> new ResourceNotFoundException("Batch not found: " + batchId));
        Set<Student> students = batch.getStudents() == null ? Set.of() : batch.getStudents();
        return students.stream().map(studentService::toResponseDTO).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<LecturerResponseDTO> getLecturersOfBatch(Integer batchId) {
        Batch batch = batchRepo.findById(batchId)
                .orElseThrow(() -> new ResourceNotFoundException("Batch not found: " + batchId));
        Set<Lecturer> lecturers = batch.getLecturers() == null ? Set.of() : batch.getLecturers();
        return lecturers.stream().map(lecturerService::toResponseDTO).collect(Collectors.toList());
    }

    public BatchResponseDTO toResponseDTO(Batch batch) {
        BatchResponseDTO dto = new BatchResponseDTO();
        dto.setId(batch.getId());
        dto.setBatchCode(batch.getBatchCode());
        dto.setName(batch.getName());
        dto.setBatchType(batch.getBatchType());
        dto.setStartYear(batch.getStartYear());
        dto.setEndYear(batch.getEndYear());

        Set<String> lecturerNames = batch.getLecturers() == null ? Set.of()
                : batch.getLecturers().stream()
                        .map(l -> l.getFirstName() + " " + l.getLastName())
                        .collect(Collectors.toSet());
        dto.setLecturerNames(lecturerNames);
        dto.setStudentCount(batch.getStudents() == null ? 0 : batch.getStudents().size());

        return dto;
    }
}
