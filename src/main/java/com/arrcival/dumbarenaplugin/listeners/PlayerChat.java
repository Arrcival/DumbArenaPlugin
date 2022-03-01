package com.arrcival.dumbarenaplugin.listeners;

import com.arrcival.dumbarenaplugin.gamevents.TypeToGet;
import com.arrcival.dumbarenaplugin.utils.Msg;
import com.arrcival.dumbarenaplugin.utils.Statics;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class PlayerChat implements Listener {

    @EventHandler
    public void onPlayerSentChat(AsyncPlayerChatEvent event)
    {
        Player player = event.getPlayer();
        if(Statics.IsGameRunning() && TypeToGet.canPeopleRequest && Statics.CurrentGame.GetPlayerIndex(player) != -1)
        {
            TypeToGet.TryGetting(player, event.getMessage().toLowerCase());
        }
    }
}
