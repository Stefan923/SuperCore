package me.Stefan923.SuperCore.Commands.Type;

import me.Stefan923.SuperCore.Commands.AbstractCommand;
import me.Stefan923.SuperCore.Commands.Exceptions.MissingPermissionException;
import me.Stefan923.SuperCore.SuperCore;
import me.Stefan923.SuperCore.Utils.MessageUtils;
import me.Stefan923.SuperCore.Utils.PlayerUtils;
import me.Stefan923.SuperCore.Utils.User;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CommandGamemode extends AbstractCommand implements MessageUtils, PlayerUtils {

    public CommandGamemode() {
        super(false, true, "gamemode");
    }

    @Override
    protected ReturnType runCommand(SuperCore instance, CommandSender sender, String... args) throws MissingPermissionException {
        boolean isConsole = !(sender instanceof Player);
        FileConfiguration languageConfig = getLanguageConfig(instance, sender);

        int length = args.length;

        if (length == 1) {
            if (isConsole) {
                sender.sendMessage(formatAll(languageConfig.getString("General.Must Be Player")));
                return ReturnType.FAILURE;
            }
            Player senderPlayer = (Player) sender;

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
            if (!sender.hasPermission("supercore.gamemode.others") && !isConsole) {
                throw new MissingPermissionException("supercore.gamemode.others");
            }
            Player targetPlayer = Bukkit.getPlayer(args[1]);
            if (targetPlayer == null) {
                sender.sendMessage(formatAll(languageConfig.getString("General.Must Be Online")));
                return ReturnType.FAILURE;
            }
            if (args[0].equalsIgnoreCase("survival") || args[0].equalsIgnoreCase("0")) {
                if (!sender.hasPermission("supercore.gamemode.survival") && !isConsole) {
                    throw new MissingPermissionException("supercore.gamemode.survival");
                }
                targetPlayer.setGameMode(GameMode.SURVIVAL);
                if (!targetPlayer.getName().equalsIgnoreCase(sender.getName()))
                    sender.sendMessage(formatAll(languageConfig.getString("Command.Gamemode.Others Gamemode Changed").replace("%playername%", targetPlayer.getName()).replace("%gamemode%", languageConfig.getString("General.Gamemode.Survival"))));
                targetPlayer.sendMessage(formatAll(languageConfig.getString("Command.Gamemode.Own Gamemode Changed").replace("%gamemode%", languageConfig.getString("General.Gamemode.Survival"))));
                return ReturnType.SUCCESS;
            }
            if (args[0].equalsIgnoreCase("creative") || args[0].equalsIgnoreCase("1")) {
                if (!sender.hasPermission("supercore.gamemode.creative") && !isConsole) {
                    throw new MissingPermissionException("supercore.gamemode.creative");
                }
                targetPlayer.setGameMode(GameMode.CREATIVE);
                if (!targetPlayer.getName().equalsIgnoreCase(sender.getName()))
                    sender.sendMessage(formatAll(languageConfig.getString("Command.Gamemode.Others Gamemode Changed").replace("%playername%", targetPlayer.getName()).replace("%gamemode%", languageConfig.getString("General.Gamemode.Creative"))));
                targetPlayer.sendMessage(formatAll(languageConfig.getString("Command.Gamemode.Own Gamemode Changed").replace("%gamemode%", languageConfig.getString("General.Gamemode.Creative"))));
                return ReturnType.SUCCESS;
            }
            if (args[0].equalsIgnoreCase("adventure") || args[0].equalsIgnoreCase("2")) {
                if (!sender.hasPermission("supercore.gamemode.adventure") && !isConsole) {
                    throw new MissingPermissionException("supercore.gamemode.adventure");
                }
                targetPlayer.setGameMode(GameMode.ADVENTURE);
                if (!targetPlayer.getName().equalsIgnoreCase(sender.getName()))
                    sender.sendMessage(formatAll(languageConfig.getString("Command.Gamemode.Others Gamemode Changed").replace("%playername%", targetPlayer.getName()).replace("%gamemode%", languageConfig.getString("General.Gamemode.Adventure"))));
                targetPlayer.sendMessage(formatAll(languageConfig.getString("Command.Gamemode.Own Gamemode Changed").replace("%gamemode%", languageConfig.getString("General.Gamemode.Adventure"))));
                return ReturnType.SUCCESS;
            }
            if (args[0].equalsIgnoreCase("spectator") || args[0].equalsIgnoreCase("3")) {
                if (!sender.hasPermission("supercore.gamemode.spectator") && !isConsole) {
                    throw new MissingPermissionException("supercore.gamemode.spectator");
                }
                targetPlayer.setGameMode(GameMode.SPECTATOR);
                if (!targetPlayer.getName().equalsIgnoreCase(sender.getName()))
                    sender.sendMessage(formatAll(languageConfig.getString("Command.Gamemode.Others Gamemode Changed").replace("%playername%", targetPlayer.getName()).replace("%gamemode%", languageConfig.getString("General.Gamemode.Spectator"))));
                targetPlayer.sendMessage(formatAll(languageConfig.getString("Command.Gamemode.Own Gamemode Changed").replace("%gamemode%", languageConfig.getString("General.Gamemode.Spectator"))));
                return ReturnType.SUCCESS;
            }
        }

        return ReturnType.SYNTAX_ERROR;
    }

    @Override
    protected List<String> onTab(SuperCore instance, CommandSender sender, String... args) {
        List<String> list = new ArrayList<>();
        if (args.length == 1) {
            if (sender.hasPermission("supercore.gamemode.survival") && "survival".startsWith(args[0]))
                list.add("survival");
            if (sender.hasPermission("supercore.gamemode.creative") && "creative".startsWith(args[0]))
                list.add("creative");
            if (sender.hasPermission("supercore.gamemode.adventure") && "adventure".startsWith(args[0]))
                list.add("adventure");
            if (sender.hasPermission("supercore.gamemode.spectator") && "spectator".startsWith(args[0]))
                list.add("spectator");
        } else if (args.length == 2) {
            onlinePlayers(sender).stream().filter(onlinePlayer -> onlinePlayer.getName().startsWith(args[1])).forEach(onlinePlayer -> list.add(onlinePlayer.getName()));
            Collections.sort(list);
        }
        return list;
    }

    @Override
    public String getPermissionNode() {
        return "supercore.gamemode";
    }

    @Override
    public String getSyntax() {
        return "/gamemode <type> [player]";
    }

    @Override
    public String getDescription() {
        return "Change own or others gamemode.";
    }

}
