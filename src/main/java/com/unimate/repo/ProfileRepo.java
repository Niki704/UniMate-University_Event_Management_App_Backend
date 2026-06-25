package com.unimate.repo;

import com.unimate.model.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProfileRepo extends JpaRepository<Profile, Integer> {

    Optional<Profile> findByUser_Id(Integer userId);
}
