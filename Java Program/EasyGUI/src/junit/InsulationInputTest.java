package junit;

import org.junit.Assert;
import org.junit.Test;
import project.InsulationProject;

/**
 * This class is used to test the Insulation Project.
 *
 * @author Robert
 */
public class InsulationInputTest {

    @Test
    public void testValues() {
        InsulationProject project = new InsulationProject("Test Project");
        String currentR = "10";
        String newR = "20";
        String Area = "1000";
        String HDD = "5000";
        String Initial = "0";
        String PPU = "1.5";
        String eff = "80";
        Assert.assertTrue(project.testInsulation(currentR, newR, Area, HDD, PPU, eff, Initial));
        Assert.assertEquals(112.5 / 12.0, project.getMonthlySavings(), 0.1);
    }


    @Test
    public void testCurrR() {
        InsulationProject project = new InsulationProject("Test Project");
        String currentR = "10";
        String newR = "20";
        String Area = "1000";
        String HDD = "5000";
        String Initial = "0";
        String PPU = "1.5";
        String eff = "80";
        Assert.assertTrue(project.testInsulation(currentR, newR, Area, HDD, PPU, eff, Initial));

        project = new InsulationProject("Test Project");
        currentR = "a";
        newR = "20";
        Area = "1000";
        HDD = "5000";
        Initial = "0";
        PPU = "1.5";
        eff = "80";
        Assert.assertFalse(project.testInsulation(currentR, newR, Area, HDD, PPU, eff, Initial));

        project = new InsulationProject("Test Project");
        currentR = "-5";
        newR = "20";
        Area = "1000";
        HDD = "5000";
        Initial = "0";
        PPU = "1.5";
        eff = "80";
        Assert.assertFalse(project.testInsulation(currentR, newR, Area, HDD, PPU, eff, Initial));

        project = new InsulationProject("Test Project");
        currentR = "500";
        newR = "20";
        Area = "1000";
        HDD = "5000";
        Initial = "0";
        PPU = "1.5";
        eff = "80";
        Assert.assertFalse(project.testInsulation(currentR, newR, Area, HDD, PPU, eff, Initial));
    }

    @Test
    public void testNewR() {
        InsulationProject project = new InsulationProject("Test Project");
        String currentR = "10";
        String newR = "20";
        String Area = "1000";
        String HDD = "5000";
        String Initial = "0";
        String PPU = "1.5";
        String eff = "80";
        Assert.assertTrue(project.testInsulation(currentR, newR, Area, HDD, PPU, eff, Initial));

        project = new InsulationProject("Test Project");
        currentR = "10";
        newR = "a";
        Area = "1000";
        HDD = "5000";
        Initial = "0";
        PPU = "1.5";
        eff = "80";
        Assert.assertFalse(project.testInsulation(currentR, newR, Area, HDD, PPU, eff, Initial));

        project = new InsulationProject("Test Project");
        currentR = "-10";
        newR = "-5";
        Area = "1000";
        HDD = "5000";
        Initial = "0";
        PPU = "1.5";
        eff = "80";
        Assert.assertFalse(project.testInsulation(currentR, newR, Area, HDD, PPU, eff, Initial));

        project = new InsulationProject("Test Project");
        currentR = "10";
        newR = "500";
        Area = "1000";
        HDD = "5000";
        Initial = "0";
        PPU = "1.5";
        eff = "80";
        Assert.assertFalse(project.testInsulation(currentR, newR, Area, HDD, PPU, eff, Initial));
    }

    @Test
    public void testArea() {
        InsulationProject project = new InsulationProject("Test Project");
        String currentR = "10";
        String newR = "20";
        String Area = "1000";
        String HDD = "5000";
        String Initial = "0";
        String PPU = "1.5";
        String eff = "80";
        Assert.assertTrue(project.testInsulation(currentR, newR, Area, HDD, PPU, eff, Initial));

        project = new InsulationProject("Test Project");
        currentR = "10";
        newR = "20";
        Area = "-5";
        HDD = "5000";
        Initial = "0";
        PPU = "1.5";
        eff = "80";
        Assert.assertFalse(project.testInsulation(currentR, newR, Area, HDD, PPU, eff, Initial));

        project = new InsulationProject("Test Project");
        currentR = "10";
        newR = "20";
        Area = "cs";
        HDD = "5000";
        Initial = "0";
        PPU = "1.5";
        eff = "80";
        Assert.assertFalse(project.testInsulation(currentR, newR, Area, HDD, PPU, eff, Initial));

        project = new InsulationProject("Test Project");
        currentR = "10";
        newR = "20";
        Area = "10000000";
        HDD = "5000";
        Initial = "0";
        PPU = "1.5";
        eff = "80";
        Assert.assertFalse(project.testInsulation(currentR, newR, Area, HDD, PPU, eff, Initial));
    }

    @Test
    public void testHDD() {
        InsulationProject project = new InsulationProject("Test Project");
        String currentR = "10";
        String newR = "20";
        String Area = "1000";
        String HDD = "5000";
        String Initial = "0";
        String PPU = "1.5";
        String eff = "80";
        Assert.assertTrue(project.testInsulation(currentR, newR, Area, HDD, PPU, eff, Initial));

        project = new InsulationProject("Test Project");
        currentR = "10";
        newR = "20";
        Area = "1000";
        HDD = "9999999";
        Initial = "0";
        PPU = "1.5";
        eff = "80";
        Assert.assertFalse(project.testInsulation(currentR, newR, Area, HDD, PPU, eff, Initial));

        project = new InsulationProject("Test Project");
        currentR = "10";
        newR = "20";
        Area = "1000";
        HDD = "gfds";
        Initial = "0";
        PPU = "1.5";
        eff = "80";
        Assert.assertFalse(project.testInsulation(currentR, newR, Area, HDD, PPU, eff, Initial));

        project = new InsulationProject("Test Project");
        currentR = "10";
        newR = "20";
        Area = "1000";
        HDD = "-20";
        Initial = "0";
        PPU = "1.5";
        eff = "80";
        Assert.assertFalse(project.testInsulation(currentR, newR, Area, HDD, PPU, eff, Initial));
    }

    @Test
    public void testPPU() {
        InsulationProject project = new InsulationProject("Test Project");
        String currentR = "10";
        String newR = "20";
        String Area = "1000";
        String HDD = "5000";
        String Initial = "0";
        String PPU = "1.5";
        String eff = "80";
        Assert.assertTrue(project.testInsulation(currentR, newR, Area, HDD, PPU, eff, Initial));

        project = new InsulationProject("Test Project");
        currentR = "10";
        newR = "20";
        Area = "1000";
        HDD = "5000";
        Initial = "0";
        PPU = "asdg";
        eff = "80";
        Assert.assertFalse(project.testInsulation(currentR, newR, Area, HDD, PPU, eff, Initial));

        project = new InsulationProject("Test Project");
        currentR = "10";
        newR = "20";
        Area = "1000";
        HDD = "5000";
        Initial = "0";
        PPU = "54783275849321";
        eff = "80";
        Assert.assertFalse(project.testInsulation(currentR, newR, Area, HDD, PPU, eff, Initial));

        project = new InsulationProject("Test Project");
        currentR = "10";
        newR = "20";
        Area = "1000";
        HDD = "5000";
        Initial = "0";
        PPU = "-20";
        eff = "80";
        Assert.assertFalse(project.testInsulation(currentR, newR, Area, HDD, PPU, eff, Initial));
    }

    @Test
    public void testEff() {
        InsulationProject project = new InsulationProject("Test Project");
        String currentR = "10";
        String newR = "20";
        String Area = "1000";
        String HDD = "5000";
        String Initial = "0";
        String PPU = "1.5";
        String eff = "80";
        Assert.assertTrue(project.testInsulation(currentR, newR, Area, HDD, PPU, eff, Initial));

        project = new InsulationProject("Test Project");
        currentR = "10";
        newR = "20";
        Area = "1000";
        HDD = "5000";
        Initial = "0";
        PPU = "1.5";
        eff = "200";
        Assert.assertFalse(project.testInsulation(currentR, newR, Area, HDD, PPU, eff, Initial));

        project = new InsulationProject("Test Project");
        currentR = "10";
        newR = "20";
        Area = "1000";
        HDD = "5000";
        Initial = "0";
        PPU = "1.5";
        eff = "fdsf";
        Assert.assertFalse(project.testInsulation(currentR, newR, Area, HDD, PPU, eff, Initial));

        project = new InsulationProject("Test Project");
        currentR = "10";
        newR = "20";
        Area = "1000";
        HDD = "5000";
        Initial = "0";
        PPU = "1.5";
        eff = "-20";
        Assert.assertFalse(project.testInsulation(currentR, newR, Area, HDD, PPU, eff, Initial));
    }
}
