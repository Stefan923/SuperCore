package me.stefan923.supercore;

import me.stefan923.supercore.configuration.setting.SettingsManager;
import me.stefan923.supercore.database.DatabaseManager;
import me.stefan923.supercore.configuration.language.LanguageManager;
import me.stefan923.supercore.util.LoggerUtil;
import me.stefan923.supercore.util.Metrics;
import org.bukkit.plugin.java.JavaPlugin;

public class SuperCore extends JavaPlugin {

    private static SuperCore instance;

    private SettingsManager settingsManager;
    private LanguageManager languageManager;
    private DatabaseManager databaseManager;

    @Override
    public void onEnable() {
        instance = this;

        settingsManager = new SettingsManager();
        settingsManager.setUp(this);

        languageManager = new LanguageManager();
        languageManager.loadAllLanguages(this);

        databaseManager = new DatabaseManager();
        databaseManager.getDatabase().init();

        new Metrics(this, 6546);

        LoggerUtil.sendInfo("> ------- ( SuperCore by Stefan923 ) ------- <");
        LoggerUtil.sendInfo("   Plugin has been initialized.");
        LoggerUtil.sendInfo("   Version: v" + getDescription().getVersion());
        LoggerUtil.sendInfo("> ------- ( SuperCore by Stefan923 ) ------- <");
    }

    public static SuperCore getInstance() {
        return instance;
    }

    public SettingsManager getSettingsManager() {
        return settingsManager;
    }

    public LanguageManager getLanguageManager() {
        return languageManager;
    }

    public DatabaseManager getDatabaseManager() {
        return databaseManager;
    }

    @Override
    public void onDisable() { }

}
