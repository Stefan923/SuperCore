package me.Stefan923.SuperCore.Utils;

import me.Stefan923.SuperCore.Database.TableType;
import me.Stefan923.SuperCore.Language.LanguageManager;
import me.Stefan923.SuperCore.SuperCore;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public interface PlayerUtils {

    /* Get player's User object. */

    default User getUser(SuperCore instance, Player player) {
        return getUser(instance, player.getName());
    }

    default User getUser(SuperCore instance, String playerName) {
        User user = null;

        if (instance.getUsers().containsKey(playerName)) {
            user = instance.getUsers().get(playerName);
        }

        return user;
    }

    /* Get a set with all online players who a given player can see. */

    default Set<Player> onlinePlayers(Player player) {
        return Bukkit.getOnlinePlayers().stream().filter(player::canSee).collect(Collectors.toSet());
    }

    /* Get a set with all online players who a given command sender can see. */

    default Set<Player> onlinePlayers(CommandSender sender) {
        return (sender instanceof Player) ? onlinePlayers((Player) sender) : new HashSet<>(Bukkit.getOnlinePlayers());
    }

    /* Get a set with all online players who a given player can see and have a certain permission. */

    default Set<Player> onlinePlayers(Player player, String permission) {
        return Bukkit.getOnlinePlayers().stream().filter(onlinePlayer -> player.canSee(onlinePlayer) && onlinePlayer.hasPermission(permission)).collect(Collectors.toSet());
    }

    /* Get a set with all online players who a given command sender can see and have a certain permission. */

    default Set<Player> onlinePlayers(CommandSender sender, String permission) {
        return (sender instanceof Player) ? onlinePlayers((Player) sender, permission) : Bukkit.getOnlinePlayers().stream().filter(onlinePlayer -> onlinePlayer.hasPermission(permission)).collect(Collectors.toSet());
    }

    /* Get a set with all online players that have a certain permission. */

    default Set<Player> onlinePlayers(String permission) {
        return Bukkit.getOnlinePlayers().stream().filter(onlinePlayer -> onlinePlayer.hasPermission(permission)).collect(Collectors.toSet());
    }

    /* Convets a location object to string. */

    default String locationToString(Location location) {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(location.getBlockX()).append(", ").append(location.getBlockY()).append(", ").append(location.getBlockZ()).append(", ").append(location.getWorld().getName());
        return stringBuffer.toString();
    }

    default long getLastOnline(Player player) {
        return getLastOnline(player.getName());
    }

    default long getLastOnline(String playerName) {
        ResultSet resultSet = SuperCore.getInstance().getDatabase("supercore").get(TableType.USERS, playerName, "lastonline");
        try {
            return resultSet == null ? -1 : resultSet.getLong("lastonline");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    default void setLastOnline(Player player) {
        setLastOnline(player.getName());
    }

    default void setLastOnline(String playerName) {
        SuperCore.getInstance().getDatabase("supercore").put(TableType.USERS, playerName, "lastonline", String.valueOf(System.currentTimeMillis()));
    }

    /* Get CommandSender, Player or User's language manager. */

    default LanguageManager getLanguageManager(SuperCore instance, CommandSender commandSender) {
        LanguageManager languageManager = null;

        if (commandSender != null) {
            languageManager = (commandSender instanceof Player) ? getLanguageManager(instance, (Player) commandSender) : instance.getLanguageManager(instance.getSettingsManager().getConfig().getString("Languages.Default Language"));
        }

        return languageManager;
    }

    default LanguageManager getLanguageManager(SuperCore instance, Player player) {
        LanguageManager languageManager = null;

        if (player != null) {
            languageManager = getLanguageManager(instance, getUser(instance, player));
        }

        return languageManager;
    }

    default LanguageManager getLanguageManager(SuperCore instance, User user) {
        LanguageManager languageManager = null;

        if (user != null) {
            languageManager = instance.getLanguageManager(user.getLanguage());
        }

        return languageManager;
    }
}
