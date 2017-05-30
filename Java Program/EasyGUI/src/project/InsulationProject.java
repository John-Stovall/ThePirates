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
public class InsulationProject extends Project implements Serializable {

	/**
	 * default version ID for serialization
	 */
	private static final long serialVersionUID = -1950641840941379037L;

	// Class specific variable used for determining the insulating properties
	// of the materials being used.
	private int rValue;
	
	// constructor sets the water usage to -1.0 because there is no way for a insulation project
	// to have an impact on water consumption. This will be beneficial for our 'Math' class.
	public InsulationProject(final String name) {
	    this.name = name;
		this.waterUsage = -1.0;
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
                if (UserManager.getLoadedUser().getMyProjects().contains(InsulationProject.this)) {

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
                    buttons.add(innerDiv1);
                    if (UserManager.getLoadedUser().getMyProjects().size() > 1) {
                        innerDiv3.add(new GButton(40, "Compare To...", Style.defaultFont, 8) {
                            @Override
                            public void clickAction() {
                                GUI.window.gotoPage(new ProjectChooser(InsulationProject.this));
                            }
                        });
                        innerDiv3.add(new GSpacer(10));
                        buttons.add(innerDiv3);
                    }
                    innerDiv2.add(new GButton(40, "Completed", Style.defaultFont, 8) {
                        @Override
                        public void clickAction() {
                            UserManager.getLoadedUser().projectComplete(InsulationProject.this);
                            GUI.window.gotoPage("Home");
                        }
                    });
                    innerDiv2.add(new GSpacer(10));
                    buttons.add(innerDiv2);


                } else {
                    GUI.window.add(new GButton(40, "Resume Project", Style.defaultFont, 8) {
                        @Override
                        public void clickAction() {
                            UserManager.getLoadedUser().projectResume(InsulationProject.this);
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
                GTextBox currentR = new GTextBox(40, "", "R-Value determines how well the insulation resists heat flow.");
                GTextBox newR = new GTextBox(40, "", "Recommended R-Value depends on where you live. If you live in a hot area you would want a lower R-Value around 2-3. If you live in a colder area you will want a higher R-Value around 5-6.");
                GTextBox areaToBeUpgraded = new GTextBox(40, "", "The surface area, in square feet, of the room where insulation is being upgraded.");
                GTextBox heatingDegreeDays = new GTextBox(40, "", "Heating degree days depends on how much often you use your heater.");
                GTextBox pricePerUnit = new GTextBox(40, "", "For natural gas and fuel oil use dollars per therm, for propane use dollars per gallon and for electricity enter dollars per KWH.");
                GTextBox furnaceEfficency = new GTextBox(40, "", "How efficiently your heater turns energy into heat. On average, use 100 for electric heaters and 80 for others.");
                GTextBox insulationPrice = new GTextBox(40, initialCost + "", "The price of the new insulation and any costs of installation.");
                GDropdown gasType = new GDropdown(new String[] {"Natural Gas", "Fuel Oil", "Propane", "Electricity"});

                GUI.window.add(new GText("Area to be upgraded:", Style.defaultFont));
                GUI.window.add(new GSpacer(5));
                GUI.window.add((GUIComponent) areaToBeUpgraded);

                GUI.window.add(new GSpacer(5));
                GUI.window.add(new GText("Heating Degree Days:", Style.defaultFont));
                GUI.window.add(new GSpacer(5));
                GUI.window.add((GUIComponent) heatingDegreeDays);

                GUI.window.add(new GSpacer(5));
                GUI.window.add(new GSpacer(5));
                GUI.window.add(new GText("Current Insulation R-Value:", Style.defaultFont));
                GUI.window.add(new GSpacer(5));
                GUI.window.add((GUIComponent) currentR);

                GUI.window.add(new GSpacer(5));
                GUI.window.add(new GText("New Insulation R-Value:", Style.defaultFont));
                GUI.window.add(new GSpacer(5));
                GUI.window.add((GUIComponent) newR);

                GUI.window.add(new GSpacer(5));
                GUI.window.add(new GText("Fuel Type:", Style.defaultFont));
                GUI.window.add(new GSpacer(5));
                GUI.window.add(gasType);

                GUI.window.add(new GSpacer(20));
                GUI.window.add(new GText("Fuel Price", Style.defaultFont));
                GUI.window.add(new GSpacer(5));
                GUI.window.add((GUIComponent) pricePerUnit);

                GUI.window.add(new GSpacer(5));
                GUI.window.add(new GText("Furnace Efficiency Percentage:", Style.defaultFont));
                GUI.window.add(new GSpacer(5));
                GUI.window.add((GUIComponent) furnaceEfficency);

                GUI.window.add(new GSpacer(5));
                GUI.window.add(new GText("Initial Costs:", Style.defaultFont));
                GUI.window.add(new GSpacer(5));
                GUI.window.add((GUIComponent) insulationPrice);
                GUI.window.add(new GSpacer(5));

                //Program what the button does when it is clicked.
                GUI.window.add(new GButton(40, "Save Changes", Style.defaultFont, 8) {
                    @Override
                    public void clickAction() {
                        //TODO: Write checks and setters for all of the textboxes.
                        boolean good = true;


                        //Example:
                        try {
                            initialCost = Double.parseDouble(insulationPrice.getText());
                            if (initialCost < 0) {
                                good = false;
                                insulationPrice.failed("Initial cost cannot be negative.");
                            }
                        } catch (NumberFormatException e) {
                            insulationPrice.failed("Initial cost must be a number.");
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
                        UserManager.getLoadedUser().getMyProjects().remove(InsulationProject.this);
                        GUI.window.gotoPage("Home");
                    }
                });
                GUI.window.add(new GSpacer(40));
                GUI.window.showMenu();
            }
        };
    }
}
