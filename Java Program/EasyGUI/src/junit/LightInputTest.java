package junit;

import org.junit.Assert;
import org.junit.Test;
import project.LightProject;

/**
 * This class is used to test the Light Project.
 *
 * @author Rand
 */
public class LightInputTest {

    @Test
    public void testValues() {
        LightProject project = new LightProject("Test Project");
        String numberOfLights = "1";
        String currentWatts = "60.0";
        String newWatts = "9.0";
        String PPU = "10.0";
        String use = "12.0";
        Assert.assertTrue(project.testLights(currentWatts, newWatts, numberOfLights, PPU, use));
        Assert.assertEquals(2.0, Math.round(project.getMonthlySavings()), 0.1);
    }

    @Test
    public void testNumberOfLights() {
        LightProject project = new LightProject("Test Project");
        String numberOfLights = "1";
        String currentWatts = "60.0";
        String newWatts = "9.0";
        String PPU = "10.0";
        String use = "12.0";
        Assert.assertTrue(project.testLights(currentWatts, newWatts, numberOfLights, PPU, use));

        project = new LightProject("Test Project");
        numberOfLights = "-10";
        currentWatts = "60.0";
        newWatts = "9.0";
        PPU = "10.0";
        use = "12.0";
        Assert.assertFalse(project.testLights(currentWatts, newWatts, numberOfLights, PPU, use));

        project = new LightProject("Test Project");
        numberOfLights = "0";
        currentWatts = "60.0";
        newWatts = "9.0";
        PPU = "10.0";
        use = "12.0";
        Assert.assertFalse(project.testLights(currentWatts, newWatts, numberOfLights, PPU, use));

        project = new LightProject("Test Project");
        numberOfLights = "sdf";
        currentWatts = "60.0";
        newWatts = "9.0";
        PPU = "10.0";
        use = "12.0";
        Assert.assertFalse(project.testLights(currentWatts, newWatts, numberOfLights, PPU, use));
    }

    @Test
    public void testCurrentWattage() {
        LightProject project = new LightProject("Test Project");
        String numberOfLights = "1";
        String currentWatts = "60.0";
        String newWatts = "9.0";
        String PPU = "10.0";
        String use = "12.0";
        Assert.assertTrue(project.testLights(currentWatts, newWatts, numberOfLights, PPU, use));

        project = new LightProject("Test Project");
        numberOfLights = "1";
        currentWatts = "0";
        newWatts = "9.0";
        PPU = "10.0";
        use = "12.0";
        Assert.assertFalse(project.testLights(currentWatts, newWatts, numberOfLights, PPU, use));

        project = new LightProject("Test Project");
        numberOfLights = "1";
        currentWatts = "37737.0";
        newWatts = "9.0";
        PPU = "10.0";
        use = "12.0";
        Assert.assertFalse(project.testLights(currentWatts, newWatts, numberOfLights, PPU, use));

        project = new LightProject("Test Project");
        numberOfLights = "1";
        currentWatts = "fdas.0";
        newWatts = "9.0";
        PPU = "10.0";
        use = "12.0";
        Assert.assertFalse(project.testLights(currentWatts, newWatts, numberOfLights, PPU, use));
    }

    @Test
    public void testNewWattage() {
        LightProject project = new LightProject("Test Project");
        String numberOfLights = "1";
        String currentWatts = "60.0";
        String newWatts = "9.0";
        String PPU = "10.0";
        String use = "12.0";
        Assert.assertTrue(project.testLights(currentWatts, newWatts, numberOfLights, PPU, use));

        project = new LightProject("Test Project");
        numberOfLights = "1";
        currentWatts = "60.0";
        newWatts = "0.0";
        PPU = "10.0";
        use = "12.0";
        Assert.assertFalse(project.testLights(currentWatts, newWatts, numberOfLights, PPU, use));

        project = new LightProject("Test Project");
        numberOfLights = "1";
        currentWatts = "60.0";
        newWatts = "asdf.0";
        PPU = "10.0";
        use = "12.0";
        Assert.assertFalse(project.testLights(currentWatts, newWatts, numberOfLights, PPU, use));

        project = new LightProject("Test Project");
        numberOfLights = "1";
        currentWatts = "60.0";
        newWatts = "95884321";
        PPU = "10.0";
        use = "12.0";
        Assert.assertFalse(project.testLights(currentWatts, newWatts, numberOfLights, PPU, use));
    }

    @Test
    public void testPPU() {
        LightProject project = new LightProject("Test Project");
        String numberOfLights = "1";
        String currentWatts = "60.0";
        String newWatts = "9.0";
        String PPU = "10.0";
        String use = "12.0";
        Assert.assertTrue(project.testLights(currentWatts, newWatts, numberOfLights, PPU, use));

        project = new LightProject("Test Project");
        numberOfLights = "1";
        currentWatts = "60.0";
        newWatts = "9.0";
        PPU = "1fdhsafh";
        use = "12.0";
        Assert.assertFalse(project.testLights(currentWatts, newWatts, numberOfLights, PPU, use));

        project = new LightProject("Test Project");
        numberOfLights = "1";
        currentWatts = "60.0";
        newWatts = "9.0";
        PPU = "473827342798.0";
        use = "12.0";
        Assert.assertFalse(project.testLights(currentWatts, newWatts, numberOfLights, PPU, use));

        project = new LightProject("Test Project");
        numberOfLights = "1";
        currentWatts = "60.0";
        newWatts = "9.0";
        PPU = "-10.0";
        use = "12.0";
        Assert.assertFalse(project.testLights(currentWatts, newWatts, numberOfLights, PPU, use));
    }

    @Test
    public void testUsage() {
        LightProject project = new LightProject("Test Project");
        String numberOfLights = "1";
        String currentWatts = "60.0";
        String newWatts = "9.0";
        String PPU = "10.0";
        String use = "12.0";
        Assert.assertTrue(project.testLights(currentWatts, newWatts, numberOfLights, PPU, use));

        project = new LightProject("Test Project");
        numberOfLights = "1";
        currentWatts = "60.0";
        newWatts = "9.0";
        PPU = "10.0";
        use = "fdsa.0";
        Assert.assertFalse(project.testLights(currentWatts, newWatts, numberOfLights, PPU, use));

        project = new LightProject("Test Project");
        numberOfLights = "1";
        currentWatts = "60.0";
        newWatts = "9.0";
        PPU = "10.0";
        use = "72.0";
        Assert.assertFalse(project.testLights(currentWatts, newWatts, numberOfLights, PPU, use));

        project = new LightProject("Test Project");
        numberOfLights = "1";
        currentWatts = "60.0";
        newWatts = "9.0";
        PPU = "10.0";
        use = "-12.0";
        Assert.assertFalse(project.testLights(currentWatts, newWatts, numberOfLights, PPU, use));
    }
}
