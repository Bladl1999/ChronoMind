package org.valor.model.dto;

public class SyncSettings {
    private boolean autoSyncEnabled;

    public SyncSettings() {
    }

    public SyncSettings(boolean autoSyncEnabled) {
        this.autoSyncEnabled = autoSyncEnabled;
    }

    public boolean isAutoSyncEnabled() {
        return autoSyncEnabled;
    }

    public void setAutoSyncEnabled(boolean autoSyncEnabled) {
        this.autoSyncEnabled = autoSyncEnabled;
    }
}
