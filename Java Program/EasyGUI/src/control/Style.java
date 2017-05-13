package control;

import java.awt.*;

/**
 * Created by Robert on 5/12/17.
 *
 * This class simply holds constant variables that relate to colors, fonts, and animations.
 * If we want to change the colors or fonts we only need to change them here instead of all over the place.
 */
public class Style {

    private Style() {}

    /**
     *  Colors
     */

    public static final Color defaultTextColor          = Color.black;

    public static final Color menuColor                 = Color.decode("#43A047");

    public static final Color menuTextColor             = Color.white;

    public static final Color menuSideBarColor          = Color.decode("#2E7D32");

    public static final Color menuSideBarSecondaryColor = Color.decode("#388E3C");

    public static final Color primaryButtonColor        = Color.decode("#2E7D32");

    public static final Color secondaryButtonColor      = Color.decode("#388E3C");

    public static final Color buttonTextColor           = Color.white;

    public static final Color primaryDropdownColor      = Color.decode("#2E7D32");

    public static final Color secondaryDropdownColor    = Color.decode("#388E3C");

    public static final Color dropdownBorderColor       = Color.decode("#1B5E20");

    public static final Color textBoxColor              = Color.gray;

    public static final Color textBoxSecondaryColor     = Color.white;

    public static final Color textBoxBorderColor        = Color.black;

    public static final Color textBoxErrorColor         = Color.red;

    public static final Color[] graphBarColors          = new Color[] {Color.black, Color.red, Color.yellow};

    /**
     * Fonts
     */

    public static final Font titleFont                  = new Font("Helvetica", Font.PLAIN, 32);

    public static final Font defaultFont                = new Font("Helvetica", Font.PLAIN, 20);

    public static final Font graphTicks                 = new Font("Helvetica", Font.PLAIN, 12);

    public static final Font textBoxFail                = new Font("Helvetica", Font.PLAIN, 16);

    /**
     * Animation Variables
     */

    public static final int frameRate                   = 1000 / 60;

    public static final double sidebarSlideSpeed        = 7.0;

    public static final double graphMoveSpeed           = 3.0;

    public static final double graphSpawnThreshold      = 0.9;

    public static final double buttonMoveSpeed          = 3.0;

    public static final double dropdownMoveSpeed        = 3.0;

    public static final double textBoxMessageMoveSpeed  = 10.0;

    public static final double textBoxFlashSpeed        = 0.02;

    /**
     * Animation Functions
     */

    public static double exponentialTweenRound(double current, double goal, double speed) {
        return Math.ceil(Math.abs((goal - current ) / speed)) * (Math.signum((goal - current) / speed));
    }

    public static double exponentialTween(double current, double goal, double speed) {
        return (goal - current) / speed;

    }


}
