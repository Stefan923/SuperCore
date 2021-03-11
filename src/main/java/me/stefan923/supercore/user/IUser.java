package me.stefan923.supercore.user;

import me.stefan923.supercore.exception.HomeNotFoundException;
import me.stefan923.supercore.configuration.language.ILanguage;
import org.bukkit.Location;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public interface IUser {

    /**
     * Returns user's UUID.
     * @return uuid - UUID
     */
    public UUID getUUID();

    /**
     * Returns user's name.
     * @return userName - String
     */
    public String getUserName();

    /**
     * Returns user's displayed name.
     * @return displayName - String
     */
    public String getDisplayName();

    /**
     * Sets user's displayed name.
     * @param displayName - the name to be set
     */
    public void setDisplayName(String displayName);

    /**
     * Returns user's language.
     * @return language - ILanguage
     */
    public ILanguage getLanguage();

    /**
     * Sets user's language.
     * @param language - user's new language - Language
     */
    public void setLanguage(ILanguage language);

    /**
     * Returns true if user's god mode is enabled.
     * @return godMode - boolean
     */
    public boolean isGodMode();

    /**
     * Sets user's god mode. (true - enabled, false - disabled)
     * @param godMode - boolean
     */
    public void setGodMode(boolean godMode);

    /**
     * Returns true if the user can receive private messages.
     * @return receivingMessages - boolean
     */
    public boolean isReceivingMessages();

    /**
     * Sets if the user can receive private messages or not.
     * @param receivingMessages - boolean
     */
    public void setReceivingMessages(boolean receivingMessages);

    /**
     * Returns true if the user ignores the given player.
     * @param playerName - given player's name
     * @return isIgnoring - boolean
     */
    public boolean isIgnoring(String playerName);

    /**
     * Returns a copy of user's list of ignored players.
     * @return ignoredPlayers - List<String> - user's list of ignored players
     */
    public List<String> getIgnoredPlayers();

    /**
     * Adds a given player to user's list of ignored players.
     * @param playerName - given player's name
     * @return true if the given player has been added.
     */
    public boolean addIgnoredPlayer(String playerName);

    /**
     * Removes a given player from user's list of ignored players.
     * @param playerName - given player's name
     * @return true if the given player has been removed.
     */
    public boolean removeIgnoredPlayer(String playerName);

    /**
     * Returns a copy of user's list of saved locations. (homes)
     * @return houses - HashMap<String, Location>
     */
    public Map<String, Location> getHomes();

    /**
     * Adds a new location to user's list of saved locations. (homes)
     * If there is already a home with this name, it's location will be replaced.
     * @param name - location's name
     * @param location - the location to be saved
     * @return true if the home has been added
     */
    public boolean addHome(String name, Location location);

    /**
     * Removes a new location from user's list of saved locations. (homes)
     * @param name - location's name
     * @return true if the home has been removed
     */
    public boolean removeHome(String name) throws HomeNotFoundException;

}
