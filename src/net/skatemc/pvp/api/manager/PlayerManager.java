package net.skatemc.pvp.api.manager;

import net.luckperms.api.LuckPermsProvider;
import net.luckperms.api.model.group.Group;
import net.skatemc.pvp.api.PvAPI;
import net.skatemc.pvp.api.player.PVPlayer;
import net.skatemc.pvp.api.player.Prestige;
import org.bukkit.entity.Player;

import java.util.HashSet;
import java.util.Set;

public class PlayerManager {

    private Set<PVPlayer> players;

    public PlayerManager() {
        players = new HashSet<PVPlayer>();
    }

    public Set<PVPlayer> getPlayers() {
        return players;
    }

    public PVPlayer getPlayer(Player player) {
        return players.stream().filter(pvPlayer -> pvPlayer.getPlayer() == player).findFirst().orElse(null);
    }

    public void injectPlayer(Player player) {
        players.add(new PVPlayer() {

            @Override
            public Player getPlayer() {
                return player;
            }

            @Override
            public Group getGroup() {
                return LuckPermsProvider.get().getGroupManager().getGroup(LuckPermsProvider.get().getUserManager()
                        .getUser(getPlayer().getUniqueId()).getCachedData().getMetaData().getPrimaryGroup());
            }
        });
        getPlayer(player).injectData();
    }

    public void rejectPlayer(Player player) {
        getPlayer(player).saveData();
        players.remove(getPlayer(player));
    }

}
