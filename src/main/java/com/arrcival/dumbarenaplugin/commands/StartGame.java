package com.arrcival.dumbarenaplugin.commands;

import com.arrcival.dumbarenaplugin.Game;
import com.arrcival.dumbarenaplugin.utils.Msg;
import com.arrcival.dumbarenaplugin.utils.Statics;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class StartGame implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if(Statics.CurrentGame != null && Statics.CurrentGame.State == Game.GameState.WAITING)
        {
            Statics.AddEnabledEvents();
            Statics.CurrentGame.StartGame();
        }
        return true;
    }
}
