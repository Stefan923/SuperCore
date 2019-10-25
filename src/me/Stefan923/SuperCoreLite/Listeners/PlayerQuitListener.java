package me.Stefan923.SuperCoreLite.Listeners;

import me.Stefan923.SuperCoreLite.Main;
import me.Stefan923.SuperCoreLite.Utils.MessageUtils;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerQuitListener implements Listener, MessageUtils {

    @EventHandler
    public void onPlayerOuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        String playerName = player.getName();

        Main instance = Main.instance;
        FileConfiguration config = instance.getSettingsManager().getConfig();
        FileConfiguration language = instance.getLanguageManager().getConfig();

        instance.removeUser(playerName);

        if (config.getBoolean("On Quit.Enable Quit Message"))
            Bukkit.broadcastMessage(formatAll(language.getString("On Quit.Quit Message").replace("%playername%", playerName)));
    }

}
