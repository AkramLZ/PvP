package net.skatemc.pvp.listeners;

import net.skatemc.pvp.Main;
import net.skatemc.pvp.api.PvAPI;
import net.skatemc.pvp.config.ConfigEnum;
import net.skatemc.pvp.utils.ChatUtils;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@SuppressWarnings("ALL")
public class ConnectionListener implements Listener {

    private final ExecutorService pool = Executors.newCachedThreadPool();

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
        FileConfiguration cfg = PvAPI.get().getConfigManager().getConfig(ConfigEnum.MESSAGES);
        if(cfg.getString("messages.join").equalsIgnoreCase("")) {
            event.setJoinMessage(null);
        } else {
            String joinMsg = cfg.getString("messages.join").replace("%player%", event.getPlayer().getName())
                    .replace("%prefix%", PvAPI.get().getPlayerManager().getPlayer(player).getGroup()
                    .getCachedData().getMetaData().getPrefix().replace("&", "§"))
                    .replace("%suffix%", PvAPI.get().getPlayerManager().getPlayer(player).getGroup()
                            .getCachedData().getMetaData().getSuffix().replace("&", "§"))
                    .replace("%prestige%", PvAPI.get().getPlayerManager().getPlayer(player)
                    .getPrestige().getDisplayName().replace("&", "§"));
            event.setJoinMessage(ChatUtils.colored(joinMsg));
        }
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
        FileConfiguration cfg = PvAPI.get().getConfigManager().getConfig(ConfigEnum.MESSAGES);
        if(cfg.getString("messages.quit").equalsIgnoreCase("")) {
            event.setQuitMessage(null);
        } else {
            String quitMsg = cfg.getString("messages.quit").replace("%player%", event.getPlayer().getName())
                    .replace("%prefix%", PvAPI.get().getPlayerManager().getPlayer(player).getGroup()
                            .getCachedData().getMetaData().getPrefix().replace("&", "§"))
                    .replace("%suffix%", PvAPI.get().getPlayerManager().getPlayer(player).getGroup()
                            .getCachedData().getMetaData().getSuffix().replace("&", "§"))
                    .replace("%prestige%", PvAPI.get().getPlayerManager().getPlayer(player)
                            .getPrestige().getDisplayName().replace("&", "§"));
            event.setQuitMessage(ChatUtils.colored(quitMsg));
        }
        pool.execute(() -> {
            PvAPI.get().getPlayerManager().rejectPlayer(player);
        });
        Main.getInstance().getProvider().getGameBoard().destroy(player);
    }

}
