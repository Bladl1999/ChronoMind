package org.valor.model.entity;

import jakarta.persistence.*;
import org.valor.model.enums.Theme;

@Entity
@Table(name = "user_settings")
public class UserSettings extends BaseEntity {
    @OneToOne
    @JoinColumn(name = "user_id")
    private Users user;
    private boolean pushEnabled;
    private boolean soundEnabled;
    private int reminderMinutes;
    @Enumerated(EnumType.STRING)
    private Theme theme;      // LIGHT, DARK, SYSTEM
    private boolean autoSyncEnabled;

    public UserSettings() {
    }

    public UserSettings(
            Users user,
            boolean pushEnabled,
            boolean soundEnabled,
            int reminderMinutes,
            Theme theme,
            boolean autoSyncEnabled
    ) {
        this.user = user;
        this.pushEnabled = pushEnabled;
        this.soundEnabled = soundEnabled;
        this.reminderMinutes = reminderMinutes;
        this.theme = theme;
        this.autoSyncEnabled = autoSyncEnabled;
    }

    public Users getUser() {
        return user;
    }

    public void setUser(Users user) {
        this.user = user;
    }

    public boolean isPushEnabled() {
        return pushEnabled;
    }

    public void setPushEnabled(boolean pushEnabled) {
        this.pushEnabled = pushEnabled;
    }

    public boolean isSoundEnabled() {
        return soundEnabled;
    }

    public void setSoundEnabled(boolean soundEnabled) {
        this.soundEnabled = soundEnabled;
    }

    public int getReminderMinutes() {
        return reminderMinutes;
    }

    public void setReminderMinutes(int reminderMinutes) {
        this.reminderMinutes = reminderMinutes;
    }

    public Theme getTheme() {
        return theme;
    }

    public void setTheme(Theme theme) {
        this.theme = theme;
    }

    public boolean isAutoSyncEnabled() {
        return autoSyncEnabled;
    }

    public void setAutoSyncEnabled(boolean autoSyncEnabled) {
        this.autoSyncEnabled = autoSyncEnabled;
    }
}
