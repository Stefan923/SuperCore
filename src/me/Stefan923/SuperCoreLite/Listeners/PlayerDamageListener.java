package me.Stefan923.SuperCoreLite.Listeners;

import me.Stefan923.SuperCoreLite.SuperCore;
import me.Stefan923.SuperCoreLite.Utils.User;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

public class PlayerDamageListener implements Listener {

    @EventHandler
    public void onPlayerDamage(EntityDamageEvent event) {
        Entity damagedEntity = event.getEntity();
        if (!(damagedEntity instanceof Player))
            return;
        Player damagedPlayer = (Player) damagedEntity;
        User damagedUser = SuperCore.getInstance().getUser(damagedPlayer);

        if (damagedUser.getGod())
            event.setCancelled(true);
    }

}