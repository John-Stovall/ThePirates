package pages;

import control.General;
import gui.*;
import main.User;

import java.awt.*;

/**
 * Created by Robert on 5/10/17.
 *
 * This class handles the Register Account page.
 */
public class RegisterAccount extends GUIPage {

    /**
     * Create a page and sets it's name.
     */
    public RegisterAccount() {
        super("Register");
    }

    @Override
    public void build() {

        //Instantiate the Checkboxes...
        GTextBox name = new GTextBox(32, Color.gray, Color.white, "", 10);
        GTextBox email = new GTextBox(32, Color.gray, Color.white, "");

        //Place all the stuff in the right order...
        GUI.window.add(new GSpacer(25));
        GUI.window.add(new GText("Create Account"));
        GUI.window.add(new GSpacer(25));
        GUI.window.add(new GText("Name:"));
        GUI.window.add(new GSpacer(5));
        GUI.window.add(name);
        GUI.window.add(new GSpacer(5));
        GUI.window.add(new GText("Email:"));
        GUI.window.add(new GSpacer(5));
        GUI.window.add(email);
        GUI.window.add(new GSpacer(5));
        GUI.window.add(new GButton(25, mainColor, secondaryColor, "Submit") {
            @Override
            public void clickAction() {
                String myName = name.getText().trim();
                String myEmail = email.getText().trim();

                if(General.testName(myName) && General.testEmail(myEmail)){
                    //This is the code for a successful login.
                    User validUser = new User(myName, myEmail);
                    User.getUsers().add(validUser);
                    User.setLoadedUser(validUser);
                    GUI.window.gotoPage("Home");
                } else {
                    if (!General.testName(myName)) {
                        name.failed("• Name must be at least 3 characters.");
                    }
                    if (!General.testEmail(myEmail)) {
                        email.failed("• Must be a valid email.");
                    }
                }
            }
        });

        if (!User.getUsers().isEmpty()) {
            GUI.window.add(new GSpacer(5));
            GUI.window.add(new GButton(25, mainColor, secondaryColor, "Back") {
                @Override
                public void clickAction() {
                    GUI.window.gotoPage("Login");
                }
            });
        } else {
            GUI.window.add(new GSpacer(5));
            GUI.window.add(new GButton(25, mainColor, secondaryColor, "Skip Login") {
                @Override
                public void clickAction() {
                    User validUser = new User("You", "");
                    User.getUsers().add(validUser);
                    User.setLoadedUser(validUser);
                    GUI.window.gotoPage("Home");
                }
            });
        }
    }
}
