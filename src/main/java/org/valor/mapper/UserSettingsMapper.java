package org.valor.mapper;

import org.springframework.stereotype.Component;
import org.valor.model.dto.SyncSettings;
import org.valor.model.dto.UserSettingsDto;
import org.valor.model.entity.UserSettings;

@Component
public class UserSettingsMapper {

    /**
     * Преобразует сущность UserSettings в DTO.
     */
    public UserSettingsDto toDto(UserSettings entity) {
        if (entity == null) {
            return null;
        }

        UserSettingsDto dto = new UserSettingsDto();
        dto.setPushEnabled(entity.isPushEnabled());
        dto.setSoundEnabled(entity.isSoundEnabled());
        dto.setReminderMinutes(entity.getReminderMinutes());
        dto.setTheme(entity.getTheme());

        SyncSettings sync = new SyncSettings();
        sync.setAutoSyncEnabled(entity.isAutoSyncEnabled());
        dto.setSync(sync);

        return dto;
    }

    /**
     * Преобразует DTO в новую сущность UserSettings.
     */
    public UserSettings toEntity(UserSettingsDto dto) {
        if (dto == null) {
            return null;
        }

        UserSettings entity = new UserSettings();
        applyDtoToEntity(dto, entity);
        return entity;
    }

    /**
     * Обновляет существующую сущность данными из DTO.
     */
    public void updateEntityFromDto(UserSettingsDto dto, UserSettings entity) {
        if (dto == null || entity == null) {
            return;
        }
        applyDtoToEntity(dto, entity);
    }

    private void applyDtoToEntity(UserSettingsDto dto, UserSettings entity) {
        entity.setPushEnabled(dto.isPushEnabled());
        entity.setSoundEnabled(dto.isSoundEnabled());
        entity.setReminderMinutes(dto.getReminderMinutes());
        entity.setTheme(dto.getTheme());

        if (dto.getSync() != null) {
            entity.setAutoSyncEnabled(dto.getSync().isAutoSyncEnabled());
        }
    }
}
