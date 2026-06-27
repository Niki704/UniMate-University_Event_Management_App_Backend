package com.unimate.dto;

import com.unimate.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginResponseDTO {
    private String token;
    private Integer userId;
    private String firstName;
    private String lastName;
    private Role role;
}
