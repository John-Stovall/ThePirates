package pages;

import control.General;
import gui.*;
import user.User;
import user.UserManager;

/**
 * Created by Robert on 5/10/17.
 *
 * This class handles the Register Account page.
 */
public class RegisterAccount extends GUIPage {

    /**
     * Create a page and sets it's name.
     *
     * @author Robert
     */
    public RegisterAccount() {
        super("Register");
    }

    @Override
    public void build() {

        //Instantiate the Checkboxes...
        GTextBox name = new GTextBox(40, "");
        GTextBox email = new GTextBox(40, "");

        //Place all the stuff in the right order...
        GUI.window.add(new GSpacer(25));
        GUI.window.add(new GText("Create Account"));
        GUI.window.add(new GSpacer(25));
        GUI.window.add(new GText("Name:", Style.defaultFont));
        GUI.window.add(new GSpacer(5));
        GUI.window.add((GUIComponent) name);
        GUI.window.add(new GSpacer(5));
        GUI.window.add(new GText("Email:", Style.defaultFont));
        GUI.window.add(new GSpacer(5));
        GUI.window.add((GUIComponent) email);
        GUI.window.add(new GSpacer(5));
        GUI.window.add(new GButton(40, "Submit", Style.defaultFont) {
            @Override
            public void clickAction() {
                String myName = name.getText().trim();
                String myEmail = email.getText().trim();

                if (General.testName(myName) && General.testEmail(myEmail) && General.isEmailFree(myEmail)) {
                    User newUser = new User(myName, myEmail);
                    UserManager.setLoadedUser(newUser);
                    GUI.window.gotoPage("Home");
                    GUI.getPopUp().destroy();
                } else {
                    if (!General.testName(myName)) {
                        name.failed("Name must be between 3 and 15 characters.");
                    } if (!General.testEmail(myEmail)) {
                        email.failed("Must be a valid email.");
                    } else if (!General.isEmailFree(myEmail)) {
                        email.failed("This Email is already taken.");
                    }
                }
            }
        });

        if (UserManager.getUsers().isEmpty())  {
            GUI.window.add(new GSpacer(10));
            GUI.window.add(new GButton(40, "Skip Login", Style.defaultFont) {
                @Override
                public void clickAction() {
                    User validUser = new User("You", "");
                    UserManager.setLoadedUser(validUser);
                    GUI.window.gotoPage("Home");
                    GUI.getPopUp().destroy();
                }
            });
        }
    }
}
