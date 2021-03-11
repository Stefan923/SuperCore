package me.stefan923.supercore.database;

import me.stefan923.supercore.user.IUser;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public interface IDatabase {

    /**
     * Initializes the database.
     */
    public void init();

    /**
     * Inserts a new user into database.
     * @param playerUUID - user's uuid
     * @param playerName - user's name
     * @return true if the user has been inserted, or
     *         false if the user couldn't be inserted
     */
    public boolean createUser(UUID playerUUID, String playerName);

    /**
     * Deletes an user from database.
     * @param user - user's instance
     * @return true if the user has been deleted, or
     *         false if the user couldn't be deleted
     */
    public boolean deleteUser(IUser user);

    /**
     * Selects an user from database.
     * @param player - player's instance
     * @return requested user's instance if it could be find
     *         null if the user does not exist
     */
    public IUser getUser(Player player);

    /**
     * Selects and returns a map of user's homes.
     * @param playerUUID - player's uuid
     * @return a map whose keys are the names of the homes,
     *         and the values are their locations
     */
    public Map<String, Location> getUserHomes(UUID playerUUID);

    /**
     * Selects and returns the user's list of ignored players.
     * @param playerUUID - player's uuid
     * @return a map whose elements are the ignored players'
     *         usernames
     */
    public List<String> getIgnoredUsers(UUID playerUUID);

}
