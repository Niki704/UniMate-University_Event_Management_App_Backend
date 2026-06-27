package com.unimate.controller;

import com.unimate.dto.StudentApproveRequestDTO;
import com.unimate.dto.StudentRegisterRequestDTO;
import com.unimate.dto.StudentResponseDTO;
import com.unimate.dto.StudentUpdateRequestDTO;
import com.unimate.security.UserPrincipal;
import com.unimate.service.StudentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/students")
@RequiredArgsConstructor
public class StudentController {

    private final StudentService studentService;

    @PostMapping("/register")
    public ResponseEntity<StudentResponseDTO> register(@Valid @RequestBody StudentRegisterRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(studentService.registerStudent(dto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<StudentResponseDTO> getById(@PathVariable Integer id) {
        return ResponseEntity.ok(studentService.getStudentById(id));
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<StudentResponseDTO>> getAll() {
        return ResponseEntity.ok(studentService.getAllStudents());
    }

    @PatchMapping("/{id}/approve")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<StudentResponseDTO> approve(@PathVariable Integer id,
            @AuthenticationPrincipal UserPrincipal admin,
            @Valid @RequestBody StudentApproveRequestDTO dto) {
        return ResponseEntity.ok(studentService.approveStudent(id, admin.getId(), dto));
    }

    @PostMapping("/{id}/reject")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<StudentResponseDTO> reject(@PathVariable Integer id) {
        return ResponseEntity.ok(studentService.rejectStudent(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<StudentResponseDTO> update(@PathVariable Integer id,
            @AuthenticationPrincipal UserPrincipal requester,
            @Valid @RequestBody StudentUpdateRequestDTO dto) {
        return ResponseEntity.ok(studentService.updateStudent(id, requester.getId(), requester.getRole(), dto));
    }
}
