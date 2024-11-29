import java.awt.Graphics;

/**
 * The Displayable interface defines a contract for objects
 * that can be displayed on a graphical component.
 */
public interface Displayable {

    /**
     * Draws the object using the provided Graphics context.
     *
     * @param g the Graphics object used for drawing
     */
    void draw(Graphics g);
}
