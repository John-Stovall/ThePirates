package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Robert on 4/11/17.
 */
public final class GUI extends JFrame {

    public static final GUI window = new GUI();

    private Map<String, ArrayList<GUIComponent>> pages = new HashMap<>();

    private int maxWidth = 720;

    private DrawPanel panel = new DrawPanel();

    private String currentPage = "default";

    private GUI() {
        super();
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(new Dimension(1280,720));
        setMinimumSize(new Dimension(320, 640));
        setVisible(true);
        setTitle("Hello World!");
        setLocationRelativeTo(null);
        add(panel, BorderLayout.CENTER);
        panel.repaint();
        pages.put("default", new ArrayList<>());
    }

    public void redraw() {
        panel.repaint();
    }

    public void maxMinimumWidth(final int max) {
        maxWidth = max;
    }

    public void add(final GUIComponent c) {
        this.add(currentPage, c);
    }

    public void add(final String page, final GUIComponent c) {
        if (pages.containsKey(page)) {
            pages.get(page).add(c);
        } else {
            final ArrayList<GUIComponent> newPage = new ArrayList<>();
            newPage.add(c);
            pages.put(page, newPage);
        }
        panel.repaint();
        panel.setVisible(true);
    }

    public void gotoPage(final String page) {
        for (GUIComponent c : pages.get(currentPage)) {
            if (c instanceof MouseListener) {
                removeMouseListener((MouseListener) c);
            }
        }
        if (!pages.containsKey(page)) {
            pages.put(page, new ArrayList<>());
        }
        currentPage = page;
        for (GUIComponent c : pages.get(currentPage)) {
            if (c instanceof MouseListener) {
                addMouseListener((MouseListener) c);
            }
        }
        panel.repaint();
    }

    private class DrawPanel extends JPanel {

        @Override
        public void paintComponent(Graphics theGraphics) {

            Graphics2D g = (Graphics2D) theGraphics;

            super.paintComponent(g);

            g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON);

            g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
                    RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

            final int x = (getWidth() > maxWidth) ? (getWidth() - maxWidth) / 2 : 0;
            int y = 0;
            for (GUIComponent c : pages.get(currentPage)) {
                g.setColor(Color.black);
                y += c.draw(g, x, y, (getWidth() > maxWidth) ? maxWidth : getWidth());
            }
        }
    }
}
