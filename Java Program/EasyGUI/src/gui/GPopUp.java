package gui;

import control.General;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
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

    private ArrayList<GUIComponent> components;

    private ArrayList<GMouseListener> mouseComponents = new ArrayList<>();

    private ArrayList<GKeyListener> keyComponents = new ArrayList<>();

    private ArrayList<GAnimation> animatedComponents = new ArrayList<>();

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
            height += c.draw(g, -1000, -1000, width);
        }
        height += padding * 2;


        int xPos = (int) (animation * (GUI.getWindowWidth() / 2) - width / 2.0);
        int yPos = (GUI.getWindowHeight() / 2) - height / 2;

        g.setColor(new Color(0, 0, 0, (int) (190 * fade)));

        g.fillRect(0, 0, GUI.getWindowWidth(), GUI.getWindowHeight());

        g.setColor(GUI.window.getBackground());

        Graphics2D g2d = (Graphics2D) g;
        RoundRectangle2D bg = new RoundRectangle2D.Double(xPos, yPos, width, height, 40, 40);

        g2d.fill(bg);

        int hPos = padding;
        for (int i = 0; i < components.size(); i++) {
            GUIComponent c = components.get(i);
            hPos += c.draw(g, xPos + padding, yPos + hPos, width - padding * 2);
        }
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
        } else {
            animation += Style.exponentialTween(animation, 3, 10);
            fade += Style.exponentialTween(fade, 0, 7);
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

            if (!General.clickedInside(xPos, yPos, width, height, e)) {
                destroy();
            }
        }
        return false;
    }
}
