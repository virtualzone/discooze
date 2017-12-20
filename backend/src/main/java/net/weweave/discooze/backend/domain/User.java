package net.weweave.discooze.backend.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import org.mindrot.jbcrypt.BCrypt;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name="`user`")
public class User extends AbstractPersistentObject {
    @NotNull
    @Column(nullable = false, unique = true)
    private String username;

    @NotNull
    @Column(nullable = false)
    private String displayName;

    @NotNull
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private UserLoginType loginType;

    private Boolean roleAdmin = false;

    @JsonIgnore
    private String hashedPassword;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public Boolean getRoleAdmin() {
        return roleAdmin;
    }

    public void setRoleAdmin(Boolean roleAdmin) {
        this.roleAdmin = roleAdmin;
    }

    public UserLoginType getLoginType() {
        return loginType;
    }

    public void setLoginType(UserLoginType loginType) {
        this.loginType = loginType;
    }

    public String getHashedPassword() {
        return hashedPassword;
    }

    public void setHashedPassword(String hashedPassword) {
        this.hashedPassword = hashedPassword;
    }

    @Transient
    public void setPlainPassword(String password) {
        this.setHashedPassword(BCrypt.hashpw(password, BCrypt.gensalt()));
    }

    @Transient
    public boolean isPasswordValid(String password) {
        if (this.getHashedPassword() != null && password != null) {
            return BCrypt.checkpw(password, this.getHashedPassword());
        }
        return false;
    }
}
