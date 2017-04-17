package gui;

import org.junit.Assert;
import org.junit.Test;

import main.Main;

public class JTest {

	//Naming Tests
	@Test
	public void test1() {
		String test1 = "bob";
		Assert.assertTrue(Main.testName(test1));
	}
	@Test
	public void test2() {
		String test2 = "boby";
		Assert.assertTrue(Main.testName(test2));
	}
	@Test
	public void test3() {
		String test3 = "Big John";
		Assert.assertTrue(Main.testName(test3));
	}
	@Test
	public void test4() {
		String test4 = "  bob";
		Assert.assertTrue(Main.testName(test4));
	}

	//emailTests
	@Test
	public void test5() {
		String test5 = "bob@gmail.com";
		Assert.assertTrue(Main.testEmail(test5));
	}
	@Test
	public void test6() {
		String test6 = "boby.uwt.edu";
		Assert.assertTrue(Main.testEmail(test6));
	}
	@Test
	public void test7() {
		String test7 = "Big John@yahoo.com";
		Assert.assertTrue(Main.testEmail(test7));
	}
	@Test
	public void test8() {
		String test8 = "  bob.aol.com";
		Assert.assertTrue(Main.testEmail(test8));
	}

}
