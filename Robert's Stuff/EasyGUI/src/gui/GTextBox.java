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

    /**
     * This method returns whatever text is in the textbox.
     *
     * @return
     */
    public String getText() {
        return text;
    }

    @Override
    public int draw(Graphics g, int x, int y, int width) {
        if (pressed || selected) {
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
        g.setColor(Color.black);
        if (selected) {
            g.drawString(text + "|", x + 4, y + font.getSize());
            g.drawRect(x, y, width, height);
        } else {
            g.drawString(text, x + 4, y + font.getSize());
        }
        return height;
    }

    @Override
    public void mouseClicked(MouseEvent e) {}

    @Override
    public void mousePressed(MouseEvent e) {
        selected = false;
        if (e.getX() > x && e.getX() < x + width && e.getY() > y && e.getY() < y + height) {
            pressed = true;
            GUI.window.redraw();
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (e.getX() > x && e.getX() < x + width && e.getY() > y && e.getY() < y + height && pressed) {
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
            if (e.getKeyChar() == KeyEvent.VK_BACK_SPACE && text.length() > 0) {
                text = text.substring(0, text.length() - 1);
            } else {
                if (e.getKeyChar() != KeyEvent.VK_TAB) {
                    text += e.getKeyChar();
                }
            }

            if (e.getKeyChar() == KeyEvent.VK_TAB) {
                System.out.println("Called");
                boolean found = false;
                for (GUIComponent c : GUI.window.getItems()) {
                    if (c == this) {
                        found = true;
                    } else if (found && c instanceof GTextBox) {
                        selected = false;
                        ((GTextBox)c).selected = true;
                    }
                }
            }
            GUI.window.redraw();
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {}

    @Override
    public void keyReleased(KeyEvent e) {}
}
