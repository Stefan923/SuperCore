package me.stefan923.supercore.commands.type;

import me.stefan923.supercore.SuperCore;
import me.stefan923.supercore.utils.MessageUtils;
import me.stefan923.supercore.utils.PlayerUtils;
import me.stefan923.supercore.utils.User;
import me.stefan923.supercore.commands.AbstractCommand;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class CommandSeen extends AbstractCommand implements MessageUtils, PlayerUtils {

    public CommandSeen() {
        super(false, true, "seen");
    }

    @Override
    protected ReturnType runCommand(SuperCore instance, CommandSender sender, String... args) {
        FileConfiguration senderLanguage;

        if (sender instanceof Player)
            senderLanguage = instance.getLanguageManager(instance.getUser((Player) sender).getLanguage()).getConfig();
        else
            senderLanguage = instance.getLanguageManager(instance.getSettingsManager().getConfig().getString("Languages.Default Language")).getConfig();

        int length = args.length;

        if (length == 1) {
            Player targetPlayer = Bukkit.getPlayer(args[0]);
            if (targetPlayer != null && targetPlayer.isOnline()) {
                User targetUser = instance.getUser(targetPlayer);
                long time = System.currentTimeMillis() - targetUser.getJoinTime();
                sender.sendMessage(formatAll(senderLanguage.getString("Command.Seen.Is Online")
                        .replace("%time%", convertTime(time, senderLanguage))
                        .replace("%target%", targetPlayer.getName())
                        .replace("%ipaddress%", targetPlayer.getAddress().getHostName())
                        .replace("%location%", locationToString(targetPlayer.getLocation()))));
                return AbstractCommand.ReturnType.SUCCESS;
            }
            long time = getLastOnline(args[0]);
            if (time == -1) {
                sender.sendMessage(formatAll(senderLanguage.getString("Command.Seen.Unknown Player")
                        .replace("%target%", args[0])));
                return ReturnType.SUCCESS;
            }
            sender.sendMessage(formatAll(senderLanguage.getString("Command.Seen.Is Offline")
                    .replace("%time%", convertTime(System.currentTimeMillis() - time, senderLanguage))
                    .replace("%target%", args[0])));
            return ReturnType.SUCCESS;
        }

        return AbstractCommand.ReturnType.SYNTAX_ERROR;
    }

    @Override
    protected List<String> onTab(SuperCore instance, CommandSender sender, String... args) {
        List<String> tabStrings = new ArrayList<>();
        if (sender.hasPermission("supercore.seen") && args.length == 1)
            if (!args[0].equals(""))
                onlinePlayers(sender).stream().filter(player -> player.getName().toLowerCase().startsWith(args[0].toLowerCase())).forEach(player -> tabStrings.add(player.getName()));
            else
                onlinePlayers(sender).forEach(player -> tabStrings.add(player.getName()));
        return tabStrings;
    }

    @Override
    public String getPermissionNode() {
        return "supercore.seen";
    }

    @Override
    public String getSyntax() {
        return "/seen <player>";
    }

    @Override
    public String getDescription() {
        return "Shows player online/offline time.";
    }

}
