package com.arrcival.dumbarenaplugin.utils;

import org.bukkit.*;
import org.bukkit.entity.Bat;
import org.bukkit.entity.Chicken;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;

public class WorldModification  {

    public static ArrayList<Material> OldArena = new ArrayList<>();
    public static ArrayList<Location> ArenaBorders = new ArrayList<>();

    public static Location startingLocation;
    public static Location endLocation;

    public static ArrayList<Material> AppearedBlocks = new ArrayList<>();

    public static int floor = 0;
    public static int ceiling = 0;

    public static int zPositive = 0;
    public static int zNegative = 0;
    public static int xPositive = 0;
    public static int xNegative = 0;

    public static float getArenaMiddle() {
        return (endLocation.getBlockX() - startingLocation.getBlockX()) / 2;
    }

    public static void CreateMap(Location start, Location end)
    {
        floor = 0; ceiling = 0; zPositive = 0; zNegative = 0; xPositive = 0; xNegative = 0;
        startingLocation = start;
        endLocation = end;
        AppearedBlocks.clear();
        for(int x = startingLocation.getBlockX() - 1; x < endLocation.getBlockX() + 1; x++)
        {
            for(int y = startingLocation.getBlockY() - 1; y < endLocation.getBlockY() + 1; y++)
            {
                for(int z = startingLocation.getBlockZ() - 1; z < endLocation.getBlockZ() + 1; z++)
                {
                    OldArena.add((new Location(startingLocation.getWorld(), x, y, z).getBlock().getType()));

                    if(     x == startingLocation.getBlockX() - 1 || x == endLocation.getBlockX() ||
                            y == startingLocation.getBlockY() - 1 || y == endLocation.getBlockY() ||
                            z == startingLocation.getBlockZ() - 1 || z == endLocation.getBlockZ())
                    {
                        Location loc = new Location(startingLocation.getWorld(), x, y, z);
                        loc.getBlock().setType(Material.WHITE_STAINED_GLASS);
                        ArenaBorders.add(loc);
                    } else
                        new Location(startingLocation.getWorld(), x, y, z).getBlock().setType(Material.AIR);
                }
            }
        }
    }

    public static BatchBlockModification GenerateMap(Location start, Location end)
    {
        floor = 0; ceiling = 0; zPositive = 0; zNegative = 0; xPositive = 0; xNegative = 0;
        startingLocation = start;
        endLocation = end;
        AppearedBlocks.clear();
        BatchBlockModification blockQueue = new BatchBlockModification();

        for(int x = startingLocation.getBlockX() - 1; x < endLocation.getBlockX() + 1; x++)
        {
            for(int y = startingLocation.getBlockY() - 1; y < endLocation.getBlockY() + 1; y++)
            {
                for(int z = startingLocation.getBlockZ() - 1; z < endLocation.getBlockZ() + 1; z++)
                {
                    OldArena.add((new Location(startingLocation.getWorld(), x, y, z).getBlock().getType()));

                    if(     x == startingLocation.getBlockX() - 1 || x == endLocation.getBlockX() ||
                            y == startingLocation.getBlockY() - 1 || y == endLocation.getBlockY() ||
                            z == startingLocation.getBlockZ() - 1 || z == endLocation.getBlockZ())
                    {
                        Location loc = new Location(startingLocation.getWorld(), x, y, z);
                        blockQueue.AddToQueue(loc, Material.WHITE_STAINED_GLASS);
                        ArenaBorders.add(loc);
                    } else
                    {
                        Location loc = new Location(startingLocation.getWorld(), x, y, z);
                        blockQueue.AddToQueue(loc, Material.AIR);
                    }
                }
            }
        }
        return blockQueue;
    }

    public static void DeleteMap(Location startingLocation, Location endLocation)
    {
        for(int x = startingLocation.getBlockX() - 1; x < endLocation.getBlockX() + 1; x++)
        {
            for(int y = startingLocation.getBlockY() - 1; y < endLocation.getBlockY() + 1; y++)
            {
                for(int z = startingLocation.getBlockZ() - 1; z < endLocation.getBlockZ() + 1; z++)
                {
                    if(OldArena.size() >= 0)
                    {
                        new Location(startingLocation.getWorld(), x, y, z).getBlock().setType(OldArena.get(0));
                        OldArena.remove(0);
                    } else
                        new Location(startingLocation.getWorld(), x, y, z).getBlock().setType(Material.AIR);

                }
            }
        }
        ArenaBorders.clear();
    }

    public static BatchBlockModification GenerateDeletionMap(Location startingLocation, Location endLocation)
    {
        BatchBlockModification blockQueue = new BatchBlockModification();
        for(int x = startingLocation.getBlockX() - 1; x < endLocation.getBlockX() + 1; x++)
        {
            for(int y = startingLocation.getBlockY() - 1; y < endLocation.getBlockY() + 1; y++)
            {
                for(int z = startingLocation.getBlockZ() - 1; z < endLocation.getBlockZ() + 1; z++)
                {
                    if(OldArena.size() >= 0)
                    {
                        blockQueue.AddToQueue(new Location(startingLocation.getWorld(), x, y, z), OldArena.get(0));
                        OldArena.remove(0);
                    } else
                        blockQueue.AddToQueue(new Location(startingLocation.getWorld(), x, y, z), Material.AIR);

                }
            }
        }
        ArenaBorders.clear();
        return blockQueue;
    }

    public static void ReplaceWith(Material from, Material to)
    {
        for(int x = startingLocation.getBlockX() + xNegative; x < endLocation.getBlockX() - xPositive; x++)
        {
            for(int y = startingLocation.getBlockY() + floor; y < endLocation.getBlockY() - ceiling; y++)
            {
                for(int z = startingLocation.getBlockZ() + zNegative; z < endLocation.getBlockZ() - zPositive; z++)
                {
                    Location loc = new Location(startingLocation.getWorld(), x, y, z);
                    if(loc.getBlock().getType() == from)
                        loc.getBlock().setType(to);

                }
            }
        }
    }
    public static void ChangeMapWith(Material mat, Float percentage)
    {
        for(int x = startingLocation.getBlockX() + xNegative; x < endLocation.getBlockX() - xPositive; x++)
        {
            for(int y = startingLocation.getBlockY() + floor; y < endLocation.getBlockY() - ceiling; y++)
            {
                for(int z = startingLocation.getBlockZ() + zNegative; z < endLocation.getBlockZ() - zPositive; z++)
                {
                    double rng = Math.random();
                    if(rng <= percentage)
                        new Location(startingLocation.getWorld(), x, y, z).getBlock().setType(mat);

                }
            }
        }
    }

    public static void FillFloor(Material mat)
    {
        if(floor >= (endLocation.getBlockY() - startingLocation.getBlockY()) - 2)
        {
            Statics.CurrentGame.SendMessageToAllAlivePlayers(ChatColor.RED + "But the floor is already too high...");
            return;
        }
        for(int x = startingLocation.getBlockX() + xNegative; x < endLocation.getBlockX() - xPositive; x++)
        {
            for(int z = startingLocation.getBlockZ() + zNegative; z < endLocation.getBlockZ() - zPositive; z++)
            {
                new Location(startingLocation.getWorld(), x, startingLocation.getBlockY() + floor, z).getBlock().setType(mat);
            }
        }
        floor++;
    }

    public static void FillCeiling(Material mat)
    {
        if(IsFloorTooHigh())
        {
            Statics.CurrentGame.SendMessageToAllAlivePlayers(ChatColor.RED + "But the ceiling is already too high...");
            return;
        }
        for(int x = startingLocation.getBlockX() + xNegative; x < endLocation.getBlockX() - xPositive; x++)
        {
            for(int z = startingLocation.getBlockZ() + zNegative; z < endLocation.getBlockZ() - zPositive; z++)
            {
                new Location(startingLocation.getWorld(), x, endLocation.getBlockY() - ceiling - 1, z).getBlock().setType(mat);
            }
        }
        ceiling++;
    }

    public static void GenerateTrees(int amount)
    {
        for(int i = 0; i < amount; i++)
        {
            int x = Statics.RandomInt(startingLocation.getBlockX() + 2 + xNegative, endLocation.getBlockX() - 2 - xPositive, true);
            int z = Statics.RandomInt(startingLocation.getBlockZ() + 2 + zNegative, endLocation.getBlockZ() - 2 - zPositive, true);
            new Location(startingLocation.getWorld(), x, startingLocation.getBlockY() + floor, z).getBlock().setType(Material.DIRT);
            startingLocation.getWorld().generateTree(
                new Location(startingLocation.getWorld(), x, startingLocation.getBlockY() + floor + 1, z), TreeType.TREE);
        }
    }

    public static boolean IsLocationArena(Location loc)
    {
        if(ArenaBorders.isEmpty())
            return false;
        for(int i = 0; i < ArenaBorders.size(); i++)
        {
            Location curr = ArenaBorders.get(i);
            if(curr.getBlockX() == loc.getBlockX() && curr.getBlockY() == loc.getBlockY() && curr.getBlockZ() == loc.getBlockZ())
                return true;
        }
        return false;
    }

    public static boolean canHaveTrees()
    {
        if(endLocation.getBlockX() - startingLocation.getBlockX() < 7)
            return false;
        if(endLocation.getBlockZ() - startingLocation.getBlockZ() - floor - ceiling < 7)
            return false;
        return true;
    }

    public static boolean AreCoordsInArena(Location loc)
    {
        if(loc.getBlockX() < startingLocation.getBlockX())
            return false;
        if(loc.getBlockY() < startingLocation.getBlockY())
            return false;
        if(loc.getBlockZ() < startingLocation.getBlockZ())
            return false;
        if(loc.getBlockX() > endLocation.getBlockX())
            return false;
        if(loc.getBlockY() > endLocation.getBlockY())
            return false;
        if(loc.getBlockZ() > endLocation.getBlockZ())
            return false;
        return true;
    }

    public static void DropItems(ItemStack[] itemStacks)
    {
        Location spawn = new Location(
                endLocation.getWorld(),
                endLocation.getBlockX() - getArenaMiddle() + (xPositive - xNegative),
                endLocation.getBlockY() - ceiling - 1,
                endLocation.getBlockZ() - getArenaMiddle() + (zPositive - zNegative)
        );
        for(int i = 0; i < itemStacks.length; i++)
            endLocation.getWorld().dropItemNaturally(spawn, itemStacks[i]);
    }

    public static void CreateChickenCeiling(int amount)
    {
        for(int i = 0; i < amount; i++)
        {
            int x = Statics.RandomInt(startingLocation.getBlockX() + 1 + xNegative, endLocation.getBlockX() - 1 - xPositive, true);
            int z = Statics.RandomInt(startingLocation.getBlockZ() + 1 + zNegative, endLocation.getBlockZ() - 1 - zPositive, true);
            Location spawn = new Location(
                    endLocation.getWorld(),
                    x,
                    endLocation.getBlockY() - ceiling - 1,
                    z
            );
            Chicken chicken = (Chicken)endLocation.getWorld().spawnEntity(spawn, EntityType.CHICKEN);
            chicken.setHealth(1);
        }
    }

    public static void CreateBlockInTheMiddle(Material mat)
    {
        Location spawn = new Location(
                endLocation.getWorld(),
                (int)(endLocation.getBlockX() - getArenaMiddle() - 1),
                (int)(endLocation.getBlockY() - getArenaMiddle() + floor / 2),
                (int)(endLocation.getBlockZ() - getArenaMiddle() - 1)
        );
        spawn.getBlock().setType(mat);
    }

    public static boolean IsFloorTooHigh()
    {
        if(floor >= (endLocation.getBlockY() - startingLocation.getBlockY()) - Consts.CEILING_PROTECTION)
            return true;
        return false;
    }

    public static void AddAppeared(Material mat)
    {
        if(!AppearedBlocks.contains(mat))
            AppearedBlocks.add(mat);
    }

}
