package me.Stefan923.SuperCore.Listeners;

import me.Stefan923.SuperCore.SuperCore;
import me.Stefan923.SuperCore.Utils.MessageUtils;
import me.Stefan923.SuperCore.Utils.VersionUtils;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoinListener implements Listener, MessageUtils, VersionUtils {

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        String playerName = player.getName();

        SuperCore instance = SuperCore.getInstance();
        FileConfiguration settings = instance.getSettingsManager().getConfig();

        instance.addUser(player);

        event.setJoinMessage("");

        if (settings.getBoolean("On Join.Enable Join Message")) {
            FileConfiguration languageConfig;
            for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
                languageConfig = instance.getLanguageManager(instance.getUser(onlinePlayer).getLanguage()).getConfig();
                onlinePlayer.sendMessage(formatAll(languageConfig.getString("On Join.Join Message").replace("%playername%", playerName)));
            }
        }

        if (player.hasPermission("supercore.updatechecker"))
            Bukkit.getScheduler().runTaskAsynchronously(instance, () -> {
                checkForUpdate(instance, instance, player);
            });
    }

}
