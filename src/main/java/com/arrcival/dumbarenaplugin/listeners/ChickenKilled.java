package com.arrcival.dumbarenaplugin.listeners;


import com.arrcival.dumbarenaplugin.gamevents.ChickenLoot;
import com.arrcival.dumbarenaplugin.utils.Statics;
import com.arrcival.dumbarenaplugin.utils.WorldModification;
import org.bukkit.entity.Chicken;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;

import java.util.Arrays;

public class ChickenKilled implements Listener {

    @EventHandler
    public void onChickenKill(EntityDeathEvent event)
    {
        if(Statics.IsGameRunning() && event.getEntity() instanceof Chicken && WorldModification.AreCoordsInArena(event.getEntity().getLocation()))
        {
            event.getDrops().clear();
            for(int i = 0; i < ChickenLoot.chosenItems.length; i++)
                event.getDrops().add(ChickenLoot.chosenItems[i]);
        }
    }
}
