package net.skatemc.pvp.listeners;

import net.skatemc.pvp.api.PvAPI;
import net.skatemc.pvp.config.ConfigEnum;
import net.skatemc.pvp.utils.ChatUtils;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public class DeathListener implements Listener {

    @EventHandler
    public void onDeath(PlayerDeathEvent event) {
        Player player = event.getEntity();
        if(player.getKiller() == null) {
            String deathBc = PvAPI.get().getConfigManager().getConfig(ConfigEnum.MESSAGES).getString("messages.death-broadcast.died")
                    .replace("%player%", player.getName())
                    .replace("%prefix%", PvAPI.get().getPlayerManager().getPlayer(player).getGroup()
                            .getCachedData().getMetaData().getPrefix().replace("&", "§"))
                    .replace("%suffix%", PvAPI.get().getPlayerManager().getPlayer(player).getGroup()
                            .getCachedData().getMetaData().getSuffix().replace("&", "§"))
                    .replace("%prestige%", PvAPI.get().getPlayerManager().getPlayer(player)
                            .getPrestige().getDisplayName().replace("&", "§"));
            if(deathBc.equals("")) {
                event.setDeathMessage(null);
            } else {
                event.setDeathMessage(ChatUtils.colored(deathBc));

            }
        }
    }

}
