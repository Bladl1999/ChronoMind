package org.valor.model.dto;

import org.valor.model.enums.Theme;

public class UserSettingsDto {
    private Boolean pushEnabled;
    private Boolean soundEnabled;
    private Integer reminderMinutes;
    private Theme theme;           // enum LIGHT, DARK, SYSTEM
    private SyncSettings sync;

    public UserSettingsDto() {
    }

    public UserSettingsDto(
            Boolean pushEnabled,
            Boolean soundEnabled,
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

    public Boolean isPushEnabled() {
        return pushEnabled;
    }

    public void setPushEnabled(Boolean pushEnabled) {
        this.pushEnabled = pushEnabled;
    }

    public Boolean isSoundEnabled() {
        return soundEnabled;
    }

    public void setSoundEnabled(Boolean soundEnabled) {
        this.soundEnabled = soundEnabled;
    }

    public Integer getReminderMinutes() {
        return reminderMinutes;
    }

    public void setReminderMinutes(Integer reminderMinutes) {
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
