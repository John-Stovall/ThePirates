package gui;

import java.awt.*;
import java.util.ArrayList;

/**
 * Created by Robert on 4/12/17.
 */
public class GText implements GUIComponent {

    private String text;

    private Font font = new Font("Helvetica", Font.PLAIN, 32);

    private int sidePadding = 5;

    public GText(final String text) {
        this.text = text;
    }

    public GText(final String title, final Font font) {
        this.text = title;
        setFont(font);
    }

    public void setFont(final Font font) {
        this.font = font;
    }


    @Override
    public int draw(Graphics g, int x, int y, int width) {
        g.setFont(font);
        int h;

        if (g.getFontMetrics().stringWidth(text) > width) {
            ArrayList<String> lines = new ArrayList<>();
            int spaceIndex = -1;
            int lastIndex = 0;
            for (int i = 0; i < text.length(); i++) {
                if (text.charAt(i) == ' ') {
                    spaceIndex = i;
                }
                if (g.getFontMetrics().stringWidth(text.substring(lastIndex, i) + sidePadding) > width) {
                    if (spaceIndex != -1) {
                        lines.add(text.substring(lastIndex, spaceIndex).trim());
                        lastIndex = spaceIndex;
                        spaceIndex = -1;
                    } else {
                        lines.add(text.substring(lastIndex , i));
                        lastIndex = i;
                    }
                }
            }
            lines.add(text.substring(lastIndex).trim());


            for (int i = 0; i < lines.size(); i++) {
                g.drawString(lines.get(i), x, y + font.getSize() * (i + 1));
            }

            h = font.getSize() * lines.size();
        } else {
            g.drawString(text, x, y + font.getSize());
            h = font.getSize();

        }

        return h;
    }
}
