package com.arrcival.dumbarenaplugin.gamevents;

import com.arrcival.dumbarenaplugin.utils.Consts;
import com.arrcival.dumbarenaplugin.utils.Pairing;
import com.arrcival.dumbarenaplugin.utils.Statics;
import com.arrcival.dumbarenaplugin.utils.WorldModification;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.scheduler.BukkitRunnable;

import java.awt.*;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;

public class Generate3DBlocks extends GameEventArena {

    public static ArrayList<Pairing<Material, Float>> MaterialPercentages = new ArrayList<>();

    Pairing<Material, Float> chosenMaterial;

    public Generate3DBlocks()
    {
        MaterialPercentages.add(new Pairing<>(Material.DIRT, 0.03f));
        MaterialPercentages.add(new Pairing<>(Material.IRON_ORE, 0.006f));
        MaterialPercentages.add(new Pairing<>(Material.DEEPSLATE_IRON_ORE, 0.01f));
        MaterialPercentages.add(new Pairing<>(Material.GOLD_ORE, 0.006f));
        MaterialPercentages.add(new Pairing<>(Material.DIAMOND_ORE, 0.003f));
        MaterialPercentages.add(new Pairing<>(Material.DEEPSLATE_DIAMOND_ORE, 0.005f));
        MaterialPercentages.add(new Pairing<>(Material.IRON_BLOCK, 0.001f));
        MaterialPercentages.add(new Pairing<>(Material.CRAFTING_TABLE, 0.003f));
        MaterialPercentages.add(new Pairing<>(Material.PACKED_ICE, 0.003f));
        MaterialPercentages.add(new Pairing<>(Material.OBSIDIAN, 0.001f));
        MaterialPercentages.add(new Pairing<>(Material.TNT, 0.0001f));
        MaterialPercentages.add(new Pairing<>(Material.OAK_PLANKS, 0.01f));
        MaterialPercentages.add(new Pairing<>(Material.WHITE_WOOL, 0.01f));
        MaterialPercentages.add(new Pairing<>(Material.SEA_LANTERN, 0.01f));
        MaterialPercentages.add(new Pairing<>(Material.SNOW_BLOCK, 0.015f));
        MaterialPercentages.add(new Pairing<>(Material.SAND, 0.005f));
    }

    @Override
    public void PrepareEvent() {

        if(Statics.IsGameRunning())
        {
            chosenMaterial = MaterialPercentages.get(Statics.RandomInt(MaterialPercentages.size(), false));

            Statics.CurrentGame.SendMessageToAllAlivePlayers(ChatColor.GREEN + "In " + Consts.TIME_BETWEEN_ANNOUNCE_AND_EVENT + " seconds : ");
            Statics.CurrentGame.SendMessageToAllAlivePlayers(
                ChatColor.GREEN +
                Statics.GetDisplayedPercentage(chosenMaterial.second) +
                "% of the map will be filled with " +
                Statics.GetPrettyName(chosenMaterial.first));

            RunEventLater();
        }
    }

    @Override
    public void RunEvent() throws InvocationTargetException, IllegalAccessException {
        WorldModification.ChangeMapWith(chosenMaterial.first, chosenMaterial.second);
        Statics.CurrentGame.SendMessageToAllAlivePlayers(ChatColor.GREEN + "Area filled with " + Statics.GetPrettyName(chosenMaterial.first));
        WorldModification.AddAppeared(chosenMaterial.first);
    }
}
