package com.arrcival.dumbarenaplugin.gamevents;

import com.arrcival.dumbarenaplugin.utils.Consts;
import com.arrcival.dumbarenaplugin.utils.Statics;
import com.arrcival.dumbarenaplugin.utils.WorldModification;
import org.bukkit.ChatColor;
import org.bukkit.Material;

import java.lang.reflect.InvocationTargetException;

public class OneBlockGetsRemoved extends GameEventArena{

    Material chosenMaterial;
    @Override
    public void PrepareEvent() {

        if(Statics.IsGameRunning() && WorldModification.AppearedBlocks.size() > 0)
        {
            int i = Statics.RandomInt(WorldModification.AppearedBlocks.size(), false);
            chosenMaterial = WorldModification.AppearedBlocks.get(i);

            WorldModification.AppearedBlocks.remove(i);
            Statics.CurrentGame.SendMessageToAllAlivePlayers(ChatColor.RED + "In " + Consts.TIME_BETWEEN_ANNOUNCE_AND_EVENT + " seconds :");
            Statics.CurrentGame.SendMessageToAllAlivePlayers(ChatColor.RED + "Every " + Statics.GetPrettyName(chosenMaterial) + " will be replaced with air.");

            RunEventLater();
        }

    }

    @Override
    public void RunEvent() throws InvocationTargetException, IllegalAccessException {
        WorldModification.ReplaceWith(chosenMaterial, Material.AIR);
    }
}
