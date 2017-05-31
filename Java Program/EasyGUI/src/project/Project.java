package project;

import gui.GUIPage;

import java.io.Serializable;

/**
 * @author Ryan Hansen
 * @author Robert Cordingly
 *
 * Abstract class which declares the getters and setters for the
 * project classes.
 */
public abstract class Project  implements Serializable {

    /** The name of the project. */
	protected String name;

	/** The initial cost of the project. */
	double initialCost;

    /**
     * This method gets the summary page that is associated with
     * this project. The summary page draws basic information
     * like monthly savings and a graph.
     *
     * @return The GUIPage for this project.
     * @author Robert
     */
	public abstract GUIPage getSummaryPage();

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
