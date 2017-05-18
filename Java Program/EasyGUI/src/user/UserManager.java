package user;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

/**
 * @author Ryan Hansen
 *         <p>
 *         File which will store all the user information including a list of their projects.
 */
public final class UserManager {

    private UserManager() {
    }

    public static ArrayList<User> allUsers = new ArrayList<>();

    private static User loadedUser;

    public static User getLoadedUser() {
        return loadedUser;
    }

    public static void setLoadedUser(User u) {
        loadedUser = u;
    }

    public static ArrayList<User> getUsers() {
        return allUsers;
    }


    /**
     * Saves the ArrayList of projects to a file in the current working directory.
     *
     * @author Ryan Hansen
     */
    public static void save() {

        //long name making it easy to search for
        String filename = "ThePiratesProjectSave";

        ObjectOutputStream oos = null;
        try {
            oos = new ObjectOutputStream(new FileOutputStream(filename));
            oos.writeObject(allUsers);
            oos.flush();
        } catch (IOException e) {
            System.out.print(e.getMessage());
        } finally {
            if (oos != null) {
                try {
                    oos.close();
                } catch (IOException e) {
                    System.out.print("oos closing error\n");
                    System.out.print(e.getMessage());
                }
            }
        }
    }

    /**
     * @author Ryan Hansen
     */
    public static void load() {
        String filename = "ThePiratesProjectSave";


        ObjectInputStream ois = null;
        try {
            ois = new ObjectInputStream(new FileInputStream(filename));
            Object obj = ois.readObject();

            if (obj instanceof ArrayList<?>) {
                allUsers = (ArrayList<User>) obj;
            }

        } catch (IOException | ClassNotFoundException e) {
            System.out.println(e);
        } finally {
            if (ois != null) {
                try {
                    ois.close();
                } catch (IOException e) {
                    System.out.println("ois closing error\n");
                    System.out.println(e);
                }
            }
        }
    }

}
