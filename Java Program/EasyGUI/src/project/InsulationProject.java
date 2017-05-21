package project;

import gui.*;
import user.UserManager;

import java.io.Serializable;

/**
 * @author Ryan Hansen
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

	public int getRValue() {
		return this.rValue;
	}

    @Override
    public GUIPage getSummaryPage() {
        return new GUIPage(name) {
            @Override
            public void build() {
                GUI.window.add(new GSpacer(40));
                GUI.window.add(new GSpacer(25));
                GUI.window.add(new GText(name));
                GUI.window.add(new GSpacer(25));

                GDivider buttons = new GDivider(240, 1);
                GDivider innerDiv = new GDivider(240, 2);
                innerDiv.add(new GButton(40, "Edit Project", Style.defaultFont, 8) {
                    @Override
                    public void clickAction() {
                        GUI.window.gotoPage(getEditPage());
                    }
                });
                innerDiv.add(new GButton(40, "Completed", Style.defaultFont, 8) {
                    @Override
                    public void clickAction() { GUI.window.gotoPage("Home");
                    }
                });
                buttons.add(innerDiv);
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

                GTextBox currentR = new GTextBox(40, "", "R-Value determines how well the insulation resists heat flow.");
                GTextBox newR = new GTextBox(40, "", "Recommended R-Value depends on where you live. If you live in a hot area you would want a lower R-Value around 2-3. If you live in a colder area you will want a higher R-Value around 5-6.");
                GTextBox areaToBeUpgraded = new GTextBox(40, "", "The surface area, in square feet, of the room where insulation is being upgraded.");
                GTextBox heatingDegreeDays = new GTextBox(40, "", "HDD ");

                GDropdown gasType = new GDropdown(new String[] {"Natural Gas", "Fuel Oil", "Propane", "Electricity"});

                GTextBox pricePerUnit = new GTextBox(40, "", "For natural gas and fuel oil used dollars per therm, for propane use dollars per gallon and for electricity enter dollars per KWH.");

                GTextBox furnaceEfficency = new GTextBox(40, "");

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

                GUI.window.add(new GButton(40, "Submit", Style.defaultFont, 8) {
                    @Override
                    public void clickAction() {
                        //TODO: Write checks and setters for all of the textboxes.
                        GUI.window.gotoPage(getSummaryPage());
                    }
                });
                GUI.window.add(new GSpacer(40));
                GUI.window.showMenu();
            }
        };
    }
}
