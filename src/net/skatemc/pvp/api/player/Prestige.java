package net.skatemc.pvp.api.player;

import org.bukkit.entity.Player;

public abstract class Prestige {

    public abstract String getName();

    public abstract String getDisplayName();

    public abstract int getRequiredPoints();

    public boolean isEligible(Player player) {
        return false;
    }

}