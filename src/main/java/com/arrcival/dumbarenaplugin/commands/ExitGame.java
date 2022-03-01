package com.arrcival.dumbarenaplugin.commands;

import com.arrcival.dumbarenaplugin.Game;
import com.arrcival.dumbarenaplugin.utils.Msg;
import com.arrcival.dumbarenaplugin.utils.Statics;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ExitGame implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if(sender instanceof Player)
        {
            Player player = (Player)sender;
            if(Statics.CurrentGame != null && Statics.CurrentGame.State == Game.GameState.WAITING)
            {
                Statics.CurrentGame.ExitPlayer(player);
            }
            else
            {
                Msg.send(player, "There's no game you could even leave.");
            }
        }

        return true;
    }
}
