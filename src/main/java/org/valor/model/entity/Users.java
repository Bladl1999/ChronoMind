package org.valor.model.entity;

import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.valor.model.dto.UpdateProfileRequest;
import org.valor.model.enums.UserRole;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

@Entity
@Table(name = "users")
public class Users extends BaseEntity implements UserDetails {
    @Column(unique = true, nullable = false)
    private String email;
    @Column(nullable = false)
    private String passwordHash;
    @Column(unique = true, nullable = false)
    private String userName;
    @Column
    private String name;
    // one‑to‑many tasks, categories
    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private UserSettings settings;
    @Column
    @Enumerated(EnumType.STRING)
    private UserRole userRole = UserRole.USER;

    public Users() {
    }

    public void updateProfile(UpdateProfileRequest request) {
        if (request != null) {
            this.name = request.name() != null ? request.name() : this.name;
            this.email = request.email() != null ? request.email() : this.email;
        }
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public UserRole getUserRole() {
        return userRole;
    }

    public void setUserRole(UserRole userRole) {
        this.userRole = userRole;
    }

    public UserSettings getSettings() {
        return settings;
    }

    public void setSettings(UserSettings settings) {
        this.settings = settings;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<SimpleGrantedAuthority> simpleGrantedAuthorities = new LinkedList<>();
        simpleGrantedAuthorities.add(new SimpleGrantedAuthority(userName.toUpperCase()));
        return simpleGrantedAuthorities;
    }

    @Override
    public String getPassword() {
        return passwordHash;
    }

    @Override
    public String getUsername() {
        return userName;
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
        return !getDeleted();
    }
}
