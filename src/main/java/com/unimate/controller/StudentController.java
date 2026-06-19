package com.unimate.controller;

import com.unimate.dto.StudentDTO;
import com.unimate.model.Student;
import com.unimate.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping(value = "api/v3/")
public class StudentController {

    @Autowired
    private StudentService studentService;

    @GetMapping("/students/get")
    public List<StudentDTO> getAllStudents() {
        return studentService.getAllStudents();
    }

    @GetMapping("/students/get/{id}")
    public StudentDTO getStudentById(@PathVariable int id) {
        return studentService.getStudentById(id);
    }

    @PostMapping("/students/add")
    public ResponseEntity<String> saveStudent(@RequestBody StudentDTO studentDTO) {
        studentService.saveStudent(studentDTO);
        return ResponseEntity.status(201).body("Student created successfully");
    }

    @PostMapping("/students/bulk")
    public ResponseEntity<List<Student>> addBulk(@RequestBody List<Student> students) {
        List<Student> savedStudents = studentService.addBulkStudents(students);
        return ResponseEntity.ok(savedStudents);
    }

    @PutMapping("/students/update/{id}")
    public ResponseEntity<String> updateStudent(@PathVariable Integer id, @RequestBody StudentDTO studentDTO) {
        studentService.updateStudent(id, studentDTO);
        return ResponseEntity.ok("Student updated successfully");
    }

    @DeleteMapping("/students/delete/{id}")
    public ResponseEntity<String> deleteStudentById(@PathVariable Integer id) {
        studentService.deleteStudentById(id);
        return ResponseEntity.ok("Student deleted successfully");
    }
}
