package me.Stefan923.SuperCoreLite.Database;

import me.Stefan923.SuperCoreLite.Main;
import me.Stefan923.SuperCoreLite.Utils.MessageUtils;

import java.io.File;
import java.sql.*;
import java.util.HashSet;
import java.util.Set;

public class H2Database extends Database implements MessageUtils {

    private final Main instance;
    private final String tablename;
    private Connection connection;

    public H2Database(String tablename) throws SQLException, ClassNotFoundException {
        this.instance = Main.instance;
        this.tablename = tablename;

        Class.forName("org.h2.Driver");

        String url = "jdbc:h2:" + instance.getDataFolder().getAbsolutePath() + File.separator + "database;mode=MySQL";
        connection = DriverManager.getConnection(url);
        if (connection == null)
            return;
        PreparedStatement stmt = connection.prepareStatement("CREATE TABLE IF NOT EXISTS %table (`id` INT NOT NULL AUTO_INCREMENT, `playerKey` VARCHAR(36) PRIMARY KEY, `language` VARCHAR(36));".replace("%table", tablename));
        stmt.executeUpdate();
        stmt.close();
    }

    @SuppressWarnings("unchecked")
    @Override
    public ResultSet get(String playerKey) {
        try {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM %table WHERE `playerKey` = ?;".replace("%table", tablename));
            statement.setString(1, playerKey);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return resultSet;
            }
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public ResultSet get(String playerKey, String key) {
        try {
            PreparedStatement statement = connection.prepareStatement("SELECT `%key` FROM %table WHERE `playerKey` = ?;".replace("%table", tablename).replace("%key", key));
            statement.setString(1, playerKey);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                statement.close();
                return resultSet;
            }
            statement.close();
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
                PreparedStatement statement = connection.prepareStatement("INSERT INTO %table (`playerKey`, `%key`) VALUES (?,?) ON DUPLICATE KEY UPDATE `%key` = ?".replace("%table", tablename).replace("%key", key));
                statement.setString(1, playerKey);
                statement.setString(2, value);
                statement.setString(3, value);
                statement.executeUpdate();
                statement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }).start();
    }

    @Override
    public boolean has(String key) {
        boolean result = false;
        try {
            PreparedStatement statement = connection.prepareStatement("SELECT `playerKey` FROM %table WHERE `playerKey` = ?;".replace("%table", tablename));
            statement.setString(1, key);
            ResultSet resultSet = statement.executeQuery();
            result = resultSet.next();
            resultSet.close();
            statement.close();
            return result;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public void clear() {
        new Thread(() -> {
            try {
                PreparedStatement preparedStatement = connection.prepareStatement("DELETE * FROM %table;".replace("%table", tablename));
                preparedStatement.executeQuery();
                preparedStatement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }).start();
    }

    public Set<String> getKeys() {
        Set<String> tempSet = new HashSet<>();
        try {
            PreparedStatement statement = connection.prepareStatement("SELECT `playerKey` FROM %table;".replace("%table", tablename));
            ResultSet result = statement.executeQuery();
            while (result.next())
                tempSet.add(result.getString("playerKey"));
            result.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tempSet;
    }


}
