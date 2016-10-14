package logic;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by ido on 11/10/2016.
 */
public class UserManager {

    private final Map<String, User> users;

    public UserManager() {
        users = new HashMap<>();
    }

    public void addUser(String username, String playerType) {

        users.put(username, new User(username, playerType));
    }

    public void removeUser(String username) {
        users.remove(username);
    }

    public List<User> getUsers() {
        return users.values().stream().collect(Collectors.toList());
    }

    public boolean isUserExists(String username) {
        return users.containsKey(username);
    }
}

