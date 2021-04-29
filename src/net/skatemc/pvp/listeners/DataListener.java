package net.skatemc.pvp.listeners;

import net.skatemc.pvp.api.events.PlayerPrestigeChangeEvent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class DataListener implements Listener {

    @EventHandler
    public void onPrestigeChange(PlayerPrestigeChangeEvent event) {
        Player player = event.getPlayer();
        Bukkit.broadcastMessage("");
    }

}