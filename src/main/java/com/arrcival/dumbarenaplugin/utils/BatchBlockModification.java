package com.arrcival.dumbarenaplugin.utils;

import com.arrcival.dumbarenaplugin.DumbArenaPlugin;
import jdk.internal.net.http.common.Pair;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.scheduler.BukkitRunnable;
import org.checkerframework.checker.units.qual.A;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;

public class BatchBlockModification {
    ArrayList<Pairing<Location, Material>> blockQueue;
    ArrayList<Pairing<Object, Method>> methods;

    public BatchBlockModification()
    {
        blockQueue = new ArrayList<>();
        methods = new ArrayList<>();
    }

    public void AddToQueue(Location loc, Material mat)
    {
        blockQueue.add(new Pairing<>(loc, mat));
    }

    public void AddEndMethods(Object object, Method method)
    {
        methods.add(new Pairing<>(object, method));
    }

    public void Run() throws InvocationTargetException, IllegalAccessException {
        if(blockQueue.size() > 0) {
            new BukkitRunnable() {
                public void run()
                {
                    for(int i = 0; i < Math.min(blockQueue.size(), Consts.BLOCKS_PER_TICK); i++) {
                        Pairing<Location, Material> pair = blockQueue.get(0);
                        pair.first.getBlock().setType(pair.second);
                        blockQueue.remove(0);
                    }
                    try {
                        Run();
                    } catch (InvocationTargetException e) {
                        e.printStackTrace();
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
            }.runTaskLater(DumbArenaPlugin.getInstance(), 1);
        } else {
            for(int i = 0; i < methods.size(); i++)
            {
                methods.get(i).second.invoke(methods.get(i).first);
            }
        }
    }
}
