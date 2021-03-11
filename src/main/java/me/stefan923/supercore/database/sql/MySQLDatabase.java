package me.stefan923.supercore.database.sql;

import me.stefan923.supercore.util.LoggerUtil;

import java.io.IOException;
import java.sql.*;

public class MySQLDatabase extends SQLDatabase {

    private static final String MYSQL_SCHEMA_FILENAME = "mysql/MySQLSchema.sql";
    private static final String MYSQL_TRIGGERS_FILENAME = "mysql/MySQLTriggers.sql";
    private static final String MYSQL_VIEWS_FILENAME = "mysql/MySQLViews.sql";

    private final String username;
    private final String password;

    public MySQLDatabase(String tablePrefix, boolean useUUID, String host, int port, String databaseName, String username, String password) throws SQLException {
        super(tablePrefix, "jdbc:mysql://" + host + ":" + port + "/" + databaseName, useUUID);
        this.username = username;
        this.password = password;
    }

    @Override
    public void init() {
        try {
            executeAll(SchemaReader.getStatements(getClass().getClassLoader().getResourceAsStream(MYSQL_SCHEMA_FILENAME)));
            executeAll(SchemaReader.getStatements(getClass().getClassLoader().getResourceAsStream(MYSQL_TRIGGERS_FILENAME)));
            executeAll(SchemaReader.getStatements(getClass().getClassLoader().getResourceAsStream(MYSQL_VIEWS_FILENAME)));
        } catch (IOException e) {
            LoggerUtil.sendSevere("MySQLDatabase#getIgnoredUsers(String): Couldn't open MySQL database init files.");
        }
    }

    @Override
    protected void connect() {
        try {
            connection = DriverManager.getConnection(this.url, this.username, this.password);
        } catch (SQLException e) {
            LoggerUtil.sendSevere("MySQLDatabase#connect(): Couldn't establish the connection to the database.");
        }
    }

}
