package me.Stefan923.SuperCoreLite.Commands;

import me.Stefan923.SuperCoreLite.Commands.Type.*;
import me.Stefan923.SuperCoreLite.Main;
import me.Stefan923.SuperCoreLite.Utils.MessageUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CommandManager implements CommandExecutor, MessageUtils {

    private static final List<AbstractCommand> commands = new ArrayList<>();
    private Main plugin;
    private TabManager tabManager;

    public CommandManager(Main plugin) {
        this.plugin = plugin;
        this.tabManager = new TabManager(this);

        plugin.getCommand("core").setExecutor(this);

        FileConfiguration settings = plugin.getSettingsManager().getConfig();

        AbstractCommand commandCore = addCommand(new CommandCore());

        if (settings.getBoolean("Enabled Commands.AdminChat")) {
            plugin.getCommand("adminchat").setExecutor(this);
            addCommand(new CommandAdminChat());
        }
        if (settings.getBoolean("Enabled Commands.DonorChat")) {
            plugin.getCommand("donorchat").setExecutor(this);
            addCommand(new CommandDonorChat());
        }
        if (settings.getBoolean("Enabled Commands.HelpOp")) {
            plugin.getCommand("helpop").setExecutor(this);
            addCommand(new CommandDonorChat());
        }
        if (settings.getBoolean("Enabled Commands.List")) {
            plugin.getCommand("list").setExecutor(this);
            addCommand(new CommandList());
        }
        addCommand(new CommandReload(commandCore));

        for (AbstractCommand abstractCommand : commands) {
            if (abstractCommand.getParent() != null) continue;
            plugin.getCommand(abstractCommand.getCommand()).setTabCompleter(tabManager);
        }
    }

    private AbstractCommand addCommand(AbstractCommand abstractCommand) {
        commands.add(abstractCommand);
        return abstractCommand;
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        for (AbstractCommand abstractCommand : commands) {
            if (abstractCommand.getCommand() != null && abstractCommand.getCommand().equalsIgnoreCase(command.getName().toLowerCase())) {
                if (strings.length == 0 || abstractCommand.hasArgs()) {
                    processRequirements(abstractCommand, commandSender, strings);
                    return true;
                }
            } else if (strings.length != 0 && abstractCommand.getParent() != null && abstractCommand.getParent().getCommand().equalsIgnoreCase(command.getName())) {
                String cmd = strings[0];
                String cmd2 = strings.length >= 2 ? String.join(" ", strings[0], strings[1]) : null;
                for (String cmds : abstractCommand.getSubCommand()) {
                    if (cmd.equalsIgnoreCase(cmds) || (cmd2 != null && cmd2.equalsIgnoreCase(cmds))) {
                        processRequirements(abstractCommand, commandSender, strings);
                        return true;
                    }
                }
            }
        }
        commandSender.sendMessage(formatAll("&8「&3SuperCore&8」 &cThe command you entered does not exist or is spelt incorrectly."));
        return true;
    }

    private void processRequirements(AbstractCommand command, CommandSender sender, String[] strings) {
        if (!(sender instanceof Player) && command.isNoConsole()) {
            sender.sendMessage(formatAll("&cYou must be a player to use this commands."));
            return;
        }
        if (command.getPermissionNode() == null || sender.hasPermission(command.getPermissionNode())) {
            AbstractCommand.ReturnType returnType = command.runCommand(plugin, sender, strings);
            if (returnType == AbstractCommand.ReturnType.SYNTAX_ERROR) {
                sender.sendMessage(formatAll("&8「&3SuperCore&8」 &cInvalid Syntax!"));
                sender.sendMessage(formatAll("&8「&3SuperCore&8」 &fThe valid syntax is: &b" + command.getSyntax() + "&f."));
            }
            return;
        }
        sender.sendMessage("&8「&3SuperCore&8」 &cYou have no permission!");
    }

    public List<AbstractCommand> getCommands() {
        return Collections.unmodifiableList(commands);
    }

}
