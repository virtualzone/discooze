package net.weweave.discooze.backend.security;

import net.weweave.discooze.backend.domain.User;
import net.weweave.discooze.backend.security.authority.AdminAuthority;
import net.weweave.discooze.backend.security.authority.UserAuthority;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.UUID;

public class JwtUser implements UserDetails {
    private final UUID id;
    private final String username;
    private final String hashedPassword;
    private final Collection<GrantedAuthority> authorities;

    public JwtUser(User user) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.hashedPassword = user.getHashedPassword();
        this.authorities = new ArrayList<>();
        this.authorities.add(new UserAuthority());
        if (user.getRoleAdmin()) {
            this.authorities.add(new AdminAuthority());
        }
    }

    public UUID getId() {
        return id;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    @Override
    public String getPassword() {
        return this.hashedPassword;
    }

    @Override
    public String getUsername() {
        return this.username;
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
        return true;
    }
}
