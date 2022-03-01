package com.arrcival.dumbarenaplugin.gamevents;

import com.arrcival.dumbarenaplugin.utils.*;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;

public class ItemDrop extends GameEventArena {
    public static ArrayList<ItemStack[]> ItemList = new ArrayList<>();

    ItemStack[] chosenItems;

    public ItemDrop()
    {
        ItemStack niceBow = new ItemStack(Material.BOW);
        niceBow.addEnchantment(Enchantment.ARROW_DAMAGE, 2);
        addItems(niceBow, new ItemStack(Material.ARROW, 16));

        ItemStack sword = new ItemStack(Material.DIAMOND_SWORD);
        sword.addEnchantment(Enchantment.DAMAGE_ALL, 1);
        addItems(sword);
        addItems(new ItemStack(Material.DIAMOND_CHESTPLATE));
        addItems(new ItemStack(Material.DIAMOND_LEGGINGS));
        addItems(new ItemStack(Material.LAVA_BUCKET));
        addItems(new ItemStack(Material.TRIDENT));
        addItems(new ItemStack(Material.BLAZE_SPAWN_EGG));
        addItems(new ItemStack(Material.CREEPER_SPAWN_EGG));
        addItems(new ItemStack(Material.GOLDEN_APPLE, 2));
    }

    @Override
    public void PrepareEvent() {
        if(Statics.IsGameRunning())
        {
            int i = Statics.RandomInt(ItemList.size(), false);
            chosenItems = ItemList.get(i);

            Statics.CurrentGame.SendMessageToAllAlivePlayers(ChatColor.GREEN + "In " + Consts.TIME_BETWEEN_ANNOUNCE_AND_EVENT + " seconds, those items will drop in the center : ");
            for(int j = 0; j < chosenItems.length; j++)
                Statics.CurrentGame.SendMessageToAllAlivePlayers(
                        ChatColor.GOLD +
                                Integer.toString(chosenItems[j].getAmount()) + " " +
                                Statics.GetPrettyName(chosenItems[j])
                );

            RunEventLater();
        }
    }

    @Override
    public void RunEvent() {
        WorldModification.DropItems(chosenItems);
    }

    void addItems(ItemStack... itemList)
    {
        ItemList.add(itemList);
    }
}
