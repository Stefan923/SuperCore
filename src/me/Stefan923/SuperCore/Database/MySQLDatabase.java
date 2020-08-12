package me.Stefan923.SuperCore.Database;

import java.sql.*;
import java.util.HashSet;
import java.util.Set;

public class MySQLDatabase extends Database {

    private String tablePrefix;
    private Connection connection;
    private String url;
    private String username;
    private String password;

    public MySQLDatabase(String tablePrefix, String host, Integer port, String dbname, String username, String password) throws SQLException {
        this.tablePrefix = tablePrefix;
        this.username = username;
        this.password = password;
        this.url = "jdbc:mysql://" + host + ":" + port + "/" + dbname;
        connection = DriverManager.getConnection(url, username, password);
        for (TableType tableType : TableType.values()) {
            initTable(tableType.getTableInit());
        }
    }

    @Override
    public ResultSet get(TableType tableType, String playerKey) {
        try {
            PreparedStatement preparedStatement = getConnection().prepareStatement("SELECT * FROM %table WHERE `playerKey` = ?;".replace("%table", tablePrefix + "_" + tableType.getTableName()));
            preparedStatement.setString(1, playerKey);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet;
            }
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public ResultSet get(TableType tableType, String playerKey, String key) {
        try {
            PreparedStatement preparedStatement = getConnection().prepareStatement("SELECT `%key` FROM %table WHERE `playerKey` = ?;".replace("%table", tablePrefix + "_" + tableType.getTableName()).replace("%key", key));
            preparedStatement.setString(1, playerKey);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet;
            }
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void delete(TableType tableType, String playerKey) {
        new Thread(() -> {
            try {
                PreparedStatement statement = getConnection().prepareStatement("DELETE FROM %table WHERE `playerKey` = ?".replace("%table", tablePrefix + "_" + tableType.getTableName()));
                statement.setString(1, playerKey);
                statement.executeUpdate();
                statement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }).start();
    }

    @Override
    public void put(TableType tableType, String playerKey, String key, String value) {
        new Thread(() -> {
            try {
                if (value != null) {
                    PreparedStatement preparedStatement = getConnection().prepareStatement("INSERT INTO %table (`playerKey`, `%key`) VALUES (?,?) ON DUPLICATE KEY UPDATE `%key` = ?".replace("%table", tablePrefix + "_" + tableType.getTableName()).replace("%key", key));
                    preparedStatement.setString(1, playerKey);
                    preparedStatement.setString(2, value);
                    preparedStatement.setString(3, value);
                    preparedStatement.executeUpdate();
                    preparedStatement.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }).start();
    }

    @Override
    public boolean has(TableType tableType, String playerKey) {
        boolean result = false;
        try {
            PreparedStatement statement = getConnection().prepareStatement("SELECT `playerKey` FROM %table WHERE `playerKey` = ?".replace("%table", tablePrefix + "_" + tableType.getTableName()));
            statement.setString(1, playerKey);
            ResultSet rs = statement.executeQuery();
            result = rs.next();
            rs.close();
            connection.close();
            return result;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public void clear(TableType tableType) {
        try {
            PreparedStatement statement = getConnection().prepareStatement("TRUNCATE TABLE %table".replace("%table", tablePrefix + "_" + tableType.getTableName()));
            statement.executeQuery();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Set<String> getKeys(TableType tableType) {
        Set<String> tempset = new HashSet<>();
        try {
            PreparedStatement statement = getConnection().prepareStatement("SELECT `playerKey` FROM %table".replace("%table", tablePrefix + "_" + tableType.getTableName()));
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                tempset.add(rs.getString("id"));
            }
            rs.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tempset;
    }

    private void initTable(String tableInit) throws SQLException {
        String tablequery = tableInit.replace("%table_prefix%", tablePrefix);
        try {
            PreparedStatement preparedStatement = getConnection().prepareStatement(tablequery);
            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (SQLException exception) {

        }
    }

    private void connect() {
        try {
            this.connection = DriverManager.getConnection(this.url, this.username, this.password);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    private Connection getConnection() throws SQLException {
        if (this.connection == null || !this.connection.isValid(5)) {
            this.connect();
        }
        return this.connection;
    }

}
