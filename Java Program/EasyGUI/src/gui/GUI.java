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
public final class GUI extends JFrame implements MouseWheelListener, MouseListener, KeyListener {

    /** The window itself. */
    public static final GUI window = new GUI();

    /** All of the pages to use. */
    private ArrayList<GUIPage> pages = new ArrayList<>();

    /** All of the current loaded GUIComponents. */
    private ArrayList<GUIComponent> components = new ArrayList<>();

    /** All of the GUIComponents that use the MouseListener. */
    private ArrayList<GMouseListener> mouseComponents = new ArrayList<>();

    /** All of the GUIComponents that use the KeyListener. */
    private ArrayList<GKeyListener> keyComponents = new ArrayList<>();

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

    /** The height of the currently drawn page. */
    private static int pageHeight;

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
        panel.addMouseWheelListener(this);
        panel.addMouseListener(this);
        addKeyListener(this);
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
        if (c instanceof JPanel) {
            panel.add((JPanel) c);
            ((JPanel)c).revalidate();
        }
        panel.setVisible(true);
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
            if (c instanceof JPanel) {
                panel.remove((JPanel) c);
                ((JPanel)c).revalidate();
            }
        }
        components.clear();
        mouseComponents.clear();
        keyComponents.clear();
        page.build();
        panel.revalidate();
        currentPage = page;
        constructLists(components);
    }

    /**
     * This loops through all of the GUIComponents and
     * through any sublists like GDivider and GMenuBar.
     * It gets all of the GUIComponents that use listeners and adds them to their own lists.
     *
     * @param list
     */
    private void constructLists(ArrayList<GUIComponent> list) {
        for (GUIComponent c : list) {
            if (c instanceof  GMouseListener) {
                mouseComponents.add((GMouseListener) c);
            }
            if (c instanceof  GKeyListener) {
                keyComponents.add((GKeyListener) c);
            }
            if (c instanceof GSubList) {
                for (ArrayList<GUIComponent> lists : ((GSubList) c).getComponents()) {
                    constructLists(lists);
                }
            }
        }
    }

    /**
     * Gets the title of the currently loaded page.
     *
     * @return The title of the page.
     */
    static String getPageTitle() {
        return currentPage.getName();
    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        if (pageHeight > panel.getHeight()) {
            scrollOffset -= e.getUnitsToScroll();
            if (scrollOffset > 0) scrollOffset = 0;
            if (scrollOffset < -pageHeight + panel.getHeight()) scrollOffset = -pageHeight + panel.getHeight();
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {}

    @Override
    public void mousePressed(MouseEvent e) {
        for (GMouseListener c : mouseComponents) {
            if (c.mousePressed(e)) break;
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        for (GMouseListener c : mouseComponents) {
            if (c.mouseReleased(e)) break;
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {}

    @Override
    public void mouseExited(MouseEvent e) {}

    @Override
    public void keyTyped(KeyEvent e) {
        for (GKeyListener c : keyComponents) {
            c.keyTyped(e);
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {}

    @Override
    public void keyReleased(KeyEvent e) {}

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

            // Loop through the list of currently loaded components and call their draw methods.
            for (GUIComponent c : components) {
                g.setColor(Color.black);
                y += c.draw(g, x, y, (getWidth() > maxWidth + sidePadding) ? maxWidth : getWidth() - sidePadding);
                if (c instanceof GWrapper) {
                    ((GWrapper) c).build(x, y, (getWidth() > maxWidth) ? maxWidth : getWidth());
                }
            }
            pageHeight = y - scrollOffset;
        }
    }
}
