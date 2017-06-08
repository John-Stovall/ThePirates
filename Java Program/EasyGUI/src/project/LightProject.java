package project;

import control.General;
import gui.*;
import pages.ProjectChooser;
import user.UserManager;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Light Project class.
 * Used for creating a new Light project.
 *
 * @author Rand
 */
public class LightProject extends Project implements Serializable {

    /**
     * default version ID for serialization
     */
    private static final long serialVersionUID = -1950641840941379038L;
    private int numberOflightBulbValue = 1 ;
    private double currentWattsValue = 60,
            lightBulbUsageValue = 12,
            newWattsValue = 9,
            pricePerUnitValue = 10.00;

    // Class specific variable used for determining the Light properties
    // of the materials being used.

    // constructor sets the water usage to -1.0 because there is no way for a insulation project
    // to have an impact on water consumption. This will be beneficial for our 'Math' class.
    public LightProject(final String name) {
        this.name = name;
        this.type = "Lights";
    }

    @Override
    public double getMonthlySavings() {
        //TODO: Program this to properly calculate the monthly savings.

        double oldSystemCost, newSystemCost;

        //watts calculaction
        //0.093 is the state of Washington KiloWatt for Hour operation
        oldSystemCost = numberOflightBulbValue * ((currentWattsValue/1000)*lightBulbUsageValue * 30 * 0.093);
        newSystemCost = numberOflightBulbValue * ((newWattsValue/1000)*lightBulbUsageValue * 30 * 0.093);

        //per month savings
        double savings;

        savings = oldSystemCost - newSystemCost;
        return savings;
    }

    @Override
    public double getInitialCost() {
        return pricePerUnitValue * numberOflightBulbValue;
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
                final GTextBox currentWatts = new GTextBox(40, currentWattsValue + "",  "Current one light bulb wattage.");
                final GTextBox newWatts = new GTextBox(40, newWattsValue + "",  "Recommended Wattage would be less than the current wattage to save on energy.");
                GTextBox numberOfLightBulb = new GTextBox(40, numberOflightBulbValue + "",  "Number of light bulbs that desired to change.");
                GTextBox lightBulbUsage = new GTextBox(40, lightBulbUsageValue + "",  "Hours in which the Light Bulbs are ON per day.");
                GTextBox pricePerUnit = new GTextBox(40, pricePerUnitValue + "",  "Dollar amount for one light bulb.");
                GTextBox lightBulbPrice = new GTextBox(40,  pricePerUnitValue + "",  "The price of the new light bulbs.");

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


                //Program what the button does when it is clicked.
                GUI.window.add(new GButton(40, "Save Changes", Style.defaultFont, 8) {
                    @Override
                    public void clickAction() {
                        //TODO: Write checks and setters for all of the textboxes.
                        boolean good = true;
                        //current watt values:
                        try {
                            currentWattsValue = Double.parseDouble(currentWatts.getText());

                            if (currentWattsValue < 0) {
                                good = false;
                                currentWatts.failed("Watts cannot be negative.");
                            } else if (currentWattsValue > 500) {
                                good = false;
                                currentWatts.failed("Watts cannot be exceed 500.");
                            }

                        } catch (NumberFormatException e) {
                            currentWatts.failed("This value must be a number.");
                            good = false;
                        }

                        if (good) {
                            UserManager.save();
                            GUI.window.gotoPage(getSummaryPage());
                            GUI.getPopUp().destroy();
                        }


                        try {
                            newWattsValue = Double.parseDouble(newWatts.getText());

                            if (newWattsValue < 0) {
                                good = false;
                                newWatts.failed("Watts cannot be negative.");
                            } else if (newWattsValue > 500) {
                                good = false;
                                newWatts.failed("Watts cannot be exceed 500.");
                            }

                        } catch (NumberFormatException e) {
                            newWatts.failed("This value must be a number.");
                            good = false;
                        }

                        if (good) {
                            UserManager.save();
                            GUI.window.gotoPage(getSummaryPage());
                            GUI.getPopUp().destroy();
                        }

                        try {
                            numberOflightBulbValue = Integer.parseInt(numberOfLightBulb.getText());

                            if (numberOflightBulbValue < 0) {
                                good = false;
                                numberOfLightBulb.failed("Number of light bulb cannot be negative.");
                            }
                        } catch (NumberFormatException e) {
                            numberOfLightBulb.failed("This value must be a number.");
                            good = false;
                        }

                        if (good) {
                            UserManager.save();
                            GUI.window.gotoPage(getSummaryPage());
                            GUI.getPopUp().destroy();
                        }


                        try {
                            pricePerUnitValue = Double.parseDouble(pricePerUnit.getText());
                            if (pricePerUnitValue < 0) {
                                good = false;
                                pricePerUnit.failed("Price cannot be negative.");
                            }
                        } catch (NumberFormatException e) {
                            pricePerUnit.failed("This value must be a number.");
                            good = false;
                        }

                        if (good) {
                            UserManager.save();
                            GUI.window.gotoPage(getSummaryPage());
                            GUI.getPopUp().destroy();
                        }



                        try {
                            lightBulbUsageValue = Double.parseDouble(lightBulbUsage.getText());
                            if (lightBulbUsageValue < 0) {
                                good = false;
                                lightBulbUsage.failed("Hours cannot be negative.");
                            }
                            else if (lightBulbUsageValue > 24) {
                                good = false;
                                newWatts.failed("Hours per days cannot be exceed 24.");
                            }


                        } catch (NumberFormatException e) {
                            lightBulbUsage.failed("This value must be a number.");
                            good = false;
                        }

                        if (good) {
                            UserManager.save();
                            GUI.window.gotoPage(getSummaryPage());
                            GUI.getPopUp().destroy();
                        }

                    }
                });

                GUI.window.add(new GSpacer(40));
                GUI.window.add(new GText("Other Settings"));
                GUI.window.add(new GSpacer(25));
                GUI.window.add(new GButton(40,"Rename Project", Style.defaultFont) {
                    @Override
                    public void clickAction() {

                        ArrayList<GUIComponent> parts = new ArrayList<>();
                        parts.add(new GText("Rename " + LightProject.this.getName()));
                        parts.add(new GSpacer(20));
                        GTextBox name = new GTextBox(40, LightProject.this.getName());
                        parts.add(name);
                        parts.add(new GSpacer(10));
                        GDivider div = new GDivider(1, 2);
                        div.add(new GButton(40,"Rename", Style.defaultFont, 16) {
                            @Override
                            public void clickAction() {
                                if (name.getText().length() < 1) {
                                    name.failed("A project name is required.");
                                } else if (name.getText().length() > 15) {
                                    name.failed("Project names can be at most 15 characters.");
                                } else if (UserManager.getLoadedUser().getMyProjects().size() >= 10) {
                                    name.failed("Sorry, you can have at most 10 projects. Either delete a project or complete one to make more room.");
                                } else if (!General.isProjectNameFree(name.getText().trim())) {
                                    name.failed("That project name is already taken.");
                                } else {
                                    setName(name.getText().trim());
                                    UserManager.save();
                                    GUI.window.refresh();
                                    GUI.getPopUp().destroy();
                                    GUI.showPopUp(getEditPage());
                                }
                            }
                        });
                        div.add(new GButton(40, "Cancel", Style.defaultFont, 16) {
                            @Override
                            public void clickAction() {
                                GUI.getPopUp().destroy();
                                GUI.showPopUp(getEditPage());
                            }
                        });
                        parts.add(div);
                        GUI.getPopUp().destroy();
                        GUI.showPopUp(new GPopUp(parts));
                    }
                });
                GUI.window.add(new GSpacer(25));
                GUI.window.add(new GButton(40, Style.redButtonColor, Style.redHoverColor, "Delete Project", Style.defaultFont) {
                    @Override
                    public void clickAction() {

                        ArrayList<GUIComponent> parts = new ArrayList<>();
                        parts.add(new GText("Warning!"));
                        parts.add(new GSpacer(20));
                        parts.add(new GText("Are you sure you want to delete this project? You will not be able to recover it.", Style.defaultFont));
                        parts.add(new GSpacer(20));
                        GDivider div = new GDivider(1, 2);
                        div.add(new GButton(40, Style.redButtonColor, Style.redHoverColor, "Delete", Style.defaultFont, 16) {
                            @Override
                            public void clickAction() {
                                UserManager.getLoadedUser().getMyProjects().remove(LightProject.this);
                                UserManager.save();
                                GUI.getPopUp().destroy();
                                GUI.window.gotoPage("Home");
                            }
                        });
                        div.add(new GButton(40, "Cancel", Style.defaultFont, 16) {
                            @Override
                            public void clickAction() {
                                GUI.getPopUp().destroy();
                                GUI.showPopUp(getEditPage());
                            }
                        });
                        parts.add(div);
                        GUI.getPopUp().destroy();
                        GUI.showPopUp(new GPopUp(parts));
                    }
                });
            }
        };
    }
}
