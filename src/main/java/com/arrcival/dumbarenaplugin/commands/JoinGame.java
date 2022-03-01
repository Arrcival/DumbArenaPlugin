package com.arrcival.dumbarenaplugin.commands;

import com.arrcival.dumbarenaplugin.Game;
import com.arrcival.dumbarenaplugin.utils.Msg;
import com.arrcival.dumbarenaplugin.utils.Statics;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.awt.image.ColorConvertOp;

public class JoinGame implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if(sender instanceof Player)
        {
            Player player = (Player)sender;
            if(Statics.CurrentGame != null && Statics.CurrentGame.State == Game.GameState.WAITING)
            {
                Statics.CurrentGame.JoinPlayer(player);
                Statics.CurrentGame.SendMessageToAllAlivePlayers(
                        "There is now " + ChatColor.GOLD + Statics.CurrentGame.PlayerList.size() +
                        ChatColor.WHITE + " player(s) in the game."
                );
            }
            else
            {
                Msg.send(player, "There's no game waiting for players.");
            }
        }


        return true;
    }
}
