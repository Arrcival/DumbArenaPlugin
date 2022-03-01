package com.arrcival.dumbarenaplugin.commands;

import com.arrcival.dumbarenaplugin.Game;
import com.arrcival.dumbarenaplugin.utils.Msg;
import com.arrcival.dumbarenaplugin.utils.Statics;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import static java.lang.Integer.parseInt;

public class DestroyGame implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if(Statics.CurrentGame != null)
        {
            Statics.CurrentGame.Destroy();
            Statics.CurrentGame = null;
            Msg.send(sender, "&4Game successfully cancelled.");
        }

        return true;
    }
}
