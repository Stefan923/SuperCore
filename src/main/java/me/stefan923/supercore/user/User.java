package me.stefan923.supercore.user;

import me.stefan923.supercore.exception.HomeNotFoundException;
import me.stefan923.supercore.configuration.language.ILanguage;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.*;

public class User implements IUser {

    private final Player player;

    private ILanguage language;
    private String nickname;

    private boolean godMode;
    private boolean receivingMessages;

    private List<String> ignoredPlayers = new ArrayList<>();

    private Map<String, Location> homes = new HashMap<>();

    public User(Player player) {
        this.player = player;
    }

    public User(Player player, ILanguage language, String nickname, boolean godMode, boolean receivingMessages, List<String> ignoredPlayers, Map<String, Location> homes) {
        this(player);
        this.language = language;
        this.nickname = nickname;
        this.godMode = godMode;
        this.receivingMessages = receivingMessages;
        this.ignoredPlayers = ignoredPlayers;
        this.homes = homes;
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
    public ILanguage getLanguage() {
        return language;
    }

    public void setLanguage(ILanguage language) {
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
    public boolean isIgnoring(String playerName) {
        return ignoredPlayers.contains(playerName);
    }

    @Override
    public List<String> getIgnoredPlayers() {
        return new ArrayList<>(ignoredPlayers);
    }

    @Override
    public boolean addIgnoredPlayer(String playerName) {
        return ignoredPlayers.add(playerName);
    }

    @Override
    public boolean removeIgnoredPlayer(String playerName) {
        return ignoredPlayers.remove(playerName);
    }

    @Override
    public Map<String, Location> getHomes() {
        return homes;
    }

    @Override
    public boolean addHome(String name, Location location) {
        homes.put(name.toLowerCase(), location);
        return true;
    }

    @Override
    public boolean removeHome(String name) throws HomeNotFoundException {
        String lowerCaseName = name.toLowerCase();
        if (!homes.containsKey(lowerCaseName)) {
            throw new HomeNotFoundException();
        }

        homes.remove(lowerCaseName);
        return true;
    }

    @Override
    public boolean teleportHome() throws HomeNotFoundException {
        return teleportHome("home");
    }

    @Override
    public boolean teleportHome(String name) throws HomeNotFoundException {
        Location location = homes.get(name.toLowerCase());

        if (location == null) {
            throw new HomeNotFoundException();
        }

        return player.teleport(location);
    }

}
