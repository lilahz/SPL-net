package bgu.spl.net.srv;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class User {
    private int userId;
    private final String userName;
    private final String password;
    private Map<String, String> genreById; // < id , genre >
    private boolean isActive;

    public User(int userId, String userName, String password) {
        this.userId = userId;
        this.userName = userName;
        this.password = password;
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

    public void unsubscribe(String subsId) {
        genreById.remove(subsId);
    }

    public String getSubscriptionId(String genre) {
        for (String subsId: genreById.keySet()) {
            if (genreById.get(subsId).equals(genre)) {
                return subsId;
            }
        }
        return null;
    }

    public String getGenreById(String subscriptionId) {
        return genreById.get(subscriptionId);
    }

    public void unSubscribeAll(){
        genreById.clear();

    }


}
