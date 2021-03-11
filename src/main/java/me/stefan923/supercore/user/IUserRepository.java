package me.stefan923.supercore.user;

import java.util.List;
import java.util.UUID;

public interface IUserRepository {

    IUser createUser(UUID uuid, String name);

    IUser getUserByName(String name);

    IUser getUserByUUID(UUID uuid);

    List<IUser> getOnlineUsers();

    boolean delete(User user);

}
