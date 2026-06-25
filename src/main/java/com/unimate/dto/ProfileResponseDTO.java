package com.unimate.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProfileResponseDTO {
    private Integer userId;
    private String firstName;
    private String lastName;
    private String bio;
    private String profilePictureUrl;
    private String address;
    private String githubUrl;
    private String linkedinUrl;
    private String facebookUrl;
    private String xUrl;
    private String youtubeUrl;
}
