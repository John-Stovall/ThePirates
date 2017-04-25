package gui;

import java.awt.*;
import java.util.ArrayList;

/**
 * Created by Robert on 4/23/17.
 *
 * GDivider allows you to place components both horizontally and vertically.
 */
public class GDivider implements GUIComponent {

    /** The width of the cell. */
    private int cellWidth;

    /** The list of components this object holds. */
    ArrayList<GUIComponent> components = new ArrayList<>();

    /**
     * Create a divider with a provided width.
     *
     * @param cellWidth The width of each cell in this divider.
     */
    public GDivider(final int cellWidth) {
        this.cellWidth = cellWidth;
    }

    /**
     * Add a component to this divider.
     *
     * @param c The component to add.
     */
    public void add(GUIComponent c) {
        components.add(c);
    }

    @Override
    public int draw(Graphics g, int x, int y, int width) {
        int cellsPerRow = (int) (Math.floor(width / cellWidth));

        int rowHeight = 0;
        int maxHeight = 0;
        for (int i = 0; i < components.size(); i++) {
            GUIComponent part = components.get(i);
            int lastHeight = part.draw(g, x + (cellWidth * (i % cellsPerRow)), y + rowHeight, cellWidth);
            if (lastHeight > maxHeight) {
                maxHeight = lastHeight;
            }
            if ((i + 1) % cellsPerRow == 0) {
                rowHeight += maxHeight;
                maxHeight = 0;
            }
        }
        rowHeight += maxHeight;
        return rowHeight;
    }
}
