package net.skatemc.pvp;

import net.skatemc.pvp.common.LocationUtils;
import net.skatemc.pvp.utils.ChatUtils;
import net.skatemc.pvp.utils.License;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class Main extends JavaPlugin {

    private static Main instance;
    private MainProvider provider;
    private LocationUtils locationUtils;
    private FileConfiguration messagesConfig;
    private FileConfiguration prestigesConfig;
    private FileConfiguration licenseConfig;
    private License license;

    public static Main getInstance() {
        return instance;
    }

    @Override
    public void onEnable() {
        long startTime = System.currentTimeMillis();
        instance = this;
        if(!getDataFolder().exists()) getDataFolder().mkdir();
        getLogger().info("__________       __________ ");
        getLogger().info("\\______   \\___  _\\______   \\");
        getLogger().info(" |     ___/\\  \\/ /|     ___/");
        getLogger().info(" |    |     \\   / |    |    ");
        getLogger().info(" |____|      \\_/  |____|    ");
        getLogger().info("                            ");
        getLogger().info("Advanced PvP System by iiProCraft");
        System.out.println("[FileManager] Loading plugin files...");
        File messagesFile = new File(getDataFolder() + "/messages.yml");
        File prestigesFile = new File(getDataFolder() + "/prestiges.yml");
        File licenseFile = new File(getDataFolder() + "/license.yml");
        try {
            if(!messagesFile.exists())
                Files.copy(getResource("messages.yml"), messagesFile.toPath());
            if(!prestigesFile.exists())
                Files.copy(getResource("prestiges.yml"), prestigesFile.toPath());
            if(!licenseFile.exists())
                Files.copy(getResource("license.yml"), licenseFile.toPath());
        } catch (IOException e) {
            System.out.println("[FileManager] Failed to load files, " + e.getMessage());
            e.printStackTrace();
        }
        this.messagesConfig = YamlConfiguration.loadConfiguration(messagesFile);
        this.prestigesConfig = YamlConfiguration.loadConfiguration(prestigesFile);
        this.licenseConfig = YamlConfiguration.loadConfiguration(licenseFile);
        saveDefaultConfig();
        System.out.println("[License] Checking plugin license...");
        String secretKey = licenseConfig.getString("loader.secret-key");
        this.license = new License(secretKey, "http://173.212.236.143/license/verify.php", this);
        this.license.setConsoleLog(License.LogType.NORMAL);
        this.license.setSecurityKey("joGYB6ulNqtOJaufmsfPAWtMGTmLYk4XSZTo");
        if(!this.license.register()) {
            System.out.println("[License] Failed to load license, disabling the plugin...");
            Bukkit.getPluginManager().disablePlugin(this);
            return;
        }
        System.out.println("[License] Successfully validated this copy of the plugin.");
        this.provider = new MainProvider(this);
        this.locationUtils = new LocationUtils(this);
        this.provider.onEnable();
        Bukkit.getConsoleSender().sendMessage(ChatUtils.colored("&e[PvP] Successfully loaded plugin in " + (System.currentTimeMillis() - startTime) + "ms"));
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

    public FileConfiguration getPrestigesConfig() {
        return prestigesConfig;
    }

}