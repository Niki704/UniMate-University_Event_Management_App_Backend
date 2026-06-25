package com.unimate.dto;

import com.unimate.enums.AccountStatus;
import com.unimate.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdminResponseDTO {
    private Integer id;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private Role role;
    private AccountStatus accountStatus;
    private LocalDateTime createdDate;
}
