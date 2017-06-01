package gui;

/**
 * Created by Robert on 5/19/17.
 * <p>
 * This interface defines GUIComponents that will be animated.
 * The updateAnimations method is called and should be used to increment
 * variables used for animations.
 */
public interface GAnimation {

    /**
     * This method is called after elements are drawn and is used to update
     * variables associated with animations.
     *
     * @author Robert
     */
    void updateAnimations();

}
