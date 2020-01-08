package bgu.spl.net.srv;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Stack;
import java.util.concurrent.ConcurrentHashMap;

public class BookClub {
    private Map<String, List<User>> usersPerGenre; // < genre , users >
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
        for (User user : users) {
            if (user.getUserName() == userName)
                return user;
        }
        return null;
    }

    public User getUser(int userId) {
        for (User user : users) {
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
        for (String genre : usersPerGenre.keySet()) {
            usersPerGenre.get(genre).remove(user);
        }

        // Remove the user from the users list
        users.remove(user);
    }

    public void joinGenre(String genre, User user) {
        //TODO edited this
        if (usersPerGenre.get(genre) == null){
            usersPerGenre.put(genre, new LinkedList<>());
        }
        usersPerGenre.get(genre).add(user);
    }

    public void exitGenre(String genre, User user) {
        usersPerGenre.get(genre).remove(user);
    }

    public List<User> getUsersByGenre(String genre){
        return usersPerGenre.get(genre);
    }
}
