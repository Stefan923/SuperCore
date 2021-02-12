package me.stefan923.supercore.user;

import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class User implements IUser {

    private final Player player;

    private String language;
    private String nickname;

    private boolean godMode;
    private boolean receivingMessages;

    private List<UUID> ignoredPlayers = new ArrayList<>();

    public User(Player player) {
        this.player = player;
    }

    public User(Player player, String language, String nickname) {
        this.player = player;
        this.language = language;
        this.nickname = nickname;
    }

    public User(Player player, String language, String nickname, boolean godMode, boolean receivingMessages, List<UUID> ignoredPlayers) {
        this.player = player;
        this.language = language;
        this.nickname = nickname;
        this.godMode = godMode;
        this.receivingMessages = receivingMessages;
        this.ignoredPlayers = ignoredPlayers;
    }

    @Override
    public Player getPlayer() {
        return player;
    }

    @Override
    public String getNickname() {
        return nickname != null ? nickname : player.getName();
    }

    @Override
    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    @Override
    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    @Override
    public boolean isGodMode() {
        return godMode;
    }

    @Override
    public void setGodMode(boolean godMode) {
        this.godMode = godMode;
    }

    @Override
    public boolean isReceivingMessages() {
        return receivingMessages;
    }

    @Override
    public void setReceivingMessages(boolean receivingMessages) {
        this.receivingMessages = receivingMessages;
    }

    @Override
    public boolean isIgnoring(UUID uuid) {
        return ignoredPlayers.contains(uuid);
    }

    @Override
    public List<UUID> getIgnoredPlayers() {
        return new ArrayList<>(ignoredPlayers);
    }

    @Override
    public boolean addIgnoredPlayer(UUID uuid) {
        return ignoredPlayers.add(uuid);
    }

    @Override
    public boolean removeIgnoredPlayer(UUID uuid) {
        return ignoredPlayers.remove(uuid);
    }

}
