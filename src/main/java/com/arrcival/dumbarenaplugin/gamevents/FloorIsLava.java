package com.arrcival.dumbarenaplugin.gamevents;

import com.arrcival.dumbarenaplugin.utils.Consts;
import com.arrcival.dumbarenaplugin.utils.Statics;
import com.arrcival.dumbarenaplugin.utils.WorldModification;
import org.bukkit.ChatColor;
import org.bukkit.Material;

public class FloorIsLava extends GameEventArena{
    @Override
    public void PrepareEvent() {

        if(Statics.IsGameRunning())
        {

            //Statics.CurrentGame.SendMessageToAllAlivePlayers(ChatColor.DARK_PURPLE + "The floor is going to be filled with lava in " + Consts.TIME_BETWEEN_ANNOUNCE_AND_EVENT + "s !");
            RunEventLater();
        }
    }

    @Override
    public void RunEvent() {
        WorldModification.FillFloor(Material.LAVA);
        //Statics.CurrentGame.SendMessageToAllAlivePlayers(ChatColor.GREEN + "Floor filled with lava, next floor event is one block higher.");
    }
}
