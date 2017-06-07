package pages;

import gui.*;
import user.User;
import user.UserManager;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;

/**
 * Created by Robert on 6/4/17.
 *
 * A class of emptiness, so majestic.
 */
public class Blank extends GUIPage {

    /**
     * Creates a blank page.
     *
     * @author Robert
     */
    public Blank() {
        super("No one should ever see this name. If you do, stop it.");
    }

    @Override
    public void build() {
        GUI.window.add(new GSpacer(2000));
        GUI.window.add(new GText("Rrr Ye Found Me Booty!"));
        GUI.window.add(new GSpacer(20));
        BufferedImage image;
        try {
            image = ImageIO.read(new File("img/easter-egg.png"));
        } catch (Exception ex) {
            image = null;
        }
        GUI.window.add(new GImage(image));
        GUI.window.add(new GSpacer(20));
        GUI.window.add(new GButton(40, "Get back to land!", Style.defaultFont) {
            @Override
            public void clickAction() {
                User validUser = new User("Land Lubber", "Congratulations! You found the easter egg!");
                UserManager.setLoadedUser(validUser);
                GUI.window.gotoPage("Home");
            }
        });
        GUI.window.add(new GSpacer(40));
    }
}
