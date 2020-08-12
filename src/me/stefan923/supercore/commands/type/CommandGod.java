package me.stefan923.supercore.commands.type;

import me.stefan923.supercore.SuperCore;
import me.stefan923.supercore.commands.exceptions.MissingPermissionException;
import me.stefan923.supercore.utils.MessageUtils;
import me.stefan923.supercore.utils.User;
import me.stefan923.supercore.commands.AbstractCommand;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.List;

public class CommandGod extends AbstractCommand implements MessageUtils {

    public CommandGod() { super(false, true, "god"); }

    @Override
    protected ReturnType runCommand(SuperCore instance, CommandSender sender, String... args) throws MissingPermissionException {
        boolean isConsole = !(sender instanceof Player);
        FileConfiguration senderLanguage = getLanguageConfig(instance, sender);

        int length = args.length;

        if (length == 0 || args[0].equalsIgnoreCase(sender.getName())) {
            if (isConsole) {
                sender.sendMessage(formatAll(senderLanguage.getString("General.Must Be Player")));
                return ReturnType.FAILURE;
            }
            Player senderPlayer = (Player) sender;
            User senderUser = instance.getUser(senderPlayer);

            boolean godMode = !senderUser.getGod();
            senderUser.setGod(godMode);
            senderPlayer.sendMessage(formatAll(senderLanguage.getString("Command.God.Own God Mode Changed")
                    .replace("%status%", godMode ? senderLanguage.getString("General.Word Enabled") : senderLanguage.getString("General.Word Disabled"))));
            return ReturnType.SUCCESS;
        }

        if (!sender.hasPermission("supercore.god.others")) {
            throw new MissingPermissionException("supercore.god.others");
        }

        Player targetPlayer = Bukkit.getPlayer(args[0]);
        if (targetPlayer == null) {
            sender.sendMessage(formatAll(senderLanguage.getString("General.Must Be Online")));
            return ReturnType.FAILURE;
        }

        User targetUser = instance.getUser(targetPlayer);
        FileConfiguration targetLanguage = instance.getLanguageManager(targetUser.getLanguage()).getConfig();
        boolean godMode = !targetUser.getGod();
        targetUser.setGod(godMode);
        sender.sendMessage(formatAll(senderLanguage.getString("Command.God.Others God Mode Changed")
                .replace("%status%", godMode ? senderLanguage.getString("General.Word Enabled") : senderLanguage.getString("General.Word Disabled"))
                .replace("%target%", targetPlayer.getName())));
        targetPlayer.sendMessage(formatAll(targetLanguage.getString("Command.God.Own God Mode Changed By")
                .replace("%status%", godMode ? targetLanguage.getString("General.Word Enabled") : targetLanguage.getString("General.Word Disabled"))
                .replace("%sender%", sender.getName())));
        return ReturnType.SUCCESS;
    }

    @Override
    protected List<String> onTab(SuperCore instance, CommandSender sender, String... args) {
        return null;
    }

    @Override
    public String getPermissionNode() {
        return "supercore.god";
    }

    @Override
    public String getSyntax() {
        return "/god <player>";
    }

    @Override
    public String getDescription() {
        return "Enables or disables god mode.";
    }
}
