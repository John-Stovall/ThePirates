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
        start();

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
     * This HAS to be the first reference to GUI.window so that it initializes the singleton
     * in here. It's really hacky and I don't like it but whatever.
     */
    private static void start() {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                GUI.window.setTitle("DIY Program");
            }
        });
    }
}