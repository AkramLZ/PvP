package net.skatemc.pvp.api.player;

import net.luckperms.api.model.group.Group;
import net.skatemc.pvp.Main;
import net.skatemc.pvp.api.PvAPI;
import net.skatemc.pvp.data.save.PVSlot;
import net.skatemc.pvp.kits.Kit;
import org.bukkit.entity.Player;

import java.text.DecimalFormat;

public abstract class PVPlayer {

    private long kills = -404L, deaths = -404L, points = -404L, souls = -404L;
    private int swordSlot = 0, bowSlot = 1, rodSlot = 2, arrowSlot = 8;

    public abstract Player getPlayer();

    public abstract Group getGroup();

    public void injectData() {
        kills = Main.getInstance().getProvider().getData().getKills(getPlayer().getUniqueId());
        deaths = Main.getInstance().getProvider().getData().getDeaths(getPlayer().getUniqueId());
        points = Main.getInstance().getProvider().getData().getPoints(getPlayer().getUniqueId());
        souls = Main.getInstance().getProvider().getData().getSouls(getPlayer().getUniqueId());
        if (getGroup().getName().equalsIgnoreCase("default")) {
            getPlayer().getInventory().clear();
            Kit.MEMBER.give(getPlayer());
        } else if (getGroup().getName().equalsIgnoreCase("youtuber")) {
            getPlayer().getInventory().clear();
            Kit.YOUTUBER.give(getPlayer());
        } else if (getGroup().getName().equalsIgnoreCase("gold")) {
            getPlayer().getInventory().clear();
            Kit.GOLD.give(getPlayer());
        } else if (getGroup().getName().equalsIgnoreCase("diamond")) {
            getPlayer().getInventory().clear();
            Kit.DIAMOND.give(getPlayer());
        } else if (getGroup().getName().equalsIgnoreCase("emerald")) {
            getPlayer().getInventory().clear();
            Kit.EMERALD.give(getPlayer());
        } else {
            getPlayer().getInventory().clear();
            Kit.MEMBER.give(getPlayer());
        }
    }

    public Prestige getPrestige() {
        return PvAPI.get().getPrestigeManager().getPrestige(points);
    }

    public void saveData() {
        Main.getInstance().getProvider().getData().setKills(getPlayer().getUniqueId(), kills);
        Main.getInstance().getProvider().getData().setDeaths(getPlayer().getUniqueId(), deaths);
        Main.getInstance().getProvider().getData().setPoints(getPlayer().getUniqueId(), points);
        Main.getInstance().getProvider().getData().setSouls(getPlayer().getUniqueId(), souls);
        if (swordSlot != 0 || bowSlot != 1 || rodSlot != 2 || arrowSlot != 8) {
            if (!Main.getInstance().getProvider().getKitSaveData().saved(getPlayer().getUniqueId())) {
                Main.getInstance().getProvider().getKitSaveData().save(getPlayer());
            } else {
                Main.getInstance().getProvider().getKitSaveData().unsave(getPlayer().getUniqueId());
                Main.getInstance().getProvider().getKitSaveData().save(getPlayer());
            }
        }
    }

    public long getKills() {
        return kills;
    }

    public long getDeaths() {
        return deaths;
    }

    public long getPoints() {
        return points;
    }

    public long getSouls() {
        return souls;
    }

    public int getSlot(PVSlot slot) {
        if (slot == PVSlot.SWORD) {
            return swordSlot;
        }
        if (slot == PVSlot.ROD) {
            return rodSlot;
        }
        if (slot == PVSlot.BOW) {
            return bowSlot;
        }
        if (slot == PVSlot.ARROW) {
            return arrowSlot;
        }
        return -1;
    }

    public double getKDR() {
        DecimalFormat decimalFormat = new DecimalFormat("##.##");
        if (getDeaths() <= 0) {
            return 1.00D;
        }
        return Double.valueOf(decimalFormat.format(kills / deaths)).doubleValue();
    }

}
