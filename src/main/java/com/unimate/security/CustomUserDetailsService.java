package com.unimate.security;

// ============================================================
// Dev Notes: CustomUserDetailsService
// Bridges Spring Security to the existing UserRepo. Works for
// any role since it queries the base User table — Hibernate's
// JOINED inheritance resolves the concrete subclass underneath,
// but we only need the base fields for authentication anyway.
// ============================================================

import com.unimate.model.User;
import com.unimate.repo.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepo userRepo;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepo.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));
        return UserPrincipal.fromUser(user);
    }
}
