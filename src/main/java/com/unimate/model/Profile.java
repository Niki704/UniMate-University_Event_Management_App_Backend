package com.unimate.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "profile")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Profile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private User user;

    @Lob
    private String bio;

    private String profilePictureUrl;

    private String address;

    private String githubUrl;

    private String linkedinUrl;

    private String facebookUrl;

    private String xUrl;

    private String youtubeUrl;
}
