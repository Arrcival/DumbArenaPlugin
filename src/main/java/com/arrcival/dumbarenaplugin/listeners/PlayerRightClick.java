package com.arrcival.dumbarenaplugin.listeners;

import com.arrcival.dumbarenaplugin.gamevents.TypeToGet;
import com.arrcival.dumbarenaplugin.utils.Msg;
import com.arrcival.dumbarenaplugin.utils.Statics;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

public class PlayerRightClick implements Listener {

    @EventHandler
    public void onPlayerRightClick(PlayerInteractEvent event)
    {
        if(!Statics.IsGameRunning()) return;
        Player player = event.getPlayer();
        if(Statics.CurrentGame.GetPlayerIndex(player) == -1) return;
        if(event.getHand() != EquipmentSlot.HAND) return;

        Action action = event.getAction();
        if(action != Action.RIGHT_CLICK_BLOCK) return;
        if(!player.isSneaking()) return;
        Block block = event.getClickedBlock();
        if(block == null) return;
        if(block.getType() != Material.CRAFTING_TABLE) return;

        // all checked passed trying to build armor
        // we cancel the event only if the user has iron or diamond in hand
        PlayerInventory inv = player.getInventory();
        int ingots = inv.getItemInMainHand().getAmount();
        if(inv.getItemInMainHand().getType() == Material.IRON_INGOT)
        {
            event.setCancelled(true);
            if(inv.getChestplate() == null && ingots >= 8)
            {
                ItemStack item = new ItemStack(Material.IRON_CHESTPLATE);
                inv.setChestplate(item);
                inv.getItemInMainHand().setAmount(ingots - 8);
                Msg.send(player, ChatColor.GRAY + "Iron chestplate automatically created and equipped.");
                return;
            }
            if(inv.getLeggings() == null && ingots >= 7)
            {
                ItemStack item = new ItemStack(Material.IRON_LEGGINGS);
                inv.setLeggings(item);
                inv.getItemInMainHand().setAmount(ingots - 7);
                Msg.send(player, ChatColor.GRAY + "Iron leggings automatically created and equipped.");
                return;
            }
            if(inv.getHelmet() == null && ingots >= 5)
            {
                ItemStack item = new ItemStack(Material.IRON_HELMET);
                inv.setHelmet(item);
                inv.getItemInMainHand().setAmount(ingots - 5);
                Msg.send(player, ChatColor.GRAY + "Iron helmt automatically created and equipped.");
                return;
            }
            if(inv.getBoots() == null && ingots >= 4)
            {
                ItemStack item = new ItemStack(Material.IRON_BOOTS);
                inv.setBoots(item);
                inv.getItemInMainHand().setAmount(ingots - 4);
                Msg.send(player, ChatColor.GRAY + "Iron boots automatically created and equipped.");
                return;
            }
        }
        if(inv.getItemInMainHand().getType() == Material.DIAMOND)
        {
            event.setCancelled(true);
            if(inv.getChestplate() == null && ingots >= 8)
            {
                ItemStack item = new ItemStack(Material.DIAMOND_CHESTPLATE);
                inv.setChestplate(item);
                inv.getItemInMainHand().setAmount(ingots - 8);
                Msg.send(player, ChatColor.GRAY + "Diamond chestplate automatically created and equipped.");
                return;
            }
            if(inv.getLeggings() == null && ingots >= 7)
            {
                ItemStack item = new ItemStack(Material.DIAMOND_LEGGINGS);
                inv.setLeggings(item);
                inv.getItemInMainHand().setAmount(ingots - 7);
                Msg.send(player, ChatColor.GRAY + "Diamond leggings automatically created and equipped.");
                return;
            }
            if(inv.getHelmet() == null && ingots >= 5)
            {
                ItemStack item = new ItemStack(Material.DIAMOND_HELMET);
                inv.setHelmet(item);
                inv.getItemInMainHand().setAmount(ingots - 5);
                Msg.send(player, ChatColor.GRAY + "Diamond helmet automatically created and equipped.");
                return;
            }
            if(inv.getBoots() == null && ingots >= 4)
            {
                ItemStack item = new ItemStack(Material.DIAMOND_BOOTS);
                inv.setBoots(item);
                inv.getItemInMainHand().setAmount(ingots - 4);
                Msg.send(player, ChatColor.GRAY + "Diamond boots automatically created and equipped.");
            }
        }
    }
}
