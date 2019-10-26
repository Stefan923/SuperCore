package me.Stefan923.SuperCoreLite.Listeners;

import me.Stefan923.SuperCoreLite.Main;
import me.Stefan923.SuperCoreLite.Settings.SettingsManager;
import me.Stefan923.SuperCoreLite.Utils.MessageUtils;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoinListener implements Listener, MessageUtils {

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        String playerName = player.getName();

        Main instance = Main.instance;
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
    }

}
