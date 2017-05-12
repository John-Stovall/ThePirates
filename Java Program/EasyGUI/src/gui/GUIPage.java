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

    /** The menu bar. */
    protected static GMenuBar menu;

    /** The primary color used by many different things. */
    protected static final Color mainColor = Color.decode("#2E7D32");

    /** The secondary color used by many different things. */
    protected static final Color secondaryColor = Color.decode("#388E3C");
    
    /** The default font used by many different things. */
    protected static final Font defaultFont = new Font("Helvetica", Font.PLAIN, 20);

    /**
     * Create a page and sets it's name.
     *
     * @param name The name of the page.
     */
    public GUIPage(final String name) {

        menu = new GMenuBar(40);
        menu.addPage(new GButton(40, mainColor, secondaryColor, "Home", defaultFont) {
            @Override
            public void clickAction() {
                GUI.window.gotoPage("Home");
            }
        });
        menu.addPage(new GSpacer(2, Color.decode("#1B5E20")));
        menu.addPage(new GButton(40, mainColor, secondaryColor, "+ New Project", defaultFont) {
            @Override
            public void clickAction() {
                GUI.window.gotoPage("New");
            }
        });
        menu.addPage(new GSpacer(2, Color.decode("#1B5E20")));
        menu.addPage(new GButton(40, mainColor, secondaryColor, "About Us", defaultFont) {
            @Override
            public void clickAction() {
                GUI.window.gotoPage("About");
            }
        });

        menu.addAccount(new GButton(40, mainColor, secondaryColor, "Edit Account", defaultFont) {
            @Override
            public void clickAction() {
                GUI.window.gotoPage("Edit Account");
            }
        });
        menu.addAccount(new GButton(40, mainColor, secondaryColor, "Log Out", defaultFont) {
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
     * @return The name of the page.
     */
    String getName() {
        return name;
    }

    /**
     * Assemble this page. Override this method with the proper functions.
     */
    public void build() {}

}
