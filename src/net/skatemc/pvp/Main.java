package net.skatemc.pvp;

import net.skatemc.pvp.common.LocationUtils;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.logging.Level;

public class Main extends JavaPlugin {

    private static Main instance;
    private MainProvider provider;
    private LocationUtils locationUtils;
    private FileConfiguration messagesConfig;

    public static Main getInstance() {
        return instance;
    }

    @Override
    public void onEnable() {
        instance = this;
        if(!getDataFolder().exists()) getDataFolder().mkdir();
        File messagesFile = new File(getDataFolder() + "/messages.yml");
        try {
            Files.copy(getResource("messages.yml"), messagesFile.toPath());
        } catch (IOException e) {
            getLogger().log(Level.SEVERE, "Failed to load messages.yml, " + e.getMessage());
        }
        this.messagesConfig = YamlConfiguration.loadConfiguration(messagesFile);
//        saveDefaultConfig();
        this.provider = new MainProvider(this);
        this.locationUtils = new LocationUtils(this);
        this.provider.onEnable();
    }

    public MainProvider getProvider() {
        return provider;
    }

    public LocationUtils getLocationUtils() {
        return locationUtils;
    }

    public FileConfiguration getMessagesConfig() {
        return messagesConfig;
    }

}