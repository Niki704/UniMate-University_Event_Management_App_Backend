package com.unimate.repo;

import com.unimate.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentRepo extends JpaRepository<Student, Integer> {
    @Query(value ="SELECT * FROM Student WHERE id = ?1", nativeQuery = true)
    Student findStudentById(int Id);
}
