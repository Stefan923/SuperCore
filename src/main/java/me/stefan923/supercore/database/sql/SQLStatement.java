package me.stefan923.supercore.database.sql;

public final class SQLStatement {

    public static final String CREATE_USER = "INSERT INTO `{prefix}users` (`uuid`, `username`) VALUE (?, ?);";

    public static final String ADD_USER_HOME = "INSERT INTO `{prefix}user_homes` (`userUUID`, `name`, `x`, `y`, `z`, `picth`, `yaw`) VALUE (?, ?, ?, ?, ?, ?, ?);";
    public static final String ADD_IGNORED_USER = "INSERT INTO `{prefix}ignored_users` (`userUUID`, `ignoredUUID`) VALUE (?, ?);";

    public static final String DELETE_USER_BY_UUID = "DELETE FROM `{prefix}users` WHERE `uuid` LIKE ?;";
    public static final String DELETE_USER_BY_NAME = "DELETE FROM `{prefix}users` WHERE `username` LIKE ?;";

    public static final String REMOVE_USER_HOME = "DELETE FROM `{prefix}user_homes` WHERE `userUUID` LIKE ? AND `name` LIKE ?;";
    public static final String REMOVE_IGNORED_USER = "DELETE FROM `{prefix}ignored_users` WHERE `userUUID` LIKE ? AND `ignoredUUID` LIKE ?;";

    public static final String GET_USER_BY_UUID = "SELECT * FROM `{prefix}view_users` WHERE `uuid` = ?;";
    public static final String GET_USER_BY_NAME = "SELECT * FROM `{prefix}view_users` WHERE `username` = ?;";
    public static final String GET_USER_HOMES = "SELECT * FROM `{prefix}view_user_homes` WHERE `userUUID` = ?;";
    public static final String GET_IGNORED_USERS = "SELECT * FROM `{prefix}view_ignored_users` WHERE `userUUID` = ?;";

    private SQLStatement() { }

}
