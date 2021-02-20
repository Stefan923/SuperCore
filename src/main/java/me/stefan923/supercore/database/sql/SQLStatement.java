package me.stefan923.supercore.database.sql;

public final class SQLStatement {

    public static final String CREATE_USER = "CALL CREATE_USER(?, ?);";

    public static final String ADD_USER_HOME = "CALL ADD_USER_HOME(?, ?, ?, ?, ?, ?, ?);";
    public static final String ADD_IGNORED_USER = "CALL ADD_IGNORED_USER(?, ?);";

    public static final String DELETE_USER_BY_UUID = "CALL DELETE_USER_BY_UUID(?);";
    public static final String DELETE_USER_BY_NAME = "CALL DELETE_USER_BY_NAME(?);";

    public static final String REMOVE_USER_HOME = "CALL REMOVE_USER_HOME(?, ?);";
    public static final String REMOVE_IGNORED_USER = "CALL REMOVE_IGNORED_USER(?, ?);";

    public static final String GET_USER_BY_UUID = "SELECT * FROM `{prefix}view_users` WHERE `uuid` = ?;";
    public static final String GET_USER_BY_NAME = "SELECT * FROM `{prefix}view_users` WHERE `username` = ?;";
    public static final String GET_USER_HOMES = "SELECT * FROM `{prefix}view_user_homes` WHERE `userUUID` = ?;";
    public static final String GET_IGNORED_USERS = "SELECT * FROM `{prefix}view_ignored_users` WHERE `userUUID` = ?;";

    private SQLStatement() { }

}
