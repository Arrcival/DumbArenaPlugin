package com.arrcival.dumbarenaplugin;

import com.arrcival.dumbarenaplugin.gamevents.FloorIsLava;
import com.arrcival.dumbarenaplugin.gamevents.GameEventArena;
import com.arrcival.dumbarenaplugin.utils.*;
import org.bukkit.*;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import java.lang.reflect.InvocationTargetException;
import java.sql.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;


public class Game {

    public ArrayList<Player> PlayerList;
    public ArrayList<Player> DeathList;

    public ArrayList<InfosOnStart> PlayerInfosOnStart;

    public GameState State;
    public enum GameState { WAITING, IN_GAME, NULL };

    Location gameLocation;
    public int arenaSize;
    Location oppositeCornerLocation;

    public boolean PvP = false;


    public Game(Location location, int size)
    {
        State = GameState.WAITING;
        PlayerList = new ArrayList<Player>();
        DeathList = new ArrayList<Player>();
        PlayerInfosOnStart = new ArrayList<InfosOnStart>();
        gameLocation = location;
        arenaSize = size;

    }

    public void StartGame() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        //if(PlayerList.size() < 2) return;
        oppositeCornerLocation = gameLocation.clone();
        oppositeCornerLocation.setX(oppositeCornerLocation.getBlockX() + arenaSize);
        oppositeCornerLocation.setY(oppositeCornerLocation.getBlockY() + arenaSize);
        oppositeCornerLocation.setZ(oppositeCornerLocation.getBlockZ() + arenaSize);

        BatchBlockModification blockQueue = WorldModification.GenerateMap(gameLocation, oppositeCornerLocation);
        blockQueue.AddEndMethods(this, this.getClass().getMethod("AfterMapIsCreated"));
        blockQueue.Run();
    }

    public void AfterMapIsCreated() throws InvocationTargetException, IllegalAccessException {
        WorldModification.ChangeMapWith(Material.DIRT, 0.02f);
        WorldModification.ChangeMapWith(Material.IRON_ORE, 0.003f);
        State = GameState.IN_GAME;
        PvP = false;
        SendMessageToAllAlivePlayers("The game is starting !");
        preparePlayers();
        SendMessageToAllAlivePlayers("There is " + Integer.toString(PlayerList.size()) + " players participating");
        SendMessageToAllAlivePlayers("Last man alive, wins. GL & HF !");

        Check2Players();
        new BukkitRunnable(){
            @Override
            public void run() {
                if(PlayerList.size() > 2)
                    EventsOverTime.EnablePvPAfter(Consts.TIME_BEFORE_PVP);
                else
                    EventsOverTime.EnablePvPAfter(Consts.TIME_BEFORE_PVP / 4);
                PrepareEvent();
            }
        }.runTaskLater(DumbArenaPlugin.getInstance(), Consts.TIME_DELAY_BEFORE_FIRST_EVENT * Consts.TICKS_PER_SECOND);

        int delayLava = Consts.TIME_BEFORE_LAVA_INCREASE + PlayerList.size() * Consts.ADDITIONAL_TIME_PER_PLAYERS;
        SendMessageToAllAlivePlayers(ChatColor.DARK_PURPLE + "After " + Consts.FIRST_LAVA_INCREASE+ "s, every " + delayLava + "s, the floor will be filled with lava !");
        addLavaOverTime(delayLava, Consts.FIRST_LAVA_INCREASE);
    }

    void addLavaOverTime(int delay, int delayFirstTime)
    {
        new BukkitRunnable(){
            @Override
            public void run() {

                FloorIsLava f = new FloorIsLava();
                f.PrepareEvent();
                if(Statics.IsGameRunning() && !WorldModification.IsFloorTooHigh())
                    addLavaOverTime(delay, 0);

            }
        }.runTaskLater(DumbArenaPlugin.getInstance(),
                Consts.TICKS_PER_SECOND * (delay + delayFirstTime));
    }

    void preparePlayers()
    {
        Location tpLocation = gameLocation.clone();
        tpLocation.setX(tpLocation.getBlockX() + arenaSize / 2);
        tpLocation.setZ(tpLocation.getBlockZ() + arenaSize / 2);
        tpLocation.setY(tpLocation.getBlockY() + 1);

        ItemCreator pickUnbreaking = new ItemCreator(Material.IRON_PICKAXE);
        pickUnbreaking.setName("Better fight with your hand");
        pickUnbreaking.setUnbreakable(true);
        pickUnbreaking.addDamages(-4);

        PlayerList.forEach(player -> {
            PlayerInfosOnStart.add(new InfosOnStart(player));
            player.setGameMode(GameMode.SURVIVAL);
            Statics.CleanInventory(player);
            player.setFoodLevel(20);
            player.setHealth(20);
            player.addPotionEffect(new PotionEffect(PotionEffectType.SATURATION, 60 * 30 * Consts.TICKS_PER_SECOND, 5));
            player.teleport(tpLocation);
            player.getInventory().addItem(pickUnbreaking.getItemStack());
            player.getInventory().addItem(new ItemStack(Material.DIRT, arenaSize * 4));
            player.getInventory().addItem(new ItemStack(Material.OAK_PLANKS, 16));
        });
    }

    void PrepareEvent()
    {
        GameEventArena event = Statics.PickRandomEvent();
        event.PrepareEvent();
        new BukkitRunnable() {
            public void run()
            {
                if(Statics.IsGameRunning())
                    PrepareEvent();
            }
        }.runTaskLater(DumbArenaPlugin.getInstance(), Consts.TIME_BETWEEN_EVENTS * Consts.TICKS_PER_SECOND);
    }


    public void SendMessageToAllPlayers(ArrayList<Player> playerList, String message)
    {
        playerList.forEach(player -> Msg.send(player, message));
    }

    public void SendMessageToAllAlivePlayers(String message)
    {
        SendMessageToAllPlayers(PlayerList, message);
    }

    public void JoinPlayer(Player player)
    {
        if(State == GameState.WAITING)
        {
            if(GetPlayerIndex(player) == -1)
            {
                PlayerList.add(player);
                Msg.send(player, "You've successfully joined the game !");
            }
            else
                Msg.send(player, "You're already part of the game.");
        } else
            Msg.send(player, "You cannot join a game right now.");

    }

    public void ExitPlayer(Player player)
    {
        if(State == GameState.WAITING)
        {
            int i = GetPlayerIndex(player);
            if(i != -1)
            {
                PlayerList.remove(i);
                Msg.send(player, "You've successfully left the game !");
            }
            else
                Msg.send(player, "You're not part of any game.");
        } else
            Msg.send(player, "You cannot join a game right now.");

    }

    public void Destroy() throws InvocationTargetException, IllegalAccessException {
        if(State == GameState.IN_GAME)
        {
            gameEnd();
        }
        State = GameState.NULL;
        Statics.CurrentGame = null;

    }

    public int GetPlayerIndex(Player p)
    {
        for(int i = 0; i < PlayerList.size(); i++)
        {
            if(PlayerList.get(i).getDisplayName() == p.getDisplayName())
                return i;
        }
        return -1;
    }

    public void PlayerDied(int i) throws InvocationTargetException, IllegalAccessException {
        Player p = PlayerList.get(i);
        PlayerList.remove(i);
        DeathList.add(p);
        p.setGameMode(GameMode.SPECTATOR);
        gameOverCheck();
        Check2Players();
    }

    void Check2Players()
    {
        if(PlayerList.size() == 2)
        {
            SendMessageToAllAlivePlayers(ChatColor.DARK_RED + "Natural regeneration is now off.");
            PlayerList.get(0).getWorld().setGameRule(GameRule.NATURAL_REGENERATION, false);
        }
    }

    void gameOverCheck() throws InvocationTargetException, IllegalAccessException {
        if(PlayerList.size() <= 1)
        {
            SendMessageToAllPlayers(PlayerList, ChatColor.GOLD + "You won the game !!!");
            if(PlayerList.size() > 0)
                SendMessageToAllPlayers(DeathList, "The winner is : " + ChatColor.GOLD + PlayerList.get(0).getDisplayName());
            gameEnd();
            Statics.CurrentGame = null;
        }
        else {
            String txt = ChatColor.RED + Integer.toString( PlayerList.size()) + ChatColor.WHITE + " players remaining...";
            SendMessageToAllPlayers(PlayerList, txt);
            SendMessageToAllPlayers(DeathList, txt);
        }
    }

    void gameEnd() throws InvocationTargetException, IllegalAccessException {
        PlayerList.forEach(player -> player.getActivePotionEffects().forEach(e -> player.removePotionEffect(e.getType())));
        PlayerList.forEach(player -> player.setGameMode(GameMode.SURVIVAL));
        DeathList.forEach(player -> player.setGameMode(GameMode.SURVIVAL));
        PlayerInfosOnStart.forEach(info -> info.RestorePlayer());

        if(DeathList.size() > 0)
            DeathList.get(0).getWorld().setGameRule(GameRule.NATURAL_REGENERATION, true);
        BatchBlockModification batch = WorldModification.GenerateDeletionMap(gameLocation, oppositeCornerLocation);
        batch.Run();
    }

    void EnablePvP()
    {
        if(State == GameState.IN_GAME)
        {
            PvP = true;
            SendMessageToAllAlivePlayers(ChatColor.BLUE + "PvP is now enabled.");
        }
    }



    public class InfosOnStart
    {
        Player p;
        ItemStack[] inventory;
        ItemStack[] armorInventory;
        GameMode gameMode;
        Location location;
        int level;
        Collection<PotionEffect> potionEffects;

        public InfosOnStart(Player player)
        {
            p = player;
            inventory = player.getInventory().getContents();
            gameMode = player.getGameMode();
            location = player.getLocation();
            armorInventory = player.getInventory().getArmorContents();
            level = player.getLevel();
            potionEffects = player.getActivePotionEffects();
        }

        public void RestorePlayer()
        {
            Statics.CleanInventory(p);
            p.getInventory().setContents(inventory);
            p.getInventory().setArmorContents(armorInventory);
            p.setGameMode(gameMode);
            p.teleport(location);
            p.setLevel(level);
            for (PotionEffect effect : p.getActivePotionEffects())
                p.removePotionEffect(effect.getType());
            for(PotionEffect effect : potionEffects)
                p.addPotionEffect(effect);
        }
    }
}
