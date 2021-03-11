package me.stefan923.supercore.user;

import java.util.List;
import java.util.UUID;

public interface IUserRepository {

    IUser createUser(UUID uuid, String name);

    IUser getUser(String name);

    IUser getUser(UUID uuid);

    IUser getOrLoadUser(String name);

    IUser getOrLoadUser(UUID uuid);

    List<IUser> getOnlineUsers();

    void remove(IUser user);

    void clear();

}
