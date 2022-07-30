package me.camm.productions.bedwars.Util.Locations;

import org.bukkit.World;
import org.bukkit.plugin.Plugin;

public interface IRegistratable {

    void register(World world, String type, Plugin p);

    void unregister(World world, String type, Plugin p);

     void register(World world, String type, int blocks, Plugin plugin);
}
