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

        import java.util.ArrayList;
        import java.util.List;

public class CommandHeal extends AbstractCommand implements MessageUtils, PlayerUtils {

    public CommandHeal() {
        super(false, true, "heal");
    }

    @Override
    protected AbstractCommand.ReturnType runCommand(SuperCore instance, CommandSender sender, String... args) {
        Player senderPlayer = (Player) sender;
        User user = instance.getUser(senderPlayer);

        FileConfiguration settings = instance.getSettingsManager().getConfig();
        FileConfiguration senderLanguage = instance.getLanguageManager(user.getLanguage()).getConfig();

        int length = args.length;

        if (length == 0) {
            senderPlayer.setHealth(senderPlayer.getMaxHealth());
            sender.sendMessage(formatAll(senderLanguage.getString("Command.Heal.Self-Healed")));
            return ReturnType.SUCCESS;
        }

        if (length == 1) {
            if (!senderPlayer.hasPermission("supercore.heal.others")) {
                sender.sendMessage(formatAll(senderLanguage.getString("General.No Permission").replace("%permission%", "supercore.heal.others")));
                return ReturnType.FAILURE;
            }
            Player targetPlayer = Bukkit.getPlayer(args[0]);
            if (targetPlayer == null) {
                senderPlayer.sendMessage(formatAll(senderLanguage.getString("General.Must Be Online")));
                return ReturnType.FAILURE;
            }
            User targetUser = instance.getUser(targetPlayer);
            FileConfiguration targetLanguage = instance.getLanguageManager(targetUser.getLanguage()).getConfig();
            targetPlayer.setHealth(targetPlayer.getMaxHealth());
            senderPlayer.sendMessage(formatAll(senderLanguage.getString("Command.Heal.Healed Others")
                    .replace("%target%", targetPlayer.getName())));
            targetPlayer.sendMessage(formatAll(targetLanguage.getString("Command.Heal.Healed By")
                    .replace("%sender%", senderPlayer.getName())));
            return ReturnType.SUCCESS;
        }

        return ReturnType.SYNTAX_ERROR;
    }

    @Override
    protected List<String> onTab(SuperCore instance, CommandSender sender, String... args) {
        List<String> tabStrings = new ArrayList<>();
        if (sender.hasPermission("supercore.heal.others") && args.length == 1)
            onlinePlayers(sender).forEach(player -> tabStrings.add(player.getName()));
        return tabStrings;
    }

    @Override
    public String getPermissionNode() {
        return "supercore.heal";
    }

    @Override
    public String getSyntax() {
        return "/heal [player]";
    }

    @Override
    public String getDescription() {
        return "Restores the player's health.";
    }

}
