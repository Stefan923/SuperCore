package me.stefan923.supercore.user;

import me.stefan923.supercore.exception.HomeNotFoundException;
import me.stefan923.supercore.configuration.language.ILanguage;
import org.bukkit.Location;

import java.util.*;

public class User implements IUser {

    private final UUID uuid;
    private final String userName;

    private final List<String> ignoredPlayers;
    private final Map<String, Location> homes;

    private String displayName;
    private ILanguage language;
    private boolean godMode;
    private boolean receivingMessages;

    public User(UUID uuid, String userName, List<String> ignoredPlayers, Map<String, Location> homes, String displayName, ILanguage language, boolean godMode, boolean receivingMessages) {
        this.uuid = uuid;
        this.userName = userName;
        this.displayName = displayName;
        this.language = language;
        this.godMode = godMode;
        this.receivingMessages = receivingMessages;
        this.ignoredPlayers = ignoredPlayers;
        this.homes = homes;
    }

    @Override
    public UUID getUUID() {
        return uuid;
    }

    @Override
    public String getUserName() {
        return userName;
    }

    @Override
    public String getDisplayName() {
        return displayName != null ? displayName : userName;
    }

    @Override
    public void setDisplayName(String displayName) {
        this.displayName = displayName;
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return uuid.equals(user.uuid);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uuid);
    }

}
