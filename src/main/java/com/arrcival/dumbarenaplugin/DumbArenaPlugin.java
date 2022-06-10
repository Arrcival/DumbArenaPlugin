package com.arrcival.dumbarenaplugin;

import com.arrcival.dumbarenaplugin.commands.*;
import com.arrcival.dumbarenaplugin.listeners.*;
import com.arrcival.dumbarenaplugin.utils.Statics;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

public final class DumbArenaPlugin extends JavaPlugin {

    public static DumbArenaPlugin instance;

    @Override
    public void onEnable() {
        instance = this;
        getLogger().info("Dumb Arena Plugin ON !");


        getCommand("newgame").setExecutor(new NewGame());
        getCommand("destroygame").setExecutor(new DestroyGame());
        getCommand("exitgame").setExecutor(new ExitGame());
        getCommand("joingame").setExecutor(new JoinGame());
        getCommand("startgame").setExecutor(new StartGame());

        getServer().getPluginManager().registerEvents(new PlayerDied(), this);
        getServer().getPluginManager().registerEvents(new PvpTesting(), this);
        getServer().getPluginManager().registerEvents(new ArenaBordersDestroyed(), this);
        getServer().getPluginManager().registerEvents(new PlayerChat(), this);
        getServer().getPluginManager().registerEvents(new SpecificBlocksDestroyed(), this);
        getServer().getPluginManager().registerEvents(new ChickenKilled(), this);
        getServer().getPluginManager().registerEvents(new PlayerRightClick(), this);

        Statics.AddEnabledEvents();
    }

    @Override
    public void onDisable() {
        getLogger().info("Dumb Arena Plugin OFF !");
    }

    public static DumbArenaPlugin getInstance() {
        return instance;
    }
}
