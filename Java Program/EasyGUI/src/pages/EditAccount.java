package pages;

import gui.*;

import control.General;
import user.UserManager;

import java.awt.*;
import java.util.ArrayList;

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
        GUI.window.add(new GText("Name:", Style.defaultFont));
        GUI.window.add(new GSpacer(5));
        GUI.window.add((GUIComponent) name);
        GUI.window.add(new GSpacer(5));
        GUI.window.add(new GText("Email:", Style.defaultFont));
        GUI.window.add(new GSpacer(5));
        GUI.window.add((GUIComponent) email);
        GUI.window.add(new GSpacer(5));

        //Add the submit button and program what it does.
        GUI.window.add(new GButton(40, "Save Changes", Style.defaultFont) {
            @Override
            public void clickAction() {
                String myName = name.getText().trim();
                String myEmail = email.getText().trim();
                if (General.testName(myName) && General.testEmail(myEmail) && (General.isEmailFree(myEmail) || !myName.equals(UserManager.getLoadedUser().getName()))) {
                    UserManager.getLoadedUser().setName(myName);
                    UserManager.getLoadedUser().setEmail(myEmail);
                    UserManager.save();
                    GUI.getPopUp().destroy();
                    GUI.window.gotoPage("Home");
                } else {
                    if (!General.testName(myName)) {
                        name.failed("Name must be between 3 and 15 characters.");
                    } if (!General.testEmail(myEmail)) {
                        email.failed("Must be a valid email.");
                    }  if (!General.isEmailFree(myEmail)) {email.failed("This email is already taken.");
                    }
                }
            }
        });


        GUI.window.add(new GSpacer(25));
        GUI.window.add(new GText("Other Settings"));
        GUI.window.add(new GSpacer(25));
        GUI.window.add(new GButton(40, Style.redButtonColor, Style.redHoverColor, "Delete Account", Style.defaultFont) {
            @Override
            public void clickAction() {
                GUI.getPopUp().destroy();
                ArrayList<GUIComponent> parts = new ArrayList<>();
                parts.add(new GText("Warning!"));
                parts.add(new GSpacer(20));
                parts.add(new GText("Are you sure you want to delete this account? You will not be able to recover it.", Style.defaultFont));
                parts.add(new GSpacer(20));
                GDivider div = new GDivider(1, 2);
                div.add(new GButton(40, Style.redButtonColor, Style.redHoverColor, "Delete", Style.defaultFont, 16) {
                    @Override
                    public void clickAction() {
                        UserManager.getUsers().remove(UserManager.getLoadedUser());
                        UserManager.save();
                        GUI.getPopUp().destroy();
                        if (UserManager.getUsers().size() == 0) {
                            GUI.window.gotoPage(new Blank());
                            GUI.showPopUp(new RegisterAccount());
                            GUI.getPopUp().setPermanent();
                        } else {
                            GUI.window.gotoPage("Login");
                        }
                    }
                });
                div.add(new GButton(40, "Cancel", Style.defaultFont, 16) {
                    @Override
                    public void clickAction() {
                        GUI.getPopUp().destroy();
                        GUI.showPopUp(EditAccount.this);
                    }
                });
                parts.add(div);
                GUI.showPopUp(new GPopUp(parts));
            }
        });
    }
}
