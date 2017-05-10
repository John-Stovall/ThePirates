package pages;

import gui.*;
import main.User;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

/**
 * Created by robertcordingly on 5/10/17.
 */
public class Login extends GUIPage {
    /**
     * Create a page and sets it's name.
     */
    public Login() {
        super("Login");
    }

    @Override
    public void build() {
        GUI.window.add(new GSpacer(25));
        GUI.window.add(new GText("Select Account"));
        GUI.window.add(new GSpacer(25));

        GDivider div = new GDivider(240, 3);

        for (User u : User.getUsers()) {
            GDivider subdiv = new GDivider(240);

            BufferedImage image;
            try {
                image = ImageIO.read(new File("img/userIcon1.png"));
            } catch (Exception ex) {
                image = null;
            }
            subdiv.add(new GButton(277, mainColor, secondaryColor, u.getName(), new Font("Helvetica", Font.PLAIN, 20),32, image) {
                @Override
                public void clickAction() {
                    User.setLoadedUser(u);
                    GUI.window.gotoPage("Home");
                }
            });
            subdiv.add(new GSpacer(32));
            div.add(subdiv);
        }
        GUI.window.add(div);

        GUI.window.add(new GSpacer(15));

        GDivider centerbutton = new GDivider(240, 1);
        GDivider innerDiv = new GDivider(240, 3);
        innerDiv.add(new GSpacer(0));
        innerDiv.add(new GButton(40, mainColor, secondaryColor, "Add new Account", new Font("Helvetica", Font.PLAIN, 20)) {
            @Override
            public void clickAction() {
                GUI.window.gotoPage("Register");
            }
        });
        centerbutton.add(innerDiv);
        GUI.window.add(centerbutton);
    }
}
