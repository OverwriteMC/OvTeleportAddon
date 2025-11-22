package ru.overwrite.teleports.configuration;

import lombok.AccessLevel;
import lombok.Getter;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import ru.overwrite.teleports.OvTeleportAddon;
import ru.overwrite.teleports.color.ColorizerProvider;
import ru.overwrite.teleports.configuration.data.MainSettings;
import ru.overwrite.teleports.configuration.data.Messages;
import ru.overwrite.teleports.configuration.data.Settings;

import java.io.File;

@Getter
public class Config {

    @Getter(AccessLevel.NONE)
    private final OvTeleportAddon plugin;

    public Config(OvTeleportAddon plugin) {
        this.plugin = plugin;
    }

    private Settings spawnSettings, tpaSettings, warpSettings, homeSettings;

    public static String timeHours, timeMinutes, timeSeconds;

    public void setupConfig(FileConfiguration config) {
        setupMainSettings(config.getConfigurationSection("main_settings"));
        String absolutePath = plugin.getDataFolder().getAbsolutePath();
        ConfigurationSection spawnConfig = mergeSectionsRecursive(config, getFile(absolutePath, "spawn.yml"));
        this.spawnSettings = Settings.create(plugin, spawnConfig);
        ConfigurationSection tpaConfig = mergeSectionsRecursive(config, getFile(absolutePath, "tpa.yml"));
        this.tpaSettings = Settings.create(plugin, tpaConfig);
        ConfigurationSection warpConfig = mergeSectionsRecursive(config, getFile(absolutePath, "warp.yml"));
        this.warpSettings = Settings.create(plugin, warpConfig);
        ConfigurationSection homeConfig = mergeSectionsRecursive(config, getFile(absolutePath, "home.yml"));
        this.homeSettings = Settings.create(plugin, homeConfig);
        setupMessages(config.getConfigurationSection("messages"));
    }

    private ConfigurationSection mergeSectionsRecursive(ConfigurationSection source, ConfigurationSection target) {
        for (String key : source.getKeys(false)) {
            if (source.isConfigurationSection(key)) {
                ConfigurationSection sourceSubSection = source.getConfigurationSection(key);

                if (target.isConfigurationSection(key)) {
                    ConfigurationSection targetSubSection = target.getConfigurationSection(key);
                    mergeSectionsRecursive(sourceSubSection, targetSubSection);
                } else if (!target.contains(key)) {
                    ConfigurationSection newSection = target.createSection(key);
                    copySection(sourceSubSection, newSection);
                }
            } else {
                if (!target.contains(key)) {
                    target.addDefault(key, source.get(key));
                }
            }
        }
        return target;
    }

    private void copySection(ConfigurationSection source, ConfigurationSection target) {
        for (String key : source.getKeys(true)) {
            target.set(key, source.get(key));
        }
    }

    private MainSettings mainSettings;

    private void setupMainSettings(ConfigurationSection mainSettings) {
        this.mainSettings = MainSettings.create(mainSettings);
    }

    private Messages messages;

    private void setupMessages(ConfigurationSection messages) {

        this.messages = Messages.create(messages);

        final ConfigurationSection time = messages.getConfigurationSection("placeholders.time");
        timeHours = ColorizerProvider.COLORIZER.colorize(time.getString("hours", " ч."));
        timeMinutes = ColorizerProvider.COLORIZER.colorize(time.getString("minutes", " мин."));
        timeSeconds = ColorizerProvider.COLORIZER.colorize(time.getString("seconds", " сек."));
    }

    public FileConfiguration getFile(String path, String fileName) {
        File file = new File(path, fileName);
        if (!file.exists()) {
            plugin.saveResource(fileName, false);
        }
        return YamlConfiguration.loadConfiguration(file);
    }

}
