package me.Stefan923.SuperCore.Language;

import me.Stefan923.SuperCore.Utils.MessageUtils;
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
        config.options().header("SuperCore by Stefan923.\n");
        config.addDefault("Language Display Name", "English");
        config.addDefault("Command.AdminChat.Format", "&3[&bSTAFF&3] %luckperms_prefix_element_highest% &7%playername%: &f%message%");
        config.addDefault("Command.AdminChat.Format By Console", "&3[&bSTAFF&3] &cCONSOLE&7: &f%message%");
        config.addDefault("Command.Broadcast.Format", "&7(&3Broadcast&7) &f%message%");
        config.addDefault("Command.Cooldown", "&8(&3!&8) &fYou must wait &b%cooldown% seconds &fto use the command again!");
        config.addDefault("Command.DonorChat.Format", "&3[&bDONOR&3] %luckperms_prefix_element_highest% &7%playername%: &f%message%");
        config.addDefault("Command.DonorChat.Format By Console", "&3[&bDONOR&3] &cCONSOLE&7: &f%message%");
        config.addDefault("Command.Feed.Self-Fed", "&8(&3!&8) &fYou have successfully fed yourself&f!");
        config.addDefault("Command.Feed.Fed By", "&8(&3!&8) &fYou have been fed by &b%sender%&f!");
        config.addDefault("Command.Feed.Fed Others", "&8(&3!&8) &fYou have successfully fed &b%target%&f!");
        config.addDefault("Command.Fly.Own Flight Mode Changed", "&8(&3!&8) &fYour flight mode has been %status%&f!");
        config.addDefault("Command.Fly.Own Flight Mode Changed By", "&8(&3!&8) &fYour flight mode has been %status%&f by &b%sender%&f!");
        config.addDefault("Command.Fly.Others Flight Mode Changed", "&8(&3!&8) &b%target%&f's flight mode has been %status%&f!");
        config.addDefault("Command.Gamemode.Own Gamemode Changed", "&8(&3!&8) &fYour gamemode has been set to &b%gamemode%&f!");
        config.addDefault("Command.Gamemode.Others Gamemode Changed", "&8(&3!&8) &fYou set &b%playername%&f's gamemode to &3%gamemode%&f!");
        config.addDefault("Command.God.Own God Mode Changed", "&8(&3!&8) &fYour god mode has been %status%&f!");
        config.addDefault("Command.God.Own God Mode Changed By", "&8(&3!&8) &fYour god mode has been %status%&f by &b%sender%&f!");
        config.addDefault("Command.God.Others God Mode Changed", "&8(&3!&8) &b%target%&f's god mode has been %status%&f!");
        config.addDefault("Command.Heal.Self-Healed", "&8(&3!&8) &fYou have successfully healed yourself&f!");
        config.addDefault("Command.Heal.Healed By", "&8(&3!&8) &fYou have been healed by &b%sender%&f!");
        config.addDefault("Command.Heal.Healed Others", "&8(&3!&8) &fYou have successfully healed &b%target%&f!");
        config.addDefault("Command.HelpOp.Format", "&4&k|&cHelpOp&4&k|&r &7%playername%: &f%message%");
        config.addDefault("Command.HelpOp.Format By Console", "&4&k|&cHelpOp&4&k|&r &7Console: &f%message%");
        config.addDefault("Command.List.Format", Arrays.asList("&f&m---------&r&b[ &3&m--------------------&r &b]&f&m---------&r", "&8(&3!&8) &fThere are &b%list_size_supercore.list.default% &fplayers online!", "&7 - &fAdmins (&3%list_size_supercore.list.admin%&f): %list_supercore.list.admin%", "&7 - &fDonors (&3%list_size_supercore.list.donor%&f): %list_supercore.list.donor%", "&f&m---------&r&b[ &3&m--------------------&r &b]&f&m---------&r"));
        config.addDefault("Command.List.Name Color", "&b");
        config.addDefault("Command.List.Separator", "&f, ");
        config.addDefault("Command.Nick.Formatting Codes", "&8(&3!&8) &cYou are not allowed to use formatting codes in nickname!");
        config.addDefault("Command.Nick.Maximum Length", "&8(&3!&8) &cSpecified name exceeds the maximum length of &4%length% &ccharacters!");
        config.addDefault("Command.Seen.Is Offline", "&8(&3!&8) &b%target% &fhas been &coffline &ffor %time%.");
        config.addDefault("Command.Seen.Is Online", "&8(&3!&8) &b%target% &fhas been &aonline &ffor %time%.\n &7- &fIP Address: &b%ipaddress%\n &7- &fLocation: &b%location%");
        config.addDefault("Command.Seen.Unknown Player", "&8(&3!&8) &b%target% &fhas never joined this server.");
        config.addDefault("Command.TpToggle.Own Teleportation Toggled", "&8(&3!&8) &fYour teleportation has been %status%&f!");
        config.addDefault("Command.TpToggle.Own Teleportation Toggled By", "&8(&3!&8) &fYour teleportation has been %status%&f by &b%sender%&f!");
        config.addDefault("Command.TpToggle.Others Teleportation Toggled", "&8(&3!&8) &b%target%&f's teleportation has been %status%&f!");
        config.addDefault("Command.WhoIs.Format", Arrays.asList("&f&m---------&r&b[ &3&m--------------------&r &b]&f&m---------&r", "&8(&3!&8) &fThere are informations about &b%playername%&f:", "&7 - &fIP Address: &3%ipaddress%", "&7 - &fLocation: &3%location%", "&7 - &fGamemode: &3%gamemode%", "&7 - &fHealth: &3%health%", "&7 - &fHunger: &3%hunger%", "&7 - &fGod mode: &3%godmode%", "&7 - &fFlying: &3%flying%", "&f&m---------&r&b[ &3&m--------------------&r &b]&f&m---------&r"));
        config.addDefault("General.Action Interrupted", "&8(&3!&8) &cThe action has been interrupted!");
        config.addDefault("General.Already Using Language", "&8(&3!&8) &fYou are already using this language.");
        config.addDefault("General.Available Languages.Syntax", "&8(&3!&8) &fAvailable languages are: ");
        config.addDefault("General.Available Languages.Separator", "&f, ");
        config.addDefault("General.Available Languages.Item Color", "&b");
        config.addDefault("General.Gamemode.Adventure", "adventure");
        config.addDefault("General.Gamemode.Creative", "creative");
        config.addDefault("General.Gamemode.Spectator", "spectator");
        config.addDefault("General.Gamemode.Survival", "survival");
        config.addDefault("General.Invalid Command Syntax", "&8(&3!&8) &cInvalid Syntax or you have no permission!\n&8(&3!&8) &fThe valid syntax is: &b%syntax%");
        config.addDefault("General.Language Changed", "&8(&3!&8) &fYou have set your language to &b%language%&f!");
        config.addDefault("General.Must Be Online", "&8(&3!&8) &cSpecified player must be online!");
        config.addDefault("General.Must Be Player", "&8(&3!&8) &cYou must be a player to do this!");
        config.addDefault("General.Nickname Changed", "&8(&3!&8) &fYour nickname has been set to &b%nickname%&f!");
        config.addDefault("General.Nickname Changed By", "&8(&3!&8) &fYour nickname has been set to &b%nickname%&f by &3%sender%&f!");
        config.addDefault("General.No Permission", "&8(&3!&8) &cYou need the &4%permission% &cpermission to do that!");
        config.addDefault("General.Others Nickname Changed", "&8(&3!&8) &fYou have set &b%target%&f's nickname to &3%nickname%&f!");
        config.addDefault("General.Repeated Message", "&8(&3!&8) &fYou can not write &cthe same message&f!");
        config.addDefault("General.Word Day", "day");
        config.addDefault("General.Word Days", "days");
        config.addDefault("General.Word Disabled", "&cdisabled");
        config.addDefault("General.Word Enabled", "&aenabled");
        config.addDefault("General.Word Hour", "hour");
        config.addDefault("General.Word Hours", "hours");
        config.addDefault("General.Word Minute", "minute");
        config.addDefault("General.Word Minutes", "minutes");
        config.addDefault("General.Word Month", "month");
        config.addDefault("General.Word Months", "months");
        config.addDefault("General.Word No", "&cno");
        config.addDefault("General.Word Second", "second");
        config.addDefault("General.Word Seconds", "seconds");
        config.addDefault("General.Word Year", "year");
        config.addDefault("General.Word Years", "years");
        config.addDefault("General.Word Yes", "&ayes");
        config.addDefault("On Join.Join Message", "&8(&3!&8) &a%playername% &fjoined the game!");
        config.addDefault("On Quit.Quit Message", "&8(&3!&8) &c%playername% &fleft the game!");
        config.addDefault("Teleport.Console Can Not Teleport", "&8(&3!&8) &fConsole &ccan not &fbe teleported!");
        config.addDefault("Teleport.His Teleport Is Disabled", "&8(&3!&8) &b%target% &fhas &cdisabled &fteleportation!");
        config.addDefault("Teleport.Teleported To Coords", "&8(&3!&8) &fYou have been teleported to &b%x% %y% %z%&f!");
        config.addDefault("Teleport.Teleported To Player", "&8(&3!&8) &fYou have been teleported to &b%target%&f!");
        config.addDefault("Teleport.Teleported Player To Coords", "&8(&3!&8) &fYou have teleported &b%target% &fto &b%x% %y% %z%&f!");
        config.addDefault("Teleport.Teleported Player To Player", "&8(&3!&8) &fYou have teleported &b%target1% &fto &b%target2%&f!");
        config.addDefault("Update Checker.Available", "&8(&3!&8) &fThere is a new version of &bSuperCore &favailable!\n&8(&3!&8) &fDownload link: &b%link%");
        config.addDefault("Update Checker.Not Available", "&8(&3!&8) &fThere's no update available for &bSuperCore&f.");
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
        config.set("Command.Cooldown", "&8(&3!&8) &fYou must wait &b%cooldown% seconds &fto use the command again!");
        config.set("Command.DonorChat.Format", "&3[&bDONOR&3] %luckperms_prefix_element_highest% &7%playername%: &f%message%");
        config.set("Command.Fly.Own Flight Mode Changed", "&8(&3!&8) &fYour flight mode has been %status%&f!");
        config.set("Command.Fly.Own Flight Mode Changed By", "&8(&3!&8) &fYour flight mode has been %status%&f by &b%sender%&f!");
        config.set("Command.Fly.Others Flight Mode Changed", "&8(&3!&8) &b%target%&f's flight mode has been %status%&f!");
        config.set("Command.Gamemode.Own Gamemode Changed", "&8(&3!&8) &fYour gamemode has been set to &b%gamemode%&f!");
        config.set("Command.Gamemode.Others Gamemode Changed", "&8(&3!&8) &fYou set &b%playername%&f's gamemode to &3%gamemode%&f!");
        config.set("Command.God.Own God Mode Changed", "&8(&3!&8) &fYour god mode has been %status%&f!");
        config.set("Command.God.Own God Mode Changed By", "&8(&3!&8) &fYour god mode has been %status%&f by &b%sender%&f!");
        config.set("Command.God.Others God Mode Changed", "&8(&3!&8) &b%target%&f's god mode has been %status%&f!");
        config.set("Command.Heal.Self-Healed", "&8(&3!&8) &fYou have successfully healed yourself&f!");
        config.set("Command.Heal.Healed By", "&8(&3!&8) &fYou have been healed by &b%sender%&f!");
        config.set("Command.Heal.Healed Others", "&8(&3!&8) &fYou have successfully healed &b%target%&f!");
        config.set("Command.HelpOp.Format", "&4&k|&cHelpOp&4&k|&r &7%playername%: &f%message%");
        config.set("Command.List.Format", Arrays.asList("&f&m---------&r&b[ &3&m--------------------&r &b]&f&m---------&r", "&8(&3!&8) &fThere are &b%list_size_supercore.list.default% &fplayers online!", "&7 - &fAdmins (&3%list_size_supercore.list.admin%&f): %list_supercore.list.admin%", "&7 - &fDonors (&3%list_size_supercore.list.donor%&f): %list_supercore.list.donor%", "&f&m---------&r&b[ &3&m--------------------&r &b]&f&m---------&r"));
        config.set("Command.List.Name Color", "&b");
        config.set("Command.List.Separator", "&f, ");
        config.set("Command.Nick.Formatting Codes", "&8(&3!&8) &cYou are not allowed to use formatting codes in nickname!");
        config.set("Command.Nick.Maximum Length", "&8(&3!&8) &cSpecified name exceeds the maximum length of &4%length% &ccharacters!");
        config.set("Command.Seen.Is Offline", "&8(&3!&8) &b%target% &fhas been &coffline &ffor %time%.");
        config.set("Command.Seen.Is Online", "&8(&3!&8) &b%target% &fhas been &aonline &ffor %time%.\n &7- &fIP Address: &b%ipaddress%\n &7- &fLocation: &b%location%");
        config.set("Command.Seen.Unknown Player", "&8(&3!&8) &b%target% &fhas never joined this server.");
        config.set("Command.WhoIs.Format", Arrays.asList("&f&m---------&r&b[ &3&m--------------------&r &b]&f&m---------&r", "&8(&3!&8) &fThere are informations about &b%playername%&f:", "&7 - &fIP Address: &3%ipaddress%", "&7 - &fLocation: &3%location%", "&7 - &fGamemode: &3%gamemode%", "&7 - &fHealth: &3%health%", "&7 - &fHunger: &3%hunger%", "&7 - &fGod mode: &3%godmode%", "&7 - &fFlying: &3%flying%", "&f&m---------&r&b[ &3&m--------------------&r &b]&f&m---------&r"));
        config.set("General.Already Using Language", "&8(&3!&8) &fYou are already using this language.");
        config.set("General.Available Languages.Syntax", "&8(&3!&8) &fAvailable languages are: ");
        config.set("General.Available Languages.Separator", "&f, ");
        config.set("General.Available Languages.Item Color", "&b");
        config.set("General.Gamemode.Adventure", "adventure");
        config.set("General.Gamemode.Creative", "creative");
        config.set("General.Gamemode.Spectator", "spectator");
        config.set("General.Gamemode.Survival", "Survival");
        config.set("General.Invalid Command Syntax", "&8(&3!&8) &cInvalid Syntax or you have no permission!\n&8(&3!&8) &fThe valid syntax is: &b%syntax%");
        config.set("General.Language Changed", "&8(&3!&8) &fYou have set your language to &b%language%&f!");
        config.set("General.Must Be Online", "&8(&3!&8) &cSpecified player must be online!");
        config.set("General.Must Be Player", "&8(&3!&8) &cYou must be a player to do this!");
        config.set("General.Nickname Changed", "&8(&3!&8) &fYour nickname has been set to &b%nickname%&f!");
        config.set("General.No Permission", "&8(&3!&8) &cYou need the &4%permission% &cpermission to do that!");
        config.set("General.Repeated Message", "&8(&3!&8) &fYou can not write &cthe same message&f!");
        config.set("General.Word Day", "day");
        config.set("General.Word Days", "days");
        config.set("General.Word Disabled", "&cdisabled");
        config.set("General.Word Enabled", "&aenabled");
        config.set("General.Word Hour", "hour");
        config.set("General.Word Hours", "hours");
        config.set("General.Word Minute", "minute");
        config.set("General.Word Minutes", "minutes");
        config.set("General.Word Month", "month");
        config.set("General.Word Months", "months");
        config.set("General.Word No", "&cno");
        config.set("General.Word Second", "second");
        config.set("General.Word Seconds", "seconds");
        config.set("General.Word Year", "year");
        config.set("General.Word Years", "years");
        config.set("General.Word Yes", "&ayes");
        config.set("On Join.Join Message", "&8(&3!&8) &a%playername% &fjoined the game!");
        config.set("On Quit.Quit Message", "&8(&3!&8) &c%playername% &fleft the game!");
        config.set("Update Checker.Available", "&8(&3!&8) &fThere is a new version of &bSuperCore &favailable!\n&8(&3!&8) &fDownload link: &b%link%");
        config.set("Update Checker.Not Available", "&8(&3!&8) &fThere's no update available for &bSuperCore&f.");

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
