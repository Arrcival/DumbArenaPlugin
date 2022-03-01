package com.arrcival.dumbarenaplugin.commands;

import com.arrcival.dumbarenaplugin.Game;
import com.arrcival.dumbarenaplugin.utils.Consts;
import com.arrcival.dumbarenaplugin.utils.Msg;
import com.arrcival.dumbarenaplugin.utils.Statics;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.text.ParseException;
import java.text.spi.NumberFormatProvider;

import static java.lang.Integer.parseInt;

public class NewGame implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(sender instanceof Player)
        {
            if(Statics.CurrentGame == null)
            {
                Player player = (Player)sender;
                if(args.length == 0)
                {
                    Msg.send(sender, "&aCreating arena at your location with a default size of " + Consts.DEFAULT_ARENA_SIZE);
                    createGame(player.getLocation(), Consts.DEFAULT_ARENA_SIZE);
                }
                else
                {
                    String firstArg = args[0];
                    try
                    {
                        int size = Integer.parseInt(firstArg); // what's tried

                        Msg.send(sender, "&aCreating arena at your location with a size of " + firstArg);
                        createGame(player.getLocation(), size);
                    }
                    catch(NumberFormatException e)
                    {
                        Msg.send(sender, "&rFirst argument must be the size of the arena (default 50)");
                        return false;
                    }
                }
            }
            else
            {
                Msg.send(sender, "&rA game is already created");
                return true;
            }

        }
        return true;
    }

    public void createGame(Location location, int size)
    {
        Statics.CurrentGame = new Game(location, size);
        Bukkit.broadcastMessage(Consts.CHAT_PREFIX + "A game is going to start ! Type /joingame to join the game");
    }
}
