import java.awt.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;

public class Stamina {
    private double stamina;
    private final double maxStamina = 5.0; // 5 secondes
    private final double staminaRegenRate = 1.67; // Recharge complète en 3 secondes
    private final double staminaDrainRate = 1.0; // Consommation en 5 secondes de sprint
    private boolean sprinting;
    private boolean outOfStamina;
    private long lastUpdateTime;

    private BufferedImage staminaBarSheet;
    private final int staminaBarFrames = 11; // 11 images utilisables dans la tilesheet
    private BufferedImage[] staminaBarImages;
    private int staminaBarWidth;
    private int staminaBarHeight;

    public Stamina() {
        // Initialisation de la stamina
        this.stamina = maxStamina;
        this.sprinting = false;
        this.outOfStamina = false;
        this.lastUpdateTime = System.currentTimeMillis();

        // Charger la barre de stamina
        try {
            this.staminaBarSheet = ImageIO.read(new File("C:/Users/kevin/Documents/TD4/img/Stamina.png"));
            this.staminaBarWidth = 100;  // Largeur de chaque barre
            this.staminaBarHeight = 10; // Hauteur de chaque barre
            this.staminaBarImages = new BufferedImage[staminaBarFrames];

            for (int i = 0; i < staminaBarFrames; i++) { // On charge toutes les 11 frames
                int row = 0;  // Comme il y a une seule ligne pour la barre
                int col = i;  // Colonne correspondant à chaque image de la barre de stamina

                // Calcul des coordonnées de découpe
                int imageX = col * staminaBarWidth; // Décalage horizontal
                int imageY = row * staminaBarHeight; // Décalage vertical

                // Découper l'image de la barre de stamina
                staminaBarImages[i] = staminaBarSheet.getSubimage(imageX, imageY, staminaBarWidth, staminaBarHeight);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Mettre à jour la stamina
    public void updateStamina(boolean sprinting) {
        long currentTime = System.currentTimeMillis();
        double deltaTime = (currentTime - lastUpdateTime) / 1000.0; // En secondes
        lastUpdateTime = currentTime;

        if (sprinting && stamina > 0) {
            stamina -= deltaTime * staminaDrainRate;
            if (stamina <= 0) {
                stamina = 0;
                outOfStamina = true;
            }
        } else if (!sprinting) {
            stamina += deltaTime * staminaRegenRate;
            if (stamina >= maxStamina) {
                stamina = maxStamina;
                outOfStamina = false;
            }
        }
    }

    // Retourner l'indice de la barre de stamina à afficher
    public int getStaminaBarIndex() {
        double ratio = stamina / maxStamina; // Ratio de la stamina restante
        int index = (int) Math.round((staminaBarFrames - 1) * ratio);  // Calculer un indice de 0 à 10 (11 images au total)

        return Math.min(index, staminaBarFrames - 1); // Retourner un indice de 0 à 10
    }

    // Dessiner la barre de stamina au-dessus du personnage
    public void drawStaminaBar(Graphics g, double x, double y, double width) {
        int barX = (int) x + (int) (width / 2) - (staminaBarWidth / 2);
        int barY = (int) y - staminaBarHeight - 10; // Position 10px au-dessus

        int staminaIndex = getStaminaBarIndex(); // Indice de la barre à afficher

        g.drawImage(
                staminaBarImages[staminaIndex], // Image déjà découpée
                barX, barY, // Position
                staminaBarWidth, staminaBarHeight, // Dimensions
                null
        );
    }

    // Getter pour savoir si le personnage est à court de stamina
    public boolean isOutOfStamina() {
        return outOfStamina;
    }

    // Setter pour le sprint
    public void setSprint(boolean sprinting) {
        this.sprinting = sprinting;
    }

    // Getter pour récupérer la stamina actuelle
    public double getStamina() {
        return stamina;
    }
}
