package me.Stefan923.SuperCoreLite.Utils;

import org.bukkit.entity.Player;

public class User {

    private Player player;

    private String adminChatLastMessage;
    private String donorChatLastMessage;

    private long adminChatCooldown;
    private long donorChatCooldown;

    public User(Player player) {
        this.player = player;
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
