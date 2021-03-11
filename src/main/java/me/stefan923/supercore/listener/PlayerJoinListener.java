package me.stefan923.supercore.listener;

import me.stefan923.supercore.SuperCore;
import me.stefan923.supercore.configuration.setting.Setting;
import me.stefan923.supercore.user.IUser;
import me.stefan923.supercore.user.IUserRepository;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.UUID;

public class PlayerJoinListener implements Listener {

    private final IUserRepository userRepository = SuperCore.getInstance().getUserRepository();

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        UUID playerUUID = player.getUniqueId();
        String playerName = player.getName();

        IUser user;
        if (Setting.STORAGE_USE_UUID) {
            user = userRepository.getOrLoadUser(playerUUID);
        } else {
            user = userRepository.getOrLoadUser(playerName);
        }

        if (user == null) {
            userRepository.createUser(playerUUID, playerName);
        }
    }

}
