package com.unimate.service;

import com.unimate.dto.LecturerRegisterRequestDTO;
import com.unimate.dto.LecturerResponseDTO;
import com.unimate.dto.LecturerUpdateRequestDTO;
import com.unimate.enums.AccountStatus;
import com.unimate.enums.Role;
import com.unimate.exception.DuplicateResourceException;
import com.unimate.exception.InvalidStateException;
import com.unimate.exception.ResourceNotFoundException;
import com.unimate.exception.UnauthorizedActionException;
import com.unimate.model.Admin;
import com.unimate.model.Batch;
import com.unimate.model.Lecturer;
import com.unimate.repo.AdminRepo;
import com.unimate.repo.BatchRepo;
import com.unimate.repo.LecturerRepo;
import com.unimate.repo.ProfileRepo;
import com.unimate.repo.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LecturerService {

    private final LecturerRepo lecturerRepo;
    private final BatchRepo batchRepo;
    private final AdminRepo adminRepo;
    private final ProfileRepo profileRepo;
    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public LecturerResponseDTO registerLecturer(LecturerRegisterRequestDTO dto) {
        if (userRepo.existsByEmail(dto.getEmail())) {
            throw new DuplicateResourceException("Email already in use: " + dto.getEmail());
        }

        Lecturer lecturer = new Lecturer();
        lecturer.setFirstName(dto.getFirstName());
        lecturer.setLastName(dto.getLastName());
        lecturer.setEmail(dto.getEmail());
        lecturer.setPassword(passwordEncoder.encode(dto.getPassword()));
        lecturer.setPhoneNumber(dto.getPhoneNumber());
        lecturer.setRole(Role.LECTURER);
        lecturer.setAccountStatus(AccountStatus.PENDING);
        lecturer.setDepartment(dto.getDepartment());
        lecturer.setBatches(new HashSet<>());
        lecturer.setCreatedDate(LocalDateTime.now());
        lecturer.setLastUpdated(LocalDateTime.now());

        try {
            Lecturer saved = lecturerRepo.save(lecturer);
            return toResponseDTO(saved);
        } catch (DataIntegrityViolationException e) {
            throw new DuplicateResourceException("Email already in use: " + dto.getEmail());
        }
    }

    @Transactional
    public LecturerResponseDTO approveLecturer(Integer lecturerId, Integer adminId) {
        Lecturer lecturer = lecturerRepo.findById(lecturerId)
                .orElseThrow(() -> new ResourceNotFoundException("Lecturer not found: " + lecturerId));

        if (lecturer.getAccountStatus() != AccountStatus.PENDING) {
            throw new InvalidStateException("Only PENDING accounts can be approved");
        }

        Admin admin = adminRepo.findById(adminId)
                .orElseThrow(() -> new ResourceNotFoundException("Admin not found: " + adminId));

        lecturer.setVerifiedBy(admin);
        lecturer.setAccountStatus(AccountStatus.ACTIVE);
        lecturer.setLastUpdated(LocalDateTime.now());

        Lecturer saved = lecturerRepo.save(lecturer);
        return toResponseDTO(saved);
    }

    @Transactional
    public LecturerResponseDTO rejectLecturer(Integer lecturerId) {
        Lecturer lecturer = lecturerRepo.findById(lecturerId)
                .orElseThrow(() -> new ResourceNotFoundException("Lecturer not found: " + lecturerId));

        if (lecturer.getAccountStatus() != AccountStatus.PENDING) {
            throw new InvalidStateException("Only PENDING accounts can be rejected");
        }

        LecturerResponseDTO snapshot = toResponseDTO(lecturer);

        profileRepo.findByUser_Id(lecturerId).ifPresent(profileRepo::delete);
        lecturerRepo.delete(lecturer);

        return snapshot;
    }

    @Transactional(readOnly = true)
    public LecturerResponseDTO getLecturerById(Integer id) {
        Lecturer lecturer = lecturerRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Lecturer not found: " + id));
        return toResponseDTO(lecturer);
    }

    @Transactional(readOnly = true)
    public List<LecturerResponseDTO> getAllLecturers() {
        return lecturerRepo.findAll().stream().map(this::toResponseDTO).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<LecturerResponseDTO> getPendingLecturers() {
        return lecturerRepo.findByAccountStatus(AccountStatus.PENDING).stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public LecturerResponseDTO updateLecturer(Integer lecturerId, Integer requesterId, Role requesterRole,
            LecturerUpdateRequestDTO dto) {
        Lecturer lecturer = lecturerRepo.findById(lecturerId)
                .orElseThrow(() -> new ResourceNotFoundException("Lecturer not found: " + lecturerId));

        if (!lecturerId.equals(requesterId) && requesterRole != Role.ADMIN) {
            throw new UnauthorizedActionException("You can only update your own lecturer record");
        }

        lecturer.setFirstName(dto.getFirstName());
        lecturer.setLastName(dto.getLastName());
        lecturer.setPhoneNumber(dto.getPhoneNumber());
        lecturer.setDepartment(dto.getDepartment());
        lecturer.setLastUpdated(LocalDateTime.now());

        Lecturer saved = lecturerRepo.save(lecturer);
        return toResponseDTO(saved);
    }

    @Transactional
    public LecturerResponseDTO assignBatch(Integer lecturerId, Integer batchId) {
        Lecturer lecturer = lecturerRepo.findById(lecturerId)
                .orElseThrow(() -> new ResourceNotFoundException("Lecturer not found: " + lecturerId));

        Batch batch = batchRepo.findById(batchId)
                .orElseThrow(() -> new ResourceNotFoundException("Batch not found: " + batchId));

        if (lecturer.getBatches() == null) {
            lecturer.setBatches(new HashSet<>());
        }

        boolean alreadyAssigned = lecturer.getBatches().stream()
                .anyMatch(b -> b.getId().equals(batchId));

        if (alreadyAssigned) {
            throw new DuplicateResourceException("Lecturer is already assigned to this batch");
        }

        lecturer.getBatches().add(batch);
        Lecturer saved = lecturerRepo.save(lecturer);
        return toResponseDTO(saved);
    }

    @Transactional
    public LecturerResponseDTO unassignBatch(Integer lecturerId, Integer batchId) {
        Lecturer lecturer = lecturerRepo.findById(lecturerId)
                .orElseThrow(() -> new ResourceNotFoundException("Lecturer not found: " + lecturerId));

        boolean removed = lecturer.getBatches() != null
                && lecturer.getBatches().removeIf(b -> b.getId().equals(batchId));

        if (!removed) {
            throw new ResourceNotFoundException("Lecturer is not assigned to batch: " + batchId);
        }

        Lecturer saved = lecturerRepo.save(lecturer);
        return toResponseDTO(saved);
    }

    public LecturerResponseDTO toResponseDTO(Lecturer lecturer) {
        LecturerResponseDTO dto = new LecturerResponseDTO();
        dto.setId(lecturer.getId());
        dto.setFirstName(lecturer.getFirstName());
        dto.setLastName(lecturer.getLastName());
        dto.setEmail(lecturer.getEmail());
        dto.setPhoneNumber(lecturer.getPhoneNumber());
        dto.setRole(lecturer.getRole());
        dto.setAccountStatus(lecturer.getAccountStatus());
        dto.setDepartment(lecturer.getDepartment());

        Set<Batch> batches = lecturer.getBatches() == null ? Set.of() : lecturer.getBatches();
        dto.setBatchIds(batches.stream().map(Batch::getId).collect(Collectors.toSet()));
        dto.setBatchCodes(batches.stream().map(Batch::getBatchCode).collect(Collectors.toSet()));

        if (lecturer.getVerifiedBy() != null) {
            dto.setVerifiedByName(
                    lecturer.getVerifiedBy().getFirstName() + " " + lecturer.getVerifiedBy().getLastName());
        }

        dto.setCreatedDate(lecturer.getCreatedDate());
        dto.setLastLogin(lecturer.getLastLogin());
        return dto;
    }
}
