package me.Stefan923.SuperCoreLite.Utils;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.Set;
import java.util.stream.Collectors;

public interface PlayerUtils {

    default Set<Player> onlinePlayers(Player player) {
        return Bukkit.getOnlinePlayers().stream().filter(player::canSee).collect(Collectors.toSet());
    }

    default Set<Player> onlinePlayers(Player player, String permission) {
        return Bukkit.getOnlinePlayers().stream().filter(onlinePlayer -> player.canSee(onlinePlayer) && onlinePlayer.hasPermission(permission)).collect(Collectors.toSet());
    }

}
