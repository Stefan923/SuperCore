package me.stefan923.supercore.command;

import me.stefan923.supercore.SuperCore;
import me.stefan923.supercore.command.general.CommandCore;
import me.stefan923.supercore.configuration.language.ILanguage;
import me.stefan923.supercore.configuration.language.MessagePath;
import me.stefan923.supercore.configuration.setting.Setting;
import me.stefan923.supercore.util.MessageUtil;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.PluginCommand;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class CommandManager implements CommandExecutor {

    private final SuperCore plugin = SuperCore.getInstance();

    private final List<Command> enabledCommands = new ArrayList<>();

    private final TabManager tabManager = new TabManager(this);

    public void setUp() {
        addCommand(new CommandCore());

        for (Command command : enabledCommands) {
            if (command.getParent() == null) {
                PluginCommand bukkitCommand = plugin.getCommand(command.getName());
                bukkitCommand.setExecutor(this);
                bukkitCommand.setTabCompleter(tabManager);
            }
        }
    }

    public List<Command> getCommands() {
        return Collections.unmodifiableList(enabledCommands);
    }

    @Override
    public boolean onCommand(CommandSender sender, org.bukkit.command.Command command, String label, String[] args) {
        for (Command enabledCommand : enabledCommands) {
            if (enabledCommand.getName().equalsIgnoreCase(label)) {
                Command foundCommand = getSubCommandByName(enabledCommand, args, 0);
                if (foundCommand != null) {
                    processRequirements(foundCommand, sender, args);
                }
                return true;
            }
        }

        sender.sendMessage(MessageUtil.formatAll("&8[&3SuperCore&8] &cThe command you entered does not exist or is spelt incorrectly."));
        return true;
    }

    private Command addCommand(Command command) {
        Command parentCommand = command.getParent();
        if (parentCommand != null) {
            parentCommand.addSubCommand(command);
        }

        enabledCommands.add(command);
        return command;
    }

    private Command getSubCommandByName(Command command, String[] args, int index) {
        if (!command.hasArgs() && index < args.length) {
            for (Command subCommand : command.getSubCommands()) {
                if (subCommand.getName().equalsIgnoreCase(args[index])) {
                    return getSubCommandByName(subCommand, args, index + 1);
                }
            }
            return null;
        }
        return command;
    }

    private void processRequirements(Command command, CommandSender sender, String[] args) {
        ILanguage language;
        Command.ReturnType returnType;

        System.out.println(command.getName());

        if ((sender instanceof Player)) {
            language = plugin.getUserRepository().getUser(sender.getName()).getLanguage();

            String permissionNode = command.getPermissionNode();
            if (permissionNode == null || sender.hasPermission(command.getPermissionNode())) {
                returnType = command.runCommand(plugin, sender, args);
            } else {
                sender.sendMessage(MessageUtil.formatAll(language.getMessage(MessagePath.GENERAL_NO_PERMISSION)
                        .replace("%permission%", permissionNode)));
                return;
            }
        } else {
            language = plugin.getLanguageManager().getLanguageByFileName(Setting.DEFAULT_LANGUAGE_FILENAME);

            if (command.isNoConsole()) {
                sender.sendMessage(MessageUtil.formatAll(language.getMessage(MessagePath.GENERAL_MUST_BE_PLAYER)));
                return;
            }
            returnType = command.runCommand(plugin, sender, args);
        }

        if (returnType == Command.ReturnType.SYNTAX_ERROR) {
            sender.sendMessage(MessageUtil.formatAll(language.getMessage(MessagePath.COMMAND_INVALID_SYNTAX).replace("%syntax%", command.getSyntax())));
        }
    }

}
