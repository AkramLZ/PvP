package net.skatemc.pvp.commands;

import net.skatemc.lib.ai.AICommand;
import net.skatemc.pvp.api.PvAPI;
import net.skatemc.pvp.api.player.Prestige;
import net.skatemc.pvp.config.ConfigEnum;
import net.skatemc.pvp.utils.ChatUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.List;

public class PrestigesCommand extends AICommand {
    @Override
    public String getName() {
        return "prestiges";
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {
        if(args.length != 1) {
            commandSender.sendMessage(ChatUtils.colored(PvAPI.get().getConfigManager()
            .getConfig(ConfigEnum.MESSAGES).getString("messages.prestiges.usage")));
        } else {
            try {
                int page = Integer.parseInt(args[0]);
                List<List<Prestige>> pageList = new ArrayList<>();
                List<Prestige> prestiges = PvAPI.get().getPrestigeManager().getPrestiges();
                double af = (double) prestiges.size()/5;
                int aff = (int) af;
                if(af > aff) {
                    aff+=1;
                }
                int pages = aff;
                if(page < 1 || page > pages) {
                    commandSender.sendMessage(ChatUtils.colored(PvAPI.get().getConfigManager()
                            .getConfig(ConfigEnum.MESSAGES).getString("messages.prestiges.invalid-page")
                    .replace("%pages%", "" + pages)));
                    return false;
                }
                int x0 = 1, plusMultiplier = 0;
                for(int i = 1; i <= pages; i++) {
                    if(i*5 > prestiges.size()) {
                        pageList.add(prestiges.subList(x0-1, prestiges.size()));
                    } else {
                        pageList.add(prestiges.subList(x0-1, i*5));
                    }
                    if (x0 == 1) {
                        x0 = 0;
                    }
                    x0+=6;
                    plusMultiplier++;
                }
                String header = ChatUtils.colored(PvAPI.get().getConfigManager().getConfig(ConfigEnum.MESSAGES)
                    .getString("messages.prestiges.header").replace("%page%", "" + page)
                    .replace("%pages%", "" + pages));
                commandSender.sendMessage(header);
                pageList.get(page-1).forEach(prestige -> {
                    String middle = ChatUtils.colored(PvAPI.get().getConfigManager().getConfig(ConfigEnum.MESSAGES)
                            .getString("messages.prestiges.middle").replace("%prestige%", prestige.getDisplayName())
                            .replace("%points%", "" + prestige.getRequiredPoints()));
                    commandSender.sendMessage(middle);
                });
                String footer = ChatUtils.colored(PvAPI.get().getConfigManager().getConfig(ConfigEnum.MESSAGES)
                        .getString("messages.prestiges.footer").replace("%page%", "" + page)
                        .replace("%pages%", "" + pages));
                commandSender.sendMessage(footer);
            } catch (NumberFormatException exception) {
                commandSender.sendMessage(ChatUtils.colored(PvAPI.get().getConfigManager()
                        .getConfig(ConfigEnum.MESSAGES).getString("messages.prestiges.invalid-number")));
            }
        }
        return true;
    }
}
