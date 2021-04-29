package net.skatemc.pvp.listeners;

import net.skatemc.pvp.Main;
import net.skatemc.pvp.api.PvAPI;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ConnectionListener implements Listener {

    private ExecutorService pool = Executors.newCachedThreadPool();

    @EventHandler
    public void onLogin(PlayerLoginEvent event) {
        pool.execute(() -> {
            if (!Main.getInstance().getProvider().getData().registered(event.getPlayer().getUniqueId())) {
                Main.getInstance().getProvider().getData().register(event.getPlayer().getUniqueId());
            }
        });
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        pool.execute(() -> {
            PvAPI.get().getPlayerManager().injectPlayer(player);
        });
        Main.getInstance().getProvider().getGameBoard().add(player);
        if (Main.getInstance().getLocationUtils().fromName("spawn") != null) {
            player.teleport(Main.getInstance().getLocationUtils().fromName("spawn"));
        }
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        pool.execute(() -> {
            PvAPI.get().getPlayerManager().rejectPlayer(player);
        });
        Main.getInstance().getProvider().getGameBoard().destroy(player);
    }

}
