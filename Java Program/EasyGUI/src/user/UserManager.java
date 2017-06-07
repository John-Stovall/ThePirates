package user;

import gui.GUI;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

/**
 * File which will store all the user information including a list of their projects.
 *
 * @author Ryan Hansen
 * @author Robert
 */
public final class UserManager {

    /**
     * This is a singleton class so the constructor must be private.
     *
     * @author Ryan Hansen
     */
    private UserManager() {}

    /**
     * The list of all users.
     */
    static ArrayList<User> allUsers = new ArrayList<>();

    /**
     * The currently loaded user.
     */
    private static User loadedUser;

    /**
     * This method gets the currently loaded user.
     *
     * @return The currently loaded user.
     * @author Robert
     */
    public static User getLoadedUser() {
        return loadedUser;
    }

    /**
     * This method lets you set the currently loaded user.
     *
     * @param u The user to set the loaded user to.
     * @author Robert
     */
    public static void setLoadedUser(User u) {
        loadedUser = u;
    }

    /**
     * This method gets all of the users.
     *
     * @return The list of all users.
     * @author Ryan Hansen
     */
    public static ArrayList<User> getUsers() {
        return allUsers;
    }

    /**
     * This method allows the user to save to a location that they define.
     *
     * @author Ryan Hansen
     */
    public static void export() {
        JFileChooser chooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter(
                "DIY Files", "diy");
        chooser.setFileFilter(filter);
        int returnVal = chooser.showSaveDialog(GUI.window);
        if(returnVal == JFileChooser.APPROVE_OPTION) {
            System.out.println("You chose to open this file: " +
                    chooser.getSelectedFile().getName());

            ObjectOutputStream oos = null;
            try {
                oos = new ObjectOutputStream(new FileOutputStream(chooser.getSelectedFile()));
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


    }

    /**
     * This method allows the user to import a file from a location on their disk.
     *
     * @author Ryan Hansen
     */
    public static void importFile() {
        JFileChooser chooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter(
                "DIY Files", "diy");
        chooser.setFileFilter(filter);
        int returnVal = chooser.showOpenDialog(GUI.window);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            System.out.println("You chose to open this file: " +
                    chooser.getSelectedFile().getName());

            ObjectInputStream ois = null;
            try {
                ois = new ObjectInputStream(new FileInputStream(chooser.getSelectedFile()));
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
        GUI.window.refresh();
    }


    /**
     * Saves the ArrayList of projects to a file in the current working directory.
     *
     * @author Ryan Hansen
     */
    public static void save() {

        //long name making it easy to search for
        String filename = "ThePiratesProjectSave.diy";

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
     * This method loads the default information.
     *
     * @author Ryan Hansen
     */
    public static void load() {
        String filename = "ThePiratesProjectSave.diy";

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
