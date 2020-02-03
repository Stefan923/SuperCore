package me.Stefan923.SuperCoreLite.Commands.Type;

import me.Stefan923.SuperCoreLite.Commands.AbstractCommand;
import me.Stefan923.SuperCoreLite.SuperCore;
import me.Stefan923.SuperCoreLite.Utils.MessageUtils;
import me.Stefan923.SuperCoreLite.Utils.User;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.List;

public class CommandAdminChat extends AbstractCommand implements MessageUtils {

    public CommandAdminChat() {
        super(true, false, "adminchat");
    }

    @Override
    protected AbstractCommand.ReturnType runCommand(SuperCore instance, CommandSender sender, String... args) {
        Player senderPlayer = (Player) sender;
        User user = instance.getUser(senderPlayer);

        FileConfiguration settings = instance.getSettingsManager().getConfig();
        FileConfiguration languageConfig = instance.getLanguageManager(user.getLanguage()).getConfig();

        if (args.length < 1)
            return ReturnType.SYNTAX_ERROR;

        long now = System.currentTimeMillis();
        if (user.getAdminChatCooldown() > now) {
            senderPlayer.sendMessage(formatAll(languageConfig.getString("Command.Cooldown").replace("%cooldown%", String.valueOf(settings.getInt("Command Cooldowns.AdminChat")))));
            return ReturnType.FAILURE;
        }

        final StringBuilder stringBuilder = new StringBuilder();

        for (String arg : args) stringBuilder.append(arg).append(" ");

        String message = stringBuilder.toString();

        if (user.getAdminChatLastMessage().equalsIgnoreCase(message)) {
            senderPlayer.sendMessage(formatAll(languageConfig.getString("General.Repeated Message")));
            return ReturnType.FAILURE;
        }

        Bukkit.getOnlinePlayers().stream().filter(receiverPlayer -> receiverPlayer.hasPermission("supercore.adminchat.receive")).forEach(receiverPlayer -> receiverPlayer.sendMessage(formatAll(replacePlaceholders(senderPlayer, languageConfig.getString("Command.AdminChat.Format"))).replace("%message%", message).replace("%playername%", receiverPlayer.getName())));

        user.setAdminChatCooldown(now + 1000 * 5);
        user.setAdminChatLastMessage(message);
        return ReturnType.SUCCESS;
    }

    @Override
    protected List<String> onTab(SuperCore instance, CommandSender sender, String... args) {
        return null;
    }

    @Override
    public String getPermissionNode() {
        return "supercore.adminchat.send";
    }

    @Override
    public String getSyntax() {
        return "/adminchat <message>";
    }

    @Override
    public String getDescription() {
        return "A special chat for admins.";
    }


}
