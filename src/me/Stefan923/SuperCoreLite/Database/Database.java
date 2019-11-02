package me.Stefan923.SuperCoreLite.Database;

import java.util.HashMap;
import java.util.Set;

public abstract class Database {

    public abstract void put(String playerKey, String key, String value);

    public abstract boolean has(String key);

    public abstract Set<String> getKeys();

    public abstract HashMap<String, Object> get(String key);

    public abstract Object get(String playerKey, String key);

    public abstract void delete(String playerKey);

    public abstract void clear();

}
