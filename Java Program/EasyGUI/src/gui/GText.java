package gui;

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

    /**
     * Basic constructor to create text with default settings.
     *
     * @param text The text.
     * @author Robert
     */
    public GText(final String text) {
        this.text = text;
    }

    /**
     * Creates text with a font.
     *
     * @param text The text.
     * @param font The font.
     * @author Robert
     */
    public GText(final String text, final Font font) {
        this.text = text;
        this.font = font;
    }

    /**
     * Creates text with a font and color.
     *
     * @param text The text.
     * @param font The font.
     * @param color The color.
     * @author Robert
     */
    public GText(final String text, final Font font, final Color color) {
        this(text, font);
        this.color = color;
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
            int sidePadding = 5;
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
     * @param text The text to change to.
     * @author Robert
     */
    void setText(String text) {
        this.text = text;
    }

    /**
     * This method gets the text that this text object draws.
     *
     * @return The text.
     * @author Robert
     */
    String getText() {
        return text;
    }

    /**
     * This method sets the color of the text.
     *
     * @param color The color to change to.
     * @author Robert
     */
    void setColor(Color color) {
        this.color = color;
    }

    /**
     * This method gets the color of the text.
     *
     * @return The color of the text.
     * @author Robert
     */
    Color getColor() {
        return color;
    }
}
