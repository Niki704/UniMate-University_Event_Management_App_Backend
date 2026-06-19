package com.unimate.service;

import com.unimate.dto.StudentDTO;
import com.unimate.model.Student;
import com.unimate.repo.StudentRepo;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class StudentService {

    @Autowired
    private StudentRepo studentRepo;

    @Autowired
    private ModelMapper modelMapper;

    public List<StudentDTO> getAllStudents() {
        List<Student> studentList = studentRepo.findAll();
        return modelMapper.map(studentList, new TypeToken<List<StudentDTO>>() {}.getType());
    }

    public StudentDTO getStudentById(Integer id) {
        Student student = studentRepo.findStudentById(id);
        return modelMapper.map(student, StudentDTO.class);
    }

    public void saveStudent(StudentDTO studentDTO) {
        studentRepo.save(modelMapper.map(studentDTO, Student.class));
    }

    public List<Student> addBulkStudents(List<Student> students) {
        return studentRepo.saveAll(students);
    }

    public void updateStudent(Integer id, StudentDTO studentDTO) {
        Student student = studentRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Student with ID " + id + " not found"));

        modelMapper.map(studentDTO, student);
        studentRepo.save(student);
    }

    public void deleteStudentById(Integer id) {
        studentRepo.deleteById(id);
    }
}
