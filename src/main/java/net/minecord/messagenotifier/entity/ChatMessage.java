package net.minecord.messagenotifier.entity;

public class ChatMessage {

    private String message;
    private String group;

    public ChatMessage(String message) {
        this.message = message;
        this.group = "default";
    }

    public ChatMessage(String message, String group) {
        this.message = message;
        this.group = group;
    }


    public String toString() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getGroup() {
        return group;
    }
}
