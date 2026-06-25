package com.unimate.repo;

import com.unimate.enums.AccountStatus;
import com.unimate.model.Lecturer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LecturerRepo extends JpaRepository<Lecturer, Integer> {

    List<Lecturer> findByAccountStatus(AccountStatus status);

    long countByAccountStatus(AccountStatus status);
}
