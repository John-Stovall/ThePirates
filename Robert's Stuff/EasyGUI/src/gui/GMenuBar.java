package gui;

import java.awt.*;
import java.util.ArrayList;

/**
 * Created by Robert on 4/14/17.
 */
public class GMenuBar implements GUIComponent {

    private int height;

    protected ArrayList<GUIComponent> components = new ArrayList<>();

    public GMenuBar(final int height) {
        this.height = height;
    }

    public void add(GUIComponent c) {
        components.add(c);
    }

    @Override
    public int draw(Graphics g, int x, int y, int width) {
        g.setColor(Color.darkGray);
        g.fillRect(0, 0, GUI.window.getWidth(), height);
        int y2 = height;
        for (GUIComponent c : components) {
            y2 += c.draw(g, 0, y2, 200);
        }
        return height;
    }
}
