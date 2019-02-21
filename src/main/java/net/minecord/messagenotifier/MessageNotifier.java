package net.minecord.messagenotifier;

import net.minecord.messagenotifier.command.ReloadCommand;
import net.minecord.messagenotifier.controller.MessageController;
import net.minecord.messagenotifier.controller.TitleController;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.java.annotation.command.Command;
import org.bukkit.plugin.java.annotation.command.Commands;
import org.bukkit.plugin.java.annotation.plugin.Description;
import org.bukkit.plugin.java.annotation.plugin.Plugin;
import org.bukkit.plugin.java.annotation.plugin.Website;

@Plugin(name = "MessageNotifier", version = "0.1")
@Description("Minecraft (Spigot/Bukkit) plugin for sending auto messages and join titles")
@Commands(@Command(name = "messagenotifier", desc = "Help command"))
@Website("https://minecord.net")
public class MessageNotifier extends JavaPlugin {

    private final String pluginPrefix = "&b[MessageNotifier] &7";
    private MessageController messageController;
    private TitleController titleController;

    @Override
    public void onEnable() {
        messageController = new MessageController(this);
        titleController = new TitleController(this);

        getCommand("messagenotifier").setExecutor(new ReloadCommand(this));

        Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', pluginPrefix + "&aPlugin successfully enabled!"));
        Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', pluginPrefix + "Author: &eRixafy &a[https://rixafy.pro]"));
    }

    public void onReload() {
        saveDefaultConfig();
        reloadConfig();

        messageController.onDisable();
        messageController.onEnable();

        titleController.onDisable();
        titleController.onEnable();
    }

    @Override
    public void onDisable() {
        messageController.onDisable();

        Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', pluginPrefix + "&6Plugin successfully disabled!"));
    }

    public MessageController getMessageController() {
        return messageController;
    }

    public TitleController getTitleController() {
        return titleController;
    }

    public String getPluginPrefix() {
        return pluginPrefix;
    }
}
