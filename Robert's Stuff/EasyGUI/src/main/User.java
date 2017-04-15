package main;

import java.util.ArrayList;

/**
 * Created by Robert on 4/12/17.
 */
public class User {

    private static ArrayList<User> allUsers = new ArrayList<>();

    private static User loadedUser;

    public static void setLoadedUser(User u) {
        loadedUser = u;
    }

    public static User getLoadedUser() {
        return loadedUser;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private String name;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    private String email;

    public User(final String name, final String email) {
        this.name = name;
        this.email = email;
    }

    public static ArrayList<User> getUsers() {
        return allUsers;
    }
}
