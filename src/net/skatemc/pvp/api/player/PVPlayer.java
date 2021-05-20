package net.skatemc.pvp.api.player;

import com.google.common.base.Strings;
import net.luckperms.api.LuckPermsProvider;
import net.luckperms.api.model.group.Group;
import net.skatemc.pvp.Main;
import net.skatemc.pvp.api.PvAPI;
import net.skatemc.pvp.config.ConfigEnum;
import net.skatemc.pvp.data.save.PVSlot;
import net.skatemc.pvp.kits.Kit;
import net.skatemc.pvp.utils.ChatUtils;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.text.DecimalFormat;
import java.util.Iterator;

public abstract class PVPlayer {

    private long kills = -404L, deaths = -404L, points = -404L, souls = -404L;
    private int swordSlot = 0, bowSlot = 1, rodSlot = 2, arrowSlot = 8;

    public abstract Player getPlayer();

    public void injectData() {
        kills = Main.getInstance().getProvider().getData().getKills(getPlayer().getUniqueId());
        deaths = Main.getInstance().getProvider().getData().getDeaths(getPlayer().getUniqueId());
        points = Main.getInstance().getProvider().getData().getPoints(getPlayer().getUniqueId());
        souls = Main.getInstance().getProvider().getData().getSouls(getPlayer().getUniqueId());
        swordSlot = Main.getInstance().getProvider().getKitSaveData().getSword(getPlayer().getUniqueId());
        rodSlot = Main.getInstance().getProvider().getKitSaveData().getRod(getPlayer().getUniqueId());
        bowSlot = Main.getInstance().getProvider().getKitSaveData().getBow(getPlayer().getUniqueId());
        arrowSlot = Main.getInstance().getProvider().getKitSaveData().getArrow(getPlayer().getUniqueId());
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

    public Group getGroup() {
        return LuckPermsProvider.get().getGroupManager().getGroup(LuckPermsProvider.get().getUserManager()
                .getUser(getPlayer().getUniqueId()).getCachedData().getMetaData().getPrimaryGroup());
    }

    public boolean isSpectating() { return false; }

    public void injectKit() {
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

    public String getPrefix() {
        String prefix = getGroup().getCachedData().getMetaData().getPrefix();
        if(prefix == null) {
            return "";
        }
        return ChatUtils.colored(prefix);
    }

    public String getSuffix() {
        String suffix = getGroup().getCachedData().getMetaData().getSuffix();
        if(suffix == null) {
            return "";
        }
        return ChatUtils.colored(suffix);
    }

    public Prestige getPrestige() {
        int points$ = points > Integer.MAX_VALUE ? Integer.MAX_VALUE : (int)points;
        return PvAPI.get().getPrestigeManager().getPrestige(points$);
    }

    public Prestige getNextPrestige() {
        Iterator<Prestige> iterator = PvAPI.get().getPrestigeManager().getPrestiges().iterator();
        int i = 0;
        while(iterator.hasNext()) {
            i++;
            Prestige prestige = iterator.next();
            if(prestige == getPrestige()) {
                if(i == PvAPI.get().getPrestigeManager().getPrestiges().size()) {
                    return prestige;
                }
                return iterator.next();
            }
        }
        return null;
    }

    public void saveData() {
        Main.getInstance().getProvider().getData().setKills(getPlayer().getUniqueId(), kills);
        Main.getInstance().getProvider().getData().setDeaths(getPlayer().getUniqueId(), deaths);
        Main.getInstance().getProvider().getData().setPoints(getPlayer().getUniqueId(), points);
        Main.getInstance().getProvider().getData().setSouls(getPlayer().getUniqueId(), souls);
        if (Main.getInstance().getProvider().getKitSaveData().saved(getPlayer().getUniqueId())) {
            Main.getInstance().getProvider().getKitSaveData().unsave(getPlayer().getUniqueId());
        }
        Main.getInstance().getProvider().getKitSaveData().save(getPlayer());
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

    public void getKills(long kills) { this.kills = kills; }

    public void setDeaths(long deaths) {  this.deaths = deaths; }

    public void setPoints(long points) { this.points = points; }

    public void setSouls(long souls) { this.souls = souls; }

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

    public void saveKit() {
        Inventory inv = getPlayer().getInventory();
        int sword = 0, rod = 0, bow = 0, arrows = 0;
        for (int slot = 0; slot < 9; slot++) {
            if (inv.getItem(slot) != null) {
                if (inv.getItem(slot).getType() == Material.STONE_SWORD) {
                    sword = slot;
                }
                if (inv.getItem(slot).getType() == Material.FISHING_ROD) {
                    rod = slot;
                }
                if (inv.getItem(slot).getType() == Material.BOW) {
                    bow = slot;
                }
                if (inv.getItem(slot).getType() == Material.ARROW) {
                    arrows = slot;
                }
            }
        }
        this.swordSlot = sword;
        this.rodSlot = rod;
        this.arrowSlot = arrows;
        this.bowSlot = bow;
    }

    public double getKDR() {
        DecimalFormat decimalFormat = new DecimalFormat("###.##");
        if (getDeaths() <= 0) {
            return 1.00D;
        }
        return Double.parseDouble(decimalFormat.format(kills / deaths));
    }

    public int getProgress() {
        int points = getPoints() > Integer.MAX_VALUE ? Integer.MAX_VALUE : (int)getPoints();
        return (getNextPrestige().getRequiredPoints() - getPrestige().getRequiredPoints()) - points;
    }

    public String getProgressBar() {
        String reachedColor = ChatUtils.colored(PvAPI.get().getConfigManager()
                .getConfig(ConfigEnum.MESSAGES).getString("progress.reached-color"));
        String leftColor = ChatUtils.colored(PvAPI.get().getConfigManager()
                .getConfig(ConfigEnum.MESSAGES).getString("progress.left-color"));
        String symbol = ChatUtils.colored(PvAPI.get().getConfigManager()
                .getConfig(ConfigEnum.MESSAGES).getString("progress.symbol"));
        int split = PvAPI.get().getConfigManager().getConfig(ConfigEnum.MESSAGES).getInt("progress.split");
        int progress = getProgress();
        int distance = getNextPrestige().getRequiredPoints() - getPrestige().getRequiredPoints();
        float percent = (float) progress / distance;
        int progressBars = (int) (split * percent);
        return Strings.repeat("" + reachedColor + symbol, progressBars)
                + Strings.repeat("" + leftColor + symbol, split - progressBars);
    }

}
