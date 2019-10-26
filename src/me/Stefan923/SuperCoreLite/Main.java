package me.Stefan923.SuperCoreLite;

import me.Stefan923.SuperCoreLite.Commands.CommandManager;
import me.Stefan923.SuperCoreLite.Language.LanguageManager;
import me.Stefan923.SuperCoreLite.Listeners.PlayerJoinListener;
import me.Stefan923.SuperCoreLite.Listeners.PlayerQuitListener;
import me.Stefan923.SuperCoreLite.Settings.SettingsManager;
import me.Stefan923.SuperCoreLite.Utils.MessageUtils;
import me.Stefan923.SuperCoreLite.Utils.User;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;

public class Main extends JavaPlugin implements MessageUtils {

    public static Main instance;

    private SettingsManager settingsManager;
    private HashMap<String, LanguageManager> languageManagers;
    private CommandManager commandManager;

    private HashMap<String, User> users;

    @Override
    public void onEnable() {
        instance = this;

        settingsManager = SettingsManager.getInstance();
        settingsManager.setup(this);

        languageManagers = new HashMap<>();
        for (String fileName : settingsManager.getConfig().getStringList("Languages.Available Languages")) {
            LanguageManager languageManager = new LanguageManager();
            fileName = fileName.toLowerCase();
            languageManager.setup(this, fileName);
            languageManagers.put(fileName, languageManager);
        }

        users = new HashMap<>();

        sendLogger("&8&l> &7&m------ &8&l( &3&lSuperCore Lite &b&lby Stefan923 &8&l) &7&m------ &8&l<");
        sendLogger("&b   Plugin has been initialized.");
        sendLogger("&b   Version: &3v" + getDescription().getVersion());
        sendLogger("&b   Enabled listeners: &3" + enableListeners());
        sendLogger("&b   Enabled commands: &3" + enableCommands());
        sendLogger("&8&l> &7&m------ &8&l( &3&lSuperCore Lite &b&lby Stefan923 &8&l) &7&m------ &8&l<");
    }

    private Integer enableListeners() {
        Integer i = 2;
        PluginManager pluginManager = getServer().getPluginManager();
        pluginManager.registerEvents(new PlayerJoinListener(), this);
        pluginManager.registerEvents(new PlayerQuitListener(), this);
        return i;
    }

    private Integer enableCommands() {
        commandManager = new CommandManager(this);
        return commandManager.getCommands().size();
    }

    public SettingsManager getSettingsManager() {
        return settingsManager;
    }

    public void reloadSettingManager() {
        settingsManager.reload();
    }

    public LanguageManager getLanguageManager(String language) {
        return languageManagers.get(language);
    }

    public HashMap<String, LanguageManager> getLanguageManagers() {
        return languageManagers;
    }

    public void reloadLanguageManagers() {
        languageManagers.clear();
        for (String fileName : settingsManager.getConfig().getStringList("Languages.Available Languages")) {
            LanguageManager languageManager = new LanguageManager();
            fileName = fileName.toLowerCase();
            languageManager.setup(this, fileName);
            languageManagers.put(fileName, languageManager);
        }
    }

    public HashMap<String, User> getUsers() {
        return users;
    }

    public void addUser(Player player) {
        users.put(player.getName(), new User(player));
    }

    public void removeUser(String playerName) {
        users.remove(playerName);
    }

    public User getUser(Player player) {
        return users.get(player.getName());
    }

    public User getUser(String playerName) {
        return users.get(playerName);
    }

    @Override
    public void onDisable() {
        users.clear();
        for (Player player : Bukkit.getOnlinePlayers()) {
            player.kickPlayer(formatAll("&8「&3SuperCore&8」 &cPlugin has been disabled."));
        }
    }

}
