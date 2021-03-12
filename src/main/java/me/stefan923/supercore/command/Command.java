package me.stefan923.supercore.command;

import me.stefan923.supercore.SuperCore;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.List;

public abstract class Command {

    private final String name;
    private final Command parent;
    private final boolean noConsole;

    private final List<Command> subCommands = new ArrayList<>();

    private boolean hasArgs;

    protected Command(Command parent, boolean noConsole, String name) {
        this.parent = parent;
        this.noConsole = noConsole;
        this.name = name;
    }

    protected Command(boolean noConsole, boolean hasArgs, String name) {
        this(null, noConsole, name);
        this.hasArgs = hasArgs;
    }

    public String getName() {
        return name;
    }

    public Command getParent() {
        return parent;
    }

    public boolean hasArgs() {
        return hasArgs;
    }

    public boolean isNoConsole() {
        return noConsole;
    }

    public List<Command> getSubCommands() {
        return subCommands;
    }

    public void addSubCommand(Command command) {
        subCommands.add(command);
    }

    protected abstract ReturnType runCommand(SuperCore plugin, CommandSender sender, String... args);

    protected abstract List<String> onTab(SuperCore plugin, CommandSender sender, String... args);

    public abstract String getPermissionNode();

    public abstract String getSyntax();

    public abstract String getDescription();

    public enum ReturnType { SUCCESS, FAILURE, SYNTAX_ERROR }

}
