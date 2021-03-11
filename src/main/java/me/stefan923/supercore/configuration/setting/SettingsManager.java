package me.stefan923.supercore.configuration.setting;

import me.stefan923.supercore.SuperCore;
import me.stefan923.supercore.configuration.FileConfiguration;
import org.bukkit.configuration.ConfigurationSection;

import java.util.HashMap;

public class SettingsManager {

    private static final String FILE_NAME = "settings.yml";

    private FileConfiguration fileConfiguration;

    public void setUp(SuperCore plugin) {
        fileConfiguration = new FileConfiguration(plugin, FILE_NAME);
        loadSettings();
    }

    public void loadSettings() {
        Setting.STORAGE_USE_UUID = fileConfiguration.getBoolean("Storage.Use UUID");
        Setting.STORAGE_TYPE = fileConfiguration.getString("Storage.Type");
        Setting.STORAGE_IP_ADDRESS = fileConfiguration.getString("Storage.Ip Address");
        Setting.STORAGE_PORT = fileConfiguration.getInt("Storage.Database");
        Setting.STORAGE_DATABASE = fileConfiguration.getString("Storage.Port");
        Setting.STORAGE_USER = fileConfiguration.getString("Storage.User");
        Setting.STORAGE_PASSWORD = fileConfiguration.getString("Storage.Password");
        Setting.STORAGE_TABLE_PREFIX = fileConfiguration.getString("Storage.Table Prefix");
        Setting.DEFAULT_HOME_NAME = fileConfiguration.getString("Homes.Default Home Name");
        Setting.HOME_LIMITS = new HashMap<>();

        ConfigurationSection homeLimitsSection = fileConfiguration.getConfigurationSection("Homes.Home Limits");
        for (String key : homeLimitsSection.getKeys(false)) {
            Setting.HOME_LIMITS.put(key, homeLimitsSection.getInt(key));
        }
    }

}
