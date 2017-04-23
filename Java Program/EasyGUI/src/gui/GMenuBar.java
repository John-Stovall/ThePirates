package gui;

import main.User;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Ellipse2D;
import java.util.ArrayList;

/**
 * Created by Robert on 4/14/17.
 */
public class GMenuBar implements GUIComponent, MouseListener {

    private int height;

    private boolean pageSelected = false;

    private boolean accountPressed = false;

    private boolean pagePressed = false;

    private boolean accountSelected = false;

    private int width;

    private int dropdownWidth;

    private int tabPadding = 8;

    private int pageTotalHeight;

    private int accountTotalHeight;

    ArrayList<GUIComponent> pageComponents = new ArrayList<>();

    ArrayList<GUIComponent> accountComponents = new ArrayList<>();

    public GMenuBar(final int height) {
        this.height = height;
    }

    public void addPage(GUIComponent c) {
        if (c instanceof GButton) {
            ((GButton) c).setActive(false);
        }
        pageComponents.add(c);
    }


    public void addAccount(GUIComponent c) {
        if (c instanceof GButton) {
            ((GButton) c).setActive(false);
        }
        accountComponents.add(c);
    }

    @Override
    public int draw(Graphics g, int x, int y, int width) {
        dropdownWidth = Math.min((int) Math.round(GUI.window.getWidth() * 0.8), 512);

        this.width = width;
        g.setColor(Color.darkGray);
        g.fillRect(0, 0, GUI.window.getWidth(), height);

        //Draw the page bar.
        g.setColor(Color.gray);
        int y2 = height;
        if (pageSelected || pagePressed) {
            g.fillRect(0, 0, height, height);
        }
        for (GUIComponent c : pageComponents) {
            y2 += c.draw(g, GUI.horizontalOffset - dropdownWidth - tabPadding, y2, dropdownWidth);
        }
        g.setColor(Color.white);
        for (int i = 0; i < 3; i++) {
            g.fillRect(height / 8, (int)(height / 7.0 * (((i + 1) * 2) - 1)),
                    (height / 8) * 6, height / 7);
        }
        pageTotalHeight = y2 - height;

        //Draw the account bar.
        g.setColor(Color.gray);
        int y3 = height;
        int newX = GUI.window.getWidth() - dropdownWidth;
        if (accountSelected || accountPressed) {
            g.fillRect(GUI.window.getWidth() - height, 0, height, height);
        }
        for (GUIComponent c : accountComponents) {
            y3 += c.draw(g, newX + dropdownWidth + GUI.horizontalOffset + tabPadding, y3, dropdownWidth);
        }
        g.setColor(Color.white);
        Graphics2D g2d = ((Graphics2D) g);

        Ellipse2D circle = new Ellipse2D.Double(GUI.window.getWidth() - height * 0.875, height / 4 / 2, height * 0.75, height * 0.75);
        g2d.fill(circle);
        accountTotalHeight = y3 - height;

        g.setFont(new Font("Helvetica", Font.PLAIN, 26));

        //Draw name
        String text = User.getLoadedUser().getName();
        int length = g.getFontMetrics().stringWidth(text);

        g.drawString(text, GUI.window.getWidth() - height - length - 4, height - 10);

        //Process slide over animation
        if (pageSelected) {
            GUI.horizontalOffset += Math.ceil((dropdownWidth - GUI.horizontalOffset) / 5.0);
        } else if (accountSelected) {
            GUI.horizontalOffset += Math.floor((-dropdownWidth - GUI.horizontalOffset) / 5.0);
        } else {
            GUI.horizontalOffset += Math.ceil(Math.abs((-GUI.horizontalOffset) / 5.0)) * (Math.signum((-GUI.horizontalOffset) / 5.0));
        }

        return height;
    }

    @Override
    public void mouseClicked(MouseEvent e) {}

    @Override
    public void mousePressed(MouseEvent e) {
        if (e.getX() > 0 && e.getX() < height && e.getY() > 0 && e.getY() < height) {
            pagePressed = true;
            //GUI.window.redraw();
        }
        if (e.getX() > GUI.window.getWidth() - height && e.getX() < GUI.window.getWidth() && e.getY() > 0 && e.getY() < height) {
            accountPressed = true;
            //GUI.window.redraw();
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if ((e.getX() > 0 && e.getX() < height && e.getY() > 0 && e.getY() < height && pagePressed) &&
        !(e.getX() > 0 && e.getX() < dropdownWidth && e.getY() > height && e.getY() < height + pageTotalHeight)) {
            pageSelected = !pageSelected;
        } else {
            pageSelected = false;
        }
        for (GUIComponent c : pageComponents) {
            if (c instanceof GButton) {
                ((GButton) c).setActive(pageSelected);
            }
        }

        if ((e.getX() > GUI.window.getWidth() - height && e.getX() < GUI.window.getWidth() && e.getY() > 0 && e.getY() < height && accountPressed) &&
                !(e.getX() > GUI.window.getWidth() - dropdownWidth && e.getX() < GUI.window.getWidth() && e.getY() > height && e.getY() < height + accountTotalHeight)) {
            accountSelected = !accountSelected;
        } else {
            accountSelected = false;
        }

        for (GUIComponent c : accountComponents) {
            if (c instanceof GButton) {
                ((GButton) c).setActive(accountSelected);
            }
        }

        pagePressed = false;
        accountPressed = false;
        //GUI.window.redraw();
    }

    @Override
    public void mouseEntered(MouseEvent e) {}

    @Override
    public void mouseExited(MouseEvent e) {}
}
