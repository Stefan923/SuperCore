package me.stefan923.supercore.language;

import java.util.Map;

public class Language implements ILanguage {

    private final Map<MessagePath, String> messages;

    private final String name;

    public Language(String name, Map<MessagePath, String> messages) {
        this.name = name;
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

}
