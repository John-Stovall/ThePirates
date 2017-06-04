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
 * This class handles the Login page.
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

        for (User u : UserManager.getUsers()) {
            GDivider subdiv = new GDivider(240);

            BufferedImage image;
            try {
                image = ImageIO.read(new File("img/userIcon1.png"));
            } catch (Exception ex) {
                image = null;
            }
            subdiv.add(new GButton(277, u.getName(), Style.defaultFont, 32, image) {
                @Override
                public void clickAction() {
                    UserManager.setLoadedUser(u);
                    GUI.window.gotoPage("Home");
                }
            });
            subdiv.add(new GSpacer(32));
            div.add(subdiv);
        }
        GUI.window.add(div);

        GUI.window.add(new GSpacer(15));

        GDivider buttons = new GDivider(240, 3);
        GDivider innerDiv1 = new GDivider(240, 1);
        innerDiv1.add(new GButton(40, "Add new Account", Style.defaultFont, 8) {
            @Override
            public void clickAction() {
                GUI.showPopUp(new RegisterAccount());
            }
        });
        innerDiv1.add(new GSpacer(10));
        GDivider innerDiv2 = new GDivider(240, 3);
        innerDiv2.add(new GButton(40, "Import", Style.defaultFont, 8) {
            @Override
            public void clickAction() {

                ArrayList<GUIComponent> parts = new ArrayList<>();
                parts.add(new GText("Warning!"));
                parts.add(new GSpacer(20));
                parts.add(new GText("Are you sure you want import accounts? This will delete any accounts and you will not be able to recover them.", Style.defaultFont));
                parts.add(new GSpacer(20));
                GDivider div = new GDivider(1, 2);
                div.add(new GButton(40, Style.redButtonColor, Style.redHoverColor, "Import", Style.defaultFont, 16) {
                    @Override
                    public void clickAction() {
                        GUI.getPopUp().destroy();
                        UserManager.importFile();
                    }
                });
                div.add(new GButton(40, "Cancel", Style.defaultFont, 16) {
                    @Override
                    public void clickAction() {
                        GUI.getPopUp().destroy();
                    }
                });
                parts.add(div);
                GUI.showPopUp(new GPopUp(parts));
            }
        });
        innerDiv2.add(new GSpacer(10));
        GDivider innerDiv3 = new GDivider(240, 3);
        innerDiv3.add(new GButton(40, "Export", Style.defaultFont, 8) {
            @Override
            public void clickAction() {
                UserManager.export();
            }
        });
        innerDiv3.add(new GSpacer(10));
        buttons.add(innerDiv1);
        buttons.add(innerDiv2);
        buttons.add(innerDiv3);
        GUI.window.add(buttons);
        GUI.window.add(new GSpacer(20));
    }
}
