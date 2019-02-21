package net.minecord.messagenotifier.controller;

import net.minecord.messagenotifier.MessageNotifier;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MessageController {

    private MessageNotifier messageNotifier;
    private List<String> defaultMessages = new ArrayList<>();
    private HashMap<String, List<String>> groupMessages = new HashMap<>();

    public MessageController(MessageNotifier messageNotifier) {
        this.messageNotifier = messageNotifier;
    }

    public void onDisable() {

    }

    public void onEnable() {

    }
}
