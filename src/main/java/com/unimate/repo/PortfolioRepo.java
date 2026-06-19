package com.unimate.repo;

import com.unimate.model.Portfolio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PortfolioRepo extends JpaRepository<Portfolio, Integer> {
    @Query(value ="SELECT * FROM Portfolio WHERE userID = ?1", nativeQuery = true)
    Optional<Portfolio> findPortfolioById(int userID);
}
