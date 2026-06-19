package com.unimate.controller;

import com.unimate.dto.LecturerDTO;
import com.unimate.model.Lecturer;
import com.unimate.service.LecturerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping(value = "api/v2/")
public class LecturerController {

    @Autowired
    private LecturerService lecturerService;

    @GetMapping("/teachers/get")
    public List<LecturerDTO> getAllLecturers() {
        return lecturerService.getAllLecturers();
    }

    @GetMapping("/teachers/get/{Id}")
    public LecturerDTO getLecturerById(@PathVariable int Id) {
        return lecturerService.getLecturerById(Id);
    }

    @PostMapping("/teachers/add")
    public ResponseEntity<String> saveLecturer(@RequestBody LecturerDTO lecturerDTO) {
        lecturerService.saveLecturer(lecturerDTO);
        return ResponseEntity.status(201).body("Teacher created successfully");
    }

    @PostMapping("/teachers/bulk")
    public ResponseEntity<List<Lecturer>> addBulk(@RequestBody List<Lecturer> lecturers) {
        List<Lecturer> savedLecturers = lecturerService.addBulkLecturers(lecturers);
        return ResponseEntity.ok(savedLecturers);
    }

    @PutMapping("/teachers/update/{id}")
    public ResponseEntity<String> updateLecturer(@PathVariable Integer id, @RequestBody LecturerDTO lecturerDTO) {
        lecturerService.updateLecturer(id, lecturerDTO);
        return ResponseEntity.ok("Teacher/Consultant updated successfully");
    }

    @DeleteMapping("/teachers/delete/{id}")
    public ResponseEntity<String> deleteLecturerById(@PathVariable Integer id) {
        lecturerService.deleteLecturerById(id);
        return ResponseEntity.ok("Teacher/Consultant deleted successfully");
    }
}
