package net.minecord.messagenotifier.events;

import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class ChatMessageScheduleEvent extends Event implements Cancellable {

    private static final HandlerList handlerList = new HandlerList();
    private boolean isCancelled = false;
    private int nextNotifyIn;

    public ChatMessageScheduleEvent(int nextNotifyIn) {
        this.nextNotifyIn = nextNotifyIn;
    }

    @Override
    public boolean isCancelled() {
        return isCancelled;
    }

    @Override
    public void setCancelled(boolean cancel) {
        isCancelled = cancel;
    }

    public int getNextNotifyIn() {
        return nextNotifyIn;
    }

    public void setNextNotifyIn(int nextNotifyIn) {
        this.nextNotifyIn = nextNotifyIn;
    }

    @Override
    public HandlerList getHandlers() {
        return handlerList;
    }

    public static HandlerList getHandlerList() {
        return handlerList;
    }
}
