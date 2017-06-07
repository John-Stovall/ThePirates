package gui;

import java.awt.*;
import java.util.ArrayList;

/**
 * Created by Robert on 4/30/17.
 * <p>
 * This class draws a simple graph of a supplied data array.
 */
public class GGraph implements GUIComponent, GAnimation {

    /**
     * All of the data that this graph will draw.
     */
    private ArrayList<double[]> times;

    /**
     * The largest value in the data.
     */
    private double largestValue = 0;

    /**
     * The color of the various lines.
     */
    private Color[] lines = Style.graphBarColors;

    /**
     * Used for the intro animation.
     */
    private double[] animation;

    /**
     * The first label.
     */
    private String label1 = "";

    /**
     * The second label.
     */
    private String label2 = "";

    /**
     * Creates a graph with the supplied data points.
     *
     * @param dataPoints The lines of the graph. Each element of the outer ArrayList is
     *                   a x point of the graph while each element of the double array is the y
     *                   value of a line on the graph.
     * @author Robert
     */
    public GGraph(final ArrayList<double[]> dataPoints) {
        times = dataPoints;
        for (double[] e : times) {
            for (double anE : e) {
                if (largestValue < anE) {
                    largestValue = anE;
                }
            }
        }
        animation = new double[times.size()];
    }

    /**
     * Sets the label.
     *
     * @param label1 What to set the label to.
     * @author Robert
     */
    public void setLabel1(String label1) {
        this.label1 = label1;
    }

    /**
     * Sets the label.
     *
     * @param label2 What to set the label to.
     * @author Robert
     */
    public void setLabel2(String label2) {
        this.label2 = label2;
    }

    @Override
    public int draw(Graphics g, int x, int y, int width) {

        int ticksLength = g.getFontMetrics().stringWidth((int)(largestValue) + "") + 10;
        g.setFont(Style.graphTicks);
        int height = (int) ((width * 0.35));
        int paneX = (int) (width / 1.2);
        int paneY = (int) (height / 1.1);
        double offsetTest = x + (int) (width * 0.05);

        while (paneX + ticksLength > GUI.getWindowWidth()) {
            paneX--;
            offsetTest += 0.5;
        }

        int offsetX = (int)offsetTest;
        paneX += 20;
        offsetX += 20;

        int offsetY = y + (int) (height * 0.05) / 2;

        int ticksLength2 = g.getFontMetrics().stringWidth(((int)largestValue) + "") + 15;
        g.drawString("$", offsetX - ticksLength2 - 8, y + (paneY) / 2 + 6);
        int nameLength = g.getFontMetrics().stringWidth("Months");
        g.drawString("Months", offsetX + paneX - nameLength, y + paneY + 50);

        if (!label1.equals("")) {
            int labelLength = g.getFontMetrics().stringWidth(label1);
            g.drawString(label1, offsetX, y + paneY + 50);
            if (!label2.equals("")) {
                g.setColor(lines[1]);
                g.drawString(label2, offsetX + labelLength + 10, y + paneY + 50);
            }
        }

        g.setColor(Color.black);

        Graphics2D g2d = (Graphics2D) g;
        g2d.setStroke(new BasicStroke(1));

        g.setColor(Color.black);
        g.drawLine(offsetX, offsetY, offsetX, offsetY + paneY);
        g.drawLine(offsetX, offsetY + paneY, offsetX + paneX, offsetY + paneY);

        int ticks = height / 30;
        if (ticks < 2) ticks = 2;

        for (int i = 0; i < ticks; i++) {
            String tick = Integer.toString((int) (Math.round(largestValue / (ticks - 1) * i)));

            int wordLength = g.getFontMetrics().stringWidth(tick);


            g.drawString(tick, offsetX - wordLength - 10, offsetY + paneY - paneY / (ticks - 1) * i + 4);
            g.drawLine(offsetX - 4, offsetY + paneY - paneY / (ticks - 1) * i, offsetX, offsetY + paneY - paneY / (ticks - 1) * i);
        }

        for (int i = 0; i < times.size(); i++) {
            if (paneX > 300 || i % 2 == 0) {
                String text = Integer.toString(i);
                int w = g.getFontMetrics().stringWidth(text);
                g.drawString(text, offsetX + paneX / (times.size() - 1) * i - w / 2, offsetY + paneY + 20);
                g.drawLine(offsetX + paneX / (times.size() - 1) * i, offsetY + paneY,
                        offsetX + paneX / (times.size() - 1) * i, offsetY + paneY + 4);
            }
        }
        g2d.setStroke(new BasicStroke(2));

        int lastX = 0;
        int lastY = 0;
        double[] e = times.get(0);
        for (int j = 0; j < e.length; j++) {
            for (int i = 0; i < times.size(); i++) {
                e = times.get(i);

                g.setColor(lines[j]);
                int xPos = offsetX + paneX / (times.size() - 1) * i;

                double value = e[j];
                if (value < 0) value = 0;

                int yPos = offsetY + (int) ((int) (paneY - value / largestValue * paneY) * animation[i]);
                if (animation[i] > 0) {
                    //g.fillOval(xPos - 2, yPos - 2, 4, 4);
                    if (i != 0) {
                        g2d.drawLine(lastX, lastY, xPos, yPos);
                    }
                }
                lastX = xPos;
                lastY = yPos;
            }
        }
        return height + 20;
    }

    @Override
    public void updateAnimations() {
        for (int i = 0; i < times.size(); i++) {
            if (i == 0) {
                animation[i] += (1 - animation[i]) / Style.graphMoveSpeed;
            } else {
                if (animation[i - 1] > Style.graphSpawnThreshold) {
                    animation[i] += (1 - animation[i]) / Style.graphMoveSpeed;
                }
            }
        }
    }
}
