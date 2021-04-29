package net.skatemc.pvp.api.manager;

import net.skatemc.pvp.api.player.Prestige;

import java.util.ArrayList;
import java.util.List;

public class PrestigeManager {

    private List<Prestige> prestiges = new ArrayList<Prestige>();

    public PrestigeManager() {
        this.prestiges = new ArrayList<>();
    }

    public Prestige getPrestige(String name) {
        return prestiges.stream().filter(prestige -> prestige.getName().equals(name)).findFirst().orElse(null);
    }

    public Prestige getPrestige(int points) {
        return prestiges.stream().filter(prestige -> {
            if (points >= prestige.getRequiredPoints()) {
                return true;
            } else {
                return false;
            }
        }).findFirst().orElse(null);
    }

    public List<Prestige> getPrestiges() {
        return prestiges;
    }

    public void registerPrestige(Prestige prestige) {
        prestiges.add(prestige);
    }

}
