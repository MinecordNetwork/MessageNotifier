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

        Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', messageNotifier.getPluginPrefix() + "&7Default messages loaded: &a" + defaultMessages.size()));

        Set<String> groups = config.getConfigurationSection("chat-messages.groups").getKeys(false);
        groups.forEach(key -> {
            if (!key.equals("default")) {
                List<String> messages = config.getStringList("chat-messages.groups." + key);
                groupMessages.put(key, messages);
                Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', messageNotifier.getPluginPrefix() + "&7Loaded group &b" + key + " &7with &a" + messages.size() + " &7messages"));
            }
        });

        Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', messageNotifier.getPluginPrefix() + "&7Groups loaded: &a" + groupMessages.size()));

        sendChatMessage(config.getInt("chat-messages.delay"));
    }

    private void sendChatMessage(int delay) {
        currentThread = new BukkitRunnable() {
            public void run() {
                NotifyScheduleEvent scheduleEvent = new NotifyScheduleEvent(delay);
                Bukkit.getPluginManager().callEvent(scheduleEvent);

                if (scheduleEvent.isCancelled()) {
                    sendChatMessage(scheduleEvent.getNextNotifyIn());
                    cancel();
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

                sendChatMessage(scheduleEvent.getNextNotifyIn());
            }
        }.runTaskLaterAsynchronously(messageNotifier, delay * 20);
    }

    public void onDisable() {
        currentThread.cancel();
    }
}
