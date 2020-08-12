package me.stefan923.supercore.listeners;

import me.stefan923.supercore.SuperCore;
import me.stefan923.supercore.utils.MessageUtils;
import me.stefan923.supercore.utils.PlayerUtils;
import me.stefan923.supercore.utils.User;
import me.stefan923.supercore.utils.VersionUtils;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoinListener implements Listener, PlayerUtils, MessageUtils, VersionUtils {

    SuperCore instance;

    public PlayerJoinListener(SuperCore instance) {
        this.instance = instance;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        String playerName = player.getName();

        FileConfiguration settings = instance.getSettingsManager().getConfig();

        User user = getUser(instance, playerName);

        user.setJoinTime(System.currentTimeMillis());

        event.setJoinMessage("");

        if (settings.getBoolean("On Join.Enable Join Message")) {
            Bukkit.getOnlinePlayers().forEach(onlinePlayer -> {
                FileConfiguration config = getLanguageConfig(instance, onlinePlayer);
                onlinePlayer.sendMessage(formatAll(config.getString("On Join.Join Message").replace("%playername%", playerName)));
            });
        }

        if (player.hasPermission("supercore.updatechecker"))
            Bukkit.getScheduler().runTaskAsynchronously(instance, () -> checkForUpdate(instance, instance, player));
    }

}
