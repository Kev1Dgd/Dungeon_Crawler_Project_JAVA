import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

/**
 * Represents a dynamic sprite with movement, animations, and stamina management.
 * It can move in a defined environment, interact with solid objects, and optionally act as the hero sprite.
 */
public class DynamicSprite extends SolidSprite {
    private boolean isHero; // Indicates if this sprite is the player-controlled hero
    private boolean isWalking; // Indicates if the sprite is currently walking
    public double speed; // Speed of the sprite's movement
    private final int spriteSheetNumberOfColumn; // Number of columns in the sprite sheet for animations
    private int timeBetweenFrame; // Time between animation frames in milliseconds
    private Direction direction; // Current movement direction
    private Direction lastDirection; // Last known movement direction (used when idle)
    private Image spriteSheet; // Sprite sheet image used for animations
    private int[] directionOrder; // Customizable order for sprite sheet rows based on direction

    // Stamina system attributes
    private double stamina; // Current stamina level
    private final double maxStamina = 5.0; // Maximum stamina (in seconds)
    private final double staminaRegenRate = 1.67; // Stamina regeneration rate (full recharge in ~3 seconds)
    private final double staminaDrainRate = 1.0; // Stamina depletion rate when sprinting
    private boolean sprinting; // Indicates if the sprite is sprinting
    private boolean outOfStamina; // Indicates if the sprite has run out of stamina
    private long lastUpdateTime; // Timestamp of the last stamina update

    // Attributes related to the stamina bar
    private BufferedImage staminaBarSheet; // Sprite sheet containing the stamina bar
    private final int staminaBarFrames = 15; // Number of frames for the stamina bar animation
    private BufferedImage[] staminaBarImages; // Array of individual stamina bar images
    private int staminaBarWidth; // Width of the stamina bar
    private int staminaBarHeight; // Height of the stamina bar

    /**
     * Creates a new DynamicSprite.
     *
     * @param spriteSheet The sprite sheet used for animations.
     * @param x           The initial x-coordinate of the sprite.
     * @param y           The initial y-coordinate of the sprite.
     * @param width       The width of the sprite.
     * @param height      The height of the sprite.
     * @param isHero      True if the sprite is the hero, false otherwise.
     */
    public DynamicSprite(Image spriteSheet, double x, double y, double width, double height, boolean isHero, int spriteSheetNumberOfColumn) {
        super(spriteSheet, x, y, width, height);
        this.isWalking = true;
        this.speed = 3;
        this.spriteSheetNumberOfColumn = spriteSheetNumberOfColumn;
        this.timeBetweenFrame = 200;
        this.direction = Direction.SOUTH; // Default direction (facing south)
        this.lastDirection = Direction.SOUTH; // Default last direction
        this.spriteSheet = spriteSheet;

        // Initialize stamina
        this.stamina = maxStamina;
        this.sprinting = false;
        this.outOfStamina = false;
        this.lastUpdateTime = System.currentTimeMillis();

        // Default direction order: SOUTH, WEST, EAST, NORTH
        this.directionOrder = new int[]{2, 1, 3, 0};

        this.isHero = isHero;

        // Load the stamina bar sprite sheet and slice it into individual frames
        try {
            this.staminaBarSheet = ImageIO.read(new File("C:/Users/kevin/Documents/TD4/img/Stamina.png"));
            this.staminaBarWidth = staminaBarSheet.getWidth();
            this.staminaBarHeight = staminaBarSheet.getHeight();
            this.staminaBarImages = new BufferedImage[staminaBarFrames];

            for (int i = 0; i < staminaBarFrames; i++) {
                staminaBarImages[i] = staminaBarSheet.getSubimage(
                        staminaBarWidth / staminaBarFrames * i,
                        0,
                        staminaBarWidth / staminaBarFrames,
                        staminaBarHeight);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Sets a custom order for the rows in the sprite sheet corresponding to different directions.
     *
     * @param directionOrder An array specifying the order of the rows in the sprite sheet.
     */
    public void setDirectionOrder(int[] directionOrder) {
        this.directionOrder = directionOrder;
    }

    /**
     * Checks if this sprite is the hero.
     *
     * @return True if this sprite is the hero, false otherwise.
     */
    public boolean isHero() {
        return isHero;
    }

    /**
     * Sets the current movement direction of the sprite.
     *
     * @param direction The new direction to set.
     */
    public void setDirection(Direction direction) {
        this.direction = direction;
        if (direction != null) {
            this.lastDirection = direction; // Update the last known direction
        }
    }

    /**
     * Moves the sprite in its current direction.
     */
    private void move() {
        if (direction == null) return;

        switch (direction) {
            case NORTH -> setY(getY() - speed);
            case SOUTH -> setY(getY() + speed);
            case EAST -> setX(getX() + speed);
            case WEST -> setX(getX() - speed);
        }
    }

    /**
     * Checks if the sprite can move in its current direction without colliding with obstacles.
     *
     * @param environment The environment containing other sprites.
     * @return True if the movement is possible, false otherwise.
     */
    private boolean isMovingPossible(ArrayList<Sprite> environment) {
        if (direction == null) return false;

        Rectangle2D.Double hitBox = new Rectangle2D.Double(getX(), getY(), getWidth(), getHeight());

        switch (direction) {
            case NORTH -> hitBox.setFrame(getX(), getY() - speed, getWidth(), getHeight());
            case SOUTH -> hitBox.setFrame(getX(), getY() + speed, getWidth(), getHeight());
            case EAST -> hitBox.setFrame(getX() + speed, getY(), getWidth(), getHeight());
            case WEST -> hitBox.setFrame(getX() - speed, getY(), getWidth(), getHeight());
        }

        for (Sprite sprite : environment) {
            if (sprite instanceof SolidSprite && sprite != this) {
                SolidSprite solidSprite = (SolidSprite) sprite;
                if (hitBox.intersects(solidSprite.getBounds())) {
                    return false; // Collision detected
                }
            }
        }

        return true;
    }

    /**
     * Moves the sprite if no collisions are detected in the current direction.
     *
     * @param environment The environment containing other sprites.
     */
    public void moveIfPossible(ArrayList<Sprite> environment) {
        if (this.direction == null) return;
        if (isMovingPossible(environment)) {
            move();
        }
    }

    /**
     * Enables or disables sprinting for the sprite.
     *
     * @param sprinting True to enable sprinting, false to disable it.
     */
    public void setSprint(boolean sprinting) {
        this.sprinting = sprinting && stamina > 0;
        this.speed = this.sprinting ? 6 : 3;
    }

    /**
     * Updates the stamina level of the sprite, handling depletion and regeneration.
     */
    private void updateStamina() {
        long currentTime = System.currentTimeMillis();
        double deltaTime = (currentTime - lastUpdateTime) / 1000.0;
        lastUpdateTime = currentTime;

        if (sprinting && stamina > 0) {
            stamina -= deltaTime * staminaDrainRate;
            if (stamina <= 0) {
                stamina = 0;
                outOfStamina = true;
                setSprint(false);
            }
        } else if (!sprinting) {
            stamina += deltaTime * staminaRegenRate;
            if (stamina >= maxStamina) {
                stamina = maxStamina;
                outOfStamina = false;
            }
        }
    }

    /**
     * Calculates the index of the stamina bar frame based on the current stamina level.
     *
     * @return The index of the stamina bar frame.
     */
    private int getStaminaBarIndex() {
        double ratio = stamina / maxStamina;
        int index = (int) Math.round((staminaBarFrames - 1) * ratio);
        return Math.min(index, staminaBarFrames - 1)*-1 +staminaBarFrames -1;
    }

    /**
     * Draws the stamina bar above the sprite.
     *
     * @param g The graphics context.
     */
    private void drawStaminaBar(Graphics g) {
        int barX = (int) getX() + (int) (getWidth() / 2) - (staminaBarWidth / staminaBarFrames / 2);
        int barY = (int) getY() - staminaBarHeight + 18;

        int staminaIndex = getStaminaBarIndex();
        g.drawImage(
                staminaBarImages[staminaIndex],
                barX, barY,
                staminaBarWidth / staminaBarFrames, staminaBarHeight,
                null
        );
    }

    /**
     * Draws the sprite and its stamina bar (if applicable).
     *
     * @param g The graphics context.
     */
    @Override
    public void draw(Graphics g) {
        updateStamina();

        long currentTime = System.currentTimeMillis();
        int index = (int) ((currentTime / timeBetweenFrame) % spriteSheetNumberOfColumn);

        int directionIndex = (direction != null ? direction.getValue() : lastDirection.getValue());
        int attitude = directionOrder[directionIndex];
        int spriteWidth = 57;
        int spriteHeight = 57;

        g.drawImage(spriteSheet,
                (int) getX(), (int) getY(),
                (int) (getX() + getWidth()), (int) (getY() + getHeight()),
                index * spriteWidth, attitude * spriteHeight,
                (index + 1) * spriteWidth, (attitude + 1) * spriteHeight,
                null);

        if (isHero()) {
            drawStaminaBar(g);
        }
    }

    /**
     * Gets the current movement direction of the sprite.
     *
     * @return The current direction.
     */
    public Direction getDirection() {
        return direction;
    }
}
