package pages;

import gui.*;
import main.User;

import control.General;

/**
 * Created by Robert on 5/10/17.
 *
 * This is the Edit Account page.
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
        GTextBox name = new GTextBox(32, User.getLoadedUser().getName(), 10);
        GTextBox email = new GTextBox(32, User.getLoadedUser().getEmail());

        //Place all of the components in the right places.
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

        //Add the submit button and program what it does.
        GUI.window.add(new GButton(25, "Save Changes") {
            @Override
            public void clickAction() {
                String myName = name.getText().trim();
                String myEmail = email.getText().trim();

                //TODO: Fix the bug here. You can't change things when you don't change the email.
                if (General.testName(myName) && General.testEmail(myEmail) && (General.isEmailFree(myEmail)
                        && !myEmail.equals(User.getLoadedUser().getEmail()))) {
                    User.getLoadedUser().setName(myName);
                    User.getLoadedUser().setEmail(myEmail);
                    GUI.window.gotoPage("Home");
                } else {
                    if (!General.testName(myName)) {
                        name.failed("• Name must be at least 3 characters.");
                    } if (!General.testEmail(myEmail)) {
                        email.failed("• Must be a valid email.");
                    } else if (!General.isEmailFree(myEmail)) {
                        email.failed("• This Email is already taken.");
                    }
                }
            }
        });
        GUI.window.add(menu);
    }
}
