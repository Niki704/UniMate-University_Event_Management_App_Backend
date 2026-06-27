package com.unimate.controller;

import com.unimate.dto.LecturerRegisterRequestDTO;
import com.unimate.dto.LecturerResponseDTO;
import com.unimate.dto.LecturerUpdateRequestDTO;
import com.unimate.security.UserPrincipal;
import com.unimate.service.LecturerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
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
@RequestMapping("/api/v1/lecturers")
@RequiredArgsConstructor
public class LecturerController {

    private final LecturerService lecturerService;

    @PostMapping("/register")
    public ResponseEntity<LecturerResponseDTO> register(@Valid @RequestBody LecturerRegisterRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(lecturerService.registerLecturer(dto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<LecturerResponseDTO> getById(@PathVariable Integer id) {
        return ResponseEntity.ok(lecturerService.getLecturerById(id));
    }

    @GetMapping
    public ResponseEntity<List<LecturerResponseDTO>> getAll() {
        return ResponseEntity.ok(lecturerService.getAllLecturers());
    }

    @PatchMapping("/{id}/approve")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<LecturerResponseDTO> approve(@PathVariable Integer id,
            @AuthenticationPrincipal UserPrincipal admin) {
        return ResponseEntity.ok(lecturerService.approveLecturer(id, admin.getId()));
    }

    @PostMapping("/{id}/reject")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<LecturerResponseDTO> reject(@PathVariable Integer id) {
        return ResponseEntity.ok(lecturerService.rejectLecturer(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<LecturerResponseDTO> update(@PathVariable Integer id,
            @AuthenticationPrincipal UserPrincipal requester,
            @Valid @RequestBody LecturerUpdateRequestDTO dto) {
        return ResponseEntity.ok(lecturerService.updateLecturer(id, requester.getId(), requester.getRole(), dto));
    }

    @PostMapping("/{id}/batches/{batchId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<LecturerResponseDTO> assignBatch(@PathVariable Integer id, @PathVariable Integer batchId) {
        return ResponseEntity.ok(lecturerService.assignBatch(id, batchId));
    }

    @DeleteMapping("/{id}/batches/{batchId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<LecturerResponseDTO> unassignBatch(@PathVariable Integer id, @PathVariable Integer batchId) {
        return ResponseEntity.ok(lecturerService.unassignBatch(id, batchId));
    }
}
