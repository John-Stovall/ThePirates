package gui;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

/**
 * Created by Robert on 4/14/17.
 */
public class GMenuBar implements GUIComponent, MouseListener {

    private int height;

    private boolean selected = false;

    private boolean pressed = false;

    private int width;

    private int totalHeight;

    protected ArrayList<GUIComponent> components = new ArrayList<>();

    public GMenuBar(final int height) {
        this.height = height;
    }

    public void add(GUIComponent c) {
        components.add(c);
    }

    @Override
    public int draw(Graphics g, int x, int y, int width) {
        this.width = width;
        g.setColor(Color.darkGray);
        g.fillRect(0, 0, GUI.window.getWidth(), height);
        g.setColor(Color.gray);
        int y2 = height;
        if (selected || pressed) {
            g.fillRect(0, 0, height, height);
            for (GUIComponent c : components) {
                y2 += c.draw(g, 0, y2, 200);
            }
        }
        totalHeight = y2 - height;
        return height;
    }

    @Override
    public void mouseClicked(MouseEvent e) {}

    @Override
    public void mousePressed(MouseEvent e) {
        if (e.getX() > 0 && e.getX() < height && e.getY() > 0 && e.getY() < height) {
            pressed = true;
            GUI.window.redraw();
        } else {
            selected = false;
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if ((e.getX() > 0 && e.getX() < height && e.getY() > 0 && e.getY() < height && pressed) &&
        !(e.getX() > 0 && e.getX() < width && e.getY() > height && e.getY() < height + totalHeight)) {
            selected = !selected;
        }
        System.out.println(e.getX());
        System.out.println(e.getY());
        pressed = false;
        GUI.window.redraw();
    }

    @Override
    public void mouseEntered(MouseEvent e) {}

    @Override
    public void mouseExited(MouseEvent e) {}
}
