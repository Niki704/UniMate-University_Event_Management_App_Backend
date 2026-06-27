package com.unimate.repo;

import com.unimate.model.Batch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BatchRepo extends JpaRepository<Batch, Integer> {

    Optional<Batch> findByBatchCode(String batchCode);
}
