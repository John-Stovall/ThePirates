package pages;

import gui.*;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;

/**
 * Created by Robert on 5/10/17.
 *
 * This is the About Us page.
 */
public class About extends GUIPage {

    /**
     * Creates the about page.
     *
     * @author Robert
     */
    public About() {
        super("About");
    }

    @Override
    public void build() {
        BufferedImage image;
        try {
            image = ImageIO.read(new File("img/pirateFlag.png"));
        } catch (Exception ex) {
            image = null;
        }
        GUI.window.add(new GImage(image));

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
    }
}
