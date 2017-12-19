package net.weweave.discooze.backend.security.authority;

import org.springframework.security.core.GrantedAuthority;

public class UserAuthority implements GrantedAuthority {
    @Override
    public String getAuthority() {
        return "ROLE_USER";
    }
}
