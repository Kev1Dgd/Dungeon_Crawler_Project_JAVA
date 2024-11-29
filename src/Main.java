import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 * The Main class serves as the entry point for the game application.
 * It initializes the game environment, creates characters, sets up the game engines,
 * and starts the game loop.
 */
public class Main {
    public static void main(String[] args) {
        // Create the main game window (JFrame).
        JFrame displayZoneFrame = new JFrame("Hero's Game");
        displayZoneFrame.setSize(1920, 1056); // Set the window size.
        displayZoneFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Close on exit.
        displayZoneFrame.setLocationRelativeTo(null); // Center the window on the screen.

        // Load the game environment (Playground) from a level file.
        Playground playground = new Playground("C:/Users/kevin/Documents/TD4/data/level.txt");

        // Create the hero character (DynamicSprite).
        DynamicSprite hero = new DynamicSprite(
                new ImageIcon("C:/Users/kevin/Documents/TD4/img/PokemonTileSheet.png").getImage(),
                196, 448, 57, 57, true, 4
        );

        // Create the companion character, initially positioned to the right of the hero.
        Companion companion = new Companion(
                new ImageIcon("C:/Users/kevin/Documents/TD4/img/Charmander.png").getImage(),
                296, 448, 57, 57, hero, 4
        );

        // Create a non-player character (WanderingNPC).
        WanderingNPC npc = new WanderingNPC(
                new ImageIcon("C:/Users/kevin/Documents/TD4/img/NPC.png").getImage(),
                500, 500, 57, 57, 3
        );

        // Set the directional animation order for the hero, NPC, and companion.
        int[] heroDirectionOrder = {0, 1, 2, 3}; // South, West, East, North.
        hero.setDirectionOrder(heroDirectionOrder);

        int[] npcDirectionOrder = {0, 2, 3, 1}; // South, West, East, North.
        npc.setDirectionOrder(npcDirectionOrder);

        int[] companionDirectionOrder = {1, 0, 2, 3}; // West, East, South, North.
        companion.setDirectionOrder(companionDirectionOrder);

        // Initialize the physics engine and the rendering engine.
        PhysicEngine physicEngine = new PhysicEngine();
        GameRender renderEngine = new GameRender();

        // Add the static environment elements (e.g., walls) to the physics engine.
        ArrayList<Sprite> solidSprites = playground.getSolidSpriteList();
        physicEngine.setEnvironment(solidSprites);
        physicEngine.addToEnvironment(npc);
        physicEngine.addToEnvironment(hero);

        // Add the environment elements to the rendering engine.
        for (Displayable displayable : playground.getSpriteList()) {
            renderEngine.addToRenderList(displayable);
        }

        // Add the hero to the physics and rendering engines, ensuring it appears in front of the environment.
        physicEngine.addMovingSprite(hero);
        renderEngine.addToRenderList(hero);

        // Add the companion to the physics and rendering engines.
        physicEngine.addMovingSprite(companion);
        renderEngine.addToRenderList(companion);

        // Add the NPC to the physics and rendering engines.
        physicEngine.addMovingSprite(npc);
        renderEngine.addToRenderList(npc);

        // Add the rendering engine to the JFrame.
        displayZoneFrame.getContentPane().add(renderEngine);

        // Configure the game loop using a Swing Timer for periodic updates.
        Timer gameLoop = new Timer(16, e -> {
            // Update the NPC behavior.
            npc.update(physicEngine.environment);

            // Update all moving entities and refresh the display.
            physicEngine.update();
            renderEngine.repaint();
        });
        gameLoop.start();

        // Add a KeyListener to the JFrame to handle keyboard inputs for controlling the hero.
        displayZoneFrame.addKeyListener(new GameEngine(hero));
        displayZoneFrame.setFocusable(true); // Ensure the JFrame receives keyboard focus.

        // Display the game window.
        displayZoneFrame.setVisible(true);
    }
}
