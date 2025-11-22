package ru.overwrite.teleports.configuration.data;

import org.bukkit.configuration.ConfigurationSection;

public record MainSettings(
        int invulnerableAfterTeleport,
        boolean applyToSpawn,
        boolean applyToTpa,
        boolean applyToWarp,
        boolean applyToHome) {

    private static final MainSettings EMPTY_MAIN_SETTINGS = new MainSettings(
            0,
            false,
            false,
            false,
            false
    );

    public static MainSettings create(ConfigurationSection mainSettings) {
        if (mainSettings == null) {
            return EMPTY_MAIN_SETTINGS;
        }
        return new MainSettings(
                mainSettings.getInt("invulnerable_after_teleport", 12),
                mainSettings.getBoolean("apply_to_spawn", true),
                mainSettings.getBoolean("apply_to_tpa", true),
                mainSettings.getBoolean("apply_to_warp", true),
                mainSettings.getBoolean("apply_to_home", true)
        );
    }
}
