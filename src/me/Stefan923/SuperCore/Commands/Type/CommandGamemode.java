package me.Stefan923.SuperCore.Commands.Type;

import me.Stefan923.SuperCore.Commands.AbstractCommand;
import me.Stefan923.SuperCore.Commands.Exceptions.MissingPermissionException;
import me.Stefan923.SuperCore.SuperCore;
import me.Stefan923.SuperCore.Utils.MessageUtils;
import me.Stefan923.SuperCore.Utils.User;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class CommandGamemode extends AbstractCommand implements MessageUtils {

    public CommandGamemode() {
        super(true, true, "gamemode");
    }

    @Override
    protected ReturnType runCommand(SuperCore instance, CommandSender sender, String... args) throws MissingPermissionException {
        Player senderPlayer = (Player) sender;
        User user = instance.getUser(senderPlayer);

        FileConfiguration settings = instance.getSettingsManager().getConfig();
        FileConfiguration languageConfig = instance.getLanguageManager(user.getLanguage()).getConfig();

        int length = args.length;

        if (length == 1) {
            if (args[0].equalsIgnoreCase("survival") || args[0].equalsIgnoreCase("0")) {
                if (!senderPlayer.hasPermission("supercore.gamemode.survival")) {
                    throw new MissingPermissionException("supercore.gamemode.survival");
                }
                senderPlayer.setGameMode(GameMode.SURVIVAL);
                senderPlayer.sendMessage(formatAll(languageConfig.getString("Command.Gamemode.Own Gamemode Changed").replace("%gamemode%", languageConfig.getString("General.Gamemode.Survival"))));
                return ReturnType.SUCCESS;
            }
            if (args[0].equalsIgnoreCase("creative") || args[0].equalsIgnoreCase("1")) {
                if (!senderPlayer.hasPermission("supercore.gamemode.creative")) {
                    throw new MissingPermissionException("supercore.gamemode.creative");
                }
                senderPlayer.setGameMode(GameMode.CREATIVE);
                senderPlayer.sendMessage(formatAll(languageConfig.getString("Command.Gamemode.Own Gamemode Changed").replace("%gamemode%", languageConfig.getString("General.Gamemode.Creative"))));
                return ReturnType.SUCCESS;
            }
            if (args[0].equalsIgnoreCase("adventure") || args[0].equalsIgnoreCase("2")) {
                if (!senderPlayer.hasPermission("supercore.gamemode.adventure")) {
                    throw new MissingPermissionException("supercore.gamemode.adventure");
                }
                senderPlayer.setGameMode(GameMode.ADVENTURE);
                senderPlayer.sendMessage(formatAll(languageConfig.getString("Command.Gamemode.Own Gamemode Changed").replace("%gamemode%", languageConfig.getString("General.Gamemode.Adventure"))));
                return ReturnType.SUCCESS;
            }
            if (args[0].equalsIgnoreCase("spectator") || args[0].equalsIgnoreCase("3")) {
                if (!senderPlayer.hasPermission("supercore.gamemode.spectator")) {
                    throw new MissingPermissionException("supercore.gamemode.spectator");
                }
                senderPlayer.setGameMode(GameMode.SPECTATOR);
                senderPlayer.sendMessage(formatAll(languageConfig.getString("Command.Gamemode.Own Gamemode Changed").replace("%gamemode%", languageConfig.getString("General.Gamemode.Spectator"))));
                return ReturnType.SUCCESS;
            }
            return ReturnType.SYNTAX_ERROR;
        }

        if (length == 2) {
            if (!senderPlayer.hasPermission("supercore.gamemode.others")) {
                throw new MissingPermissionException("supercore.gamemode.others");
            }
            Player targetPlayer = Bukkit.getPlayer(args[0]);
            if (targetPlayer == null) {
                senderPlayer.sendMessage(formatAll(languageConfig.getString("General.Must Be Online")));
                return ReturnType.FAILURE;
            }
            if (args[1].equalsIgnoreCase("survival") || args[1].equalsIgnoreCase("0")) {
                if (!senderPlayer.hasPermission("supercore.gamemode.survival")) {
                    throw new MissingPermissionException("supercore.gamemode.survival");
                }
                targetPlayer.setGameMode(GameMode.SURVIVAL);
                if (!targetPlayer.getName().equalsIgnoreCase(sender.getName()))
                    senderPlayer.sendMessage(formatAll(languageConfig.getString("Command.Gamemode.Others Gamemode Changed").replace("%playername%", targetPlayer.getName()).replace("%gamemode%", languageConfig.getString("General.Gamemode.Survival"))));
                targetPlayer.sendMessage(formatAll(languageConfig.getString("Command.Gamemode.Own Gamemode Changed").replace("%gamemode%", languageConfig.getString("General.Gamemode.Survival"))));
                return ReturnType.SUCCESS;
            }
            if (args[1].equalsIgnoreCase("creative") || args[1].equalsIgnoreCase("1")) {
                if (!senderPlayer.hasPermission("supercore.gamemode.creative")) {
                    throw new MissingPermissionException("supercore.gamemode.creative");
                }
                targetPlayer.setGameMode(GameMode.CREATIVE);
                if (!targetPlayer.getName().equalsIgnoreCase(sender.getName()))
                    senderPlayer.sendMessage(formatAll(languageConfig.getString("Command.Gamemode.Others Gamemode Changed").replace("%playername%", targetPlayer.getName()).replace("%gamemode%", languageConfig.getString("General.Gamemode.Creative"))));
                targetPlayer.sendMessage(formatAll(languageConfig.getString("Command.Gamemode.Own Gamemode Changed").replace("%gamemode%", languageConfig.getString("General.Gamemode.Creative"))));
                return ReturnType.SUCCESS;
            }
            if (args[1].equalsIgnoreCase("adventure") || args[1].equalsIgnoreCase("2")) {
                if (!senderPlayer.hasPermission("supercore.gamemode.adventure")) {
                    throw new MissingPermissionException("supercore.gamemode.adventure");
                }
                targetPlayer.setGameMode(GameMode.ADVENTURE);
                if (!targetPlayer.getName().equalsIgnoreCase(sender.getName()))
                    senderPlayer.sendMessage(formatAll(languageConfig.getString("Command.Gamemode.Others Gamemode Changed").replace("%playername%", targetPlayer.getName()).replace("%gamemode%", languageConfig.getString("General.Gamemode.Adventure"))));
                targetPlayer.sendMessage(formatAll(languageConfig.getString("Command.Gamemode.Own Gamemode Changed").replace("%gamemode%", languageConfig.getString("General.Gamemode.Adventure"))));
                return ReturnType.SUCCESS;
            }
            if (args[1].equalsIgnoreCase("spectator") || args[1].equalsIgnoreCase("3")) {
                if (!senderPlayer.hasPermission("supercore.gamemode.spectator")) {
                    throw new MissingPermissionException("supercore.gamemode.spectator");
                }
                targetPlayer.setGameMode(GameMode.SPECTATOR);
                if (!targetPlayer.getName().equalsIgnoreCase(sender.getName()))
                    senderPlayer.sendMessage(formatAll(languageConfig.getString("Command.Gamemode.Others Gamemode Changed").replace("%playername%", targetPlayer.getName()).replace("%gamemode%", languageConfig.getString("General.Gamemode.Spectator"))));
                targetPlayer.sendMessage(formatAll(languageConfig.getString("Command.Gamemode.Own Gamemode Changed").replace("%gamemode%", languageConfig.getString("General.Gamemode.Spectator"))));
                return ReturnType.SUCCESS;
            }
        }

        return ReturnType.SYNTAX_ERROR;
    }

    @Override
    protected List<String> onTab(SuperCore instance, CommandSender sender, String... args) {
        List<String> list = new ArrayList<>();
        if (sender.hasPermission("supercore.gamemode.survival"))
            list.add("survival");
        if (sender.hasPermission("supercore.gamemode.creative"))
            list.add("creative");
        if (sender.hasPermission("supercore.gamemode.adventure"))
            list.add("adventure");
        if (sender.hasPermission("supercore.gamemode.spectator"))
            list.add("spectator");
        return list;
    }

    @Override
    public String getPermissionNode() {
        return "supercore.gamemode";
    }

    @Override
    public String getSyntax() {
        return "/gamemode <player> <type>";
    }

    @Override
    public String getDescription() {
        return "Change your gamemode.";
    }

}
