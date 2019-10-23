package me.Stefan923.SuperCoreLite.Commands.Type;

import me.Stefan923.SuperCoreLite.Commands.AbstractCommand;
import me.Stefan923.SuperCoreLite.Commands.CommandManager;
import me.Stefan923.SuperCoreLite.Main;
import org.bukkit.command.CommandSender;

import java.util.Arrays;
import java.util.List;

public class CommandReload extends AbstractCommand {

    public CommandReload(AbstractCommand abstractCommand) {
        super(abstractCommand, false, "reload");
    }

    @Override
    protected AbstractCommand.ReturnType runCommand(Main instance, CommandSender sender, String... args) {
        if (args.length != 1)
            return ReturnType.SYNTAX_ERROR;

        Main plugin = Main.instance;

        if (args[1].equalsIgnoreCase("all")) {
            plugin.getSettingsManager().reload();
            plugin.getLanguageManager().reload();
            return ReturnType.SUCCESS;
        }

        if (args[1].equalsIgnoreCase("settings")) {
            plugin.getSettingsManager().reload();
            return ReturnType.SUCCESS;
        }

        if (args[1].equalsIgnoreCase("languages")) {
            plugin.getLanguageManager().reload();
            return ReturnType.SUCCESS;
        }

        return ReturnType.SYNTAX_ERROR;
    }

    @Override
    protected List<String> onTab(Main instance, CommandSender sender, String... args) {
        if (sender.hasPermission("supercore.admin"))
            return Arrays.asList("settings", "languages", "all");
        return null;
    }

    @Override
    public String getPermissionNode() {
        return "supercore.admin";
    }

    @Override
    public String getSyntax() {
        return "/core reload";
    }

    @Override
    public String getDescription() {
        return "Reloads plugin settings.";
    }

}
