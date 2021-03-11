package me.stefan923.supercore.configuration.setting;

import me.stefan923.supercore.SuperCore;
import me.stefan923.supercore.configuration.FileConfiguration;
import org.bukkit.configuration.ConfigurationSection;

import java.util.HashMap;

public class SettingsManager {

    private static final String FILE_NAME = "settings.yml";
    private static final SettingsManager INSTANCE = new SettingsManager();

    private FileConfiguration fileConfiguration;

    private SettingsManager() {}

    public static SettingsManager getInstance() {
        return INSTANCE;
    }

    public void setUp(SuperCore plugin) {
        fileConfiguration = new FileConfiguration(plugin, FILE_NAME);
        loadSettings();
    }

    public void loadSettings() {
        Setting.DEFAULT_HOME_NAME = fileConfiguration.getString("Homes.Default Home Name");
        Setting.HOME_LIMITS = new HashMap<>();

        ConfigurationSection homeLimitsSection = fileConfiguration.getConfigurationSection("Homes.Home Limits");
        for (String key : homeLimitsSection.getKeys(false)) {
            Setting.HOME_LIMITS.put(key, homeLimitsSection.getInt(key));
        }
    }

}
