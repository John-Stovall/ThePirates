package gui;

/**
 * Created by Robert on 4/12/17.
 */
public abstract class GUIPage {

    private String name;

    public GUIPage(final String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void build() {}

}
