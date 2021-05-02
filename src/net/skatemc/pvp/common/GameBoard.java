package net.skatemc.pvp.common;

import net.skatemc.lib.ai.AIScoreboard;
import net.skatemc.lib.scoreboard.common.EntryBuilder;
import net.skatemc.lib.scoreboard.type.Entry;
import net.skatemc.lib.scoreboard.type.Scoreboard;
import net.skatemc.pvp.api.PvAPI;
import net.skatemc.pvp.api.player.PVPlayer;
import net.skatemc.pvp.config.ConfigEnum;
import net.skatemc.pvp.utils.ChatUtils;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class GameBoard extends AIScoreboard {

    private Map<UUID, Scoreboard> scoreboards;

    public GameBoard() {
        this.scoreboards = new HashMap<UUID, Scoreboard>();
    }

    @Override
    public List<Entry> getScores(Player player) {
        PVPlayer pvplayer = PvAPI.get().getPlayerManager().getPlayer(player);
        EntryBuilder entryBuilder = new EntryBuilder();
        FileConfiguration config = PvAPI.get().getConfigManager().getConfig(ConfigEnum.MESSAGES);
        for(String si : config.getStringList("scoreboard.scores")) {
            if(si.equals("")) {
                entryBuilder = entryBuilder.blank();
            } else {
                entryBuilder = entryBuilder.next(ChatUtils.colored(translate(player, si)));
            }
        }
        return entryBuilder.build();
//        return new EntryBuilder()
//                .blank()
//                .next("§fPrestige: §eSOON")
//                .blank()
//                .next("§fKills: §e" + pvplayer.getKills())
//                .next("§fDeaths: §e" + pvplayer.getDeaths())
//                .next("§fPoints: §e" + pvplayer.getPoints())
//                .next("§fSouls: §e" + pvplayer.getSouls())
//                .next("§fKDR: §e" + pvplayer.getKDR())
//                .blank()
//                .next("§eplay.skatemc.net")
//                .build();
    }

    @Override
    public String getName() {
        return "§e§lPVP";
    }

    @Override
    public Map<UUID, Scoreboard> getScoreboards() {
        return scoreboards;
    }

    @Override
    public long getUpdateInterval() {
        return 10L;
    }

    private String translate(Player player, String text) {
        PVPlayer pvPlayer = PvAPI.get().getPlayerManager().getPlayer(player);
        return text.replace("%player%", player.getName())
                .replace("%prestige%", ChatUtils.colored(pvPlayer.getPrestige().getDisplayName()))
                .replace("%progress%", ChatUtils.colored(pvPlayer.getProgressBar()))
                .replace("%kills%", ChatUtils.formattedInt(pvPlayer.getKills()))
                .replace("%kills%", ChatUtils.formattedInt(pvPlayer.getKills()))
                .replace("%deaths%", ChatUtils.formattedInt(pvPlayer.getDeaths()))
                .replace("%points%", ChatUtils.formattedInt(pvPlayer.getPoints()))
                .replace("%souls%", ChatUtils.formattedInt(pvPlayer.getSouls()))
                .replace("%kdr%", "" + pvPlayer.getKDR());
    }

}
