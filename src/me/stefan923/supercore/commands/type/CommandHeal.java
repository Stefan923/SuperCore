package me.stefan923.supercore.commands.type;

        import me.stefan923.supercore.SuperCore;
        import me.stefan923.supercore.commands.exceptions.MissingPermissionException;
        import me.stefan923.supercore.utils.MessageUtils;
        import me.stefan923.supercore.utils.PlayerUtils;
        import me.stefan923.supercore.utils.User;
        import me.stefan923.supercore.commands.AbstractCommand;
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
    protected ReturnType runCommand(SuperCore instance, CommandSender sender, String... args) throws MissingPermissionException {

        FileConfiguration senderLanguage = getLanguageConfig(instance, sender);

        int length = args.length;

        if (length == 0 || args[0].equalsIgnoreCase(sender.getName())) {
            if (!(sender instanceof Player)) {
                sender.sendMessage(formatAll(senderLanguage.getString("General.Must Be Player")));
                return ReturnType.FAILURE;
            }
            Player senderPlayer = (Player) sender;
            senderPlayer.setHealth(senderPlayer.getMaxHealth());
            senderPlayer.setFoodLevel(20);
            senderPlayer.setFireTicks(0);
            sender.sendMessage(formatAll(senderLanguage.getString("Command.Heal.Self-Healed")));
            return ReturnType.SUCCESS;
        }

        if (length == 1) {
            if (!sender.hasPermission("supercore.heal.others")) {
                throw new MissingPermissionException("supercore.heal.others");
            }

            Player targetPlayer = Bukkit.getPlayer(args[0]);
            if (targetPlayer == null) {
                sender.sendMessage(formatAll(senderLanguage.getString("General.Must Be Online")));
                return ReturnType.FAILURE;
            }

            User targetUser = instance.getUser(targetPlayer);
            FileConfiguration targetLanguage = instance.getLanguageManager(targetUser.getLanguage()).getConfig();
            targetPlayer.setHealth(targetPlayer.getMaxHealth());
            targetPlayer.setFoodLevel(20);
            targetPlayer.setFireTicks(0);
            sender.sendMessage(formatAll(senderLanguage.getString("Command.Heal.Healed Others")
                    .replace("%target%", targetPlayer.getName())));
            targetPlayer.sendMessage(formatAll(targetLanguage.getString("Command.Heal.Healed By")
                    .replace("%sender%", sender.getName())));
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
