package me.stefan923.supercore.user;

import org.bukkit.entity.Player;

import java.util.List;
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
     * @return language - String
     */
    public String getLanguage();

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

}
