package gui;

import java.awt.*;
import java.util.ArrayList;

/**
 * Created by Robert on 4/23/17.
 *
 * GDivider allows you to place components both horizontally and vertically.
 */
public class GDivider implements GUIComponent, GSubList {

    /** The width of the cell. */
    private int cellWidth;

    private int cells = 0;

    /** The list of components this object holds. */
    private ArrayList<GUIComponent> components = new ArrayList<>();

    /**
     * Create a divider with a provided width.
     * It will fill rows with as many cells as it can.
     *
     * @param cellWidth The width of each cell in this divider.
     */
    public GDivider(final int cellWidth) {
        this.cellWidth = cellWidth;
    }

    /**
     * Creates a divider with a scaling width. It will divide the
     * width into the provided number of cells. If the cells get less than
     * the minimum width then they will be split into new rows.
     *
     * @param cellWidth The minimum width of a cell.
     * @param cells The number of cells per row.
     */
    public GDivider(final int cellWidth, final int cells) {
        this(cellWidth);
        this.cells = cells;
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
        int cellsPerRow, widthPerCell;
        int cellOffset = 0;

        if (cells == 0) {
            cellsPerRow = (int)(Math.floor(width / cellWidth));
            widthPerCell = cellWidth;
            if (width > cellsPerRow * widthPerCell) {
                cellOffset = (width - cellsPerRow * widthPerCell) / cellsPerRow / 2;
            }
        } else {
            cellsPerRow = cells;
            widthPerCell = width / cellsPerRow;
            while (widthPerCell < cellWidth) {
                cellsPerRow--;
                widthPerCell = width / cellsPerRow;
            }
        }

        int rowHeight = 0;
        int maxHeight = 0;
        for (int i = 0; i < components.size(); i++) {
            GUIComponent part = components.get(i);
            int lastHeight = part.draw(g, x + (widthPerCell * (i % cellsPerRow)) + cellOffset,
                    y + rowHeight, widthPerCell);
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

    @Override
    public ArrayList<ArrayList<GUIComponent>> getComponents() {
        ArrayList<ArrayList<GUIComponent>> list = new ArrayList<>();
        list.add(components);
        return list;
    }
}