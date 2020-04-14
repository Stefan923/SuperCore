package me.Stefan923.SuperCore.Commands.Type;

import me.Stefan923.SuperCore.Commands.AbstractCommand;
import me.Stefan923.SuperCore.Commands.Exceptions.MissingPermissionException;
import me.Stefan923.SuperCore.SuperCore;
import me.Stefan923.SuperCore.Utils.MessageUtils;
import me.Stefan923.SuperCore.Utils.User;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.List;

public class CommandTpToggle extends AbstractCommand implements MessageUtils {

    public CommandTpToggle() { super(false, true, "tptoggle"); }

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

            boolean tpToggle = !senderUser.getTeleport();
            senderUser.setTeleport(tpToggle);
            senderPlayer.sendMessage(formatAll(senderLanguage.getString("Command.TpToggle.Own Teleportation Toggled")
                    .replace("%status%", tpToggle ? senderLanguage.getString("General.Word Enabled") : senderLanguage.getString("General.Word Disabled"))));
            return ReturnType.SUCCESS;
        }

        if (sender.hasPermission("supercore.tptoggle.others") && !isConsole) {
            throw new MissingPermissionException("supercore.tptoggle.others");
        }

        Player targetPlayer = Bukkit.getPlayer(args[0]);
        if (targetPlayer == null) {
            sender.sendMessage(formatAll(senderLanguage.getString("General.Must Be Online")));
            return ReturnType.FAILURE;
        }

        User targetUser = instance.getUser(targetPlayer);
        FileConfiguration targetLanguage = instance.getLanguageManager(targetUser.getLanguage()).getConfig();
        boolean tpToggle = !targetUser.getTeleport();
        targetUser.setTeleport(tpToggle);
        sender.sendMessage(formatAll(senderLanguage.getString("Command.TpToggle.Others Teleportation Toggled")
                .replace("%status%", tpToggle ? senderLanguage.getString("General.Word Enabled") : senderLanguage.getString("General.Word Disabled"))
                .replace("%target%", targetPlayer.getName())));
        targetPlayer.sendMessage(formatAll(targetLanguage.getString("Command.God.Own Teleportation Toggled By")
                .replace("%status%", tpToggle ? targetLanguage.getString("General.Word Enabled") : targetLanguage.getString("General.Word Disabled"))
                .replace("%sender%", sender.getName())));
        return ReturnType.SUCCESS;
    }

    @Override
    protected List<String> onTab(SuperCore instance, CommandSender sender, String... args) {
        return null;
    }

    @Override
    public String getPermissionNode() {
        return "supercore.tptoggle";
    }

    @Override
    public String getSyntax() {
        return "/tptoggle [player]";
    }

    @Override
    public String getDescription() {
        return "Enables or disables teleportation.";
    }
}
