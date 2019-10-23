package me.Stefan923.SuperCoreLite.Hooks;

import me.Stefan923.SuperCoreLite.Main;
import me.clip.placeholderapi.external.EZPlaceholderHook;
import org.bukkit.entity.Player;

@SuppressWarnings("deprecation")
public class PlaceholderAPIHook extends EZPlaceholderHook {

    PlaceholderAPIHook() {
        super(Main.instance, "supercorelite");
    }

    public String onPlaceholderRequest(final Player player, final String identifier) {
        return null;
    }

}
