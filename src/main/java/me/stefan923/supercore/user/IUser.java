package me.stefan923.supercore.user;

import me.stefan923.supercore.exception.HomeNotFoundException;
import me.stefan923.supercore.language.Language;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public interface IUser {

    /**
     * Returns user's Player instance.
     * @return player - Player
     */
    public Player getPlayer();

    /**
     * Returns user's custom nickname.
     * @return nickname - String
     */
    public String getNickname();

    /**
     * Sets user's custom nickname.
     * @param nickname - the nickname to be set
     */
    public void setNickname(String nickname);

    /**
     * Returns user's language.
     * @return language - ILanguage
     */
    public Language getLanguage();

    /**
     * Sets user's language.
     * @param language - user's new language - Language
     */
    public void setLanguage(Language language);

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
     * @param uuid - given player's UUID
     * @return isIgnoring - boolean
     */
    public boolean isIgnoring(UUID uuid);

    /**
     * Returns a copy of user's list of ignored players.
     * @return ignoredPlayers - List<UUID> - user's list of ignored players
     */
    public List<UUID> getIgnoredPlayers();

    /**
     * Adds a given player to user's list of ignored players.
     * @param uuid - given player's UUID
     * @return true if the given player has been added.
     */
    public boolean addIgnoredPlayer(UUID uuid);

    /**
     * Removes a given player from user's list of ignored players.
     * @param uuid - given player's UUID
     * @return true if the given player has been removed.
     */
    public boolean removeIgnoredPlayer(UUID uuid);

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

    /**
     * Teleports the user to his default home.
     * @return true if the user has been teleported
     */
    public boolean teleportHome() throws HomeNotFoundException;

    /**
     * Teleports the user to a given home.
     * @param name - location's name
     * @return true if the user has been teleported
     */
    public boolean teleportHome(String name) throws HomeNotFoundException;

}
