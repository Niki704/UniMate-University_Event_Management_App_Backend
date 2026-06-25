package com.unimate.repo;

import com.unimate.model.Announcement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AnnouncementRepo extends JpaRepository<Announcement, Integer> {

    List<Announcement> findByCreatedBy_Id(Integer userId);

    List<Announcement> findByTargetBatches_Id(Integer batchId);

    List<Announcement> findByTargetStudents_Id(Integer studentId);
}
