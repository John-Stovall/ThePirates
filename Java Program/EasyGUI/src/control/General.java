package control;

/**
 * Created by Robert on 5/10/17.
 *
 * This is a class that holds general methods that
 * other classes may more may not use.
 */
public class General {

    private General() {}

    /**
     *
     * This is a simple character counter that returns false if there are
     * less than 3 characters.
     *
     * @param name The name the user entered.
     * @return Whether the name is valid.
     *
     * @author Reagan
     */
    public static boolean testName(String name){
        return name.length() >= 3;
    }

    /**
     * This tests that there is an @ sign and a '.com' a bit lacking,
     * but we can expand it if we want.
     *
     * @param email The email the user entered.
     * @return Whether the email is valud.
     *
     * @author Reagan
     */
    public static boolean testEmail(String email){
        // contains @
        if (email.indexOf('@') == -1) {
            return false;
        }
        // contains a '.com' at the end
        return email.toLowerCase().endsWith(".com");
    }

}
