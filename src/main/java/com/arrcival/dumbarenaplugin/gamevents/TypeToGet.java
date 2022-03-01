package com.arrcival.dumbarenaplugin.gamevents;

import com.arrcival.dumbarenaplugin.gamevents.GameEventArena;
import com.arrcival.dumbarenaplugin.utils.*;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Locale;

public class TypeToGet extends GameEventArena {

    public static ArrayList<ItemStack> ItemList = new ArrayList<>();

    public static ArrayList<ItemStack> chosenItems = new ArrayList<>();

    public static boolean canPeopleRequest = false;

    public TypeToGet()
    {
        ItemList.add(new ItemStack(Material.GOLDEN_APPLE));
        ItemList.add(new ItemStack(Material.ENDER_PEARL));
        ItemList.add(new ItemStack(Material.OAK_LOG, 8));
        ItemList.add(new ItemStack(Material.DIRT, 64));
        ItemList.add(new ItemStack(Material.STONE_SWORD));
        ItemList.add(new ItemStack(Material.STONE_AXE));
        ItemList.add(new ItemStack(Material.IRON_SHOVEL));
        ItemList.add(new ItemStack(Material.LAVA_BUCKET));
        ItemList.add(new ItemStack(Material.IRON_INGOT, 8));

        ItemStack stick = new ItemStack(Material.STICK);
        stick.addUnsafeEnchantment(Enchantment.KNOCKBACK, 1);
        ItemList.add(stick);

        ItemCreator carrot = new ItemCreator(Material.CARROT);
        carrot.setName(ChatColor.GOLD + "Dustray's carrot");
        carrot.addUnsafeEnchantment(Enchantment.DAMAGE_ALL, 3);
        ItemList.add(carrot.getItemStack());
    }

    @Override
    public void PrepareEvent() {

        if(Statics.IsGameRunning())
        {
            canPeopleRequest = false;
            Statics.CurrentGame.SendMessageToAllAlivePlayers(ChatColor.GREEN + "In " + Consts.TIME_BETWEEN_ANNOUNCE_AND_EVENT + " seconds : ");
            Statics.CurrentGame.SendMessageToAllAlivePlayers(
                    ChatColor.GREEN +
                            "You'll be prompted 3 items, first one getting typed will be given to all players.");

            RunEventLater();
        }
    }

    @Override
    public void RunEvent() {
        // pick 3
        chosenItems.clear();
        ArrayList<ItemStack> cloned = (ArrayList<ItemStack>) ItemList.clone();

        canPeopleRequest = true;
        while(chosenItems.size() < 3)
        {
            int i = Statics.RandomInt(cloned.size(), false);
            chosenItems.add(cloned.get(i));
            cloned.remove(i);
        }
        Statics.CurrentGame.SendMessageToAllAlivePlayers(
                ChatColor.GREEN + "Type the item you want ! The 3 items are : ");
        Statics.CurrentGame.SendMessageToAllAlivePlayers(ChatColor.BLUE +
                String.format("%s, %s, %s",
                        Statics.GetPrettyName(chosenItems.get(0)),
                        Statics.GetPrettyName(chosenItems.get(1)),
                        Statics.GetPrettyName(chosenItems.get(2))
                )
        );

    }

    public static void TryGetting(Player sender, String message)
    {
        for(int i = 0; i < chosenItems.size(); i++)
        {
            ItemStack itemStack = chosenItems.get(i);
            String itemName = Statics.GetPrettyName(itemStack);
            if(message.equals(itemName))
            {
                Statics.CurrentGame.PlayerList.forEach( player -> {
                    player.getInventory().addItem(itemStack);
                    Msg.send(player, ChatColor.GREEN + sender.getDisplayName() + ChatColor.WHITE + " typed the fastest !");
                });
                canPeopleRequest = false;
                chosenItems.clear();
                return;
            }
        }
    }
}
