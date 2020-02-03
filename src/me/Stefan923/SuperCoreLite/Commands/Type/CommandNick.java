package me.Stefan923.SuperCoreLite.Commands.Type;

import me.Stefan923.SuperCoreLite.Commands.AbstractCommand;
import me.Stefan923.SuperCoreLite.SuperCore;
import me.Stefan923.SuperCoreLite.Utils.MessageUtils;
import me.Stefan923.SuperCoreLite.Utils.PlayerUtils;
import me.Stefan923.SuperCoreLite.Utils.User;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.List;

public class CommandNick extends AbstractCommand implements MessageUtils, PlayerUtils {

    public CommandNick() {
        super(true, true, "nick");
    }

    @Override
    protected AbstractCommand.ReturnType runCommand(SuperCore instance, CommandSender sender, String... args) {

        if (args.length != 1)
            return ReturnType.SYNTAX_ERROR;

        Player senderPlayer = (Player) sender;
        User user = instance.getUser(senderPlayer);

        FileConfiguration config = instance.getSettingsManager().getConfig();
        FileConfiguration language = instance.getLanguageManager(user.getLanguage()).getConfig();

        Integer maxLength = config.getInt("Nick.Maximum Length");
        if (maxLength < removeFormat(args[0]).length()) {
            senderPlayer.sendMessage(formatAll(language.getString("Command.Nick.Maximum Length").replace("%length%", String.valueOf(maxLength))));
            return ReturnType.FAILURE;
        }

        if (!senderPlayer.hasPermission("supercore.nick.format")) {
            senderPlayer.sendMessage(formatAll(language.getString("Command.Nick.Formatting Codes")));
            return ReturnType.FAILURE;
        }

        user.setNickname(args[0]);
        senderPlayer.sendMessage(formatAll(language.getString("General.Nickname Changed").replace("%nickname%", args[0])));
        return ReturnType.SUCCESS;
    }

    @Override
    protected List<String> onTab(SuperCore instance, CommandSender sender, String... args) {
        return null;
    }

    @Override
    public String getPermissionNode() {
        return "supercore.nick";
    }

    @Override
    public String getSyntax() {
        return "/nick <nickname>";
    }

    @Override
    public String getDescription() {
        return "Change your nickname.";
    }

}
