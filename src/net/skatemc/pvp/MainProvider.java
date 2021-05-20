package net.skatemc.pvp;

import net.skatemc.lib.SkateLib;
import net.skatemc.lib.ai.AICommand;
import net.skatemc.lib.ai.Loadable;
import net.skatemc.lib.managers.PluginManager;
import net.skatemc.pvp.api.PvAPI;
import net.skatemc.pvp.api.common.PrestigeBuilder;
import net.skatemc.pvp.commands.PrestigesCommand;
import net.skatemc.pvp.commands.ProgressCommand;
import net.skatemc.pvp.commands.SaveKitCommand;
import net.skatemc.pvp.commands.SetSpawnCommand;
import net.skatemc.pvp.common.GameBoard;
import net.skatemc.pvp.config.ConfigEnum;
import net.skatemc.pvp.data.Data;
import net.skatemc.pvp.data.DataType;
import net.skatemc.pvp.data.KitSave;
import net.skatemc.pvp.data.SQLite;
import net.skatemc.pvp.data.impl.SQLConnection;
import net.skatemc.pvp.listeners.ChatListener;
import net.skatemc.pvp.listeners.ConnectionListener;
import net.skatemc.pvp.listeners.DataListener;
import net.skatemc.pvp.listeners.DeathListener;
import net.skatemc.pvp.utils.ChatUtils;
import org.bukkit.event.Listener;

import java.lang.reflect.Array;
import java.util.Arrays;

public class MainProvider implements Loadable {

    protected Main main;
    private SQLConnection sql;
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
        DataType dataType = DataType.valueOf(main.getDataConfig().getString("data.type"));
        sql = new SQLite(main.getDataFolder().toString());
        sql.connect();
        this.data = new Data(sql);
        this.kitSave = new KitSave(sql);
        System.out.println("[CommandManager] Loading commands...");
        AICommand[] commands;
        manager.registerCommands(commands = new AICommand[]{new SetSpawnCommand(), new SaveKitCommand(), new PrestigesCommand(),
            new ProgressCommand()});
        Arrays.stream(commands).forEach(cmd -> {
            System.out.println("[CommandManager] Loaded command (/" + cmd.getName() + ") from class " + cmd.getClass().getSimpleName());
        });
        System.out.println("[ListenerManager] Loading listeners...");
        Listener[] listeners;
        manager.registerListeners(listeners = new Listener[]{new ConnectionListener(), new DataListener(), new DeathListener(),
            new ChatListener()});
        Arrays.stream(listeners).forEach(listener -> {
            System.out.println("[ListenerManager] Loaded listener from class " + listener.getClass().getSimpleName());
        });
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
