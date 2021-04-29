package net.skatemc.pvp.api.events;

import net.skatemc.pvp.api.player.Prestige;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class PlayerPrestigeChangeEvent extends Event {

    private static final HandlerList handlers = new HandlerList();
    private Player player;
    private PrestigeChange change;
    private Prestige oldPrestige;
    private Prestige newPrestige;

    public PlayerPrestigeChangeEvent(Player player, PrestigeChange change, Prestige oldPrestige, Prestige newPrestige) {
        this.player = player;
        this.change = change;
        this.newPrestige = newPrestige;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    public HandlerList getHandlers() {
        return handlers;
    }

    public Player getPlayer() {
        return player;
    }

    public PrestigeChange getPrestigeChange() {
        return change;
    }

    public Prestige getPrestige(PrestigeDate date) {
        return date == PrestigeDate.OLD ? oldPrestige : newPrestige;
    }

    public static enum PrestigeChange {
        UPGRADE, DOWNGRADE;
    }

    public static enum PrestigeDate {
        OLD, NEW;
    }

}
