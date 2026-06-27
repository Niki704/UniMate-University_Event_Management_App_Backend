package com.unimate.service;

import com.unimate.dto.ProfileRequestDTO;
import com.unimate.dto.ProfileResponseDTO;
import com.unimate.exception.ResourceNotFoundException;
import com.unimate.exception.UnauthorizedActionException;
import com.unimate.model.Profile;
import com.unimate.model.User;
import com.unimate.repo.ProfileRepo;
import com.unimate.repo.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ProfileService {

    private final ProfileRepo profileRepo;
    private final UserRepo userRepo;

    @Transactional(readOnly = true)
    public ProfileResponseDTO getProfile(Integer userId) {
        Profile profile = profileRepo.findByUser_Id(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Profile not found for user: " + userId));
        return toResponseDTO(profile);
    }

    @Transactional
    public ProfileResponseDTO upsertProfile(Integer userId, Integer requesterId, ProfileRequestDTO dto) {
        if (!userId.equals(requesterId)) {
            throw new UnauthorizedActionException("You can only update your own profile");
        }

        User user = userRepo.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found: " + userId));

        Profile profile = profileRepo.findByUser_Id(userId).orElseGet(() -> {
            Profile p = new Profile();
            p.setUser(user);
            return p;
        });

        profile.setBio(dto.getBio());
        profile.setProfilePictureUrl(dto.getProfilePictureUrl());
        profile.setAddress(dto.getAddress());
        profile.setGithubUrl(dto.getGithubUrl());
        profile.setLinkedinUrl(dto.getLinkedinUrl());
        profile.setFacebookUrl(dto.getFacebookUrl());
        profile.setXUrl(dto.getXUrl());
        profile.setYoutubeUrl(dto.getYoutubeUrl());

        Profile saved = profileRepo.save(profile);
        return toResponseDTO(saved);
    }

    public ProfileResponseDTO toResponseDTO(Profile profile) {
        ProfileResponseDTO dto = new ProfileResponseDTO();
        dto.setUserId(profile.getUser().getId());
        dto.setFirstName(profile.getUser().getFirstName());
        dto.setLastName(profile.getUser().getLastName());
        dto.setBio(profile.getBio());
        dto.setProfilePictureUrl(profile.getProfilePictureUrl());
        dto.setAddress(profile.getAddress());
        dto.setGithubUrl(profile.getGithubUrl());
        dto.setLinkedinUrl(profile.getLinkedinUrl());
        dto.setFacebookUrl(profile.getFacebookUrl());
        dto.setXUrl(profile.getXUrl());
        dto.setYoutubeUrl(profile.getYoutubeUrl());
        return dto;
    }
}
