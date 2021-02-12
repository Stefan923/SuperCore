package me.stefan923.supercore.language;

public enum MessagePath {

    LANGUAGE_NAME("Language Display Name"),
    ADMIN_CHAT_MESSAGE_FORMAT("Chat.AdminChat.Format"),
    DONOR_CHAT_MESSAGE_FORMAT("Chat.DonorChat.Format"),
    HELP_CHAT_MESSAGE_FORMAT("Chat.HelpOp.Format"),
    PRIVATE_MESSAGE_FORMAT("Chat.Private Message.Format");

    private String path;

    private MessagePath(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }

}
