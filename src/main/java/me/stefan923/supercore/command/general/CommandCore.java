package me.stefan923.supercore.command.general;

import me.stefan923.supercore.SuperCore;
import me.stefan923.supercore.command.Command;
import me.stefan923.supercore.util.MessageUtil;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class CommandCore extends Command {

    private static final String PLUGIN_DESCRIPTION =
            "\n&8&m--+----------------------------------------+--&r\n" +
            "  &3&lSuperCore &f&lv%version%\n" +
            "&8&l> &fPlugin author: &bStefan923\n\n" +
            "&8&l> &fProvides a core set of commands and server utilities.\n" +
            "&8&m--+----------------------------------------+--&r\n";

    public CommandCore() {
        super(false, false, "core");
    }

    @Override
    protected ReturnType runCommand(SuperCore plugin, CommandSender sender, String... args) {
        if (sender instanceof Player) {
            for (String line : PLUGIN_DESCRIPTION.split("\n")) {
                sender.sendMessage(MessageUtil.centerMessage(line.replace("%version%", plugin.getDescription().getVersion())));
            }
        }
        sender.sendMessage(MessageUtil.formatAll(PLUGIN_DESCRIPTION.replace("%version%", plugin.getDescription().getVersion())));
        return ReturnType.SUCCESS;
    }

    @Override
    protected List<String> onTab(SuperCore plugin, CommandSender sender, String... args) {
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
        return "Shows a short description of the plugin.";
    }

}
