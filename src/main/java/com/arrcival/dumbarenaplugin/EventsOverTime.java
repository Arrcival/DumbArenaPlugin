package com.arrcival.dumbarenaplugin;

import com.arrcival.dumbarenaplugin.utils.Consts;
import com.arrcival.dumbarenaplugin.utils.Statics;
import org.bukkit.ChatColor;
import org.bukkit.scheduler.BukkitRunnable;

public class EventsOverTime {

    public static void EnablePvPAfter(int seconds)
    {
        if(Statics.CurrentGame != null && Statics.CurrentGame.State == Game.GameState.IN_GAME)
        {
            Statics.CurrentGame.SendMessageToAllAlivePlayers(ChatColor.BLUE + "PvP will be enabled in " + seconds + " seconds.");
            new BukkitRunnable() {
                public void run()
                {
                    if(Statics.CurrentGame != null)
                        Statics.CurrentGame.EnablePvP();
                }
            }.runTaskLater(DumbArenaPlugin.getInstance(), seconds * Consts.TICKS_PER_SECOND);
        }
    }
}
