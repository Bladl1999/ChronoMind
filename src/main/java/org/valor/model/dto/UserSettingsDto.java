package org.valor.model.dto;

import org.valor.model.enums.Theme;

public class UserSettingsDto {
    private NotificationSettings notifications;
    private Theme theme;           // enum LIGHT, DARK, SYSTEM
    private SyncSettings sync;
}
