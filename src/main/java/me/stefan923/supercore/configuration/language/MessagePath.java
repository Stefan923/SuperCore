package me.stefan923.supercore.configuration.language;

public enum MessagePath {

    LANGUAGE_NAME("Language Display Name"),
    COMMAND_INVALID_SYNTAX("Command.Invalid Syntax"),
    GENERAL_MUST_BE_PLAYER("General.Must Be Player"),
    GENERAL_NO_PERMISSION("General.No Permission");

    private final String path;

    MessagePath(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }

}
