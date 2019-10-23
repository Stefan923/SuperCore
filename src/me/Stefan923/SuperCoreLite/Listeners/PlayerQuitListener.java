package me.Stefan923.SuperCoreLite.Listeners;

import me.Stefan923.SuperCoreLite.Main;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerQuitListener implements Listener {

    @EventHandler
    public void onPlayerOuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        Main.instance.removeUser(player.getName());
    }

}
