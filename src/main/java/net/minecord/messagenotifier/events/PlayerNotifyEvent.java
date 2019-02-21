package net.minecord.messagenotifier.events;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class PlayerNotifyEvent extends Event {

    private static final HandlerList handlerList = new HandlerList();
    private Player receiver;
    private String message;
    private String prefix;

    public PlayerNotifyEvent(Player receiver, String message, String prefix) {
        this.receiver = receiver;
        this.message = message;
        this.prefix = prefix;
    }

    public Player getReceiver() {
        return receiver;
    }

    public void setReceiver(Player receiver) {
        this.receiver = receiver;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    @Override
    public HandlerList getHandlers() {
        return handlerList;
    }

    public static HandlerList getHandlerList() {
        return handlerList;
    }
}
