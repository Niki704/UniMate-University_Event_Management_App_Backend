package com.unimate.controller;

import com.unimate.dto.AnnouncementRequestDTO;
import com.unimate.dto.AnnouncementResponseDTO;
import com.unimate.security.UserPrincipal;
import com.unimate.service.AnnouncementService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/announcements")
@RequiredArgsConstructor
public class AnnouncementController {

    private final AnnouncementService announcementService;

    @PostMapping
    @PreAuthorize("hasAnyRole('LECTURER','ADMIN')")
    public ResponseEntity<AnnouncementResponseDTO> create(@AuthenticationPrincipal UserPrincipal creator,
            @Valid @RequestBody AnnouncementRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(announcementService.createAnnouncement(creator.getId(), dto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<AnnouncementResponseDTO> getById(@PathVariable Integer id) {
        return ResponseEntity.ok(announcementService.getAnnouncementById(id));
    }

    @GetMapping
    public ResponseEntity<List<AnnouncementResponseDTO>> getAll(
            @RequestParam(required = false) Integer batchId) {

        if (batchId != null) {
            return ResponseEntity.ok(announcementService.getAnnouncementsByBatch(batchId));
        }
        return ResponseEntity.ok(announcementService.getAllAnnouncements());
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('LECTURER','ADMIN')")
    public ResponseEntity<Void> delete(@PathVariable Integer id, @AuthenticationPrincipal UserPrincipal requester) {
        announcementService.deleteAnnouncement(id, requester.getId(), requester.getRole());
        return ResponseEntity.noContent().build();
    }
}
