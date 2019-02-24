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
    private int groupsLoaded;
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
            if (!key.equals("default")) {
                groupMessages.put((String) key, config.getStringList("chat-messages.groups." + key));
            }
        }));

        groupsLoaded = groupMessages.size();

        sendChatMessage(config.getInt("chat-messages.delay"));
    }

    private void sendChatMessage(int delay) {
        currentThread = new BukkitRunnable() {
            public void run() {
                NotifyScheduleEvent scheduleEvent = new NotifyScheduleEvent(delay);
                Bukkit.getPluginManager().callEvent(scheduleEvent);

                if (scheduleEvent.isCancelled()) {
                    sendChatMessage(scheduleEvent.getNextNotifyIn());
                    return;
                }

                String defaultMessage = defaultMessages.get(random.nextInt(defaultMessages.size()));
                HashMap<String, String> groupFinalMessages = new HashMap<>();

                groupMessages.entrySet().stream()
                        .filter(entry -> !entry.getValue().isEmpty())
                        .forEach(entry -> groupFinalMessages.put(entry.getKey(), entry.getValue().get(random.nextInt(entry.getValue().size()))));

                for (Player player : Bukkit.getOnlinePlayers()) {
                    List<String> filteredMessages = new ArrayList<>();

                    if (!groupFinalMessages.isEmpty()) {
                        groupFinalMessages.entrySet().stream()
                                .filter(entry -> player.hasPermission("messagenotifier.group." + entry.getKey()))
                                .forEach(entry -> filteredMessages.add(entry.getValue()));
                    }

                    filteredMessages.add(defaultMessage);

                    String finalMessage = filteredMessages.get(random.nextInt(filteredMessages.size()));

                    PlayerNotifyEvent playerNotifyEvent = new PlayerNotifyEvent(player, finalMessage, messagePrefix);
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
