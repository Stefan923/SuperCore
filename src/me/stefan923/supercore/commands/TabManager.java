package me.stefan923.supercore.commands;

import me.stefan923.supercore.SuperCore;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.ArrayList;
import java.util.List;

public class TabManager implements TabCompleter {

    private final CommandManager commandManager;

    TabManager(CommandManager commandManager) {
        this.commandManager = commandManager;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] strings) {
        SuperCore instance = SuperCore.getInstance();
        List<String> tabStrings = new ArrayList<>();
        for (AbstractCommand abstractCommand : commandManager.getCommands()) {
            if (abstractCommand.getCommand() != null && abstractCommand.getCommand().equalsIgnoreCase(command.getName())) {
                List<String> tempStrings = abstractCommand.onTab(instance, sender, strings);
                if (tempStrings != null)
                    tabStrings.addAll(tempStrings);
            }
        }
        return tabStrings.isEmpty() ? null : tabStrings;
    }

}