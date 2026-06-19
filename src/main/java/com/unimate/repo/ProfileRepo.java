package com.unimate.repo;

import com.unimate.model.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProfileRepo extends JpaRepository<Profile, Integer> {
    @Query(value ="SELECT * FROM Profile WHERE userID = ?1", nativeQuery = true)
    Optional<Profile> findProfileById(int userID);
}
