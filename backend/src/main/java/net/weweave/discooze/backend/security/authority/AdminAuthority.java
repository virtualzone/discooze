package net.weweave.discooze.backend.security.authority;

import org.springframework.security.core.GrantedAuthority;

public class AdminAuthority implements GrantedAuthority {
    @Override
    public String getAuthority() {
        return "ROLE_ADMIN";
    }
}
