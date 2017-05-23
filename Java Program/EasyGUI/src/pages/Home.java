package pages;

import gui.Style;
import gui.*;
import project.Project;
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
        if (UserManager.getLoadedUser().getMyProjects().isEmpty()
                && UserManager.getLoadedUser().getCompletedProject().isEmpty()) {
            GUI.window.add(new GText("Hello, " + UserManager.getLoadedUser().getName() + "!"));
            GUI.window.add(new GSpacer(10));
            GUI.window.add(new GText("It looks like you don't have any projects yet. Here is the basics of how to use DIYApp", Style.defaultFont));
            GUI.window.add(new GSpacer(30));

            GDivider div = new GDivider(240, 2);
            BufferedImage image;
            try {
                image = ImageIO.read(new File("img/pageIcon.png"));
            } catch (Exception ex) {
                image = null;
            }

            GDivider subdiv = new GDivider(360, 1);
            subdiv.add(new GSpacer(30));
            subdiv.add(new GText("Starting Projects:"));
            subdiv.add(new GSpacer(10));
            subdiv.add(new GText("To navigate between pages, projects, and start new projects click on the button in the top left corner next to the page title.", Style.defaultFont));
            subdiv.add(new GSpacer(20));
            div.add(subdiv);
            div.add(new GImage(image));
            GUI.window.add(div);

            GUI.window.add(new GSpacer(30));

            GDivider div2 = new GDivider(240, 2);
            BufferedImage image2;
            try {
                image2 = ImageIO.read(new File("img/userIcon.png"));
            } catch (Exception ex) {
                image2 = null;
            }

            GDivider subdiv2 = new GDivider(360, 1);
            subdiv2.add(new GSpacer(30));
            subdiv2.add(new GText("Editing Your Account:"));
            subdiv2.add(new GSpacer(10));
            subdiv2.add(new GText("If you want to change anything about your account open the right sidebar by clicking the round button in the top right corner.", Style.defaultFont));
            subdiv2.add(new GSpacer(20));
            div2.add(subdiv2);
            div2.add(new GImage(image2));
            GUI.window.add(div2);
        } else {
            GUI.window.add(new GText("Projected Earnings:"));
            GUI.window.add(new GSpacer(25));
            ArrayList<double[]> data = new ArrayList<>();
            data.add(new double[] {5, 2});
            data.add(new double[] {10, 7});
            data.add(new double[] {8, 5});
            data.add(new double[] {1, 9});
            data.add(new double[] {4, 6});
            data.add(new double[] {1, 3});
            data.add(new double[] {5, 2});
            data.add(new double[] {10, 7});
            data.add(new double[] {8, 5});
            data.add(new double[] {1, 9});
            data.add(new double[] {4, 6});
            data.add(new double[] {1, 3});
            GUI.window.add(new GGraph(data));
            GUI.window.add(new GSpacer(20));
            GDivider div = new GDivider(240, 2);
            GDivider left = new GDivider(240, 1);
            GDivider right = new GDivider(240, 1);

            left.add(new GText("Current Projects:"));
            left.add(new GSpacer(20));

            for (Project p: UserManager.getLoadedUser().getMyProjects()) {
                left.add(new GButton(40, p.getName(), Style.defaultFont, 32) {
                    @Override
                    public void clickAction() {
                        GUI.window.gotoPage(p.getSummaryPage());
                    }
                });
                left.add(new GSpacer(10));
            }


            right.add(new GText("Completed Projects:"));
            right.add(new GSpacer(20));

            for (Project p: UserManager.getLoadedUser().getCompletedProject()) {
                right.add(new GButton(40, p.getName(), Style.defaultFont, 32) {
                    @Override
                    public void clickAction() {
                        GUI.window.gotoPage(p.getSummaryPage());
                    }
                });
                right.add(new GSpacer(10));
            }


            div.add(left);
            div.add(right);
            GUI.window.add(div);

        }

        GUI.window.showMenu();
    }

}
