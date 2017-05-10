package main;

import gui.*;
import pages.*;

import java.awt.*;

/**
 * Main is used to build all the the page layouts. Eventually this will be broken up into
 * a few classes so that it's not just one gigantic file. Plus each class will represent a
 * project.
 */
public class Main {

    public static void main(String[] args) {

        GUI.window.setTitle("DIY Program");

        GUI.window.addPage(new Login());
        GUI.window.addPage(new RegisterAccount());
        GUI.window.addPage(new About());
        GUI.window.addPage(new Home());
        GUI.window.addPage(new EditAccount());
        GUI.window.addPage(new NewProject());

        if (User.getUsers().isEmpty()) {
            GUI.window.gotoPage("Register");
        } else {
            GUI.window.gotoPage("Login");
        }
    }

    /**
     * Old code that we don't need anymore apparently. Leaving it there in case we run into problems without it.
     */
    //private static void start() {
        //EventQueue.invokeLater(() -> GUI.window.setTitle("DIY Program"));
    //}
}