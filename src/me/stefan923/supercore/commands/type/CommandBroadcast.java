package me.stefan923.supercore.commands.type;

import me.stefan923.supercore.SuperCore;
import me.stefan923.supercore.language.LanguageManager;
import me.stefan923.supercore.utils.MessageUtils;
import me.stefan923.supercore.commands.AbstractCommand;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class CommandBroadcast extends AbstractCommand implements MessageUtils {

    public CommandBroadcast() {
        super(false, true, "broadcast");
    }

    @Override
    protected ReturnType runCommand(SuperCore instance, CommandSender sender, String... args) {
        if (args.length < 1)
            return ReturnType.SYNTAX_ERROR;

        final StringBuilder stringBuilder = new StringBuilder();
        for (String arg : args) stringBuilder.append(arg).append(" ");
        stringBuilder.setLength(stringBuilder.length() - 1);

        final String message = stringBuilder.toString();

        for (Player player : Bukkit.getOnlinePlayers()) {
            LanguageManager languageManager = instance.getLanguageManager(instance.getUser(player).getLanguage());
            player.sendMessage(formatAll(replacePlaceholders(player, languageManager.getConfig().getString("Command.Broadcast.Format").replace("%message%", message))));
        }

        return ReturnType.SUCCESS;
    }

    @Override
    protected List<String> onTab(SuperCore instance, CommandSender sender, String... args) {
        return null;
    }

    @Override
    public String getPermissionNode() {
        return "supercore.broadcast";
    }

    @Override
    public String getSyntax() {
        return "/broadcast <text>";
    }

    @Override
    public String getDescription() {
        return "Sends a message to all online players.";
    }

}
