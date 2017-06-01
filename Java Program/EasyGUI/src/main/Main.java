package main;

import gui.*;
import pages.*;
import user.UserManager;

/**
 * This class loads the users, creates so pages and then goes
 * to the proper page depending on how many users exist.
 */
public class Main {

    public static void main(String[] args) {
        UserManager.load();

        GUI.window.setTitle("DIY App");
        GUI.window.addPage(new Login());
        GUI.window.addPage(new RegisterAccount());
        GUI.window.addPage(new About());
        GUI.window.addPage(new Home());
        GUI.window.addPage(new EditAccount());
        GUI.window.addPage(new NewProject());

        if (UserManager.getUsers().isEmpty()) {
            GUI.window.gotoPage("Register");
        } else {
            if (UserManager.getUsers().size() == 1) {
                UserManager.setLoadedUser(UserManager.getUsers().get(0));
                GUI.window.gotoPage("Home");
            } else {
                GUI.window.gotoPage("Login");
            }
        }
    }
}