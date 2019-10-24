package me.Stefan923.SuperCoreLite.Language;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.io.IOException;

public class LanguageManager {

    static LanguageManager instance = new LanguageManager();
    FileConfiguration config;
    File cfile;

    public static LanguageManager getInstance() {
        return instance;
    }

    public void setup(Plugin p) {
        cfile = new File(p.getDataFolder(), "language.yml");
        config = YamlConfiguration.loadConfiguration(cfile);
        config.options().header("SuperCore Lite by Stefan923.\n");
        config.addDefault("Command.AdminChat.Format", "&3[&bSTAFF&3] %luckperms_prefix_element_highest% &7%playername%: &f%message%");
        config.addDefault("Command.Cooldown", "&7&l(&3&l!&7&l) &fYou must wait &b%cooldown% seconds &fto use the command again!");
        config.addDefault("Command.DonorChat.Format", "&3[&bDONOR&3] %luckperms_prefix_element_highest% &7%playername%: &f%message%");
        config.addDefault("Command.HelpOp.Format", "&4&k|&cHelpOp&4&k|&r &7%playername%: &f%message%");
        config.addDefault("General.Repeated Message", "&7&l(&3&l!&7&l) &fYou can not write &cthe same message&f!");
        config.options().copyDefaults(true);
        save();
    }

    public FileConfiguration getConfig() {
        return config;
    }

    public void reset() {
        config.set("Command.AdminChat.Cooldown", "&7&l(&3&l!&7&l) &fYou must wait &b5 seconds &fto use the command again!");
        config.set("Command.AdminChat.Format", "&3[&bSTAFF&3] %luckperms_prefix_element_highest% &7%playername%: &f%message%");
        config.set("Command.AdminChat.Repeated Message", "&7&l(&3&l!&7&l) &fYou can not write &cthe same message&f!");
        config.set("Command.DonorChat.Cooldown", "&7&l(&3&l!&7&l) &fYou must wait &b5 seconds &fto use the command again!");
        config.set("Command.DonorChat.Format", "&3[&bDONOR&3] %luckperms_prefix_element_highest% &7%playername%: &f%message%");
        config.set("Command.DonorChat.Repeated Message", "&7&l(&3&l!&7&l) &fYou can not write &cthe same message&f!");
        save();
    }

    public void save() {
        try {
            config.save(cfile);
        } catch (IOException e) {
            Bukkit.getLogger().severe(ChatColor.RED + "File 'language.yml' couldn't be saved!");
        }
    }

    public void reload() {
        config = YamlConfiguration.loadConfiguration(cfile);
    }

}
