package me.stefan923.supercore.language;

public enum MessagePath {

    LANGUAGE_NAME("Language Name"),
    ADMIN_CHAT_MESSAGE_FORMAT("Chat.Admin.Format"),
    DONOR_CHAT_MESSAGE_FORMAT("Chat.Donor.Format"),
    HELP_CHAT_MESSAGE_FORMAT("Chat.HelpOp.Format"),
    PRIVATE_MESSAGE_FORMAT("Chat.Private Message.Format");

    private String messagePath;

    private MessagePath(String messagePath) {
        this.messagePath = messagePath;
    }

    public String getMessagePath() {
        return messagePath;
    }

}
