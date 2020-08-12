package me.stefan923.supercore.commands.type;

import me.stefan923.supercore.SuperCore;
import me.stefan923.supercore.utils.MessageUtils;
import me.stefan923.supercore.utils.PlayerUtils;
import me.stefan923.supercore.utils.User;
import me.stefan923.supercore.commands.AbstractCommand;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.List;

public class CommandWhoIs extends AbstractCommand implements MessageUtils, PlayerUtils {

    public CommandWhoIs() {
        super(false, true, "whois");
    }

    @Override
    protected ReturnType runCommand(SuperCore instance, CommandSender sender, String... args) {
        FileConfiguration languageConfig = getLanguageConfig(instance, sender);

        if (args.length != 1)
            return ReturnType.SYNTAX_ERROR;

        String targetPlayerName = args[0];
        Player targetPlayer = Bukkit.getPlayer(targetPlayerName);

        if (targetPlayer == null) {
            sender.sendMessage(formatAll(languageConfig.getString("General.Must Be Online")));
            return ReturnType.FAILURE;
        }

        User targetUser = instance.getUser(targetPlayerName);

        String targetPlayerGamemode = "";
        switch (targetPlayer.getGameMode()) {
            case ADVENTURE:
                targetPlayerGamemode = "Adventure";
                break;
            case CREATIVE:
                targetPlayerGamemode = "Creative";
                break;
            case SPECTATOR:
                targetPlayerGamemode = "Spectator";
                break;
            case SURVIVAL:
                targetPlayerGamemode = "Survival";
                break;
        }

        sender.sendMessage(formatAll(String.join("\n", languageConfig.getStringList("Command.WhoIs.Format"))
                .replace("%playername%", targetPlayerName)
                .replace("%gamemode%", languageConfig.getString("General.Gamemode." + targetPlayerGamemode))
                .replace("%health%", String.valueOf(targetPlayer.getHealth()))
                .replace("%hunger%", String.valueOf(targetPlayer.getFoodLevel()))
                .replace("%godmode%", targetUser.getGod() ? languageConfig.getString("General.Word Yes") : languageConfig.getString("General.Word No"))
                .replace("%flying%", targetPlayer.getAllowFlight() ? languageConfig.getString("General.Word Yes") : languageConfig.getString("General.Word No"))
                .replace("%ipaddress%", targetPlayer.getAddress().toString().replace("/", "")))
                .replace("%location%", locationToString(targetPlayer.getLocation())));
        return ReturnType.SUCCESS;
    }

    @Override
    protected List<String> onTab(SuperCore instance, CommandSender sender, String... args) {
        return null;
    }

    @Override
    public String getPermissionNode() {
        return "supercore.whois";
    }

    @Override
    public String getSyntax() {
        return "/whois <player>";
    }

    @Override
    public String getDescription() {
        return "Shows info about a certain player.";
    }

}
