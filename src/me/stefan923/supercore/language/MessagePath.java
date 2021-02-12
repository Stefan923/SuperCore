package me.stefan923.supercore.language;

public enum MessagePath {

    ADMIN_CHAT_MESSAGE_FORMAT("chat.admin.format"),
    DONOR_CHAT_MESSAGE_FORMAT("chat.donor.format"),
    PRIVATE_MESSAGE_FORMAT("chat.private-message.format");

    private String messagePath;

    private MessagePath(String messagePath) {
        this.messagePath = messagePath;
    }

    public String getMessagePath() {
        return messagePath;
    }

}
