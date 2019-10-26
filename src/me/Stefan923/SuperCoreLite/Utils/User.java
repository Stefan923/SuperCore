package me.Stefan923.SuperCoreLite.Utils;

import me.Stefan923.SuperCoreLite.Main;
import me.Stefan923.SuperCoreLite.Settings.SettingsManager;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

public class User {

    private Player player;

    private String language;

    private String adminChatLastMessage;
    private String donorChatLastMessage;

    private long adminChatCooldown;
    private long donorChatCooldown;

    public User(Player player) {
        FileConfiguration settings = Main.instance.getSettingsManager().getConfig();

        this.player = player;

        this.language = settings.getString("Languages.Default Language").toLowerCase();

        this.adminChatLastMessage = "";
        this.donorChatLastMessage = "";

        long now = System.currentTimeMillis();
        this.adminChatCooldown = now;
        this.donorChatCooldown = now;
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
}
