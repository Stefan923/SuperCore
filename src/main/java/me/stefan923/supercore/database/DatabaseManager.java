package me.stefan923.supercore.database;

import me.stefan923.supercore.configuration.setting.Setting;
import me.stefan923.supercore.database.sql.H2Database;
import me.stefan923.supercore.database.sql.MySQLDatabase;
import me.stefan923.supercore.util.LoggerUtil;

import java.sql.SQLException;

public class DatabaseManager {

    private static final DatabaseManager INSTANCE = new DatabaseManager();

    private IDatabase database;

    public DatabaseManager() { }

    public static DatabaseManager getInstance() {
        return INSTANCE;
    }

    public IDatabase getDatabase() {
        if (database != null) {
            return database;
        }

        switch (Setting.STORAGE_TYPE.toLowerCase()) {
            case "mysql":
                return getMySQLDatabase();
            default:
                return getH2Database();
        }
    }

    public void reload() {
        database = null;
        getDatabase();
    }

    private IDatabase getMySQLDatabase() {
        try {
            database = new MySQLDatabase(Setting.STORAGE_TABLE_PREFIX, Setting.STORAGE_IP_ADDRESS, Setting.STORAGE_PORT, Setting.STORAGE_DATABASE, Setting.STORAGE_USER, Setting.STORAGE_PASSWORD, Setting.STORAGE_USE_UUID);
            LoggerUtil.sendInfo("The connection to the MySQL database has been successfully established.");
        } catch (SQLException e) {
            LoggerUtil.sendInfo("MySQL connection failed!");
            LoggerUtil.sendInfo("Reason: " + e.getMessage());

            if (database == null) {
                LoggerUtil.sendInfo("Attempting to use H2 database instead...");
                database = getH2Database();
            }
        }

        return database;
    }

    private IDatabase getH2Database() {
        try {
            database = new H2Database(Setting.STORAGE_TABLE_PREFIX, Setting.STORAGE_USE_UUID);
            LoggerUtil.sendInfo("The connection to the H2 database has been successfully established.");
        } catch (ClassNotFoundException | SQLException e) {
            LoggerUtil.sendInfo("H2 connection failed!");
            LoggerUtil.sendInfo("Reason: " + e.getMessage());
        }

        return database;
    }

}
