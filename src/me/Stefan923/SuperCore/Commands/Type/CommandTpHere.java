package me.Stefan923.SuperCore.Commands.Type;

import me.Stefan923.SuperCore.Commands.AbstractCommand;
import me.Stefan923.SuperCore.Commands.Exceptions.MustBeOnlineException;
import me.Stefan923.SuperCore.SuperCore;
import me.Stefan923.SuperCore.Utils.MessageUtils;
import me.Stefan923.SuperCore.Utils.PlayerUtils;
import me.Stefan923.SuperCore.Utils.User;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class CommandTpHere extends AbstractCommand implements MessageUtils, PlayerUtils {

    public CommandTpHere() {
        super(true, true, "tphere");
    }

    @Override
    protected ReturnType runCommand(SuperCore instance, CommandSender sender, String... args) throws MustBeOnlineException {
        if (args.length != 1) {
            return ReturnType.SYNTAX_ERROR;
        }

        Player senderPlayer = (Player) sender;
        FileConfiguration senderLanguage = getLanguageConfig(instance, senderPlayer);

        Player targetPlayer = Bukkit.getPlayer(args[0]);
        if (targetPlayer == null) {
            throw new MustBeOnlineException();
        }

        User targetUser = getUser(instance, targetPlayer);
        if (targetUser == null) {
            throw new MustBeOnlineException();
        }
        FileConfiguration targetLanguage = getLanguageConfig(instance, targetPlayer);

        if (!targetUser.getTeleport()) {
            senderPlayer.sendMessage(formatAll(senderLanguage.getString("Teleport.His Teleport Is Disabled").replace("%target%", targetPlayer.getName())));
            return ReturnType.FAILURE;
        }

        targetPlayer.teleport(senderPlayer);
        senderPlayer.sendMessage(formatAll(senderLanguage.getString("Teleport.Teleported Player To You").replace("%target%", targetPlayer.getName())));
        targetPlayer.sendMessage(formatAll(targetLanguage.getString("Teleport.Teleported By").replace("%sender%", senderPlayer.getName())));
        return ReturnType.SUCCESS;
    }

    @Override
    protected List<String> onTab(SuperCore instance, CommandSender sender, String... args) {
        List<String> tabStrings = new ArrayList<>();
        if (sender.hasPermission("supercore.tphere") && args.length == 1)
            onlinePlayers(sender).forEach(player -> {
                String name = player.getName();
                if (name.toLowerCase().startsWith(args[0].toLowerCase()))
                    tabStrings.add(name);
            });
        return tabStrings;
    }

    @Override
    public String getPermissionNode() {
        return "supercore.tphere";
    }

    @Override
    public String getSyntax() {
        return "/tphere <player>";
    }

    @Override
    public String getDescription() {
        return "Teleports mentioned player to the command sender.";
    }

}
