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
        super(true, false, "fly");
    }

    @Override
    protected AbstractCommand.ReturnType runCommand(Main instance, CommandSender sender, String... args) {
        Player senderPlayer = (Player) sender;
        User user = instance.getUser(senderPlayer);

        FileConfiguration languageConfig = instance.getLanguageManager(user.getLanguage()).getConfig();

        int length = args.length;

        if (length == 0) {
            senderPlayer.setAllowFlight(!senderPlayer.getAllowFlight());
            return ReturnType.SUCCESS;
        }

        if (length == 2) {
            Player targetPlayer = Bukkit.getPlayer(args[0]);
            if (targetPlayer == null) {
                senderPlayer.sendMessage(formatAll(languageConfig.getString("General.Must Be Online")));
                return ReturnType.FAILURE;
            }
            targetPlayer.setAllowFlight(!targetPlayer.getAllowFlight());
            return ReturnType.SUCCESS;
        }

        return ReturnType.SYNTAX_ERROR;
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
