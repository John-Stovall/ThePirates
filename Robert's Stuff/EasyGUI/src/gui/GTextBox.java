package gui;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * Created by Robert on 4/12/17.
 */
public class GTextBox implements GUIComponent, MouseListener, KeyListener {

    private int height;

    private int x;

    private int y;

    private int width;

    private Color color;

    private Color hover;

    private boolean pressed;

    private boolean selected;

    private Font font;

    private String text;

    public GTextBox(final int height, final Color main, final Color hover, final String text) {
        this.color = main;
        this.hover = hover;
        this.height = height;
        this.font = new Font("Helvetica", Font.PLAIN, height - 6);
        this.text = text;
    }

    public String getText() {
        return text;
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
        if (selected) {
            g.setColor(Color.black);
            g.drawString(text + "|", x, y + font.getSize());
            g.drawRect(x, y, width, height);
        } else {
            g.drawString(text, x, y + font.getSize());
        }
        return height;
    }

    @Override
    public void mouseClicked(MouseEvent e) {}

    @Override
    public void mousePressed(MouseEvent e) {
        selected = false;
        if (e.getX() > x && e.getX() < x + width && e.getY() > y + height && e.getY() < y + height * 2) {
            pressed = true;
            GUI.window.redraw();
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (e.getX() > x && e.getX() < x + width && e.getY() > y + height && e.getY() < y + height * 2 && pressed) {
            selected = true;
        }
        pressed = false;
        GUI.window.redraw();
    }

    @Override
    public void mouseEntered(MouseEvent e) {}

    @Override
    public void mouseExited(MouseEvent e) {}


    @Override
    public void keyTyped(KeyEvent e) {
        if (selected) {
            if (e.getKeyChar() == KeyEvent.VK_BACK_SPACE) {
                text = text.substring(0, text.length() - 1);
            } else {
                text += e.getKeyChar();
            }
            GUI.window.redraw();
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {}

    @Override
    public void keyReleased(KeyEvent e) {}
}
