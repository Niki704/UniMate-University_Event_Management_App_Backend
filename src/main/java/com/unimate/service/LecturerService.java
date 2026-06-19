package com.unimate.service;

import com.unimate.dto.LecturerDTO;
import com.unimate.model.Lecturer;
import com.unimate.repo.LecturerRepo;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class LecturerService {

    @Autowired
    private LecturerRepo lecturerRepo;

    @Autowired
    private ModelMapper modelMapper;

    public List<LecturerDTO> getAllLecturers() {
        List<Lecturer>lecturerList = lecturerRepo.findAll();
        return modelMapper.map(lecturerList, new TypeToken<List<LecturerDTO>>() {}.getType());
    }

    public LecturerDTO getLecturerById(Integer Id) {
        Lecturer lecturer = lecturerRepo.findLecturerById(Id);
        return modelMapper.map(lecturer, LecturerDTO.class);
    }

    public void saveLecturer(LecturerDTO lecturerDTO) {
        lecturerRepo.save(modelMapper.map(lecturerDTO, Lecturer.class));
    }

    public List<Lecturer> addBulkLecturers(List<Lecturer> lecturers) {
        return lecturerRepo.saveAll(lecturers);
    }

    public void updateLecturer(Integer id, LecturerDTO lecturerDTO) {
        Lecturer lecturer = lecturerRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Lecturer with ID " + id + " not found"));

        modelMapper.map(lecturerDTO, lecturer);
        lecturerRepo.save(lecturer);
    }

    public void deleteLecturerById(Integer id) {
        lecturerRepo.deleteById(id);
    }
}
