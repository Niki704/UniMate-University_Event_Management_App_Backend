package com.unimate.repo;

import com.unimate.model.Portfolio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PortfolioRepo extends JpaRepository<Portfolio, Integer> {
    Optional<Portfolio> findByStudent_Id(Integer studentId);
}
