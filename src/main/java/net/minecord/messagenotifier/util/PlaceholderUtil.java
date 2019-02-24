package net.minecord.messagenotifier.util;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class PlaceholderUtil {

    public static String replace(String message, Player player) {
        return message.replace("{playerName}", player.getName())
                .replace("{playerDisplayName}", player.getDisplayName())
                .replace("{onlinePlayers}", Bukkit.getOnlinePlayers().size() + "")
                .replace("{maxPlayers}", Bukkit.getMaxPlayers() + "");
    }
}
