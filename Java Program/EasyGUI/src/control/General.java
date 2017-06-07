package control;

import project.Project;
import user.User;
import user.UserManager;

import java.awt.event.MouseEvent;

/**
 * Created by Robert on 5/10/17.
 * <p>
 * This is a class that holds general methods that
 * other classes may more may not use.
 */
public class General {

    /**
     * This is a class that only has static methods. No need to instantiate it.
     *
     * @author Robert
     * @author Reagan
     */
    private General() {
    }

    /**
     * This is a simple character counter that returns false if there are
     * less than 3 characters or more than 15.
     *
     * @param name The name the user entered.
     * @return Whether the name is valid.
     * @author Reagan
     */
    public static boolean testName(String name) {
        return name.length() >= 3 && name.length() <= 15;
    }

    /**
     * This test to make sure that an email is valid.
     *
     * @param email The email the user entered.
     * @return Whether the email is valud.
     * @author Reagan
     * @author Robert
     */
    public static boolean testEmail(String email) {

        //TODO Make this better.

        // contains @
        if (email.indexOf('@') == -1) {
            return false;
        }
        // contains a '.com' at the end or other domain.
        return email.toLowerCase().endsWith(".com") ||
                email.toLowerCase().endsWith(".net") ||
                email.toLowerCase().endsWith(".edu") ||
                email.toLowerCase().endsWith(".org");
    }

    /**
     * This method checks to see if any other use is already using a specified email address.
     *
     * @param email The email address to check.
     * @return Whether it is taken or not.
     * @author Robert
     */
    public static boolean isEmailFree(String email) {
        for (User u : UserManager.getUsers()) {
            if (u.getEmail().equals(email)) {
                return false;
            }
        }
        return true;
    }

    /**
     * This method checks to see if any other project is already using a specified name.
     *
     * @param name The name to check for.
     * @return Whether the name is taken or not.
     * @author Robert
     */
    public static boolean isProjectNameFree(String name) {
        for (Project p : UserManager.getLoadedUser().getMyProjects()) {
            if (p.getName().equals(name)) {
                return false;
            }
        }
        for (Project p : UserManager.getLoadedUser().getCompletedProject()) {
            if (p.getName().equals(name)) {
                return false;
            }
        }
        return true;
    }

    /**
     * This method checks to see if you clicked within a rectangle.
     *
     * @param x      The x position of the top left corner of the rectangle.
     * @param y      The y position of the top left corner of the rectangle.
     * @param width  The width of the rectangle.
     * @param height The height of the rectangle.
     * @param e      The mouse event object.
     * @return Whether you clicked inside the box.
     * @author Robert
     */
    public static boolean clickedInside(int x, int y, int width, int height, MouseEvent e) {
        return (e.getX() > x && e.getX() < x + width && e.getY() > y && e.getY() < y + height);
    }
}
