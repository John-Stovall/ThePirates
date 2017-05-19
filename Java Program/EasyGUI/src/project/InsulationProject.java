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
                GUI.window.add(menu);
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

                GTextBox currentR = new GTextBox(32, "");
                GTextBox newR = new GTextBox(32, "");
                GTextBox areaToBeUpgraded = new GTextBox(32, "");
                GTextBox heatingDegreeDays = new GTextBox(32, "");

                GDropdown gasType = new GDropdown(new String[] {"Natural Gas", "Fuel Oil", "Propane", "Electricty"});

                GTextBox furnaceEfficency = new GTextBox(32, "");

                GUI.window.add(new GText("Area to be upgraded:", Style.defaultFont));
                GUI.window.add(new GSpacer(5));
                GUI.window.add(areaToBeUpgraded);

                GUI.window.add(new GSpacer(5));
                GUI.window.add(new GText("Heating Degree Days:", Style.defaultFont));
                GUI.window.add(new GSpacer(5));
                GUI.window.add(heatingDegreeDays);

                GUI.window.add(new GSpacer(5));
                GUI.window.add(new GSpacer(5));
                GUI.window.add(new GText("Current Insulation R-Value:", Style.defaultFont));
                GUI.window.add(new GSpacer(5));
                GUI.window.add(currentR);

                GUI.window.add(new GSpacer(5));
                GUI.window.add(new GText("New Insulation R-Value:", Style.defaultFont));
                GUI.window.add(new GSpacer(5));
                GUI.window.add(newR);

                GUI.window.add(new GSpacer(5));
                GUI.window.add(new GText("Gas Type:", Style.defaultFont));
                GUI.window.add(new GSpacer(5));
                GUI.window.add(gasType);

                GUI.window.add(new GSpacer(5));
                GUI.window.add(new GText("Furnace Efficiency Percentage:", Style.defaultFont));
                GUI.window.add(new GSpacer(5));
                GUI.window.add(furnaceEfficency);

                GUI.window.add(new GSpacer(5));

                GUI.window.add(new GButton(40, "Submit", Style.defaultFont, 8) {
                    @Override
                    public void clickAction() {
                        //TODO: Write checks and setters for all of the textboxes.
                        GUI.window.gotoPage(getSummaryPage());
                    }
                });
                GUI.window.add(new GSpacer(40));
                GUI.window.add(menu);
            }
        };
    }
}
