package control;

import user.User;
import user.UserManager;

import java.awt.event.MouseEvent;

/**
 * Created by Robert on 5/10/17.
 *
 * This is a class that holds general methods that
 * other classes may more may not use.
 */
public class General {

    private General() {}

    /**
     * This is a simple character counter that returns false if there are
     * less than 3 characters.
     *
     * @param name The name the user entered.
     * @return Whether the name is valid.
     * @author Reagan
     */
    public static boolean testName(String name) {
        return name.length() >= 3;
    }

    /**
     * This tests that there is an @ sign and a '.com' a bit lacking,
     * but we can expand it if we want.
     *
     * @param email The email the user entered.
     * @return Whether the email is valud.
     * @author Reagan
     */
    public static boolean testEmail(String email) {

        //TODO Make this better.

        // contains @
        if (email.indexOf('@') == -1) {
            return false;
        }
        // contains a '.com' at the end
        return email.toLowerCase().endsWith(".com");
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
     * This method checks to see if you clicked within a rectangle.
     *
     * @param x The x position of the top left corner of the rectangle.
     * @param y The y position of the top left corner of the rectangle.
     * @param width The width of the rectangle.
     * @param height The height of the rectangle.
     * @param e The mouse event object.
     * @return Whether you clicked inside the box.
     * @author Robert
     */
    public static boolean clickedInside(int x, int y, int width, int height, MouseEvent e) {
        return (e.getX() > x && e.getX() < x + width && e.getY() > y && e.getY() < y + height);
    }

}
