package gui;

import control.Style;

import java.awt.*;
import java.util.ArrayList;

/**
 * Created by Robert on 4/12/17.
 *
 * GText is exactly what its name is. Text. Text will
 * auto fit the assigned area so you don't know
 * the width or height of the space the text will fill, it just will.
 * 
 */
public class GText implements GUIComponent {

    /** The text. */
    private String text;

    /** The default font. */
    private Font font = Style.titleFont;

    /** The default color. */
    private Color color = Style.defaultTextColor;

    /** The padding on the left and right side of text. */
    private int sidePadding = 5;

    /**
     * Basic constructor to create text with default settings.
     *
     * @param text The text.
     */
    public GText(final String text) {
        this.text = text;
    }

    /**
     * Creates text with a font.
     *
     * @param text The text.
     * @param font The font.
     */
    public GText(final String text, final Font font) {
        this.text = text;
        setFont(font);
    }

    /**
     * Creates text with a font and color.
     *
     * @param text The text.
     * @param font The font.
     * @param color The color.
     */
    public GText(final String text, final Font font, final Color color) {
        this(text, font);
        this.color = color;
    }

    /**
     * Sets the font of the text.
     *
     * @param font The font to change to.
     */
    public void setFont(final Font font) {
        this.font = font;
    }

    @Override
    public int draw(Graphics g, int x, int y, int width) {
        g.setFont(font);
        g.setColor(color);
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

    /**
     * This method sets the text that this text object draws.
     *
     * @param text
     */
    public void setText(String text) {
        this.text = text;
    }
}
