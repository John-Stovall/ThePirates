package pages;

import gui.*;
import main.User;

import java.awt.*;

/**
 * Created by robertcordingly on 5/10/17.
 */
public class EditAccount extends GUIPage {
    /**
     * Create a page and sets it's name.
     */
    public EditAccount() {
        super("Edit Account");
    }

    @Override
    public void build() {
        GUI.window.add(new GSpacer(40));

        //Instantiate the Checkboxes...
        GTextBox name = new GTextBox(32, Color.gray, Color.white, User.getLoadedUser().getName(), 10);
        GTextBox email = new GTextBox(32, Color.gray, Color.white, User.getLoadedUser().getEmail());

        //Place all the stuff in the right order...
        GUI.window.add(new GSpacer(25));
        GUI.window.add(new GText("Edit Account"));
        GUI.window.add(new GSpacer(25));
        GUI.window.add(new GText("Name:"));
        GUI.window.add(new GSpacer(5));
        GUI.window.add(name);
        GUI.window.add(new GSpacer(5));
        GUI.window.add(new GText("Email:"));
        GUI.window.add(new GSpacer(5));
        GUI.window.add(email);
        GUI.window.add(new GSpacer(5));
        GUI.window.add(new GButton(25, mainColor, secondaryColor, "Save Changes") {
            @Override
            public void clickAction() {
                String myName = name.getText().trim();
                String myEmail = email.getText().trim();

                if (testName(myName) && testEmail(myEmail)) {
                    User.getLoadedUser().setName(myName);
                    User.getLoadedUser().setEmail(myEmail);
                    GUI.window.gotoPage("Home");
                } else {
                    if (!testName(myName)) {
                        name.failed("• Name must be at least 3 characters.");
                    }
                    if (!testEmail(myEmail)) {
                        email.failed("• Must be a valid email.");
                    }
                }
            }
        });
        GUI.window.add(menu);
    }

    /**
     * This is a simple character counter that returns false if there are
     * less than 3 characters.
     *
     * @param name
     * @return
     */
    private static boolean testName(String name){
        if (name.length() < 3) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * This tests that there is an @ sign and a '.com' a bit lacking,
     * but we can expand it if we want.
     *
     * @param email
     * @return
     */
    private static boolean testEmail(String email){
        // contains @
        if (email.indexOf('@') == -1) {
            return false;
        }
        // contains a '.com' at the end
        if(!email.toLowerCase().endsWith(".com")) {
            return false;
        }
        return true;
    }
}
