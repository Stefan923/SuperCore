package me.stefan923.supercore.listener;

import me.stefan923.supercore.SuperCore;
import me.stefan923.supercore.configuration.setting.Setting;
import me.stefan923.supercore.user.UserRepository;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoinListener implements Listener {

    private final UserRepository userRepository = SuperCore.getInstance().getUserRepository();

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        if (Setting.STORAGE_USE_UUID) {
            userRepository.getOrLoadUser(player.getUniqueId());
        } else {
            userRepository.getOrLoadUser(player.getName());
        }
    }

}
