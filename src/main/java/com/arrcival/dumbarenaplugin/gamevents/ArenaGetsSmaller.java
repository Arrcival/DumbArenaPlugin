package com.arrcival.dumbarenaplugin.gamevents;

import com.arrcival.dumbarenaplugin.utils.Consts;
import com.arrcival.dumbarenaplugin.utils.Statics;
import com.arrcival.dumbarenaplugin.utils.WorldModification;
import org.bukkit.ChatColor;
import org.bukkit.Material;

public class ArenaGetsSmaller extends GameEventArena{

    boolean ceiling = false;
    @Override
    public void PrepareEvent() {

        if(Statics.IsGameRunning())
        {
            int roll = Statics.RandomInt(0, 1, true);
            if(roll == 1)
                ceiling = true;

            Statics.CurrentGame.SendMessageToAllAlivePlayers(ChatColor.GREEN + "In " + Consts.TIME_BETWEEN_ANNOUNCE_AND_EVENT + " seconds : ");

            if(ceiling)
                Statics.CurrentGame.SendMessageToAllAlivePlayers(ChatColor.DARK_PURPLE + "The CEILING will be filled with bedrock...");
            else
                Statics.CurrentGame.SendMessageToAllAlivePlayers(ChatColor.DARK_PURPLE + "The FLOOR will be filled with bedrock...");

            RunEventLater(Consts.ADDITIONAL_TIME_BEFORE_AREA_BEDROCK);
        }
    }

    @Override
    public void RunEvent() {
        if(ceiling)
        {
            WorldModification.FillCeiling(Material.BEDROCK);
            Statics.CurrentGame.SendMessageToAllAlivePlayers(ChatColor.GREEN + "Ceiling filled with bedrock, next ceiling event is one block higher.");

        } else
        {
            WorldModification.FillFloor(Material.BEDROCK);
            Statics.CurrentGame.SendMessageToAllAlivePlayers(ChatColor.GREEN + "Floor filled with bedrock, next floor event is one block higher.");
        }
    }

}
