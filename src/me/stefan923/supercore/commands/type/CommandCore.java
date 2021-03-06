package me.stefan923.supercore.commands.type;

import me.stefan923.supercore.SuperCore;
import me.stefan923.supercore.utils.MessageUtils;
import me.stefan923.supercore.commands.AbstractCommand;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Collections;
import java.util.List;

public class CommandCore extends AbstractCommand implements MessageUtils {

    public CommandCore() {
        super(null, false, "core");
    }

    @Override
    protected ReturnType runCommand(SuperCore instance, CommandSender sender, String... args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(formatAll(" "));
            sender.sendMessage(formatAll("&8&m--+----------------------------------------+--&r"));
            sender.sendMessage(formatAll("  &3&lSuperCore &f&lv" + instance.getDescription().getVersion()));
            sender.sendMessage(formatAll("&8&l> &fPlugin author: &bStefan923"));
            sender.sendMessage(formatAll(" "));
            sender.sendMessage(formatAll("&8&l> &fProvides a core set of commands and server utilities."));
            sender.sendMessage(formatAll("&8&m--+----------------------------------------+--&r"));
            sender.sendMessage(formatAll(" "));

            return ReturnType.SUCCESS;
        }
        sender.sendMessage(formatAll(" "));
        sendCenteredMessage(sender, formatAll("&8&m--+----------------------------------------+--&r"));
        sendCenteredMessage(sender, formatAll("&3&lSuperCore &f&lv" + instance.getDescription().getVersion()));
        sendCenteredMessage(sender, formatAll("&8&l» &fPlugin author: &bStefan923"));
        sendCenteredMessage(sender, formatAll(" "));
        sendCenteredMessage(sender, formatAll("&8&l» &fProvides a core set of commands and server utilities."));
        sendCenteredMessage(sender, formatAll("&8&m--+----------------------------------------+--&r"));
        sender.sendMessage(formatAll(" "));

        return ReturnType.SUCCESS;
    }

    @Override
    protected List<String> onTab(SuperCore instance, CommandSender sender, String... args) {
        if (sender.hasPermission("supercore.admin"))
            return Collections.singletonList("reload");
        return null;
    }

    @Override
    public String getPermissionNode() {
        return null;
    }

    @Override
    public String getSyntax() {
        return "/core";
    }

    @Override
    public String getDescription() {
        return "Displays plugin info";
    }

}
