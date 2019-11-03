package me.Stefan923.SuperCoreLite.Language;

import me.Stefan923.SuperCoreLite.Utils.MessageUtils;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

public class LanguageManager implements MessageUtils {

    private static LanguageManager instance = new LanguageManager();
    private FileConfiguration config;
    private File cfile;
    private String languageFile;

    public static LanguageManager getInstance() {
        return instance;
    }

    public void setup(Plugin p, String languageFile) {
        this.languageFile = languageFile;

        cfile = new File(p.getDataFolder(), "languages/" + languageFile);
        if (!cfile.exists()) {
            try {
                //noinspection ResultOfMethodCallIgnored
                File dir = new File(p.getDataFolder() + "/languages");

                if (!dir.exists())
                    dir.mkdir();

                cfile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        config = YamlConfiguration.loadConfiguration(cfile);
        config.options().header("SuperCore Lite by Stefan923.\n");
        config.addDefault("Language Display Name", "English");
        config.addDefault("Command.AdminChat.Format", "&3[&bSTAFF&3] %luckperms_prefix_element_highest% &7%playername%: &f%message%");
        config.addDefault("Command.Broadcast.Format", "&7(&3Broadcast&7) &f%message%");
        config.addDefault("Command.Cooldown", "&7(&3!&7) &fYou must wait &b%cooldown% seconds &fto use the command again!");
        config.addDefault("Command.DonorChat.Format", "&3[&bDONOR&3] %luckperms_prefix_element_highest% &7%playername%: &f%message%");
        config.addDefault("Command.HelpOp.Format", "&4&k|&cHelpOp&4&k|&r &7%playername%: &f%message%");
        config.addDefault("Command.List.Format", Arrays.asList("&f&m---------&r&b[ &3&m--------------------&r &b]&f&m---------&r", "&7(&3!&7) &fThere are &b%list_size_supercore.list.default% &fplayers online!", " &7- &fAdmins (&3%list_size_supercore.list.admin%&f): %list_supercore.list.admin%", " &7- &fDonors (&3%list_size_supercore.list.donor%&f): %list_supercore.list.donor%", "&f&m---------&r&b[ &3&m--------------------&r &b]&f&m---------&r"));
        config.addDefault("Command.List.Name Color", "&b");
        config.addDefault("Command.List.Separator", "&f, ");
        config.addDefault("Command.Nick.Formatting Codes", "&7(&3!&7) &cYou are not allowed to use formatting codes in nickname!");
        config.addDefault("Command.Nick.Maximum Length", "&7(&3!&7) &cSpecified name exceeds the maximum length of &4%length% &ccharacters!");
        config.addDefault("General.Already Using Language", "&7(&3!&7) &fYou are already using this language.");
        config.addDefault("General.Available Languages.Syntax", "&7(&3!&7) &fAvailable languages are: ");
        config.addDefault("General.Available Languages.Separator", "&f, ");
        config.addDefault("General.Available Languages.Item Color", "&b");
        config.addDefault("General.Language Changed", "&7(&3!&7) &fYou have set your language to &b%language%&f!");
        config.addDefault("General.Nickname Changed", "&7(&3!&7) &fYour nickname has been set to &b%nickname%&f!");
        config.addDefault("General.Repeated Message", "&7(&3!&7) &fYou can not write &cthe same message&f!");
        config.addDefault("On Join.Join Message", "&7(&3!&7) &a%playername% &fjoined the game!");
        config.addDefault("On Quit.Quit Message", "&7(&3!&7) &c%playername% &fleft the game!");
        config.options().copyDefaults(true);

        save();
    }

    public FileConfiguration getConfig() {
        return config;
    }

    public void reset() {
        config.set("Language Display Name", "English");
        config.set("Command.AdminChat.Format", "&3[&bSTAFF&3] %luckperms_prefix_element_highest% &7%playername%: &f%message%");
        config.set("Command.Broadcast.Format", "&7(&3Broadcast&7) &f%message%");
        config.set("Command.Cooldown", "&7(&3!&7) &fYou must wait &b%cooldown% seconds &fto use the command again!");
        config.set("Command.DonorChat.Format", "&3[&bDONOR&3] %luckperms_prefix_element_highest% &7%playername%: &f%message%");
        config.set("Command.HelpOp.Format", "&4&k|&cHelpOp&4&k|&r &7%playername%: &f%message%");
        config.set("Command.List.Format", Arrays.asList("&f&m---------&r&b[ &3&m--------------------&r &b]&f&m---------&r", "&7(&3!&7) &fThere are &b%list_size_supercore.list.default% &fplayers online!", " &7- &fAdmins (&3%list_size_supercore.list.admin%&f): %list_supercore.list.admin%", " &7- &fDonors (&3%list_size_supercore.list.donor%&f): %list_supercore.list.donor%", "&f&m---------&r&b[ &3&m--------------------&r &b]&f&m---------&r"));
        config.set("Command.List.Name Color", "&b");
        config.set("Command.List.Separator", "&f, ");
        config.set("Command.Nick.Formatting Codes", "&7(&3!&7) &cYou are not allowed to use formatting codes in nickname!");
        config.set("Command.Nick.Maximum Length", "&7(&3!&7) &cSpecified name exceeds the maximum length of &4%length% &ccharacters!");
        config.set("General.Already Using Language", "&7(&3!&7) &fYou are already using this language.");
        config.set("General.Available Languages.Syntax", "&7(&3!&7) &fAvailable languages are: ");
        config.set("General.Available Languages.Separator", "&f, ");
        config.set("General.Available Languages.Item Color", "&b");
        config.set("General.Language Changed", "&7(&3!&7) &fYou have set your language to &b%language%&f!");
        config.set("General.Nickname Changed", "&7(&3!&7) &fYour nickname has been set to &b%nickname%&f!");
        config.set("General.Repeated Message", "&7(&3!&7) &fYou can not write &cthe same message&f!");
        config.set("On Join.Join Message", "&7(&3!&7) &a%playername% &fjoined the game!");
        config.set("On Quit.Quit Message", "&7(&3!&7) &c%playername% &fleft the game!");

        save();
    }

    public void save() {
        try {
            config.save(cfile);
        } catch (IOException e) {
            sendLogger(ChatColor.RED + "File '" + languageFile + "' couldn't be saved!");
        }
    }

    public void reload() {
        config = YamlConfiguration.loadConfiguration(cfile);
    }

    public String getLanguageFileName() {
        return languageFile;
    }

}
