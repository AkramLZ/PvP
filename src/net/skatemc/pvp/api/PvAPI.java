package net.skatemc.pvp.api;

import net.skatemc.pvp.api.manager.PlayerManager;
import net.skatemc.pvp.api.manager.PrestigeManager;
import net.skatemc.pvp.config.ConfigManager;

public class PvAPI {

    private static PvAPI api;

    static {
        api = new PvAPI();
    }

    private PlayerManager playerManager;
    private PrestigeManager prestigeManager;
    private ConfigManager configManager;

    public PvAPI() {
        this.playerManager = new PlayerManager();
        this.prestigeManager = new PrestigeManager();
        this.configManager = new ConfigManager();
    }

    public static PvAPI get() {
        return api;
    }

    public PlayerManager getPlayerManager() {
        return this.playerManager;
    }

    public PrestigeManager getPrestigeManager() {
        return this.prestigeManager;
    }

}