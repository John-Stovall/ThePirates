package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * Created by Robert on 4/12/17.
 */
public class GButton extends GSpacer implements GUIComponent, MouseListener {

    private int height;

    private int x = 0;

    private int y = 0;

    private int width = 0;

    public GButton(final int height, final Color color) {
        super(height, color);
        this.height = height;
    }

    @Override
    public int draw(Graphics g, int x, int y, int width) {
        super.draw(g, x, y, width);
        this.x = x;
        this.y = y;
        this.width = width;
        return height;
    }

    @Override
    public void mouseClicked(MouseEvent e) {}

    @Override
    public void mousePressed(MouseEvent e) {}

    @Override
    public void mouseReleased(MouseEvent e) {
        if (e.getX() > x && e.getX() < x + width && e.getY() > y + height && e.getY() < y + height * 2) {
            clickAction();
        }}

    @Override
    public void mouseEntered(MouseEvent e) {}

    @Override
    public void mouseExited(MouseEvent e) {}

    public void clickAction() {

    }
}
