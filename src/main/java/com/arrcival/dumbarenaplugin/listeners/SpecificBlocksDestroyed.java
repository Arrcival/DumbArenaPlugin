package com.arrcival.dumbarenaplugin.listeners;

import com.arrcival.dumbarenaplugin.utils.Statics;
import com.arrcival.dumbarenaplugin.utils.WorldModification;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

public class SpecificBlocksDestroyed implements Listener {

    @EventHandler
    public void onIronOreDestroyed(BlockBreakEvent event)
    {
        if(Statics.IsGameRunning() && WorldModification.AreCoordsInArena(event.getBlock().getLocation()))
        {
            Block b = event.getBlock();
            if(b.getType() == Material.IRON_ORE)
            {
                event.setDropItems(false);
                b.getWorld().dropItemNaturally(b.getLocation(), new ItemStack(Material.IRON_INGOT));
            }
            if(b.getType() == Material.GOLD_ORE)
            {
                event.setDropItems(false);
                b.getWorld().dropItemNaturally(b.getLocation(), new ItemStack(Material.GOLD_INGOT));
            }
        }
    }
}
