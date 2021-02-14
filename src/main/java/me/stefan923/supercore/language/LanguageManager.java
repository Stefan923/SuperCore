package me.stefan923.supercore.language;

import me.stefan923.supercore.SuperCore;
import me.stefan923.supercore.configuration.FileConfiguration;

import java.util.*;

public class LanguageManager {

    private static final LanguageManager instance = new LanguageManager();

    private final List<Language> languages = new ArrayList<>();

    private LanguageManager() {}

    public static LanguageManager getInstance() {
        return instance;
    }

    public void loadLanguage(SuperCore plugin, String fileName) {
        FileConfiguration fileConfiguration = new FileConfiguration(plugin, "languages/" + fileName);

        String languageName = fileConfiguration.getString(MessagePath.LANGUAGE_NAME.getPath());
        Map<MessagePath, String> messages = new HashMap<>();
        for (MessagePath messagePath : MessagePath.values()) {
            if (!MessagePath.LANGUAGE_NAME.equals(messagePath)) {
                messages.put(messagePath, fileConfiguration.getString(messagePath.getPath()));
            }
        }

        languages.add(new Language(languageName, fileName, messages));
    }

    public Language getLanguageByName(String name) {
        return languages.stream()
                .filter(language -> name.equals(language.getName()))
                .findFirst()
                .orElse(null);
    }

    public Language getLanguageByFileName(String fileName) {
        return languages.stream()
                .filter(language -> fileName.equals(language.getFileName()))
                .findFirst()
                .orElse(null);
    }

}
