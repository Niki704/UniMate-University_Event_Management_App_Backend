package com.unimate.dto;

import com.unimate.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StudentDTO {
    private int id;
    private String username;
    private String password;
    private String email;
    private Role role;
    private int enrollmentYear;
    private String batchID;
}
