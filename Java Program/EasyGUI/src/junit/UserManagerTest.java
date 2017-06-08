package junit;

import java.util.ArrayList;

import org.junit.BeforeClass;
import org.junit.Test;
import user.User;
import user.UserManager;

/**
 * This class is used to test the UserManager.
 *
 * @author Ryan Hansen
 */
public class UserManagerTest {

	
	
	@BeforeClass
	public static void beforeClass() {

		new User("1st User Project Name", "test@uw.edu");
		new User("2nd User Project Name", "tester@uw.edu");
		new User("3rd User Project Name", "tested@uw.edu");
		
		System.out.println("Before Class:");
		
		for (int i = 0; i < UserManager.getUsers().size(); i++) {
			System.out.println(UserManager.getUsers().get(i).getName());
		}
		
		UserManager.save();
	}
	
	
	
	@Test
	public void test() {
		UserManager.getUsers().clear();

		UserManager.load();
		
		System.out.println("After Loading:");
		
		for (int i = 0; i < UserManager.getUsers().size(); i++) {
			System.out.println(UserManager.getUsers().get(i).getName());
		}
	}

	
	@Test
	public void test2() {
		ArrayList<User> testUsers = UserManager.getUsers();
	}
	
	@Test
	public void test3() {
		User testUser = UserManager.getLoadedUser();
	}
}
