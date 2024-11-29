import java.awt.*;
import java.util.ArrayList;

/**
 * The Companion class represents a companion sprite that follows the hero's movements
 * while maintaining a specific offset based on the hero's direction.
 */
public class Companion extends DynamicSprite {
    private DynamicSprite hero; // The hero sprite that this companion follows
    private double lastX, lastY; // Variables to store the last known position of the companion

    /**
     * Constructor for the Companion class.
     *
     * @param spriteSheet the image representing the companion's sprite sheet
     * @param x           the initial x-coordinate of the companion
     * @param y           the initial y-coordinate of the companion
     * @param width       the width of the companion sprite
     * @param height      the height of the companion sprite
     * @param hero        the hero sprite to follow
     */
    public Companion(Image spriteSheet, double x, double y, double width, double height, DynamicSprite hero, int spriteSheetNumberOfColumn) {
        super(spriteSheet, x, y, width, height, false, spriteSheetNumberOfColumn);
        this.hero = hero;
        this.lastX = x;
        this.lastY = y;
    }

    /**
     * Updates the position and animation of the companion to follow the hero.
     *
     * @param environment the list of sprites in the game environment, used for collision checks
     */
    public void updatePosition(ArrayList<Sprite> environment) {
        Direction heroDirection = hero.getDirection(); // Get the current direction of the hero

        // If the hero is not moving, retain the last known position
        if (heroDirection != null) {
            // Move the companion relative to the hero's direction with a small offset
            switch (heroDirection) {
                case NORTH:
                    setY(hero.getY() + hero.getHeight() + 0.5); // Follow slightly below the hero
                    setX(hero.getX());
                    break;
                case SOUTH:
                    setY(hero.getY() - getHeight() - 0.5); // Follow slightly above the hero
                    setX(hero.getX());
                    break;
                case EAST:
                    setX(hero.getX() - getWidth() - 0.5); // Offset to the left of the hero
                    setY(hero.getY());
                    break;
                case WEST:
                    setX(hero.getX() + hero.getWidth() + 0.5); // Offset to the right of the hero
                    setY(hero.getY());
                    break;
            }

            // Update the last valid position
            lastX = getX();
            lastY = getY();

            // Update the animation direction to match the hero's direction
            setDirection(heroDirection);
        } else {
            // If the hero is stationary, retain the last known position
            setX(lastX);
            setY(lastY);
            setDirection(null); // No direction update for the animation
        }
    }

    /**
     * Draws the companion sprite on the screen.
     *
     * @param g the Graphics object used for rendering
     */
    @Override
    public void draw(Graphics g) {
        super.draw(g); // Call the superclass's draw method to handle dynamic rendering
    }
}
