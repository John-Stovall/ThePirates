package user;

import project.Project;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * @author Ryan Hansen
 *
 * Stores basic information for each user.
 */
public class User implements Serializable{

	/**
	 * Generated serial ID
	 */
	private static final long serialVersionUID = 1074418663306939530L;

    /**
     * The projects that this user has.
     */
	private final ArrayList<Project> myProjects = new ArrayList<>();

    /**
     * The completed projects this user has.
     */
	private final ArrayList<Project> completedProject = new ArrayList<>();

    /**
     * The name of the user.
     */
	private String name;

    /**
     * The email of the user.
     */
	private String email;

    /**
     * Creates a user and adds them to the list of users.
     *
     * @param theName The name for the user to have.
     * @param theEmail The email for the user to have.
     * @author Ryan Hansen
     */
	public User(final String theName, final String theEmail) {
        this.name = theName;
        this.email = theEmail;
        UserManager.getUsers().add(this);
    }

    /**
     * This method moves a project from the current list to the completed list.
     *
     * @param p The project to move.
     * @author Robert
     */
    public void projectComplete(Project p) {
	    if (myProjects.contains(p)) {
            completedProject.add(p);
            myProjects.remove(p);
        } else {
	        throw new IllegalArgumentException("Unknown project.");
        }
    }

    /**
     * This method moves a project from the completed list to the current list.
     *
     * @param p The project to move.
     * @author Robert
     */
    public void projectResume(Project p) {
        if (completedProject.contains(p)) {
            myProjects.add(p);
            completedProject.remove(p);
        } else {
            throw new IllegalArgumentException("Unknown project.");
        }
    }

    /**
     * Returns the name of the user.
     *
     * return The name of the user.
     * @author Ryan Hansen
     */
	public String getName() {
        return name;
    }

    /**
     * Sets the name of the user.
     *
     * @param name The name to set to.
     * @author Ryan Hansen
     */
	public void setName(String name) {
        this.name = name;
    }

    /**
     * This method returns the email of the user.
     *
     * @return the email of the user.
     * @author Ryan Hansen
     */
	public String getEmail() {
        return email;
    }

    /**
     * Sets the email of the user.
     *
     * @param email The email to set to.
     * @author Ryan Hansen
     */
	public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Gets the list of projects that this user uses.
     *
     * @return The list of projects.
     * @author Ryan Hansen
     */
    public ArrayList<Project> getMyProjects() {
        return myProjects;
    }

    /**
     * Gets the list of completed projects that this user uses.
     *
     * @return The list of completed projects.
     * @author Ryan Hansen
     */
    public ArrayList<Project> getCompletedProject() {
        return completedProject;
    }
}
