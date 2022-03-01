package com.arrcival.dumbarenaplugin.gamevents;

import com.arrcival.dumbarenaplugin.utils.Consts;
import com.arrcival.dumbarenaplugin.utils.Statics;
import com.arrcival.dumbarenaplugin.utils.WorldModification;
import org.bukkit.ChatColor;
import org.bukkit.Material;

public class GenerateTrees extends GameEventArena
{
    int trees = 0;
    @Override
    public void PrepareEvent()
    {

        if(Statics.IsGameRunning())
        {
            trees = Consts.TREE_SPAWNS + (Statics.CurrentGame.arenaSize / 5);
            Statics.CurrentGame.SendMessageToAllAlivePlayers(ChatColor.GREEN + "In " + Consts.TIME_BETWEEN_ANNOUNCE_AND_EVENT + " seconds : ");
            Statics.CurrentGame.SendMessageToAllAlivePlayers(ChatColor.DARK_GREEN + Integer.toString(trees) + " trees are spawning in the floor !");
            RunEventLater();
        }
    }

    @Override
    public void RunEvent() {
        WorldModification.GenerateTrees(trees);
        Statics.CurrentGame.SendMessageToAllAlivePlayers(ChatColor.DARK_GREEN + Integer.toString(trees) + " trees tried to spawn.");
        WorldModification.AddAppeared(Material.OAK_PLANKS);
        WorldModification.AddAppeared(Material.OAK_LOG);
        WorldModification.AddAppeared(Material.OAK_LEAVES);
    }
}
