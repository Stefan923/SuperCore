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

import java.sql.*;
import java.util.*;

public abstract class SQLDatabase implements IDatabase {

    private static final LanguageManager languageManager = SuperCore.getInstance().getLanguageManager();

    protected final String tablePrefix;
    protected final boolean useUUID;
    protected final String url;

    protected Connection connection;

    public SQLDatabase(String tablePrefix, String url, boolean useUUID) throws SQLException {
        this.tablePrefix = tablePrefix;
        this.url = url;
        this.useUUID = useUUID;
        
        connection = getConnection();
    }

    @Override
    public boolean createUser(UUID playerUUID, String playerName) {
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = getConnection().prepareStatement(SQLStatement.CREATE_USER.replace("{prefix}", tablePrefix));
            preparedStatement.setString(1, playerUUID.toString());
            preparedStatement.setString(2, playerName);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            LoggerUtil.sendSevere("SQLDatabase#createUser(UUID, String): Couldn't create a new user: User{uuid = " + playerUUID + ", name = " + playerName + "}");
            return false;
        } finally {
            try {
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
            } catch (SQLException e) {
                LoggerUtil.sendSevere("SQLDatabase#createUser(UUID, String): Couldn't close preparedStatement.");
            }
        }

        return true;
    }

    @Override
    public boolean deleteUser(IUser user) {
        return useUUID ? deleteUser(user.getUUID()) : deleteUser(user.getUserName());
    }

    @Override
    public IUser getUser(UUID playerUUID) {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            preparedStatement = getConnection()
                    .prepareStatement(SQLStatement.GET_USER_BY_UUID.replace("{prefix}", tablePrefix));
            preparedStatement.setString(1, String.valueOf(playerUUID));

            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                String foundUUID = resultSet.getString("uuid");
                Map<String, Location> homes = getUserHomes(foundUUID);
                List<String> ignoredUsers = getIgnoredUsers(foundUUID);

                ILanguage language = languageManager.getLanguageByName(resultSet.getString("language"));
                if (language == null) {
                    language = languageManager.getLanguageByFileName(Setting.DEFAULT_LANGUAGE_FILENAME);
                }

                return new User(
                        UUID.fromString(foundUUID),
                        resultSet.getString("username"),
                        ignoredUsers,
                        homes,
                        resultSet.getString("customNickname"),
                        language,
                        resultSet.getBoolean("godMode"),
                        resultSet.getBoolean("receivingMessages")
                );
            }
        } catch (SQLException e) {
            LoggerUtil.sendSevere("SQLDatabase#getuser(Player): Couldn't select this user: User{uuid = " + playerUUID + "}");
        } finally {
            try {
                if (resultSet != null) {
                    resultSet.close();
                }
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
            } catch (SQLException e) {
                LoggerUtil.sendSevere("SQLDatabase#getuser(Player): Couldn't close preparedStatement or resultSet.");
            }

        }

        return null;
    }

    @Override
    public IUser getUser(String playerName) {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            preparedStatement = getConnection()
                    .prepareStatement(SQLStatement.GET_USER_BY_NAME.replace("{prefix}", tablePrefix));
            preparedStatement.setString(1, playerName);

            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                String foundUUID = resultSet.getString("uuid");
                Map<String, Location> homes = getUserHomes(foundUUID);
                List<String> ignoredUsers = getIgnoredUsers(foundUUID);

                ILanguage language = languageManager.getLanguageByName(resultSet.getString("language"));
                if (language == null) {
                    language = languageManager.getLanguageByFileName(Setting.DEFAULT_LANGUAGE_FILENAME);
                }

                return new User(
                        UUID.fromString(foundUUID),
                        resultSet.getString("username"),
                        ignoredUsers,
                        homes,
                        resultSet.getString("customNickname"),
                        language,
                        resultSet.getBoolean("godMode"),
                        resultSet.getBoolean("receivingMessages")
                );
            }
        } catch (SQLException e) {
            LoggerUtil.sendSevere("SQLDatabase#getuser(Player): Couldn't select this user: User{name = " + playerName + "}");
        } finally {
            try {
                if (resultSet != null) {
                    resultSet.close();
                }
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
            } catch (SQLException e) {
                LoggerUtil.sendSevere("SQLDatabase#getuser(Player): Couldn't close preparedStatement or resultSet.");
            }

        }

        return null;
    }

    @Override
    public Map<String, Location> getUserHomes(UUID playerUUID) {
        return getUserHomes(String.valueOf(playerUUID));
    }

    @Override
    public List<String> getIgnoredUsers(UUID playerUUID) {
        return getIgnoredUsers(String.valueOf(playerUUID));
    }

    protected abstract void connect();

    protected Connection getConnection() throws SQLException {
        if (this.connection == null || !this.connection.isValid(5)) {
            this.connect();
        }

        return this.connection;
    }

    protected void executeAll(List<String> queries) {
        for (String query : queries) {
            try {
                PreparedStatement preparedStatement = getConnection().prepareStatement(query.replace("{prefix}", tablePrefix));
                preparedStatement.executeUpdate();
            } catch (SQLException e) {
                LoggerUtil.sendSevere("SQLDatabase#getIgnoredUsers(String): Couldn't execute the following sql query:\n" + query);
            }
        }
    }

    private boolean deleteUser(UUID playerUUID) {
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = getConnection().prepareStatement(SQLStatement.DELETE_USER_BY_UUID.replace("{prefix}", tablePrefix));
            preparedStatement.setString(1, String.valueOf(playerUUID));
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            LoggerUtil.sendSevere("SQLDatabase#deleteUser(UUID): Couldn't delete this user: User{uuid = " + playerUUID + "}");
            return false;
        } finally {
            try {
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
            } catch (SQLException e) {
                LoggerUtil.sendSevere("SQLDatabase#deleteUser(UUID): Couldn't close preparedStatement.");
            }
        }

        return true;
    }

    private boolean deleteUser(String playerName) {
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = getConnection().prepareStatement(SQLStatement.DELETE_USER_BY_NAME.replace("{prefix}", tablePrefix));
            preparedStatement.setString(1, playerName);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            LoggerUtil.sendSevere("SQLDatabase#deleteUser(String): Couldn't delete this user: User{name = " + playerName + "}");
            return false;
        } finally {
            try {
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
            } catch (SQLException e) {
                LoggerUtil.sendSevere("SQLDatabase#deleteUser(String): Couldn't close preparedStatement.");
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
                    homes.put(
                            resultSet.getString("name"),
                            new Location(
                                    world,
                                    resultSet.getDouble("x"),
                                    resultSet.getDouble("y"),
                                    resultSet.getDouble("z"),
                                    resultSet.getFloat("yaw"),
                                    resultSet.getFloat("pitch")
                            )
                    );
                }
            }
        } catch (SQLException e) {
            LoggerUtil.sendSevere("SQLDatabase#getUserHomes(String): Couldn't select this user: User{uuid = " + playerUUID + "}");
            e.printStackTrace();
        } finally {
            try {
                if (resultSet != null) {
                    resultSet.close();
                }
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
            } catch (SQLException e) {
                LoggerUtil.sendSevere("SQLDatabase#getUserHomes(String): Couldn't close preparedStatement or resultSet.");
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
            LoggerUtil.sendSevere("SQLDatabase#getIgnoredUsers(String): Couldn't select this user: User{uuid = " + playerUUID + "}");
        } finally {
            try {
                if (resultSet != null) {
                    resultSet.close();
                }
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
            } catch (SQLException e) {
                LoggerUtil.sendSevere("SQLDatabase#getIgnoredUsers(String): Couldn't close preparedStatement or resultSet.");
            }
        }

        return ignoredPlayers;
    }

}
