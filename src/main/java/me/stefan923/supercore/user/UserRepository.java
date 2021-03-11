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
        return loadUserByUUID(uuid);
    }

    @Override
    public IUser getUserByName(String name) {
        if (name == null || name.isEmpty()) {
            return null;
        }

        IUser user = onlineUsers.stream().filter(onlineUser -> name.equalsIgnoreCase(onlineUser.getUserName())).findFirst().orElse(null);
        if (user == null) {
            return loadUserByName(name);
        }

        return user;
    }

    @Override
    public IUser getUserByUUID(UUID uuid) {
        if (uuid == null) {
            return null;
        }

        IUser user = onlineUsers.stream().filter(onlineUser -> uuid.equals(onlineUser.getUUID())).findFirst().orElse(null);
        if (user == null) {
            return loadUserByUUID(uuid);
        }

        return user;
    }

    @Override
    public List<IUser> getOnlineUsers() {
        return new ArrayList<>(onlineUsers);
    }

    @Override
    public boolean delete(User user) {
        return onlineUsers.remove(user);
    }

    private IUser loadUserByName(String name) {
        IUser user = database.getUser(name);
        onlineUsers.add(user);
        return user;
    }

    private IUser loadUserByUUID(UUID uuid) {
        IUser user = database.getUser(uuid);
        onlineUsers.add(user);
        return user;
    }

}
