package net.minecord.messagenotifier.entity;

public class ChatMessage {

    private String message;
    private String prefix;
    private String group;

    public ChatMessage(String message, String prefix) {
        this(message, prefix, "default");
    }

    public ChatMessage(String message, String prefix, String group) {
        this.message = message;
        this.prefix = prefix;
        this.group = group;
    }


    public String toString() {
        return message.replace("{prefix}", prefix);
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getGroup() {
        return group;
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }
}
