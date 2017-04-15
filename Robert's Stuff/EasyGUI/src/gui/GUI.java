package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.util.ArrayList;

/**
 * Created by Robert on 4/11/17.
 */
public final class GUI extends JFrame implements MouseWheelListener {

    public static final GUI window = new GUI();

    private ArrayList<GUIPage> pages = new ArrayList<>();

    private ArrayList<GUIComponent> components = new ArrayList<>();

    private int maxWidth = 720;

    private int sidePadding = 8;

    private int scrollOffset;

    private DrawPanel panel = new DrawPanel();

    private GUI() {
        super();
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setFocusTraversalKeysEnabled(false);
        setSize(new Dimension(1280,720));
        setMinimumSize(new Dimension(320, 640));
        setVisible(true);
        setTitle("Hello World!");
        setLocationRelativeTo(null);
        add(panel, BorderLayout.CENTER);
        addMouseWheelListener(this);
        panel.setLayout(null);
        panel.repaint();
    }

    public void addPage(final GUIPage page) {
        pages.add(page);
    }

    ArrayList<GUIComponent> getItems() {
        return components;
    }

    void redraw() {
        panel.repaint();
    }

    /**
     * This method adds a GUIComponent to the current page.
     *
     * @param c The GUIComponent to add.
     */
    public void add(final GUIComponent c) {
        components.add(c);
        addListeners(c);
        if (c instanceof JPanel) {
            panel.add((JPanel) c);
            ((JPanel)c).revalidate();
        }
        panel.setVisible(true);
    }

    /**
     * This method recursively loops through all components in all lists
     * and adds listeners that they use.
     *
     * @param c The GUIComponent to add listeners from.
     */
    private void addListeners(final GUIComponent c) {
        if (c instanceof KeyListener) {
            addKeyListener((KeyListener) c);
        }
        if (c instanceof MouseListener) {
            panel.addMouseListener((MouseListener) c);
        }
        if (c instanceof GMenuBar) {
            for (GUIComponent g : ((GMenuBar) c).pageComponents) {
                addListeners(g);
            }
            for (GUIComponent g : ((GMenuBar) c).accountComponents) {
                addListeners(g);
            }
        }
    }

    /**
     * This method recursively loops through all components in all lists
     * and removes the listeners that they use.
     *
     * @param c The GUIComponent to remove listeners from.
     */
    private void removeListeners(final GUIComponent c) {
        if (c instanceof MouseListener) {
            panel.removeMouseListener((MouseListener) c);
        }
        if (c instanceof KeyListener) {
            removeKeyListener((KeyListener) c);
        }
        if (c instanceof GMenuBar) {
            for (GUIComponent g : ((GMenuBar) c).pageComponents) {
                removeListeners(g);
            }
            for (GUIComponent g : ((GMenuBar) c).accountComponents) {
                removeListeners(g);
            }
        }
    }

    /**
     * If you don't have access to the particular page object you can use
     * the pages's name to find the correct page to go to. A little slow but works.
     *
     * @param page The NAME of the page to go to.
     */
    public void gotoPage(final String page) {
        for (GUIPage p : pages) {
            if (p.getName().equals(page)) {
                gotoPage(p);
                break;
            }
        }
    }

    /**
     * Remove all objects from the page and call the new Page object's build method
     * to reassemble the page.
     *
     * @param page The page to build.
     */
    public void gotoPage(final GUIPage page) {
        scrollOffset = 0;
        panel.removeAll();
        for (GUIComponent c : components) {
            removeListeners(c);
            if (c instanceof JPanel) {
                panel.remove((JPanel) c);
                ((JPanel)c).revalidate();
            }
        }
        components.clear();
        page.build();
        panel.revalidate();
        panel.repaint();
    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        scrollOffset -= e.getUnitsToScroll();
        repaint();
    }

    /**
     * This class handles all of the drawing.
     */
    private class DrawPanel extends JPanel {
        @Override
        public void paintComponent(Graphics theGraphics) {
            Graphics2D g = (Graphics2D) theGraphics;
            super.paintComponent(g);
            g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
            final int x = (getWidth() > maxWidth + sidePadding) ? (getWidth() - maxWidth) / 2 : sidePadding / 2;
            int y = scrollOffset;
            for (GUIComponent c : components) {
                g.setColor(Color.black);
                y += c.draw(g, x, y, (getWidth() > maxWidth + sidePadding) ? maxWidth : getWidth() - sidePadding);
                if (c instanceof GWrapper) {
                    ((GWrapper) c).build(x, y, (getWidth() > maxWidth) ? maxWidth : getWidth());
                }
            }
        }
    }
}
