package gui;

import java.awt.event.KeyEvent;

/**
 * Created by Robert on 5/7/17.
 *
 * This class identifies GUIComponents that use key listener methods.
 */
public interface GKeyListener {

    /**
     * This method is the same as KeyListener's keyTyped method.
     *
     * @param e The key event.
     */
    void keyTyped(KeyEvent e);

}
