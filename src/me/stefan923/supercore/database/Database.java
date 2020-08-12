package me.stefan923.supercore.database;

import java.sql.ResultSet;
import java.util.Set;

public abstract class Database {

    public abstract void put(TableType tableType, String playerKey, String key, String value);

    public abstract boolean has(TableType tableType, String key);

    public abstract Set<String> getKeys(TableType tableType);

    public abstract ResultSet get(TableType tableType, String key);

    public abstract ResultSet get(TableType tableType, String playerKey, String key);

    public abstract void delete(TableType tableType, String playerKey);

    public abstract void clear(TableType tableType);

}
