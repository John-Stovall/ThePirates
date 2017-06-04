package gui;

import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Scene;
import javafx.stage.Stage;

import javax.swing.*;
import javax.swing.plaf.nimbus.State;
import java.awt.*;

/**
 * Created by Robert on 4/14/17.
 * <p>
 * This class adds compatibility for JavaFX components.
 */
public class GJavaFXWrapper extends JFXPanel implements GUIComponent {

    /**
     * The height of the wrapper.
     */
    private int height;

    protected Stage stage = new Stage();

    /**
     * Create a wrapper.
     *
     * @param height The height of the area.
     * @author Robert
     */
    public GJavaFXWrapper(final int height) {
        this.height = height;
        setOpaque(false);
        setLayout(new BorderLayout());
        setVisible(true);


        Stage stage = new Stage();
        stage.show();

    }

    @Override
    public int draw(Graphics g, int x, int y, int width) {
        setLocation(x, y);
        setSize(width, height);
        revalidate();
        return height;
    }
}
