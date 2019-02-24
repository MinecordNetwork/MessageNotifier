package net.minecord.messagenotifier.controller;

import net.minecord.messagenotifier.MessageNotifier;
import net.minecord.messagenotifier.entity.WelcomeTitle;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class TitleController {

    private MessageNotifier messageNotifier;
    private List<WelcomeTitle> titles = new ArrayList<>();
    private boolean sendAll;

    public TitleController(MessageNotifier messageNotifier) {
        this.messageNotifier = messageNotifier;

        onEnable(this.messageNotifier.getConfig());
    }

    public void onEnable(FileConfiguration config) {
        sendAll = config.getBoolean("welcome-titles.send-all");

        config.getMapList("welcome-titles.titles").forEach((entry) ->
                titles.add(new WelcomeTitle(entry.get("title").toString(), entry.get("sub-title").toString(), Integer.valueOf(entry.get("stay").toString()) * 20, 5, 5)));

        Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', messageNotifier.getPluginPrefix() + "&7Welcome titles loaded: &a" + titles.size()));
    }

    public void onJoin(Player player) {

    }

    public void onDisable() {

    }
}
