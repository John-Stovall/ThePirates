package project;

import gui.*;
import pages.ProjectChooser;
import user.UserManager;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * @author Ryan Hansen
 * @author Robert Cordingly
 *
 * Insulation Project class.
 * Used for creating a new insulation project.
 */
public class LightProject extends Project implements Serializable {

    /**
     * default version ID for serialization
     */
    private static final long serialVersionUID = -1950641840941379038L;

    // Class specific variable used for determining the insulating properties
    // of the materials being used.
    private int rValue;

    // constructor sets the water usage to -1.0 because there is no way for a insulation project
    // to have an impact on water consumption. This will be beneficial for our 'Math' class.
    public LightProject(final String name) {
        this.name = name;
    }

    @Override
    public double getMonthlySavings() {
        //TODO: Program this to properly calculate the monthly savings.


        return 2.0;
    }

    @Override
    public double getInitialCost() {
        return initialCost;
    }

    @Override
    public GUIPage getSummaryPage() {
        return new GUIPage(name) {
            @Override
            public void build() {
                GUI.window.add(new GSpacer(40));
                GUI.window.add(new GSpacer(25));
                GUI.window.add(new GText(name + " Summary"));
                GUI.window.add(new GSpacer(25));ArrayList<double[]> data = new ArrayList<>();

                data.add(new double[] {0, 0});

                boolean payedFor = false;
                int payedDate = 0;
                double potential = -getInitialCost();
                for (int i = 0; i < 12; i++) {
                    potential += getMonthlySavings();
                    if (potential >= 0 && !payedFor) {
                        payedDate = i + 1;
                        payedFor = true;
                    }
                    data.add(new double[] {potential});
                }

                if (potential > 0) {
                    GUI.window.add(new GGraph(data));
                    GUI.window.add(new GSpacer(20));

                    GUI.window.add(new GText("Details:"));
                    GUI.window.add(new GSpacer(20));
                    GUI.window.add(new GText("Nice! After one year this project will save you $" + Math.round(potential) + ".", Style.defaultFont));

                    if (payedDate != 1) {
                        GUI.window.add(new GSpacer(10));
                        GUI.window.add(new GText("This project will pay for itself in " + payedDate + " months.", Style.defaultFont));
                    }
                } else {
                    GUI.window.add(new GText("Oh no! This project won't save you any money this year. Try reducing the initial costs and change other settings to make the project more profitable.", Style.defaultFont));
                }

                GUI.window.add(new GSpacer(20));

                GDivider buttons = new GDivider(240, 3);
                if (UserManager.getLoadedUser().getMyProjects().contains(LightProject.this)) {

                    GDivider innerDiv1 = new GDivider(240, 1);
                    GDivider innerDiv2 = new GDivider(240, 1);
                    GDivider innerDiv3 = new GDivider(240, 1);

                    innerDiv1.add(new GButton(40, "Edit Project", Style.defaultFont, 8) {
                        @Override
                        public void clickAction() {
                            GUI.window.gotoPage(getEditPage());
                        }
                    });
                    innerDiv1.add(new GSpacer(10));
                    innerDiv3.add(new GButton(40, "Compare To...", Style.defaultFont, 8) {
                        @Override
                        public void clickAction() {
                            GUI.window.gotoPage(new ProjectChooser(LightProject.this));
                        }
                    });
                    innerDiv3.add(new GSpacer(10));
                    innerDiv2.add(new GButton(40, "Completed", Style.defaultFont, 8) {
                        @Override
                        public void clickAction() {
                            UserManager.getLoadedUser().projectComplete(LightProject.this);
                            GUI.window.gotoPage("Home");
                        }
                    });
                    innerDiv2.add(new GSpacer(10));
                    buttons.add(innerDiv1);
                    buttons.add(innerDiv3);
                    buttons.add(innerDiv2);


                } else {
                    GUI.window.add(new GButton(40, "Resume Project", Style.defaultFont, 8) {
                        @Override
                        public void clickAction() {
                            UserManager.getLoadedUser().projectResume(LightProject.this);
                            GUI.window.gotoPage(getSummaryPage());
                        }
                    });
                }
                GUI.window.add(buttons);
                GUI.window.add(new GSpacer(40));
                GUI.window.showMenu();
            }
        };
    }

    @Override
    public GUIPage getEditPage() {
        return new GUIPage("Edit " + name) {
            @Override
            public void build() {
                GUI.window.add(new GSpacer(40));
                GUI.window.add(new GSpacer(25));
                GUI.window.add(new GText("Edit " + name));
                GUI.window.add(new GSpacer(25));

                //Create all of the text boxes and drop downs for this page.
                GTextBox currentWatts = new GTextBox(40, "", "Current one light bulb wattage.");
                GTextBox newWatts = new GTextBox(40, "", "Recommended Wattage would be less than the current wattage to save on energy.");
                GTextBox numberOfLightBulb = new GTextBox(40, "", "Number of light bulbs that desired to change.");
                GTextBox lightBulbUsage = new GTextBox(40, "", "Hours in which the Light Bulbs are ON per day.");
                GTextBox pricePerUnit = new GTextBox(40, "", "Dollar amount for one light bulb.");
                GTextBox lightBulbPrice = new GTextBox(40, initialCost + "", "The price of the new light bulbs.");

                GUI.window.add(new GText("Number of Light Desired to Change: ", Style.defaultFont));
                GUI.window.add(new GSpacer(5));
                GUI.window.add((GUIComponent) numberOfLightBulb);

                GUI.window.add(new GSpacer(5));
                GUI.window.add(new GText("Hours Light Bulbs are ON:", Style.defaultFont));
                GUI.window.add(new GSpacer(5));
                GUI.window.add((GUIComponent) lightBulbUsage);

                GUI.window.add(new GSpacer(5));
                GUI.window.add(new GSpacer(5));
                GUI.window.add(new GText("Current Wattage for one light bulb:", Style.defaultFont));
                GUI.window.add(new GSpacer(5));
                GUI.window.add((GUIComponent) currentWatts);

                GUI.window.add(new GSpacer(5));
                GUI.window.add(new GText("New Wattage Value For One Light Bulb:", Style.defaultFont));
                GUI.window.add(new GSpacer(5));
                GUI.window.add((GUIComponent) newWatts);

                GUI.window.add(new GSpacer(20));
                GUI.window.add(new GText("Light Bulb Price", Style.defaultFont));
                GUI.window.add(new GSpacer(5));
                GUI.window.add((GUIComponent) pricePerUnit);



                GUI.window.add(new GSpacer(5));
                GUI.window.add(new GText("Initial Costs:", Style.defaultFont));
                GUI.window.add(new GSpacer(5));
                GUI.window.add((GUIComponent) lightBulbPrice);
                GUI.window.add(new GSpacer(5));

                //Program what the button does when it is clicked.
                GUI.window.add(new GButton(40, "Save Changes", Style.defaultFont, 8) {
                    @Override
                    public void clickAction() {
                        //TODO: Write checks and setters for all of the textboxes.
                        boolean good = true;


                        //Example:
                        try {
                            initialCost = Double.parseDouble(lightBulbPrice.getText());
                        } catch (NumberFormatException e) {
                            lightBulbPrice.failed("This value must be a number.");
                            good = false;
                        }

                        if (good) {
                            GUI.window.gotoPage(getSummaryPage());
                        }
                    }
                });

                GUI.window.add(new GSpacer(40));
                GUI.window.add(new GText("Other Settings"));
                GUI.window.add(new GSpacer(25));

                GUI.window.add(new GButton(40, Style.redButtonColor, Style.redHoverColor, "Delete Project", Style.defaultFont) {
                    @Override
                    public void clickAction() {
                        UserManager.getLoadedUser().getMyProjects().remove(LightProject.this);
                        GUI.window.gotoPage("Home");
                    }
                });
                GUI.window.add(new GSpacer(40));
                GUI.window.showMenu();
            }
        };
    }
}
