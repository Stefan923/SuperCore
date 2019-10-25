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
        FileConfiguration config = instance.getSettingsManager().getConfig();
        FileConfiguration language = instance.getLanguageManager().getConfig();

        instance.addUser(player);

        if (config.getBoolean("On Join.Enable Join Message"))
            event.setJoinMessage(formatAll(language.getString("On Join.Join Message").replace("%playername%", playerName)));
    }

}
