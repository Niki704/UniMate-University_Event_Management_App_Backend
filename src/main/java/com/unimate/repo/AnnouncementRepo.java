package com.unimate.repo;

import com.unimate.model.Announcement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AnnouncementRepo extends JpaRepository<Announcement, Integer> {

    List<Announcement> findByTeacherID(Integer teacherID);

    List<Announcement> findByStudentIDsContaining(Integer studentID);

    List<Announcement> findByBatchIDsContaining(Integer batchID);

    @Query(value ="SELECT * FROM Announcement WHERE id = ?1", nativeQuery = true)
    Optional<Announcement> findAnnouncementById(int id);
}
