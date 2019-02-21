package net.minecord.messagenotifier.controller;

import net.minecord.messagenotifier.MessageNotifier;

import java.util.ArrayList;
import java.util.List;

public class TitleController {

    private MessageNotifier messageNotifier;
    private List<String> titles = new ArrayList<>();

    public TitleController(MessageNotifier messageNotifier) {
        this.messageNotifier = messageNotifier;
    }

    public void onEnable() {

    }

    public void onDisable() {

    }
}
