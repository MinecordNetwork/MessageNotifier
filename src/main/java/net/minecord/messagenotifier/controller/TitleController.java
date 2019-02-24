package net.minecord.messagenotifier.controller;

import net.minecord.messagenotifier.MessageNotifier;
import net.minecord.messagenotifier.entity.WelcomeTitle;
import net.minecord.messagenotifier.events.WelcomeTitleSendEvent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class TitleController {

    private MessageNotifier messageNotifier;
    private List<WelcomeTitle> titles = new ArrayList<>();
    private boolean sendAll;
    private Random random = new Random();

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
        new BukkitRunnable() {
            public void run() {
                if (!sendAll) {
                    WelcomeTitle randomTitle = titles.get(random.nextInt(titles.size()));
                    WelcomeTitleSendEvent welcomeTitleSendEvent = new WelcomeTitleSendEvent(player, randomTitle);

                    if (welcomeTitleSendEvent.isCancelled()) {
                        cancel();
                    }

                    sendTitle(player, randomTitle);

                } else {
                    int delayTicks = 0;
                    for (WelcomeTitle welcomeTitle : titles) {
                        sendTitle(player, welcomeTitle, delayTicks);
                        delayTicks += welcomeTitle.getFadeIn() + welcomeTitle.getStay();
                    }
                }
            }
        }.runTaskAsynchronously(messageNotifier);
    }

    private void sendTitle(Player player, WelcomeTitle welcomeTitle, int delay) {
        if (delay != 0) {
            new BukkitRunnable() {
                public void run() {
                    sendTitle(player, welcomeTitle);
                }
            }.runTaskLaterAsynchronously(messageNotifier, delay);

        } else {
            sendTitle(player, welcomeTitle);
        }
    }

    private void sendTitle(Player player, WelcomeTitle welcomeTitle) {
        player.sendTitle(ChatColor.translateAlternateColorCodes('&', welcomeTitle.getTitle()), ChatColor.translateAlternateColorCodes('&', welcomeTitle.getSubTitle()), welcomeTitle.getFadeIn(), welcomeTitle.getStay(), welcomeTitle.getFadeOut());
    }

    public void onDisable() {

    }
}
