package ru.overwrite.teleports.configuration.data;

import it.unimi.dsi.fastutil.objects.Object2IntLinkedOpenHashMap;
import it.unimi.dsi.fastutil.objects.Object2IntSortedMap;
import it.unimi.dsi.fastutil.objects.Object2IntSortedMaps;
import net.milkbowl.vault.permission.Permission;
import org.bukkit.configuration.ConfigurationSection;

import java.util.ArrayList;
import java.util.List;

public record Cooldown(
        int defaultPreTeleportCooldown,
        Object2IntSortedMap<String> preTeleportCooldowns) {

    private static final Cooldown EMPTY_COOLDOWN = new Cooldown(
            0,
            Object2IntSortedMaps.emptyMap()
    );

    public static Cooldown create(Permission perms, ConfigurationSection cooldown) {
        if (cooldown == null) {
            return EMPTY_COOLDOWN;
        }

        Object2IntSortedMap<String> preTeleportCooldownsMap = new Object2IntLinkedOpenHashMap<>();

        boolean useLastGroupCooldown = cooldown.getBoolean("use_last_group_cooldown", false);

        int defaultPreTeleportCooldown = cooldown.getInt("default_pre_teleport_cooldown", -1);
        ConfigurationSection preTeleportGroupCooldownsSection = cooldown.getConfigurationSection("pre_teleport_group_cooldowns");
        if (preTeleportGroupCooldownsSection != null) {
            defaultPreTeleportCooldown = processCooldownSection(perms, preTeleportGroupCooldownsSection, preTeleportCooldownsMap, useLastGroupCooldown, defaultPreTeleportCooldown);
        }

        return new Cooldown(defaultPreTeleportCooldown, preTeleportCooldownsMap);
    }

    private static int processCooldownSection(Permission perms, ConfigurationSection section, Object2IntSortedMap<String> map, boolean useLastGroup, int currentDefault) {
        if (perms != null) {
            for (String groupName : section.getKeys(false)) {
                map.put(groupName, section.getInt(groupName));
            }
            if (!map.isEmpty() && useLastGroup) {
                List<String> keys = new ArrayList<>(map.keySet());
                currentDefault = section.getInt(keys.get(keys.size() - 1));
            }
        }
        return currentDefault;
    }
}
