package gui;

import java.util.ArrayList;

/**
 * Created by Robert on 5/7/17.
 *
 * This interface identifies GUIComponents that holds their own lists of GUIComponents like GDivider
 * and GMenuBar.
 */
public interface GSubList {

    /**
     * Get the lists of GUIComponents that this class holds.
     *
     * @return A list of GUIComponent lists.
     */
    ArrayList<ArrayList<GUIComponent>> getComponents();

}
