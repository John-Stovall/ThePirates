package pages;

import gui.GSpacer;
import gui.GText;
import gui.GUI;
import gui.GUIPage;

import java.awt.*;

/**
 * Created by robertcordingly on 5/10/17.
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
}
