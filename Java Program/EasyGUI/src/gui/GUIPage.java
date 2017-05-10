package gui;

import java.awt.*;

/**
 * Created by Robert on 4/12/17.
 *
 * The GUI is broken into pages. Ya.
 */
public abstract class GUIPage {

    /** The name of the page. */
    private String name;

    protected static GMenuBar menu;

    protected static Color mainColor = Color.decode("#2E7D32");

    protected static Color secondaryColor = Color.decode("#388E3C");

    /**
     * Create a page and sets it's name.
     *
     * @param name The name of the page.
     */
    public GUIPage(final String name) {

        menu = new GMenuBar(40);
        menu.addPage(new GButton(40, mainColor, secondaryColor, "Home", new Font("Helvetica", Font.PLAIN, 20)) {
            @Override
            public void clickAction() {
                GUI.window.gotoPage("Home");
            }
        });
        menu.addPage(new GSpacer(2, Color.decode("#1B5E20")));
        menu.addPage(new GButton(40, mainColor, secondaryColor, "+ New Project", new Font("Helvetica", Font.PLAIN, 20)) {
            @Override
            public void clickAction() {
                GUI.window.gotoPage("New");
            }
        });
        menu.addPage(new GSpacer(2, Color.decode("#1B5E20")));
        menu.addPage(new GButton(40, mainColor, secondaryColor, "About Us", new Font("Helvetica", Font.PLAIN, 20)) {
            @Override
            public void clickAction() {
                GUI.window.gotoPage("About");
            }
        });

        menu.addAccount(new GButton(40, mainColor, secondaryColor, "Edit Account", new Font("Helvetica", Font.PLAIN, 20)) {
            @Override
            public void clickAction() {
                GUI.window.gotoPage("Edit Account");
            }
        });
        menu.addAccount(new GButton(40, mainColor, secondaryColor, "Log Out", new Font("Helvetica", Font.PLAIN, 20)) {
            @Override
            public void clickAction() {
                GUI.window.gotoPage("Login");
                GUI.horizontalOffset = 0;
            }
        });

        this.name = name;
    }

    /**
     * Returns the name of the page.
     *
     * @return
     */
    public String getName() {
        return name;
    }

    /**
     * Assemble this page. Override this method with the proper functions.
     */
    public void build() {}

}
