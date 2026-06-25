package com.unimate.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProfileRequestDTO {
    private String bio;
    private String profilePictureUrl;
    private String address;
    private String githubUrl;
    private String linkedinUrl;
    private String facebookUrl;
    private String xUrl;
    private String youtubeUrl;
}
