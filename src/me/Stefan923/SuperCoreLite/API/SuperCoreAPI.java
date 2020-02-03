package me.Stefan923.SuperCoreLite.API;

import me.Stefan923.SuperCoreLite.SuperCore;
import me.Stefan923.SuperCoreLite.Utils.User;

import java.util.HashMap;

public class SuperCoreAPI {

    private static SuperCoreAPI instance;
    private SuperCore plugin;

    public static SuperCoreAPI getInstance() {
        if (instance == null) {
            new SuperCoreAPI(SuperCore.getInstance());
        }
        return instance;
    }

    protected SuperCoreAPI(final SuperCore plugin) {
        this.plugin = plugin;
        SuperCoreAPI.instance = this;
    }

    /**
     * Returns a list of UserAPI objects of each online player or null if nobody is online
     *
     * @return UserAPI object
     */
    public HashMap<String, UserAPI> getOnlineUsers() {
        HashMap<String, UserAPI> hashMap = new HashMap<>();
        HashMap<String, User> usersMap = plugin.getUsers();
        if (usersMap.isEmpty())
            return null;
        usersMap.forEach((playerName, user) -> hashMap.put(playerName, new UserAPI(user)));
        return hashMap;
    }

    /**
     * Returns the UserAPI object of a player or null if user not online
     *
     * @param playerName Name of the user
     *
     * @return UserAPI object
     */
    public UserAPI getUser(String playerName) {
        return new UserAPI(plugin.getUser(playerName));
    }

}