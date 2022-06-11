package com.arrcival.dumbarenaplugin.gamevents;

import com.arrcival.dumbarenaplugin.utils.*;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.entity.Item;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;

public class GivePlayersItem extends GameEventArena {

    public static ArrayList<Pairing<ItemStack[], Boolean>> ItemList = new ArrayList<>();

    ItemStack[] chosenItems;
    public GivePlayersItem()
    {
        ItemList.add(toPair(true, new ItemStack(Material.DIAMOND)));
        ItemList.add(toPair(false, new ItemStack(Material.FISHING_ROD)));
        ItemList.add(toPair(true, new ItemStack(Material.ENDER_PEARL)));

        ItemList.add(toPair(true, new ItemStack(Material.SNOWBALL, 16)));
        ItemList.add(toPair(false, new ItemStack(Material.WOODEN_SHOVEL)));
        ItemList.add(toPair(false, new ItemStack(Material.CHAINMAIL_CHESTPLATE)));
        ItemList.add(toPair(true, new ItemStack(Material.IRON_INGOT, 4)));
        ItemList.add(toPair(true, new ItemStack(Material.APPLE, 8)));
        ItemList.add(toPair(false, new ItemStack(Material.SLIME_SPAWN_EGG)));
        ItemList.add(toPair(false,
            new ItemStack(Material.LEATHER_HELMET),
            new ItemStack(Material.LEATHER_BOOTS))
        );
        ItemList.add(toPair(false,
                new ItemStack(Material.BOW),
                new ItemStack(Material.ARROW, 16))
        );
        ItemList.add(toPair(false,
                new ItemStack(Material.TNT),
                new ItemStack(Material.FLINT_AND_STEEL))
        );

        ItemCreator harmPot = new ItemCreator(Statics.CreateSplashPotion(PotionEffectType.HARM, Color.RED,1, 1));
        harmPot.setName(ChatColor.DARK_RED + "Harm potion");
        ItemList.add(toPair(true, harmPot.getItemStack()));
        ItemCreator healPot = new ItemCreator(Statics.CreateSplashPotion(PotionEffectType.HEAL, Color.FUCHSIA,1, 1));
        healPot.setName(ChatColor.GREEN + "Heal potion");
        ItemList.add(toPair(true, healPot.getItemStack()));
        ItemCreator weakPot = new ItemCreator(Statics.CreateSplashPotion(PotionEffectType.WEAKNESS, Color.GRAY, 30 * Consts.TICKS_PER_SECOND, 0));
        weakPot.setName(ChatColor.GREEN + "Weakness potion");
        ItemList.add(toPair(true, weakPot.getItemStack()));
    }

    @Override
    public void PrepareEvent() {
        if(Statics.IsGameRunning())
        {
            int i = Statics.RandomInt(ItemList.size(), false);
            Pairing<ItemStack[], Boolean> chosenList = ItemList.get(i);
            chosenItems = chosenList.first;

            if(!chosenList.second)
                ItemList.remove(i);

            Statics.CurrentGame.SendMessageToAllAlivePlayers(ChatColor.GREEN + "In " + Consts.TIME_BETWEEN_ANNOUNCE_AND_EVENT + " seconds, every players will receive : ");
            for(int j = 0; j < chosenItems.length; j++)
                Statics.CurrentGame.SendMessageToAllAlivePlayers(
                        ChatColor.GREEN +
                        Integer.toString(chosenItems[j].getAmount()) + " " +
                        Statics.GetPrettyName(chosenItems[j])
                );

            RunEventLater();
        }
    }

    @Override
    public void RunEvent() {
        Statics.CurrentGame.PlayerList.forEach( player -> {
            player.getInventory().addItem(chosenItems);
            Msg.send(player, "Item(s) received !");
            });
    }

    Pairing<ItemStack[], Boolean> toPair(boolean repeat, ItemStack... itemList)
    {
        return new Pairing<>(itemList, repeat);
    }
}
