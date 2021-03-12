package me.stefan923.supercore.user;

import me.stefan923.supercore.database.IDatabase;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class UserRepository implements IUserRepository {

    private final IDatabase database;

    private final List<IUser> onlineUsers = new ArrayList<>();

    public UserRepository(IDatabase database) {
        this.database = database;
    }

    @Override
    public IUser createUser(UUID uuid, String name) {
        database.createUser(uuid, name);
        return getOrLoadUser(uuid);
    }

    @Override
    public IUser getUser(UUID uuid) {
        if (uuid == null) {
            return null;
        }

        return onlineUsers.stream().filter(onlineUser -> uuid.equals(onlineUser.getUUID())).findFirst().orElse(null);
    }

    @Override
    public IUser getUser(String name) {
        if (name == null || name.isEmpty()) {
            return null;
        }

        return onlineUsers.stream().filter(onlineUser -> name.equalsIgnoreCase(onlineUser.getUserName())).findFirst().orElse(null);
    }

    @Override
    public IUser getOrLoadUser(UUID uuid) {
        if (uuid == null) {
            return null;
        }

        IUser user = onlineUsers.stream().filter(onlineUser -> uuid.equals(onlineUser.getUUID())).findFirst().orElse(null);
        if (user == null) {
            return loadUser(uuid);
        }

        return user;
    }

    @Override
    public IUser getOrLoadUser(String name) {
        if (name == null || name.isEmpty()) {
            return null;
        }

        IUser user = onlineUsers.stream().filter(onlineUser -> name.equalsIgnoreCase(onlineUser.getUserName())).findFirst().orElse(null);
        if (user == null) {
            return loadUser(name);
        }

        return user;
    }

    @Override
    public List<IUser> getOnlineUsers() {
        return new ArrayList<>(onlineUsers);
    }

    @Override
    public void remove(IUser user) {
        onlineUsers.remove(user);
    }

    @Override
    public void clear() {
        onlineUsers.forEach(this::remove);
    }

    private IUser loadUser(UUID uuid) {
        IUser user = database.getUser(uuid);
        if (user != null) {
            onlineUsers.add(user);
        }

        return user;
    }

    private IUser loadUser(String name) {
        IUser user = database.getUser(name);
        if (user != null) {
            onlineUsers.add(user);
        }

        return user;
    }

}
