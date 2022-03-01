package com.arrcival.dumbarenaplugin.listeners;

import com.arrcival.dumbarenaplugin.Game;
import com.arrcival.dumbarenaplugin.utils.Msg;
import com.arrcival.dumbarenaplugin.utils.Statics;
import com.arrcival.dumbarenaplugin.utils.WorldModification;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockExplodeEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.entity.PlayerDeathEvent;

import java.util.ArrayList;
import java.util.List;

import static com.arrcival.dumbarenaplugin.utils.WorldModification.ArenaBorders;

public class ArenaBordersDestroyed implements Listener {
    @EventHandler
    public void onBlockBroke(BlockBreakEvent event)
    {
        Location loc = event.getBlock().getLocation();
        if(WorldModification.IsLocationArena(loc))
        {
            Msg.send(event.getPlayer(), ChatColor.RED + "You cannot break the arena walls");
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onBlockExplode(BlockExplodeEvent event)
    {
        List<Block> blocks = new ArrayList<>(event.blockList());
        event.blockList().clear();

        blocks.forEach(block -> {
            if(!WorldModification.IsLocationArena(block.getLocation()))
                event.blockList().add(block);
        });
    }


    @EventHandler
    public void onBlockExplodeTNT(EntityExplodeEvent event)
    {
        List<Block> blocks = new ArrayList<>(event.blockList());
        event.blockList().clear();

        blocks.forEach(block -> {
            if(!WorldModification.IsLocationArena(block.getLocation()))
                event.blockList().add(block);
        });
    }
}
