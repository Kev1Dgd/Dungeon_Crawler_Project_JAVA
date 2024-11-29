/**
 * Represents an engine that can be updated over time.
 * Implementing classes should define the behavior of the update process.
 */
public interface Engine {

    /**
     * Updates the state of the engine.
     * This method is intended to be called regularly (e.g., in a game loop)
     * to handle logic such as movement, physics, or other dynamic behaviors.
     */
    void update();
}
