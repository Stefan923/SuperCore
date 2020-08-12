package me.stefan923.supercore.hooks;

import me.stefan923.supercore.utils.User;
import me.stefan923.supercore.SuperCore;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class PlaceholderAPIHook extends PlaceholderExpansion {

    private SuperCore instance;

    public PlaceholderAPIHook(SuperCore instance) {
        this.instance = instance;
    }

    @Override
    public boolean persist(){
        return true;
    }

    @Override
    public boolean canRegister(){
        return true;
    }

    @Override
    public String getIdentifier() {
        return "supercore";
    }

    @Override
    public String getAuthor() {
        return "Stefan923";
    }

    @Override
    public String getVersion() {
        return instance.getDescription().getVersion();
    }

    @Override
    public String onPlaceholderRequest(final Player player, final String identifier) {
        User user = instance.getUser(player);
        if (identifier.equalsIgnoreCase("nickname")) {
            return (user.getNickname() == null) ? player.getName() : ChatColor.translateAlternateColorCodes('&', user.getNickname());
        }
        if (identifier.equalsIgnoreCase("isGodMode")) {
            return user.getGod() ? "yes" : "no";
        }
        return null;
    }
}
