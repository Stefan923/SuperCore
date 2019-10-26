package me.Stefan923.SuperCoreLite.Settings;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;

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
        config.options().header("SuperCore Lite by Stefan923\n");
        config.addDefault("Languages.Default Language Display Name", "lang_en.yml");
        config.addDefault("Languages.Available Languages.English", "lang_en.yml");
        config.addDefault("Enabled Commands.AdminChat", true);
        config.addDefault("Enabled Commands.DonorChat", true);
        config.addDefault("Enabled Commands.HelpOp", true);
        config.addDefault("Enabled Commands.Language", true);
        config.addDefault("Enabled Commands.List", true);
        config.addDefault("Command Cooldowns.AdminChat", 5);
        config.addDefault("Command Cooldowns.DonorChat", 10);
        config.addDefault("Command Cooldowns.HelpOp", 10);
        config.addDefault("Command.List.Group Permissions", Arrays.asList("supercore.list.default", "supercore.list.admin", "supercore.list.donor"));
        config.addDefault("On Join.Enable Join Message", true);
        config.addDefault("On Join.Enable Quit Message", true);
        config.options().copyDefaults(true);
        save();
    }

    public FileConfiguration getConfig() {
        return config;
    }

    public void resetConfig() {
        config.set("Languages.Default Language", "lang_en.yml");
        config.set("Languages.Available Languages.English", "lang_en.yml");
        config.set("Enabled Commands.AdminChat", true);
        config.set("Enabled Commands.DonorChat", true);
        config.set("Enabled Commands.HelpOp", true);
        config.set("Enabled Commands.Language", true);
        config.set("Enabled Commands.List", true);
        config.set("Command Cooldowns.AdminChat", 5);
        config.set("Command Cooldowns.DonorChat", 10);
        config.set("Command Cooldowns.HelpOp", 10);
        config.set("Command.List.Group Permissions", Arrays.asList("supercore.list.default", "supercore.list.admin", "supercore.list.donor"));
        config.set("On Join.Enable Join Message", true);
        config.set("On Join.Enable Quit Message", true);
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
