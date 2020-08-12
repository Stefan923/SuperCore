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
import java.util.Collections;
import java.util.List;

public class CommandFly extends AbstractCommand implements MessageUtils, PlayerUtils {

    public CommandFly() {
        super(false, true, "fly");
    }

    @Override
    protected ReturnType runCommand(SuperCore instance, CommandSender sender, String... args) {
        FileConfiguration senderLanguage = getLanguageConfig(instance, sender);

        int length = args.length;

        if (length == 0 || args[0].equalsIgnoreCase(sender.getName())) {
            if (!(sender instanceof Player)) {
                sender.sendMessage(formatAll(senderLanguage.getString("General.Must Be Player")));
                return ReturnType.FAILURE;
            }
            Player senderPlayer = (Player) sender;
            boolean isAllowed = !senderPlayer.getAllowFlight();
            senderPlayer.setAllowFlight(isAllowed);
            sender.sendMessage(formatAll(senderLanguage.getString("Command.Fly.Own Flight Mode Changed")
                    .replace("%status%", isAllowed ? senderLanguage.getString("General.Word Enabled") : senderLanguage.getString("General.Word Disabled"))));
            return ReturnType.SUCCESS;
        }

        Player targetPlayer = Bukkit.getPlayer(args[0]);
        if (targetPlayer == null) {
            sender.sendMessage(formatAll(senderLanguage.getString("General.Must Be Online")));
            return ReturnType.FAILURE;
        }
        User targetUser = instance.getUser(targetPlayer);
        FileConfiguration targetLanguage = instance.getLanguageManager(targetUser.getLanguage()).getConfig();
        boolean isAllowed = !targetPlayer.getAllowFlight();
        targetPlayer.setAllowFlight(isAllowed);
        sender.sendMessage(formatAll(senderLanguage.getString("Command.Fly.Others Flight Mode Changed")
                .replace("%status%", isAllowed ? senderLanguage.getString("General.Word Enabled") : senderLanguage.getString("General.Word Disabled"))
                .replace("%target%", targetPlayer.getName())));
        targetPlayer.sendMessage(formatAll(targetLanguage.getString("Command.Fly.Own Flight Mode Changed By")
                .replace("%status%", isAllowed ? targetLanguage.getString("General.Word Enabled") : targetLanguage.getString("General.Word Disabled"))
                .replace("%sender%", sender.getName())));
        return ReturnType.SUCCESS;
    }

    @Override
    protected List<String> onTab(SuperCore instance, CommandSender sender, String... args) {
        if (!(sender instanceof Player) || !sender.hasPermission("supercore.fly"))
            return null;
        Player senderPlayer = (Player) sender;
        List<String> list = new ArrayList<>();
        onlinePlayers(senderPlayer).forEach(onlinePlayer -> list.add(onlinePlayer.getName()));
        Collections.sort(list);
        return list;
    }

    @Override
    public String getPermissionNode() {
        return "supercore.fly";
    }

    @Override
    public String getSyntax() {
        return "/fly <player>";
    }

    @Override
    public String getDescription() {
        return "Enables or disables flight.";
    }

}
