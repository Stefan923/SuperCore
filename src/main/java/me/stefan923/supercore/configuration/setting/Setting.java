package me.stefan923.supercore.configuration.setting;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class Setting {

    // Storage Settings
    public static boolean STORAGE_USE_UUID = false;
    public static String STORAGE_TYPE = "h2";
    public static String STORAGE_IP_ADDRESS = "127.0.0.1";
    public static int STORAGE_PORT = 3306;
    public static String STORAGE_DATABASE = "database";
    public static String STORAGE_USER = "username";
    public static String STORAGE_PASSWORD = "password";
    public static String STORAGE_TABLE_PREFIX = "supercore_";

    // Language Settings
    public static String DEFAULT_LANGUAGE_FILENAME = "english.yml";
    public static List<String> LANGUAGE_FILENAMES = new ArrayList<>();

    // Homes Settings
    public static String DEFAULT_HOME_NAME = "home";
    public static Map<String, Integer> HOME_LIMITS = new HashMap<>();

    private Setting() { }

    static {
        LANGUAGE_FILENAMES.add("english.yml");

        HOME_LIMITS.put("default", 3);
        HOME_LIMITS.put("vip", 5);
        HOME_LIMITS.put("vip+", 10);
    }

}
