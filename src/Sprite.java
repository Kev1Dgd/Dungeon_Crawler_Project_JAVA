import java.awt.Graphics;
import java.awt.Image;
import java.awt.geom.Rectangle2D;

/**
 * The Sprite class represents a generic visual element in the game, which can
 * be displayed and manipulated. This class provides common attributes and
 * methods for handling position, dimensions, and rendering of the sprite.
 */
public class Sprite implements Displayable {
    private Image image; // The image representing the sprite.
    protected double x, y; // The position of the sprite (top-left corner).
    private double width, height; // The dimensions of the sprite.

    /**
     * Constructs a Sprite with the specified image, position, and dimensions.
     *
     * @param image  the image representing the sprite.
     * @param x      the x-coordinate of the sprite's position.
     * @param y      the y-coordinate of the sprite's position.
     * @param width  the width of the sprite.
     * @param height the height of the sprite.
     */
    public Sprite(Image image, double x, double y, double width, double height) {
        this.image = image;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    /**
     * Gets the x-coordinate of the sprite's position.
     *
     * @return the x-coordinate.
     */
    public double getX() {
        return x;
    }

    /**
     * Sets the x-coordinate of the sprite's position.
     *
     * @param x0 the new x-coordinate.
     */
    public void setX(double x0) {
        x = x0;
    }

    /**
     * Gets the y-coordinate of the sprite's position.
     *
     * @return the y-coordinate.
     */
    public double getY() {
        return y;
    }

    /**
     * Sets the y-coordinate of the sprite's position.
     * Prints the change in the y-coordinate if the sprite is not an instance
     * of WanderingNPC (for debugging purposes).
     *
     * @param y0 the new y-coordinate.
     */
    public void setY(double y0) {
        if (!(this instanceof WanderingNPC)) {
            System.out.println(y - y0); // Debugging statement to track y changes.
        }
        y = y0;
    }

    /**
     * Gets the bounding box of the sprite as a Rectangle2D.Double object.
     * This is useful for collision detection or spatial queries.
     *
     * @return the bounding box of the sprite.
     */
    public Rectangle2D.Double getBounds() {
        return new Rectangle2D.Double(x, y, width, height);
    }

    /**
     * Draws the sprite on the specified graphics context.
     * This method implements the draw method from the Displayable interface.
     *
     * @param g the Graphics context on which to draw the sprite.
     */
    @Override
    public void draw(Graphics g) {
        g.drawImage(image, (int) x, (int) y, (int) width, (int) height, null);
    }
}
