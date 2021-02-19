package me.stefan923.supercore.configuration.setting;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class Setting {

    public static String DEFAULT_LANGUAGE = "english.yml";
    public static List<String> LANGUAGES = new ArrayList<>();

    public static String DEFAULT_HOME_NAME = "home";
    public static Map<String, Integer> HOME_LIMITS = new HashMap<>();

    /*
     * PRIVATE MESSAGES SETTINGS
     */

    private Setting() { }

    static {
        LANGUAGES.add("english.yml");

        HOME_LIMITS.put("default", 3);
        HOME_LIMITS.put("vip", 5);
        HOME_LIMITS.put("vip+", 10);
    }

}
