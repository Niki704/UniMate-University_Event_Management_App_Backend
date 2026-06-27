package com.unimate.service;

import com.unimate.dto.StudentApproveRequestDTO;
import com.unimate.dto.StudentRegisterRequestDTO;
import com.unimate.dto.StudentResponseDTO;
import com.unimate.dto.StudentUpdateRequestDTO;
import com.unimate.enums.AccountStatus;
import com.unimate.enums.Role;
import com.unimate.exception.DuplicateResourceException;
import com.unimate.exception.InvalidStateException;
import com.unimate.exception.ResourceNotFoundException;
import com.unimate.exception.UnauthorizedActionException;
import com.unimate.model.Admin;
import com.unimate.model.Batch;
import com.unimate.model.Student;
import com.unimate.repo.AdminRepo;
import com.unimate.repo.BatchRepo;
import com.unimate.repo.ProfileRepo;
import com.unimate.repo.StudentRepo;
import com.unimate.repo.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StudentService {

    private final StudentRepo studentRepo;
    private final BatchRepo batchRepo;
    private final AdminRepo adminRepo;
    private final ProfileRepo profileRepo;
    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public StudentResponseDTO registerStudent(StudentRegisterRequestDTO dto) {
        if (userRepo.existsByEmail(dto.getEmail())) {
            throw new DuplicateResourceException("Email already in use: " + dto.getEmail());
        }

        Student student = new Student();
        student.setFirstName(dto.getFirstName());
        student.setLastName(dto.getLastName());
        student.setEmail(dto.getEmail());
        student.setPassword(passwordEncoder.encode(dto.getPassword()));
        student.setPhoneNumber(dto.getPhoneNumber());
        student.setRole(Role.STUDENT);
        student.setAccountStatus(AccountStatus.PENDING);
        student.setEnrollmentYear(dto.getEnrollmentYear());
        student.setCreatedDate(LocalDateTime.now());
        student.setLastUpdated(LocalDateTime.now());

        try {
            Student saved = studentRepo.save(student);
            return toResponseDTO(saved);
        } catch (DataIntegrityViolationException e) {
            throw new DuplicateResourceException("Email already in use: " + dto.getEmail());
        }
    }

    @Transactional
    public StudentResponseDTO approveStudent(Integer studentId, Integer adminId, StudentApproveRequestDTO dto) {
        Student student = studentRepo.findById(studentId)
                .orElseThrow(() -> new ResourceNotFoundException("Student not found: " + studentId));

        if (student.getAccountStatus() != AccountStatus.PENDING) {
            throw new InvalidStateException("Only PENDING accounts can be approved");
        }

        Batch batch = batchRepo.findById(dto.getBatchId())
                .orElseThrow(() -> new ResourceNotFoundException("Batch not found: " + dto.getBatchId()));

        Admin admin = adminRepo.findById(adminId)
                .orElseThrow(() -> new ResourceNotFoundException("Admin not found: " + adminId));

        studentRepo.findByStudentIdNumber(dto.getStudentIdNumber()).ifPresent(existing -> {
            if (!existing.getId().equals(studentId)) {
                throw new DuplicateResourceException("Student ID number already in use: " + dto.getStudentIdNumber());
            }
        });

        student.setBatch(batch);
        student.setStudentIdNumber(dto.getStudentIdNumber());
        student.setVerifiedBy(admin);
        student.setAccountStatus(AccountStatus.ACTIVE);
        student.setLastUpdated(LocalDateTime.now());

        try {
            Student saved = studentRepo.save(student);
            return toResponseDTO(saved);
        } catch (DataIntegrityViolationException e) {
            throw new DuplicateResourceException("Student ID number already in use: " + dto.getStudentIdNumber());
        }
    }

    @Transactional
    public StudentResponseDTO rejectStudent(Integer studentId) {
        Student student = studentRepo.findById(studentId)
                .orElseThrow(() -> new ResourceNotFoundException("Student not found: " + studentId));

        if (student.getAccountStatus() != AccountStatus.PENDING) {
            throw new InvalidStateException("Only PENDING accounts can be rejected");
        }

        StudentResponseDTO snapshot = toResponseDTO(student);

        profileRepo.findByUser_Id(studentId).ifPresent(profileRepo::delete);
        studentRepo.delete(student);

        return snapshot;
    }

    @Transactional(readOnly = true)
    public StudentResponseDTO getStudentById(Integer id) {
        Student student = studentRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Student not found: " + id));
        return toResponseDTO(student);
    }

    @Transactional(readOnly = true)
    public List<StudentResponseDTO> getAllStudents() {
        return studentRepo.findAll().stream().map(this::toResponseDTO).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<StudentResponseDTO> getPendingStudents() {
        return studentRepo.findByAccountStatus(AccountStatus.PENDING).stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public StudentResponseDTO updateStudent(Integer studentId, Integer requesterId, Role requesterRole,
            StudentUpdateRequestDTO dto) {
        Student student = studentRepo.findById(studentId)
                .orElseThrow(() -> new ResourceNotFoundException("Student not found: " + studentId));

        if (!studentId.equals(requesterId) && requesterRole != Role.ADMIN) {
            throw new UnauthorizedActionException("You can only update your own student record");
        }

        student.setFirstName(dto.getFirstName());
        student.setLastName(dto.getLastName());
        student.setPhoneNumber(dto.getPhoneNumber());
        student.setEnrollmentYear(dto.getEnrollmentYear());
        student.setLastUpdated(LocalDateTime.now());

        Student saved = studentRepo.save(student);
        return toResponseDTO(saved);
    }

    public StudentResponseDTO toResponseDTO(Student student) {
        StudentResponseDTO dto = new StudentResponseDTO();
        dto.setId(student.getId());
        dto.setFirstName(student.getFirstName());
        dto.setLastName(student.getLastName());
        dto.setEmail(student.getEmail());
        dto.setPhoneNumber(student.getPhoneNumber());
        dto.setRole(student.getRole());
        dto.setAccountStatus(student.getAccountStatus());
        dto.setEnrollmentYear(student.getEnrollmentYear());

        if (student.getBatch() != null) {
            dto.setBatchId(student.getBatch().getId());
            dto.setBatchCode(student.getBatch().getBatchCode());
        }

        dto.setStudentIdNumber(student.getStudentIdNumber());

        if (student.getVerifiedBy() != null) {
            dto.setVerifiedByName(student.getVerifiedBy().getFirstName() + " " + student.getVerifiedBy().getLastName());
        }

        dto.setCreatedDate(student.getCreatedDate());
        dto.setLastLogin(student.getLastLogin());
        return dto;
    }
}
