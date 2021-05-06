package net.skatemc.pvp;

import net.skatemc.lib.SkateLib;
import net.skatemc.lib.ai.AICommand;
import net.skatemc.lib.ai.Loadable;
import net.skatemc.lib.managers.PluginManager;
import net.skatemc.pvp.api.PvAPI;
import net.skatemc.pvp.api.common.PrestigeBuilder;
import net.skatemc.pvp.commands.PrestigesCommand;
import net.skatemc.pvp.commands.SaveKitCommand;
import net.skatemc.pvp.commands.SetSpawnCommand;
import net.skatemc.pvp.common.GameBoard;
import net.skatemc.pvp.config.ConfigEnum;
import net.skatemc.pvp.data.Data;
import net.skatemc.pvp.data.KitSave;
import net.skatemc.pvp.data.SQLite;
import net.skatemc.pvp.listeners.ConnectionListener;
import net.skatemc.pvp.listeners.DataListener;
import net.skatemc.pvp.listeners.DeathListener;
import net.skatemc.pvp.utils.ChatUtils;
import org.bukkit.event.Listener;

public class MainProvider implements Loadable {

    protected Main main;
    private SQLite sql;
    private Data data;
    private KitSave kitSave;
    private GameBoard gameBoard;

    public MainProvider(Main main) {
        this.main = main;
        this.gameBoard = new GameBoard();
    }

    @Override
    public void onEnable() {
        PluginManager manager = SkateLib.getAPI().getPluginManager(main);
        if (!main.getDataFolder().exists())
            main.getDataFolder().mkdir();
        System.out.println("[DataManager] Loading data...");
        sql = new SQLite();
        sql.connect();
        this.data = new Data(sql);
        this.kitSave = new KitSave(sql);
        System.out.println("[CommandManager] Loading commands...");
        manager.registerListeners(new Listener[]{new ConnectionListener(), new DataListener(), new DeathListener()});
        System.out.println("[ListenerManager] Loading listeners...");
        manager.registerCommands(new AICommand[]{new SetSpawnCommand(), new SaveKitCommand(), new PrestigesCommand()});
        System.out.println("[PrestigeManager] Loading prestiges...");
        for(String name : PvAPI.get().getConfigManager().getConfig(ConfigEnum.PRESTIGES)
                .getConfigurationSection("prestiges").getKeys(false)) {
            String displayName = ChatUtils.colored(PvAPI.get().getConfigManager().getConfig(ConfigEnum.PRESTIGES)
                    .getString("prestiges." + name + ".display-name"));
            int requiredPoints = PvAPI.get().getConfigManager().getConfig(ConfigEnum.PRESTIGES)
                    .getInt("prestiges." + name + ".required-points");
            PvAPI.get().getPrestigeManager().registerPrestige(new PrestigeBuilder(name)
                .setDisplayName(displayName)
                .setRequiredPoints(requiredPoints).build());
            System.out.println("[PrestigeManager] Loaded prestige (" + name + ") with " + requiredPoints + " required points.");
        }
    }

    public Data getData() {
        return data;
    }

    public KitSave getKitSaveData() {
        return kitSave;
    }

    public GameBoard getGameBoard() {
        return gameBoard;
    }

}
