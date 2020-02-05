package me.Stefan923.SuperCore.Hooks;

import me.Stefan923.SuperCore.SuperCore;
import me.Stefan923.SuperCore.Utils.User;
import me.clip.placeholderapi.external.EZPlaceholderHook;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

@SuppressWarnings("ALL")
public class PlaceholderAPIHook extends EZPlaceholderHook {

    private SuperCore instance;

    PlaceholderAPIHook(SuperCore instance) {
        super(instance, "supercore");
        this.instance = instance;
    }

    public String onPlaceholderRequest(final Player player, final String identifier) {
        User user = instance.getUser(player);
        if (identifier.equalsIgnoreCase("nick")) {
            return (user.getNickname() == null) ? player.getName() : ChatColor.translateAlternateColorCodes('&', user.getNickname());
        }
        if (identifier.equalsIgnoreCase("isGodMode")) {
            return user.getGod() ? "yes" : "no";
        }
        return null;
    }

}
