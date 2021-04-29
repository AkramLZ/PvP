package net.skatemc.pvp.commands;

import net.skatemc.lib.ai.AICommand;
import net.skatemc.pvp.utils.ChatUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public class PrestigesCommand extends AICommand {
    @Override
    public String getName() {
        return "prestiges";
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {
        if(args.length != 1) {
            commandSender.sendMessage();
        }
        return true;
    }
}
