package me.Stefan923.SuperCore.Commands.Type;

import me.Stefan923.SuperCore.Commands.AbstractCommand;
import me.Stefan923.SuperCore.SuperCore;
import me.Stefan923.SuperCore.Utils.MessageUtils;
import me.Stefan923.SuperCore.Utils.PlayerUtils;
import me.Stefan923.SuperCore.Utils.User;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CommandFly extends AbstractCommand implements MessageUtils, PlayerUtils {

    public CommandFly() {
        super(true, true, "fly");
    }

    @Override
    protected AbstractCommand.ReturnType runCommand(SuperCore instance, CommandSender sender, String... args) {
        Player senderPlayer = (Player) sender;
        User user = instance.getUser(senderPlayer);

        FileConfiguration senderLanguage = instance.getLanguageManager(user.getLanguage()).getConfig();

        int length = args.length;

        if (length == 0 || args[0].equalsIgnoreCase(senderPlayer.getName())) {
            boolean isAllowed = !senderPlayer.getAllowFlight();
            senderPlayer.setAllowFlight(isAllowed);
            senderPlayer.sendMessage(formatAll(senderLanguage.getString("Command.Fly.Own Flight Mode Changed")
                    .replace("%status%", isAllowed ? senderLanguage.getString("General.Word Enabled") : senderLanguage.getString("General.Word Disabled"))));
            return ReturnType.SUCCESS;
        }

        Player targetPlayer = Bukkit.getPlayer(args[0]);
        if (targetPlayer == null) {
            senderPlayer.sendMessage(formatAll(senderLanguage.getString("General.Must Be Online")));
            return ReturnType.FAILURE;
        }
        User targetUser = instance.getUser(targetPlayer);
        FileConfiguration targetLanguage = instance.getLanguageManager(targetUser.getLanguage()).getConfig();
        boolean isAllowed = !targetPlayer.getAllowFlight();
        targetPlayer.setAllowFlight(isAllowed);
        senderPlayer.sendMessage(formatAll(senderLanguage.getString("Command.Fly.Others Flight Mode Changed")
                .replace("%status%", isAllowed ? senderLanguage.getString("General.Word Enabled") : senderLanguage.getString("General.Word Disabled"))
                .replace("%target%", targetPlayer.getName())));
        targetPlayer.sendMessage(formatAll(targetLanguage.getString("Command.Fly.Own Flight Mode Changed By")
                .replace("%status%", isAllowed ? targetLanguage.getString("General.Word Enabled") : targetLanguage.getString("General.Word Disabled"))
                .replace("%sender%", senderPlayer.getName())));
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