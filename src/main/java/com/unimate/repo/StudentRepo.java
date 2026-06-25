package com.unimate.repo;

import com.unimate.enums.AccountStatus;
import com.unimate.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StudentRepo extends JpaRepository<Student, Integer> {

    Optional<Student> findByStudentIdNumber(String studentIdNumber);

    List<Student> findByAccountStatus(AccountStatus status);

    long countByAccountStatus(AccountStatus status);
}
