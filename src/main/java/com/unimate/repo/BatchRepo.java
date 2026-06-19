package com.unimate.repo;

import com.unimate.model.Batch;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BatchRepo extends JpaRepository<Batch, String> {
}
