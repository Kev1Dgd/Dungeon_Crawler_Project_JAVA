import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * The GameEngine class handles keyboard input to control the hero's actions.
 * It implements the {@link KeyListener} interface to listen for key events.
 */
public class GameEngine implements KeyListener {
    private DynamicSprite hero; // The hero sprite controlled by this engine.

    /**
     * Constructs a GameEngine instance linked to the specified hero.
     *
     * @param hero the DynamicSprite representing the hero.
     */
    public GameEngine(DynamicSprite hero) {
        this.hero = hero;
    }

    /**
     * Handles key press events. Determines the direction of the hero's movement
     * or activates sprinting when specific keys are pressed.
     *
     * @param e the KeyEvent triggered when a key is pressed.
     */
    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_UP:    // Move hero north.
                hero.setDirection(Direction.NORTH);
                break;
            case KeyEvent.VK_DOWN:  // Move hero south.
                hero.setDirection(Direction.SOUTH);
                break;
            case KeyEvent.VK_LEFT:  // Move hero west.
                hero.setDirection(Direction.WEST);
                break;
            case KeyEvent.VK_RIGHT: // Move hero east.
                hero.setDirection(Direction.EAST);
                break;
            case KeyEvent.VK_SHIFT: // Activate sprinting.
                hero.setSprint(true);
                break;
            default:
                break; // No action for other keys.
        }
    }

    /**
     * Handles key release events. Stops the hero's movement or deactivates
     * sprinting when specific keys are released.
     *
     * @param e the KeyEvent triggered when a key is released.
     */
    @Override
    public void keyReleased(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_UP:    // Stop moving when the key is released.
            case KeyEvent.VK_DOWN:
            case KeyEvent.VK_LEFT:
            case KeyEvent.VK_RIGHT:
                hero.setDirection(null); // Stop hero movement.
                break;
            case KeyEvent.VK_SHIFT: // Deactivate sprinting.
                hero.setSprint(false);
                break;
            default:
                break; // No action for other keys.
        }
    }

    /**
     * Handles key typed events. This method is not used in the current implementation.
     *
     * @param e the KeyEvent triggered when a key is typed.
     */
    @Override
    public void keyTyped(KeyEvent e) {
        // Not used in this implementation.
    }
}
