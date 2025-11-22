package ru.overwrite.teleports.configuration.data;

import org.bukkit.configuration.ConfigurationSection;
import ru.overwrite.teleports.color.ColorizerProvider;

public record Messages(
        String movedOnTeleport,
        String teleportedOnTeleport,
        String damagedOnTeleport,
        String damagedOtherOnTeleport,
        String warpNotFound,
        String cancelled,
        String reload,
        String noPerms) {

    public static Messages create(ConfigurationSection messages) {

        String messagesPrefix = ColorizerProvider.COLORIZER.colorize(messages.getString("prefix", "messages.prefix"));

        return new Messages(
                getPrefixed(messages.getString("moved_on_teleport", "messages.moved_on_teleport"), messagesPrefix),
                getPrefixed(messages.getString("teleported_on_teleport", "messages.teleported_on_teleport"), messagesPrefix),
                getPrefixed(messages.getString("damaged_on_teleport", "messages.damaged_on_teleport"), messagesPrefix),
                getPrefixed(messages.getString("damaged_other_on_teleport", "messages.damaged_other_on_teleport"), messagesPrefix),
                getPrefixed(messages.getString("warp_not_found", "messages.warp_not_found"), messagesPrefix),
                getPrefixed(messages.getString("cancelled", "messages.cancelled"), messagesPrefix),
                getPrefixed(messages.getString("reload", "messages.reload"), messagesPrefix),
                getPrefixed(messages.getString("no_perms", "messages.no_perms"), messagesPrefix)
        );
    }

    private static String getPrefixed(String message, String prefix) {
        return message != null ? ColorizerProvider.COLORIZER.colorize(message.replace("%prefix%", prefix)) : null;
    }
}
