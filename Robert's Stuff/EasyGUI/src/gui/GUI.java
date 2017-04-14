package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.util.ArrayList;

/**
 * Created by Robert on 4/11/17.
 */
public final class GUI extends JFrame {

    public static final GUI window = new GUI();

    private ArrayList<GUIPage> pages = new ArrayList<>();

    private ArrayList<GUIComponent> components = new ArrayList<>();

    private int maxWidth = 720;

    private DrawPanel panel = new DrawPanel();

    private GUI() {
        super();
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(new Dimension(1280,720));
        setMinimumSize(new Dimension(320, 640));
        setVisible(true);
        setTitle("Hello World!");
        setLocationRelativeTo(null);
        add(panel, BorderLayout.CENTER);
        panel.setLayout(null);
        panel.repaint();

//        JPanel test = new JPanel();
//        test.add(new JButton("Hello!"));
//        test.setLocation(20, 20);
//        test.setSize(50, 50);
//        test.setBackground(Color.red);
//        panel.add(test);
    }

    public void addPage(final GUIPage page) {
        pages.add(page);
    }

    public void redraw() {
        panel.repaint();
    }

    public void add(final GUIComponent c) {
        components.add(c);
        if (c instanceof KeyListener) {
            addKeyListener((KeyListener) c);
        }
        if (c instanceof MouseListener) {
            addMouseListener((MouseListener) c);
        }
        if (c instanceof JPanel) {
            panel.add((JPanel) c);
            ((JPanel)c).revalidate();
        }
        panel.setVisible(true);
    }

    public void gotoPage(final String page) {
        for (GUIPage p : pages) {
            if (p.getName().equals(page)) {
                gotoPage(p);
                break;
            }
        }
    }

    public void gotoPage(final GUIPage page) {
        panel.removeAll();
        for (GUIComponent c : components) {
            if (c instanceof MouseListener) {
                removeMouseListener((MouseListener) c);
            }
            if (c instanceof KeyListener) {
                removeKeyListener((KeyListener) c);
            }
            if (c instanceof JPanel) {
                panel.remove((JPanel) c);
                ((JPanel)c).revalidate();
            }
        }
        components.clear();
        page.build();
        panel.revalidate();
        panel.repaint();
        //panel.repaint();
    }

    private class DrawPanel extends JPanel {
        @Override
        public void paintComponent(Graphics theGraphics) {
            Graphics2D g = (Graphics2D) theGraphics;
            super.paintComponent(g);
            g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
            final int x = (getWidth() > maxWidth) ? (getWidth() - maxWidth) / 2 : 0;
            int y = 0;
            for (GUIComponent c : components) {
                g.setColor(Color.black);
                y += c.draw(g, x, y, (getWidth() > maxWidth) ? maxWidth : getWidth());
                if (c instanceof GWrapper) {
                    ((GWrapper) c).build(x, y, (getWidth() > maxWidth) ? maxWidth : getWidth());
                }
            }
        }
    }
}
