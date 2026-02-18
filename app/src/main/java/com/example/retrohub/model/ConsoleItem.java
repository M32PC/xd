package com.example.retrohub.model;

public class ConsoleItem {
    private final String id;
    private final String displayName;
    private final int iconResId;
    private final String emulatorPackage;

    public ConsoleItem(String id, String displayName, int iconResId, String emulatorPackage) {
        this.id = id;
        this.displayName = displayName;
        this.iconResId = iconResId;
        this.emulatorPackage = emulatorPackage;
    }

    public String getId() {
        return id;
    }

    public String getDisplayName() {
        return displayName;
    }

    public int getIconResId() {
        return iconResId;
    }

    public String getEmulatorPackage() {
        return emulatorPackage;
    }
}
