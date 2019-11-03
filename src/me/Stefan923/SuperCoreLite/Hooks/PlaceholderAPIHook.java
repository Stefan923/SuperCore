package me.Stefan923.SuperCoreLite.Hooks;

import me.Stefan923.SuperCoreLite.Main;
import me.Stefan923.SuperCoreLite.Utils.User;
import me.clip.placeholderapi.external.EZPlaceholderHook;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

@SuppressWarnings("ALL")
public class PlaceholderAPIHook extends EZPlaceholderHook {

    private Main instance;

    PlaceholderAPIHook(Main instance) {
        super(instance, "supercore");
        this.instance = instance;
    }

    public String onPlaceholderRequest(final Player player, final String identifier) {
        User user = instance.getUser(player);
        if (identifier.equals("nick")) {
            return (user.getNickname() == null) ? player.getName() : ChatColor.translateAlternateColorCodes('&', user.getNickname());
        }
        return null;
    }

}
