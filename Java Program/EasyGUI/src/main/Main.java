package main;

import gui.*;

import java.awt.*;

import java.util.regex.*;

/**
 * Main is used to build all the the page layouts. Eventually this will be broken up into
 * a few classes so that it's not just one gigantic file. Plus each class will represent a
 * project.
 */
public class Main {

    public static void main(String[] args) {
        start();

        //Create some testing users...
//        User.getUsers().add(new User("Jim", "JimBob@gmail.com"));
//        User.getUsers().add(new User("Joe", "asdf@aol.com"));
//        User.getUsers().add(new User("John", "fdsa@msn.com"));
//        User.getUsers().add(new User("Jill", "123@comcast.net"));

        //Assemble the menu bar..
        final GMenuBar menu = new GMenuBar(40);
        menu.addPage(new GButton(40, Color.decode("#2E7D32"), Color.decode("#388E3C"), "Home", new Font("Helvetica", Font.PLAIN, 20)) {
            @Override
            public void clickAction() {
                GUI.window.gotoPage("home");
            }
        });
        menu.addPage(new GButton(40, Color.decode("#2E7D32"), Color.decode("#388E3C"), "Page 2", new Font("Helvetica", Font.PLAIN, 20)));
        menu.addPage(new GButton(40, Color.decode("#2E7D32"), Color.decode("#388E3C"), "Page 3", new Font("Helvetica", Font.PLAIN, 20)));
        menu.addPage(new GButton(40, Color.decode("#2E7D32"), Color.decode("#388E3C"), "Page 4", new Font("Helvetica", Font.PLAIN, 20)));
        menu.addPage(new GButton(40, Color.decode("#2E7D32"), Color.decode("#388E3C"), "About Us", new Font("Helvetica", Font.PLAIN, 20)) {
            @Override
            public void clickAction() {
                GUI.window.gotoPage("about");
            }
        });

        menu.addAccount(new GButton(40, Color.decode("#2E7D32"), Color.decode("#388E3C"), "Page 1", new Font("Helvetica", Font.PLAIN, 20)));
        menu.addAccount(new GButton(40, Color.decode("#2E7D32"), Color.decode("#388E3C"), "Page 2", new Font("Helvetica", Font.PLAIN, 20)));
        menu.addAccount(new GButton(40, Color.decode("#2E7D32"), Color.decode("#388E3C"), "Log Out", new Font("Helvetica", Font.PLAIN, 20)) {
            @Override
            public void clickAction() {
                GUI.window.gotoPage("login");
                GUI.window.horizontalOffset = 0;
            }
        });

        //Build the login page..
        final GUIPage login = new GUIPage("login") {
            @Override
            public void build() {
                GUI.window.add(new GSpacer(25));
                GUI.window.add(new GText("Select Account"));
                GUI.window.add(new GSpacer(25));

                GDivider div = new GDivider(240);

                for (User u : User.getUsers()) {
                    GDivider subdiv = new GDivider(240);
                    subdiv.add(new GText(u.getName()));
                    subdiv.add(new GSpacer(5));
                    subdiv.add(new GSpacer(1, Color.BLACK));
                    subdiv.add(new GSpacer(5));
                    subdiv.add(new GText(u.getEmail(), new Font("Helvetica", Font.PLAIN, 20)));
                    subdiv.add(new GSpacer(10));

                    subdiv.add(new GButton(25, Color.darkGray, Color.gray, "Login") {
                        @Override
                        public void clickAction() {
                            User.setLoadedUser(u);
                            GUI.window.gotoPage("home");
                        }
                    });
                    div.add(subdiv);
                    //GUI.window.add(button);
                    //GUI.window.add(new GSpacer(10));
                }
                GUI.window.add(div);

                GUI.window.add(new GSpacer(15));
                GUI.window.add(new GButton(25, Color.darkGray, Color.gray, "Add new Account") {
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
                GUI.window.add(new GText("Create Account"));
                GUI.window.add(new GSpacer(25));
                GUI.window.add(new GText("Name:"));
                GUI.window.add(new GSpacer(5));
                GUI.window.add(name);
                GUI.window.add(new GSpacer(5));
                GUI.window.add(new GText("Email:"));
                GUI.window.add(new GSpacer(5));
                GUI.window.add(email);
                GUI.window.add(new GSpacer(5));
                GUI.window.add(new GButton(25, Color.darkGray, Color.gray, "Submit") {
                    @Override
                    public void clickAction() {
                        //Program the submit button to do stuff...

                        // Apologies, I only know how to code ugly.
                    	boolean textBoxFail = false;
                    	String myName = name.getText().trim();
                    	String myEmail = email.getText().trim();
                    	
                    	// the two functions are set up this way for junit testing
                    	if(!testName(myName) || !testEmail(myEmail)){
                    		textBoxFail = true; //fails the test
                    	}
                    	
                    	if(!textBoxFail){
	                        //This is the code for a successful login.
	                        User validUser = new User(myName, myEmail);
	                        User.getUsers().add(validUser);
	                        User.setLoadedUser(validUser);
	                        GUI.window.gotoPage("home");
                    	}
                    }
                });

                GUI.window.add(new GSpacer(5));
                GUI.window.add(new GButton(25, Color.darkGray, Color.gray, "Skip Login") {
                    @Override
                    public void clickAction() {
                        User validUser = new User("Jimbo", "jimbo@gmail.com");
                        User.getUsers().add(validUser);
                        User.setLoadedUser(validUser);
                        GUI.window.gotoPage("home");
                    }
                });


                if (!User.getUsers().isEmpty()) {
                    GUI.window.add(new GSpacer(5));
                    GUI.window.add(new GButton(25, Color.darkGray, Color.gray, "Back") {
                        @Override
                        public void clickAction() {
                            GUI.window.gotoPage("login");
                        }
                    });
                }
            }
        };

        //Build the home page...
        final GUIPage home = new GUIPage("home") {
            @Override
            public void build() {
                GUI.window.add(new GSpacer(40));
                GUI.window.add(new GSpacer(25));
                GUI.window.add(new GText("Home"));
                GUI.window.add(new GSpacer(25));
                GUI.window.add(new GText("Hello, " + User.getLoadedUser().getName() + "! Lorem ipsum dolor sit amet, consectetur adipiscing elit. Donec quis tortor id est facilisis sodales pulvinar congue lectus. Nullam suscipit vulputate ligula quis congue. Ut consectetur fringilla lacinia. Aenean in ornare magna, tristique lacinia est. Aenean at elit vehicula, tincidunt leo at, convallis tellus. Nam mollis, odio quis efficitur porttitor, ante mi tincidunt ligula, quis ornare mauris nunc sed quam. Donec molestie enim odio, id viverra risus convallis a. Praesent et mi mauris. Nam sagittis eu sapien non accumsan.", new Font("Helvetica", Font.PLAIN, 20)));
                GUI.window.add(new GSpacer(25));
                GUI.window.add(new GSpacer(5));
                GUI.window.add(new GSpacer(5));
                GUI.window.add(menu);
            }
        };

        //Build the about page...
        final GUIPage about = new GUIPage("about") {
            @Override
            public void build() {
                GUI.window.add(new GSpacer(40));
                GUI.window.add(new GSpacer(25));
                GUI.window.add(new GText("About Us"));

                GUI.window.add(new GSpacer(25));
                GUI.window.add(new GText("Robert Cordingly", new Font("Helvetica", Font.PLAIN, 20)));
                GUI.window.add(new GSpacer(15));
                GUI.window.add(new GText("Ryan Hansen", new Font("Helvetica", Font.PLAIN, 20)));
                GUI.window.add(new GSpacer(15));
                GUI.window.add(new GText("Rand Almaroof", new Font("Helvetica", Font.PLAIN, 20)));
                GUI.window.add(new GSpacer(15));
                GUI.window.add(new GText("Reagan Stovall", new Font("Helvetica", Font.PLAIN, 20)));
                GUI.window.add(new GSpacer(15));

                GUI.window.add(new GSpacer(25));
                GUI.window.add(menu);
            }
        };

        GUI.window.addPage(login);
        GUI.window.addPage(register);
        GUI.window.addPage(about);
        GUI.window.addPage(home);

        if (User.getUsers().isEmpty()) {
            GUI.window.gotoPage(register);
        } else {
            GUI.window.gotoPage(login);
        }
    }
    
    /**
     * This is a simple character counter that returns false if there are
     * less than 3 characters.
     *
     * @param name
     * @return
     */
    public static boolean testName(String name){
    	if(name.length() < 3){
    		System.out.println("Less than 3 characters");
    		return false;
    	}else{
    		System.out.println("LengthTest Passed");
    		return true;
    	}
    }
    
    /**
     * This tests that there is an @ sign and a '.com' a bit lacking,
     * but we can expand it if we want.
     *  
     * @param email
     * @return
     */
    public static boolean testEmail(String email){
    	// contains @
    	if(email.indexOf('@') == -1){
    		System.out.println("has doesn't have an @");
    		return false;
    	}else{
    		System.out.println("@test Passed");
    	}
    	// contains a '.com' at the end 
    	if(!email.toLowerCase().endsWith(".com")) {
    		System.out.println("does not end with .com");
    		return false;
    	}else{
    		System.out.println(".com-test Passed");
    	}
		return true;
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
