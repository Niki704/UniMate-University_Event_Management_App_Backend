package com.unimate.dto;

import com.unimate.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LecturerDTO {
    private int id;
    private String username;
    private String password;
    private String email;
    private Role role;
    private String contact;
    private String department;
}
