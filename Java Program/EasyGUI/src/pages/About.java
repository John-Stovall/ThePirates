package pages;

import control.Style;
import gui.GSpacer;
import gui.GText;
import gui.GUI;
import gui.GUIPage;

/**
 * Created by Robert on 5/10/17.
 *
 * This is the About Us page.
 */
public class About extends GUIPage {

    public About() {
        super("About");
    }

    @Override
    public void build() {
        GUI.window.add(new GSpacer(40));
        GUI.window.add(new GSpacer(25));
        GUI.window.add(new GText("About Us"));

        GUI.window.add(new GSpacer(25));
        GUI.window.add(new GText("Robert Cordingly", Style.defaultFont));
        GUI.window.add(new GSpacer(15));
        GUI.window.add(new GText("Ryan Hansen", Style.defaultFont));
        GUI.window.add(new GSpacer(15));
        GUI.window.add(new GText("Rand Almaroof", Style.defaultFont));
        GUI.window.add(new GSpacer(15));
        GUI.window.add(new GText("Reagan Stovall", Style.defaultFont));
        GUI.window.add(new GSpacer(15));

        GUI.window.add(new GSpacer(25));
        GUI.window.add(menu);
    }
}
