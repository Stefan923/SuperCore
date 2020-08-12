package me.stefan923.supercore.api;

import me.stefan923.supercore.utils.User;

public class UserAPI {

    private User user;

    public UserAPI(User user) {
        this.user = user;
    }

    /**
     * Returns true if user has god mode enabled
     *
     * @return godMode
     */
    public boolean isGodMode() {
        return (user != null) ? user.getGod() : false;
    }

    /**
     * Returns user's language file name
     *
     * @return language
     */
    public String getLanguage() {
        return (user != null) ? user.getLanguage() : null;
    }

    /**
     * Returns user's nickname
     *
     * @return nickname
     */
    public String getNickname() {
        return (user != null) ? user.getNickname() : null;
    }

}
