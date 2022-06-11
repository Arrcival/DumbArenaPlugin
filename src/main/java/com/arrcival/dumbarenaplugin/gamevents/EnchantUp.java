package com.arrcival.dumbarenaplugin.gamevents;

import com.arrcival.dumbarenaplugin.utils.Consts;
import com.arrcival.dumbarenaplugin.utils.Msg;
import com.arrcival.dumbarenaplugin.utils.Statics;
import com.arrcival.dumbarenaplugin.utils.WorldModification;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class EnchantUp extends GameEventArena{

    List<Material> ProtectionMaterials = Arrays.asList(
            Material.LEATHER_BOOTS, Material.LEATHER_HELMET,  Material.LEATHER_CHESTPLATE,  Material.LEATHER_LEGGINGS,
            Material.IRON_BOOTS, Material.IRON_HELMET,  Material.IRON_CHESTPLATE,  Material.IRON_LEGGINGS,
            Material.CHAINMAIL_BOOTS, Material.CHAINMAIL_HELMET,  Material.CHAINMAIL_CHESTPLATE,  Material.CHAINMAIL_LEGGINGS,
            Material.DIAMOND_BOOTS, Material.DIAMOND_HELMET,  Material.DIAMOND_CHESTPLATE,  Material.DIAMOND_LEGGINGS,
            Material.GOLDEN_BOOTS, Material.GOLDEN_HELMET,  Material.GOLDEN_CHESTPLATE,  Material.GOLDEN_LEGGINGS
            );
    List<Material> SharpnessMaterials = Arrays.asList(
            Material.WOODEN_SWORD, Material.STONE_SWORD, Material.IRON_SWORD, Material.GOLDEN_SWORD, Material.DIAMOND_SWORD
    );
    List<Material> EfficiencyMaterials = Arrays.asList(
            Material.WOODEN_PICKAXE, Material.STONE_PICKAXE, Material.IRON_PICKAXE, Material.GOLDEN_PICKAXE, Material.DIAMOND_PICKAXE,
            Material.WOODEN_SHOVEL, Material.STONE_SHOVEL, Material.IRON_SHOVEL, Material.GOLDEN_SHOVEL, Material.DIAMOND_SHOVEL,
            Material.WOODEN_AXE, Material.STONE_AXE, Material.IRON_AXE, Material.GOLDEN_AXE, Material.DIAMOND_AXE
    );
    @Override
    public void PrepareEvent() {
        Statics.CurrentGame.SendMessageToAllAlivePlayers(ChatColor.GREEN + "In " + Consts.TIME_BETWEEN_ANNOUNCE_AND_EVENT + " seconds : ");
        Statics.CurrentGame.SendMessageToAllAlivePlayers(
                ChatColor.GOLD +
                        "Your current item in hand will be enchanted/upgraded");

        RunEventLater();
    }

    @Override
    public void RunEvent() {
        for(Player player : Statics.CurrentGame.PlayerList)
        {
            PlayerInventory inv = player.getInventory();
            ItemStack item = inv.getItemInMainHand();
            if(item.containsEnchantment(Enchantment.KNOCKBACK))
            {
                item.addUnsafeEnchantment(Enchantment.KNOCKBACK, Math.min(item.getEnchantmentLevel(Enchantment.KNOCKBACK) + 1, 2));
                Msg.send(player, ChatColor.DARK_PURPLE + "Your item got increased knockback.");
                return;
            }
            if(item.containsEnchantment(Enchantment.DAMAGE_ALL))
            {
                item.addEnchantment(Enchantment.DAMAGE_ALL, Math.min(item.getEnchantmentLevel(Enchantment.DAMAGE_ALL) + 1, 5));
                Msg.send(player, ChatColor.DARK_PURPLE + "Your item got increased sharpness.");
                return;
            }
            if(item.containsEnchantment(Enchantment.ARROW_DAMAGE))
            {
                item.addEnchantment(Enchantment.ARROW_DAMAGE, Math.min(item.getEnchantmentLevel(Enchantment.ARROW_DAMAGE) + 1, 5));
                Msg.send(player, ChatColor.DARK_PURPLE + "Your item got increased power.");
                return;
            }
            if(item.containsEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL))
            {
                item.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, Math.min(item.getEnchantmentLevel(Enchantment.PROTECTION_ENVIRONMENTAL) + 1, 4));
                Msg.send(player, ChatColor.DARK_PURPLE + "Your item got increased protection.");
                return;
            }
            if(item.containsEnchantment(Enchantment.DIG_SPEED))
            {
                item.addEnchantment(Enchantment.DIG_SPEED, Math.min(item.getEnchantmentLevel(Enchantment.DIG_SPEED) + 1, 5));
                Msg.send(player, ChatColor.DARK_PURPLE + "Your item got increased efficiency.");
                return;
            }

            if(SharpnessMaterials.contains(item.getType()))
            {
                item.addEnchantment(Enchantment.DAMAGE_ALL, 1);
                Msg.send(player, ChatColor.DARK_PURPLE + "Your item got sharpness.");
                return;
            }
            if(ProtectionMaterials.contains(item.getType()))
            {
                item.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1);
                Msg.send(player, ChatColor.DARK_PURPLE + "Your item got protection.");
                return;
            }
            if(EfficiencyMaterials.contains(item.getType()))
            {
                item.addEnchantment(Enchantment.DIG_SPEED, 1);
                Msg.send(player, ChatColor.DARK_PURPLE + "Your item got efficiency.");
                return;
            }
            if(item.getType() == Material.BOW)
            {
                item.addEnchantment(Enchantment.ARROW_DAMAGE, 1);
                Msg.send(player, ChatColor.DARK_PURPLE + "Your item got power.");
                return;
            }
            if(item.getType() == Material.STICK)
            {
                item.addUnsafeEnchantment(Enchantment.KNOCKBACK, 1);
                item.setAmount(1);
                Msg.send(player, ChatColor.DARK_PURPLE + "Your item got knockback.");
                return;
            }
        }
    }
}
