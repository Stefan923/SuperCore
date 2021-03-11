package me.stefan923.supercore.database.sql;

import me.stefan923.supercore.SuperCore;
import me.stefan923.supercore.util.LoggerUtil;

import java.io.File;
import java.io.IOException;
import java.sql.*;
import java.util.*;

public class H2Database extends SQLDatabase {

    private static final String H2_SCHEMA_FILENAME = "h2/H2Schema.sql";
    private static final String H2_VIEWS_FILENAME = "h2/H2Views.sql";

    public H2Database(String tablePrefix, boolean useUUID) throws SQLException, ClassNotFoundException {
        super(tablePrefix, "jdbc:h2:" + SuperCore.getInstance().getDataFolder().getAbsolutePath() +
                File.separator + "database;mode=MySQL", useUUID);

        Class.forName("org.h2.Driver");
    }

    @Override
    public void init() {
        try {
            executeAll(SchemaReader.getStatements(getClass().getClassLoader().getResourceAsStream(H2_SCHEMA_FILENAME)));
            executeAll(SchemaReader.getStatements(getClass().getClassLoader().getResourceAsStream(H2_VIEWS_FILENAME)));
        } catch (IOException e) {
            LoggerUtil.sendSevere("H2Database#getIgnoredUsers(String): Couldn't open MySQL database init files.");
        }
    }

    @Override
    public boolean createUser(UUID playerUUID, String playerName) {
        if (super.createUser(playerUUID, playerName)) {
            return createUserData(playerUUID) && createUserSettings(playerUUID);
        }

        return false;
    }

    @Override
    protected void connect() {
        try {
            connection = DriverManager.getConnection(this.url);
        } catch (SQLException e) {
            LoggerUtil.sendSevere("H2Database#connect(): Couldn't establish the connection to the database.");
        }
    }

    private boolean createUserData(UUID playerUUID) {
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = getConnection().prepareStatement(SQLStatement.CREATE_USER_DATA.replace("{prefix}", tablePrefix));
            preparedStatement.setString(1, String.valueOf(playerUUID));
            preparedStatement.execute();
        } catch (SQLException e) {
            LoggerUtil.sendSevere("H2Database#createUserData(UUID): Couldn't create a new user: User{uuid = " + playerUUID + "}");
            return false;
        } finally {
            try {
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
            } catch (SQLException e) {
                LoggerUtil.sendSevere("H2Database#createUserData(UUID): Couldn't close preparedStatement.");
            }
        }

        return true;
    }

    private boolean createUserSettings(UUID playerUUID) {
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = getConnection().prepareStatement(SQLStatement.CREATE_USER_SETTINGS.replace("{prefix}", tablePrefix));
            preparedStatement.setString(1, String.valueOf(playerUUID));
            preparedStatement.execute();
        } catch (SQLException e) {
            LoggerUtil.sendSevere("H2Database#createUserSettings(UUID): Couldn't create a new user: User{uuid = " + playerUUID + "}");
            return false;
        } finally {
            try {
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
            } catch (SQLException e) {
                LoggerUtil.sendSevere("H2Database#createUserSettings(UUID): Couldn't close preparedStatement.");
            }
        }

        return true;
    }
    
}
