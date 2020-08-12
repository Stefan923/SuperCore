package me.stefan923.supercore.listeners;

import me.stefan923.supercore.SuperCore;
import me.stefan923.supercore.utils.MessageUtils;
import me.stefan923.supercore.utils.PlayerUtils;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerQuitListener implements Listener, MessageUtils, PlayerUtils {

    @EventHandler
    public void onPlayerOuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        String playerName = player.getName();

        SuperCore instance = SuperCore.getInstance();
        FileConfiguration settings = instance.getSettingsManager().getConfig();

        event.setQuitMessage("");

        if (settings.getBoolean("On Quit.Enable Quit Message")) {
            FileConfiguration languageConfig;
            for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
                languageConfig = instance.getLanguageManager(instance.getUser(onlinePlayer).getLanguage()).getConfig();
                onlinePlayer.sendMessage(formatAll(languageConfig.getString("On Quit.Quit Message").replace("%playername%", playerName)));
            }
        }

        setLastOnline(player);

        instance.removeUser(playerName);
    }

}
