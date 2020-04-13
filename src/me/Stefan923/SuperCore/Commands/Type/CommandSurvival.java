package me.Stefan923.SuperCore.Commands.Type;

import me.Stefan923.SuperCore.Commands.AbstractCommand;
import me.Stefan923.SuperCore.Commands.Exceptions.MissingPermissionException;
import me.Stefan923.SuperCore.SuperCore;
import me.Stefan923.SuperCore.Utils.MessageUtils;
import me.Stefan923.SuperCore.Utils.PlayerUtils;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CommandSurvival extends AbstractCommand implements MessageUtils, PlayerUtils {

    public CommandSurvival() {
        super(false, true, "survival");
    }

    @Override
    protected ReturnType runCommand(SuperCore instance, CommandSender sender, String... args) throws MissingPermissionException {
        if (!sender.hasPermission("supercore.gamemode.survival")) {
            throw new MissingPermissionException("supercore.gamemode.survival");
        }

        FileConfiguration languageConfig = getLanguageManager(instance, sender).getConfig();

        int length = args.length;

        if (length == 0) {
            if (!(sender instanceof Player)) {
                sender.sendMessage(formatAll(languageConfig.getString("General.Must Be Player")));
                return ReturnType.FAILURE;
            }
            Player senderPlayer = (Player) sender;
            senderPlayer.setGameMode(GameMode.SURVIVAL);
            senderPlayer.sendMessage(formatAll(languageConfig.getString("Command.Gamemode.Own Gamemode Changed").replace("%gamemode%", languageConfig.getString("General.Gamemode.Survival"))));
            return ReturnType.SUCCESS;
        }

        if (length == 1) {
            if (!sender.hasPermission("supercore.gamemode.others")) {
                throw new MissingPermissionException("supercore.gamemode.others");
            }
            Player targetPlayer = Bukkit.getPlayer(args[0]);
            if (targetPlayer == null) {
                sender.sendMessage(formatAll(languageConfig.getString("General.Must Be Online")));
                return ReturnType.FAILURE;
            }
            targetPlayer.setGameMode(GameMode.SURVIVAL);
            if (!targetPlayer.getName().equalsIgnoreCase(sender.getName()))
                sender.sendMessage(formatAll(languageConfig.getString("Command.Gamemode.Others Gamemode Changed").replace("%playername%", targetPlayer.getName()).replace("%gamemode%", languageConfig.getString("General.Gamemode.Survival"))));
            targetPlayer.sendMessage(formatAll(languageConfig.getString("Command.Gamemode.Own Gamemode Changed").replace("%gamemode%", languageConfig.getString("General.Gamemode.Survival"))));
            return ReturnType.SUCCESS;
        }

        return ReturnType.SYNTAX_ERROR;
    }

    @Override
    protected List<String> onTab(SuperCore instance, CommandSender sender, String... args) {
        if (!(sender instanceof Player) || !sender.hasPermission("supercore.gamemode.others"))
            return null;
        Player senderPlayer = (Player) sender;
        List<String> list = new ArrayList<>();
        onlinePlayers(senderPlayer).forEach(onlinePlayer -> list.add(onlinePlayer.getName()));
        Collections.sort(list);
        return list;
    }

    @Override
    public String getPermissionNode() {
        return "supercore.gamemode";
    }

    @Override
    public String getSyntax() {
        return "/survival [player]";
    }

    @Override
    public String getDescription() {
        return "Change your gamemode to survival.";
    }

}
