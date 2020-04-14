package me.Stefan923.SuperCore.Commands.Type;

import me.Stefan923.SuperCore.Commands.AbstractCommand;
import me.Stefan923.SuperCore.Commands.Exceptions.MissingPermissionException;
import me.Stefan923.SuperCore.SuperCore;
import me.Stefan923.SuperCore.Utils.MessageUtils;
import me.Stefan923.SuperCore.Utils.PlayerUtils;
import me.Stefan923.SuperCore.Utils.User;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.List;

public class CommandNick extends AbstractCommand implements MessageUtils, PlayerUtils {

    public CommandNick() {
        super(true, true, "nick");
    }

    @Override
    protected ReturnType runCommand(SuperCore instance, CommandSender sender, String... args) throws MissingPermissionException {
        FileConfiguration senderLanguage = getLanguageConfig(instance, sender);
        FileConfiguration settings = instance.getSettingsManager().getConfig();

        if (args.length == 1 || (args.length == 2 && args[1].equalsIgnoreCase(sender.getName()))) {
            if (!(sender instanceof Player)) {
                sender.sendMessage(formatAll(senderLanguage.getString("General.Must Be Player")));
                return ReturnType.FAILURE;
            }

            Player senderPlayer = (Player) sender;
            User senderUser = instance.getUser(senderPlayer);

            int maxLength = settings.getInt("Nick.Maximum Length");
            if (maxLength < removeFormat(args[0]).length()) {
                senderPlayer.sendMessage(formatAll(senderLanguage.getString("Command.Nick.Maximum Length").replace("%length%", String.valueOf(maxLength))));
                return ReturnType.FAILURE;
            }

            if (!senderPlayer.hasPermission("supercore.nick.format")) {
                senderPlayer.sendMessage(formatAll(senderLanguage.getString("Command.Nick.Formatting Codes")));
                return ReturnType.FAILURE;
            }

            senderUser.setNickname(args[0]);
            senderPlayer.sendMessage(formatAll(senderLanguage.getString("General.Nickname Changed").replace("%nickname%", args[0])));
            return ReturnType.SUCCESS;
        }

        if (!sender.hasPermission("supercore.nick.others")) {
            throw new MissingPermissionException("supercore.nick.others");
        }

        Player targetPlayer = Bukkit.getPlayer(args[1]);
        if (targetPlayer == null) {
            sender.sendMessage(formatAll(senderLanguage.getString("General.Must Be Online")));
            return ReturnType.FAILURE;
        }
        User targetUser = instance.getUser(targetPlayer);
        FileConfiguration targetLanguage = getLanguageConfig(instance, targetPlayer);

        int maxLength = settings.getInt("Nick.Maximum Length");
        if (maxLength < removeFormat(args[0]).length()) {
            sender.sendMessage(formatAll(senderLanguage.getString("Command.Nick.Maximum Length").replace("%length%", String.valueOf(maxLength))));
            return ReturnType.FAILURE;
        }

        if (!targetPlayer.hasPermission("supercore.nick.format")) {
            sender.sendMessage(formatAll(senderLanguage.getString("Command.Nick.Formatting Codes")));
            return ReturnType.FAILURE;
        }

        targetUser.setNickname(args[0]);
        sender.sendMessage(formatAll(senderLanguage.getString("General.Others Nickname Changed").replace("%target%", targetPlayer.getName()).replace("%nickname%", args[0])));
        targetPlayer.sendMessage(formatAll(targetLanguage.getString("General.Nickname Changed By").replace("%sender%", sender.getName()).replace("%nickname%", args[0])));
        return ReturnType.SUCCESS;
    }

    @Override
    protected List<String> onTab(SuperCore instance, CommandSender sender, String... args) {
        return null;
    }

    @Override
    public String getPermissionNode() {
        return "supercore.nick";
    }

    @Override
    public String getSyntax() {
        return "/nick <nickname> [player]";
    }

    @Override
    public String getDescription() {
        return "Change own or others nickname.";
    }

}
