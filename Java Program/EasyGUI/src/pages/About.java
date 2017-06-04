package pages;

import gui.Style;
import gui.GSpacer;
import gui.GText;
import gui.GUI;
import gui.GUIPage;

/**
 * Created by Robert on 5/10/17.
 *
 * This is the About Us page.
 *
 * This page has been replaced by a popup in GUIPage
 */
@Deprecated
public class About extends GUIPage {

    public About() {
        super("About");
    }

    @Override
    public void build() {
        GUI.window.add(new GSpacer(40));
        GUI.window.add(new GSpacer(25));
        GUI.window.add(new GText("The Pirates:"));

        GUI.window.add(new GSpacer(25));
        GUI.window.add(new GText("Robert Cordingly"));

        GUI.window.add(new GSpacer(15));
        GUI.window.add(new GText("Graphical backend programming and UI design.", Style.defaultFont));
        GUI.window.add(new GSpacer(15));

        GUI.window.add(new GText("Ryan Hansen"));

        GUI.window.add(new GSpacer(15));
        GUI.window.add(new GText("Project system backend programming.", Style.defaultFont));
        GUI.window.add(new GSpacer(15));

        GUI.window.add(new GText("Rand Almaroof"));

        GUI.window.add(new GSpacer(15));
        GUI.window.add(new GText("Content creation and testing.", Style.defaultFont));
        GUI.window.add(new GSpacer(15));

        GUI.window.add(new GText("Reagan Stovall"));

        GUI.window.add(new GSpacer(15));
        GUI.window.add(new GText("Project development and testing.", Style.defaultFont));
        GUI.window.add(new GSpacer(15));

        GUI.window.add(new GSpacer(25));
        GUI.window.showMenu();
    }
}
