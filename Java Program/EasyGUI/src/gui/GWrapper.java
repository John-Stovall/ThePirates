package gui;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Robert on 4/14/17.
 *
 * This class adds compatibility for standard JComponents in the EasyGUI system.
 * This way you can use JTextFields or other components.
 */
public class GWrapper extends JPanel implements GUIComponent {

    /** The height of the wrapper. */
    private int height;

    /** The SWING component that this wrapper will hold. */
    private JComponent item;

    /**
     * Create a wrapper.
     *
     * @param height The height of the area.
     * @param item The swing component that this wrapper will hold.
     * @author Robert
     */
    public GWrapper(final int height, final JComponent item) {
        this.height = height;
        this.item = item;
        setOpaque(false);
        setLayout(new BorderLayout());
        add(item, BorderLayout.CENTER);
        setVisible(true);
    }

    @Override
    public int draw(Graphics g, int x, int y, int width) {

        //g.fillRect(x - 8, y - 8, width + 16, height + 16);

        setLocation(x, y);
        //setBounds(x, y, width, height);
        setSize(width, height);
        revalidate();
        return height;
    }

    /**
     * This method returns the currently help JComponent.
     *
     * @return The component used by this wrapper.
     * @author Robert
     */
    public JComponent getComponent() {
        return item;
    }
}
