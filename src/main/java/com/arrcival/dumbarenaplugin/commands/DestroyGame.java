package com.arrcival.dumbarenaplugin.commands;

import com.arrcival.dumbarenaplugin.utils.Msg;
import com.arrcival.dumbarenaplugin.utils.Statics;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.lang.reflect.InvocationTargetException;

public class DestroyGame implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if(Statics.CurrentGame != null)
        {
            try {
                Statics.CurrentGame.Destroy();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            Statics.CurrentGame = null;
            Msg.send(sender, "&4Game successfully cancelled.");
        }

        return true;
    }
}
