package com.unimate.controller;

import com.unimate.dto.BatchRequestDTO;
import com.unimate.dto.BatchResponseDTO;
import com.unimate.dto.LecturerResponseDTO;
import com.unimate.dto.StudentResponseDTO;
import com.unimate.service.BatchService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/batches")
@RequiredArgsConstructor
public class BatchController {

    private final BatchService batchService;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<BatchResponseDTO> create(@Valid @RequestBody BatchRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(batchService.createBatch(dto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<BatchResponseDTO> getById(@PathVariable Integer id) {
        return ResponseEntity.ok(batchService.getBatchById(id));
    }

    @GetMapping
    public ResponseEntity<List<BatchResponseDTO>> getAll() {
        return ResponseEntity.ok(batchService.getAllBatches());
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<BatchResponseDTO> update(@PathVariable Integer id, @Valid @RequestBody BatchRequestDTO dto) {
        return ResponseEntity.ok(batchService.updateBatch(id, dto));
    }

    @GetMapping("/{id}/students")
    public ResponseEntity<List<StudentResponseDTO>> getStudents(@PathVariable Integer id) {
        return ResponseEntity.ok(batchService.getStudentsOfBatch(id));
    }

    @GetMapping("/{id}/lecturers")
    public ResponseEntity<List<LecturerResponseDTO>> getLecturers(@PathVariable Integer id) {
        return ResponseEntity.ok(batchService.getLecturersOfBatch(id));
    }
}
