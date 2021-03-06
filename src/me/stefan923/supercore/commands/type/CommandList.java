package me.stefan923.supercore.commands.type;

import me.stefan923.supercore.SuperCore;
import me.stefan923.supercore.utils.MessageUtils;
import me.stefan923.supercore.utils.PlayerUtils;
import me.stefan923.supercore.commands.AbstractCommand;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.Set;

public class CommandList extends AbstractCommand implements MessageUtils, PlayerUtils {

    public CommandList() {
        super(false, true, "list");
    }

    @Override
    protected ReturnType runCommand(SuperCore instance, CommandSender sender, String... args) {
        FileConfiguration config = instance.getSettingsManager().getConfig();
        FileConfiguration language = getLanguageConfig(instance, sender);

        String messageToSend = String.join("\n", language.getStringList("Command.List.Format"));

        for (String permission : config.getStringList("Command.List.Group Permissions")) {
            Set<Player> playerSet = onlinePlayers(sender, permission);

            messageToSend = messageToSend.replace("%list_size_" + permission + "%", String.valueOf(playerSet.size()));

            StringBuilder playerList = new StringBuilder();
            String separator = language.getString("Command.List.Separator");
            String nameColor = language.getString("Command.List.Name Color");

            for (Player onlinePlayer : playerSet) {
                playerList.append(nameColor).append(onlinePlayer.getName()).append(separator);
            }

            if (playerList.length() > 0)
                playerList.setLength(playerList.length() - separator.length());

            messageToSend = messageToSend.replace("%list_" + permission + "%", playerSet.size() != 0 ? playerList.toString() : nameColor + "none");
        }

        sender.sendMessage(formatAll(messageToSend));

        return ReturnType.SUCCESS;
    }

    @Override
    protected List<String> onTab(SuperCore instance, CommandSender sender, String... args) {
        return null;
    }

    @Override
    public String getPermissionNode() {
        return "supercore.list";
    }

    @Override
    public String getSyntax() {
        return "/list";
    }

    @Override
    public String getDescription() {
        return "Shows who's online.";
    }

}
