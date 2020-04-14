package me.Stefan923.SuperCore.Commands.Type;

import me.Stefan923.SuperCore.Commands.AbstractCommand;
import me.Stefan923.SuperCore.SuperCore;
import me.Stefan923.SuperCore.Utils.MessageUtils;
import me.Stefan923.SuperCore.Utils.User;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.List;

public class CommandDonorChat extends AbstractCommand implements MessageUtils {

    public CommandDonorChat() {
        super(false, true, "donorchat");
    }

    @Override
    protected ReturnType runCommand(SuperCore instance, CommandSender sender, String... args) {
        if (!(sender instanceof Player)) {
            final StringBuilder stringBuilder = new StringBuilder();
            for (String arg : args)
                stringBuilder.append(arg).append(" ");
            String message = stringBuilder.toString();

            Bukkit.getOnlinePlayers().stream().filter(receiverPlayer -> receiverPlayer.hasPermission("supercore.donorchat.receive")).forEach(receiverPlayer -> receiverPlayer.sendMessage(formatAll(getLanguageConfig(instance, receiverPlayer).getString("Command.DonorChat.Format By Console").replace("%message%", message))));
            ConsoleCommandSender logger = Bukkit.getConsoleSender();
            Bukkit.getConsoleSender().sendMessage(formatAll(getLanguageConfig(instance, logger).getString("Command.DonorChat.Format By Console").replace("%message%", message)));
            return ReturnType.SUCCESS;
        }
        Player senderPlayer = (Player) sender;
        User user = instance.getUser(senderPlayer);

        FileConfiguration settings = instance.getSettingsManager().getConfig();
        FileConfiguration languageConfig = instance.getLanguageManager(user.getLanguage()).getConfig();

        if (args.length < 1)
            return ReturnType.SYNTAX_ERROR;

        long now = System.currentTimeMillis();
        if (user.getDonorChatCooldown() > now) {
            senderPlayer.sendMessage(formatAll(languageConfig.getString("Command.Cooldown").replace("%cooldown%", String.valueOf(settings.getInt("Command Cooldowns.DonorChat")))));
            return ReturnType.FAILURE;
        }

        final StringBuilder stringBuilder = new StringBuilder();

        for (String arg : args) stringBuilder.append(arg).append(" ");

        String message = stringBuilder.toString();

        if (user.getDonorChatLastMessage().equalsIgnoreCase(message)) {
            senderPlayer.sendMessage(formatAll(languageConfig.getString("General.Repeated Message")));
            return ReturnType.FAILURE;
        }

        Bukkit.getOnlinePlayers().stream().filter(receiverPlayer -> receiverPlayer.hasPermission("supercore.donorchat.receive")).forEach(receiverPlayer -> receiverPlayer.sendMessage(formatAll(replacePlaceholders(senderPlayer, getLanguageConfig(instance, receiverPlayer).getString("Command.DonorChat.Format"))).replace("%message%", message).replace("%playername%", senderPlayer.getName())));
        ConsoleCommandSender logger = Bukkit.getConsoleSender();
        Bukkit.getConsoleSender().sendMessage(formatAll(replacePlaceholders(senderPlayer, getLanguageConfig(instance, logger).getString("Command.DonorChat.Format"))).replace("%message%", message).replace("%playername%", senderPlayer.getName()));

        user.setDonorChatCooldown(now + 1000 * settings.getInt("Command Cooldowns.DonorChat"));
        user.setDonorChatLastMessage(message);
        return ReturnType.SUCCESS;
    }

    @Override
    protected List<String> onTab(SuperCore instance, CommandSender sender, String... args) {
        return null;
    }

    @Override
    public String getPermissionNode() {
        return "supercore.donorchat.send";
    }

    @Override
    public String getSyntax() {
        return "/donorchat <message>";
    }

    @Override
    public String getDescription() {
        return "A special chat for donors.";
    }


}
