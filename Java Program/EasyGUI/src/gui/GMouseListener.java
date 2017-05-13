package gui;

import java.awt.event.MouseEvent;

/**
 * Created by Robert on 5/7/17.
 *
 * This interface identifies classes that use mouse events.
 */
public interface GMouseListener {

    /**
     * This method is mostly the same as MouseListener's mouse pressed event.
     * The only differences is that it will return a boolean if a mouse event if correctly fired.
     * An example of this is pressing a button. When this method returns true the loop that triggers
     * all of the mouse events will be broken making it so that when two buttons are on top of each other
     * only one will be fired.
     *
     * @param e The mouse event.
     * @return Whether a mouse event has been correctly fired.
     */
    boolean mousePressed(MouseEvent e);

    /**
     * This method is mostly the same as MouseListener's mouse released event.
     * The only differences is that it will return a boolean if a mouse event if correctly fired.
     * An example of this is pressing a button. When this method returns true the loop that triggers
     * all of the mouse events will be broken making it so that when two buttons are on top of each other
     * only one will be fired.
     *
     * @param e The mouse event.
     * @return Whether a mouse event has been correctly fired.
     */
    boolean mouseReleased(MouseEvent e);

}
