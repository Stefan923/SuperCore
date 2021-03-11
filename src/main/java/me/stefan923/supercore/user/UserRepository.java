package me.stefan923.supercore.user;

import java.util.List;

public interface UserRepository {

    IUser add(User user);

    IUser getUserByName(String name);

    IUser getUserByUuid(String uuid);

    List<User> getOnlineUsers();

    IUser delete(User user);

}
