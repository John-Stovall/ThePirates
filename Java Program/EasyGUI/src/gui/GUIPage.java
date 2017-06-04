package gui;

import pages.About;
import pages.EditAccount;
import project.Project;
import user.UserManager;

import java.awt.*;
import java.awt.event.MouseWheelEvent;


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
        this.name = name;
    }

    /**
     * This method defines the menu bar.
     *
     * @author Robert
     */
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
                GUI.showPopUp(new About());
            }
        });

        menu.addAccount(new GButton(40, Style.menuSideBarColor,
                Style.menuSideBarSecondaryColor, "Edit Account", Style.defaultFont) {
            @Override
            public void clickAction() {
                GUI.showPopUp(new EditAccount());
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
