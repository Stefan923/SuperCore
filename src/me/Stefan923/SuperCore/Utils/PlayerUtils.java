package me.Stefan923.SuperCore.Utils;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.Set;
import java.util.stream.Collectors;

public interface PlayerUtils {

    /* Get a set with all online players that can see someone. */

    default Set<Player> onlinePlayers(Player player) {
        return Bukkit.getOnlinePlayers().stream().filter(player::canSee).collect(Collectors.toSet());
    }

    /* Get a set with all online players that can see someone and has certain permission. */

    default Set<Player> onlinePlayers(Player player, String permission) {
        return Bukkit.getOnlinePlayers().stream().filter(onlinePlayer -> player.canSee(onlinePlayer) && onlinePlayer.hasPermission(permission)).collect(Collectors.toSet());
    }

    /* Convets a location object to string. */

    default String locationToString(Location location) {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(location.getBlockX()).append(", ").append(location.getBlockY()).append(", ").append(location.getBlockZ()).append(", ").append(location.getWorld().getName());
        return stringBuffer.toString();
    }

}
