package junit;

import control.General;
import org.junit.Assert;
import org.junit.Test;

/**
 * This class is used for testing the methods in the General class.
 *
 * @author Reagan
 */
public class GeneralTest {

    //Naming Tests

    @Test
    public void test0() {
        String test0 = "The quick brown fox jumped over the lazy dog.";
        Assert.assertFalse(General.testName(test0));
    }

    @Test
    public void test1() {
        String test1 = "bob";
        Assert.assertFalse(General.testName(test1));
    }

    @Test
    public void test2() {
        String test2 = "boby";
        Assert.assertTrue(General.testName(test2));
    }

    @Test
    public void test3() {
        String test3 = "Big John";
        Assert.assertTrue(General.testName(test3));
    }

    @Test
    public void test4() {
        String test4 = "  bob  ";
        Assert.assertFalse(General.testName(test4.trim()));
    }

    //emailTests
    @Test
    public void test5() {
        String test5 = "bob@gmail.com";
        Assert.assertTrue(General.testEmail(test5));
    }

    @Test
    public void test6() {
        String test6 = "boby.uwt.edu";
        Assert.assertTrue(General.testEmail(test6));
    }

    @Test
    public void test7() {
        String test7 = "Big John@yahoo.com";
        Assert.assertTrue(General.testEmail(test7));
    }

    @Test
    public void test8() {
        String test8 = "  bob.aol.com";
        Assert.assertFalse(General.testEmail(test8));
    }

}
