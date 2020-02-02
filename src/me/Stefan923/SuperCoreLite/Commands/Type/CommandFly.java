package me.Stefan923.SuperCoreLite.Commands.Type;

import me.Stefan923.SuperCoreLite.Commands.AbstractCommand;
import me.Stefan923.SuperCoreLite.Main;
import me.Stefan923.SuperCoreLite.Utils.MessageUtils;
import me.Stefan923.SuperCoreLite.Utils.PlayerUtils;
import me.Stefan923.SuperCoreLite.Utils.User;
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
    protected AbstractCommand.ReturnType runCommand(Main instance, CommandSender sender, String... args) {
        Player senderPlayer = (Player) sender;
        User user = instance.getUser(senderPlayer);

        FileConfiguration languageConfig = instance.getLanguageManager(user.getLanguage()).getConfig();

        int length = args.length;

        if (length == 0 || args[0].equalsIgnoreCase(senderPlayer.getName())) {
            boolean isAllowed = !senderPlayer.getAllowFlight();
            senderPlayer.setAllowFlight(isAllowed);
            senderPlayer.sendMessage(formatAll(languageConfig.getString("Command.Fly.Own Flight Mode Changed")
                    .replace("%status%", isAllowed ? languageConfig.getString("General.Word Enabled") : languageConfig.getString("General.Word Disabled"))));
            return ReturnType.SUCCESS;
        }

        Player targetPlayer = Bukkit.getPlayer(args[0]);
        if (targetPlayer == null) {
            senderPlayer.sendMessage(formatAll(languageConfig.getString("General.Must Be Online")));
            return ReturnType.FAILURE;
        }
        boolean isAllowed = !targetPlayer.getAllowFlight();
        targetPlayer.setAllowFlight(isAllowed);
        senderPlayer.sendMessage(formatAll(languageConfig.getString("Command.Fly.Others Flight Mode Changed")
                .replace("%status%", isAllowed ? languageConfig.getString("General.Word Enabled") : languageConfig.getString("General.Word Disabled"))
                .replace("%target%", targetPlayer.getName())));
        targetPlayer.sendMessage(formatAll(languageConfig.getString("Command.Fly.Own Flight Mode Changed By")
                .replace("%status%", isAllowed ? languageConfig.getString("General.Word Enabled") : languageConfig.getString("General.Word Disabled"))
                .replace("%sender%", senderPlayer.getName())));
        return ReturnType.SUCCESS;
    }

    @Override
    protected List<String> onTab(Main instance, CommandSender sender, String... args) {
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
