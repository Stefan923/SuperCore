package me.stefan923.supercore.listener;

import me.stefan923.supercore.SuperCore;
import me.stefan923.supercore.user.IUserRepository;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerKickEvent;

public class PlayerKickListener implements Listener {

    private final IUserRepository userRepository = SuperCore.getInstance().getUserRepository();

    @EventHandler
    public void onPlayerKick(PlayerKickEvent event) {
        Player player = event.getPlayer();

        userRepository.remove(userRepository.getUser(player.getUniqueId()));
    }

}
