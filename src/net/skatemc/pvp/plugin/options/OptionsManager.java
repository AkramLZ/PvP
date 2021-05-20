package net.skatemc.pvp.plugin.options;

import net.skatemc.pvp.Main;
import net.skatemc.pvp.multipliers.PointsMultiplier;
import net.skatemc.pvp.multipliers.SoulsMultiplier;

public class OptionsManager {

    private Main main;
    private PointsMultiplier pointsMultiplier;
    private SoulsMultiplier soulsMultiplier;
    private boolean removePointsOnSelfDeath;
    private boolean removeSoulsOnSelfDeath;
    private boolean removeSoulsOnKilledDeath;
    private int pointKillValue, pointDeathValue, soulsValue, soulsMaxValue;

    public OptionsManager(Main main) {
        this.main = main;
        this.pointsMultiplier = PointsMultiplier.valueOf(main.getConfig().getString("death.multipliers.points.mode"));
        this.soulsMultiplier = SoulsMultiplier.valueOf(main.getConfig().getString("death.multipliers.souls.mode"));
        this.removeSoulsOnSelfDeath = main.getConfig().getBoolean("death.multipliers.souls.remove-on-death.self");
        this.removeSoulsOnKilledDeath = main.getConfig().getBoolean("death.multipliers.souls.remove-on-death.killed");
        this.pointKillValue = main.getConfig().getInt("death.multipliers.points.kill.value");
        this.pointDeathValue = main.getConfig().getInt("death.multipliers.points.death.value");
        this.soulsValue = main.getConfig().getInt("death.multipliers.points.souls.value");
        this.soulsMaxValue = main.getConfig().getInt("death.multipliers.points.souls.max-value");
        this.removePointsOnSelfDeath = main.getConfig().getBoolean("death.multipliers.points.remove-on-self-death");
    }

    public PointsMultiplier getPointsMultiplier() { return this.pointsMultiplier; }

    public SoulsMultiplier getSoulsMultiplier() { return this.soulsMultiplier; }

    public boolean isRemoveSoulsOnSelfDeath() { return this.removeSoulsOnSelfDeath; }

    public boolean isRemoveSoulsOnKilledDeath() { return this.removeSoulsOnKilledDeath; }

    public int getPointKillValue() { return this.pointKillValue; }

    public int getPointDeathValue() { return this.pointDeathValue; }

    public int getSoulsValue() { return this.soulsValue; }

    public int getSoulsMaxValue() { return this.soulsMaxValue; }

    public boolean isRemovePointsOnSelfDeath() { return this.removePointsOnSelfDeath; }

}
