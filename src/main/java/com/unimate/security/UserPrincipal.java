package com.unimate.security;

// ============================================================
// Dev Notes: UserPrincipal
// Wraps a User entity for Spring Security. isEnabled() is the
// key design point: it returns true only when accountStatus is
// ACTIVE. Spring Security's DaoAuthenticationProvider calls this
// automatically before allowing login (via its built-in
// AccountStatusUserDetailsChecker) and throws DisabledException
// on its own if it's false — so PENDING/REJECTED accounts are
// rejected at login with zero custom code required.
// ============================================================

import com.unimate.enums.AccountStatus;
import com.unimate.enums.Role;
import com.unimate.model.User;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Getter
public class UserPrincipal implements UserDetails {

    private final Integer id;
    private final String email;
    private final String password;
    private final Role role;
    private final AccountStatus accountStatus;

    public UserPrincipal(Integer id, String email, String password, Role role, AccountStatus accountStatus) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.role = role;
        this.accountStatus = accountStatus;
    }

    public static UserPrincipal fromUser(User user) {
        return new UserPrincipal(
                user.getId(),
                user.getEmail(),
                user.getPassword(),
                user.getRole(),
                user.getAccountStatus());
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_" + role.name()));
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return accountStatus == AccountStatus.ACTIVE;
    }
}
