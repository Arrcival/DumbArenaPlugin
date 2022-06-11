package com.arrcival.dumbarenaplugin.utils;

import com.arrcival.dumbarenaplugin.DumbArenaPlugin;
import com.arrcival.dumbarenaplugin.Game;
import com.arrcival.dumbarenaplugin.gamevents.*;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.loot.LootContext;
import org.bukkit.loot.LootTable;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import java.util.regex.Pattern;

public class Statics {

    public static Game CurrentGame = null;
    static ArrayList<GameEventArena> EnabledEvents = new ArrayList<GameEventArena>();

    public static void CleanInventory(Player player)
    {
        for(ItemStack inven : player.getInventory().getContents())
        {
            if (inven == null) continue; // null check
            player.getInventory().remove(inven);
        }
        player.updateInventory();
    }

    public static int RandomInt(int max, boolean inclusiveMax)
    {
        return RandomInt(0, max, inclusiveMax);
    }

    public static int RandomInt(int inclusiveMin, int max, boolean inclusiveMax)
    {
        if(inclusiveMax)
            max += 1;
        return ThreadLocalRandom.current().nextInt(inclusiveMin, max);
    }

    public static void AddEnabledEvents()
    {
        EnabledEvents.clear();
        EnabledEvents.add(new Generate3DBlocks());
        EnabledEvents.add(new GivePlayersItem());
        //EnabledEvents.add(new FloorIsLava());
        //EnabledEvents.add(new ArenaGetsSmaller());
        EnabledEvents.add(new TypeToGet());
        EnabledEvents.add(new GenerateTrees());
        EnabledEvents.add(new RandomEffect());
        EnabledEvents.add(new ItemDrop());
        EnabledEvents.add(new BlockSpawn());
        EnabledEvents.add(new ChickenLoot());
        EnabledEvents.add(new OneBlockGetsRemoved());
    }

    public static GameEventArena PickRandomEvent()
    {
        GameEventArena event = EnabledEvents.get(RandomInt(EnabledEvents.size(), false));
        if(event instanceof GenerateTrees && Statics.IsGameRunning() && !WorldModification.canHaveTrees())
        {
            EnabledEvents.remove(event);
            PickRandomEvent();
        }
        if(event instanceof OneBlockGetsRemoved && Statics.IsGameRunning() && WorldModification.AppearedBlocks.size() == 0)
        {
            EnabledEvents.remove(event);
            PickRandomEvent();
        }
        return event;
    }

    public static boolean IsGameRunning()
    {
        return (CurrentGame != null && CurrentGame.State == Game.GameState.IN_GAME);
    }

    public static String GetDisplayedPercentage(Float f)
    {
        Float f2 = f * 100;
        return f2.toString();
    }

    public static String GetPrettyName(ItemStack item)
    {
        return item.getType().name().replace("_", " ").toLowerCase();
    }
    public static String GetPrettyName(Material mat)
    {
        return mat.name().replace("_", " ").toLowerCase();
    }
    public static String GetPrettyName(String str)
    {
        return str.replace("_", " ").toLowerCase();
    }

    public static ItemStack CreateSplashPotion(PotionEffectType type, int duration, int rank)
    {
        ItemStack splash = new ItemStack(Material.SPLASH_POTION);
        PotionMeta pMeta = (PotionMeta)splash.getItemMeta();
        pMeta.addCustomEffect(new PotionEffect(type, duration, rank), true);
        splash.setItemMeta(pMeta);
        return splash;
    }

    public static LootTable CreateLootTableFromItems(ItemStack[] items)
    {
        return new LootTable() {
            @Override
            public Collection<ItemStack> populateLoot(Random random, LootContext context) {
                return new ArrayList<ItemStack>(Arrays.asList(items));
            }

            @Override
            public void fillInventory(Inventory inventory, Random random, LootContext context) {
                inventory.clear();
                inventory.addItem(items);
            }

            @Override
            public NamespacedKey getKey() {
                return new NamespacedKey(DumbArenaPlugin.getInstance(), "dumbArenaKey");
            }
        };
    }
}
