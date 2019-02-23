package net.minecord.messagenotifier.controller;

import net.minecord.messagenotifier.MessageNotifier;
import net.minecord.messagenotifier.events.NotifyScheduleEvent;
import net.minecord.messagenotifier.events.PlayerNotifyEvent;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class MessageController {

    private MessageNotifier messageNotifier;
    private List<String> defaultMessages = new ArrayList<>();
    private HashMap<String, List<String>> groupMessages = new HashMap<>();
    private String messagePrefix;
    private Random random = new Random();

    public MessageController(MessageNotifier messageNotifier) {
        this.messageNotifier = messageNotifier;

        onEnable(this.messageNotifier.getConfig());
    }

    public void onEnable(FileConfiguration config) {
        messagePrefix = config.getString("chat-messages.prefix");
        defaultMessages = config.getStringList("chat-messages.groups.default");

        sendChatMessage(config.getInt("chat-messages.delay"));
    }

    private void sendChatMessage(int delay) {
        new BukkitRunnable() {
            public void run() {
                String defaultMessage = defaultMessages.get(random.nextInt(defaultMessages.size()));

                NotifyScheduleEvent scheduleEvent = new NotifyScheduleEvent(delay);
                Bukkit.getPluginManager().callEvent(scheduleEvent);

                if (scheduleEvent.isCancelled()) {
                    sendChatMessage(scheduleEvent.getNextNotifyIn());
                }

                for (Player player : Bukkit.getOnlinePlayers()) {
                    PlayerNotifyEvent playerNotifyEvent = new PlayerNotifyEvent(player, defaultMessage, messagePrefix);
                    Bukkit.getPluginManager().callEvent(playerNotifyEvent);

                    player.sendMessage(playerNotifyEvent.getMessage().replace("{prefix}", playerNotifyEvent.getPrefix()));
                }
            }
        }.runTaskAsynchronously(messageNotifier);
    }

    public void onDisable() {

    }
}
