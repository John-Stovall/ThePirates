package main;

import gui.*;

import javax.swing.*;
import java.awt.*;

public class Main {

    public static void main(String[] args) {
        start();

        User.getUsers().add(new User("Jim", "JimBob@gmail.com"));
        User.getUsers().add(new User("Joe", "asdf@aol.com"));
        User.getUsers().add(new User("John", "fdsa@msn.com"));
        User.getUsers().add(new User("Jill", "123@comcast.net"));

        final GMenuBar menu = new GMenuBar(40);
        menu.add(new GButton(20, Color.gray, Color.darkGray, "Page 1"));
        menu.add(new GButton(20, Color.gray, Color.darkGray, "Page 2"));
        menu.add(new GButton(20, Color.gray, Color.darkGray, "Page 3"));
        menu.add(new GButton(20, Color.gray, Color.darkGray, "Page 4"));
        menu.add(new GButton(20, Color.gray, Color.darkGray, "Page 5"));

        final GUIPage login = new GUIPage("login") {
            @Override
            public void build() {
                GUI.window.add(new GSpacer(25));
                GUI.window.add(new GText("Select Account"));
                GUI.window.add(new GSpacer(25));

                for (User u : User.getUsers()) {
                    GButton button = new GButton(25, Color.blue, Color.red, u.getName() + ": " + u.getEmail()) {
                        @Override
                        public void clickAction() {
                            GUI.window.gotoPage("home");
                        }
                    };
                    GUI.window.add(button);
                    GUI.window.add(new GSpacer(10));
                }

                GUI.window.add(new GSpacer(15));
                GUI.window.add(new GButton(25, Color.blue, Color.red, "Add new Account") {
                    @Override
                    public void clickAction() {
                        GUI.window.gotoPage("register");
                    }
                });
            }
        };

        //Create the "register" page..
        final GUIPage register = new GUIPage("register") {
            @Override
            public void build() {

                //Instantiate the Checkboxes...
                GTextBox name = new GTextBox(32, Color.gray, Color.white, "");
                GTextBox email = new GTextBox(32, Color.gray, Color.white, "");

                //Place all the stuff in the right order...
                GUI.window.add(new GSpacer(25));
                GUI.window.add(new GText("Register Account"));
                GUI.window.add(new GSpacer(25));
                GUI.window.add(new GText("Name:"));
                GUI.window.add(new GSpacer(5));
                GUI.window.add(name);
                GUI.window.add(new GSpacer(5));
                GUI.window.add(new GText("Email:"));
                GUI.window.add(new GSpacer(5));
                GUI.window.add(email);
                GUI.window.add(new GSpacer(5));
                GUI.window.add(new GButton(25, Color.blue, Color.red, "Submit") {
                    @Override
                    public void clickAction() {
                        //Program the submit button to do stuff...

                        //This is where you would program to make sure the name and email are good...
                        User.getUsers().add(new User(name.getText(), email.getText()));
                        GUI.window.gotoPage("login");
                    }
                });
            }
        };

        final GUIPage home = new GUIPage("home") {
            @Override
            public void build() {
                GUI.window.add(menu);
                GUI.window.add(new GSpacer(25));
                GUI.window.add(new GText("Home"));
                GUI.window.add(new GSpacer(25));
                GUI.window.add(new GText("Hello", new Font("Helvetica", Font.PLAIN, 20)));
                GUI.window.add(new GSpacer(25));
                GUI.window.add(new GSpacer(5));
                GUI.window.add(new GButton(25, Color.blue, Color.green,"Log Out") {
                    @Override
                    public void clickAction() {
                        GUI.window.gotoPage("login");
                    }
                });
                GUI.window.add(new GSpacer(5));
                GUI.window.add(new GButton(25, Color.blue, Color.magenta, "About Us") {
                    @Override
                    public void clickAction() {
                        GUI.window.gotoPage("about");
                    }
                });
            }
        };

        final GUIPage about = new GUIPage("about") {
            @Override
            public void build() {
                GUI.window.add(menu);
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
            }
        };

        GUI.window.addPage(login);
        GUI.window.addPage(register);
        GUI.window.addPage(about);
        GUI.window.addPage(home);

        GUI.window.gotoPage(login);
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
