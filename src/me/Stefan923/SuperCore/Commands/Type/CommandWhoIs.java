package me.Stefan923.SuperCore.Commands.Type;

import me.Stefan923.SuperCore.Commands.AbstractCommand;
import me.Stefan923.SuperCore.SuperCore;
import me.Stefan923.SuperCore.Utils.MessageUtils;
import me.Stefan923.SuperCore.Utils.PlayerUtils;
import me.Stefan923.SuperCore.Utils.User;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.List;

public class CommandWhoIs extends AbstractCommand implements MessageUtils, PlayerUtils {

    public CommandWhoIs() {
        super(true, true, "whois");
    }

    @Override
    protected AbstractCommand.ReturnType runCommand(SuperCore instance, CommandSender sender, String... args) {
        Player senderPlayer = (Player) sender;
        User user = instance.getUser(senderPlayer);

        FileConfiguration languageConfig = instance.getLanguageManager(user.getLanguage()).getConfig();

        if (args.length != 1)
            return ReturnType.SYNTAX_ERROR;

        String targetPlayerName = args[0];
        Player targetPlayer = Bukkit.getPlayer(targetPlayerName);

        if (targetPlayer == null) {
            senderPlayer.sendMessage(languageConfig.getString("General.Must Be Online"));
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

        senderPlayer.sendMessage(formatAll(String.join("\n", languageConfig.getStringList("Command.WhoIs.Format"))
                .replace("%playername%", targetPlayerName)
                .replace("%gamemode%", languageConfig.getString("General.Gamemode." + targetPlayerGamemode))
                .replace("%health%", String.valueOf(targetPlayer.getHealth()))
                .replace("%hunger%", String.valueOf(targetPlayer.getFoodLevel()))
                .replace("%godmode%", user.getGod() ? languageConfig.getString("General.Word Yes") : languageConfig.getString("General.Word No"))
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
        return "/whois <player_name>";
    }

    @Override
    public String getDescription() {
        return "Shows info about a certain player.";
    }

}
