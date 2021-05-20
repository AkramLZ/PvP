package net.skatemc.pvp.commands;

import net.skatemc.lib.ai.AICommand;
import net.skatemc.pvp.api.PvAPI;
import net.skatemc.pvp.config.ConfigEnum;
import net.skatemc.pvp.utils.ChatUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class ProgressCommand extends AICommand {
    @Override
    public String getName() {
        return "progress";
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        boolean isPlayer = commandSender instanceof Player;
        if(isPlayer) {
            Player player = (Player) commandSender;
            if(strings.length == 0) {
                List<String> msg = PvAPI.get().getConfigManager().getConfig(ConfigEnum.MESSAGES)
                        .getStringList("messages.progress-command.you");
                msg.forEach(x -> {
                    player.sendMessage(ChatUtils.colored(x).replace("%progress%",
                            PvAPI.get().getPlayerManager().getPlayer(player).getProgressBar()));
                });
            } else if(strings.length == 1) {
                Player target = Bukkit.getPlayerExact(strings[0]);
                if(target == null) {
                    String notOnline = PvAPI.get().getConfigManager().getConfig(ConfigEnum.MESSAGES)
                            .getString("messages.progress-command.not-online");
                    player.sendMessage(ChatUtils.colored(notOnline).replace("%player%", strings[0]));
                } else {
                    List<String> msg = PvAPI.get().getConfigManager().getConfig(ConfigEnum.MESSAGES)
                            .getStringList("messages.progress-command.others");
                    msg.forEach(x -> {
                        player.sendMessage(ChatUtils.colored(x)
                                .replace("%suffix%",
                                        PvAPI.get().getPlayerManager().getPlayer(target).getSuffix())
                                .replace("%player%", target.getName())
                                .replace("%progress%",
                                        PvAPI.get().getPlayerManager().getPlayer(target).getProgressBar()));
                    });
                }
            } else {
                String usage = PvAPI.get().getConfigManager().getConfig(ConfigEnum.MESSAGES)
                        .getString("messages.progress-command.usage");
                player.sendMessage(ChatUtils.colored(usage));
            }
            return true;
        }
        return true;
    }
}
