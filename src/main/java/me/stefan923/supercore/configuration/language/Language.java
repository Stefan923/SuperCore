package me.stefan923.supercore.configuration.language;

import java.util.Map;

public class Language implements ILanguage {

    private final Map<MessagePath, String> messages;

    private final String name;
    private final String fileName;

    public Language(String name, String fileName, Map<MessagePath, String> messages) {
        this.name = name;
        this.fileName = fileName;
        this.messages = messages;
    }

    public Map<MessagePath, String> getMessages() {
        return messages;
    }

    public String getMessage(MessagePath messagePath) {
        return messages.get(messagePath);
    }

    public String getName() {
        return name;
    }

    public String getFileName() {
        return fileName;
    }

}
