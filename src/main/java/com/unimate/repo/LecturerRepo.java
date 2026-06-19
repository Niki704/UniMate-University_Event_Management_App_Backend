package com.unimate.repo;

import com.unimate.model.Lecturer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface LecturerRepo extends JpaRepository<Lecturer, Integer> {
    @Query(value ="SELECT * FROM Lecturer WHERE id = ?1", nativeQuery = true)
    Lecturer findLecturerById(int Id);
}
