package com.arrcival.dumbarenaplugin.listeners;

import com.arrcival.dumbarenaplugin.Game;
import com.arrcival.dumbarenaplugin.utils.Consts;
import com.arrcival.dumbarenaplugin.utils.Msg;
import com.arrcival.dumbarenaplugin.utils.Statics;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class PlayerDied implements Listener {

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event)
    {
        if(Statics.CurrentGame != null && Statics.CurrentGame.State == Game.GameState.IN_GAME)
        {
            Player playerKilled = event.getEntity().getPlayer();
            Player killer = event.getEntity().getKiller();
            int i = Statics.CurrentGame.GetPlayerIndex(playerKilled);
            if(i != -1)
            {
                Statics.CurrentGame.PlayerDied(i);
                if(killer != null)
                {
                    killer.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, Consts.TIME_REGEN_ON_KILL * Consts.TICKS_PER_SECOND, 1));
                    Msg.send(killer, "You killed a player ! You got 5 seconds of regeneration 2.");
                    if(killer.hasPotionEffect(PotionEffectType.INCREASE_DAMAGE))
                    {
                        PotionEffect effect = killer.getPotionEffect(PotionEffectType.INCREASE_DAMAGE);
                        Msg.send(killer, ChatColor.DARK_PURPLE + "You now have Strength " + (effect.getAmplifier() + 2));
                        killer.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, Consts.TICKS_PER_SECOND * 60 * 30, effect.getAmplifier() + 1));
                    } else {
                        Msg.send(killer, ChatColor.DARK_PURPLE + "You now have Strength 1");
                        killer.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, Consts.TICKS_PER_SECOND * 60 * 30, 0));
                    }
                }


            }
        }
    }
}
