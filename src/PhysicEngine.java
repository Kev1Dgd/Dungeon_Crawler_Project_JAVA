import java.util.ArrayList;

/**
 * The PhysicEngine class handles the movement and collision detection of dynamic sprites in the game.
 * It manages a list of dynamic sprites and interacts with the environment to ensure proper physics behavior.
 */
public class PhysicEngine implements Engine {
    private ArrayList<DynamicSprite> movingSpriteList; // List of dynamic sprites to update.
    public ArrayList<Sprite> environment; // List of solid sprites representing the game environment.

    /**
     * Initializes a new PhysicEngine instance.
     */
    public PhysicEngine() {
        this.movingSpriteList = new ArrayList<>();
        this.environment = new ArrayList<>();
    }

    /**
     * Adds a dynamic sprite to the list of sprites managed by the engine.
     *
     * @param sprite the dynamic sprite to be added.
     */
    public void addMovingSprite(DynamicSprite sprite) {
        movingSpriteList.add(sprite);
    }

    /**
     * Sets the environment with a given list of solid sprites.
     *
     * @param environment the list of solid sprites in the environment.
     */
    public void setEnvironment(ArrayList<? extends Sprite> environment) {
        this.environment = new ArrayList<>(environment);
    }

    /**
     * Adds a single sprite to the environment.
     *
     * @param sprite the sprite to add to the environment.
     */
    public void addToEnvironment(Sprite sprite) {
        this.environment.add(sprite);
    }

    /**
     * Updates the state of all dynamic sprites, handles collisions, and manages special cases like companions.
     */
    @Override
    public void update() {
        // Update each dynamic sprite's position based on its movement and environment.
        for (DynamicSprite sprite : movingSpriteList) {
            sprite.moveIfPossible(environment);
        }

        // Handle collisions between dynamic sprites.
        for (int i = 0; i < movingSpriteList.size(); i++) {
            DynamicSprite spriteA = movingSpriteList.get(i);

            for (int j = i + 1; j < movingSpriteList.size(); j++) {
                DynamicSprite spriteB = movingSpriteList.get(j);

                // Check if two sprites overlap and resolve the collision.
                if (spriteA.getBounds().intersects(spriteB.getBounds())) {
                    resolveCollision(spriteA, spriteB);
                }
            }
        }

        // Update the companion's position based on its logic.
        for (DynamicSprite sprite : movingSpriteList) {
            if (sprite instanceof Companion) {
                Companion companion = (Companion) sprite;
                companion.updatePosition(environment); // Update position with respect to the environment.
            }
        }

        // Handle special collision cases between the hero and the companion.
        for (DynamicSprite sprite : movingSpriteList) {
            if (sprite instanceof Companion) {
                Companion companion = (Companion) sprite;

                // Check for collisions with other dynamic sprites (e.g., the hero).
                for (DynamicSprite otherSprite : movingSpriteList) {
                    if (otherSprite != companion && companion.getBounds().intersects(otherSprite.getBounds())) {
                        // Swap positions of the hero and the companion upon collision.
                        double tempX = companion.getX();
                        double tempY = companion.getY();

                        companion.setX(otherSprite.getX());
                        companion.setY(otherSprite.getY());

                        otherSprite.setX(tempX);
                        otherSprite.setY(tempY);
                    }
                }
            }
        }
    }

    /**
     * Resolves a collision between two dynamic sprites by adjusting their positions.
     *
     * @param spriteA the first sprite involved in the collision.
     * @param spriteB the second sprite involved in the collision.
     */
    private void resolveCollision(DynamicSprite spriteA, DynamicSprite spriteB) {
        // Calculate relative distances between the sprites.
        double dx = spriteA.getX() - spriteB.getX();
        double dy = spriteA.getY() - spriteB.getY();

        // Adjust positions based on the dominant axis of collision.
        if (Math.abs(dx) > Math.abs(dy)) {
            // Horizontal collision.
            if (dx > 0) {
                spriteA.setX(spriteA.getX() + spriteA.speed);
                spriteB.setX(spriteB.getX() - spriteB.speed);
            } else {
                spriteA.setX(spriteA.getX() - spriteA.speed);
                spriteB.setX(spriteB.getX() + spriteB.speed);
            }
        } else {
            // Vertical collision.
            if (dy > 0) {
                spriteA.setY(spriteA.getY() + spriteA.speed);
                spriteB.setY(spriteB.getY() - spriteB.speed);
            } else {
                spriteA.setY(spriteA.getY() - spriteA.speed);
                spriteB.setY(spriteB.getY() + spriteB.speed);
            }
        }
    }
}
