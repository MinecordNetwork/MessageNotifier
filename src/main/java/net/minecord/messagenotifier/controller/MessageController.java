package net.minecord.messagenotifier.controller;

import net.minecord.messagenotifier.MessageNotifier;
import net.minecord.messagenotifier.events.NotifyScheduleEvent;
import net.minecord.messagenotifier.events.PlayerNotifyEvent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.*;

public class MessageController {

    private MessageNotifier messageNotifier;
    private List<String> defaultMessages;
    private HashMap<String, List<String>> groupMessages = new HashMap<>();
    private String messagePrefix;
    private Random random = new Random();
    private BukkitTask currentThread;

    public MessageController(MessageNotifier messageNotifier) {
        this.messageNotifier = messageNotifier;

        onEnable(this.messageNotifier.getConfig());
    }

    public void onEnable(FileConfiguration config) {
        messagePrefix = config.getString("chat-messages.prefix");
        defaultMessages = config.getStringList("chat-messages.groups.default");

        List<Map<?, ?>> groups = config.getMapList("chat-messages.groups");
        groups.forEach((entry) -> entry.forEach((key, value) -> {
            groupMessages.put((String) key, config.getStringList("chat-messages.groups." + key));
        }));

        sendChatMessage(config.getInt("chat-messages.delay"));
    }

    private void sendChatMessage(int delay) {
        currentThread = new BukkitRunnable() {
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

                    if (playerNotifyEvent.isCancelled()) {
                        continue;
                    }

                    player.sendMessage(ChatColor.translateAlternateColorCodes('&', playerNotifyEvent.getMessage().replace("{prefix}", playerNotifyEvent.getPrefix())));
                }
            }
        }.runTaskLaterAsynchronously(messageNotifier, delay * 20);
    }

    public void onDisable() {
        currentThread.cancel();
    }
}
