package pages;

import gui.*;

import control.General;
import user.UserManager;

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
        GTextBox name = new GTextBox(40, UserManager.getLoadedUser().getName());
        GTextBox email = new GTextBox(40, UserManager.getLoadedUser().getEmail());

        //Place all of the components in the right places.
        GUI.window.add(new GSpacer(25));
        GUI.window.add(new GText("Edit Account"));
        GUI.window.add(new GSpacer(25));
        GUI.window.add(new GText("Name:"));
        GUI.window.add(new GSpacer(5));
        GUI.window.add((GUIComponent) name);
        GUI.window.add(new GSpacer(5));
        GUI.window.add(new GText("Email:"));
        GUI.window.add(new GSpacer(5));
        GUI.window.add((GUIComponent) email);
        GUI.window.add(new GSpacer(5));

        //Add the submit button and program what it does.
        GUI.window.add(new GButton(40, "Save Changes", Style.defaultFont) {
            @Override
            public void clickAction() {
                String myName = name.getText().trim();
                String myEmail = email.getText().trim();

                //TODO: Fix the bug here. You can't change the name when you don't change the email.
                if (General.testName(myName) && General.testEmail(myEmail) && General.isEmailFree(myEmail)) {
                    UserManager.getLoadedUser().setName(myName);
                    UserManager.getLoadedUser().setEmail(myEmail);
                    GUI.window.gotoPage("Home");
                } else {
                    if (!General.testName(myName)) {
                        name.failed("• Name must be at least 3 characters.");
                    } if (!General.testEmail(myEmail)) {
                        email.failed("• Must be a valid email. This is a really long message to see if it work. Hello, the quick brown fox jumps over the lazy dog. Wow, does this actually work?");
                    }  if (!General.isEmailFree(myEmail)) {email.failed("• This Email is already taken.");
                    }
                }
            }
        });


        GUI.window.add(new GSpacer(25));
        GUI.window.add(new GText("Other Settings"));
        GUI.window.add(new GSpacer(25));
        GUI.window.add(new GButton(40, "Delete Account", Style.defaultFont) {
            @Override
            public void clickAction() {
                UserManager.getUsers().remove(UserManager.getLoadedUser());
                UserManager.save();
                if (UserManager.getUsers().size() == 0) {
                    GUI.window.gotoPage("Register");
                } else {
                    GUI.window.gotoPage("Login");
                }
            }
        });

        GUI.window.showMenu();
    }
}
