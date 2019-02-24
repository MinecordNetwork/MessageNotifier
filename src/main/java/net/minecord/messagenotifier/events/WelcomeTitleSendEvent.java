package net.minecord.messagenotifier.events;

import net.minecord.messagenotifier.entity.WelcomeTitle;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class WelcomeTitleSendEvent extends Event implements Cancellable {

    private static final HandlerList handlerList = new HandlerList();
    private boolean isCancelled = false;
    private Player receiver;
    private WelcomeTitle welcomeTitle;

    public WelcomeTitleSendEvent(Player receiver, WelcomeTitle welcomeTitle) {
        this.receiver = receiver;
        this.welcomeTitle = welcomeTitle;
    }

    public Player getReceiver() {
        return receiver;
    }

    public void setReceiver(Player receiver) {
        this.receiver = receiver;
    }

    @Override
    public HandlerList getHandlers() {
        return handlerList;
    }

    public static HandlerList getHandlerList() {
        return handlerList;
    }

    @Override
    public boolean isCancelled() {
        return isCancelled;
    }

    @Override
    public void setCancelled(boolean cancel) {
        isCancelled = cancel;
    }

    public WelcomeTitle getWelcomeTitle() {
        return welcomeTitle;
    }

    public void setWelcomeTitle(WelcomeTitle welcomeTitle) {
        this.welcomeTitle = welcomeTitle;
    }
}
