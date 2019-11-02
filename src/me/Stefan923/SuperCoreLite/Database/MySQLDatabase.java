package me.Stefan923.SuperCoreLite.Database;

import java.sql.*;
import java.util.HashSet;
import java.util.Set;

public class MySQLDatabase extends Database {

    private final String tablename;
    private Connection connection;

    public MySQLDatabase(String host, Integer port, String dbname, String tablename, String username, String password) throws SQLException {
        this.tablename = tablename;
        String url = "jdbc:mysql://" + host + ":" + port + "/" + dbname;
        connection = DriverManager.getConnection(url, username, password);
        initTable();
    }

    @Override
    public ResultSet get(String playerKey) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM %table WHERE `playerKey` = ?;".replace("%table",tablename));
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
    public ResultSet get(String playerKey, String key) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT `%key` FROM %table WHERE `id` = ?;".replace("%table",tablename).replace("%key", key));
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
    public void delete(String playerKey) {
        new Thread(() -> {
            try {
                PreparedStatement statement = connection.prepareStatement("DELETE FROM %table WHERE `playerKey` = ?".replace("%table", tablename));
                statement.setString(1, playerKey);
                statement.executeUpdate();
                statement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }).start();
    }

    @Override
    public void put(String playerKey, String key, String value) {
        new Thread(() -> {
            try {
                if (value != null) {
                    PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO %table (`playerKey`, `%key`) VALUES (?,?) ON DUPLICATE KEY UPDATE `%key` = ?".replace("%table", tablename).replace("%key", key));
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
    public boolean has(String playerKey) {
        boolean result = false;
        try {
            PreparedStatement statement = connection.prepareStatement("SELECT `id` FROM %table WHERE `playerKey` = ?".replace("%table", tablename));
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
    public void clear() {
        try {
            PreparedStatement statement = connection.prepareStatement("TRUNCATE TABLE %table".replace("%table", tablename));
            statement.executeQuery();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Set<String> getKeys() {
        Set<String> tempset = new HashSet<>();
        try {
            PreparedStatement statement = connection.prepareStatement("SELECT `id` FROM %table".replace("%table", tablename));
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

    private void initTable() throws SQLException {
        String tablequery = "CREATE TABLE IF NOT EXISTS %table (`id` INT NOT NULL AUTO_INCREMENT, `playerKey` VARCHAR(36) PRIMARY KEY, `language` VARCHAR(36), `nickname` VARCHAR(256));".replace("%table", tablename);
        PreparedStatement preparedStatement = connection.prepareStatement(tablequery);
        preparedStatement.executeUpdate();
        preparedStatement.close();
        preparedStatement = connection.prepareStatement("IF NOT EXISTS( SELECT `column_name` FROM INFORMATION_SCHEMA.COLUMNS WHERE table_name = '%table' AND table_schema = 'database' AND `column_name` = 'nickname') THEN ALTER TABLE `%table` ADD `nickname` VARCHAR(256) DEFAULT NULL; END IF;");
        preparedStatement.executeUpdate();
        preparedStatement.close();
    }

}
