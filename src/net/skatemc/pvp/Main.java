package net.skatemc.pvp;

import net.skatemc.pvp.common.LocationUtils;
import net.skatemc.pvp.plugin.options.OptionsManager;
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
    private FileConfiguration dataConfig;
    private License license;
    private OptionsManager optionsManager;

    public static Main getInstance() {
        return instance;
    }

    @Override
    public void onEnable() {
        long startTime = System.currentTimeMillis();
        instance = this;
        if(!getDataFolder().exists()) getDataFolder().mkdir();
        Bukkit.getConsoleSender().sendMessage("§6__________       §6__________ ");
        Bukkit.getConsoleSender().sendMessage("§6\\______   \\§9___  _§6\\______   \\");
        Bukkit.getConsoleSender().sendMessage("§6 |     ___/§9\\  \\/ /§6|     ___/     §3Running PvP by iiProCraft");
        Bukkit.getConsoleSender().sendMessage("§6 |    |     §9\\   / §6|    |         §3Version: §b" + getDescription().getVersion());
        Bukkit.getConsoleSender().sendMessage("§6 |____|      §9\\_/  §6|____|    ");
        Bukkit.getConsoleSender().sendMessage("                            ");
        System.out.println("[FileManager] Loading plugin files...");
        File messagesFile = new File(getDataFolder() + "/messages.yml");
        File prestigesFile = new File(getDataFolder() + "/prestiges.yml");
        File licenseFile = new File(getDataFolder() + "/license.yml");
        File dataFile = new File(getDataFolder() + "/data.yml");
        try {
            if(!messagesFile.exists())
                Files.copy(getResource("messages.yml"), messagesFile.toPath());
            if(!prestigesFile.exists())
                Files.copy(getResource("prestiges.yml"), prestigesFile.toPath());
            if(!licenseFile.exists())
                Files.copy(getResource("license.yml"), licenseFile.toPath());
            if(!dataFile.exists()) {
                Files.copy(getResource("data.yml"), dataFile.toPath());
            }
        } catch (IOException e) {
            System.out.println("[FileManager] Failed to load files, " + e.getMessage());
            e.printStackTrace();
        }
        this.messagesConfig = YamlConfiguration.loadConfiguration(messagesFile);
        this.prestigesConfig = YamlConfiguration.loadConfiguration(prestigesFile);
        this.licenseConfig = YamlConfiguration.loadConfiguration(licenseFile);
        this.dataConfig = YamlConfiguration.loadConfiguration(dataFile);
        saveDefaultConfig();
        System.out.println("[License] Checking plugin license...");
        String secretKey = licenseConfig.getString("loader.secret-key");
        this.license = new License(secretKey, "http://173.212.236.143/license/verify.php", this);
        this.license.setConsoleLog(License.LogType.NORMAL);
        this.license.setSecurityKey("joGYB6ulNqtOJaufmsfPAWtMGTmLYk4XSZTo");
        if(!this.license.register()) {
            System.out.println("[License] If this is an issue please contact with plugin developer.");
            Bukkit.getPluginManager().disablePlugin(this);
            return;
        }
        if(!this.license.isValidSimple()) {
            Bukkit.getConsoleSender().sendMessage("§e[License] Successfully validated this copy of the plugin.");
            this.provider = new MainProvider(this);
            this.locationUtils = new LocationUtils(this);
            System.out.println("[OptionsManager] Loading Options Manager");
            this.optionsManager = new OptionsManager(this);
            this.provider.onEnable();
            Bukkit.getConsoleSender().sendMessage(ChatUtils.colored("&e[PvP] Successfully loaded plugin in " + (System.currentTimeMillis() - startTime) + "ms"));
        }
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

    public OptionsManager getOptionsManager() { return optionsManager; }

    public FileConfiguration getDataConfig() { return dataConfig; }

    public License getLicense() { return license; }

}