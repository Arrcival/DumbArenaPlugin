package com.arrcival.dumbarenaplugin.gamevents;


import com.arrcival.dumbarenaplugin.DumbArenaPlugin;
import com.arrcival.dumbarenaplugin.utils.Consts;
import com.arrcival.dumbarenaplugin.utils.Statics;
import org.bukkit.scheduler.BukkitRunnable;

public abstract class GameEventArena {

    public abstract void PrepareEvent();
    public abstract void RunEvent();

    public void RunEventLater()
    {
        RunEventLater(0);
    }

    public void RunEventLater(int extraSecs)
    {
        new BukkitRunnable()
        {
            public void run()
            {
                if(Statics.IsGameRunning())
                    RunEvent();
            }
        }.runTaskLater(DumbArenaPlugin.getInstance(), (Consts.TIME_BETWEEN_ANNOUNCE_AND_EVENT + extraSecs) * Consts.TICKS_PER_SECOND);
    }

}
