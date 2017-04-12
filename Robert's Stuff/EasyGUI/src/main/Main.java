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
        GUI.window.add(new GButton(25, Color.blue) {
            @Override
            public void clickAction() {
                System.out.println("Clicked home");
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
        GUI.window.add(new GButton(25, Color.blue) {
            @Override
            public void clickAction() {
                System.out.println("Clicked register");
                GUI.window.gotoPage("register");
            }
        });

        //Set the current page:
        GUI.window.gotoPage("register");


    }
}
