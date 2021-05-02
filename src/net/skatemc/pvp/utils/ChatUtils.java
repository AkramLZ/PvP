package net.skatemc.pvp.utils;

import org.bukkit.ChatColor;

import java.text.DecimalFormat;

public class ChatUtils {

    public static String colored(String text) {
        return ChatColor.translateAlternateColorCodes('&', text);
    }

    public static String formattedInt(long x) {
        DecimalFormat decimalFormat = new DecimalFormat("###.##");
        String formatted = "" + x;
        if(x >= 1000L && x < 1000000L) {
            formatted = decimalFormat.format(x/1000L) + "k";
        }
        if(x >= 1000000L) {
            formatted = decimalFormat.format(x/1000000L) + "M";
        }
        return formatted;
    }

}
