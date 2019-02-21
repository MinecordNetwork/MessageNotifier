package net.minecord.messagenotifier.command;

import net.minecord.messagenotifier.MessageNotifier;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class ReloadCommand implements CommandExecutor {
    private MessageNotifier messageNotifier;

    public ReloadCommand(MessageNotifier messageNotifier) {
        this.messageNotifier = messageNotifier;
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (commandSender.hasPermission("messagenotifier.reload") && strings.length > 0 && strings[0].equals("reload")) {
            messageNotifier.onReload();
            commandSender.sendMessage(ChatColor.translateAlternateColorCodes('&', messageNotifier.getPluginPrefix() + "&aPlugin was successfully reloaded"));
            return true;
        }
        return false;
    }
}