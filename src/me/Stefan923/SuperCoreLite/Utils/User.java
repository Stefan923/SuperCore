package me.Stefan923.SuperCoreLite.Utils;

import me.Stefan923.SuperCoreLite.Database.Database;
import me.Stefan923.SuperCoreLite.Main;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

public class User {

    private Player player;

    private String language;
    private String nickname;

    private String adminChatLastMessage;
    private String donorChatLastMessage;

    private long adminChatCooldown;
    private long donorChatCooldown;

    public User(Player player) {
        Main instance = Main.instance;
        FileConfiguration settings = instance.getSettingsManager().getConfig();
        Database database = instance.getDatabase("supercore_users");

        this.player = player;

        this.language = settings.getString("Languages.Default Language").toLowerCase();
        this.nickname = null;

        this.adminChatLastMessage = "";
        this.donorChatLastMessage = "";

        long now = System.currentTimeMillis();
        this.adminChatCooldown = now;
        this.donorChatCooldown = now;

        if (database.has(player.getName())) {
            ResultSet resultSet = database.get(player.getName());
            try {
                this.language = resultSet.getString("language");
                this.language = resultSet.getString("nickname");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            database.put(player.getName(), "language", this.language);
        }

        if (!instance.getLanguageManagers().containsKey(language)) {
            setLanguage(settings.getString("Languages.Default Language").toLowerCase());
        }
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
        Main.instance.getDatabase("supercore_users").put(player.getName(), "language", language);
    }

    public String getAdminChatLastMessage() {
        return adminChatLastMessage;
    }

    public void setAdminChatLastMessage(String adminChatLastMessage) {
        this.adminChatLastMessage = adminChatLastMessage;
    }

    public String getDonorChatLastMessage() {
        return donorChatLastMessage;
    }

    public void setDonorChatLastMessage(String donorChatLastMessage) {
        this.donorChatLastMessage = donorChatLastMessage;
    }

    public long getAdminChatCooldown() {
        return adminChatCooldown;
    }

    public void setAdminChatCooldown(long adminChatCooldown) {
        this.adminChatCooldown = adminChatCooldown;
    }

    public long getDonorChatCooldown() {
        return donorChatCooldown;
    }

    public void setDonorChatCooldown(long donorChatCooldown) {
        this.donorChatCooldown = donorChatCooldown;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
        Main.instance.getDatabase("supercore_users").put(player.getName(), "nickname", nickname);
    }
}
