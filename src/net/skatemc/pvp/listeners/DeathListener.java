package net.skatemc.pvp.listeners;

import net.skatemc.pvp.Main;
import net.skatemc.pvp.api.PvAPI;
import net.skatemc.pvp.api.events.PlayerPrestigeChangeEvent;
import net.skatemc.pvp.api.player.PVPlayer;
import net.skatemc.pvp.api.player.Prestige;
import net.skatemc.pvp.config.ConfigEnum;
import net.skatemc.pvp.multipliers.PointsMultiplier;
import net.skatemc.pvp.multipliers.SoulsMultiplier;
import net.skatemc.pvp.utils.ChatUtils;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerRespawnEvent;

import java.util.Random;

public class DeathListener implements Listener {

    @EventHandler
    public void onDeath(PlayerDeathEvent event) {
        Player player = event.getEntity();
        PVPlayer pvPlayer = PvAPI.get().getPlayerManager().getPlayer(player);
        boolean removePointsOnDeath = Main.getInstance().getOptionsManager().isRemovePointsOnSelfDeath();
        boolean removeSoulsOnSelfDeath = Main.getInstance().getOptionsManager().isRemoveSoulsOnSelfDeath();
        boolean removeSoulsOnKilledDeath = Main.getInstance().getOptionsManager().isRemoveSoulsOnKilledDeath();
        SoulsMultiplier soulsMultiplier = Main.getInstance().getOptionsManager().getSoulsMultiplier();
        PointsMultiplier pointsMultiplier = Main.getInstance().getOptionsManager().getPointsMultiplier();
        int pointKillValue = Main.getInstance().getOptionsManager().getPointKillValue();
        int pointDeathValue = Main.getInstance().getOptionsManager().getPointDeathValue();
        int soulsValue = Main.getInstance().getOptionsManager().getSoulsValue();
        int soulsMaxValue = Main.getInstance().getOptionsManager().getSoulsMaxValue();
        if(player.getKiller() == null) {
            String deathBc = PvAPI.get().getConfigManager().getConfig(ConfigEnum.MESSAGES).getString("messages.death-broadcast.died")
                    .replace("%player%", player.getName())
                    .replace("%prefix%", pvPlayer.getPrefix())
                    .replace("%suffix%", pvPlayer.getSuffix())
                    .replace("%prestige%", PvAPI.get().getPlayerManager().getPlayer(player)
                            .getPrestige().getDisplayName());
            event.setDeathMessage(null);
            if(!deathBc.equals("")) {
                Bukkit.broadcastMessage(ChatUtils.colored(deathBc));
            }
            String deathMsg = PvAPI.get().getConfigManager().getConfig(ConfigEnum.MESSAGES).getString("messages.death-message.died")
                    .replace("%player%", player.getName())
                    .replace("%prefix%", pvPlayer.getPrefix())
                    .replace("%suffix%", pvPlayer.getSuffix())
                    .replace("%prestige%", PvAPI.get().getPlayerManager().getPlayer(player)
                            .getPrestige().getDisplayName());
            if(!deathMsg.equals("")) {
                player.sendMessage(ChatUtils.colored(deathMsg));
            }
            Prestige oldPrestige = pvPlayer.getPrestige();
            if(removePointsOnDeath) {
                if(pointsMultiplier == PointsMultiplier.INTEGER) {
                    pvPlayer.setPoints(pvPlayer.getPoints() - pointDeathValue);
                } else {
                    pvPlayer.setPoints(Math.round(pvPlayer.getPoints()/pointDeathValue));
                }
            }
            Prestige newPrestige = pvPlayer.getPrestige();
            if(!newPrestige.getName().equals(oldPrestige.getName())) {
                Bukkit.getPluginManager().callEvent(new PlayerPrestigeChangeEvent(player, PlayerPrestigeChangeEvent.PrestigeChange.DOWNGRADE,
                        oldPrestige, newPrestige));
            }
            pvPlayer.setDeaths(pvPlayer.getDeaths()-1L);
            if(removeSoulsOnSelfDeath) {
                if(soulsMultiplier == SoulsMultiplier.INTEGER) {
                    pvPlayer.setSouls(pvPlayer.getSouls() - soulsValue);
                } else {
                    int amount = new Random().nextInt(soulsMaxValue);
                    while(amount < soulsValue) {
                        amount = new Random().nextInt(soulsMaxValue);
                    }
                    pvPlayer.setSouls(pvPlayer.getSouls() - amount);
                }
            }
        } else {
            PVPlayer pvKiller = PvAPI.get().getPlayerManager().getPlayer(player.getKiller());
            String deathBc = PvAPI.get().getConfigManager().getConfig(ConfigEnum.MESSAGES).getString("messages.death-broadcast.killed")
                    .replace("%player%", player.getName())
                    .replace("%player_prefix%", pvPlayer.getPrefix())
                    .replace("%player_suffix%", pvPlayer.getSuffix())
                    .replace("%killer_prefix%", pvKiller.getPrefix())
                    .replace("%killer_suffix%", pvKiller.getSuffix())
                    .replace("%prestige%", PvAPI.get().getPlayerManager().getPlayer(player)
                            .getPrestige().getDisplayName())
                    .replace("%killer%", player.getKiller().getName());
            event.setDeathMessage(null);
            if(!deathBc.equals("")) {
                Bukkit.broadcastMessage(ChatUtils.colored(deathBc));
            }
            if(pointsMultiplier == PointsMultiplier.INTEGER) {
                pvPlayer.setPoints(pvPlayer.getPoints() - pointDeathValue);
                pvKiller.setPoints(pvKiller.getPoints() + pointKillValue);
            } else {
                pvPlayer.setPoints(Math.round(pvPlayer.getPoints()/pointDeathValue));
            }
        }
        Bukkit.getPluginManager().callEvent(new PlayerRespawnEvent(player, player.getLocation(), false));
    }

    @EventHandler
    public void onDamage(EntityDamageEvent event) {
        if(event.getCause() == EntityDamageEvent.DamageCause.FALL) {
            event.setCancelled(true);
        }
        if(event.getEntity() instanceof Player) {
            Player player = (Player) event.getEntity();
            if(event.getFinalDamage() >= player.getHealth()) {
                if(!event.isCancelled()) {
                    event.setCancelled(true);
                    player.setHealth(20);
                    Bukkit.getPluginManager().callEvent(new PlayerDeathEvent(player, null, 0, null));
                }
            }
        }
    }

    @EventHandler
    public void onRespawn(PlayerRespawnEvent event) {
        Player player = event.getPlayer();
        player.getInventory().clear();
        player.getInventory().setArmorContents(null);
        player.setGameMode(GameMode.SPECTATOR);
        Bukkit.getScheduler().runTaskLater(Main.getInstance(), () -> {
            player.setGameMode(GameMode.SURVIVAL);
            Location spawn = Main.getInstance().getLocationUtils().fromName("spawn");
            if(spawn == null) {
                player.teleport(player.getWorld().getSpawnLocation());
            } else {
                player.teleport(spawn);
            }
            PvAPI.get().getPlayerManager().getPlayer(player).injectKit();
        }, 60L);
    }

}
