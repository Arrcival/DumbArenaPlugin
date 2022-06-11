package com.arrcival.dumbarenaplugin.gamevents;


import com.arrcival.dumbarenaplugin.DumbArenaPlugin;
import com.arrcival.dumbarenaplugin.utils.Consts;
import com.arrcival.dumbarenaplugin.utils.Statics;
import org.bukkit.scheduler.BukkitRunnable;

import java.lang.reflect.InvocationTargetException;

public abstract class GameEventArena {

    public abstract void PrepareEvent();
    public abstract void RunEvent() throws InvocationTargetException, IllegalAccessException;

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
                if(Statics.IsGameRunning()) {
                    try {
                        RunEvent();
                    } catch (InvocationTargetException e) {
                        e.printStackTrace();
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
            }
        }.runTaskLater(DumbArenaPlugin.getInstance(), (Consts.TIME_BETWEEN_ANNOUNCE_AND_EVENT + extraSecs) * Consts.TICKS_PER_SECOND);
    }

}
