package org.valor.model.entity;

import jakarta.persistence.*;
import org.valor.model.dto.UserSettingsDto;

import java.time.LocalDateTime;

@Entity
@Table(name = "users")
public class Users extends BaseEntity{
    private String email;
    private String passwordHash;
    private String name;
    private LocalDateTime createdAt;
    // one‑to‑many tasks, categories
    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private UserSettings settings;
}
