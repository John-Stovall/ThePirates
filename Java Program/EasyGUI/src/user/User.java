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
	 * generated serial ID
	 */
	private static final long serialVersionUID = 1074418663306939530L;

	private final ArrayList<Project> myProjects = new ArrayList<>();

	private String name;
	private String email;


	public User(final String theName, final String theEmail) {
        this.name = theName;
        this.email = theEmail;
        UserManager.getUsers().add(this);
    }

	public String getName() {
        return name;
    }

	public void setName(String name) {
        this.name = name;
    }

	public String getEmail() {
        return email;
    }

	public void setEmail(String email) {
        this.email = email;
    }

    public ArrayList<Project> getMyProjects() {
        return myProjects;
    }
}
