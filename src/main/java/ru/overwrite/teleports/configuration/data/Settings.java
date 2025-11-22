package ru.overwrite.teleports.configuration.data;

import org.bukkit.configuration.ConfigurationSection;
import ru.overwrite.teleports.OvTeleportAddon;

public record Settings(
        Cooldown cooldown,
        Bossbar bossbar,
        Particles particles,
        Restrictions restrictions,
        Actions actions) {

    public static Settings create(OvTeleportAddon plugin, ConfigurationSection config) {
        return new Settings(
                Cooldown.create(plugin.getPerms(), config.getConfigurationSection("cooldown")),
                Bossbar.create(config.getConfigurationSection("bossbar")),
                Particles.create(config.getConfigurationSection("particles")),
                Restrictions.create(config.getConfigurationSection("restrictions")),
                Actions.create(plugin.getTeleportManager().getActionService(), config.getConfigurationSection("actions"))
        );
    }
}
