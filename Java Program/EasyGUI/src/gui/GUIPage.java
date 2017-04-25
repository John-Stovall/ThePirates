package gui;

/**
 * Created by Robert on 4/12/17.
 *
 * The GUI is broken into pages. Ya.
 */
public abstract class GUIPage {

    /** The name of the page. */
    private String name;

    /**
     * Create a page and sets it's name.
     *
     * @param name The name of the page.
     */
    public GUIPage(final String name) {
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
