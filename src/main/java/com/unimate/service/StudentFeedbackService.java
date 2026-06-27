package com.unimate.service;

import com.unimate.dto.StudentFeedbackRequestDTO;
import com.unimate.dto.StudentFeedbackResponseDTO;
import com.unimate.exception.ResourceNotFoundException;
import com.unimate.exception.UnauthorizedActionException;
import com.unimate.model.Batch;
import com.unimate.model.Lecturer;
import com.unimate.model.Student;
import com.unimate.model.StudentFeedback;
import com.unimate.repo.LecturerRepo;
import com.unimate.repo.StudentFeedbackRepo;
import com.unimate.repo.StudentRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StudentFeedbackService {

    private final StudentFeedbackRepo studentFeedbackRepo;
    private final LecturerRepo lecturerRepo;
    private final StudentRepo studentRepo;

    @Transactional
    public StudentFeedbackResponseDTO createFeedback(Integer lecturerId, StudentFeedbackRequestDTO dto) {
        Lecturer lecturer = lecturerRepo.findById(lecturerId)
                .orElseThrow(() -> new ResourceNotFoundException("Lecturer not found: " + lecturerId));

        Student student = studentRepo.findById(dto.getStudentId())
                .orElseThrow(() -> new ResourceNotFoundException("Student not found: " + dto.getStudentId()));

        Set<Integer> assignedBatchIds = lecturer.getBatches() == null ? Set.of()
                : lecturer.getBatches().stream().map(Batch::getId).collect(Collectors.toSet());

        if (student.getBatch() == null || !assignedBatchIds.contains(student.getBatch().getId())) {
            throw new UnauthorizedActionException(
                    "You are not assigned to this student's batch, so you cannot give them feedback");
        }

        StudentFeedback feedback = new StudentFeedback();
        feedback.setDate(dto.getDate());
        feedback.setContent(dto.getContent());
        feedback.setStudentRating(dto.getStudentRating());
        feedback.setLecturer(lecturer);
        feedback.setStudent(student);

        StudentFeedback saved = studentFeedbackRepo.save(feedback);
        return toResponseDTO(saved);
    }

    @Transactional(readOnly = true)
    public List<StudentFeedbackResponseDTO> getFeedbackByStudent(Integer studentId) {
        return studentFeedbackRepo.findByStudent_Id(studentId).stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<StudentFeedbackResponseDTO> getFeedbackByLecturer(Integer lecturerId) {
        return studentFeedbackRepo.findByLecturer_Id(lecturerId).stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    public StudentFeedbackResponseDTO toResponseDTO(StudentFeedback feedback) {
        StudentFeedbackResponseDTO dto = new StudentFeedbackResponseDTO();
        dto.setId(feedback.getId());
        dto.setDate(feedback.getDate());
        dto.setContent(feedback.getContent());
        dto.setStudentRating(feedback.getStudentRating());
        dto.setLecturerId(feedback.getLecturer().getId());
        dto.setLecturerName(feedback.getLecturer().getFirstName() + " " + feedback.getLecturer().getLastName());
        dto.setStudentId(feedback.getStudent().getId());
        dto.setStudentName(feedback.getStudent().getFirstName() + " " + feedback.getStudent().getLastName());
        return dto;
    }
}
