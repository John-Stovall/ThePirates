package gui;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * Created by Robert on 4/12/17.
 */
public class GButton implements GUIComponent, MouseListener {

    private int height;

    private int x = 0;

    private int y = 0;

    private int width = 0;

    private Color color;

    private Color hover;

    private boolean pressed = false;

    private Font font;

    private String text;

    public GButton(final int height, final Color main, final Color hover, final String text) {
        this.color = main;
        this.hover = hover;
        this.height = height;
        this.font = new Font("Helvetica", Font.PLAIN, height - 6);
        this.text = text;
    }

    public void setFont(final Font font) {
        this.font = font;
    }

    @Override
    public int draw(Graphics g, int x, int y, int width) {
        if (pressed) {
            g.setColor(hover);
        } else {
            g.setColor(color);
        }
        g.fillRect(x, y, width, height);
        this.x = x;
        this.y = y;
        this.width = width;
        g.setColor(Color.white);
        g.setFont(font);
        g.drawString(text, x, y + font.getSize());
        return height;
    }

    @Override
    public void mouseClicked(MouseEvent e) {}

    @Override
    public void mousePressed(MouseEvent e) {
        if (e.getX() > x && e.getX() < x + width && e.getY() > y + height && e.getY() < y + height * 2) {
            pressed = true;
            GUI.window.redraw();
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (e.getX() > x && e.getX() < x + width && e.getY() > y + height && e.getY() < y + height * 2 && pressed) {
            clickAction();
        }
        pressed = false;
    }

    @Override
    public void mouseEntered(MouseEvent e) {}

    @Override
    public void mouseExited(MouseEvent e) {}

    /**
     * When you create a button override this method to add
     * custom functionality to certain buttons.
     */
    public void clickAction() {}
}
