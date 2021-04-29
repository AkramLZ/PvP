package net.skatemc.pvp.utils;

import org.bukkit.ChatColor;

public class ChatUtils {

    public static String colored(String text) {
        return ChatColor.translateAlternateColorCodes('&', text);
    }

}
