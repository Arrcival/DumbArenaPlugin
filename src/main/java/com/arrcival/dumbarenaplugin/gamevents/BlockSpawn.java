package com.arrcival.dumbarenaplugin.gamevents;

import com.arrcival.dumbarenaplugin.utils.Consts;
import com.arrcival.dumbarenaplugin.utils.Pairing;
import com.arrcival.dumbarenaplugin.utils.Statics;
import com.arrcival.dumbarenaplugin.utils.WorldModification;
import org.bukkit.ChatColor;
import org.bukkit.Material;

import java.util.ArrayList;

public class BlockSpawn extends GameEventArena{

    public static ArrayList<Material> Materials = new ArrayList<>();

    Material chosenMaterial;

    public BlockSpawn()
    {
        Materials.add(Material.DIAMOND_BLOCK);
        Materials.add(Material.IRON_BLOCK);
        Materials.add(Material.GOLD_BLOCK);
    }
    @Override
    public void PrepareEvent() {
        chosenMaterial = Materials.get(Statics.RandomInt(Materials.size(), false));
        Statics.CurrentGame.SendMessageToAllAlivePlayers(ChatColor.GREEN + "In " + Consts.TIME_BETWEEN_ANNOUNCE_AND_EVENT + " seconds : ");
        Statics.CurrentGame.SendMessageToAllAlivePlayers(
                ChatColor.GOLD +
                        "A " + Statics.GetPrettyName(chosenMaterial) + " will spawn in the middle of the arena");

        RunEventLater();
    }

    @Override
    public void RunEvent() {

        WorldModification.CreateBlockInTheMiddle(chosenMaterial);
    }
}
