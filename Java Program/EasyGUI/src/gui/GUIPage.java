package gui;

import control.General;
import project.Project;
import user.UserManager;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;

/**
 * Created by Robert on 4/12/17.
 * <p>
 * A GUIPage defines the build method that will be called by
 * the GUI when a page is changed. Basically that method will define what
 * components go on the page and the functions that buttons call also should
 * be defined here as GUIPages act as the model and bridge between the control and view.
 */
public abstract class GUIPage {

    /**
     * The name of the page.
     */
    private String name;

    /**
     * The menu bar.
     */
    static GMenuBar menu;

    /**
     * Create a page and sets it's name.
     *
     * @param name The name of the page.
     * @author Robert
     */
    public GUIPage(final String name) {

        refresh();

        this.name = name;
    }

    void refresh() {
        menu = new GMenuBar(40);
        menu.addPage(new GButton(40, Style.menuSideBarColor,
                Style.menuSideBarSecondaryColor, "Home", Style.defaultFont) {
            @Override
            public void clickAction() {
                GUI.window.gotoPage("Home");
            }
        });
        menu.addPage(new GSpacer(2, Color.decode("#1B5E20")));
        menu.addPage(new GButton(40, Style.menuSideBarColor,
                Style.menuSideBarSecondaryColor, "+ New Project", Style.defaultFont) {
            @Override
            public void clickAction() {
                GUI.window.gotoPage("New");
            }
        });

        if (UserManager.getLoadedUser() != null) {
            for (Project p : UserManager.getLoadedUser().getMyProjects()) {
                menu.addPage(new GButton(40, Style.menuSideBarColor,
                        Style.menuSideBarSecondaryColor, p.getName(), Style.defaultFont) {
                    @Override
                    public void clickAction() {
                        GUI.window.gotoPage(p.getSummaryPage());
                    }
                });
            }
        }

        menu.addPage(new GSpacer(2, Color.decode("#1B5E20")));
        menu.addPage(new GButton(40, Style.menuSideBarColor,
                Style.menuSideBarSecondaryColor, "About Us", Style.defaultFont) {
            @Override
            public void clickAction() {
                ArrayList<GUIComponent> parts = new ArrayList<>();

                BufferedImage image;
                try {
                    image = ImageIO.read(new File("img/pirateFlag.png"));
                } catch (Exception ex) {
                    image = null;
                }
                parts.add(new GImage(image));

                parts.add(new GSpacer(25));
                parts.add(new GText("Robert Cordingly"));

                parts.add(new GSpacer(15));
                parts.add(new GText("Graphical backend programming and UI design.", Style.defaultFont));
                parts.add(new GSpacer(15));

                parts.add(new GText("Ryan Hansen"));

                parts.add(new GSpacer(15));
                parts.add(new GText("Project system backend programming.", Style.defaultFont));
                parts.add(new GSpacer(15));

                parts.add(new GText("Rand Almaroof"));

                parts.add(new GSpacer(15));
                parts.add(new GText("Content creation and testing.", Style.defaultFont));
                parts.add(new GSpacer(15));

                parts.add(new GText("Reagan Stovall"));

                parts.add(new GSpacer(15));
                parts.add(new GText("Project development and testing.", Style.defaultFont));
                parts.add(new GSpacer(15));

                parts.add(new GSpacer(25));

                GUI.showPopUp(new GPopUp(parts));
            }
        });

        menu.addAccount(new GButton(40, Style.menuSideBarColor,
                Style.menuSideBarSecondaryColor, "Edit Account", Style.defaultFont) {
            @Override
            public void clickAction() {

                ArrayList<GUIComponent> menu = new ArrayList<>();

                //Instantiate the Checkboxes...
                GTextBox name = new GTextBox(40, UserManager.getLoadedUser().getName());
                GTextBox email = new GTextBox(40, UserManager.getLoadedUser().getEmail());

                //Place all of the components in the right places.
                menu.add(new GText("Edit Account"));
                menu.add(new GSpacer(25));
                menu.add(new GText("Name:", Style.defaultFont));
                menu.add(new GSpacer(5));
                menu.add(name);
                menu.add(new GSpacer(5));
                menu.add(new GText("Email:", Style.defaultFont));
                menu.add(new GSpacer(5));
                menu.add(email);
                menu.add(new GSpacer(5));

                //Add the submit button and program what it does.
                menu.add(new GButton(40, "Save Changes", Style.defaultFont) {
                    @Override
                    public void clickAction() {
                        String myName = name.getText().trim();
                        String myEmail = email.getText().trim();
                        if (General.testName(myName) && General.testEmail(myEmail) && (General.isEmailFree(myEmail) || !myName.equals(UserManager.getLoadedUser().getName()))) {
                            UserManager.getLoadedUser().setName(myName);
                            UserManager.getLoadedUser().setEmail(myEmail);
                            UserManager.save();
                            GUI.getPopUp().destroy();
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


                menu.add(new GSpacer(25));
                menu.add(new GText("Other Settings"));
                menu.add(new GSpacer(25));
                menu.add(new GButton(40, Style.redButtonColor, Style.redHoverColor, "Delete Account", Style.defaultFont) {
                    @Override
                    public void clickAction() {
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
                                    GUI.window.gotoPage("Register");
                                } else {
                                    GUI.window.gotoPage("Login");
                                }
                            }
                        });
                        div.add(new GButton(40, "Cancel", Style.defaultFont, 16) {
                            @Override
                            public void clickAction() {
                                GUI.getPopUp().destroy();
                            }
                        });
                        parts.add(div);
                        GUI.getPopUp().destroy();
                        GUI.showPopUp(new GPopUp(parts));
                    }
                });

                GPopUp edit = new GPopUp(menu);
                GUI.showPopUp(edit);

                //GUI.window.gotoPage("Edit Account");
            }
        });
        menu.addAccount(new GButton(40, Style.menuSideBarColor,
                Style.menuSideBarSecondaryColor, "Log Out", Style.defaultFont) {
            @Override
            public void clickAction() {
                UserManager.save();
                GUI.window.gotoPage("Login");
                GUI.horizontalOffset = 0;
            }
        });
    }

    /**
     * Returns the name of the page.
     *
     * @return The name of the page.
     * @author Robert
     */
    String getName() {
        return name;
    }

    /**
     * Assemble this page. Override this method with the proper functions.
     *
     * @author Robert
     */
    public abstract void build();

}
