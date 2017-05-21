package pages;

import gui.Style;
import gui.*;
import user.User;
import user.UserManager;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;

/**
 * Created by Robert on 5/10/17.
 *
 * This is the home page.
 */
public class Home extends GUIPage {

    public Home() {
        super("Home");
    }

    @Override
    public void build() {
        GUI.window.add(new GSpacer(40));
        GUI.window.add(new GSpacer(25));
        if (UserManager.getLoadedUser().getMyProjects().isEmpty()) {
            GUI.window.add(new GText("Hello, " + UserManager.getLoadedUser().getName() + "!"));
            GUI.window.add(new GSpacer(10));
            GUI.window.add(new GText("It looks like you don't have any projects yet.", Style.defaultFont));
            GUI.window.add(new GSpacer(30));
            GDivider div = new GDivider(720, 1);

            BufferedImage image;
            try {
                image = ImageIO.read(new File("img/pageIcon.png"));
            } catch (Exception ex) {
                image = null;
            }
            div.add(new GButton(277, "", Style.defaultFont, 32, image) {
                @Override
                public void clickAction() {
                }
            });


            GUI.window.add(div);
            GUI.window.add(new GText("Starting Projects:"));
            GUI.window.add(new GSpacer(10));
            GUI.window.add(new GText("To navigate between pages, projects, and start new projects click on the button in the top left corner next to the page title.", Style.defaultFont));
            GUI.window.add(new GSpacer(30));
            GUI.window.add(new GText("Editing Your Account:"));
            GUI.window.add(new GSpacer(10));
            GUI.window.add(new GText("If you want to change anything about your account open the right sidebar by clicking the round button in the top right corner. ", Style.defaultFont));
        } else {

        }

        GUI.window.showMenu();
    }

}
