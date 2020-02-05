package me.Stefan923.SuperCore.API;

import me.Stefan923.SuperCore.Utils.User;

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
        return user.getGod();
    }

    /**
     * Returns user's language file name
     *
     * @return language
     */
    public String getLanguage() {
        return user.getLanguage();
    }

    /**
     * Returns user's nickname
     *
     * @return nickname
     */
    public String getNickname() {
        return user.getNickname();
    }

}
