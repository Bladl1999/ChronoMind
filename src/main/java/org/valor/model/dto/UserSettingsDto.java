package org.valor.model.dto;

import org.valor.model.enums.Theme;

public class UserSettingsDto {
    private boolean pushEnabled;
    private boolean soundEnabled;
    private int reminderMinutes;
    private Theme theme;           // enum LIGHT, DARK, SYSTEM
    private SyncSettings sync;

    public UserSettingsDto() {
    }

    public UserSettingsDto(
            boolean pushEnabled,
            boolean soundEnabled,
            int reminderMinutes,
            Theme theme,
            SyncSettings sync
    ) {
        this.pushEnabled = pushEnabled;
        this.soundEnabled = soundEnabled;
        this.reminderMinutes = reminderMinutes;
        this.theme = theme;
        this.sync = sync;
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

    public SyncSettings getSync() {
        return sync;
    }

    public void setSync(SyncSettings sync) {
        this.sync = sync;
    }
}
