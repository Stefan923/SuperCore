package me.Stefan923.SuperCoreLite.Utils;

import me.Stefan923.SuperCoreLite.Database.Database;
import me.Stefan923.SuperCoreLite.SuperCore;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.sql.ResultSet;
import java.sql.SQLException;

public class User {

    private SuperCore plugin;

    private Player player;

    private String language;
    private String nickname;
    private boolean god;

    private String adminChatLastMessage;
    private String donorChatLastMessage;

    private long adminChatCooldown;
    private long donorChatCooldown;

    public User(Player player) {
        plugin = SuperCore.getInstance();
        FileConfiguration settings = plugin.getSettingsManager().getConfig();
        Database database = plugin.getDatabase("supercore_users");

        this.player = player;

        this.language = settings.getString("Languages.Default Language").toLowerCase();
        this.nickname = null;
        this.god = false;

        this.adminChatLastMessage = "";
        this.donorChatLastMessage = "";

        long now = System.currentTimeMillis();
        this.adminChatCooldown = now;
        this.donorChatCooldown = now;

        if (database.has(player.getName())) {
            ResultSet resultSet = database.get(player.getName());
            try {
                this.language = resultSet.getString("language");
                this.nickname = resultSet.getString("nickname");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            database.put(player.getName(), "language", this.language);
        }

        if (!plugin.getLanguageManagers().containsKey(language)) {
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
        plugin.getDatabase("supercore_users").put(player.getName(), "language", language);
    }

    public void setGod(boolean god) {
        this.god = god;
    }

    public boolean getGod() {
        return god;
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
        plugin.getDatabase("supercore_users").put(player.getName(), "nickname", nickname);
    }

}
