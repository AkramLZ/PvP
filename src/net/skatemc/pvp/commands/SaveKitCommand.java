package net.skatemc.pvp.commands;

import net.skatemc.lib.ai.AICommand;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public class SaveKitCommand extends AICommand {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(args.length != 0) {

        }
        return true;
    }

    @Override
    public String getName() {
        return "save";
    }

}