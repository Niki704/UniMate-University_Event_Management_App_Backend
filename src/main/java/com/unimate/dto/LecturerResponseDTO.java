package com.unimate.dto;

import com.unimate.enums.AccountStatus;
import com.unimate.enums.Department;
import com.unimate.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LecturerResponseDTO {
    private Integer id;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private Role role;
    private AccountStatus accountStatus;
    private Department department;
    private Set<Integer> batchIds;
    private Set<String> batchCodes;
    private String verifiedByName;
    private LocalDateTime createdDate;
    private LocalDateTime lastLogin;
}
