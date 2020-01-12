package bgu.spl.net.srv;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class BookClub {
    private ConcurrentHashMap<String, List<User>> usersPerGenre; // < genre , users >
    private List<User> users;

    private BookClub() {
        this.usersPerGenre = new ConcurrentHashMap<>();
        this.users = new LinkedList<>();

    }

    //TODO: change name
    private static class threadHolder {
        private static final BookClub instance = new BookClub();
    }

    public static BookClub getInstance() {
        return threadHolder.instance;
    }

    public User getUser(String userName) {
        synchronized (users) {
            for (User user : users) {
                if (user.getUserName().equals(userName))
                    return user;
            }
            return null;
        }
    }

    public User getUser(int userId) {
        synchronized (users) {
            for (User user : users) {
                if (user.getUserId() == userId)
                    return user;
            }
        }
        return null;
    }

    public void addUser(User newUser) {
        synchronized (users) {
            users.add(newUser);
        }
    }

    public void exitAllGenres(User user) {
        // Remove the user from each genre list
        for (String genre : usersPerGenre.keySet()) {
            synchronized (usersPerGenre.get(genre)) {
                usersPerGenre.get(genre).remove(user);
            }
        }
    }

    public void joinGenre(String genre, User user) {
        synchronized (usersPerGenre) {
            usersPerGenre.putIfAbsent(genre, new LinkedList<>());
            usersPerGenre.get(genre).add(user);
        }
    }

    public void exitGenre(String genre, User user) {
        synchronized (usersPerGenre.get(genre)) {
            usersPerGenre.get(genre).remove(user);
        }
    }

    public List<User> getUsersByGenre(String genre){
        synchronized (usersPerGenre) {
            return usersPerGenre.get(genre);
        }
    }
}
