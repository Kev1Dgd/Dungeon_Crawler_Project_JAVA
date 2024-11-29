import java.awt.Graphics;
import java.awt.Image;

/**
 * The SolidSprite class represents a sprite that has solid properties,
 * meaning it may obstruct movement or interact with other objects in the game.
 * This class extends {@link Sprite} and includes specific attributes for
 * handling solid objects in the game environment.
 */
public class SolidSprite extends Sprite {
    private Image image;  // The image representing the solid sprite.
    private double width; // The width of the sprite.
    private double height; // The height of the sprite.

    /**
     * Constructs a SolidSprite with the specified image, position, and dimensions.
     *
     * @param image  the image to represent the sprite.
     * @param x      the x-coordinate of the sprite's position.
     * @param y      the y-coordinate of the sprite's position.
     * @param width  the width of the sprite.
     * @param height the height of the sprite.
     */
    public SolidSprite(Image image, double x, double y, double width, double height) {
        super(image, x, y, width, height); // Call the constructor of the parent class Sprite.
        this.image = image;
        this.width = width;
        this.height = height;
    }

    /**
     * Gets the width of the solid sprite.
     *
     * @return the width of the sprite.
     */
    public double getWidth() {
        return width;
    }

    /**
     * Gets the height of the solid sprite.
     *
     * @return the height of the sprite.
     */
    public double getHeight() {
        return height;
    }

    /**
     * Draws the sprite on the specified graphics context.
     * This method overrides the parent class's draw method.
     *
     * @param g the Graphics context on which to draw the sprite.
     */
    @Override
    public void draw(Graphics g) {
        g.drawImage(image, (int) x, (int) y, (int) width, (int) height, null); // Draw the image on the screen.
    }
}
