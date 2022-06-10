package com.arrcival.dumbarenaplugin.gamevents;

import com.arrcival.dumbarenaplugin.utils.Consts;
import com.arrcival.dumbarenaplugin.utils.Pairing;
import com.arrcival.dumbarenaplugin.utils.Statics;
import com.arrcival.dumbarenaplugin.utils.WorldModification;
import org.bukkit.ChatColor;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Arrays;

public class ChickenLoot extends GameEventArena{

    public static ItemStack[] chosenItems;
    int chickenAmount = 1;
    @Override
    public void PrepareEvent() {

        int i = Statics.RandomInt(GivePlayersItem.ItemList.size(), false);
        Pairing<ItemStack[], Boolean> chosenList = GivePlayersItem.ItemList.get(i);
        chosenItems = chosenList.first;

        if(!chosenList.second)
            GivePlayersItem.ItemList.remove(i);

        chickenAmount = Math.max((int)(Statics.CurrentGame.arenaSize / 3), 1);
        Statics.CurrentGame.SendMessageToAllAlivePlayers(ChatColor.GREEN + "In " + Consts.TIME_BETWEEN_ANNOUNCE_AND_EVENT + " seconds, " +
                chickenAmount + " special chicken(s) will spawn !");

        RunEventLater();
    }

    @Override
    public void RunEvent() {
        WorldModification.CreateChickenCeiling(chickenAmount);
    }
}
