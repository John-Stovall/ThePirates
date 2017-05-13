package main;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Robert on 4/12/17.
 *
 * Ultra basic user class. You guys can figure this out.
 */
public class User implements Serializable {

    /** The list of all users, this is what will need to be saved. */
    private static ArrayList<User> allUsers = new ArrayList<>();

    /** The currently loaded users. */
    private static User loadedUser;

    /** The name of the user. */
    private String name;

    /** The email of the user. */
    private String email;

    /**
     * Create a user.
     *
     * @param name The name of the user.
     * @param email The email of the user.
     */
    public User(final String name, final String email) {
        this.name = name;
        this.email = email;
    }

    /**
     * This method sets the loaded user.
     *
     * @param u The user to set to be loaded.
     */
    public static void setLoadedUser(User u) {
        loadedUser = u;
    }

    /**
     * This method gets the loaded user. At this point you may be asking
     * yourself why the loadedUser isn't just public. I mean we aren't returning
     * a clone of it, we're just throwing it out there anyway. There really is no
     * answer to that question, it should be public, but for some reason we were
     * taught that EVERYTHING needed a getter and setter. It's too late now.
     *
     * @return The loaded user.
     */
    public static User getLoadedUser() {
        return loadedUser;
    }

    /**
     * This method gets the name of the user.
     *
     * @return The name of this user.
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the user to something.
     *
     * @param name Something.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Yep, it gets the email of this user.
     *
     * @return The email of the user.
     */
    public String getEmail() {
        return email;
    }

    /**
     * This method sets the email of the user.
     *
     * @param email What to set the email to.
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * This method returns the list of users.
     *
     * @return The list of users.
     */
    public static ArrayList<User> getUsers() {
        return allUsers;
    }
}
