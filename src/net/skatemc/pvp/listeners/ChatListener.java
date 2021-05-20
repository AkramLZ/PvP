package net.skatemc.pvp.listeners;

import net.skatemc.pvp.api.PvAPI;
import net.skatemc.pvp.api.player.PVPlayer;
import net.skatemc.pvp.config.ConfigEnum;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class ChatListener implements Listener {

    @EventHandler
    public void onChat(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();
        PVPlayer pvPlayer = PvAPI.get().getPlayerManager().getPlayer(player);
        String chatFormat = PvAPI.get().getConfigManager().getConfig(ConfigEnum.MESSAGES)
                .getString("chat-format.normal");
    }

}
