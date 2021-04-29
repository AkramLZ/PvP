package net.skatemc.pvp.common;

import net.skatemc.pvp.Main;
import org.bukkit.Bukkit;
import org.bukkit.Location;

public class LocationUtils {

    protected Main main;

    public LocationUtils(final Main main) {
        this.main = main;
    }

    public Location fromName(final String name) {
        if (!main.getConfig().contains("locations." + name.toLowerCase()))
            return null;
        return new Location(Bukkit.getWorld(main.getConfig().getString("locations." + name + ".world")),
                main.getConfig().getInt("locations." + name + ".x") + 0.5,
                main.getConfig().getDouble("locations." + name + ".y"),
                main.getConfig().getInt("locations." + name + ".z") + 0.5,
                (float) main.getConfig().getInt("locations." + name + ".yaw"),
                (float) main.getConfig().getInt("locations." + name + ".pitch"));
    }

    public void saveLocation(String name, Location location) {
        main.getConfig().set("locations." + name + ".world", location.getWorld().getName());
        main.getConfig().set("locations." + name + "x", location.getBlockX());
        main.getConfig().set("locations." + name + "y", location.getY());
        main.getConfig().set("locations." + name + "z", location.getBlockZ());
        main.getConfig().set("locations." + name + "yaw", location.getYaw());
        main.getConfig().set("locations." + name + "pitch", location.getPitch());
        main.saveConfig();
    }

}
