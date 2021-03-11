package me.stefan923.supercore.user;

import java.util.List;
import java.util.UUID;

public interface IUserRepository {

    IUser createUser(UUID uuid, String name);

    IUser getUser(UUID uuid);

    IUser getUser(String name);

    IUser getOrLoadUser(UUID uuid);

    IUser getOrLoadUser(String name);

    List<IUser> getOnlineUsers();

    void remove(IUser user);

    void clear();

}
