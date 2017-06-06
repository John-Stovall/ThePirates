package project;

import gui.*;
import pages.ProjectChooser;
import user.UserManager;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;

/**
 * @author Ryan Hansen
 * @author Robert Cordingly
 *         <p>
 *         Abstract class which declares the getters and setters for the
 *         project classes.
 */
public abstract class Project implements Serializable {

    /**
     * The name of the project.
     */
    protected String name;

    protected String type;

    /**
     * The initial cost of the project.
     */
    double initialCost;

    /**
     * This method gets the summary page that is associated with
     * this project. The summary page draws basic information
     * like monthly savings and a graph.
     *
     * @return The GUIPage for this project.
     * @author Robert
     */
    public GUIPage getSummaryPage() {
        return new GUIPage(name) {
            @Override
            public void build() {
                GUI.window.add(new GSpacer(40));
                GUI.window.add(new GSpacer(25));
                BufferedImage image;
                try {
                    switch (type) {
                        case ("Insulation"):
                            image = ImageIO.read(new File("img/insulation.png"));
                            break;
                        case ("Lights"):
                            image = ImageIO.read(new File("img/lightbulb.png"));
                            break;
                        default:
                            image = ImageIO.read(new File("img/insulation.png"));
                            break;
                    }

                } catch (Exception ex) {
                    image = null;
                }
                GDivider title = new GDivider(1, 2);
                title.add(new GImage(image, 0.5));

                GDivider right = new GDivider(1, 1);
                right.add(new GSpacer(40));
                right.add(new GText(name));
                right.add(new GSpacer(5));
                right.add(new GText(type, Style.defaultFont));
                title.add(right);
                GUI.window.add(title);
                GUI.window.add(new GSpacer(20));
                GUI.window.add(new GText("Potential Savings:"));
                GUI.window.add(new GSpacer(25));
                ArrayList<double[]> data = new ArrayList<>();

                data.add(new double[]{0, 0});

                boolean payedFor = false;
                int payedDate = 0;
                double potential = -getInitialCost();
                for (int i = 0; i < 12; i++) {
                    potential += getMonthlySavings();
                    if (potential >= 0 && !payedFor) {
                        payedDate = i + 1;
                        payedFor = true;
                    }
                    data.add(new double[]{potential});
                }

                if (potential > 0) {
                    GGraph graph = new GGraph(data);
                    graph.setLabel1("• Project Savings");
                    GUI.window.add(graph);
                    GUI.window.add(new GSpacer(20));

                    GUI.window.add(new GText("Details:"));
                    GUI.window.add(new GSpacer(20));

                    //The RADICAL option is sort of an inside joke with all the games I make.
                    String[] words = {"Nice", "Awesome", "Great", "Spectacular", "RADICAL", "Wow", "Cool"};
                    int rnd = new Random().nextInt(words.length);

                    GUI.window.add(new GText(words[rnd] + "! After one year this project will save you $" + Math.round(potential) + ".", Style.defaultFont));

                    if (payedDate != 1) {
                        GUI.window.add(new GSpacer(10));
                        GUI.window.add(new GText("This project will pay for itself in " + payedDate + " months.", Style.defaultFont));
                    }
                } else {
                    GUI.window.add(new GSpacer(50));
                    GUI.window.add(new GText("Oh no! This project won't save you any money this year. Try reducing the initial costs and change other settings to make the project more profitable.", Style.defaultFont));
                    GUI.window.add(new GSpacer(50));
                }

                GUI.window.add(new GSpacer(20));

                GDivider buttons = new GDivider(240, 3);
                if (UserManager.getLoadedUser().getMyProjects().contains(Project.this)) {

                    GDivider innerDiv1 = new GDivider(240, 1);
                    GDivider innerDiv2 = new GDivider(240, 1);
                    GDivider innerDiv3 = new GDivider(240, 1);

                    innerDiv1.add(new GButton(40, "Edit Project", Style.defaultFont, 8) {
                        @Override
                        public void clickAction() {
                            GUI.showPopUp(getEditPage());
                            //GUI.window.gotoPage(getEditPage());
                        }
                    });
                    innerDiv1.add(new GSpacer(10));
                    buttons.add(innerDiv1);
                    if (UserManager.getLoadedUser().getMyProjects().size() > 1) {
                        innerDiv3.add(new GButton(40, "Compare To...", Style.defaultFont, 8) {
                            @Override
                            public void clickAction() {
                                GUI.showPopUp(new ProjectChooser(Project.this));
                            }
                        });
                        innerDiv3.add(new GSpacer(10));
                        buttons.add(innerDiv3);
                    }
                    innerDiv2.add(new GButton(40, "Completed", Style.defaultFont, 8) {
                        @Override
                        public void clickAction() {
                            UserManager.getLoadedUser().projectComplete(Project.this);
                            GUI.window.gotoPage("Home");
                        }
                    });
                    innerDiv2.add(new GSpacer(10));
                    buttons.add(innerDiv2);


                } else {
                    if (UserManager.getLoadedUser().getMyProjects().size() < 10) {
                        GUI.window.add(new GButton(40, "Resume Project", Style.defaultFont, 8) {
                            @Override
                            public void clickAction() {
                                UserManager.getLoadedUser().projectResume(Project.this);
                                GUI.window.gotoPage(getSummaryPage());

                            }
                        });
                    } else {
                        GUI.window.add(new GText("To resume this project either delete one or mark one as completed.", Style.defaultFont));
                    }
                }
                GUI.window.add(buttons);
                GUI.window.add(new GSpacer(40));
                GUI.window.showMenu();
            }
        };
    }


    /**
     * This method gets the edit page that is associated with
     * this project. The edit page contains all of the text boxes
     * associated with editing this page.
     *
     * @return The GUIPage for this project.
     * @author Robert
     */
    public abstract GUIPage getEditPage();

    /**
     * This method sets the name of this project.
     *
     * @param theName The name of the project.
     * @author Ryan
     */
    public void setName(String theName) {
        this.name = theName;
    }

    /**
     * This method gets the name of this project.
     *
     * @return The name of the project.
     * @author Ryan
     */
    public String getName() {
        return this.name;
    }

    /**
     * This method will get the monthly savings for this project.
     * This method is abstract so the children of this class
     * must implement it.
     *
     * @return The amount of money saved every month.
     * @author Robert
     */
    public abstract double getMonthlySavings();

    /**
     * This method will get the initial cost of the project.
     *
     * @return The initial cost of this project.
     * @author Ryan
     */
    public double getInitialCost() {
        return initialCost;
    }
}
