package user;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.BeforeClass;
import org.junit.Test;

public class UserManagerTest {

	
	
	@BeforeClass
	public static void beforeClass() {
		//UserManager.createNewUser("1st User Project Name", "test@uw.edu");
		//UserManager.createNewUser("2nd User Project Name", "tester@uw.edu");
		//UserManager.createNewUser("3rd User Project Name", "tested@uw.edu");
		
		System.out.println("Before Class:");
		
		for (int i = 0; i < UserManager.allUsers.size(); i++) {
			System.out.println(UserManager.allUsers.get(i).getName());			
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
