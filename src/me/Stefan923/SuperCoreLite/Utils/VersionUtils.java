package me.Stefan923.SuperCoreLite.Utils;

import me.Stefan923.SuperCoreLite.Language.LanguageManager;
import me.Stefan923.SuperCoreLite.Main;
import org.bukkit.Bukkit;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Scanner;

public interface VersionUtils extends MessageUtils {

    default void checkForUpdate(Plugin plugin, Main instance) {
        String version = getLatestPluginVersion(plugin);

        LanguageManager languageManager = instance.getLanguageManager(instance.getSettingsManager().getConfig().getString("Languages.Default Language"));
        if (!version.equalsIgnoreCase(getCurrentPluginVersion()))
            sendLogger(languageManager.getConfig().getString("Update Checker.Available").replace("%link%", "https://www.spigotmc.org/resources/72224"));
        else
            sendLogger(languageManager.getConfig().getString("Update Checker.Not Available"));
    }

    default void checkForUpdate(Plugin plugin, Main instance, Player player) {
        String version = getLatestPluginVersion(plugin);

        LanguageManager languageManager = instance.getLanguageManager(instance.getUser(player).getLanguage());
        if (!version.equalsIgnoreCase(getCurrentPluginVersion()))
            player.sendMessage(languageManager.getConfig().getString("Update Checker.Available").replace("%link%", "https://www.spigotmc.org/resources/72224"));
        else
            player.sendMessage(languageManager.getConfig().getString("Update Checker.Not Available"));
    }

    default String getLatestPluginVersion(Plugin plugin) {
        String version = "";
        try {
            InputStream inputStream = new URL("https://api.spigotmc.org/legacy/update.php?resource=72224").openStream();
            Scanner scanner = new Scanner(inputStream);
            if (scanner.hasNext())
                version = scanner.next();
        } catch (IOException exception) {
            plugin.getLogger().info("Cannot look for updates: " + exception.getMessage());
        }
        return version;
    }

    default String getCurrentPluginVersion() {
        return Bukkit.getVersion();
    }

}
