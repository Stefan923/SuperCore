package me.stefan923.supercore.configuration.language;

import java.util.Map;

public interface ILanguage {

    /**
     * Returns a copy of the language's map of messages.
     * @return messages - HashMap<MessageType, String>
     */
    public Map<MessagePath, String> getMessages();

    /**
     * Retruns the message corresponding to the requested message path.
     * @param messagePath - requested message's path
     * @return requested message - String
     */
    public String getMessage(MessagePath messagePath);

    /**
     * Returns language's name
     * @return language's name - String
     */
    public String getName();

}
