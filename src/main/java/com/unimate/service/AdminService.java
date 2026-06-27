package com.unimate.service;

import com.unimate.dto.AdminResponseDTO;
import com.unimate.dto.PendingApprovalsResponseDTO;
import com.unimate.exception.ResourceNotFoundException;
import com.unimate.model.Admin;
import com.unimate.repo.AdminRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AdminService {

    private final AdminRepo adminRepo;
    private final StudentService studentService;
    private final LecturerService lecturerService;

    @Transactional(readOnly = true)
    public AdminResponseDTO getAdminById(Integer id) {
        Admin admin = adminRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Admin not found: " + id));
        return toResponseDTO(admin);
    }

    @Transactional(readOnly = true)
    public List<AdminResponseDTO> getAllAdmins() {
        return adminRepo.findAll().stream().map(this::toResponseDTO).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public PendingApprovalsResponseDTO getPendingApprovals() {
        PendingApprovalsResponseDTO dto = new PendingApprovalsResponseDTO();
        dto.setPendingStudents(studentService.getPendingStudents());
        dto.setPendingLecturers(lecturerService.getPendingLecturers());
        return dto;
    }

    public AdminResponseDTO toResponseDTO(Admin admin) {
        AdminResponseDTO dto = new AdminResponseDTO();
        dto.setId(admin.getId());
        dto.setFirstName(admin.getFirstName());
        dto.setLastName(admin.getLastName());
        dto.setEmail(admin.getEmail());
        dto.setPhoneNumber(admin.getPhoneNumber());
        dto.setRole(admin.getRole());
        dto.setAccountStatus(admin.getAccountStatus());
        dto.setCreatedDate(admin.getCreatedDate());
        return dto;
    }
}
