package gui;

import java.awt.*;

/**
 * Created by Robert on 4/12/17.
 */
public class GText implements GUIComponent {

    private String text;

    private Font font = new Font("Helvetica", Font.PLAIN, 32);

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
        g.drawString(text, x, y + font.getSize());
        return font.getSize();
    }
}
