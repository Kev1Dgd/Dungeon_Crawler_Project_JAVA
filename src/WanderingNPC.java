import java.awt.Image;
import java.util.ArrayList;
import java.util.Random;

public class WanderingNPC extends DynamicSprite {
    private Random random;
    private long lastDirectionChangeTime;
    private final int directionChangeInterval; // Temps entre deux changements de direction (en ms)
    private final double movementSpeedFactor;  // Facteur de vitesse pour ralentir le PNJ

    public WanderingNPC(Image spriteSheet, double x, double y, double width, double height, int spriteSheetNumberOfColumn) {
        super(spriteSheet, x, y, width, height, false, spriteSheetNumberOfColumn);
        this.random = new Random();
        this.lastDirectionChangeTime = System.currentTimeMillis();
        this.directionChangeInterval = 2000; // Change de direction toutes les 1 seconde
        this.movementSpeedFactor = 0.5; // Vitesse réduite par rapport au héros

        // Initialisation avec une vitesse réduite et une direction aléatoire
        this.speed *= movementSpeedFactor;
        setDirection(getRandomDirection());
    }

    private Direction getRandomDirection() {
        int randomIndex = random.nextInt(Direction.values().length); // Choisir une direction aléatoire
        return Direction.values()[randomIndex];
    }

    public void update(ArrayList<Sprite> environment) {
        long currentTime = System.currentTimeMillis();

        // Changer de direction si l'intervalle est dépassé
        if (currentTime - lastDirectionChangeTime > directionChangeInterval) {
            setDirection(getRandomDirection());
            lastDirectionChangeTime = currentTime;
        }

        // Déplacement avec la vitesse réduite
        moveIfPossible(environment);
    }
}
