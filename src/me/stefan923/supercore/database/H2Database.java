package me.stefan923.supercore.database;

import me.stefan923.supercore.SuperCore;
import me.stefan923.supercore.utils.MessageUtils;

import java.io.File;
import java.sql.*;
import java.util.HashSet;
import java.util.Set;

public class H2Database extends Database implements MessageUtils {

    private final String tablePrefix;
    private Connection connection;

    public H2Database(SuperCore instance, String tablePrefix) throws SQLException, ClassNotFoundException {
        this.tablePrefix = tablePrefix;

        Class.forName("org.h2.Driver");

        String url = "jdbc:h2:" + instance.getDataFolder().getAbsolutePath() + File.separator + "database;mode=MySQL";
        connection = DriverManager.getConnection(url);
        if (connection == null)
            return;
        for (TableType tableType : TableType.values()) {
            PreparedStatement preparedStatement = connection.prepareStatement(tableType.getTableInit().replace("%table_prefix%", tablePrefix));
            preparedStatement.executeUpdate();
            preparedStatement.close();
        }
    }

    @Override
    public ResultSet get(TableType tableType, String playerKey) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM %table WHERE `playerKey` = ?;".replace("%table", tablePrefix + "_" + tableType.getTableName()));
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
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT `%key` FROM %table WHERE `playerKey` = ?;".replace("%table", tablePrefix + "_" + tableType.getTableName()).replace("%key", key));
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
                PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM %table WHERE `playerKey` = ?".replace("%table", tablePrefix + "_" + tableType.getTableName()));
                preparedStatement.setString(1, playerKey);
                preparedStatement.executeUpdate();
                preparedStatement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }).start();
    }

    @Override
    public void put(TableType tableType, String playerKey, String key, String value) {
        new Thread(() -> {
            try {
                PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO %table (`playerKey`, `%key`) VALUES (?,?) ON DUPLICATE KEY UPDATE `%key` = ?".replace("%table", tablePrefix + "_" + tableType.getTableName()).replace("%key", key));
                preparedStatement.setString(1, playerKey);
                preparedStatement.setString(2, value);
                preparedStatement.setString(3, value);
                preparedStatement.executeUpdate();
                preparedStatement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }).start();
    }

    @Override
    public boolean has(TableType tableType, String key) {
        boolean result = false;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT `playerKey` FROM %table WHERE `playerKey` = ?;".replace("%table", tablePrefix + "_" + tableType.getTableName()));
            preparedStatement.setString(1, key);
            ResultSet resultSet = preparedStatement.executeQuery();
            result = resultSet.next();
            resultSet.close();
            preparedStatement.close();
            return result;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public void clear(TableType tableType) {
        new Thread(() -> {
            try {
                PreparedStatement preparedStatement = connection.prepareStatement("DELETE * FROM %table;".replace("%table", tablePrefix + "_" + tableType.getTableName()));
                preparedStatement.executeQuery();
                preparedStatement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }).start();
    }

    public Set<String> getKeys(TableType tableType) {
        Set<String> tempSet = new HashSet<>();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT `playerKey` FROM %table;".replace("%table", tablePrefix + "_" + tableType.getTableName()));
            ResultSet result = preparedStatement.executeQuery();
            while (result.next())
                tempSet.add(result.getString("playerKey"));
            result.close();
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tempSet;
    }


}
