package main;

import gui.*;

import java.awt.*;

public class Main {

    public static void main(String[] args) {

        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                GUI.window.setTitle("DIY Program");
            }
        });

        //Build the register page
        GUI.window.gotoPage("register");
        GUI.window.add(new GSpacer(25));
        GUI.window.add(new GText("Register Account"));
        GUI.window.add(new GSpacer(25));
        GUI.window.add(new GText("Name:"));
        GUI.window.add(new GSpacer(5));
        GUI.window.add(new GSpacer(32, Color.gray));
        GUI.window.add(new GSpacer(5));
        GUI.window.add(new GText("Email:"));
        GUI.window.add(new GSpacer(5));
        GUI.window.add(new GSpacer(32, Color.gray));
        GUI.window.add(new GSpacer(5));
        GUI.window.add(new GButton(25, Color.blue, Color.red, "Submit") {
            @Override
            public void clickAction() {
                GUI.window.gotoPage("home");
            }
        });

        //Build the home page
        GUI.window.gotoPage("home");
        GUI.window.add(new GSpacer(25));
        GUI.window.add(new GText("Home"));
        GUI.window.add(new GSpacer(25));
        GUI.window.add(new GText("There is nothing on this page yet."));
        GUI.window.add(new GSpacer(25));
        GUI.window.add(new GText("Register New Account:"));
        GUI.window.add(new GSpacer(5));
        GUI.window.add(new GButton(25, Color.blue, Color.green,"Go Register") {
            @Override
            public void clickAction() {
                GUI.window.gotoPage("register");
            }
        });
        GUI.window.add(new GSpacer(5));
        GUI.window.add(new GButton(25, Color.blue, Color.magenta, "About Us") {
            @Override
            public void clickAction() {
                GUI.window.gotoPage("about");
            }
        });

        //Build the about pages
        GUI.window.gotoPage("about");
        GUI.window.add(new GSpacer(25));
        GUI.window.add(new GText("About Us"));

        GUI.window.add(new GSpacer(25));
        GUI.window.add(new GText("Robert Cordingly", new Font("Helvetica", Font.PLAIN, 20)));
        GUI.window.add(new GSpacer(15));
        GUI.window.add(new GText("Add your names here...", new Font("Helvetica", Font.PLAIN, 20)));

        GUI.window.add(new GSpacer(25));
        GUI.window.add(new GButton(25, Color.blue, Color.pink, "Go Back") {
            @Override
            public void clickAction() {
                GUI.window.gotoPage("home");
            }
        });

        //Set the current page:
        GUI.window.gotoPage("register");


    }
}
