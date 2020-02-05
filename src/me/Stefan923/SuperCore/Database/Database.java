package me.Stefan923.SuperCore.Database;

import java.sql.ResultSet;
import java.util.Set;

public abstract class Database {

    public abstract void put(String playerKey, String key, String value);

    public abstract boolean has(String key);

    public abstract Set<String> getKeys();

    public abstract ResultSet get(String key);

    public abstract ResultSet get(String playerKey, String key);

    public abstract void delete(String playerKey);

    public abstract void clear();

}
