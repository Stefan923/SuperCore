package me.stefan923.supercore.command;

import me.stefan923.supercore.SuperCore;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.ArrayList;
import java.util.List;

public class TabManager implements TabCompleter {

    private final SuperCore plugin;

    private final CommandManager commandManager;

    public TabManager(CommandManager commandManager) {
        this.plugin = SuperCore.getInstance();
        this.commandManager = commandManager;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, org.bukkit.command.Command command, String alias, String[] strings) {
        List<String> tabStrings = new ArrayList<>();
        for (Command enabledCommand : commandManager.getCommands()) {
            if (enabledCommand.getName() != null && enabledCommand.getName().equalsIgnoreCase(command.getName())) {
                List<String> tempStrings = enabledCommand.onTab(plugin, sender, strings);
                if (tempStrings != null) {
                    tabStrings.addAll(tempStrings);
                }
            }
        }
        return tabStrings.isEmpty() ? null : tabStrings;
    }

}
