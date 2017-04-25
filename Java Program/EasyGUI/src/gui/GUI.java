package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

/**
 * Created by Robert on 4/11/17.
 *
 * This is the main class that does all the rendering.
 */
public final class GUI extends JFrame implements MouseWheelListener {

    /** The window itself. */
    public static final GUI window = new GUI();

    /** All of the pages to use. */
    private ArrayList<GUIPage> pages = new ArrayList<>();

    /** All of the current loaded GUIComponents. */
    private ArrayList<GUIComponent> components = new ArrayList<>();

    /** The max width of the window's contents before it starts adding padding. */
    private int maxWidth = 720;

    /** The padding added to the sides of all components. */
    private int sidePadding = 8;

    /** The horizontal offset of components. Used in the slide-over animation. */
    public static int horizontalOffset = 0;

    /** The vertical offset controlled by scrolling. */
    private int scrollOffset;

    /** The drawing panel that everything is drawn to. */
    private static DrawPanel panel;

    /** The currently loaded page. */
    private static GUIPage currentPage;

    /**
     * Starts the GUI with some basic settings.
     */
    private GUI() {
        super();
        panel = new DrawPanel();
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

        /** The animation clock. */
        Timer clock = new Timer(1000 / 60, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                panel.repaint();
            }
        });
        clock.start();
    }

    /**
     * Add a GUIPage to the list of pages.
     *
     * @param page The page to add.
     */
    public void addPage(final GUIPage page) {
        pages.add(page);
    }

    /**
     * Returned the list of currently loaded components.
     *
     * @return The ArrayList of components
     */
    ArrayList<GUIComponent> getItems() {
        return components;
    }

    /**
     * Gets the inner width of the window.
     *
     * @return The width of the panel
     */
    public static int getWindowWidth() {
        return panel.getWidth();
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
        if (c instanceof GDivider) {
            for (GUIComponent g : ((GDivider) c).components) {
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
        if (c instanceof GDivider) {
            for (GUIComponent g : ((GDivider) c).components) {
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
        currentPage = page;
    }

    /**
     * Gets the title of the currently loaded page.
     *
     * @return The title of the page.
     */
    public static String getPageTitle() {
        return currentPage.getName();
    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        scrollOffset -= e.getUnitsToScroll();
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
            int x = horizontalOffset + ((getWidth() > maxWidth + sidePadding) ? (getWidth() - maxWidth) / 2 : sidePadding / 2);
            int y = scrollOffset;

            /** Loop through the list of currently loaded components and call their draw methods. */
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
