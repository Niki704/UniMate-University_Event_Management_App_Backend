package com.unimate.controller;

import com.unimate.dto.ProfileDTO;
import com.unimate.model.Profile;
import com.unimate.service.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping(value = "api/v5/")
public class ProfileController {

    @Autowired
    private ProfileService profileService;

    @PostMapping("/profiles/add")
    public ResponseEntity<String> saveProfile(@RequestBody ProfileDTO profileDTO) {
        profileService.saveProfile(profileDTO);
        return ResponseEntity.ok("Profile successfully added");
    }

    @PostMapping("/profiles/bulk")
    public ResponseEntity<List<Profile>> addBulkProfiles(@RequestBody List<Profile> profiles) {
        List<Profile> savedProfiles = profileService.addBulkProfiles(profiles);
        return ResponseEntity.ok(savedProfiles);
    }

    @GetMapping("/profiles/get/{userID}")
    public ProfileDTO getProfileById(@PathVariable int userID) {
        return profileService.getProfileById(userID);
    }

    @PutMapping("/profiles/update/{userID}")
    public ResponseEntity<String> updateProfile(@PathVariable Integer userID, @RequestBody ProfileDTO profileDTO) {
        profileService.updateProfile(userID, profileDTO);
        return ResponseEntity.ok("Student Profile updated successfully");
    }
}
