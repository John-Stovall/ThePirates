package gui;

import java.awt.*;
import java.util.ArrayList;

/**
 * Created by Robert on 4/30/17.
 */
public class GGraph implements GUIComponent {

    private static ArrayList<double[]> times;

    private double largestValue = 0;

    private Color[] lines = {Color.black, Color.red};

    private double[] animation;

    public GGraph(final ArrayList<double[]> dataPoints) {
        times = dataPoints;
        for (double[] e : times) {
            for (int y = 0; y < e.length; y++) {
                if (largestValue < e[y]) {
                    largestValue = e[y];
                }
            }
        }
        animation = new double[times.size()];
    }


    @Override
    public int draw(Graphics g, int x, int y, int width) {

        int height = (int) ((width * 0.35));
        int paneX = (int) (width / 1.1);
        int paneY = (int) (height / 1.1);
        int offsetX = x + (int) (width * 0.05);
        int offsetY = y + (int) (height * 0.05) / 2;
        //g.setColor(Color.decode("#E8F5E9"));
        //g.fillRect(offsetX, offsetY, paneX, paneY);
        g.setColor(Color.black);
        g.drawLine(offsetX, offsetY, offsetX, offsetY + paneY);
        g.drawLine(offsetX, offsetY + paneY, offsetX + paneX, offsetY + paneY);

        Graphics2D g2d = (Graphics2D) g;
        g2d.setStroke(new BasicStroke(2));

        int ticks = height / 30;
        if (ticks < 2) ticks = 2;

        g.setFont(new Font("Helvetica", Font.PLAIN, 12));
        for (int i = 0; i < ticks; i++) {
            g.drawString(Integer.toString((int)(Math.round(largestValue / (ticks - 1) * i))), x, offsetY + paneY - paneY / (ticks - 1) * i);
        }

        for (int i = 0; i < times.size(); i++) {
            g.drawString(Integer.toString(i), (int) (offsetX + paneX / (times.size() - 1) * i), offsetY + paneY + 20);
        }
        int lastX = 0;
        int lastY = 0;
        double[] e = times.get(0);
        for (int j = 0; j < e.length; j++) {
            for (int i = 0; i < times.size(); i++) {
                e = times.get(i);

                g.setColor(lines[j]);
                int xPos = offsetX + (int) (paneX / (times.size() - 1) * i);
                int yPos = offsetY + (int)((int) (paneY - e[j] / largestValue * paneY) * animation[i]);
                if (animation[i] > 0) {
                    g.fillOval(xPos - 2, yPos - 2, 4, 4);
                    if (i != 0) {
                        g2d.drawLine(lastX, lastY, xPos, yPos);
                    }
                }
                lastX = xPos;
                lastY = yPos;
            }
        }

        for (int i = 0; i < times.size(); i++) {
            if (i == 0) {
                animation[i] += (1 - animation[i]) / 10.0;
            } else {
                if (animation[i - 1] > 0.5) {
                    animation[i] += (1 - animation[i]) / 10.0;
                }
            }
        }

        return height;
    }


}
