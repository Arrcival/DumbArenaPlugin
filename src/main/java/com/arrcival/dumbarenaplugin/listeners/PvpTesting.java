package com.arrcival.dumbarenaplugin.listeners;

import com.arrcival.dumbarenaplugin.Game;
import com.arrcival.dumbarenaplugin.utils.Statics;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class PvpTesting implements Listener {
    @EventHandler
    public void onTestEntityDamage(EntityDamageByEntityEvent event) {

        if(Statics.CurrentGame != null && Statics.CurrentGame.State == Game.GameState.IN_GAME && !Statics.CurrentGame.PvP)
        {
            if (event.getDamager() instanceof Player){
                if (event.getEntity() instanceof Player) {
                    Player damager = (Player)event.getDamager();
                    if(Statics.CurrentGame.GetPlayerIndex(damager) != -1)
                        event.setCancelled(true);
                }
            }
        }
    }
}
