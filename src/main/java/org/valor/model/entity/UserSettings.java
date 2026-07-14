package org.valor.model.entity;

import jakarta.persistence.*;
import org.apache.catalina.User;
import org.valor.model.enums.Theme;

@Entity
@Table(name = "user_settings")
public class UserSettings extends BaseEntity{
    @OneToOne
    @JoinColumn(name = "user_id")
    private Users user;
    private boolean pushEnabled;
    private boolean soundEnabled;
    private int reminderMinutes;
    @Enumerated(EnumType.STRING)
    private Theme theme;      // LIGHT, DARK, SYSTEM
    private boolean autoSyncEnabled;
}
