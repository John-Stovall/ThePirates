package main;

import gui.*;

import java.awt.*;

public class Main {

    public static void main(String[] args) {

        User.getUsers().add(new User("Jim", "JimBob@gmail.com"));
        User.getUsers().add(new User("Joe", "asdf@aol.com"));
        User.getUsers().add(new User("John", "fdsa@msn.com"));
        User.getUsers().add(new User("Jill", "123@comcast.net"));

        start();

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

        final GUIPage register = new GUIPage("register") {
            @Override
            public void build() {
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
                        GUI.window.gotoPage("login");
                    }
                });
            }
        };

        final GUIPage home = new GUIPage("home") {
            @Override
            public void build() {
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

    private static void start() {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                GUI.window.setTitle("DIY Program");
            }
        });
    }
}
