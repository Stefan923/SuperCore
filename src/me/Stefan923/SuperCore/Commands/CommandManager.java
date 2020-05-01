package me.Stefan923.SuperCore.Commands;

import me.Stefan923.SuperCore.Commands.Exceptions.MissingPermissionException;
import me.Stefan923.SuperCore.Commands.Type.*;
import me.Stefan923.SuperCore.SuperCore;
import me.Stefan923.SuperCore.Utils.MessageUtils;
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
    private SuperCore plugin;
    private TabManager tabManager;

    public CommandManager(SuperCore plugin) {
        this.plugin = plugin;
        this.tabManager = new TabManager(this);

        plugin.getCommand("core").setExecutor(this);

        FileConfiguration settings = plugin.getSettingsManager().getConfig();

        AbstractCommand commandCore = addCommand(new CommandCore());

        if (settings.getBoolean("Enabled Commands.AdminChat")) {
            plugin.getCommand("adminchat").setExecutor(this);
            addCommand(new CommandAdminChat());
        }
        if (settings.getBoolean("Enabled Commands.Broadcast")) {
            plugin.getCommand("broadcast").setExecutor(this);
            addCommand(new CommandBroadcast());
        }
        if (settings.getBoolean("Enabled Commands.DonorChat")) {
            plugin.getCommand("donorchat").setExecutor(this);
            addCommand(new CommandDonorChat());
        }
        if (settings.getBoolean("Enabled Commands.Feed")) {
            plugin.getCommand("feed").setExecutor(this);
            addCommand(new CommandFeed());
        }
        if (settings.getBoolean("Enabled Commands.Fly")) {
            plugin.getCommand("fly").setExecutor(this);
            addCommand(new CommandFly());
        }
        if (settings.getBoolean("Enabled Commands.Gamemode")) {
            plugin.getCommand("gamemode").setExecutor(this);
            addCommand(new CommandGamemode());
            plugin.getCommand("survival").setExecutor(this);
            addCommand(new CommandSurvival());
            plugin.getCommand("creative").setExecutor(this);
            addCommand(new CommandCreative());
            plugin.getCommand("spectator").setExecutor(this);
            addCommand(new CommandSpectator());
            plugin.getCommand("adventure").setExecutor(this);
            addCommand(new CommandAdventure());
        }
        if (settings.getBoolean("Enabled Commands.God")) {
            plugin.getCommand("god").setExecutor(this);
            addCommand(new CommandGod());
        }
        if (settings.getBoolean("Enabled Commands.Heal")) {
            plugin.getCommand("heal").setExecutor(this);
            addCommand(new CommandHeal());
        }
        if (settings.getBoolean("Enabled Commands.HelpOp")) {
            plugin.getCommand("helpop").setExecutor(this);
            addCommand(new CommandHelpOp());
        }
        if (settings.getBoolean("Enabled Commands.Language")) {
            plugin.getCommand("language").setExecutor(this);
            addCommand(new CommandLanguage());
        }
        if (settings.getBoolean("Enabled Commands.List")) {
            plugin.getCommand("list").setExecutor(this);
            addCommand(new CommandList());
        }
        if (settings.getBoolean("Enabled Commands.Nick")) {
            plugin.getCommand("nick").setExecutor(this);
            addCommand(new CommandNick());
        }
        if (settings.getBoolean("Enabled Commands.Seen")) {
            plugin.getCommand("seen").setExecutor(this);
            addCommand(new CommandSeen());
        }
        if (settings.getBoolean("Enabled Commands.Tp")) {
            plugin.getCommand("tp").setExecutor(this);
            addCommand(new CommandTp());
        }
        if (settings.getBoolean("Enabled Commands.TpToggle")) {
            plugin.getCommand("tptoggle").setExecutor(this);
            addCommand(new CommandTpToggle());
        }
        if (settings.getBoolean("Enabled Commands.WhoIs")) {
            plugin.getCommand("whois").setExecutor(this);
            addCommand(new CommandWhoIs());
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
        commandSender.sendMessage(formatAll("&8[&3SuperCore&8] &cThe command you entered does not exist or is spelt incorrectly."));
        return true;
    }

    private void processRequirements(AbstractCommand command, CommandSender sender, String[] strings) {
        FileConfiguration language = getLanguageConfig(plugin, sender);
        if ((sender instanceof Player)) {
            String permissionNode = command.getPermissionNode();
            if (permissionNode == null || sender.hasPermission(command.getPermissionNode())) {
                AbstractCommand.ReturnType returnType = null;
                try {
                    returnType = command.runCommand(plugin, sender, strings);
                } catch (MissingPermissionException e) {
                    sender.sendMessage(formatAll(language.getString("General.No Permission").replace("%permission%", e.getMessage())));
                }
                if (returnType == AbstractCommand.ReturnType.SYNTAX_ERROR) {
                    sender.sendMessage(formatAll(language.getString("General.Invalid Command Syntax").replace("%syntax%", command.getSyntax())));
                }
                return;
            }
            sender.sendMessage(formatAll(language.getString("General.No Permission").replace("%permission%", permissionNode)));
            return;
        }
        if (command.isNoConsole()) {
            sender.sendMessage(formatAll(language.getString("General.Must Be Player")));
            return;
        }
        if (command.getPermissionNode() == null || sender.hasPermission(command.getPermissionNode())) {
            AbstractCommand.ReturnType returnType = null;
            try {
                returnType = command.runCommand(plugin, sender, strings);
            } catch (MissingPermissionException e) {
                e.printStackTrace();
            }
            if (returnType == AbstractCommand.ReturnType.SYNTAX_ERROR) {
                sender.sendMessage(formatAll(language.getString("General.Invalid Command Syntax").replace("%syntax%", command.getSyntax())));
            }
            return;
        }
        sender.sendMessage(formatAll("&8[&3SuperCore&8] &cYou have no permission!"));
    }

    public List<AbstractCommand> getCommands() {
        return Collections.unmodifiableList(commands);
    }

}