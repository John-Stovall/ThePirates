package gui;

import java.awt.*;

/**
 * Created by Robert on 5/12/17.
 *
 * This class simply holds constant variables that relate to colors, fonts, and animations.
 * If we want to change the colors or fonts we only need to change them here instead of all over the place.
 */
public final class Style {

    /**
     * Blank constructor so this class cannot be instantiated.
     * @author Robert
     */
    private Style() {}

    /**
     *  Colors
     */

    static final Color defaultTextColor          = Color.black;

    static final Color menuColor                 = Color.decode("#43A047");

    static final Color menuTextColor             = Color.white;

    static final Color menuSideBarColor          = Color.decode("#2E7D32");

    static final Color menuSideBarSecondaryColor = Color.decode("#388E3C");

    static final Color primaryButtonColor        = Color.decode("#43A047");

    static final Color secondaryButtonColor      = Color.decode("#388E3C");

    public static final Color redButtonColor     = Color.decode("#F44336");

    public static final Color redHoverColor      = Color.decode("#D32F2F");

    static final Color buttonTextColor           = Color.white;

    static final Color primaryDropdownColor      = Color.decode("#43A047");

    static final Color secondaryDropdownColor    = Color.decode("#388E3C");

    static final Color dropdownBorderColor       = Color.decode("#1B5E20");

    static final Color textBoxColor              = Color.gray;

    static final Color textBoxSecondaryColor     = Color.white;

    static final Color textBoxBorderColor        = Color.black;

    static final Color textBoxErrorColor         = Color.red;

    static final Color[] graphBarColors          = new Color[] {Color.black, Color.decode("#43A047"), Color.green};

    /**
     * Fonts
     */

    static final Font titleFont                  = new Font("Helvetica", Font.PLAIN, 32);

    public static final Font defaultFont         = new Font("Helvetica", Font.PLAIN, 20);

    public static final Font graphTicks          = new Font("Helvetica", Font.PLAIN, 12);

    static final Font textBoxFail                = new Font("Helvetica", Font.PLAIN, 16);

    /**
     * Animation Variables
     */

    private static final double speedModifier     = 1;

    static final int frameRate                   = (int) ((1000 / 60) / speedModifier);

    static final double sidebarSlideSpeed        = Math.max(7.0 * speedModifier, 1);

    static final double graphMoveSpeed           = Math.max(3.0 * speedModifier, 1);

    static final double graphSpawnThreshold      = 0.9;

    static final double buttonMoveSpeed          = Math.max(3.0 * speedModifier, 1);

    static final double dropdownMoveSpeed        = Math.max(3.0 * speedModifier, 1);

    static final double textBoxMessageMoveSpeed  = Math.max(5.0 * speedModifier, 1);

    static final double textBoxFlashSpeed        = 0.03 / speedModifier;

    /**
     * Animation Functions
     */

    /**
     * This method takes a current position, goal and speed and returns the
     * distance to travel using an exponential tween. If the value is between -1 and 1
     * the value will be rounded to one of those two.
     *
     * @param current The current position of the object being animated.
     * @param goal The goal position.
     * @param speed The speed to travel. LOW VALUES ARE FASTER!!
     * @return The distance to travel this frame.
     * @author Robert
     */
    static double exponentialTweenRound(double current, double goal, double speed) {
        return Math.ceil(Math.abs((goal - current ) / speed)) * (Math.signum((goal - current) / speed));
    }

    /**
     * This method takes a current position, goal and speed and returns the
     * distance to travel using an exponential tween. Values are not rounded.
     *
     * @param current The current position of the object being animated.
     * @param goal The goal position.
     * @param speed The speed to travel. LOW VALUES ARE FASTER!!
     * @return The distance to travel this frame.
     * @author Robert
     */
    static double exponentialTween(double current, double goal, double speed) {
        return (goal - current) / speed;
    }
}
