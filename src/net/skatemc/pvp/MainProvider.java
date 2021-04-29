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
import net.skatemc.pvp.data.Data;
import net.skatemc.pvp.data.KitSave;
import net.skatemc.pvp.data.SQLite;
import net.skatemc.pvp.listeners.ConnectionListener;
import net.skatemc.pvp.listeners.DataListener;
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
        sql = new SQLite();
        sql.connect();
        this.data = new Data(sql);
        this.kitSave = new KitSave(sql);
        manager.registerListeners(new Listener[]{new ConnectionListener(), new DataListener()});
        manager.registerCommands(new AICommand[]{new SetSpawnCommand(), new SaveKitCommand(), new PrestigesCommand()});
        PvAPI.get().getPrestigeManager().registerPrestige(new PrestigeBuilder("Unranked")
                .setDisplayName("§eUNRANKED").setRequiredPoints(0).build());
        PvAPI.get().getPrestigeManager().registerPrestige(new PrestigeBuilder("Bronze I")
                .setDisplayName("§2Bronze §5I").setRequiredPoints(1000).build());
        PvAPI.get().getPrestigeManager().registerPrestige(new PrestigeBuilder("Bronze II")
                .setDisplayName("§2Bronze §6II").setRequiredPoints(5000).build());
        PvAPI.get().getPrestigeManager().registerPrestige(new PrestigeBuilder("Bronze III")
                .setDisplayName("§2Bronze §8III").setRequiredPoints(10000).build());
        PvAPI.get().getPrestigeManager().registerPrestige(new PrestigeBuilder("Bronze IV")
                .setDisplayName("§2Bronze §cIV").setRequiredPoints(15000).build());
        PvAPI.get().getPrestigeManager().registerPrestige(new PrestigeBuilder("Silver I")
                .setDisplayName("§7Silver §5I").setRequiredPoints(20000).build());
        PvAPI.get().getPrestigeManager().registerPrestige(new PrestigeBuilder("Silver II")
                .setDisplayName("§7Silver §6II").setRequiredPoints(25000).build());
        PvAPI.get().getPrestigeManager().registerPrestige(new PrestigeBuilder("Silver III")
                .setDisplayName("§7Silver §8III").setRequiredPoints(30000).build());
        PvAPI.get().getPrestigeManager().registerPrestige(new PrestigeBuilder("Silver IV")
                .setDisplayName("§7Silver §cIV").setRequiredPoints(35000).build());
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
