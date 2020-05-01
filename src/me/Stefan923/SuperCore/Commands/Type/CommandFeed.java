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
import org.bukkit.event.entity.FoodLevelChangeEvent;

import java.util.ArrayList;
import java.util.List;

public class CommandFeed extends AbstractCommand implements MessageUtils, PlayerUtils {

    public CommandFeed() {
        super(false, true, "feed");
    }

    @Override
    protected ReturnType runCommand(SuperCore instance, CommandSender sender, String... args) {
        FileConfiguration senderLanguage = getLanguageConfig(instance, sender);

        int length = args.length;

        if (length == 0 || args[0].equalsIgnoreCase(sender.getName())) {
            if (!(sender instanceof Player)) {
                sender.sendMessage(formatAll(senderLanguage.getString("General.Must Be Player")));
                return ReturnType.FAILURE;
            }
            Player senderPlayer = (Player) sender;
            if (feed(instance, senderPlayer)) {
                sender.sendMessage(formatAll(senderLanguage.getString("Command.Feed.Self-Fed")));
            } else {
                sender.sendMessage(formatAll(senderLanguage.getString("General.Action Interrupted")));
            }
            return ReturnType.SUCCESS;
        }

        if (length == 1) {
            if (!sender.hasPermission("supercore.feed.others")) {
                sender.sendMessage(formatAll(senderLanguage.getString("General.No Permission").replace("%permission%", "supercore.feed.others")));
                return ReturnType.FAILURE;
            }

            Player targetPlayer = Bukkit.getPlayer(args[0]);
            if (targetPlayer == null) {
                sender.sendMessage(formatAll(senderLanguage.getString("General.Must Be Online")));
                return ReturnType.FAILURE;
            }

            User targetUser = instance.getUser(targetPlayer);
            FileConfiguration targetLanguage = instance.getLanguageManager(targetUser.getLanguage()).getConfig();
            if (feed(instance, targetPlayer)) {
                sender.sendMessage(formatAll(senderLanguage.getString("Command.Feed.Fed Others")
                        .replace("%target%", targetPlayer.getName())));
                targetPlayer.sendMessage(formatAll(targetLanguage.getString("Command.Feed.Fed By")
                        .replace("%sender%", sender.getName())));
            } else {
                sender.sendMessage(formatAll(senderLanguage.getString("General.Action Interrupted")));
            }
            return ReturnType.SUCCESS;
        }

        return ReturnType.SYNTAX_ERROR;
    }

    private boolean feed(SuperCore instance, Player player) {
        FoodLevelChangeEvent event = new FoodLevelChangeEvent(player, 30);
        instance.getServer().getPluginManager().callEvent(event);
        if (event.isCancelled()) {
            return false;
        }
        player.setFoodLevel(Math.min(event.getFoodLevel(), 20));
        player.setSaturation(10);
        player.setExhaustion(0F);
        return true;
    }

    @Override
    protected List<String> onTab(SuperCore instance, CommandSender sender, String... args) {
        List<String> tabStrings = new ArrayList<>();
        if (sender.hasPermission("supercore.feed.others") && args.length == 1)
            onlinePlayers(sender).forEach(player -> {
                String name = player.getName();
                if (name.toLowerCase().startsWith(args[0].toLowerCase()))
                    tabStrings.add(name);
            });
        return tabStrings;
    }

    @Override
    public String getPermissionNode() {
        return "supercore.feed";
    }

    @Override
    public String getSyntax() {
        return "/feed [player]";
    }

    @Override
    public String getDescription() {
        return "Restores the player's hunger and saturation.";
    }

}
