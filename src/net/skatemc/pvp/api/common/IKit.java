package net.skatemc.pvp.api.common;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public interface IKit {

    ItemStack[] getContents(Player player);

    ItemStack[] getArmors(Player player);

    String getName();

    default void give(Player player) {
        player.getInventory().clear();
        player.getInventory().setArmorContents(getArmors(player));
        player.getInventory().setContents(getContents(player));
    }

}