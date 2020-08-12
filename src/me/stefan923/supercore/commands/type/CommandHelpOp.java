package me.stefan923.supercore.commands.type;

import me.stefan923.supercore.SuperCore;
import me.stefan923.supercore.utils.MessageUtils;
import me.stefan923.supercore.utils.User;
import me.stefan923.supercore.commands.AbstractCommand;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.List;

public class CommandHelpOp extends AbstractCommand implements MessageUtils {

    public CommandHelpOp() {
        super(false, true, "helpop");
    }

    @Override
    protected ReturnType runCommand(SuperCore instance, CommandSender sender, String... args) {
        if (!(sender instanceof Player)) {
            final StringBuilder stringBuilder = new StringBuilder();
            for (String arg : args)
                stringBuilder.append(arg).append(" ");
            String message = stringBuilder.toString();

            Bukkit.getOnlinePlayers().stream().filter(receiverPlayer -> receiverPlayer.hasPermission("supercore.helpop.receive")).forEach(receiverPlayer -> receiverPlayer.sendMessage(formatAll(getLanguageConfig(instance, receiverPlayer).getString("Command.HelpOp.Format By Console").replace("%message%", message))));
            ConsoleCommandSender logger = Bukkit.getConsoleSender();
            Bukkit.getConsoleSender().sendMessage(formatAll(getLanguageConfig(instance, logger).getString("Command.HelpOp.Format By Console").replace("%message%", message)));
            return ReturnType.SUCCESS;
        }
        Player senderPlayer = (Player) sender;
        User user = instance.getUser(senderPlayer);

        FileConfiguration settings = instance.getSettingsManager().getConfig();
        FileConfiguration languageConfig = getLanguageConfig(instance, senderPlayer);

        if (args.length < 1)
            return ReturnType.SYNTAX_ERROR;

        long now = System.currentTimeMillis();
        if (user.getHelpOpCooldown() > now) {
            senderPlayer.sendMessage(formatAll(languageConfig.getString("Command.Cooldown").replace("%cooldown%", String.valueOf(settings.getInt("Command Cooldowns.HelpOp")))));
            return ReturnType.FAILURE;
        }

        final StringBuilder stringBuilder = new StringBuilder();

        for (String arg : args) stringBuilder.append(arg).append(" ");

        String message = stringBuilder.toString();

        if (user.getHelpOpLastMessage().equalsIgnoreCase(message)) {
            senderPlayer.sendMessage(formatAll(languageConfig.getString("General.Repeated Message")));
            return ReturnType.FAILURE;
        }

        Bukkit.getOnlinePlayers().stream().filter(receiverPlayer -> receiverPlayer.hasPermission("supercore.helpop.receive")).forEach(receiverPlayer -> receiverPlayer.sendMessage(formatAll(replacePlaceholders(senderPlayer, getLanguageConfig(instance, receiverPlayer).getString("Command.HelpOp.Format"))).replace("%message%", message).replace("%playername%", senderPlayer.getName())));
        ConsoleCommandSender logger = Bukkit.getConsoleSender();
        Bukkit.getConsoleSender().sendMessage(formatAll(replacePlaceholders(senderPlayer, getLanguageConfig(instance, logger).getString("Command.HelpOp.Format"))).replace("%message%", message).replace("%playername%", senderPlayer.getName()));

        user.setHelpOpCooldown(now + 1000 * settings.getInt("Command Cooldowns.HelpOp"));
        user.setHelpOpLastMessage(message);
        return ReturnType.SUCCESS;
    }

    @Override
    protected List<String> onTab(SuperCore instance, CommandSender sender, String... args) {
        return null;
    }

    @Override
    public String getPermissionNode() {
        return "supercore.helpop.send";
    }

    @Override
    public String getSyntax() {
        return "/helpop <message>";
    }

    @Override
    public String getDescription() {
        return "A special chat to request help.";
    }

}
