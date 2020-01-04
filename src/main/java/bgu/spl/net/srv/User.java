package bgu.spl.net.srv;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class User {
    private int userId;
    private final String userName;
    private final String password;
    private int port;
    private Map<String, String> genreById;
    private boolean isActive;

    public User(int userId, String userName, String password, int port) {
        this.userId = userId;
        this.userName = userName;
        this.password = password;
        this.port = port;
        this.genreById = new ConcurrentHashMap<>();
        this.isActive = true;
    }

    public int getUserId() {
        return userId;
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }

    public void setActive(int userId) {
        isActive = true;
        this.userId = userId;
    }

    public void logout() {
        isActive = false;
        this.userId = -1;
    }

    public boolean isActive() {
        return isActive;
    }

    public void subscribe(String subsId, String genre) {
        genreById.put(subsId, genre);
    }

    public void unsubscribe(String subsId, String genre) {
        genreById.remove(subsId, genre);
    }

    public String getSubscriptionId(String genre) {
        for (String subsId: genreById.keySet()) {
            if (genreById.get(subsId) == genre) {
                return subsId;
            }
        }
        return null;
    }
}
