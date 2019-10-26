package me.Stefan923.SuperCoreLite.Commands.Type;

import me.Stefan923.SuperCoreLite.Commands.AbstractCommand;
import me.Stefan923.SuperCoreLite.Main;
import me.Stefan923.SuperCoreLite.Utils.MessageUtils;
import me.Stefan923.SuperCoreLite.Utils.PlayerUtils;
import me.Stefan923.SuperCoreLite.Utils.User;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.Set;

public class CommandList extends AbstractCommand implements MessageUtils, PlayerUtils {

    public CommandList() {
        super(true, true, "list");
    }

    @Override
    protected AbstractCommand.ReturnType runCommand(Main instance, CommandSender sender, String... args) {
        Player senderPlayer = (Player) sender;
        User user = instance.getUser(senderPlayer);

        FileConfiguration config = instance.getSettingsManager().getConfig();
        FileConfiguration language = instance.getLanguageManager(user.getLanguage()).getConfig();

        String messageToSend = String.join("\n", language.getStringList("Command.List.Format"));

        for (String permission : config.getStringList("Command.List.Group Permissions")) {
            Set<Player> playerSet = onlinePlayers(senderPlayer, permission);

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

        senderPlayer.sendMessage(formatAll(messageToSend));

        return ReturnType.SUCCESS;
    }

    @Override
    protected List<String> onTab(Main instance, CommandSender sender, String... args) {
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
