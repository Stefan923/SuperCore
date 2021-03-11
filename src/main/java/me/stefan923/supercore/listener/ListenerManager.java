package me.stefan923.supercore.listener;

import me.stefan923.supercore.SuperCore;
import org.bukkit.plugin.PluginManager;

public class ListenerManager {

    private final SuperCore plugin = SuperCore.getInstance();

    public void enableAllListeners() {
        PluginManager pluginManager = plugin.getServer().getPluginManager();
        pluginManager.registerEvents(new PlayerJoinListener(), plugin);
        pluginManager.registerEvents(new PlayerKickListener(), plugin);
        pluginManager.registerEvents(new PlayerQuitListener(), plugin);
    }

}
