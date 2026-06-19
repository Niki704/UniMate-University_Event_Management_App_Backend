package com.unimate.controller;

import jakarta.validation.Valid;
import com.unimate.dto.AnnouncementRequestDTO;
import com.unimate.dto.AnnouncementResponseDTO;
import com.unimate.service.AnnouncementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping(value = "api/v7/announcements/")
public class AnnouncementController {

    @Autowired
    private AnnouncementService announcementService;

    @PostMapping("/add")
    public ResponseEntity<AnnouncementResponseDTO> saveAnnouncement(@RequestBody @Valid AnnouncementRequestDTO requestDTO) {
        AnnouncementResponseDTO responseDTO = announcementService.saveAnnouncement(requestDTO);
        return ResponseEntity.ok(responseDTO);
    }

    @GetMapping("/teacher/{teacherID}")
    public ResponseEntity<List<AnnouncementResponseDTO>> getAnnouncementsByTeacher(@PathVariable Integer teacherID) {
        List<AnnouncementResponseDTO> responseDTOs = announcementService.getAnnouncementsByTeacher(teacherID);
        return ResponseEntity.ok(responseDTOs);
    }

    @GetMapping("/student/{studentID}")
    public ResponseEntity<List<AnnouncementResponseDTO>> getAnnouncementsForStudents(@PathVariable Integer studentID) {
        List<AnnouncementResponseDTO> responseDTOs = announcementService.getAnnouncementsForStudents(studentID);
        return ResponseEntity.ok(responseDTOs);
    }

    @GetMapping("/batch/{batchID}")
    public ResponseEntity<List<AnnouncementResponseDTO>> getAnnouncementsForBatch(@PathVariable Integer batchID) {
        List<AnnouncementResponseDTO> responseDTOs = announcementService.getAnnouncementsForBatch(batchID);
        return ResponseEntity.ok(responseDTOs);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<AnnouncementResponseDTO> updateAnnouncement(@PathVariable Integer id, @RequestBody @Valid AnnouncementRequestDTO requestDTO) {
        AnnouncementResponseDTO responseDTO = announcementService.updateAnnouncement(id, requestDTO);
        return ResponseEntity.ok(responseDTO);
    }
}
