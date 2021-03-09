package me.stefan923.supercore.database.sql;

import me.stefan923.supercore.SuperCore;
import me.stefan923.supercore.configuration.language.ILanguage;
import me.stefan923.supercore.configuration.language.LanguageManager;
import me.stefan923.supercore.configuration.setting.Setting;
import me.stefan923.supercore.database.IDatabase;
import me.stefan923.supercore.user.IUser;
import me.stefan923.supercore.user.User;
import me.stefan923.supercore.util.LoggerUtil;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;
import java.sql.*;
import java.util.*;

public class H2Database implements IDatabase {

    private static final String MYSQL_SCHEMA_FILENAME = "mysql/MySQLSchema.sql";
    private static final String MYSQL_TRIGGERS_FILENAME = "mysql/MySQLTriggers.sql";
    private static final String MYSQL_VIEWS_FILENAME = "mysql/MySQLViews.sql";

    private static final LanguageManager languageManager = LanguageManager.getInstance();

    private final String url;
    private final String tablePrefix;
    private final boolean useUUID;

    private Connection connection;

    public H2Database(String tablePrefix, boolean useUUID) throws SQLException, ClassNotFoundException {
        String absolutePath = SuperCore.getInstance().getDataFolder().getAbsolutePath();
        this.url = "jdbc:h2:" + absolutePath + File.separator + "database;mode=MySQL";
        this.tablePrefix = tablePrefix;
        this.useUUID = useUUID;

        Class.forName("org.h2.Driver");

        connection = getConnection();
    }

    @Override
    public void init() {
        try {
            executeAll(SchemaReader.getStatements(getClass().getClassLoader().getResourceAsStream(MYSQL_SCHEMA_FILENAME)));
            executeAll(SchemaReader.getStatements(getClass().getClassLoader().getResourceAsStream(MYSQL_TRIGGERS_FILENAME)));
            executeAll(SchemaReader.getStatements(getClass().getClassLoader().getResourceAsStream(MYSQL_VIEWS_FILENAME)));
        } catch (IOException e) {
            LoggerUtil.sendError("H2Database#getIgnoredUsers(String): Couldn't open MySQL database init files.");
        }
    }

    @Override
    public boolean createUser(UUID playerUUID, String playerName) {
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = getConnection().prepareStatement(SQLStatement.CREATE_USER.replace("{prefix}", tablePrefix));
            preparedStatement.setString(1, String.valueOf(playerUUID));
            preparedStatement.setString(2, String.valueOf(playerName));
            preparedStatement.execute();
        } catch (SQLException e) {
            LoggerUtil.sendError("H2Database#createUser(UUID, String): Couldn't create a new user: User{uuid = " + playerUUID + ", name = " + playerName + "}");
            return false;
        } finally {
            try {
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
            } catch (SQLException e) {
                LoggerUtil.sendError("H2Database#createUser(UUID, String): Couldn't close preparedStatement.");
            }
        }

        return true;
    }

    @Override
    public boolean deleteUser(IUser user) {
        Player player = user.getPlayer();

        return useUUID ? deleteUser(player.getUniqueId()) : deleteUser(player.getName());
    }

    @Override
    public IUser getUser(Player player) {
        UUID playerUUID = player.getUniqueId();
        User user = null;

        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            preparedStatement = getConnection()
                    .prepareStatement(SQLStatement.GET_USER_BY_UUID.replace("{prefix}", tablePrefix));
            preparedStatement.setString(1, String.valueOf(playerUUID));

            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                String foundUUID = resultSet.getString("uuid");
                Map<String, Location> homes = getUserHomes(playerUUID);
                List<String> ignoredUsers = getIgnoredUsers(foundUUID);

                ILanguage language = languageManager.getLanguageByName(resultSet.getString("language"));
                if (language == null) {
                    language = languageManager.getLanguageByFileName(Setting.DEFAULT_LANGUAGE);
                }

                user = new User(player,
                        language,
                        resultSet.getString("customNickname"),
                        resultSet.getBoolean("godMode"),
                        resultSet.getBoolean("receivingMessages"),
                        ignoredUsers,
                        homes);
            }
        } catch (SQLException e) {
            LoggerUtil.sendError("H2Database#getuser(Player): Couldn't select this user: User{uuid = " + playerUUID + "}");
        } finally {
            try {
                if (resultSet != null) {
                    resultSet.close();
                }
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
            } catch (SQLException e) {
                LoggerUtil.sendError("H2Database#getuser(Player): Couldn't close preparedStatement or resultSet.");
            }
        }

        return user;
    }

    @Override
    public Map<String, Location> getUserHomes(UUID playerUUID) {
        return getUserHomes(String.valueOf(playerUUID));
    }

    @Override
    public List<String> getIgnoredUsers(UUID playerUUID) {
        return getIgnoredUsers(String.valueOf(playerUUID));
    }

    private void connect() {
        try {
            this.connection = DriverManager.getConnection(this.url);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private Connection getConnection() throws SQLException {
        if (this.connection == null || !this.connection.isValid(5)) {
            this.connect();
        }

        return this.connection;
    }

    private boolean deleteUser(UUID playerUUID) {
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = getConnection().prepareStatement(SQLStatement.DELETE_USER_BY_UUID.replace("{prefix}", tablePrefix));
            preparedStatement.setString(1, String.valueOf(playerUUID));
            preparedStatement.execute();
        } catch (SQLException e) {
            LoggerUtil.sendError("H2Database#deleteUser(UUID): Couldn't delete this user: User{uuid = " + playerUUID + "}");
            return false;
        } finally {
            try {
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
            } catch (SQLException e) {
                LoggerUtil.sendError("H2Database#deleteUser(UUID): Couldn't close preparedStatement.");
            }
        }

        return true;
    }

    private boolean deleteUser(String playerName) {
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = getConnection().prepareStatement(SQLStatement.DELETE_USER_BY_NAME.replace("{prefix}", tablePrefix));
            preparedStatement.setString(1, playerName);
            preparedStatement.execute();
        } catch (SQLException e) {
            LoggerUtil.sendError("H2Database#deleteUser(String): Couldn't delete this user: User{name = " + playerName + "}");
            return false;
        } finally {
            try {
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
            } catch (SQLException e) {
                LoggerUtil.sendError("H2Database#deleteUser(String): Couldn't close preparedStatement.");
            }
        }

        return true;
    }

    private Map<String, Location> getUserHomes(String playerUUID) {
        Map<String, Location> homes = new HashMap<>();

        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            preparedStatement = getConnection()
                    .prepareStatement(SQLStatement.GET_USER_HOMES.replace("{prefix}", tablePrefix));
            preparedStatement.setString(1, playerUUID);

            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                World world = Bukkit.getWorld(resultSet.getString("world"));

                if (world != null) {
                    homes.put(resultSet.getString("name"),
                            new Location(world,
                                    resultSet.getDouble("x"),
                                    resultSet.getDouble("y"),
                                    resultSet.getDouble("z"),
                                    resultSet.getFloat("yaw"),
                                    resultSet.getFloat("pitch")));
                }
            }
        } catch (SQLException e) {
            LoggerUtil.sendError("H2Database#getUserHomes(String): Couldn't select this user: User{uuid = " + playerUUID + "}");
        } finally {
            try {
                if (resultSet != null) {
                    resultSet.close();
                }
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
            } catch (SQLException e) {
                LoggerUtil.sendError("H2Database#getUserHomes(String): Couldn't close preparedStatement or resultSet.");
            }
        }

        return homes;
    }

    private List<String> getIgnoredUsers(String playerUUID) {
        List<String> ignoredPlayers = new ArrayList<>();

        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            preparedStatement = getConnection()
                    .prepareStatement(SQLStatement.GET_IGNORED_USERS.replace("{prefix}", tablePrefix));
            preparedStatement.setString(1, playerUUID);

            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                ignoredPlayers.add(resultSet.getString("ignoredUsername"));
            }
        } catch (SQLException e) {
            LoggerUtil.sendError("H2Database#getIgnoredUsers(String): Couldn't select this user: User{uuid = " + playerUUID + "}");
        } finally {
            try {
                if (resultSet != null) {
                    resultSet.close();
                }
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
            } catch (SQLException e) {
                LoggerUtil.sendError("H2Database#getIgnoredUsers(String): Couldn't close preparedStatement or resultSet.");
            }
        }

        return ignoredPlayers;
    }

    private void executeAll(List<String> queries) {
        for (String query : queries) {
            try {
                PreparedStatement preparedStatement = getConnection().prepareStatement(query.replace("{prefix}", tablePrefix));
                preparedStatement.execute();
            } catch (SQLException e) {
                LoggerUtil.sendError("H2Database#getIgnoredUsers(String): Couldn't execute the following sql query:\n" + query);
            }
        }
    }
    
}
