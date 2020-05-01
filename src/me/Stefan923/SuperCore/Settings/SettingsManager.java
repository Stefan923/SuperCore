package me.Stefan923.SuperCore.Settings;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

public class SettingsManager {

    private static SettingsManager instance = new SettingsManager();
    private FileConfiguration config;
    private File cfile;

    public static SettingsManager getInstance() {
        return instance;
    }

    public void setup(Plugin p) {
        cfile = new File(p.getDataFolder(), "settings.yml");
        config = YamlConfiguration.loadConfiguration(cfile);
        config.options().header("SuperCore by Stefan923\n");
        config.addDefault("Languages.Default Language", "lang_en.yml");
        config.addDefault("Languages.Available Languages", Arrays.asList("lang_en.yml"));
        config.addDefault("Enabled Commands.AdminChat", true);
        config.addDefault("Enabled Commands.Broadcast", true);
        config.addDefault("Enabled Commands.DonorChat", true);
        config.addDefault("Enabled Commands.Feed", true);
        config.addDefault("Enabled Commands.Fly", true);
        config.addDefault("Enabled Commands.Gamemode", true);
        config.addDefault("Enabled Commands.God", true);
        config.addDefault("Enabled Commands.Heal", true);
        config.addDefault("Enabled Commands.HelpOp", true);
        config.addDefault("Enabled Commands.Language", true);
        config.addDefault("Enabled Commands.List", true);
        config.addDefault("Enabled Commands.Nick", true);
        config.addDefault("Enabled Commands.Seen", true);
        config.addDefault("Enabled Commands.Tp", true);
        config.addDefault("Enabled Commands.TpToggle", true);
        config.addDefault("Enabled Commands.WhoIs", true);
        config.addDefault("Command Cooldowns.AdminChat", 5);
        config.addDefault("Command Cooldowns.DonorChat", 10);
        config.addDefault("Command Cooldowns.HelpOp", 10);
        config.addDefault("Command Cooldowns.Nick", 60);
        config.addDefault("Command.List.Group Permissions", Arrays.asList("supercore.list.default", "supercore.list.admin", "supercore.list.donor"));
        config.addDefault("Nick.Maximum Length", 16);
        config.addDefault("On Join.Enable Join Message", true);
        config.addDefault("On Quit.Enable Quit Message", true);
        config.addDefault("Storage.MySQL.Enable", false);
        config.addDefault("Storage.MySQL.IP Adress", "127.0.0.1");
        config.addDefault("Storage.MySQL.Port", 3306);
        config.addDefault("Storage.MySQL.Database Name", "yourDatabase");
        config.addDefault("Storage.MySQL.User", "yourUser");
        config.addDefault("Storage.MySQL.Password", "yourPassword");
        config.addDefault("Update Checker.Enable.On Plugin Enable", true);
        config.addDefault("Update Checker.Enable.On Join", true);
        config.options().copyDefaults(true);
        save();
    }

    public FileConfiguration getConfig() {
        return config;
    }

    public void resetConfig() {
        config.set("Languages.Default Language", "lang_en.yml");
        config.set("Languages.Available Languages", Arrays.asList("lang_en.yml"));
        config.set("Enabled Commands.AdminChat", true);
        config.set("Enabled Commands.Broadcast", true);
        config.set("Enabled Commands.DonorChat", true);
        config.set("Enabled Commands.Fly", true);
        config.set("Enabled Commands.Gamemode", true);
        config.set("Enabled Commands.God", true);
        config.set("Enabled Commands.Heal", true);
        config.set("Enabled Commands.HelpOp", true);
        config.set("Enabled Commands.Language", true);
        config.set("Enabled Commands.List", true);
        config.set("Enabled Commands.Nick", true);
        config.set("Enabled Commands.Seen", true);
        config.set("Enabled Commands.Tp", true);
        config.set("Enabled Commands.TpToggle", true);
        config.set("Enabled Commands.WhoIs", true);
        config.set("Command Cooldowns.AdminChat", 5);
        config.set("Command Cooldowns.DonorChat", 10);
        config.set("Command Cooldowns.HelpOp", 10);
        config.set("Command Cooldowns.Nick", 60);
        config.set("Command.List.Group Permissions", Arrays.asList("supercore.list.default", "supercore.list.admin", "supercore.list.donor"));
        config.set("Nick.Maximum Length", 16);
        config.set("On Join.Enable Join Message", true);
        config.set("On Quit.Enable Quit Message", true);
        config.set("Storage.MySQL.Enable", false);
        config.set("Storage.MySQL.IP Adress", "127.0.0.1");
        config.set("Storage.MySQL.Port", 3306);
        config.set("Storage.MySQL.Database Name", "yourDatabase");
        config.set("Storage.MySQL.User", "yourUser");
        config.set("Storage.MySQL.Password", "yourPassword");
        config.set("Update Checker.Enable.On Plugin Enable", true);
        config.set("Update Checker.Enable.On Join", true);
        save();
    }

    private void save() {
        try {
            config.save(cfile);
        } catch (IOException e) {
            Bukkit.getLogger().severe(ChatColor.RED + "File 'settings.yml' couldn't be saved!");
        }
    }

    public void reload() {
        config = YamlConfiguration.loadConfiguration(cfile);
    }

}
