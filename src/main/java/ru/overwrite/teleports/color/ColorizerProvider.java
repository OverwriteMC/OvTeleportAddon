package ru.overwrite.teleports.color;

import org.bukkit.configuration.ConfigurationSection;
import ru.overwrite.teleports.color.impl.LegacyColorizer;
import ru.overwrite.teleports.color.impl.MiniMessageColorizer;

import java.util.Locale;

public class ColorizerProvider {
    public static Colorizer COLORIZER;

    public static void init(ConfigurationSection config) {
        String serializerType = config.getString("serializer", "LEGACY").toUpperCase(Locale.ENGLISH);
        COLORIZER = "MINIMESSAGE".equals(serializerType)
                ? new MiniMessageColorizer()
                : new LegacyColorizer();
    }
}
