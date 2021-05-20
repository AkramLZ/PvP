package net.skatemc.pvp.commands;

import net.skatemc.lib.ai.AICommand;
import net.skatemc.pvp.api.PvAPI;
import net.skatemc.pvp.config.ConfigEnum;
import net.skatemc.pvp.utils.ChatUtils;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class SaveKitCommand extends AICommand {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        boolean isPlayer = sender instanceof Player;
        if(isPlayer) {
            Player player = (Player) sender;
            if(args.length != 0) {
                String usage = ChatUtils.colored(PvAPI.get().getConfigManager().getConfig(ConfigEnum.MESSAGES)
                        .getString("messages.kit-save.usage"));
                player.sendMessage(usage);
                return false;
            }
            if(isEligible(player)) {
                PvAPI.get().getPlayerManager().getPlayer(player).saveKit();
                String saved = ChatUtils.colored(PvAPI.get().getConfigManager().getConfig(ConfigEnum.MESSAGES)
                        .getString("messages.kit-save.saved"));
                player.sendMessage(saved);
                return true;
            }
            String notEligible = ChatUtils.colored(PvAPI.get().getConfigManager().getConfig(ConfigEnum.MESSAGES)
                    .getString("messages.kit-save.not-eligible"));
            player.sendMessage(notEligible);
        }
        return true;
    }

    @Override
    public String getName() {
        return "save";
    }

    protected boolean isEligible(Player player) {
        Inventory inv = player.getInventory();
        return inv.contains(Material.STONE_SWORD) && inv.contains(Material.FISHING_ROD)
                && inv.contains(Material.BOW);
    }

}