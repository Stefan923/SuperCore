package me.Stefan923.SuperCoreLite.Commands.Type;

import me.Stefan923.SuperCoreLite.Commands.AbstractCommand;
import me.Stefan923.SuperCoreLite.Main;
import me.Stefan923.SuperCoreLite.Utils.MessageUtils;
import me.Stefan923.SuperCoreLite.Utils.User;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.List;

public class CommandGod extends AbstractCommand implements MessageUtils {

    public CommandGod() { super(true, true, "god"); }

    @Override
    protected ReturnType runCommand(Main instance, CommandSender sender, String... args) {
        Player senderPlayer = (Player) sender;
        User senderUser = instance.getUser(senderPlayer);

        FileConfiguration senderLanguage = instance.getLanguageManager(senderUser.getLanguage()).getConfig();

        int length = args.length;

        if (length == 0 || args[0].equalsIgnoreCase(senderPlayer.getName())) {
            boolean godMode = !senderUser.getGod();
            senderUser.setGod(godMode);
            senderPlayer.sendMessage(formatAll(senderLanguage.getString("Command.God.Own God Mode Changed")
                    .replace("%status%", godMode ? senderLanguage.getString("General.Word Enabled") : senderLanguage.getString("General.Word Disabled"))));
            return ReturnType.SUCCESS;
        }

        Player targetPlayer = Bukkit.getPlayer(args[0]);
        if (targetPlayer == null) {
            senderPlayer.sendMessage(formatAll(senderLanguage.getString("General.Must Be Online")));
            return ReturnType.FAILURE;
        }

        User targetUser = instance.getUser(targetPlayer);
        FileConfiguration targetLanguage = instance.getLanguageManager(targetUser.getLanguage()).getConfig();
        boolean godMode = !targetUser.getGod();
        senderUser.setGod(godMode);
        senderPlayer.sendMessage(formatAll(senderLanguage.getString("Command.God.Others God Mode Changed")
                .replace("%status%", godMode ? senderLanguage.getString("General.Word Enabled") : senderLanguage.getString("General.Word Disabled"))
                .replace("%target%", targetPlayer.getName())));
        targetPlayer.sendMessage(formatAll(targetLanguage.getString("Command.God.Own God Mode Changed By")
                .replace("%status%", godMode ? targetLanguage.getString("General.Word Enabled") : targetLanguage.getString("General.Word Disabled"))
                .replace("%sender%", senderPlayer.getName())));
        return ReturnType.SUCCESS;
    }

    @Override
    protected List<String> onTab(Main instance, CommandSender sender, String... args) {
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
