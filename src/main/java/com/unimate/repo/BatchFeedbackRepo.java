package com.unimate.repo;

import com.unimate.model.BatchFeedback;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BatchFeedbackRepo extends JpaRepository<BatchFeedback, Integer> {

    List<BatchFeedback> findByLecturer_Id(Integer lecturerId);

    List<BatchFeedback> findByBatch_Id(Integer batchId);
}
