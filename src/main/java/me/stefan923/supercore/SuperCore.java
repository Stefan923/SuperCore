package me.stefan923.supercore;

import me.stefan923.supercore.configuration.setting.SettingsManager;
import me.stefan923.supercore.database.DatabaseManager;
import me.stefan923.supercore.configuration.language.LanguageManager;
import me.stefan923.supercore.util.LoggerUtil;
import me.stefan923.supercore.util.Metrics;
import org.bukkit.plugin.java.JavaPlugin;

public class SuperCore extends JavaPlugin {

    private static SuperCore instance;

    @Override
    public void onEnable() {
        instance = this;

        SettingsManager settingsManager = SettingsManager.getInstance();
        settingsManager.setUp(this);

        LanguageManager languageManager = LanguageManager.getInstance();
        languageManager.loadAllLanguages(this);

        DatabaseManager databaseManager = DatabaseManager.getInstance();
        databaseManager.getDatabase().init();

        new Metrics(this, 6546);

        LoggerUtil.sendInfo("&8&l> &7&m------- &8&l( &3&lSuperCore &b&lby Stefan923 &8&l) &7&m------- &8&l<");
        LoggerUtil.sendInfo("&b   Plugin has been initialized.");
        LoggerUtil.sendInfo("&b   Version: &3v" + getDescription().getVersion());
        LoggerUtil.sendInfo("&8&l> &7&m------- &8&l( &3&lSuperCore &b&lby Stefan923 &8&l) &7&m------- &8&l<");
    }

    public static SuperCore getInstance() {
        return instance;
    }

    @Override
    public void onDisable() { }

}
