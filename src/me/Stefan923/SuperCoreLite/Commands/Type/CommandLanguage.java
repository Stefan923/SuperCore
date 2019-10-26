package me.Stefan923.SuperCoreLite.Commands.Type;

import me.Stefan923.SuperCoreLite.Commands.AbstractCommand;
import me.Stefan923.SuperCoreLite.Language.LanguageManager;
import me.Stefan923.SuperCoreLite.Main;
import me.Stefan923.SuperCoreLite.Utils.MessageUtils;
import me.Stefan923.SuperCoreLite.Utils.User;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CommandLanguage extends AbstractCommand implements MessageUtils {

    private HashMap<String, String> languages;

    public CommandLanguage() {
        super(true, true, "language");
        languages = new HashMap<>();
        for (LanguageManager languageManager : Main.instance.getLanguageManagers().values()) {
            languages.put(languageManager.getConfig().getString("Language Display Name").toLowerCase(), languageManager.getLanguageFileName());
        }
    }

    @Override
    protected AbstractCommand.ReturnType runCommand(Main instance, CommandSender sender, String... args) {
        Player senderPlayer = (Player) sender;
        User user = instance.getUser(senderPlayer);

        FileConfiguration languageConfig = instance.getLanguageManager(user.getLanguage()).getConfig();

        if (args.length != 1)
            return ReturnType.SYNTAX_ERROR;

        String language = args[0].toLowerCase();

        if (!languages.containsKey(language)) {
            StringBuilder stringBuilder = new StringBuilder(languageConfig.getString("General.Available Languages.Syntax"));
            String itemColor = languageConfig.getString("General.Available Languages.Item Color");
            String separator = languageConfig.getString("General.Available Languages.Separator");
            for (String listItem : languages.keySet())
                stringBuilder.append(itemColor).append(capitalizeFirstLetter(listItem)).append(separator);
            stringBuilder.setLength(stringBuilder.length() - separator.length());
            senderPlayer.sendMessage(formatAll(stringBuilder.toString()));
            return ReturnType.FAILURE;
        }

        String languageFile = languages.get(language);

        if (user.getLanguage().equalsIgnoreCase(languageFile)) {
            senderPlayer.sendMessage(formatAll(languageConfig.getString("General.Already Using Language")));
            return ReturnType.FAILURE;
        }

        user.setLanguage(languageFile);

        languageConfig = instance.getLanguageManager(languageFile).getConfig();

        senderPlayer.sendMessage(formatAll(languageConfig.getString("General.Language Changed").replace("%language%", capitalizeFirstLetter(language))));

        return ReturnType.SUCCESS;
    }

    @Override
    protected List<String> onTab(Main instance, CommandSender sender, String... args) {
        return new ArrayList<>(languages.keySet());
    }

    @Override
    public String getPermissionNode() {
        return "supercore.language";
    }

    @Override
    public String getSyntax() {
        return "/language <language_name>";
    }

    @Override
    public String getDescription() {
        return "A special chat to request help.";
    }

}
