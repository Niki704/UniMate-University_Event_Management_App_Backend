package com.unimate.service;

import com.unimate.dto.AnnouncementRequestDTO;
import com.unimate.dto.AnnouncementResponseDTO;
import com.unimate.enums.Role;
import com.unimate.exception.InvalidStateException;
import com.unimate.exception.ResourceNotFoundException;
import com.unimate.exception.UnauthorizedActionException;
import com.unimate.model.Announcement;
import com.unimate.model.Batch;
import com.unimate.model.Lecturer;
import com.unimate.model.Student;
import com.unimate.model.User;
import com.unimate.repo.AnnouncementRepo;
import com.unimate.repo.BatchRepo;
import com.unimate.repo.StudentRepo;
import com.unimate.repo.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AnnouncementService {

    private final AnnouncementRepo announcementRepo;
    private final UserRepo userRepo;
    private final BatchRepo batchRepo;
    private final StudentRepo studentRepo;

    @Transactional
    public AnnouncementResponseDTO createAnnouncement(Integer creatorId, AnnouncementRequestDTO dto) {
        User creator = userRepo.findById(creatorId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found: " + creatorId));

        if (creator.getRole() != Role.LECTURER && creator.getRole() != Role.ADMIN) {
            throw new InvalidStateException("Only lecturers or admins can create announcements");
        }

        Set<Batch> targetBatches = resolveBatches(dto.getBatchIds());
        Set<Student> targetStudents = resolveStudents(dto.getStudentIds());

        if (creator instanceof Lecturer lecturerCreator) {
            Set<Integer> assignedBatchIds = lecturerCreator.getBatches() == null ? Set.of()
                    : lecturerCreator.getBatches().stream().map(Batch::getId).collect(Collectors.toSet());

            for (Batch batch : targetBatches) {
                if (!assignedBatchIds.contains(batch.getId())) {
                    throw new UnauthorizedActionException(
                            "You can only post announcements to batches you are assigned to (batch: "
                                    + batch.getBatchCode() + ")");
                }
            }

            for (Student student : targetStudents) {
                if (student.getBatch() == null || !assignedBatchIds.contains(student.getBatch().getId())) {
                    throw new UnauthorizedActionException(
                            "You can only target students within batches you are assigned to (student: "
                                    + student.getId() + ")");
                }
            }
        }

        Announcement announcement = new Announcement();
        announcement.setTitle(dto.getTitle());
        announcement.setContent(dto.getContent());
        announcement.setDate(dto.getDate());
        announcement.setExpiryDate(dto.getExpiryDate());
        announcement.setCreatedBy(creator);
        announcement.setTargetBatches(targetBatches);
        announcement.setTargetStudents(targetStudents);

        Announcement saved = announcementRepo.save(announcement);
        return toResponseDTO(saved);
    }

    @Transactional(readOnly = true)
    public AnnouncementResponseDTO getAnnouncementById(Integer id) {
        Announcement announcement = announcementRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Announcement not found: " + id));
        return toResponseDTO(announcement);
    }

    @Transactional(readOnly = true)
    public List<AnnouncementResponseDTO> getAllAnnouncements() {
        return announcementRepo.findAll().stream().map(this::toResponseDTO).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<AnnouncementResponseDTO> getAnnouncementsByBatch(Integer batchId) {
        return announcementRepo.findByTargetBatches_Id(batchId).stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public void deleteAnnouncement(Integer announcementId, Integer requesterId, Role requesterRole) {
        Announcement announcement = announcementRepo.findById(announcementId)
                .orElseThrow(() -> new ResourceNotFoundException("Announcement not found: " + announcementId));

        boolean isOwner = announcement.getCreatedBy().getId().equals(requesterId);
        boolean isAdmin = requesterRole == Role.ADMIN;

        if (!isOwner && !isAdmin) {
            throw new UnauthorizedActionException("You can only delete announcements you created");
        }

        announcementRepo.delete(announcement);
    }

    private Set<Batch> resolveBatches(Set<Integer> batchIds) {
        if (batchIds == null || batchIds.isEmpty()) {
            return new HashSet<>();
        }
        return batchIds.stream()
                .map(id -> batchRepo.findById(id)
                        .orElseThrow(() -> new ResourceNotFoundException("Batch not found: " + id)))
                .collect(Collectors.toSet());
    }

    private Set<Student> resolveStudents(Set<Integer> studentIds) {
        if (studentIds == null || studentIds.isEmpty()) {
            return new HashSet<>();
        }
        return studentIds.stream()
                .map(id -> studentRepo.findById(id)
                        .orElseThrow(() -> new ResourceNotFoundException("Student not found: " + id)))
                .collect(Collectors.toSet());
    }

    public AnnouncementResponseDTO toResponseDTO(Announcement announcement) {
        AnnouncementResponseDTO dto = new AnnouncementResponseDTO();
        dto.setId(announcement.getId());
        dto.setTitle(announcement.getTitle());
        dto.setContent(announcement.getContent());
        dto.setDate(announcement.getDate());
        dto.setExpiryDate(announcement.getExpiryDate());

        User creator = announcement.getCreatedBy();
        dto.setCreatedByName(creator.getFirstName() + " " + creator.getLastName());
        dto.setCreatedByRole(creator.getRole());

        Set<String> batchCodes = announcement.getTargetBatches() == null ? Set.of()
                : announcement.getTargetBatches().stream().map(Batch::getBatchCode).collect(Collectors.toSet());
        dto.setTargetBatchCodes(batchCodes);

        Set<Integer> studentIds = announcement.getTargetStudents() == null ? Set.of()
                : announcement.getTargetStudents().stream().map(Student::getId).collect(Collectors.toSet());
        dto.setTargetStudentIds(studentIds);

        return dto;
    }
}
