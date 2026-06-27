package com.unimate.service;

import com.unimate.dto.LoginRequestDTO;
import com.unimate.dto.LoginResponseDTO;
import com.unimate.model.User;
import com.unimate.repo.UserRepo;
import com.unimate.security.JwtService;
import com.unimate.security.UserPrincipal;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final UserRepo userRepo;
    private final JwtService jwtService;

    public LoginResponseDTO login(LoginRequestDTO request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));

        UserPrincipal principal = (UserPrincipal) authentication.getPrincipal();

        User user = userRepo.findById(principal.getId())
                .orElseThrow(() -> new IllegalStateException("Authenticated user not found in database"));

        String token = jwtService.generateToken(principal);

        return new LoginResponseDTO(
                token,
                user.getId(),
                user.getFirstName(),
                user.getLastName(),
                user.getRole());
    }
}
