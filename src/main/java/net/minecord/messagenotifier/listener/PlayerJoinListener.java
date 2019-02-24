package net.minecord.messagenotifier.listener;

import net.minecord.messagenotifier.MessageNotifier;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoinListener implements Listener {

    private MessageNotifier messageNotifier;

    public PlayerJoinListener(MessageNotifier messageNotifier) {
        this.messageNotifier = messageNotifier;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        messageNotifier.getTitleController().onJoin(event.getPlayer());
    }
}
