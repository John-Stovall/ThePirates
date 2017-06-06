package gui;

import control.General;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.geom.RoundRectangle2D;
import java.util.ArrayList;

/**
 * Created by Robert on 6/3/17.
 *
 * This class allows you to display temporary information on the screen.
 */
public class GPopUp implements GAnimation, GSubList, GKeyListener, GMouseListener {

    private int width;

    private int height;

    private int padding = 32;

    private double animation = -1.0;

    private double fade = 0;

    private boolean dead = false;

    private int buttonPos = 40;

    private ArrayList<GUIComponent> components;

    private ArrayList<GMouseListener> mouseComponents = new ArrayList<>();

    private ArrayList<GKeyListener> keyComponents = new ArrayList<>();

    private ArrayList<GAnimation> animatedComponents = new ArrayList<>();

    private boolean closable = true;

    /**
     * The vertical offset controlled by scrolling.
     */
    private int scrollOffset = 0;

    /**
     * The height of the currently drawn page.
     */
    private int pageHeight = 0;

    /**
     * Changes how fast you can scroll the page.
     */
    private static final double scrollSpeedMultiplier = 4.0;

    public GPopUp(ArrayList<GUIComponent> components) {
        this.components = components;
        constructLists(this.components);
    }

    private void constructLists(ArrayList<GUIComponent> list) {
        for (GUIComponent c : list) {
            if (c instanceof GMouseListener) {
                mouseComponents.add((GMouseListener) c);
            }
            if (c instanceof GKeyListener) {
                keyComponents.add((GKeyListener) c);
            }
            if (c instanceof GSubList) {
                for (ArrayList<GUIComponent> lists : ((GSubList) c).getComponents()) {
                    constructLists(lists);
                }
            }
            if (c instanceof GAnimation) {
                animatedComponents.add((GAnimation) c);
            }
        }
    }

    public void draw(Graphics g, int width) {

        this.width = width;

        height = 0;
        for (int i = 0; i < components.size(); i++) {
            GUIComponent c = components.get(i);
            height += c.draw(g, -1000, -1000, width - padding * 2);
        }
        height += padding * 2;

        int xPos = (int) (animation * (GUI.getWindowWidth() / 2) - width / 2.0);
        int yPos = (GUI.getWindowHeight() / 2) - height / 2;

        if (height + 40 > GUI.getWindowHeight()) {
            yPos = 64;
        }

        g.setColor(new Color(0, 0, 0, (int) (190 * fade)));

        g.fillRect(0, 0, GUI.getWindowWidth(), GUI.getWindowHeight());

        g.setColor(GUI.window.getBackground());

        if (components.size() > 0) {
            if (closable) {
                int xOffset = 0;
                if (xPos - 25 < 0) {
                    xOffset = -(xPos - 25);
                }
                g.drawOval(xPos - 20 + buttonPos + xOffset, yPos - 20 + buttonPos + scrollOffset, 20, 20);
                g.drawLine(xPos - 20 + 6 + buttonPos + xOffset, yPos - 20 + 6 + buttonPos + scrollOffset, xPos - 6 + buttonPos + xOffset, yPos - 6 + buttonPos + scrollOffset);
                g.drawLine(xPos - 6 + buttonPos + xOffset, yPos - 20 + 6 + buttonPos + scrollOffset, xPos - 20 + 6 + buttonPos + xOffset, yPos - 6 + buttonPos + scrollOffset);
            }

            Graphics2D g2d = (Graphics2D) g;
            RoundRectangle2D bg = new RoundRectangle2D.Double(xPos, yPos + scrollOffset, width, height, 40, 40);
            g2d.fill(bg);
        }

        int hPos = padding;
        for (int i = 0; i < components.size(); i++) {
            GUIComponent c = components.get(i);
            hPos += c.draw(g, xPos + padding, yPos + hPos + scrollOffset, width - padding * 2);
        }
        pageHeight = hPos + 40;
    }

    public void destroy() {
        dead = true;
    }

    @Override
    public void updateAnimations() {
        for (GAnimation c : animatedComponents) {
            c.updateAnimations();
        }
        if (!dead) {
            animation += Style.exponentialTween(animation, 1, 5);
            fade += Style.exponentialTween(fade, 1, 7);
            if (animation > 0.999) {
                buttonPos += Style.exponentialTweenRound(buttonPos, 0, 5);
            }
        } else {
            animation += Style.exponentialTween(animation, 3, 10);
            fade += Style.exponentialTween(fade, 0, 7);
            buttonPos += Style.exponentialTweenRound(buttonPos, 40, 5);
            if (animation > 2.8) {
                GUI.destroyPopUp();
            }
        }
    }

    @Override
    public ArrayList<ArrayList<GUIComponent>> getComponents() {
        ArrayList<ArrayList<GUIComponent>> myComponents = new ArrayList<>();
        myComponents.add(components);
        return myComponents;
    }

    @Override
    public void keyTyped(KeyEvent e) {
        if (!dead) {
            for (GKeyListener c : keyComponents) {
                c.keyTyped(e);
            }
        }
    }

    @Override
    public boolean mousePressed(MouseEvent e) {
        if (!dead) {
            for (GMouseListener c : mouseComponents) {
                c.mousePressed(e);
            }
        }
        return false;
    }

    @Override
    public boolean mouseReleased(MouseEvent e) {
        if (!dead) {
            for (GMouseListener c : mouseComponents) {
                c.mouseReleased(e);
            }

            int xPos = (int) (animation * (GUI.getWindowWidth() / 2 - width / 2));
            int yPos = (GUI.getWindowHeight() / 2) - height / 2;

            if (!General.clickedInside(xPos, yPos, width, height, e) && closable) {
                destroy();
            }
        }
        return false;
    }

    /**
     * Allows for popups to be scrolled.
     *
     * @param e The mouse wheel event.
     * @author Robert
     */
    void mouseWheelMoved(MouseWheelEvent e) {
        if (pageHeight > GUI.getWindowHeight()) {
            scrollOffset -= e.getUnitsToScroll() * scrollSpeedMultiplier;
            if (scrollOffset > 0) scrollOffset = 0;
            if (scrollOffset < -pageHeight + GUI.getWindowHeight() - 150) scrollOffset = -pageHeight + GUI.getWindowHeight() - 150;
        } else if (scrollOffset != 0) {
            scrollOffset = 0;
        }
    }

    /**
     * Calling this method will make it so that this popup cannot be closed by clicking outside it.
     *
     * @author Robert
     */
    public void setPermanent() {
        this.closable = false;
    }
}
