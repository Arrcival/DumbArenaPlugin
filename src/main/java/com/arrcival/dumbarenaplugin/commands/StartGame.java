package com.arrcival.dumbarenaplugin.commands;

import com.arrcival.dumbarenaplugin.Game;
import com.arrcival.dumbarenaplugin.utils.Msg;
import com.arrcival.dumbarenaplugin.utils.Statics;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.lang.reflect.InvocationTargetException;

public class StartGame implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if(Statics.CurrentGame != null && Statics.CurrentGame.State == Game.GameState.WAITING)
        {
            Statics.AddEnabledEvents();
            try {
                Statics.CurrentGame.StartGame();
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return true;
    }
}
