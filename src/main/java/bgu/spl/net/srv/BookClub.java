package bgu.spl.net.srv;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Stack;
import java.util.concurrent.ConcurrentHashMap;

public class BookClub {
    private Map<String, List<User>> usersPerGenre;
    private List<User> users;
    private Map<String, String> subsIdMap;


    private BookClub() {
        this.usersPerGenre = new ConcurrentHashMap<>();
        this.users = new LinkedList<>();
        this.subsIdMap = new ConcurrentHashMap<>();
    }

    //TODO: change name
    private static class threadHolder {
        private static final BookClub instance = new BookClub();
    }

    public static BookClub getInstance() {
        return threadHolder.instance;
    }

    public User getUser(String userName) {
        for (User user: users) {
            if (user.getUserName() == userName)
                return user;
        }
        return null;
    }

    public User getUser(int userId) {
        for (User user: users) {
            if (user.getUserId() == userId)
                return user;
        }
        return null;
    }

    public void addUser(User newUser) {
        users.add(newUser);
    }

    public void removeUser(User user) {
        // Remove the user from each genre list
        for (String genre: usersPerGenre.keySet()) {
            usersPerGenre.get(genre).remove(user);
        }

        // Remove the user from the users list
        users.remove(user);
    }

    public void joinGenre(String genre, User user) {
        usersPerGenre.get(genre).add(user);
    }

    public void exitGenre(String genre, User user) {
        usersPerGenre.get(genre).remove(user);
    }

    public void addSubscription(String subsId, String genre){
        subsIdMap.put(subsId, genre);
    }

    public void removeSubscription(String subsId, String genre){
        subsIdMap.remove(subsId, genre);
    }

    public String getGenreById(String subscriptionId) {
        return subsIdMap.get(subscriptionId);
    }

}
