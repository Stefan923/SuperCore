package me.Stefan923.SuperCoreLite.Commands.Type;

import me.Stefan923.SuperCoreLite.Commands.AbstractCommand;
import me.Stefan923.SuperCoreLite.SuperCore;
import me.Stefan923.SuperCoreLite.Utils.MessageUtils;
import org.bukkit.command.CommandSender;

import java.util.Arrays;
import java.util.List;

public class CommandCore extends AbstractCommand implements MessageUtils {

    public CommandCore() {
        super(null, false, "core");
    }

    @Override
    protected AbstractCommand.ReturnType runCommand(SuperCore instance, CommandSender sender, String... args) {
        sender.sendMessage(formatAll(" "));
        sendCenteredMessage(sender, formatAll("&8&m--+----------------------------------------+--&r"));
        sendCenteredMessage(sender, formatAll("&3&lSuperCore Lite &f&lv" + instance.getDescription().getVersion()));
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
            return Arrays.asList("reload");
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
