package net.skatemc.pvp.config;

import net.skatemc.pvp.Main;
import org.bukkit.configuration.file.FileConfiguration;

public class ConfigManager {

    public FileConfiguration getConfig(ConfigEnum configEnum) {
        if(configEnum == ConfigEnum.CONFIG) {
            return Main.getInstance().getConfig();
        }
        if(configEnum == ConfigEnum.MESSAGES) {
            return Main.getInstance().getMessagesConfig();
        }
        return null;
    }

}
