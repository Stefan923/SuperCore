package me.Stefan923.SuperCore.Commands.Type;

import me.Stefan923.SuperCore.Commands.AbstractCommand;
import me.Stefan923.SuperCore.Commands.Exceptions.MissingPermissionException;
import me.Stefan923.SuperCore.SuperCore;
import me.Stefan923.SuperCore.Utils.MessageUtils;
import me.Stefan923.SuperCore.Utils.PlayerUtils;
import me.Stefan923.SuperCore.Utils.User;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.List;

public class CommandTp extends AbstractCommand implements MessageUtils, PlayerUtils {

    public CommandTp() {
        super(false, true, "tp");
    }

    @Override
    protected ReturnType runCommand(SuperCore instance, CommandSender sender, String... args) throws MissingPermissionException {
        boolean isConsole = !(sender instanceof Player);
        FileConfiguration senderLanguage = getLanguageManager(instance, sender).getConfig();
        switch (args.length) {
            case 1:
                if (isConsole) {
                    sender.sendMessage(formatAll(senderLanguage.getString("Teleport.Console Can Not Teleport")));
                    return ReturnType.FAILURE;
                }

                Player senderPlayer1 = (Player) sender;

                Player targetPlayer1 = Bukkit.getPlayer(args[0]);
                if (targetPlayer1 == null) {
                    sender.sendMessage(formatAll(senderLanguage.getString("General.Must Be Online")));
                    return ReturnType.FAILURE;
                }

                User targetUser = instance.getUser(targetPlayer1);
                if (!targetUser.getTeleport()) {
                    sender.sendMessage(formatAll(senderLanguage.getString("Teleport.His Teleport Is Disabled").replace("%target%", targetPlayer1.getName())));
                    return ReturnType.FAILURE;
                }

                senderPlayer1.teleport(targetPlayer1);
                sender.sendMessage(formatAll(senderLanguage.getString("Teleport.Teleported To Player").replace("%target%", targetPlayer1.getName())));
                return ReturnType.SUCCESS;
            case 2:
                if (!sender.hasPermission("supercore.tp.others") && !isConsole) {
                    throw new MissingPermissionException("supercore.tp.others");
                }

                Player target1Player = Bukkit.getPlayer(args[0]);
                Player target2Player = Bukkit.getPlayer(args[1]);
                if (target1Player == null || target2Player == null) {
                    sender.sendMessage(formatAll(senderLanguage.getString("General.Must Be Online")));
                    return ReturnType.FAILURE;
                }

                User target1User = instance.getUser(target1Player);
                User target2User = instance.getUser(target2Player);
                if (!target1User.getTeleport()) {
                    sender.sendMessage(formatAll(senderLanguage.getString("Teleport.His Teleport Is Disabled").replace("%target%", target1Player.getName())));
                    return ReturnType.FAILURE;
                }
                if (!target2User.getTeleport()) {
                    sender.sendMessage(formatAll(senderLanguage.getString("Teleport.His Teleport Is Disabled").replace("%target%", target2Player.getName())));
                    return ReturnType.FAILURE;
                }

                FileConfiguration target1Language = getLanguageManager(instance, target1User).getConfig();
                target1Player.teleport(target2Player);
                sender.sendMessage(formatAll(senderLanguage.getString("Teleport.Teleported Player To Player").replace("%target1%", target1Player.getName()).replace("%target2%", target2Player.getName())));
                target1Player.sendMessage(formatAll(target1Language.getString("Teleport.Teleported To Player").replace("%target%", target2Player.getName())));
                return ReturnType.SUCCESS;
            case 3:
                if (isConsole) {
                    sender.sendMessage(formatAll(senderLanguage.getString("Teleport.Console Can Not Teleport")));
                    return ReturnType.FAILURE;
                }

                Player senderPlayer3 = (Player) sender;
                final double x3 = args[0].startsWith("~") ? senderPlayer3.getLocation().getX() + (args[0].length() > 1 ? Double.parseDouble(args[0].substring(1)) : 0) : Double.parseDouble(args[0]);
                final double y3 = args[1].startsWith("~") ? senderPlayer3.getLocation().getY() + (args[1].length() > 1 ? Double.parseDouble(args[1].substring(1)) : 0) : Double.parseDouble(args[1]);
                final double z3 = args[2].startsWith("~") ? senderPlayer3.getLocation().getZ() + (args[2].length() > 1 ? Double.parseDouble(args[2].substring(1)) : 0) : Double.parseDouble(args[2]);
                final Location location3 = new Location(senderPlayer3.getWorld(), x3, y3, z3, senderPlayer3.getLocation().getYaw(), senderPlayer3.getLocation().getPitch());

                senderPlayer3.teleport(location3);
                sender.sendMessage(formatAll(senderLanguage.getString("Teleport.Teleported To Coords").replace("%x%", String.valueOf(location3.getBlockX())).replace("%y%", String.valueOf(location3.getBlockY())).replace("%z%", String.valueOf(location3.getBlockZ()))));
                return ReturnType.SUCCESS;
            case 4:
                if (!sender.hasPermission("supercore.tp.others") && !isConsole) {
                    throw new MissingPermissionException("supercore.tp.others");
                }

                Player targetPlayer4 = Bukkit.getPlayer(args[0]);
                if (targetPlayer4 == null) {
                    sender.sendMessage(formatAll(senderLanguage.getString("General.Must Be Online")));
                    return ReturnType.FAILURE;
                }
                User targetUser4 = getUser(instance, targetPlayer4);
                if (!targetUser4.getTeleport()) {
                    sender.sendMessage(formatAll(senderLanguage.getString("Teleport.His Teleport Is Disabled").replace("%target%", targetPlayer4.getName())));
                    return ReturnType.FAILURE;
                }

                final double x4 = args[0].startsWith("~") ? targetPlayer4.getLocation().getX() + (args[0].length() > 1 ? Double.parseDouble(args[0].substring(1)) : 0) : Double.parseDouble(args[0]);
                final double y4 = args[1].startsWith("~") ? targetPlayer4.getLocation().getY() + (args[1].length() > 1 ? Double.parseDouble(args[1].substring(1)) : 0) : Double.parseDouble(args[1]);
                final double z4 = args[2].startsWith("~") ? targetPlayer4.getLocation().getZ() + (args[2].length() > 1 ? Double.parseDouble(args[2].substring(1)) : 0) : Double.parseDouble(args[2]);
                final Location location4 = new Location(targetPlayer4.getWorld(), x4, y4, z4, targetPlayer4.getLocation().getYaw(), targetPlayer4.getLocation().getPitch());

                targetPlayer4.teleport(location4);
                sender.sendMessage(formatAll(senderLanguage.getString("Teleport.Teleported Player To Coords").replace("%target%", targetPlayer4.getName()).replace("%x%", String.valueOf(location4.getBlockX())).replace("%y%", String.valueOf(location4.getBlockY())).replace("%z%", String.valueOf(location4.getBlockZ()))));
                return ReturnType.SUCCESS;
        }
        return ReturnType.SYNTAX_ERROR;
    }

    @Override
    protected List<String> onTab(SuperCore instance, CommandSender sender, String... args) {
        return null;
    }

    @Override
    public String getPermissionNode() {
        return "supercore.tp";
    }

    @Override
    public String getSyntax() {
        return "/tp [player] <location|player>";
    }

    @Override
    public String getDescription() {
        return null;
    }
}
