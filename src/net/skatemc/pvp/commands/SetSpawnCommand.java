package net.skatemc.pvp.commands;

import net.skatemc.lib.ai.AICommand;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SetSpawnCommand extends AICommand {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (!player.hasPermission("pvp.setspawn")) {
                player.sendMessage("§cYou don't have permission to do that,");
                return false;
            }
            player.sendMessage("§eYou've successfully set spawn.");
        }
        return true;
    }

    @Override
    public String getName() {
        return "setspawn";
    }

}
